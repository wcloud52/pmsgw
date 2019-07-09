var baseOperation = {
	initTable : function(tableId, queryOperationUrl, initSort, sort, fuzzyQuery, cols, layui) {
		var table = layui.table;
		table.render({
			elem : "#" + tableId,
			height : 490,
			url : queryOperationUrl, // 数据接口
			page : true, // 开启分页
			//limit:2,
			limits:[10,20,50,100,200,500,1000,2000,5000,10000],
			initSort : initSort,
			cols : cols,
			where : {
				sort : sort,
				fuzzyQuery : fuzzyQuery
			},
			method : 'post',
			contentType : 'application/json'
		});
	},

	flashTable : function(fuzzyQuery, tableId, layui) {
		var table = layui.table;
		var layer = layui.layer;

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		table.reload(tableId, {
			page : {
				curr : 1
			},
			where : {
				fuzzyQuery : fuzzyQuery
			},
			done : function(res, curr, count) {
				layer.close(curIndex);
			}
		});
	},
	sortTable : function(obj, tableId, layui) {
		var table = layui.table;
		table.reload(tableId, {
			initSort : obj,
			where : {
				sort : obj.field + ' ' + obj.type
			}
		});
	},
	deleteForm : function(deleteOperationUrl,layui,callbackFun) {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		$.ajax({
			type : "POST",
			url : deleteOperationUrl,
			contentType : 'application/json',
			success : function(data) {
				if (data.code == 0) {
					layerTips.msg('操作成功');
					callbackFun();
					layer.close(curIndex);
				} else {
					layerTips.msg(data.message);
					layer.close(curIndex);
				}
			}
		});
	},
	operationForm : function(operationUrl,layui,callbackFun) {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		$.ajax({
			type : "POST",
			url : operationUrl,
			contentType : 'application/json',
			success : function(data) {
				if (data.code == 0) {
					layerTips.msg('操作成功');
					callbackFun();
					layer.close(curIndex);
				} else {
					layerTips.msg(data.message);
					layer.close(curIndex);
				}
			}
		});
	},
	
	viewForm : function(viewPageUrl, title, layui) {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		$.get(viewPageUrl, null, function(form) {
			addBoxIndex = layer.open({
				type : 1,
				title : title,
				content : form,
				shade : false,
				offset : [ '100px', '30%' ],
				area : [ '600px', '550px' ],
				zIndex : 1000,
				maxmin : false,
				full : function(elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top : 0,
							left : 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success : function(layero, index) {

				},
				end : function() {
					addBoxIndex = -1;
				}
			});
			layer.full(addBoxIndex);
		});
	},
	editForm : function(editPageUrl, title, updateOperationUrl,  layui,callbackFun) {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		$.get(editPageUrl, null, function(form) {
			addBoxIndex = layer.open({
				type : 1,
				title : title,
				content : form,
				btn : [ '保存', '取消' ],
				shade : false,
				offset : [ '100px', '30%' ],
				area : [ '600px', '450px' ],
				zIndex : 1000,
				maxmin : false,
				yes : function(index) {
					$('form.layui-form').find('button[lay-filter=edit]').click();
				},
				full : function(elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top : 0,
							left : 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success : function(layero, index) {
					// 弹出窗口成功后渲染表单
					var form = layui.form;
					form.render();
					form.on('submit(edit)', function(data) {
						var curIndex = layer.load(2, {
							zIndex : 2000
						});
						
						
						$.ajax({
							type : "POST",
							url : updateOperationUrl,
							contentType : 'application/json',
							data : JSON.stringify(data.field),
							success : function(data) {
								if (data.code == 0) {
									layerTips.msg('操作成功');
									layerTips.close(index);
									layer.close(addBoxIndex);
									callbackFun();
									layer.close(curIndex);
								} else {
									layerTips.msg('保存失败');
									layerTips.close(index);
									layer.close(curIndex);
								}
							}
						});
						return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
					});
				},
				end : function() {
					addBoxIndex = -1;
				}
			});
			layer.full(addBoxIndex);
		});
	},
	openOperationForm : function(editPageUrl, title, updateOperationUrl,  layui,jsonData,callbackFun) {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		$.get(editPageUrl, null, function(form) {
			addBoxIndex = layer.open({
				type : 1,
				title : title,
				content : form,
				btn : [ '保存', '取消' ],
				shade : false,
				offset : [ '100px', '30%' ],
				area : [ '600px', '450px' ],
				zIndex : 1000,
				maxmin : false,
				yes : function(index) {
					$('form.layui-form').find('button[lay-filter=edit]').click();
				},
				full : function(elem) {
					var win = window.top === window.self ? window : parent.window;
					$(win).on('resize', function() {
						var $this = $(this);
						elem.width($this.width()).height($this.height()).css({
							top : 0,
							left : 0
						});
						elem.children('div.layui-layer-content').height($this.height() - 95);
					});
				},
				success : function(layero, index) {
					// 弹出窗口成功后渲染表单
					var form = layui.form;
					form.render();
					form.on('submit(edit)', function(data) {
						var curIndex = layer.load(2, {
							zIndex : 2000
						});
						var field=data.field;
						var jsData={
								base:field,
								ext:jsonData
						};
						$.ajax({
							type : "POST",
							url : updateOperationUrl,
							contentType : 'application/json',
							data : JSON.stringify(jsData),
							success : function(data) {
								if (data.code == 0) {
									layerTips.msg('操作成功');
									layerTips.close(index);
									layer.close(addBoxIndex);
									callbackFun();
									layer.close(curIndex);
								} else {
									layerTips.msg('保存失败');
									layerTips.close(index);
									layer.close(curIndex);
								}
							}
						});
						return false; // 阻止表单跳转。如果需要表单跳转，去掉这段即可。
					});
				},
				end : function() {
					addBoxIndex = -1;
				}
			});
			layer.full(addBoxIndex);
		});
	}
}
