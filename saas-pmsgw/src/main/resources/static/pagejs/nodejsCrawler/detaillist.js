layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	//fuzzyQuery : 'fuzzyQuery'
});
layui.use([ 'element', 'table', 'form', 'fuzzyQuery' ], function() {
	var $ = layui.$;
	var fuzzyQuery = layui.fuzzyQuery;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;

	fuzzyQuery.clearQuery();
	
	var currentMasterId = $("#currentMasterId").val();
	if (currentMasterId.length > 0)
		fuzzyQuery.addEqual("master_id", currentMasterId);
	
	var search_database = $("#search_database").val();
	if (search_database.length > 0)
		fuzzyQuery.addEqual("database", search_database, 'none');
	
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listDetailTable", "/nodejsCrawler/detailList", {
		field : 'create_time',
		type : 'desc'
	}, 'rank asc,create_time asc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '名次',
		field : 'rank',
		width : 80,
		fixed : 'left'
	},  {
		field : 'detail_id',
		width : 200,
		title : '编号'
	},{
		field : 'pigowner',
		width : 200,
		title : '鸽主'
	}, {
		field : 'cotenum',
		width : 100,
		title : '棚号'
	},{
		field : 'ringnum',
		width : 200,
		title : '足环号'
	}, {
		field : 'cometime',
		width : 200,
		title : '归巢时间'
	}, {
		field : 'distence',
		width : 200,
		title : '空距'
	}, {
		field : 'speed',
		width : 200,
		title : '分速'
	}  ] ], layui);

	
});