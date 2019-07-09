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
	
	  laydate.render({
	    elem: '#search_bind_startTime'
	  });
	  
	  laydate.render({
		    elem: '#search_bind_endTime'
		  });

	  laydate.render({
		    elem: '#search_club_bind_startTime'
		  });
		  
		  laydate.render({
			    elem: '#search_club_bind_endTime'
			  });
		  
		  laydate.render({
			    elem: '#search_create_time_startTime'
			  });
			  
			  laydate.render({
				    elem: '#search_create_time_endTime'
				  });
	
	fuzzyQuery.clearQuery();
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/weixinuser/list", {
		field : 'create_time',
		type : 'asc'
	}, 'create_time asc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
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
		field : 'subscribe',
		width : 100,
		title : '关注状态'
	}, {
		field : 'create_time',
		title : '关注创建时间',
		width : 200
	}, {
		field : 'bind_type',
		title : '绑定类型',
		width : 200
	}, {
		field : 'bind_tel',
		title : '公棚绑定手机',
		width : 200
	}, {
		field : 'bind_name',
		title : '公棚绑定鸽主',
		width : 200
	}, {
		field : 'bind_address',
		title : '公棚绑定地区',
		width : 200
	},{
		field : 'bind_time',
		title : '公棚绑定时间',
		width : 200
	}, {
		field : 'club_bind_tel',
		title : '俱乐部协会绑定手机',
		width : 200
	}, {
		field : 'club_bind_name',
		title : '俱乐部协会绑定鸽主',
		width : 200
	}, {
		field : 'club_bind_loft',
		title : '俱乐部协会绑定鸽棚',
		width : 200
	}, {
		field : 'club_bind_address',
		title : '俱乐部协会绑定地区',
		width : 200
	},{
		field : 'club_bind_time',
		title : '俱乐部时间',
		width : 200
	} ] ], layui);

	
	$('#btnSendResultMessage').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		$.ajax({
			 type: "POST",
	            url: '/weixinuser/customer/result/autosendmessage',
			success : function(data) {
				if (data.code == 0) {
					layerTips.msg('操作成功');
					layer.close(curIndex);
				} else {
					layerTips.msg(data.message);
					layer.close(curIndex);
				}
			}
		});

	});
	// 获取所有选择的列
	$('#btnUpdate').on('click', function() {
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length == 1) {
			var id=data[0].id;
			var url='/weixinuser/edit.html?id=' + id;
			var opUrl="/weixinuser/update";
			baseOperation.editForm(url, '用户数据', opUrl, layui, flashTable);
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

		var search_bind_name = $("#search_bind_name").val();
		if (search_bind_name.length > 0)
			fuzzyQuery.addLike("bind_name", search_bind_name);

		var search_bind_tel = $("#search_bind_tel").val();
		if (search_bind_tel.length > 0)
			fuzzyQuery.addLike("bind_tel", search_bind_tel);
		
		var search_bind_startTime = $("#search_bind_startTime").val();
		
		var search_bind_endTime = $("#search_bind_endTime").val();
		
		var bindTime=[];
		bindTime.push(search_bind_startTime);
		bindTime.push(search_bind_endTime);
		
		if(search_bind_startTime.length>0&&search_bind_endTime.length>0)
		fuzzyQuery.addBetween("DATE_FORMAT(bind_time,'%Y-%m-%d')", bindTime);
		
		
		var search_club_bind_name = $("#search_club_bind_name").val();
		if (search_club_bind_name.length > 0)
			fuzzyQuery.addLike("club_bind_name", search_club_bind_name);

		var search_club_bind_tel = $("#search_club_bind_tel").val();
		if (search_club_bind_tel.length > 0)
			fuzzyQuery.addLike("club_bind_tel", search_club_bind_tel);
		
		var search_club_bind_startTime = $("#search_club_bind_startTime").val();		
		var search_club_bind_endTime = $("#search_club_bind_endTime").val();	
		var club_bindTime=[];
		club_bindTime.push(search_club_bind_startTime);
		club_bindTime.push(search_club_bind_endTime);	
		if(search_club_bind_startTime.length>0&&search_club_bind_endTime.length>0)
		fuzzyQuery.addBetween("DATE_FORMAT(club_bind_time,'%Y-%m-%d')", club_bindTime);
		
        var search_create_time_startTime = $("#search_create_time_startTime").val();		
		var search_create_time_endTime = $("#search_create_time_endTime").val();
		var create_time=[];
		create_time.push(search_create_time_startTime);
		create_time.push(search_create_time_endTime);
		
		if(search_create_time_startTime.length>0&&search_create_time_endTime.length>0)
		fuzzyQuery.addBetween("DATE_FORMAT(create_time,'%Y-%m-%d')", create_time);
		
		var search_openid = $("#search_openid").val();
		if (search_openid.length > 0)
			fuzzyQuery.addLike("openid", search_openid);

		var search_subscribe = $("#search_subscribe").val();
		if (search_subscribe.length > 0)
			fuzzyQuery.addEqual("subscribe", search_subscribe);
		
		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});