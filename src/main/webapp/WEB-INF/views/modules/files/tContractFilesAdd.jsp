<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>

<html>
<head>
	<title>意向调查管理</title>
	<meta name="decorator" content="default"/>
		<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
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
			 $("#filelist").load("${ctx}/files/tContractFiles/showfilelist/${tContractFiles.taskId}.html?height=350&businesstype=<%=FileType.CUSTOMER_ARCHIVES%>&oper=edit&nid=${nid}file");
		});
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="tContractFiles" action="${ctx}/files/tContractFiles/toSave" method="post" class="form-horizontal">
		<form:input path="taskId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tContractFiles.taskId}"/>
		<form:input path="type"   htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tContractFiles.type}"/>
	     <div class="control-group" style="height: 500px;">
		   <label class="control-label">附件：</label>
			<div id="filelist" style="height: 550px;"></div>
		</div>
		
	<div class="form-actions">
		<shiro:hasPermission name="customerintent:tCustomerIntent:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
	</div>
		
	</form:form>
</body>
</html>