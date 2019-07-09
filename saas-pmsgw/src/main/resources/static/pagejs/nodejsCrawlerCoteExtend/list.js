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
	//selectCote();
	fuzzyQuery.clearQuery();
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/nodejsCrawlerCoteExtend/list", {
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
		width : 300,
		title : '俱乐部/公棚名称'
	}, {
		field : 'cote_web_url',
		width : 450,
		title : '俱乐部/公棚url'
	}, {
		field : 'cote_short_name',
		width : 200,
		title : '俱乐部/公棚简称'
	}, {
		field : 'sort_number',
		width : 100,
		sort: true,
		title : '排序号'
	}, {
		field : 'cote_state',
		width : 100,
		sort: true,
		title : '显示状态'
	}, {
		field : 'create_time',
		title : '创建时间',
		sort: true,
		width : 200
	}] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);		
	});
	$('#btnAdd').on('click', function() {

		var id=-1;
		var url='/nodejsCrawlerCoteExtend/edit.html?id=' + id;
		var opUrl="/nodejsCrawlerCoteExtend/insert";
		baseOperation.editForm(url, '俱乐部/公棚数据', opUrl, layui, flashTable);

	});
	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].cote_id;
			var url='/nodejsCrawlerCoteExtend/edit.html?id=' + id;
			var opUrl="/nodejsCrawlerCoteExtend/update";
			baseOperation.editForm(url, '俱乐部/公棚数据', opUrl, layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	$('#btnDelete').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id= data[0].cote_id;
			var url='/nodejsCrawlerCoteExtend/delete/one/' + id;
			baseOperation.deleteForm(url ,layui, flashTable);
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
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
					html += "<option value=\"" + result.data[i].cote_id + "\">"
							+ result.data[i].cote_name + "</option>"
				}
				debugger;
				$("#cote_id").append(html);
				layui.form.render();

			}
		});
	}
	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_cote_name = $("#search_cote_name").val();
		if (search_cote_name.length > 0)
			fuzzyQuery.addLike("cote_name", search_cote_name);
		
		
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});