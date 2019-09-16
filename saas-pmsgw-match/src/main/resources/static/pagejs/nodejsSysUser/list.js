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

	baseOperation.initTable("listTable", "/nodejsSysUser/list", {
		field : 'createdDatetime',
		type : 'desc'
	}, 'createdDatetime desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
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
		field : 'loginName',
		width : 200,
		title : '登录账户'
	}, {
		field : 'userName',
		width : 100,
		title : '用户名称'
	}, {
		field : 'userType',
		width : 100,
		title : '用户类型'
	},  {
		field : 'createdDatetime',
		title : '创建时间',
		width : 200
	}] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);
		
	});
	
	$('#btnAdd').on('click', function() {

		var id=-1;
		var url='/nodejsSysUser/edit.html?id=' + id;
		var opUrl="/nodejsSysUser/insert";
		baseOperation.editForm(url, '平台用户数据', opUrl, layui, flashTable);

	});
	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].id;
			var url='/nodejsSysUser/edit.html?id=' + id;
			var opUrl="/nodejsSysUser/update";
			baseOperation.editForm(url, '平台用户数据', opUrl, layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnDelete').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id= data[0].id;
			var url='/nodejsSysUser/delete/one/' + id;
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

		var search_cote_name = $("#search_cote_name").val();
		if (search_cote_name.length > 0)
			fuzzyQuery.addLike("cote_name", search_cote_name);

		var search_loginName = $("#search_loginName").val();
		if (search_loginName.length > 0)
			fuzzyQuery.addLike("loginName", search_loginName);
		
		
		var search_userName = $("#search_userName").val();
		if (search_userName.length > 0)
			fuzzyQuery.addLike("userName", search_userName);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});