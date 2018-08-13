// 相当于类名，js模块化
var manageLogin = {
	// 初始化方法
	init : function() {
		
		$("#login").click(function() {
			manageLogin.login();
		});

	},

	login : function() {
		var username = $("#email").val();
		var password = $("#password").val();
		var vcode = $("#vcode").val();
		var rememberMe = $("#rememberMe").is(":checked");
		var url = $("#base").val() + '/user/login';
		
		//请求数据,登录账号 +密码
	     var jsondata = {
	    		 "userName": username,
	    		 "password":password,
	    		 "vcode": vcode,
	    		 "rememberMe" : rememberMe
	     }
	     var data = {
	    		 username: username,
	    		 password:password,
	    		 vcode: vcode,
	    		 rememberMe : rememberMe
	     }
	     
	     
	     $.ajax({
	            url : url,
	            /*data : JSON.stringify(jsondata), //直接用JSON对象*/
	            /*contentType: "application/json;charset=utf-8",*/
	            data : data,
	            type: "POST",
	            success:function(data){ 
	            	var msg = data.msg;
//	            	var role = msg.substring(msg.length-1);
//	            	msg = msg.substring(0, msg.length-1);
	            	alert(msg);
	                if (data.success) {
	                	window.location.href = $("#base").val() + '/view/userInfo';
					} else {
						// 刷新验证码
		                $("#captcha")[0].click();
					}
	              },
	              error:function(data){ 
	                  alert("fail"); 
	              }
	     });
	}
}

$(function() {
	// 入口，调用初始化方法
	manageLogin.init();
});