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
	baseOperation.initTable("listTable", "/nodejsWeixinUserCote/list", {
		field : 'cote_name',
		type : 'asc'
	}, 'cote_name asc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	}, {
		type : 'checkbox',
		fixed : 'left'
	}, {
		field : 'openid',
		width : 280,
		title : '用户openid'
	}, {
		field : 'nickname',
		width : 200,
		title : '用户昵称'
	}, {
		field : 'headimgurl',
		width : 100,
		title : '用户头像',
		
		toolbar : '#headimgurlBarTpl'
	}, {
		field : 'cote_name',
		width : 200,
		title : '公棚'
	} ] ], layui);

	$('#btnSearch').on('click', function() {
		flashTable();
	});
	// 获取所有选择的列
	$('#btnSendMessage').on(
			'click',
			function() {
				var checkStatus = table.checkStatus('listTable');
				var data = checkStatus.data;
				if (data != null && data.length > 0) {
					var jsonData = data;
					var url = '/nodejsWeixinUserCote/edit.html';
					var opUrl = "/nodejsWeixinUserCote/sendMessage";
					
					baseOperation.openOperationForm(url, '发送信息', opUrl, layui,
							jsonData, flashTable);
				} else {
					layer.alert("请选择操作的数据！");
				}

			});
	function selectCote() {
		$.ajax({
			url : "/nodejsCrawlerCote/query/list",
			type : "get",
			dataType : "json",
			success : function(result) {
				var html = "";
				for (var i = 0; i < result.data.length; i++) {
					html += "<option value=\"" + result.data[i].cote_id + "\">"
							+ result.data[i].cote_name + "</option>"
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

		var search_openid = $("#search_openid").val();
		if (search_openid.length > 0)
			fuzzyQuery.addLike("openid", search_openid);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable',
				layui);
	}

});