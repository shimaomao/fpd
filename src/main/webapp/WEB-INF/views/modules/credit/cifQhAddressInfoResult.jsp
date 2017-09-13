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
	
	
	地址验证查询：

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
			<label class="control-label">省：</label>
			<div class="controls">
				<form:input path="province" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市：</label>
			<div class="controls">
				<form:input path="cityInfo" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">区：</label>
			<div class="controls">
				<form:input path="borough" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">格式化地区：</label>
			<div class="controls">
				<form:input path="fmtAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经度：</label>
			<div class="controls">
				<form:input path="longitude" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">纬度：</label>
			<div class="controls">
				<form:input path="latitude" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼盘名称：</label>
			<div class="controls">
				<form:input path="buildingName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼盘地址：</label>
			<div class="controls">
				<form:input path="buildingAddress" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼盘周边均价：</label>
			<div class="controls">
				<form:input path="houseArodAvgPrice" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">楼盘均价：</label>
			<div class="controls">
				<form:input path="houseAvgPrice" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">查询数据状态：</label>
			<div class="controls">
				<form:input path="state" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑类型：</label>
			<div class="controls">
				<form:input path="buildingType" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址属性：</label>
			<div class="controls">
				<form:input path="addressPorperty" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否真实：</label>
			<form:select path="isMatch" class="input-xlarge " id="pouseEducation">
				<form:options items="${fns:getDictList('isRealIdentity')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</form:select>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:cifQhValidate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="征信查询"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>