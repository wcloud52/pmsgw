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

	baseOperation.initTable("listTable", "/nodejsCustomerTextMessage/list", {
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
		field : 'message_create_time',
		title : '消息创建时间',
		width : 200
	}, {
		field : 'message_text',
		title : '消息主体',
		width : 200
	} ] ], layui);

	$('#btnSearch').on('click', function() {
		flashTable();
	});

	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_message_text = $("#search_message_text").val();
		if (search_message_text.length > 0)
			fuzzyQuery.addLike("message_text", search_message_text);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});