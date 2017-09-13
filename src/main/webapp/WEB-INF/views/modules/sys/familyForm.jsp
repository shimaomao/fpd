<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>从业人员家庭成员管理</title>
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
		<%-- <li><a href="${ctx}/sys/family/">从业人员家庭成员列表</a></li> --%>
		<li class="active"><a href="${ctx}/sys/family/form?id=${family.id}">从业人员家庭成员<shiro:hasPermission name="sys:family:edit">${not empty family.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:family:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="family" action="${ctx}/sys/family/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="employId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">关系：</label>
			<div class="controls">
				<select id="type" name="type" class="required select2-offscreen">
					<option value="配偶" <c:if test="${'配偶' == family.type}">selected</c:if> >配偶</option>
					<option value="子女" <c:if test="${'子女' == family.type}">selected</c:if> >子女</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<select id="sex" name="sex" class="required select2-offscreen">
					<option value="男" <c:if test="${'男' == family.sex}">selected</c:if> >男</option>
					<option value="女" <c:if test="${'女' == family.sex}">selected</c:if> >女</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现工作或学习单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职务：</label>
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
			<shiro:hasPermission name="sys:family:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
	</form:form>
</body>
</html>