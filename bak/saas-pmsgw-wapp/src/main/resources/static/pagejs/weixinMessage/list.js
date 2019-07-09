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
	var  laydate = layui.laydate;
	
	fuzzyQuery.clearQuery();
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/weixinMessage/list", {
		field : 'CreatedDatetime',
		type : 'desc'
	}, 'CreatedDatetime desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	},  {
		field : 'messageId',
		width : 100,
		title : 'messageId'
	}, {
		field : 'receiverName',
		width : 200,
		title : '用户receiverName'
	}, {
		field : 'messageTextId',
		width : 100,
		title : 'messageTextId'
	}, {
		field : 'messageTitle',
		width : 300,
		title : 'messageTitle'
	}, {
		field : 'messageText',
		title : 'messageText',
		width : 200
	}, {
		field : 'createdDatetime',
		title : 'createdDatetime',
		width : 200
	}] ], layui);

	$('#btnSearch').on('click', function() {
		flashTable();
	});
	
	function flashTable() {
		fuzzyQuery.clearQuery();

		
		var search_receiverName = $("#search_receiverName").val();
		if (search_receiverName.length > 0)
			fuzzyQuery.addLike("receiverName", search_receiverName);
		

		var search_messageId = $("#search_messageId").val();
		if (search_messageId.length > 0)
			fuzzyQuery.addEqual("messageId", search_messageId);
		

		var search_messageTitle = $("#search_messageTitle").val();
		if (search_messageTitle.length > 0)
			fuzzyQuery.addLike("messageTitle", search_messageTitle);
		
		
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});