<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>上传文件</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").click(function (){
				var form = $("#${nid}inputForm");
				var fileName = $("#${nid}file").val();
				if(fileName == ""){
					showTip("请选择上传的文件");
				}else{
					$("#${nid}btnSubmit").attr("disabled", "disabled");   
					loading('正在提交，请稍等...');
					closeLoading();
					form.ajaxSubmit({
						type: "post",
						dataType: "json",
						success: function(response, status) {
							$("#${nid}btnSubmit").removeAttr("disabled");
							closeLoading();
		            		//$("#submit").removeAttr("disabled");               	
		                	if(response.status==1){
		                		showTip("上传完成");
		                		$("#${nid}btnCancel").click();
		                	}else{
		                		showTip(response.message.value);
		                	}
		       			}
		           });
				}
			});
		});
		
		//点关闭按钮
	    $("#btnCancel").click(function(){
	    	//dialog.dialog("close");
	     	formClose($(this));
	    }); 
	</script>
</head>
<body>
	<div class="text-center" style="font-size: 12px; color: red; display: block; height: 20px; line-height: 20px; background-color: #eee; margin-bottom: 5px;">附件大小不能大于10M</div>
	<form:form id="inputForm" modelAttribute="reimburseFile" action="${ctx}/refund/reimburseFile/upload" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="reimburseId"/>
		<form:hidden path="type"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文件标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<input type="file" id="file" name="file" class="required"  style="width:300px"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="上传"/>
			<input id="btnCancel" class="btn" type="button" value="关 闭"/>
		</div>
	</form:form>
</body>
</html>