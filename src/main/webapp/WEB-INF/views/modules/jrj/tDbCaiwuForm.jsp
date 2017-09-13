<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产负债管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			/* $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",	
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			}); */
			
			
			$("#btnSubmit").click(function (){
				var fileName = $("#file").val();
				 if(fileName == ""){
					showTip("请选择上传的文件");
				}else{					
					var form = $("#mainForm");
					form.ajaxSubmit({
						type: "post",
						dataType: "json",
						success: function(response) {
							if(response.status==1){
		                		showTip(response.message);		                		
		                	}else{
		                		showTip(response.message);
		                	}
		       			}
		           });
				}
			});			 
			
		});
		
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/db/caiWu/form">导入担保报表</a></li>
	</ul><br/> 
	
	<form:form id="mainForm" name="mainForm" action="${ctx}/db/caiWu/save" enctype="multipart/form-data" method="post" >
	
	<br>
	<table class="table-form">              
	  <tr>
	   <td >所属月份：</td>
       <td >
          <input name="date" id="date" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="${date}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
       </td> 
        
	    <td>附件上传：</td>
	    <td>
	    	<input id="file" name="file" class="inputbox set_form_text" maxlength="20" type="file" >	 
	    	<span style="color: red">请上传excel版本</span>
	    </td>
	  </tr>
	</table>
	
	<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="button"  value="保 存"/>
			<input id="btnCancel" class="btn" type="button" value="重置" onclick="javascript:document.forms[0].reset();"/>
	</div>	
	</form:form>
	
</body>
</html>