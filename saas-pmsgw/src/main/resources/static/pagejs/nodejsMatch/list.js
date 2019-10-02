layui.config({
    base: '../js/',
    version: new Date().getTime()
}).extend({ // 模块别名
    fuzzyQuery: 'fuzzyQuery',
    formSelects: 'formSelects-v4',
    yutons_sug : 'yutons_sug'
});
var tableRowData,match_id;
layui.use(['element', 'laydate', 'table', 'form', 'fuzzyQuery', 'layedit'], function () {
    var $ = layui.$;
    var fuzzyQuery = layui.fuzzyQuery;
    var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
    var layer = layui.layer;// 获取当前窗口的layer对象
    var table = layui.table;
    var laydate = layui.laydate;
    fuzzyQuery.clearQuery();

    var fuzzyQueryJson = fuzzyQuery.getQuery();
    Date.prototype.Format = function(fmt){
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
    baseOperation.initTable("listTable", "/nodejsMatch/list", {
            field: 'create_time',
            type: 'desc'
        },
        'create_time desc',
        JSON.stringify(fuzzyQueryJson),
        [[ // 表头
            {
                title: '序号',
                type: 'numbers',
                fixed: 'left'
            }, {
                type: 'checkbox',
                fixed: 'left'
            }, {
                field: 'cote_name',
                width: 280,
                title: '俱乐部/公棚名称'
            }, {
                field: 'match_title',
                width: 200,
                title: '标题'
            }, {
                field: 'match_status',
                width: 100,
                title: '状态'
            }, {
                field: 'match_desc',
                width: 100,
                title: '描述'
            }, {
                field: 'create_time',
                title: '创建时间',
                width: 200
            }, {
                fixed: 'right',
                title: '报名',
                templet: '#match_sign_up',
                width: 180
            }]], layui);

    table.on('sort(test)', function (obj) {
        baseOperation.sortTable(obj, "listTable", layui);

    });
    table.on('tool(test)', function (obj) {
        var data = obj.data;
        match_id=data.match_id;
        console.log(data)
        if (obj.event =='sign_up') {
            var url = '/nodejsMatch/sign_up_listPage.html' ;
            tableRowData=data;
            baseOperation.viewForm(url, '报名管理', layui);
        }else if (obj.event =='match_start') {
            var url = '/nodejsMatch/update' ;
            var time2 = (new Date()).Format("yyyy-MM-dd") ;
            $.ajax({
                url : url,
                type : "post",
                dataType : "json",
                contentType : 'application/json',
                data : JSON.stringify({match_id:data.match_id,match_status:2,start_time:time2}),
                success : function(data) {
                    if (data.code == 0) {
                        layer.msg('比赛开始',{icon:1});
                        $(obj.tr).find('[lay-event="sign_up"]').attr('lay-event','show_sing_up').text('报名');
                        $(obj.tr).find('[lay-event="match_start"]').attr('lay-event','match_end').text('结束');
                    } else {
                        layer.msg(data.message);
                    }
                }
            });
        }else if (obj.event =='match_end') {
            var url = '/nodejsMatch/update' ;
            var time2 = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
            $.ajax({
                url : url,
                type : "post",
                dataType : "json",
                contentType : 'application/json',
                data : JSON.stringify({match_id:data.match_id,match_status:3,end_time:time2}),
                success : function(data) {
                    if (data.code == 0) {
                        layer.msg('比赛结束',{icon:1});
                        $(obj.tr).find('[lay-event="sign_up"]').attr('lay-event','show_sing_up').text('报名');
                        $(obj.tr).find('[lay-event="match_end"]').attr('lay-event','show_result').text('结果');
                    } else {
                        layer.msg(data.message);
                    }
                }
            });
            debugger
            $.ajax({
                url : "/nodejsMatch/updateRegistRank",
                type : "post",
                dataType : "json",
                contentType : 'application/json',
                async : true,
                data : JSON.stringify({match_id:data.match_id})
            });
        }else if(obj.event=='show_result'){
            window.open('/nodejsMatch/result?match_id='+match_id);
        }
    });
    $('#btnAdd').on('click', function () {
        var id = -1;
        var search_news_type = $("#search_news_type").val();
        var url = '/nodejsMatch/edit.html?id=' + id + "&type=" + search_news_type;
        var opUrl = "/nodejsMatch/insert";
        baseOperation.editForm(url, '内容管理', opUrl, layui, flashTable);

    });
    // 获取所有选择的列
    $('#btnUpdate').on('click', function () {
        var checkStatus = table.checkStatus('listTable');
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
    $('#btnDelete').on('click', function () {
        var checkStatus = table.checkStatus('listTable');
        var data = checkStatus.data;
        if (data != null && data.length == 1) {
            var id = data[0].match_id;
            var url = '/nodejsMatch/delete/one/' + id;
            baseOperation.deleteForm(url, layui, flashTable);
        } else {
            layer.alert("请选择一条操作的数据！");
        }

    });

    $('#btnSearch').on('click', function () {
        flashTable();
    });

    function flashTable() {
        fuzzyQuery.clearQuery();


        var search_match_title = $("#search_match_title").val();
        if (search_match_title.length > 0)
            fuzzyQuery.addLike("match_title", search_match_title);

        var fuzzyQueryJson = fuzzyQuery.getQuery();
        baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
    }



});