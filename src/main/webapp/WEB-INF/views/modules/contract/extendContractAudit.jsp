<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>展期申请审核</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			form.submit();
		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li>
		<shiro:hasPermission name="contract:tLoanContract:edit"><li><a href="${ctx}/contract/tLoanContract/form">业务办理添加</a></li></shiro:hasPermission>
	</ul>
	
	<br/>
	<form:form id="inputForm" modelAttribute="extendContract" action="${ctx}/contract/tLoanContract/complete" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户：</label>
				<div class="controls">
					${tLoanContract.customerName}
				</div>
			<div class="controls">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">您的意见${tLoanContract.act.status}：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="contract:tLoanContract:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
	</form:form>
</body>
</html>