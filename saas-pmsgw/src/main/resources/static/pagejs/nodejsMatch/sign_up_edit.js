layui.use(['upload', 'formSelects', 'table', 'laydate'], function () {
    var $ = layui.$,
        upload = layui.upload,
        formSelects = layui.formSelects,
        table = layui.table,
        laydate = layui.laydate;


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
        1:{'rule-temp':1},
        2:{'rule-temp':2},
        3:{'rule-temp':3},
        4:{'rule-temp':3},
        5:{'rule-temp':3},
        6:{'rule-temp':3},
        7:{'rule-temp':3},
        8:{'rule-temp':5},
        9:{'rule-temp':6},
        10:{'rule-temp':1},
        11:{'rule-temp':4},
    };
    table.on('tool(test)', function (obj) {
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
                        debugger
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

