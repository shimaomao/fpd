<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>业务办理管理</title>
<%-- 	<link href="${ctxStatic}/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css" rel="stylesheet" /> --%>
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
	
		function updateMoveStatus(loanContractId,moveStatus){
			
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/updateMoveStatus",
		         	data: {'loanContractId':loanContractId,"moveStatus":moveStatus},
		         	dataType: "json",
		         	success: function(data){
		         		if(data.status == '1' ){
		         			location.reload(true);
		         		 }else{
		         			showTip(data.message);
		         		} 
		         	}
		       }); 
			 
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/moveContractlist">提现订单转移列表</a></li>
		<%-- <shiro:hasPermission name="contract:tLoanContract:edit"><li><a href="${ctx}/contract/tLoanContract/form">业务办理添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
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
				<th>选择</th>
				<th>合同编号</th>
				<th>提现人姓名</th>
				<th>产品名称</th>
				<th> 
				   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额  (元)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 提现金额 (元)
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
				<th>发起机构</th>
				<th>转移对象</th>
				 <th>合同状态</th>
				<th>平台审核</th>
			  
				<!-- <th>操作</th> -->
				
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/moveDetail?id=${tLoanContract.id}">
					${tLoanContract.contractNumber}
				</a>
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
<%-- 					${(tLoanContract.loanPeriod == 1) ? '年' : ((tLoanContract.loanPeriod == 3) ? '日' : '个月')} --%>
				</td>
				<td>
					<%-- ${tLoanContract.loanRate}(${tLoanContract.loanRateType}) #3662--%>
					${tLoanContract.loanRate}%
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
				</td>
					<td>
					${tLoanContract.moveOffice.name}
				</td>
				<td>
					${tLoanContract.companyName}
				</td>
			
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td> 
				<td>
					   <c:if test="${tLoanContract.moveStatus eq '1'}">
					                待审核
					   </c:if>
					    <c:if test="${tLoanContract.moveStatus eq '2'}">
					               同意
					   </c:if>
					    <c:if test="${tLoanContract.moveStatus eq '3'}">
					                拒绝
					   </c:if>
				</td>
				<td>
				   	 <c:if test="${admin == true}">
				   	     <c:if test="${tLoanContract.moveStatus eq '1'}">
				           <button class="btn btn-small" type="button" onclick="updateMoveStatus('${tLoanContract.id}',2)">同意</button>
				           <button class="btn btn-small" type="button" onclick="updateMoveStatus('${tLoanContract.id}',3)">拒绝</button>
				         </c:if>
				        
				        <c:if test="${tLoanContract.moveStatus eq '2'}">
					        <button class="btn btn-small" type="button" onclick="updateMoveStatus('${tLoanContract.id}',1)">撤销</button>
					    </c:if>
				         
				        <c:if test="${tLoanContract.moveStatus eq '3'}">
				            <button class="btn btn-small" type="button" onclick="updateMoveStatus('${tLoanContract.id}',1)">撤销</button>
				        </c:if>
				         
				     </c:if>
				     <%-- <c:if test="${admin != true}">
				        <button class="btn btn-small" type="button" >发布贝通理财</button>
				    </c:if> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>