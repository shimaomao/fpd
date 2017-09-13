<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理管理</title>
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
		<li><a href="${ctx}/sms/tMsgSwitch/">消息管理列表</a></li>
		<li class="active"><a href="${ctx}/sms/tMsgSwitch/form?id=${tMsgSwitch.id}">消息管理<shiro:hasPermission name="sms:tMsgSwitch:edit">${not empty tMsgSwitch.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sms:tMsgSwitch:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tMsgSwitch" action="${ctx}/sms/tMsgSwitch/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">开关业务节点名称：</label>
			<div class="controls">
				<form:input path="businessName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">站内信开关状态：</label>
			<div class="controls">
				<form:select path="letterStatus" class="input-xlarge ">
					<form:option value="0" label="关" />
					<form:option value="1" label="开" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营销开关状态：</label>
			<div class="controls">
				<form:select path="marketStatus" class="input-xlarge ">
					<form:option value="0" label="关" />
					<form:option value="1" label="开" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮件开关状态：</label>
			<div class="controls">
				<form:select path="mailStatus" class="input-xlarge ">
					<form:option value="0" label="关" />
					<form:option value="1" label="开" />
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sms:tMsgSwitch:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>