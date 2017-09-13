<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<html>
<head>
	<title>还款提醒</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
  <script type="text/javascript" src="${ctxStatic}/util.js"></script>
  <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=1"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
    	return false;
    }
		 
		
		/***风险控制***/
		function riskControl(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		  
		  var url = "${ctx}/contract/tLoanContract/toRiskControl?loanContractId="+array[0];
		  location.href = url;
		}
		
		
		
		/***还款提醒***/
		function warn(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		  var url = "${ctx}/index/todo/repaywarn?id="+array[0];
		  location.href = url;
		}
		
		
	</script>
</head>
<body>
     <div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">业务中心&gt;</a>
				<a href="#">我的业务&gt;</a>
				<a href="#">贷后管理&gt;</a>
				<a href="#">还款提醒</a>
			</div>
		</div>
	</div>
	<form:form id="searchForm" modelAttribute="tRepayPlan.tLoanContract" action="${ctx}/contract/tLoanContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<!-- 		<ul class="ul-form"> -->
<!-- 			<li><label>合同编号：</label> -->
<%-- 				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li><label>贷款金额：</label> -->
<%-- 				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li><label>贷款期限：</label> -->
<%-- 				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
<!-- 			<li class="clearfix"></li> -->
<!-- 		</ul> -->
	</form:form>

  	<shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr><td>
			    <a class="btn btn-primary" onclick="warn();" title="短信记录在我的桌面-消息管理-短信记录">还款提醒</a>
<!-- 				<a class="btn btn-primary" onclick="toUpdate();">查看明细</a> -->
			</td></tr>
	  </table>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>贷款金额（元）</th>
				<th>贷款期限</th>
				<th>还款日期</th>
				<th>还款金额(本金+利息)</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="plan">
		    <c:set var="tLoanContract" value="${plan.loanContract}"/>
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}">
					${tLoanContract.contractNumber}
				</a>
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					 ${tLoanContract.loanPeriod}(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
				</td>
				<td>
				   ${plan.accountDate}
				</td>
				<td>
				   ${plan.principal+plan.interest}
				</td>
				<td>
					${fns:getDictLabel(plan.status, 'repay_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>