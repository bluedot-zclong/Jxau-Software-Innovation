
 // 相当于类名，js模块化
 var manageUserInfo = {
	 // 初始化方法
     init: function() {
    	 // 触发报名按钮
    	 $("#signUp").click(function() {
    		 manageUserInfo.signUp();
    	 });
    	 
    	 // 添加指导老师
    	 $("#goAddTeacher").click(function() {
    		 manageUserInfo.addTeacher();
    	 });
    	 
    	 // 删除组员
    	 $("#removeMember").click(function() {
    		 var memberId = $("#removeMember").attr("title");
    		 if(confirm("确定删除该数据")){
    			 manageUserInfo.removeMember(memberId);
    		 }
    	 });
    	 $("#removeTeacher").click(function() {
    		 var memberId = $("#removeTeacher").attr("title");
    		 if(confirm("确定删除该数据")){
    			 manageUserInfo.removeMember(memberId);
    		 }
    	 });
    	 
    	 // 查找队员信息
    	 $("#queryMember").click(function() {
    		 var memberId = $("#queryMember").attr("title");
    		 manageUserInfo.queryMember(memberId);
    	 });
     },
 
     validateAddTeacherIsNull : function() {
         $("#teacherNameSpan").html("");
         $("#teacherAcademySpan").html("");
         
         
         var input1 = $.trim($('#teacherName').val());
         if (!input1) {
             $("#teacherNameSpan").html("姓名不能为空!");
             $("#teacherName").focus();
             return false;
         }

         var input2 = $.trim($('#teacherAcademy').val());
         if (!input2) {
             $("#teacherAcademySpan").html("院系不能为空!");
             $("#teacherAcademy").focus();
             return false;
         }

         return true;
     },
     
     addTeacher : function() {
    	if (manageUserInfo.validateAddTeacherIsNull()) {
	 		var teacherName = $("#teacherName").val();
	 		var teacherAcademy = $("#teacherAcademy").val();
	 		var memberRole = $("#memberRole").val();
	 		var memberId = $("#memberId").val();
	 		var url = $("#base").val() + '/member/addMember';
	 		
	 		//请求数据
	 	     var jsondata = {
	 	    		'memberName': teacherName,
	 	    		'memberAcademy':teacherAcademy,
	 	    		'memberRole' :memberRole,
	 	    		'memberId' :memberId
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
	 	                	window.location.reload();
	 					}
	 	              },
	 	              error:function(data){ 
	 	                  alert("fail"); 
	 	              }
	 	     });
    	}
 	},
 	
 	// 删除队员
 	removeMember : function(memberId) {
		var memberId = memberId;
		var url = $("#base").val() + '/member/removeMember';
	     var data = {
	    	memberId: memberId
	     }
	     $.ajax({
	            url : url,
	            data : data,
	            type: "POST",
	            success:function(data){ 
	            	alert(data.msg);
	                if (data.success) {
	                	window.location.reload();
					}
	              },
	              error:function(data){ 
	                  alert("fail"); 
	              }
	     });
	},
     
	// 查找队员
 	queryMember : function(memberId) {
		var memberId = memberId;
		var url = $("#base").val() + '/member/queryMember';
	     var data = {
	    	memberId: memberId
	     }
	     $.ajax({
	            url : url,
	            data : data,
	            type: "POST",
	            success:function(data){ 
	            	alert(data.msg);
	                if (data.success) {
	                	window.location.href = $("#base").val() + '/view/modifyMember';
					}
	              },
	              error:function(data){ 
	                  alert("fail"); 
	              }
	     });
	},
	
 	 // 用户报名大赛
 	 signUp: function() {
 		 $.ajax({
 			type : 'POST' ,
 			contentType : 'application/json',
 			url : $("#base").val() + '/user/signUp',
 			processData : false,  
 	        dataType : 'json', 
 	        success : function(data) {
 	        	alert(data.msg);
                if (data.success) {
				}else if(data.msg.indexOf("请先登录") >= 0){
					window.location.href = $("#base").val() + '/view/login';
				}
 	        },
 	        error : function() {
 	        	alert("error");
 	        }
 		 });
 	 }
 }




 $(function() {
	// 入口，调用初始化方法
	manageUserInfo.init();
 });