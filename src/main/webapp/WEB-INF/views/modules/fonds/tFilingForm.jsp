<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资料归档管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs"> 
		<li><a href="${ctx}/fonds/tFiling/?loancontractId=${tFiling.loancontractId}">资料归档列表</a></li>
		<li class="active"><a href="${ctx}/fonds/tFiling/form?loancontractId=${tFiling.loancontractId}&id=${tFiling.id}">资料归档<shiro:hasPermission name="fonds:tFiling:edit">${not empty tFiling.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="fonds:tFiling:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tFiling" action="${ctx}/fonds/tFiling/save" method="post" class="form-horizontal">
		<input type="hidden" id="loancontractId" name="loancontractId" value="${tFiling.loancontractId}">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">卷宗名称：</label>
			<div class="controls">
				<form:input path="fondsName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卷宗编号：</label>
			<div class="controls">
				<form:input path="fondsNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案卷目录号：</label>
			<div class="controls">
				<form:input path="fileNumber" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案卷目录名称：</label>
			<div class="controls">
				<form:input path="fileName" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">案卷张数：</label>
			<div class="controls">
				<form:input path="fileSheets" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目录中案卷起止号：</label>
			<div class="controls">
				<form:input path="fileEndash" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">存放位置：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="fonds:tFiling:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> #3466-->
		</div>
	</form:form>
</body>
</html>