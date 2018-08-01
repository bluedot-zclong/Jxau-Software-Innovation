// 相当于类名，js模块化
var manageModify = {
	// 初始化方法
	init : function() {
		
		$("#modifyInfo").click(function() {
			manageModify.modifyInfo();
		});
		
		$("#modifyPassword").click(function() {
			manageModify.modifyPassword();
		});
	},

	// 去掉二边的空格
	trim : function(str){"  zhaojun  "
		str = str.replace(/^\s*/,"");// "zhaojun "
		str = str.replace(/\s*$/,"");// "zhaojun"
		return str; 	
	},
	
	validateInfoIsNull : function() {
        $("#userNameSpan").html("");
        $("#telSpan").html("");
        $("#academySpan").html("");
        $("#classesSpan").html("");
        
        var input1 = $.trim($('#userName').val());
        if (!input1) {
            $("#userNameSpan").html("昵称不能为空!");
            $("#userName").focus();
            return false;
        }

        /*var input2 = $.trim($('#email').val());
        var retEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!input2) {
            $("#emailSpan").html("邮箱不能为空!");
            $("#email").focus();
            return false;
        }else if(!retEmail.test(input2)){
            $("#emailSpan").html("邮箱格式错误！");    
            $("#email").focus();
            return false;
         }*/
        
        var input3 = $.trim($('#tel').val());
        var retTel = /^1[3|4|5|7|8][0-9]\d{8}$/;
        if (!input3) {
            $("#telSpan").html("手机号码不能为空!");
            $("#tel").focus();
            return false;
        }else if(!retTel.test(input3)){
            $("#telSpan").html("请输入正确的电话号码！");    
            $("#tel").focus();
            return false;
          }

        // 验证院系
        var $academy = $('#academy');
        // 获取select下拉框中选中的文本值
        var academyText = $("#academy").find("option:selected").text();
        if(academyText == "===请选择===") {
               // 得到span元素errorAcademy
            $("#academySpan").html("院系不能为空!");
            $("#academy").focus();
            return false;
        }
        
        // 验证专业
        // 获取select中的ID
        var $classes = $('#classes');
      // 获取select下拉框中选中的文本值
        var classesText = $("#classes").find("option:selected").text(); 
        if(classesText == "===请选择===") {
            // 得到span元素errorAcademy
            $('#classesSpan').html("班级不能为空!");
            $("#classes").focus();
            return false;
        }
        
        return true;
    },
    
    // validatePasswordIsNull()：验证修改密码的各个输入框是否为空,若都不为空，则返回true，否则返回false，不进入修改密码方法
    validatePasswordIsNull : function() {
        $("#oldpwSpan").html("");
        $("#newpwSpan").html("");
        $("#repwSpan").html("");
        
        
        var input1 = $.trim($('#oldpw').val());
        if (!input1) {
            $("#oldpwSpan").html("原密码不能为空!");
            $("#oldpw").focus();
            return false;
        }

        var input2 = $.trim($('#newpw').val());
        if (!input2) {
            $("#newpwSpan").html("新密码不能为空!");
            $("#newpw").focus();
            return false;
        }

        var input3 = $.trim($('#repw').val());
        if (!input3) {
            $("#repwSpan").html("重复密码不能为空!");
            $("#repw").focus();
            return false;
        }else if(!input2 === input3){
            $("#repwSpan").html("密码不匹配，请重试！");    
            $("#repwSpan").focus();
            return false;
        }
        
        var input4 = $.trim($('#vcode').val());
        if (!input4) {
            $("#vcodeSpan").html("验证码不能为空!");
            $("#vcode").focus();
            return false;
        }

        return true;
    },
    
    // 修改密碼
    modifyPassword : function() {
        // validatePasswordIsNull()：验证各个输入框是否为空
        if (manageModify.validatePasswordIsNull()) {
        	var oldpassword = $('#oldpw').val(); 
			var newpassword = $('#newpw').val();
			var repassword = $('#repw').val();
			var vcode = $("#vcode").val();
			var url = $("#base").val() + '/user/modifyPassword';
			
        	var data = {
                oldPassword : oldpassword,
                newPassword : newpassword,
                rePassword : repassword,
                vcode: vcode
            }
        
            $.ajax({
            	 url : url,
 	            data : data,
 	            type: "POST",
 	            success:function(data){ 
 	            	alert(data.msg);
 	                if (data.success) {
 						window.location.href = $("#base").val() + '/logout';
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
    },
    
    // 修改用户信息
	modifyInfo : function() {
		// validateInfoIsNull()：验证各个输入框是否为空
        if (manageModify.validateInfoIsNull()) {
			var tel = $("#tel").val();
			var userName = $("#userName").val();
			var sex = $("#sex").find("option:selected").text(); 
			var academy = $.trim($('#academy').val()); 
			var major = $("#major").val(); 
			var classs = $("#classs").val();
			var studentNumber = $("#sid").val();
			var url = $("#base").val() + '/user/modifyInfo';
			
			// 请求数据,登录账号 +密码
		     var jsondata = {
		    		 "tel" : tel,
		    		 "sex" : sex,
		    		 "userName": userName,
		    		 "academy" : academy,
		    		 "major" : major,
		    		 "classs" : classs,
		    		 "studentNumber" : studentNumber
		     }
		     /*var data = {
		    		 username: username,
		    		 password:password,
		    		 vcode: vcode,
		    		 rememberMe : rememberMe
		     }*/
		     
		     $.ajax({
		            url : url,
		            dataType : "JSON",
		            data : JSON.stringify(jsondata), //直接用JSON对象 
		            contentType: "application/json;charset=utf-8", 
		            /*data : data,*/
		            type: "POST",
		            success:function(data){ 
		            	alert(data.msg);
		                if (data.success) {
							window.location.href = $("#base").val() + '/view/userInfo';
						}else if(data.msg.indexOf("请先登录") >= 0){
							window.location.href = $("#base").val() + '/view/login';
						}
		              },
		              error:function(data){ 
		                  alert("fail"); 
		              }
		     });
		}
	}
}

$(function() {
	// 入口，调用初始化方法
	manageModify.init();
});

