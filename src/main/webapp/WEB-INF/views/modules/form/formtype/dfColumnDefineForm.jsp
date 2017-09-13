<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字段库维护管理</title>
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
		<li><a href="${ctx}/form/formtype/dfColumnDefine/">字段库维护列表</a></li>
		<li class="active"><a href="${ctx}/form/formtype/dfColumnDefine/form?id=${dfColumnDefine.id}">字段库维护<shiro:hasPermission name="form:formtype:dfColumnDefine:edit">${not empty dfColumnDefine.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="form:formtype:dfColumnDefine:edit">查看</shiro:lacksPermission></a></li>
	</ul>
<%-- <div>
	<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		              产品中心>代码生成>字段库
	   </div>
	</div>--%>
	<br/> 
	<form:form id="inputForm" modelAttribute="dfColumnDefine" action="${ctx}/form/formtype/dfColumnDefine/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
         
		<div class="control-group">
			<label class="control-label">字段所属模块：</label>
			<div class="controls">
				<form:input path="category" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段所属模块名称：</label>
			<div class="controls">
				<form:input path="categoryname" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实体类属性名：</label>
			<div class="controls">
				<form:input path="filed" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段值显示属性名：</label>
			<div class="controls">
				<form:input path="filed2" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据表字段：</label>
			<div class="controls">
				<form:input path="colname" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段存储类型：</label>
			<div class="controls">
			    <form:input path="type" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段的表单类型：</label>
			<div class="controls">
					<form:select path="formtype" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('formtype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">字段的表单数据URL：</label>
			<div class="controls">
				<form:input path="formurl" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单数据格式1：</label>
			<div class="controls">
			    <form:input path="dataType" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			            标签的class
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单数据格式2：</label>
			<div class="controls">
			    <form:input path="dataType2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			           标签属性
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单控件的后缀：</label>
			<div class="controls">
			    <form:input path="suffix" htmlEscape="false" maxlength="50" class="input-large "/>
				如：元、年、月、日、%
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="form:formtype:dfColumnDefine:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>