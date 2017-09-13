<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>creditResult管理</title>
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
		<li><a href="${ctx}/credit/cifQhValidate/">征信查询</a></li>
		<li class="active"><a href="${ctx}/credit/cifQhValidate/form?id=${cifQhValidate.id}">creditResult<shiro:hasPermission name="credit:cifQhValidate:edit">${not empty cifQhValidate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:cifQhValidate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>


	
	<form:form id="inputForm" modelAttribute="cifQhValidate" action="${ctx}/credit/cifQhValidate/searchCredit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="code" value="${code}" />
		<form:hidden path="searchCode" htmlEscape="false" maxlength="64" class="input-xlarge "/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="idNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="idType" class="input-xlarge " id="pouseEducation">
					<form:options items="${fns:getDictList('idType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行卡号：</label>
			<div class="controls">
				<form:input path="cardNo" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="cardNo" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期年(YYYY)：</label>
			<div class="controls">
				<form:input path="expiresYear" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期月(MM)：</label>
			<div class="controls">
				<form:input path="expiresMonth" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">鉴权类型：</label>
			<div class="controls">
				<form:select path="authType" class="input-xlarge " id="pouseEducation">
					<form:options items="${fns:getDictList('authType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原因代码：</label>
			<div class="controls">
				<form:select path="reasonCode" class="input-xlarge " id="pouseEducation">
					<form:options items="${fns:getDictList('reasonCode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡主授权代码：</label>
			<div class="controls">
				<form:input path="entityAuthCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">卡主授权时间：</label>
			<div class="controls">
				<form:input path="entityAuthDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:cifQhValidate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="征信查询"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>