layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	fuzzyQuery : 'fuzzyQuery'
});
layui.use([ 'upload','element', 'laydate', 'table', 'form', 'fuzzyQuery','layedit' ], function() {
	var $ = layui.$;
	var fuzzyQuery = layui.fuzzyQuery;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;
	var  laydate = layui.laydate;
	var upload = layui.upload;
	
	fuzzyQuery.clearQuery();
	var search_typeId = $("#search_typeId").val();
	var search_typeName =['未知','收鸽','集鸽'];
	
	if (search_typeId.length > 0)
		fuzzyQuery.addEqual("typeId", search_typeId);
	var fuzzyQueryJson = fuzzyQuery.getQuery();

	baseOperation.initTable("listTable", "/nodejsPigeonCollection/list", {
		field : 'create_time',
		type : 'asc'
	}, ' create_time asc,batchNumber asc,sortNumber asc ', JSON.stringify(fuzzyQueryJson), [ [ // 表头
	{
		title : '序号',
		type : 'numbers',
		fixed : 'left'
	}, {
		type : 'checkbox',
		fixed : 'left'
	}, {
		field : 'cote_name',
		width : 150,
		title : '俱乐部/公棚名称'
	}, {
		field : 'pigowner',
		width : 300,
		title : '鸽主名称'
	}, {
		field : 'ringnum',
		width : 200,
		title : '足环号'
	}, {
		field : 'pigowner_mobile',
		width : 150,
		title : '手机号码'
	}
	, {
		field : 'collection_sendTime',
		width : 200,
		title : '收鸽日期'
	}
	, {
		field : 'pigeon_ext',
		width : 200,
		title : '其它资料'
	}, {
		field : 'fileName',
		width : 200,
		title : '上传文件名'
	},  {
		field : 'status',
		title : '当前状态',
		width : 100
	},  {
		field : 'create_time',
		title : '创建时间',
		width : 200
	},
	{
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
	}
	] ], layui);

	table.on('sort(test)', function(obj) {
		baseOperation.sortTable(obj,"listTable",layui);
		
	});
	
	//普通图片上传
	  var uploadInst = upload.render({
	    elem: '#btnUpload',
	    url: '/storage/create/',
	    accept: 'file',
	    before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	    	  
	    	  var layer = layui.layer;
	    	  var curIndex =layer.load(2, {
	    		content: '加载中...',
	  			zIndex : 2000
	  		});
	    	  
	    	  var filename=file.name;
	    	  var reader = new FileReader();
			    reader.onload = function (e) {
			        // pre-process data
			        var binary = "";
			        var bytes = new Uint8Array(e.target.result);
			        var length = bytes.byteLength;
			        for (var i = 0; i < length; i++) {
			            binary += String.fromCharCode(bytes[i]);
			        }

			        /* read workbook */
			        var wb = XLSX.read(binary, {type: 'binary'});

			        /* grab first sheet */
			        var wsname = wb.SheetNames[0];
			        var ws = wb.Sheets[wsname];

			        /* generate HTML */
			        var HTML = XLSX.utils.sheet_to_html(ws);
			        var json=XLSX.utils.sheet_to_json(ws);

			        var reqJson=[];
                    for(var ii=0;ii<json.length;ii++)
                    {
                    	var hasname=json[ii].hasOwnProperty("姓名");
                    	if(hasname)
                    	{
                    		var form={};
                    		
                    		var pigowner=json[ii]["姓名"];
                    		form["pigowner"]=pigowner;
                    		form["fileName"]=filename;
                    		form["typeId"]=search_typeId;
                    		form["typeName"]=search_typeName[search_typeId];
                    		
                    		if(json[ii].hasOwnProperty("鸽主编号"))
                    		{
                    			form["pigowner_num"]=json[ii]["鸽主编号"];
                    		}
                    		if(json[ii].hasOwnProperty("环号"))
                    		{
                    			form["ringnum"]=json[ii]["环号"];
                    		}
                    		if(json[ii].hasOwnProperty("羽色"))
                    		{
                    			form["pigeon_color"]=json[ii]["羽色"];
                    		}
                    		if(json[ii].hasOwnProperty("收鸽日期"))
                    		{
                    			form["collection_sendTime"]=json[ii]["收鸽日期"];
                    		}
                    		if(json[ii].hasOwnProperty("手机号码"))
                    		{
                    			form["pigowner_mobile"]=json[ii]["手机号码"];
                    		}
                    		form["pigeon_ext"]=JSON.stringify(json[ii]);
                    		reqJson.push(form);
                    	}                  	
                    }
			        $.ajax({
						url : '/nodejsPigeonCollection/insertBatch',
						type : "post",
						dataType : "json",
						contentType : 'application/json',
						data : JSON.stringify(reqJson),
						success : function(data) {
							if (data.code == 0) {
								layer.msg('操作成功->成功上传'+data.data+'条');
								flashTable();
								layer.close(curIndex);
							} else {
								layer.msg(data.message);
								layer.close(curIndex);
							}
						}
					});

			    };

			    reader.readAsArrayBuffer(file);
	      });
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code == 0){
	    	 
	      }
	      else
	      {
	    	  return layer.msg('上传失败');
	      }
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var pImgText = $('#pImgText');
	      pImgText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
	      pImgText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
	      });
	    }
	  });
	  
	$('#btnDelete').on('click', function() {
		 var layer = layui.layer;
   	  var curIndex =layer.load(2, {
 			zIndex : 2000
 		});
   	  layer.msg('删除处理中');
   	  
		var checkStatus = table.checkStatus('listTable');
		var data = checkStatus.data;
		if (data != null && data.length > 0) {
			var ids=[];
			for(var ii=0;ii<data.length;ii++)
			{
				ids.push(data[ii].collection_id);
			}
			$.ajax({
				url : '/nodejsPigeonCollection/delete/listBy',
				type : "post",
				dataType : "json",
				contentType : 'application/json',
				data : JSON.stringify(ids),
				success : function(data) {
					if (data.code == 0) {
						layer.msg('操作成功');
						flashTable();
						layer.close(curIndex);
					} else {
						layer.msg(data.message);
						layer.close(curIndex);
					}
				}
			});
		} else {
			layer.alert("请选择一条操作的数据！");
		}

	});
	
	$('#btnSearch').on('click', function() {
		flashTable();
	});

	$('#btnSms').on('click', function() {
		var $ = layui.$;
		var table = layui.table;
		var layer = layui.layer;
		var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象

		var curIndex = layer.load(2, {
			zIndex : 2000
		});
		var search_typeId = $("#search_typeId").val();
		$.ajax({
			 type: "POST",
	            url: '/nodejsPigeonCollection/sendSms?type='+search_typeId,
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
	function flashTable() {
		fuzzyQuery.clearQuery();

		var search_typeId = $("#search_typeId").val();
		if (search_typeId.length > 0)
			fuzzyQuery.addEqual("typeId", search_typeId);
		
		var search_pigowner = $("#search_pigowner").val();
		if (search_pigowner.length > 0)
			fuzzyQuery.addLike("pigowner", search_pigowner);
		
		var search_ringnum = $("#search_ringnum").val();
		if (search_ringnum.length > 0)
			fuzzyQuery.addLike("ringnum", search_ringnum);
		
		var search_fileName = $("#search_fileName").val();
		if (search_fileName.length > 0)
			fuzzyQuery.addLike("fileName", search_fileName);

		var fuzzyQueryJson = fuzzyQuery.getQuery();
		baseOperation.flashTable(JSON.stringify(fuzzyQueryJson), 'listTable', layui);
	}

});