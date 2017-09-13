<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>放款记录管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		
		function importExcel(){
			if (confirm("您要导出台账?")){
			    var taiZhangYueFen = $("#taiZhangYueFen").val();	    	    
				var url = "${ctx}/lending/tLending/excelPlan?taiZhangYueFen="+taiZhangYueFen;
				location.href = url;
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/lending/tLending/ledgerList">台账记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tLending" action="${ctx}/lending/tLending/ledgerList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>月份：</label>
				<input name="contract.searchtime" id="taiZhangYueFen" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" value="${tLending.contract.searchtime}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table class="table table-bordered">
		<tr><td>
			<input class="btn btn-primary" type="button" onclick="importExcel();" value="导出"/>
		</td></tr>
    </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>合同编号</th>
				<th>客户名称</th>
				<th>客户类型</th>
				<th>贷款日期</th>
				<th>贷款金额</th>
				<th>贷款期限</th>
				<th>贷款利率</th>
				<th>贷款到期日</th>
				<th>法定代表人</th>
				<th>电话</th>
				<th>已还本金</th>
				<th>已还利息</th>
				<th>最近还款日</th>
				<th>合同状态</th>
				<th>备注</th>
			</tr>			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contract" varStatus="index">
			<tr>
				<td>${index.count}</td>
				<td>${contract.contractNumber}</td>
				<td>${contract.customerName}</td>
				<td>${fns:getDictLabel(contract.customerType, 'customer_type', '')}</td>
				<td><fmt:formatDate value="${contract.applyDate}" pattern="yyyy-MM-dd"/></td>
				<td>${contract.loanAmount}</td>
				<td>${contract.loanPeriod}(${fns:getDictLabel(contract.periodType, 'period_type', '')})</td>
				<td>${contract.loanRate}(${contract.loanRateType}利率)</td>
				<td><fmt:formatDate value="${contract.payPrincipalDate}" pattern="yyyy-MM-dd"/></td>
				
                   <!--  企业1  个人2  -->
				   <c:if test="${contract.customerType==1}">
				     <td> ${contract.legalPerson} </td>
				      <td> ${contract.suretyTelephone}</td>    
				   </c:if>
				    <c:if test="${contract.customerType==2}">
				       <td>${contract.customerName}</td>
				         <td>${contract.linkphone}  </td>
				   </c:if>
				
				<td>${contract.backmoney+contract.advanceamount}</td>
				<td>${contract.backlixi}</td>
				<td><fmt:formatDate value="${contract.backtime}" pattern="yyyy-MM-dd"/></td>
				<td>
					${fns:getDictLabel(contract.status, 'loan_contract_status', '')} 
				</td>
				<td>${contract.remarks}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>