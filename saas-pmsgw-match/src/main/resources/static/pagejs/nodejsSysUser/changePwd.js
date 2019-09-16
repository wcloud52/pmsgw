layui.use([ 'element', 'form' ], function() {
	var $ = layui.$;
	var form = layui.form;

    //添加验证规则
    form.verify({
        oldPwd : function(value, item){
        	var oldpwdtext=$("#currentPassword").val();
            if(value != oldpwdtext){
                return "密码错误，请重新输入！";
            }
        },
        newPwd : function(value, item){
            if(value.length < 6){
                return "密码长度不能小于6位";
            }
        },
        confirmPwd : function(value, item){
            if(!new RegExp($("#oldPwd").val()).test(value)){
                return "两次输入密码不一致，请重新输入！";
            }
        }
    });

    //修改密码
    form.on("submit(changePwd)",function(data){
    	var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            
            $.ajax({
				url : "/nodejsSysUser/changePwd",
				type : "post",
				dataType : "json",
				contentType : 'application/json',
				data : JSON.stringify(data.field),
				success : function(ret) {
					if (ret.code === 0) {
						layer.msg("密码修改成功！");
						window.location.href = '/logout';
					} else {
						
						layer.msg(ret.message);
					}
					
					layer.close(index);
				}
			});
        },2000);
    	return false; 
    });

});
