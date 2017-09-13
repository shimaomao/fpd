<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="nid" value="looploanform" />
<html>
<head>
	<title>授信信息管理</title>
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
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${tLoopLoan.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_1%>&oper=edit&nid=${nid}file";
		    $("#${nid}filelist").load(url); --%>
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/looploan/tLoopLoan/">授信信息列表</a></li>
		<li class="active"><a href="${ctx}/looploan/tLoopLoan/form?id=${tLoopLoan.id}">授信信息<shiro:hasPermission name="looploan:tLoopLoan:edit">${(not empty tLoopLoan.id && fn:contains(tLoopLoan.id, 'new_') == false ) ? '修改' : '添加'}</shiro:hasPermission><shiro:lacksPermission name="looploan:tLoopLoan:edit">查看</shiro:lacksPermission></a></li>
	</ul>
<%--      <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>授信管理>编辑
	   </div>
	</div>
	<br/>
	<br/> --%>
	<form:form id="inputForm" modelAttribute="tLoopLoan" action="${ctx}/looploan/tLoopLoan/save" method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户：</label>
				<div class="controls">
					<sys:treeselect id="customer" name="customerId" value="${tLoopLoan.customerId}" labelName="customerName" labelValue="${tLoopLoan.customerName}" parentName="customerType"
						title="客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				</div>
			<div class="controls">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">申请日期：</label>
			<div class="controls">
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoopLoan.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="cardNum" htmlEscape="false" maxlength="25" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">授信金额：</label>
			<div class="controls">
				<form:input path="creditPrice" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信编号：</label>
			<div class="controls">
				<form:input path="loopNumber" htmlEscape="false" maxlength="50" class="input-xlarge " /><font color="red">自动生成</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信期限：</label>
			<div class="controls">
				<form:input path="period" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
				<font color="red">*</font>
				<form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信利率：</label>
			<div class="controls">
				<form:input path="floatRate" htmlEscape="false" class="input-xlarge  number"/>%
				<font color="red">*</font>
				<form:radiobuttons path="loanRateType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款方式：</label>
			<div class="controls">
				<form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onchange="loanTypeChange()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:select path="payType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投向行业：</label>
			<div class="controls">
				<form:select path="industry" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款区域：</label>
			<div class="controls">
				<sys:treeselect id="area" name="" value="" labelName="area" labelValue="${tLoopLoan.area}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
				<form:select path="purpose" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		
		<!-- 引用不同贷款方式对应的表单信息 -->
		<jsp:include page="/WEB-INF/views/common/loanTypeInfo.jsp">
			<jsp:param name="loanTypeInputName" value="loanTypeList"/>
			<jsp:param name="nid" value="${nid}"/>
			<jsp:param name="height" value="80"/>
			<jsp:param name="businessTable" value="t_loop_loan"/>
			<jsp:param name="businessId" value="${tLoopLoan.id}"/>
			<jsp:param name="oper" value="edit"/>
		</jsp:include>

		<div class="form-actions">
			<shiro:hasPermission name="looploan:tLoopLoan:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		
	</form:form>
</body>
</html>