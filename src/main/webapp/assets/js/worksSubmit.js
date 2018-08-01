
 var manageSubmit = {
		
	// 需要跳转的路径
	URL: {
		goPageUrl: function() {
			// 点击登录时要请求的路径,进入Servlet内进行验证信息是否正确，返回验证结果
			return $("#baseUrl").val() + "/LoginServlet";
		},
		refreshUrl: function() {
			// 点击登录后返回正确信息,进入Servlet内进行登录，跳转到成功页面，并保存数据
			return $("#baseUrl").val() + "/LoginServlet?method=Submit&email="+$('#emailID').val();
		}
	},
	
	init: function() {
		// 判断是否有提交信息
		var msg = $("#msg").val();
		if(msg != null && msg != "") {
			alert(msg);
		}
		
		$("#exampleInputFile").change(function(){
			$("#uploadFile").val("");
			// 名称
			var name = this.files[0].name;  
			// alert(this.files[0].name);
			// 大小 字节 M
			var size = Math.round(this.files[0].size / 1024 / 1024);
			if(size > 30) {
				alert('文件大小不得超过30M');
		        return;
			}
			// 类型
			var type = this.files[0].name.substring(this.files[0].name.lastIndexOf(".")).toLowerCase();
			 if (!type.match(/.zip|.rar/)) {
				 alert('文件格式不对！');
	             return false;
	         }  
			 $("#uploadFile").val(this.files[0].name);
		});
		
		// 用户上传文件
        $("#goUpload").click(function() {
        	manageSubmit.goUpload();
        });
	},
	
	
	
	// validateIsNull()：验证各个输入框是否为空,若都不为空，则返回true，否则返回false，不进入login()方法
	validateIsNull: function() {
		$("#errorFilename").html("");
		$("#errorExampleInputFile").html("");
		
		var input1 = $.trim($('#productionName').val());
		if(!input1) {
			$("#errorFilename").html("作品名称不能为空!");
			$("#productionName").focus();
			return false;
		}
		
		var input2 = $.trim($('#exampleInputFile').val());
		if(!input2) {
			$("#errorExampleInputFile").html("请选择文件!");
			$("#exampleInputFile").focus();
			return false;
		} 
		
		return true;
	},
	
	// 提交
	goUpload: function() {
		// validateIsNull()：验证各个输入框是否为空
		if(manageSubmit.validateIsNull()) {
			var form=document.getElementById("uploadProduction");
	        var fd =new FormData(form);
	 		var url = $("#base").val() + '/user/uploadWorks';
	 		
	 		//请求数据
	 		 var data = {
	 			fd: fd
	 		 }
	 	     
			$.ajax({
                type: 'POST',
                url: url,
                processData: false,
                contentType: false,
                data: data,
                success:function(data){ 
	            	alert(data.msg);
	                if (data.success) {
	                	
					}
	            },
                error:function(data){ 
                  alert("fail"); 
                }
            });
		}
	}
 }
 
 // 入口
 $(function(){
	 manageSubmit.init();
 });