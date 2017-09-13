<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义表单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmitNew").click(function(){
				var form = $("#inputForm");
				form.attr("action",form.attr("action")+"?isNew=true")
				form.submit();
			});
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
		<li><a href="${ctx}/form/tpl/dfFormTpl/">自定义表单列表</a></li>
		<li class="active"><a href="${ctx}/form/tpl/dfFormTpl/form?id=${dfFormTpl.id}">表单修改<shiro:hasPermission name="form:tpl:dfFormTpl:edit">${not empty dfFormTpl.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="form:tpl:dfFormTpl:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="dfFormTpl" action="${ctx}/form/tpl/dfFormTpl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">表单名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单名称：</label>
			<div class="controls">
				<form:input path="sname" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">表类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('df_table_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模块：</label>
			<div class="controls">
				<form:select path="model" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('df_model')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">子模块：</label>
			<div class="controls">
				<form:select path="modsub" class="input-xlarge ">
					<form:option value="" label=""/>
					<c:if test="${dfFormTpl.model eq 'business_application' }">
						<form:options items="${fns:getDictList('df_model_bapplication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</c:if>
					<c:if test="${dfFormTpl.model eq 'product' }">
						<form:options items="${fns:getDictList('df_model_product')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</c:if>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${dfFormTpl.office.id}" labelName="office.name" labelValue="${dfFormTpl.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><input id="btnSubmitNew" class="btn btn-primary" type="button" value="保存新模板"/>&nbsp;</shiro:hasPermission>
			<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>