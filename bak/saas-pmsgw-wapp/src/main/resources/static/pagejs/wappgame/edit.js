layui.config({
	base : '../js/',
	version : new Date().getTime()
}).extend({ // 模块别名
	
});
layui.use([ 'element', 'laydate', 'table', 'form' ], function() {
	var $ = layui.$;
	
	var layerTips = parent.layer === undefined ? layui.layer : parent.layer;// 获取父窗口的layer对象
	var layer = layui.layer;// 获取当前窗口的layer对象
	var table = layui.table;
	var  laydate = layui.laydate;
	
	  laydate.render({
	    elem: '#startDatetime'
	  });
	  
	  laydate.render({
		    elem: '#endDatetime'
		  });

	  bindRule();
	  function bindRule() {
		  var hiddenruleMasterId= $("#hiddenruleMasterId").val(); 			
			baseOperation.bindSelect("/wappRule/all","ruleMasterId",layui,hiddenruleMasterId,false);
		}
});