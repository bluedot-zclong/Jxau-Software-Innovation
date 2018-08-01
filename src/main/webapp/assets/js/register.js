  
 var manageRegister = {
		
	//清空输入框
	clean: function() {
		$('#emailID').html("");
		$("#passwordID").html("");
	},
	
	//检查用户名（邮箱）
	validateEmail: function() {
		  var ret = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		  var email = $.trim($('#email').val());
		  var span = $("#errorSpan");
		  span.html("");
		  if(email.length == 0){
			  //得到span元素
			  span.html("邮箱不能为空");	
//			  $("#email").focus();
				return false;
		  }else if(!ret.test(email)){
			  span.html("邮箱格式错误！");	
//			  $("#email").focus();
			  return false;
		  }else {
			  var url = $("#base").val() +　'/user/queryEmail';
			  var data = {
					email: email
			     }
			  $.ajax({
		            url :url ,
		            data : data,
		            type: "POST",
		            success:function(data){ 
		                if (!data.success) {
		                	span.html("该邮箱已被注册！");
		                	return false;
						} else {
							span.html("");
							return true;
						}
		              },
		              error:function(data){ 
		                  alert("fail"); 
		                  return false;
		              }
		     });
	     }
	},
	
	//检查密码
	validatePassword: function() {
		var ret = /^[a-z0-9_-]{6,10}$/;
		  var password = $.trim($('#password').val());
		  var span = $("#errorPassword");
		  if(password.length == 0){
			  //得到span元素
			  span.html("密码不能为空");	
//			  $("#password").focus();
			  return false;
		  }else if(!ret.test(password)){
			  span.html("密码长度大小为6~10之间！");	
//			  $("#password").focus();
			  return false;
		  }else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查重复密码
	validateRePassword: function() {
		var password = $.trim($('#password').val());
		var repassword = $.trim($('#repassword').val());
		  var span = $("#errorRepassword");
		  if(repassword.length == 0){
			  //得到span元素
			  span.html("密码不能为空");	
//			  $("#repassword").focus();
			  return false;
		  }else if(password != repassword){
			  span.html("与原密码不同！");	
//			  $("#repassword").focus();
			  return false;
		  }else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查电话号码
	validateTel: function() {
		var ret = /^1[3|4|5|7|8][0-9]\d{8}$/;
		  var tel = $.trim($('#tel').val());
		  var span = $("#errorTel");
		  if(tel.length == 0){
			  //得到span元素
			  span.html("电话号码不能为空！");	
//			  $("#tel").focus();
			  return false;
		  }else if(!ret.test(tel)){
			  span.html("请输入正确的电话号码！");	
//			  $("#tel").focus();
			  return false;
		  }else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查姓名
	validateName: function() {
		  var ret = /^[\u4e00-\u9fa5]{2,20}$/;
		  var name = $.trim($('#name').val());
		  var span = $("#errorName");
		  if(name.length == 0){
			  //得到span元素
			  span.html("姓名不能为空！");	
//			  $("#name").focus();
			  return false;
		  }else if(!ret.test(name)){
			  span.html("请输入真实姓名！");	
//			  $("#name").focus();
			  return false;
		  }else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查班级
	validateClasss: function() {
		  var ret = /^\d{3,4}$/;
		  var classs = $.trim($('#classs').val());
		  var span = $("#errorClass");
		  if(classs.length == 0){
			  //得到span元素
			  span.html("班级不能为空！");	
//			  $("#classs").focus();
			  return false;
		  }/*else if(!ret.test(classs)){
			  span.html("请按格式输入！");	
//			  $("#classs").focus();
			  return false;
		  }*/else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查学号
	validateSid: function() {
		  var ret = /^\d{8}$/;
		  var sid = $.trim($('#sid').val());
		  var span = $("#errorSid");
		  if(sid.length == 0){
			  //得到span元素
			  span.html("学号不能为空！");	
//			  $("#sid").focus();
			  return false;
		  }else if(!ret.test(sid)){
			  span.html("请按格式输入！");	
//			  $("#sid").focus();
			  return false;
		  }else {
			  
			  var url = $("#base").val() +　'/user/queryStudent';
			  var data = {
			    	sid: sid
			     }
			  
			  $.ajax({
		            url :url ,
		            /*data : JSON.stringify(jsondata), //直接用JSON对象*/
		            /*contentType: "application/json;charset=utf-8",*/
		            data : data,
		            type: "POST",
		            success:function(data){ 
		                if (data.success) {
		                	$('#name').val(data.obj.studentName);
		                	$('#academy').val(data.obj.academy);
		                	$('#classs').val(data.obj.classes);
						} else {
							alert(data.msg);
						}
		              },
		              error:function(data){ 
		                  alert("fail"); 
		              }
		     });
			  span.html("");
			  return true;
		  }
	},
	
	//检查院系
//	validateAcademy: function() {
//		/*
//		 * 验证院系是否被选中
//		 */
//		var span = $("#errorAcademy");
//		//获取select中的ID  
//        var academy = document.getElementById("academy");
//       // selectedIndex代表的是你所选中项的index
//        var index=academy.selectedIndex ; 
//        //获取select下拉框中第一个文本值  
//        var academyText = academy.options[index].text; 
//        if(academyText == "===请选择===") {
//        	//得到span元素
//			 span.html("院系不能为空！");	
//			 return false;
//        }else {
//        	span.html("");	
//        	return true;
//        }
//	},
	validateAcademy: function() {
		  var ret = /^[\u4e00-\u9fa5]{2,20}$/;
		  var academy = $.trim($('#academy').val());
		  var span = $("#errorAcademy");
		  if(academy.length == 0){
			  //得到span元素
			  span.html("学院不能为空！");	
			  return false;
		  }else {
			  span.html("");
			  return true;
		  }
	},
	
	//检查专业
	validateMajor: function() {
		/*
		 * 验证专业是否被选中
		 */
		var span = $("#errorMajor");
		//获取select中的ID  
        var major = document.getElementById("major");
        // selectedIndex代表的是你所选中项的index
        var index=major.selectedIndex ; 
        //获取select下拉框中第一个文本值  
        var majorText = major.options[index].text; 
        if(majorText == "===请选择===") {
        	//得到span元素
			 span.html("专业不能为空！");	
			 return false;
        }else {
        	span.html("");	
        	return true;
        }
	},
	
	
	
	//点击登录触发的事件
	init: function() {
	        
		/* 对电子邮箱的验证 */
		$("#email").blur(function(){
			manageRegister.validateEmail();
		});
		
		
		/* 对密码的验证 */
		$("#password").blur(function(){
			manageRegister.validatePassword();
		});
		
		/* 对重复密码的验证 */
		$("#repassword").blur(function(){
			manageRegister.validateRePassword();
		});
		
		/* 对电话号码的验证 */
		$("#tel").blur(function(){
			manageRegister.validateTel();
		});
		
		
		/* 对姓名的验证 */
		$("#name").blur(function(){
			manageRegister.validateName();
		});
		
		/* 对班级的验证 */
		$("#classs").blur(function(){
			manageRegister.validateClasss();
		});
		
		/* 对学号的验证 */
		$("#sid").blur(function(){
			manageRegister.validateSid();
		});
		
		/* 对学院的验证 
		document.getElementById("academy").onchange = function() {
			manageRegister.validateAcademy();
		};*/
		$("#academy").blur(function(){
			manageRegister.validateAcademy();
		});
		
		 //对专业的验证 
		/*document.getElementById("major").onchange = function() {
			manageRegister.validateMajor();
		};*/
		
		
		//点击注册，进行第二次检验
		$("#goRegister").click(function() {
			manageRegister.register();
		});
	},
	
	//validateIsNull()：验证各个输入框是否为空,若都不为空，则返回true，否则返回false，不进入login()方法
	validateIsNull: function() {
		/* 对电子邮箱的验证 */
		//return manageRegister.validateEmail();
		
		/* 对密码的验证 */
		return manageRegister.validatePassword();
		/* 对重复密码的验证 */
		return manageRegister.validateRePassword();
		/* 对电话号码的验证 */
		return manageRegister.validateTel();
		
		/* 对姓名的验证 */
		return manageRegister.validateName();
		/* 对班级的验证 */
		return manageRegister.validateClasss();
		/* 对学院的验证 */
		return manageRegister.validateAcademy();
		
		/* 对专业的验证 */
//		return manageRegister.validateMajor();
		
		/* 对学号的验证 */
		return manageRegister.validateSid();
		//return true;
		
	},
	
	//注册
	register: function() {
		//validateIsNull()：验证各个输入框是否为正确
//		alert(manageRegister.validateIsNull());
//		return manageRegister.validateIsNull();
//		alert(validateIsNull());
//		return false;
		// validateInfoIsNull()：验证各个输入框是否为空
        if (manageRegister.validateIsNull()) {
        	var email = $.trim($('#email').val());
        	var password = $.trim($('#password').val());
			var tel = $.trim($('#tel').val());
			var userName = $.trim($('#name').val());
			var sex = $("#sex").find("option:selected").val(); 
			var academy = $.trim($('#academy').val()); 
			var classs = $.trim($('#classs').val());
			var studentNumber = $.trim($('#sid').val());
			var url = $("#base").val() + '/user/register';
			
			// 请求数据,登录账号 +密码
		     var jsondata = {
		    		 "email" : email,
		    		 'password' : password,
		    		 "tel" : tel,
		    		 "sex" : sex,
		    		 "userName": userName,
		    		 "academy" : academy,
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
							window.location.href = $("#base").val() + '/view/login';
						}
		              },
		              error:function(data){ 
		                  alert("注册失败！"); 
		              }
		     });
		}
		
	}
 }
 
  //入口
  $(function(){
	    
	    //入口，点击注册进入
	    manageRegister.init();
	    
 });
