layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	fuzzyQuery : 'fuzzyQuery'
});
layui.use([ 'element', 'laydate', 'table', 'form', 'fuzzyQuery' ], function() {
	var $ = layui.$;
	var fuzzyQuery = layui.fuzzyQuery;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;
	var laydate = layui.laydate;
	
	fuzzyQuery.clearQuery();
	var fuzzyQueryJson = fuzzyQuery.getQuery();
	selectCote();
	baseOperation.initTable("listTable", "/nodejsCustomerGameMessage/list", {
		field : 'message_create_time',
		type : 'desc'
	}, 'message_create_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		width : 80,
		fixed : 'left'
	},
	{
		field : 'message_receiverId',
		width : 300,
		title : '消息接收openId'
	}, {
		field : 'message_receiverName',
		width : 200,
		title : '消息接收名称'
	}, {
		field : 'game_master_text',
		width : 300,
		title : '比赛标题'
	}, {
		field : 'game_cote_name',
		width : 200,
		title : '公棚'
	}, {
		field : 'game_master_website',
		width : 150,
		title : '来源'
	}, {
		field : 'game_create_time',
		title : '爬取时间',
		width : 200
	}, {
		field : 'message_create_time',
		title : '消息创建时间',
		width : 200
	}, {
		field : 'title',
		title : '延时',
		width : 200,
		templet : function(d) {
			var m1 = moment(d.game_create_time);
		    var m2 = moment(d.message_create_time);
		    var du = //moment.duration(m1.diff(m2)).format('HH时mm分ss秒');
		    	moment.tz(m2 - m1, "Africa/Abidjan").format('HH时mm分ss秒');
			return du;
		}
	}, {
		field : 'message_text',
		title : '消息主体',
		width : 200
	} ] ], layui);

	$('#btnSearch').on('click', function() {
		flashTable();
	});
	function selectCote() {
		$.ajax({
			url : "/nodejsCrawlerCote/query/list",
			type : "get",
			dataType : "json",
			success : function(result) {
				var html = "";
				for (var i = 0; i < result.data.length; i++) {
					html += "<option value=\"" + result.data[i].cote_id + "\">" + result.data[i].cote_name + "</option>"
				}
				$("#search_game_cote").append(html);
				layui.form.render();

			}
		});
	}

	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_game_cote = $("#search_game_cote").val();
		if (search_game_cote.length > 0)
			fuzzyQuery.addEqual("cote_id", search_game_cote);


		var search_game_master_text = $("#search_game_master_text").val();
		if (search_game_master_text.length > 0)
			fuzzyQuery.addLike("game_master_text", search_game_master_text);

		var search_game_master_website = $("#search_game_master_website").val();
		if (search_game_master_website.length > 0)
			fuzzyQuery.addLike("game_master_website", search_game_master_website);
		
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});