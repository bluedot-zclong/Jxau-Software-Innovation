// 相当于类名，js模块化
var manageMember = {
	// 初始化方法
	init : function() {
		
		/* 对学号的验证 */
		$("#sid").blur(function(){
			manageMember.validateSid();
		});
		
		// 添加队员信息
		$("#addMember").click(function() {
			manageMember.addMember();
		});
		
		// 修改队员信息
		$("#modifyMember").click(function() {
			manageMember.modifyMember();
		});

	},

	//检查学号
	validateSid: function() {
		  var ret = /^\d{8}$/;
		  var sid = $.trim($('#sid').val());
		  var span = $("#errorSid");
		  if(sid.length == 0){
			  //得到span元素
			  span.html("学号不能为空!");	
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
		                	$('#memberName').val(data.obj.studentName);
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
	
	validateInfoIsNull : function() {
        $("#memberNameSpan").html("");
        $("#telSpan").html("");
        $("#academySpan").html("");
        $("#classesSpan").html("");
        
        var input1 = $.trim($('#memberName').val());
        if (!input1) {
            $("#memberNameSpan").html("姓名不能为空!");
            $("#memberName").focus();
            return false;
        }

        var input2 = $.trim($('#email').val());
        var retEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
        if (input2 != "") {
        	if(!retEmail.test(input2)){
	            $("#emailSpan").html("邮箱格式错误！");    
	            $("#email").focus();
	            return false;
        	}
         }
        
        var input3 = $.trim($('#tel').val());
        var retTel = /^1[3|4|5|7|8][0-9]\d{8}$/;
        if (input3 != "") {
        	if(!retTel.test(input3)){
	            $("#telSpan").html("请输入正确的电话号码！");    
	            $("#tel").focus();
	            return false;
        	}
          }
        
        var input4 = $.trim($('#classs').val());
        if (!input4) {
            $("#classsSpan").html("班级不能为空!");
            $("#classs").focus();
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
        var major = $('#major');
      // 获取select下拉框中选中的文本值
        var majorText = $("#major").find("option:selected").text(); 
        if(majorText == "===请选择===") {
            // 得到span元素errorAcademy
            $('#majorSpan').html("班级不能为空!");
            $("#major").focus();
            return false;
        }
        
        return true;
    },
    
	addMember : function() {
		if (manageMember.validateInfoIsNull()) {
			var memberEmail = $("#email").val();
			var memberTel = $("#tel").val();
			var memberName = $("#memberName").val();
			var memberAcademy = $.trim($('#academy').val()); 
//			var memberMajor = $("#major").find("option:selected").text(); 
			var memberClasss = $("#classs").val();
			var url = $("#base").val() + '/member/addMember';
			
			// 请求数据,登录账号 +密码
		     var jsondata = {
		    		 "memberEmail" : memberEmail,
		    		 "memberTel" : memberTel,
		    		 "memberName": memberName,
		    		 "memberAcademy" : memberAcademy,
//		    		 "memberMajor" : memberMajor,
		    		 "memberClasss" : memberClasss,
		     }
		     
		     $.ajax({
		            url : url,
		            dataType : "JSON",
		            data : JSON.stringify(jsondata), //直接用JSON对象 
		            contentType: "application/json;charset=utf-8", 
		            type: "POST",
		            success:function(data){ 
		            	alert(data.msg);
		                if (data.success) {
							window.location.href = $("#base").val() + '/view/userInfo';
						}
		              },
		              error:function(data){ 
		                  alert("fail"); 
		              }
		     });
		}
	},
	
	modifyMember : function() {
		if (manageMember.validateInfoIsNull()) {
			var memberId = $("#memberId").val();
			var memberEmail = $("#email").val();
			var memberTel = $("#tel").val();
			var memberName = $("#memberName").val();
			var memberAcademy = $.trim($('#academy').val()); 
//			var memberMajor = $("#major").find("option:selected").text(); 
			var memberClasss = $("#classs").val();
			var url = $("#base").val() + '/member/modifyMember';
			
			// 请求数据,登录账号 +密码
		     var jsondata = {
		    		 "memberId" : memberId,
		    		 "memberEmail" : memberEmail,
		    		 "memberTel" : memberTel,
		    		 "memberName": memberName,
		    		 "memberAcademy" : memberAcademy,
//		    		 "memberMajor" : memberMajor,
		    		 "memberClasss" : memberClasss,
		     }
		     
		     $.ajax({
		            url : url,
		            dataType : "JSON",
		            data : JSON.stringify(jsondata), //直接用JSON对象 
		            contentType: "application/json;charset=utf-8", 
		            type: "POST",
		            success:function(data){ 
		            	alert(data.msg);
		                if (data.success) {
							window.location.href = $("#base").val() + '/view/userInfo';
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
	manageMember.init();
});