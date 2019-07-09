layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	fuzzyQuery : 'fuzzyQuery'
});
layui.use([ 'element', 'table', 'form', 'fuzzyQuery' ], function() {
	var $ = layui.$;
	var fuzzyQuery = layui.fuzzyQuery;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;
	
	
	fuzzyQuery.clearQuery();
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/wappGame/list", {
		field : 'createdDatetime',
		type : 'desc'
	}, 'createdDatetime asc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
		{
			title : '序号',
			type : 'numbers',
			fixed : 'left'
		},
	{
		type : 'checkbox',
		fixed : 'left'
	},{
		field : 'name',
		width : 280,
		title : '比赛名称'
	}, {
		field : 'description',
		width : 300,
		title : '比赛说明'
	}, {
		field : 'detailsMessage',
		width : 400,
		title : '比赛详情'
	}, {
		field : 'createdDatetime',
		title : '创建时间',
		width : 200
	}, {
		fixed : 'right',
		width : 178,
		align : 'center',
		toolbar : '#toolBarTpl'
	} ] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);
		
	});

	// 监听工具条
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			var id=data.id;
			var url='/wappGame/view.html?id=' + id;
			baseOperation.viewForm(url, '比赛定义数据', layui);
		} else if (obj.event === 'del') {
			var id=data.id;
			var url='/wappGame/delete/' + id;
			baseOperation.deleteForm(url ,layui, flashTable);
		} else if (obj.event === 'edit') {
			var id=data.id;
			var url='/wappGame/edit.html?id=' + id;
			var opUrl="/wappGame/update";
			baseOperation.editForm(url, '比赛定义数据', opUrl, layui, flashTable);
		}
	});

	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].id;
			var url='/wappGame/edit.html?id=' + id;
			var opUrl="/wappGame/update";
			baseOperation.editForm(url, '比赛定义数据', opUrl, layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnDelete').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length>0) {
			var ids = [];
			for(var ii=0;ii<data.length;ii++)
				ids.push(data[ii].id);
			var url='/wappGame/delete';
			baseOperation.deleteListOperation(url,ids ,layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnView').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].id;
			var url='/wappRule/view.html?id=' + id;
			baseOperation.viewForm(url, '比赛定义数据', layui);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnAdd').on('click', function() {

		var id=-1;
		var url='/wappGame/edit.html?id=' + id;
		var opUrl="/wappGame/insert";
		baseOperation.editForm(url, '比赛定义数据', opUrl, layui, flashTable);

	});
	$('#btnSearch').on('click', function() {
		flashTable();
	});
	function flashTable() {
		fuzzyQuery.clearQuery();
		
		var search_name = $("#search_name").val();
		if (search_name.length > 0)
			fuzzyQuery.addLike("name", search_name);

	
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});