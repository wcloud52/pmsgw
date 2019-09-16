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

	baseOperation.initTable("listTable", "/nodejsCrawlerCote/list", {
		field : 'create_time',
		type : 'desc'
	}, 'create_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		width : 80,
		fixed : 'left'
	}, {
		type : 'checkbox',
		fixed : 'left'
	},
	{
		field : 'cote_id',
		width : 400,
		sort: true,
		title : '公棚Id'
	}, {
		field : 'cote_name',
		width : 300,
		sort: true,
		title : '公棚名称'
	}, {
		title : '来源',
		field : 'cote_website',
		sort: true,
		width : 200
	}, {
		field : 'cote_state',
		width : 100,
		sort: true,
		title : '状态'
	}, {
		field : 'create_time',
		title : '创建时间',
		sort: true,
		width : 200
	} ] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);		
	});
	
	$('#btnSearch').on('click', function() {
		flashTable();
	});
	
	$('#btnAdd').on('click', function() {

		var id=-1;
		var url='/nodejsCrawlerCote/edit.html?id=' + id;
		var opUrl="/nodejsCrawlerCote/insert";
		baseOperation.editForm(url, '俱乐部/公棚数据', opUrl, layui, flashTable);

	});
	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].cote_id;
			var url='/nodejsCrawlerCote/edit.html?id=' + id;
			var opUrl="/nodejsCrawlerCote/update";
			baseOperation.editForm(url, '俱乐部/公棚数据', opUrl, layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnActive').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var cote_id= data[0].cote_id;
			var url='/nodejsCrawlerCote/state/' + cote_id+"/1";
			baseOperation.operationForm(url ,layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnEnable').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var cote_id= data[0].cote_id;
			var url='/nodejsCrawlerCote/state/' + cote_id+"/0";
			baseOperation.operationForm(url ,layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_cote_id = $("#search_cote_id").val();
		if (search_cote_id.length > 0)
			fuzzyQuery.addLike("cote_id", search_cote_id);

		var search_cote_name = $("#search_cote_name").val();
		if (search_cote_name.length > 0)
			fuzzyQuery.addLike("cote_name", search_cote_name);

		var search_cote_website = $("#search_cote_website").val();
		if (search_cote_website.length > 0)
			fuzzyQuery.addEqual("cote_website", search_cote_website);
		
		var search_cote_state = $("#search_cote_state").val();
		if (search_cote_state.length > 0)
			fuzzyQuery.addEqual("cote_state", search_cote_state);
		
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});