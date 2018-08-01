// 相当于类名，js模块化
var manageModify = {
	// 初始化方法
	init : function() {
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
        $("#telSpan").html("");
        $("#emailSpan").html("");
        
        var input2 = $.trim($('#email').val());
        var retEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (!input2) {
            $("#emailSpan").html("邮箱不能为空!");
            $("#email").focus();
            return false;
        }else if(!retEmail.test(input2)){
            $("#emailSpan").html("邮箱格式错误！");    
            $("#email").focus();
            return false;
         }
        
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

        return true;
    },
    
    // 修改密碼
    modifyPassword : function() {
        // validatePasswordIsNull()：验证各个输入框是否为空
        if (manageModify.validateInfoIsNull()) {
        	var email = $('#email').val(); 
			var tel = $('#tel').val();
			var url = $("#base").val() + '/user/forgetPassword';
			
        	var data = {
    			email : email,
    			tel : tel,
            }
        
            $.ajax({
            	 url : url,
 	            data : data,
 	            type: "POST",
 	            success:function(data){ 
 	            	alert(data.msg);
 	                if (data.success) {
 						window.location.href = $("#base").val() + '/logout';
 					}
 	              },
 	              error:function(data){ 
 	                  alert("fail"); 
 	              }
            });
        }
    },
    
}

$(function() {
	// 入口，调用初始化方法
	manageModify.init();
});

