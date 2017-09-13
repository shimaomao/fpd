<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>催收记录管理</title>
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
		<li><a href="${ctx}/hastenrepay/hastenRepayRecord?contractId=${hastenRepayRecord.contractId}">催收记录列表</a></li>
		<li class="active"><a href="${ctx}/hastenrepay/hastenRepayRecord/form?contractId=${hastenRepayRecord.contractId}&id=${hastenRepayRecord.id}">催收记录<shiro:hasPermission name="hastenrepay:hastenRepayRecord:edit">${not empty hastenRepayRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="hastenrepay:hastenRepayRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul> 
	<br/>
	<br/>
	<form:form id="inputForm" modelAttribute="hastenRepayRecord" action="${ctx}/hastenrepay/hastenRepayRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="contractId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">借款人：</label>
			<div class="controls">
				<form:input path="borrower" htmlEscape="false" maxlength="20" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="contact" htmlEscape="false" maxlength="20" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">催收时间：</label>
			<div class="controls">
				<input name="hastenDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${hastenRepayRecord.hastenDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">催收说明：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="8" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="hastenrepay:hastenRepayRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>