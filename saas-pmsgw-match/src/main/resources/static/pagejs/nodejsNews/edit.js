layui.use(['upload','layedit', 'form'], function(){

	 var $ = layui.jquery;
	 var upload = layui.upload;
	 
	 var layedit = layui.layedit;
	 var form = layui.form;
	 
	//实例化编辑器
	    var um = UE.getEditor('myEditor');
	    
		/*//构建一个默认的编辑器
		  var index_news_text = layedit.build('news_text');
	  
		  form.verify({
			  pattern:function () {
			  layedit.sync(index_news_text);
			  }
			  });
			  form.render();*/
			  
	  //普通图片上传
	  var uploadInst = upload.render({
	    elem: '#btnUploadImg'
	    ,url: '/storage/create/'
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#imgUpload').attr('src', result); //图片链接（base64）
	      });
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code == 0){
	    	  $('#news_imgHref').val(res.data.url);
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
  
});