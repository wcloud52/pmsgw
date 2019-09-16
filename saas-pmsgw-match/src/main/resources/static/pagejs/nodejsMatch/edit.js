layui.use(['upload', 'formSelects', 'table', 'laydate'], function () {
    var $ = layui.$,
        upload = layui.upload,
        formSelects = layui.formSelects,
        table = layui.table,
        laydate = layui.laydate;

    var uploadInst = upload.render({
        elem: '#btnUpload',
        url: '/storage/create/',
        accept: 'file',
        before: function(obj){
            //预读本地文件示例，不支持ie8
            obj.preview(function(index, file, result){

                var layer = layui.layer;
                var curIndex =layer.load(2, {
                    content: '加载中...',
                    zIndex : 2000
                });
                var match_id=$('#match_id').val()
                var filename=file.name;
                var reader = new FileReader();
                reader.onload = function (e) {
                    // pre-process data
                    var binary = "";
                    var bytes = new Uint8Array(e.target.result);
                    var length = bytes.byteLength;
                    for (var i = 0; i < length; i++) {
                        binary += String.fromCharCode(bytes[i]);
                    }

                    /* read workbook */
                    var wb = XLSX.read(binary, {type: 'binary'});

                    /* grab first sheet */
                    var wsname = wb.SheetNames[0];
                    var ws = wb.Sheets[wsname];

                    /* generate HTML */
                    var HTML = XLSX.utils.sheet_to_html(ws);
                    var json=XLSX.utils.sheet_to_json(ws);

                    var reqJson=[];
                    for(var ii=0;ii<json.length;ii++)
                    {
                        var hasname=json[ii].hasOwnProperty("鸽主名称");
                        if(hasname)
                        {
                            var form={};

                            var pigowner=json[ii]["鸽主名称"];
                            form["pigowner"]=pigowner;
                            form["fileName"]=filename;

                            if(json[ii].hasOwnProperty("鸽主编号"))
                            {
                                form["pigowner_num"]=json[ii]["鸽主编号"];
                            }
                            if(json[ii].hasOwnProperty("足环号"))
                            {
                                form["ringnum"]=json[ii]["足环号"];
                            }
                            form["match_id"]=match_id;
                            form["pigeon_ext"]=JSON.stringify(json[ii]);
                            reqJson.push(form);
                        }
                    }
                    $.ajax({
                        url : '/NodejsMatchPigeonCollection/insertBatch',
                        type : "post",
                        dataType : "json",
                        contentType : 'application/json',
                        data : JSON.stringify(reqJson),
                        success : function(data) {
                            if (data.code == 0) {
                                layer.msg('操作成功->成功上传'+data.data+'条');
                                layer.close(curIndex);
                            } else {
                                layer.msg(data.message);
                                layer.close(curIndex);
                            }
                        }
                    });

                };

                reader.readAsArrayBuffer(file);
            });
        }
        ,done: function(res){
            //如果上传失败
            if(res.code == 0){

            }
            else
            {
                return layer.msg('上传失败');
            }
        }
        ,error: function(){
            //演示失败状态，并实现重传
            var pImgText = $('#pImgText');
            pImgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            pImgText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            });
        }
    });
    table.render({
        elem: '#ruleTable',
        title: '指定规则',
        cellMinWidth:180,
        page:false,
        limit:20,
        data: [],
        cols: [[
            {field: 'id', title: '序号', width: 80, type: 'numbers'},
            {field: 'name', title: '名称',align: 'center'},
            {field: 'grade', title: '档次',align: 'center',width:120,templet:'<div>{{d.rule.grade_money.length}}</div>'},
            {field: 'desc', title: '说明',align: 'center',width:350},
            {fixed: 'right', title: '编辑', toolbar: '#matchEditBar',width:80}
        ]]
    });
    if(match_rule!=null&&match_rule.length>0){
        for (var i = 0; i < match_rule.length; i++) {
            layui.formSelects.value('ruleSelect', [match_rule[i]['value']], true);
        }
        table.reload('ruleTable', {
            data: match_rule
        })
    }

    //id:点击select的id
    //vals:当前select已选中的值
    //val:当前select点击的值
    //isAdd:当前操作选中or取消
    //isDisabled:当前选项是否是disabled
    formSelects.on('ruleSelect', function (id, vals, val, isAdd, isDisabled) {
        var oldData = table.cache["ruleTable"];
        if (isAdd){
            val['rule']={};
            val['grade']='';
            oldData.push(val)
        }else {
            for(var i=0;i<oldData.length;i++){
                if (val.value==oldData[i].value){
                    oldData.splice(i,1);
                    break;
                }
            }
        }
        table.reload('ruleTable', {
            data: oldData
        });
    }, true);



    var ruelType={
        1:{'rule-temp':1,'name':'一把抓'},
        2:{'rule-temp':2,'name':'普指'},
        3:{'rule-temp':3,'name':'11取1'},
        4:{'rule-temp':3,'name':'22取1'},
        5:{'rule-temp':3,'name':'33取1'},
        6:{'rule-temp':3,'name':'55取1'},
        7:{'rule-temp':3,'name':'110取1'},
        8:{'rule-temp':5,'name':'手机'},
        9:{'rule-temp':6,'name':'汽车'},
        10:{'rule-temp':1,'name':'大家乐'},
        11:{'rule-temp':4,'name':'幸运'},
    };
    table.on('tool(ruleTableTest)', function (obj) {
        var data = obj.data;
        console.log(data)
        if (obj.event =='edit') {
            $.get('/nodejsMatch/rule-'+ruelType[data.value]['rule-temp']+'Page.html', null, function(str){
                layer.open({
                    type: 1,
                    title:data.name,
                    content: str, //注意，如果str是object，那么需要字符拼接。
                    area:[30,500],
                    success: function(layero, index){
                        var result=data['rule'];
                        switch (result['rule-temp']) {
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
                                    $('#rule-2 tr').eq(i).find('[name="start_rank"]').val(result['grades'][i-3]['start_rank']);
                                    $('#rule-2 tr').eq(i).find('[name="end_rank"]').val(result['grades'][i-3]['end_rank']);
                                  for (var j = 0; j < $('#rule-2 tr').eq(i).find('[name="reward"]').length; j++) {
                                        $('#rule-2 tr').eq(i).find('[name="reward"]').eq(j).val(result['grades'][i-3]['reward'][j]);
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
                    },
                    cancel: function(index, layero){
                        var result={
                            'name':data.name,
                            'code':data.value,
                            'rule-temp':ruelType[data.value]['rule-temp'],
                            'grade_money':[],
                            'rank':0,
                            'ranks':[],
                            'reward':0
                        };
                        switch (ruelType[data.value]['rule-temp']) {
                            case 1:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                result['rank'] = $('[name="rank"]').val();
                                result['reward'] = $('[name="reward"]').val();
                                break;
                            case 2:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                var grades = [];
                                for (var i = 3; i < 6; i++) {
                                    var grade = {};
                                    grade = {
                                        'start_rank': $('#rule-2 tr').eq(i).find('[name="start_rank"]').val(),
                                        'end_rank': $('#rule-2 tr').eq(i).find('[name="end_rank"]').val(),
                                        'reward': []
                                    };
                                    for (var j = 0; j < $('#rule-2 tr').eq(i).find('[name="reward"]').length; j++) {
                                        var val = $('#rule-2 tr').eq(i).find('[name="reward"]').eq(j).val();
                                        if (val != undefined && val != '') {
                                            grade['reward'].push(val);
                                        }
                                    }
                                    grades.push(grade);
                                }
                                result['grades'] = grades;
                                break;
                            case 3:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                break;
                            case 4:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                result['rank'] = $('[name="rank"]').val();
                                break;
                            case 5:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                for (var i = 0; i < $('[name="rank"]').length; i++) {
                                    var val = $('[name="rank"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['ranks'].push(val);
                                    }
                                }
                                result['desc'] = $('[name="desc"]').val();
                                break;
                            case 6:
                                for (var i = 0; i < $('[name="grade_money"]').length; i++) {
                                    var val = $('[name="grade_money"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['grade_money'].push(val);
                                    }
                                }
                                for (var i = 0; i < $('[name="rank"]').length; i++) {
                                    var val = $('[name="rank"]').eq(i).val();
                                    if (val != undefined && val != '') {
                                        result['ranks'].push(val);
                                    }
                                }
                                result['desc'] = $('[name="desc"]').val();
                                break;
                            default:

                        }
                        obj.update({
                            rule: result,
                            grade:result['grade_money'].length
                        });
                        layer.close(index)
                    }
                });
                /*layer.open({
                    type: 1,
                    content :str
                }, function (value, index) {
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });*/
            });




            console.log(obj.data)

        }
    });

    $('#rule-submit').on('click',function () {
        var data=table.cache["ruleTable"]
        for (var i = 0; i < data.length; i++) {
            delete data[i]['LAY_TABLE_INDEX']
        }
        $('[name="rule"]').val(JSON.stringify(data));
    })
})

