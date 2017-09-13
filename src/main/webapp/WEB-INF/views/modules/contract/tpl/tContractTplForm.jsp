<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同模板管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
		$(document).ready(function() {
			var HTCkeditor = CKEDITOR.replace("ftlContent");
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
                    form.submit();
				},
				
				/* {allowedContent: true} */
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
		<li><a href="${ctx}/contract/tpl/tContractTpl/">合同模板列表</a></li>
		<li class="active"><a href="${ctx}/contract/tpl/tContractTpl/form?id=${tContractTpl.id}">合同模板<shiro:hasPermission name="contract:tpl:tContractTpl:edit">${not empty tContractTpl.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="contract:tpl:tContractTpl:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tContractTpl" action="${ctx}/contract/tpl/tContractTpl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div class="control-group">
			<label class="control-label">表单ID：</label>
			<div class="controls">
				<form:input path="formId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单名：</label>
			<div class="controls">
				<form:input path="formName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建名：</label>
			<div class="controls">
				<form:input path="createName" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('contract_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="ftlStatus" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('contract_ftlStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本：</label>
			<div class="controls">
				<form:select path="ftlVersion" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('contract_ftlVersion')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上传word布局模板的URL：</label>
			<div class="controls">
				<form:input path="ftlWordUrl" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户ID：</label>
			<div class="controls">
				<form:input path="organId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容描述：</label>
			<div class="controls">
				<textarea id="ftlContent" name="ftlContent" rows="4" maxlength="200" class="input-xxlarge" >${tContractTpl.ftlContent }</textarea>
				<%-- <sys:ckeditor replace="ftlContent" uploadPath="/ftlContent/images" /> --%>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="contract:tpl:tContractTpl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>