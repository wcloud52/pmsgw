layui.use([ 'element',  'form' ], function() {
	var $ = layui.$;
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	
	var cote_id=$("#bind_cote_id").val();
	selectCote(cote_id);
	
	function selectCote(cote_id) {
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
				
				$("#cote_id").append(html);
				$("#cote_id").val(cote_id);
				layui.form.render();
				
			}
		});
	}
	
});