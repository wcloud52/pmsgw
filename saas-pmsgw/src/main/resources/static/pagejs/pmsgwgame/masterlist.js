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
	
	var search_website = $("#search_website").val();
	if (search_website.length > 0)
		fuzzyQuery.addEqual("website", search_website);
	
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/pmsgwgame/masterList", {
		field : 'create_time',
		type : 'desc'
	}, 'create_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	}, {
		field : 'id',
		width : 180,
		title : 'id'
	}, {
		field : 'main_type',
		width : 100,
		title : '类型'
	}, {
		field : 'main_text',
		width : 300,
		title : '比赛标题'
	}, {
		field : 'main_href',
		width : 200,
		title : '比赛url'
	}, {
		field : 'main_date',
		width : 200,
		title : '比赛时间'
	}, {
		field : 'title',
		width : 200,
		title : '比赛名称'
	}, {
		field : 'data_time',
		width : 200,
		title : '司放日期'
	}, {
		field : 'data_address',
		width : 200,
		title : '司放地点'
	}, {
		field : 'data_fengxiang',
		width : 200,
		title : '风向'
	}, {
		field : 'data_fengli',
		width : 200,
		title : '风力'
	}, {
		field : 'tq',
		width : 200,
		title : '天气'
	}, {
		field : 'jd',
		width : 200,
		title : '司放地经度'
	}, {
		field : 'wd',
		width : 200,
		title : '司放地纬度'
	}, {
		field : 'data_num',
		width : 200,
		title : '参赛羽数'
	}, {
		fixed : 'right',
		width : 100,
		align : 'center',
		toolbar : '#toolBarTpl'
	}  ] ], layui);

	// 监听工具条
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			var id=data.id;
			var url='/pmsgwgame/detailList.html?id=' + id;
			baseOperation.viewForm(url, '比赛数据', layui);
		} 
	});
	
	$('#btnCrawler').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		$.ajax({
			type : "GET",
			url : '/pmsgwgame/crawler?website='+$("#search_website").val(),
			contentType : 'application/json',
			success : function(data) {
				if (data.code == 0) {
					layerTips.msg('操作成功');
					flashTable();
					layer.close(curIndex);
				} else {
					layerTips.msg(data.message);
					layer.close(curIndex);
				}
			}
		});

	});
	$('#btnSearch').on('click', function() {
		flashTable();
	});

	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_website = $("#search_website").val();
		if (search_website.length > 0)
			fuzzyQuery.addEqual("website", search_website);
		
		var search_main_text = $("#search_main_text").val();
		if (search_main_text.length > 0)
			fuzzyQuery.addLike("main_text", search_main_text);

		var search_main_type = $("#search_main_type").val();
		if (search_main_type.length > 0)
			fuzzyQuery.addLike("main_type", search_main_type);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}
});