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
        }else {
            one.push({
                field:'rule-'+rule[i]['value'],
                align: 'center',
                title: rule[i]['name']
            });
        }

        for (var j = 0; j < rule[i]['rule']['grade_money'].length; j++) {
            two.push({
                field: 'field-'+rule[i]['value']+'-'+rule[i]['rule']['grade_money'][j],
                width: 80,
                align: 'center',
                title: rule[i]['rule']['grade_money'][j]
            })
        }
    }
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
                $('.layui-table-body').find('[data-field^="rule"]').remove()
                var ths=$('#signUpListTable').next().find('tr').eq(0).find('[data-field^="rule-"]');
                for (var i = 0; i < ths.length; i++) {
                    $(ths).eq(i).find('span').parent().append('<a class="layui-btn layui-btn-xs ruleShow" data-rule="' + $(ths).eq(i).attr('data-field') + '">说明</a>')
                }
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
                for (var i=2;i<trs.length;i++){
                    var regist={};
                    var checkboxs=$(trs).eq(i).find('[type="checkbox"]:checked');
                    for (var j = 0; j < checkboxs.length; j++) {
                        var key=$(checkboxs).eq(j).attr('name');
                        var val=$(checkboxs).eq(j).val();
                        regist[key]=val;
                    }
                    if ( Object.keys(regist).length>0) {
                        $(trs).eq(i).find('[name="regist"]').val(JSON.stringify(regist));
                        var row = {
                            'match_id': $(trs).eq(i).find('[name="match_id"]').val(),
                            'regist': $(trs).eq(i).find('[name="regist"]').val(),
                            'member_code': $(trs).eq(i).find('[name="member_code"]').val(),
                            'member_name': $(trs).eq(i).find('[name="member_name"]').val(),
                            'pigeon_code': $(trs).eq(i).find('[name="pigeon_code"]').val(),
                        }
                        result.push(row);
                    }
                }
                $.ajax({
                    type : "POST",
                    url :  "/nodejsMatch/sign_up_insert",
                    contentType : 'application/json',
                    data : JSON.stringify(result),
                    success : function(data) {
                        if (data.code == 0) {
                            layer.msg('操作成功',{icon:1});
                            layer.close(addBoxIndex);
                            //flashTable();
                        } else {
                            layer.msg('保存失败',{icon:5});
                            layer.close(addBoxIndex);
                        }
                    }
                });
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
                $('.layui-table-body').find('[data-field^="rule"]').remove()
                var ths=$('#signUpListTable').next().find('tr').eq(0).find('[data-field^="rule-"]');
                for (var i = 0; i < ths.length; i++) {
                    $(ths).eq(i).find('span').parent().append('<a class="layui-btn layui-btn-xs ruleShow" data-rule="' + $(ths).eq(i).attr('data-field') + '">说明</a>')
                }
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
    }

});
