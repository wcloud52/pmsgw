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

	baseOperation.initTable("listTable", "/weixinuserpurse/list", {
		field : 'bind_time',
		type : 'desc'
	}, 'bind_time desc', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	}, {
		field : 'headimgurl',
		width : 100,
		title : '微信头像',
		toolbar : '#headimgurlBarTpl',
		align:'center'
	}, {
		field : 'nickname',
		width : 150,
		title : '微信昵称',
			align:'center'
	},  {
		field : 'pigowner',
		width : 150,
		title : '鸽主名称',
			align:'center'
	}, {
		field : 'bind_name',
		title : '绑定名称',
		width : 150,
			align:'center'
	}, {
		field : 'bind_tel',
		title : '绑定手机',
		width : 150,
			align:'center'
	}, {
		field : 'state',
		width : 90,
		title : '关注状态',
		toolbar:'#stateTpl',
			align:'center'
	}, {
		field : 'money',
		width : 100,
		title : '余额',
		align:'center',
	}, {
		width : 100,
		title : '操作',
		align:'center',
			toolbar:'#barDemo'
	}  ] ], layui);

	table.on('tool(test)', function (obj) {
		var data = obj.data, layEvent = obj.event;
		var tr = obj.tr;
		if (layEvent === 'deposit') {
			layer.prompt({
				title:'请输入充值金额',
				formType: 0
			}, function(value, index, elem){
				var regPos = /^\d+(\.\d+)?$/; //非负浮点数
				var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
				if(regPos.test(value) || regNeg.test(value)){
					data.money=value;
					debugger
					$.ajax({
						type: "POST",
						url: '/weixinuserpurse/update',
						dataType:'json',
						data:data,
						success : function(data) {
							if (data.data.code != 0) {
								layerTips.msg('充值成功');
								var temp={}
								temp['money']=data.data.money;
								obj.update(temp);
							} else {
								layerTips.msg("充值失败，请联系管理员");
							}
						}
					});
					layer.close(index);
					return true;
				}else{
					layer.msg('充值金额错误!',{icon:5})
					return false;
				}
			});
		}
	});
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
	            url: '/weixinuserpurse/customer/result/autosendmessage',
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
			var url='/weixinuserpurse/edit.html?id=' + id;
			var opUrl="/weixinuserpurse/update";
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
			fuzzyQuery.addLike("a.bind_name", search_bind_name);

		var search_bind_tel = $("#search_bind_tel").val();
		if (search_bind_tel.length > 0)
			fuzzyQuery.addLike("a.bind_tel", search_bind_tel);

		var search_pigowner = $("#search_pigowner").val();
		if (search_pigowner.length > 0)
			fuzzyQuery.addLike("a.pigowner", search_pigowner);

		var search_bind_tel = $("#search_bind_tel").val();
		if (search_bind_tel.length > 0)
			fuzzyQuery.addLike("a.bind_tel", search_bind_tel);

		var search_state = $("#search_state").val();
		if (search_state.length > 0)
			fuzzyQuery.addEqual("a.state", search_state);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});