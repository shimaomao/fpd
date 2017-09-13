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
	
	好信欺诈度提示征信查询结果：
	
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
					<form:options items="${fns:getDictList('id_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<label class="control-label">是否命中第三方标注黑名单：</label>
			<form:select path="isMachdBlMakt" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中骚扰电话：</label>
			<form:select path="isMachCraCall" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中欺诈号码：</label>
			<form:select path="isMachFraud" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中空号（非正常短信语音号码）：</label>
			<form:select path="isMachEmpty" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中收码平台号码：</label>
			<form:select path="isMachYZmobile" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中小号：</label>
			<form:select path="isMachSmallNo" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">是否命中社工库号码：</label>
			<form:select path="isMachSzNo" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="control-group">
			<label class="control-label">返回信息：</label>
			<div class="controls">
				<form:input path="erMsg" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码风险描述：</label>
			<div class="controls">
				<form:input path="mRskDesc" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="credit:cifQhValidate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="征信查询"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>