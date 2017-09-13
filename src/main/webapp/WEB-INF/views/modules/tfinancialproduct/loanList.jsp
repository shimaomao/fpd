<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>业务办理管理</title>
	<meta name="decorator" content="default"/>
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
		
		
		
		function checkmy(){
			 var array = getCheckValue("loanContractId");
			 $("#loancontractIds").val(array);
		}
		 
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/tfinancialproduct/tFinancialProduct/getLoanRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="loancontractIds" name="loancontractIds" type="hidden" value="${contractIds}"/>
		<ul class="ul-form">
			<li><label>合同编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label> 
			       <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额 ：
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款金额 ：
	                </c:if>
	            </label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label> 
			        <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限：
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限：
	                </c:if>
	             </label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择/状态</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>产品名称</th>
				<th> 
				   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额  (元)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款金额 (元)
	                </c:if>	
	            </th>
				<th>
				    <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限
	                </c:if>
	           	</th>
				<th> 
				   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
		                                                         担保费率(%)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款利率(%)
	                </c:if>
	             </th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<th>合同状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr>
				<td>
			         <!-- 如果要检索的字符串值没有出现，则该方法返回 -1。 -->
			        <c:if test="${loancontractids.indexOf(tLoanContract.id) eq '-1'}">
					     <input type="checkbox" onclick="checkmy();"  name="loanContractId" id="loanContractId" value="${tLoanContract.id}"  <c:if test="${contractIds.indexOf(tLoanContract.id)!='-1'}">checked="checked"</c:if>
					     />
				    </c:if>
				    <c:if test="${loancontractids.indexOf(tLoanContract.id) ne '-1'}">
				    	已转让
				    </c:if>
				    
				</td>
				<td>
				     ${tLoanContract.contractNumber}
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					${tLoanContract.productname}
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					${tLoanContract.loanPeriod}
					(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
				</td>
				<td>
					${tLoanContract.loanRate}(${tLoanContract.loanRateType})
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>