layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	fuzzyQuery : 'fuzzyQuery'
});
layui.use([ 'element', 'laydate', 'table', 'form', 'fuzzyQuery','layedit' ], function() {
	var $ = layui.$;
	var fuzzyQuery = layui.fuzzyQuery;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;
	var  laydate = layui.laydate;
	
	fuzzyQuery.clearQuery();
	
	var search_news_type = $("#search_news_type").val();
	if (search_news_type.length > 0)
		fuzzyQuery.addEqual("news_type", search_news_type);
	
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/nodejsNews/list", {
		field : 'create_time',
		type : 'desc'
	}, 'create_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	}, {
		type : 'checkbox',
		fixed : 'left'
	}, {
		field : 'cote_name',
		width : 280,
		title : '俱乐部/公棚名称'
	}, {
		field : 'news_title',
		width : 200,
		title : '标题'
	}, {
		field : 'news_status',
		width : 100,
		title : '状态'
	}, {
		field : 'news_type',
		width : 100,
		title : '类型'
	},  {
		field : 'create_time',
		title : '创建时间',
		width : 200
	}] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);
		
	});
	
	$('#btnAdd').on('click', function() {

		var id=-1;
		var search_news_type = $("#search_news_type").val();
		var url='/nodejsNews/edit.html?id=' + id+"&type="+search_news_type;
		var opUrl="/nodejsNews/insert";
		baseOperation.editForm(url, '内容管理', opUrl, layui, flashTable);

	});
	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].news_id;
			var search_news_type = $("#search_news_type").val();
			var url='/nodejsNews/edit.html?id=' + id+"&type="+search_news_type;
			
			var opUrl="/nodejsNews/update";
			baseOperation.editForm(url, '内容管理', opUrl, layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnDelete').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id= data[0].news_id;
			var url='/nodejsNews/delete/one/' + id;
			baseOperation.deleteForm(url ,layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnSearch').on('click', function() {
		flashTable();
	});

	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_news_type = $("#search_news_type").val();
		if (search_news_type.length > 0)
			fuzzyQuery.addEqual("news_type", search_news_type);
		
		var search_news_title = $("#search_news_title").val();
		if (search_news_title.length > 0)
			fuzzyQuery.addLike("news_title", search_news_title);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});