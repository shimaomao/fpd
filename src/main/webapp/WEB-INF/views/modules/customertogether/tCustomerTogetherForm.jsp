<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>共同借款管理</title>
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
		<li><a href="${ctx}/customertogether/tCustomerTogether/?mainEmployeeid=${tCustomerTogether.mainEmployeeid}&mainCompanyid=${tCustomerTogether.mainCompanyid}">共同借款列表</a></li>
		<li class="active"><a href="${ctx}/customertogether/tCustomerTogether/form?id=${tCustomerTogether.id}&mainEmployeeid=${tCustomerTogether.mainEmployeeid}&mainCompanyid=${tCustomerTogether.mainCompanyid}">共同借款<shiro:hasPermission name="customertogether:tCustomerTogether:edit">${not empty tCustomerTogether.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="customertogether:tCustomerTogether:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tCustomerTogether" action="${ctx}/customertogether/tCustomerTogether/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="mainEmployeeid" name="mainEmployeeid" type="hidden" value="${tCustomerTogether.mainEmployeeid}"/>
		<input id="mainCompanyid" name="mainCompanyid"  type="hidden" value="${tCustomerTogether.mainCompanyid}"/>
		<sys:message content="${message}"/>		
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">客户类型：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:select path="customerType" class="input-xlarge required"> --%>
<%-- 					<form:option value="" label=""/> --%>
<%-- 					<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 				</form:select> --%>
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">客户id：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="customerId" htmlEscape="false" maxlength="50" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">客户姓名：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="customerName" htmlEscape="false" maxlength="50" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->

	        <div class="control-group">
				<label class="control-label">客户名称：</label>
				<div class="controls">
					<sys:treeselect  id="customer" isAll="false" name="customerId" value="${tCustomerTogether.customerId}" labelName="customerName" labelValue="${tCustomerTogether.customerName}"
						parentName="customerType" parentValue="${tCustomerTogether.customerType}" title="客户" url="/company/tCompany/treeData" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>
		<div class="control-group">
			<label class="control-label">共同借款金额：</label>
			<div class="controls">
				<form:input path="togetherMoney" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单人借款金额：</label>
			<div class="controls">
				<form:input path="loanMoney" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="customertogether:tCustomerTogether:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>