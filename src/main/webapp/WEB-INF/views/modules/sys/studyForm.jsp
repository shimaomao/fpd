<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习经历管理</title>
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
		<%-- <li><a href="${ctx}/sys/study/">学习经历列表</a></li> --%>
		<li class="active"><a href="${ctx}/sys/study/form?id=${study.id}">学习经历<shiro:hasPermission name="sys:study:edit">${not empty study.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:study:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="study" action="${ctx}/sys/study/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="employId"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">学习时间起：</label>
			<div class="controls">
					<form:input path="studyDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学习时间止：</label>
			<div class="controls">
				<form:input path="studyEndDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">
				<form:input path="school" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				<form:input path="professional" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:input path="job" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">报备日期：</label>
			<div class="controls">
				<form:input path="signDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:study:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
	</form:form>
</body>
</html>