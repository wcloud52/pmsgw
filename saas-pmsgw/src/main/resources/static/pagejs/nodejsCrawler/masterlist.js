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
	
	var search_database = $("#search_database").val();
	if (search_database.length > 0)
		fuzzyQuery.addEqual("database", search_database, 'none');
	
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/nodejsCrawler/masterList", {
		field : 'create_time',
		type : 'desc'
	}, 'create_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	},{
		type : 'checkbox',
		fixed : 'left'
	} , {
		field : 'master_id',
		width : 180,
		title : '编号'
	}, {
		field : 'master_type',
		width : 100,
		title : '类型'
	}, {
		field : 'cote_name',
		width : 250,
		title : '俱乐部/公棚'
	}, {
		field : 'master_text',
		width : 300,
		title : '比赛标题'
	}, {
		field : 'master_href',
		width : 200,
		title : '比赛url'
	}, {
		field : 'master_date',
		width : 200,
		title : '比赛时间'
	}, {
		field : 'detail_crawler_total',
		width : 100,
		title : '参赛羽数'
	}, {
		field : 'create_time',
		width : 200,
		title : '创建时间'
	},  {
		fixed : 'right',
		width : 100,
		align : 'center',
		toolbar : '#toolBarTpl'
	}  ] ], layui);

	// 监听工具条
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			debugger;
			var master_id=data.master_id;
			var search_database = $("#search_database").val();
			var url='/nodejsCrawler/detailList.html?master_id=' + master_id+"&database="+search_database;
			baseOperation.viewForm(url, '比赛数据', layui);
		} 
	});
	
	$('#btnTogether').on('click', function() {
		 var layer = layui.layer;
  	 
  	  
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length > 0) {
			var ids=[];
			for(var ii=0;ii<data.length;ii++)
			{
				ids.push(data[ii].collection_id);
			}
			var strIds=ids.join(',');
			var url='/nodejsCrawler/masterEdit.html?ids=' + strIds;
			var opUrl="/nodejsCrawler/masterInsert";
			baseOperation.editForm(url, '聚合赛事管理', opUrl, layui, flashTable);
			
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnCrawlerMaster').on('click', function() {
		var key=$("#search_database").val()+".master";
		crawler(key);
	});
	
	$('#btnCrawlerDetail').on('click', function() {
		var key=$("#search_database").val()+".detail";
		crawler(key);
	});
	
	$('#btnSearch').on('click', function() {
		flashTable();
	});

	function crawler(key)
	{
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		var crawler=key;
		$.ajax({
			type : "GET",
			url : '/nodejsCrawler/crawler?crawler='+crawler,
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
	}
	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_database = $("#search_database").val();
		if (search_database.length > 0)
			fuzzyQuery.addEqual("database", search_database, 'none');
		
		var search_master_text = $("#search_master_text").val();
		if (search_master_text.length > 0)
			fuzzyQuery.addLike("master_text", search_master_text);

		var search_master_type = $("#search_master_type").val();
		if (search_master_type.length > 0)
			fuzzyQuery.addLike("master_type", search_master_type);
		
		var search_cote_name = $("#search_cote_name").val();
		if (search_cote_name.length > 0)
			fuzzyQuery.addLike("cote_name", search_cote_name);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}
});