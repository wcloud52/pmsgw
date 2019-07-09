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
		fuzzyQuery.addEqual("masterId", currentMasterId);
	
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listDetailTable", "/pmsgwgame/detailList", {
		field : 'create_time',
		type : 'desc'
	}, 'rank asc,create_time asc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '名次',
		type : 'numbers',
		fixed : 'left'
	}, {
		field : 'pigowner',
		width : 100,
		title : '鸽主'
	}, {
		field : 'cotenum',
		width : 100,
		title : '棚号'
	}, {
		field : 'mm',
		width : 100,
		title : '密码'
	}, {
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

	// 监听工具条
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			var id=data.id;
			var url='/pmsgwgame/detailList.html?id=' + id;
			baseOperation.viewForm(url, '比赛数据', layui);
		} 
	});
	// 获取所有选择的列
	$('#btnSendGameMessage').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var checkStatus = table.checkStatus('listDetailTable');
		var data = checkStatus.data;
		if (data != null && data.length>0) {
			var openidList = [];
			for(var ii=0;ii<data.length;ii++)
				openidList.push(data[ii].id);

		
			var curIndex = layer.load(2, {
				zIndex : 2000
			});
			$.ajax({
				 type: "POST",
		            url: '/weixinuser/customer/game/sendmessage',
		            data: JSON.stringify(openidList),
		            contentType: 'application/json',
		            dataType: 'json',
				success : function(data) {
					if (data.code == 0) {
						layerTips.msg('操作成功');
						//flashTable();
						layer.close(curIndex);
					} else {
						layerTips.msg(data.message);
						layer.close(curIndex);
					}
				}
			});

		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnSendResultMessage').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length>0) {
			var openidList = [];
			for(var ii=0;ii<data.length;ii++)
				openidList.push(data[ii].id);

		
			var curIndex = layer.load(2, {
				zIndex : 2000
			});
			$.ajax({
				 type: "POST",
		            url: '/weixinuser/customer/result/sendmessage',
		            data: JSON.stringify(openidList),
		            contentType: 'application/json',
		            dataType: 'json',
				success : function(data) {
					if (data.code == 0) {
						layerTips.msg('操作成功');
						//flashTable();
						layer.close(curIndex);
					} else {
						layerTips.msg(data.message);
						layer.close(curIndex);
					}
				}
			});

		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnSynchronize').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		$.ajax({
			type : "GET",
			url : '/weixinuser/synchronize',
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

		var currentMasterId = $("#currentMasterId").val();
		if (currentMasterId.length > 0)
			fuzzyQuery.addEqual("masterId", currentMasterId);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listDetailTable', layui);
	}
});