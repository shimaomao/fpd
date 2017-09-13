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
			
			var str = '${tCustomerIntent.guaranteeMode}';
            var chk_value =[];   
			$("input[name='guaranteeMode']").each(function(){    
				   chk_value.push($(this).val()); 
				   if(str.indexOf($(this).val())>=0){
					    document.getElementById($(this).attr("id")).checked = true;
				   }
			});  
			
			
			if('${tCustomerIntent.employeeId}'==''){
				   $("#filelist").load("${ctx}/files/tContractFiles/showfilelist/${tCustomerIntent.companyId}.html?height=150&businesstype=<%=FileType.CUSTOMER_INTENT%>&oper=edit&nid=${nid}file");
			}else{
				  $("#filelist").load("${ctx}/files/tContractFiles/showfilelist/${tCustomerIntent.employeeId}.html?height=150&businesstype=<%=FileType.CUSTOMER_INTENT%>&oper=edit&nid=${nid}file");
			}
		});
	</script>
</head>
<body>
	<br/>
	<form:form id="inputForm" modelAttribute="tCustomerIntent" action="${ctx}/customerintent/tCustomerIntent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:input path="companyId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tCustomerIntent.companyId}"/>
		<form:input path="employeeId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tCustomerIntent.employeeId}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge " value="${name}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="20" class="input-xlarge " value="${Mobile}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品类型：</label>
			<div class="controls">
				<form:input path="productId" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select path="repaymentMode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款额度：</label>
			<div class="controls">
				<form:input path="loanLimit" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款期数：</label>
			<div class="controls">
				<form:input path="repaymentPeriod" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年利率：</label>
			<div class="controls">
				<form:input path="annualRate" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期年利率：</label>
			<div class="controls">
				<form:input path="overdueAnnualRate" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
				<form:select path="application" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_purpose_new')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保方式：</label>
			<div class="controls">
				<form:checkboxes path="guaranteeMode" items="${fns:getDictList('guarantWay')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onchange=""/>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开始时间：</label>
			<div class="controls">
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tCustomerIntent.startTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">结束时间：</label>
			<div class="controls">
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tCustomerIntent.endTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group" style="height: 300px;">
		   <label class="control-label">附件：</label>
			<div id="filelist" style="height: 250px;"></div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="customerintent:tCustomerIntent:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
<!-- 			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
		
	</form:form>
</body>
</html>