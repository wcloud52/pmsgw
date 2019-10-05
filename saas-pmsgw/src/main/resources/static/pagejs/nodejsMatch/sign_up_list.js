layui.use(['element', 'laydate', 'table', 'form', 'fuzzyQuery', 'layedit'], function () {
    var $ = layui.$;
    var fuzzyQuery = layui.fuzzyQuery;
    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
    var layer = layui.layer;// 获取当前窗口的layer对象
    var table = layui.table;
    var laydate = layui.laydate;

    fuzzyQuery.clearQuery();
    fuzzyQuery.addEqual('match_id',match_id)
    var fuzzyQueryJson = fuzzyQuery.getQuery();
    var cols=[];
    var tableCount=0;
    var one=[
        {
        title: '序号',
        type: 'numbers',
        rowspan:2,
        align: 'center',
        fixed: 'left'
    }, {
        type: 'checkbox',
        rowspan:2,
        align: 'center',
        fixed: 'left'

    }, {
        field: 'member_code',
        rowspan:2,
        align: 'center',
        width: 150,
        title: '会员编号'
    }, {
        field: 'member_name',
        rowspan:2,
        width: 150,
        align: 'center',
        title: '鸽主姓名'
    }, {
        field: 'pigeon_code',
        rowspan:2,
        width: 150,
        align: 'center',
        title: '参赛鸽环号'
    }];
    var two=[];
    var rule=JSON.parse(tableRowData.rule)
    for (var i = 0; i < rule.length; i++) {
        if (rule[i]['rule']['grade_money'].length>1) {
            one.push({
                field:'rule-'+rule[i]['value'],
                colspan:rule[i]['rule']['grade_money'].length,
                align: 'center',
                title: rule[i]['name']
            });
            for (var j = 0; j < rule[i]['rule']['grade_money'].length; j++) {
                two.push({
                    field: 'field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][j],
                    align: 'center',
                    title: rule[i]['rule']['grade_money'][j]
                })
            }
        }else {
            one.push({
                field:'field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][0],
                rowspan:2,
                align: 'center',
                width:80,
                title: rule[i]['name']+'\r\n'+rule[i]['rule']['grade_money'][0]
            });
        }


    }
    one.push({
        field:'sum',
        width: 80,
        align: 'center',
        rowspan:2,
        title: '合计'
    });
    cols.push(one)
    cols.push(two)

    baseOperation.initTableOption("signUpListTable", "/nodejsMatch/sign_up_list", {
            field: 'create_time',
            type: 'desc'
        },
        'create_time desc',
        JSON.stringify(fuzzyQueryJson),
        cols, layui,{
            done: function(res, curr, count){
                tableCount=count;
                tableOption();
            }
        });

    table.on('sort(test)', function (obj) {
        baseOperation.sortTable(obj, "signUpListTable", layui);

    });
    $('#btnSignUpAdd').on('click', function () {
        var url = '/nodejsMatch/sign_up_editPage.html' ;
        var opUrl = "/nodejsMatch/sign_up_insert";
        baseOperation.editFormOption(url, '内容管理', layui,{
            yes:function (index) {
                var trs=$('#sign_up_table tr');
                var result=[];
                debugger
                for (var i=0;i<trs.length;i++){
                    var regist={};
                    var checkboxs=$(trs).eq(i).find('[type="checkbox"]:checked');
                    for (var j = 0; j < checkboxs.length; j++) {
                        var key=$(checkboxs).eq(j).attr('name');
                        var val=$(checkboxs).eq(j).val();
                        regist[key]=val;
                    }
                    var pigeon_code=$(trs).eq(i).find('[name="pigeon_code"]').val();
                    debugger
                    if ( Object.keys(regist).length>0&&pigeon_code!=undefined&&pigeon_code!='') {
                        $(trs).eq(i).find('[name="regist"]').val(JSON.stringify(regist));
                        var row = {
                            'id': $(trs).eq(i).find('[name="id"]').val(),
                            'match_id': $(trs).eq(i).find('[name="match_id"]').val(),
                            'regist': $(trs).eq(i).find('[name="regist"]').val(),
                            'member_code': $(trs).eq(i).find('[name="member_code"]').val(),
                            'member_name': $(trs).eq(i).find('[name="member_name"]').val(),
                            'pigeon_code': $(trs).eq(i).find('[name="pigeon_code"]').val(),
                        }
                        result.push(row);
                    }else if($(trs).eq(i).find('[name="id"]').val()!=undefined&&$(trs).eq(i).find('[name="id"]').val()!=''){
                        var row = {
                            'id': $(trs).eq(i).find('[name="id"]').val(),
                            'match_id': $(trs).eq(i).find('[name="match_id"]').val(),
                        }
                        result.push(row);
                    }
                }
                if (result.length<=0){
                    layer.msg('信息填写不完整',{icon:5});
                    return
                }
                $.ajax({
                    type : "POST",
                    url :  "/nodejsMatch/sign_up_insert",
                    contentType : 'application/json',
                    data : JSON.stringify(result),
                    success : function(data) {
                        if (data.code == 0) {
                            layer.msg('保存成功',{icon:1});
                            debugger
                            $('#temp').nextAll('[id!="tempSum"]').remove();
                            $('#temp').find('[type="text"]').val('');
                            $('#temp').find('[name="regist"]').val('');
                            $('#temp').find('[type="checkbox"]').prop("checked",'');
                            $('#temp').find('.sum').text('0');
                            $('#tempSum').find('.sum').text('0');
                            $('#tempSum').find('.allSum').text('0');
                            layui.form.render();
                            //layer.close(addBoxIndex);

                        } else {
                            layer.msg('保存失败',{icon:5});
                            layer.close(addBoxIndex);
                        }
                    }
                });
            },
            cancel:function(){
                flashTable();
            },full : function(elem) {
                var win = window.top === window.self ? window : parent.window;
                $(win).on('resize', function() {
                    var $this = $(this);
                    elem.width($this.width()).height($this.height()).css({
                        top : 0,
                        left : 0
                    });
                    elem.children('div.layui-layer-content').height($this.height() - 95);
                });
                var hs=$('#sign_up_table thead').find('div');
                for (var i = 0; i < hs.length; i++) {
                    $(hs).eq(i).css("width",($(hs).eq(i).width()<25?25:$(hs).eq(i).width()))
                    $('#sign_up_table #tempSum').find('[class*=" field-'+$(hs).eq(i).attr('class')+'"]').children().css("width",$(hs).eq(i).width())
                }
                $('#sign_up_thead').css("width",$('#sign_up_table').width());
                $('#sign_up_table tr').eq(0).append('<th rowspan="2"></th>');
                $("#sign_up_thead").append($('#sign_up_table thead'))
                $(".layui-table-body").scroll(function(event){
                    $(".layui-table-header").scrollLeft($(this).scrollLeft());
                });
            },
            success :function(){
                $('.layui-layer-btn.layui-layer-btn-').find('.layui-layer-btn1').hide();
            }
        });
    });
    // 获取所有选择的列
    $('#btnSignUpUpdate').on('click', function () {
        var checkStatus = table.checkStatus('signUpListTable');
        var data = checkStatus.data;
        if (data != null && data.length == 1) {
            var id = data[0].match_id;
            var search_news_type = $("#search_news_type").val();
            var url = '/nodejsMatch/edit.html?id=' + id + "&type=" + search_news_type;

            var opUrl = "/nodejsMatch/update";
            baseOperation.editForm(url, '内容管理', opUrl, layui, flashTable);
        } else {
            layer.alert("请选择一条操作的数据！");
        }
    });
    $('#btnSignUpDelete').on('click', function () {
        var checkStatus = table.checkStatus('signUpListTable');
        var data = checkStatus.data;
        if (data != null && data.length == 1) {
            var id = data[0].id;
            var url = '/nodejsMatch/delete/sign_up_one/' + id;
            baseOperation.deleteForm(url, layui, flashTable);
        } else {
            layer.alert("请选择一条操作的数据！");
        }

    });

    $('#btnSignUpSearch').on('click', function () {
        flashTable();
    });

    function flashTable() {
        fuzzyQuery.clearQuery();


        var search_member_name = $("#search_member_name").val();
        if (search_member_name.length > 0)
            fuzzyQuery.addLike("member_name", search_member_name);
            fuzzyQuery.addEqual('match_id',match_id)
        var fuzzyQueryJson = fuzzyQuery.getQuery();
        var table = layui.table;
        var layer = layui.layer;

        var curIndex = layer.load(2, {
            zIndex : 2000
        });
        table.reload('signUpListTable', {
            page : {
                curr : 1
            },
            where : {
                fuzzyQuery : JSON.stringify(fuzzyQueryJson)
            },
            done : function(res, curr, count){
                layer.close(curIndex);
                tableOption();

            }
        });
    }
    $('#btnPrint').on('click', function () {
        window.open('/nodejsMatch/page/registList?match_id='+match_id)
    })
    $('#btnExp').on('click', function () {
        fuzzyQuery.clearQuery();
        fuzzyQuery.addEqual('match_id',match_id)
        var fuzzyQueryJson = fuzzyQuery.getQuery();

        $.ajax({
            url:"/nodejsMatch/sign_up_list",
            type:"POST",
            data:{'page':1,'limit':tableCount,'sort':'create_time desc','fuzzyQuery':JSON.stringify(fuzzyQueryJson)},
            dataType:"json",
            success:function(data){
                debugger
                var keyMap=['member_code','member_name','pigeon_code'];
                var jsono = [{
                    "member_code":"会员编号",
                "member_name":"鸽主姓名",
                "pigeon_code":"参赛鸽环号"
                }];
                var jsont=[{
                    "member_code": "",
                    "member_name": "",
                    "pigeon_code": ""
                }]
                for (var i = 0; i < rule.length; i++) {
                    for (var j = 0; j < rule[i]['rule']['grade_money'].length; j++) {
                        jsono[0]['field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][j]]=rule[i]['name'];
                        jsont[0]['field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][j]]=rule[i]['rule']['grade_money'][j];
                        keyMap[keyMap.length]='field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][j];
                    }
                }
                var temp=data.data[0];
                data.data[0]=jsont[0];
                jsont[0]=temp;
                jsont=jsont.concat(jsono,data.data);
                downloadExl(keyMap,jsont)
            }
        });
    });

    var tmpDown; //导出的二进制对象
    function downloadExl(keyMap,json, type) {
        var tmpdata = [];//用来保存转换好的json
        json.map((v, i) => keyMap.map((k, j) => Object.assign({}, {
            v: v[k],
            position: (j > 25 ? getCharCol(j) : String.fromCharCode(65 + j)) + (i + 1)
        }))).reduce((prev, next) => prev.concat(next)).forEach((v, i) => tmpdata[v.position] = {
            v: v.v
        });
        var outputPos = Object.keys(tmpdata); //设置区域,比如表格从A1到D10
        var tmpWB = {
            SheetNames: ['mySheet'], //保存的表标题
            Sheets: {
                'mySheet': Object.assign({},
                    tmpdata, //内容
                    {
                        '!ref': outputPos[0] + ':' + outputPos[outputPos.length - 1] //设置填充区域
                    })
            }
        };
        tmpDown = new Blob([s2ab(XLSX.write(tmpWB,
            {bookType: (type == undefined ? 'xlsx':type),bookSST: false, type: 'binary'}//这里的数据是用来定义导出的格式类型
        ))], {
            type: ""
        }); //创建二进制对象写入转换好的字节流
        var href = URL.createObjectURL(tmpDown); //创建对象超链接
        document.getElementById("hf").href = href; //绑定a标签
        document.getElementById("hf").click(); //模拟点击实现下载
        setTimeout(function() { //延时释放
            URL.revokeObjectURL(tmpDown); //用URL.revokeObjectURL()来释放这个object URL
        }, 100);
    }

    function s2ab(s) { //字符串转字符流
        var buf = new ArrayBuffer(s.length);
        var view = new Uint8Array(buf);
        for (var i = 0; i != s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
        return buf;
    }
    // 将指定的自然数转换为26进制表示。映射关系：[0-25] -> [A-Z]。
    function getCharCol(n) {
        let temCol = '',
            s = '',
            m = 0
        while (n > 0) {
            m = n % 26 + 1
            s = String.fromCharCode(m + 64) + s
            n = (n - m) / 26
        }
        return s
    }

    function tableOption(){
        var ths=$('#signUpListTable').next().find('tr').eq(0).find('[data-field^="rule-"]');
        for (var i = 0; i < ths.length; i++) {
            $(ths).eq(i).find('span').parent().append('<a class="layui-btn layui-btn-xs ruleShow" data-rule="' + $(ths).eq(i).attr('data-field') + '">说明</a>')
        }
        ths=$('#signUpListTable').next().find('tr').eq(0).find('[data-field^="field-"]');
        for (var i = 0; i < ths.length; i++) {
            $(ths).eq(i).find('span').parent().append('<a class="layui-btn layui-btn-xs ruleShow" data-rule="rule-' + $(ths).eq(i).attr('data-field').split('-')[1] + '">说明</a>')
        }
        $('.ruleShow').unbind('click')
        $('.ruleShow').on('click', function () {
            var type = $(this).attr('data-rule');
            var rule = JSON.parse(tableRowData.rule);
            var result = [];
            var title = $(this).prev().text()
            for (var j = 0; j < rule.length; j++) {
                if ('rule-' + rule[j]['value'] == type) {
                    result = rule[j].rule;
                    break
                }
            }
            $.get('/nodejsMatch/rule-' + result['rule-temp'] + 'Page.html', null, function (str) {
                layer.open({
                    type: 1,
                    title: title,
                    content: str, //注意，如果str是object，那么需要字符拼接。
                    area: [30, 500],
                    success: function (layero, index) {
                        switch (parseInt(result['rule-temp'])) {
                            case 1:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                $('[name="rank"]').val(result['rank']);
                                $('[name="reward"]').val(result['reward']);
                                break;
                            case 2:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                var grades = [];
                                for (var i = 3; i < 6; i++) {
                                    $('#rule-2 tr').eq(i).find('[name="start_rank"]').val(result['grades'][i - 3]['start_rank']);
                                    $('#rule-2 tr').eq(i).find('[name="end_rank"]').val(result['grades'][i - 3]['end_rank']);
                                    for (var j = 0; j < $('#rule-2 tr').eq(i).find('[name="reward"]').length; j++) {
                                        $('#rule-2 tr').eq(i).find('[name="reward"]').eq(j).val(result['grades'][i - 3]['reward'][j]);
                                    }
                                }
                                break;
                            case 3:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                break;
                            case 4:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                $('[name="rank"]').val(result['rank']);
                                break;
                            case 5:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                for (var i = 0; i < result['ranks'].length; i++) {
                                    $('[name="rank"]').eq(i).val(result['ranks'][i]);
                                }
                                $('[name="desc"]').val(result['desc']);
                                break;
                            case 6:
                                for (var i = 0; i < result['grade_money'].length; i++) {
                                    $('[name="grade_money"]').eq(i).val(result['grade_money'][i]);
                                }
                                for (var i = 0; i < result['ranks'].length; i++) {
                                    $('[name="rank"]').eq(i).val(result['ranks'][i]);
                                }
                                $('[name="desc"]').val(result['desc']);
                                break;
                            default:
                        }
                        layero.find('input').attr('readonly', 'readonly')
                        layero.find('textarea').attr('readonly', 'readonly')
                    }
                });
            })
        })
    }
});
