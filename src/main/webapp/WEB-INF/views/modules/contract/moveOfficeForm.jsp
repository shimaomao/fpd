<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
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
			});
			checkCompany('${office.type}');
		});
		
		function moveSave(){
			  var loanContractId="${loanContractId}";
			  var officeId="${officeId}"
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/saveMove",
		         	data: {"loanContractId":loanContractId,"officeId":officeId},
		         	dataType: "json",
		         	success: function(data){
		         		if(data.status == '1' ){
		         			alert(data.message);
		         			var url = "${ctx}/contract/tLoanContract";
		         			location.href = url;
		         			
		         		 }else{
		         			alert(data.message);
		         		} 
		         	}
		       });  
			 
		}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/moveContract?loanContractId=${loanContractId}&officeId=${officeId}&tab=bd">转移机构信息</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/contract/tLoanContract/saveMove" method="post" target="mainFrame" class="form-horizontal">
	     <%-- <div class="control-group">
			<label class="control-label">目标产品：</label>
			<div class="controls">
				<sys:treeselect id="moveOffice" isAll="false" name="" value="" labelName="moveOfficeName" labelValue="" 
			title="目标产品" url="/product/tProduct/productTreeData?origan=${officeId}" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div><font color="red">*</font>
		</div> --%>
		
	   
	    <div class="control-group" id="name">
			<label class="control-label">机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
		
		<div class="form-actions">
		    <input id="btnSubmit" class="btn btn-primary" type="button" value="提交" onclick="moveSave();"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>