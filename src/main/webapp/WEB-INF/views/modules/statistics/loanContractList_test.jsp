<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>贷款明细查询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        };
        
      	//导出文件
        function toDownload(){
        	var currentMonth = $("#p_currentMonth").val();
        	console.info("当期月份:"+currentMonth);
        	if(currentMonth == "" || currentMonth == undefined || currentMonth == null){
        		alertx("请填写当期月份");
        	}else{
        		window.open("${ctx}/statistics/loanContract/loanDatail?currentMonth="+currentMonth, "_blank");
        	}
        	
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/statistics/loanContract/">贷款明细</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="loanContract" action="${ctx}/statistics/loanContract/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>当期月份：</label>
				<input id="p_currentMonth" name="currentMonth" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.currentMonth}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<%-- <li><label>放款日期：</label>
				<input name="beginLoanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.beginLoanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endLoanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.endLoanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>贷款期限：</label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>还本金日期：</label>
				<input name="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.payPrincipalDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>创建用户：</label>
				<form:input path="createBy.id" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;<a class="btn btn-primary" onclick="toDownload();">导出</a></li>
			<li class="clearfix"></li>
		</ul>

	</form:form>
	<sys:message content="${message}"/>

	<table id="contentTable" class="text-center" border="1" cellpadding="1" cellspacing="1" >
		<thead>
			<tr>
				<th width="32px;">序号</th>
				<th width="130px;">业务编号</th>
				<th width="60px;">放款日期</th>
				<th width="80px;">所属部门</th>
				<th width="60px;">客户经理</th>
				<th width="60px;">客户</th>
				<th width="100px;">借款主体</th>
				<th width="60px;">贷款本金</th>
				<!-- 8 -->
				<th width="120px;">贷款用途</th>
				<th width="60px;">是否涉农</th>
				<th width="60px;">贷款期限(月)</th>
				<th width="60px;">贷款利率</th>
				
				<th width="60px;">本月期初贷款余额</th>
				<th width="60px;">本月提前还本金</th>
				<th width="60px;">本月应收本金</th>
				<th width="60px;">本月应收利息</th>
				<th width="60px;">前期服务费<br>(本月应收)</th>
				<th width="60px;">管理费<br>(本月应收)</th>
				<!-- 19,20 -->
				<th width="60px;">本月应收利息</th>
				<th width="60px;">本月末贷款余额</th>
				<th width="60px;">当前已收本金</th>
				<th width="60px;">当前已收业务收入</th>
				<th width="60px;">是否已结清贷款</th>
				<th width="60px;">结清状态</th>
				<th width="60px;">客户地区</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr>
				<td>${loanContract.serial}</td>
				<td>${loanContract.contractNumber}</td>
				<td><fmt:formatDate value="${loanContract.loanDate}" pattern="yyyy-MM-dd"/></td>
				<td>${loanContract.createOfficeName}</td>
				<td>${loanContract.createName}</td>
				<td>${loanContract.customerName}</td>
				<td>${fns:getDictLabel(loanContract.borrower, 'borrower', '')}</td>
				<td>${loanContract.loanAmount}</td>
				<!-- 8 -->
				<td>${fns:getDictLabel(loanContract.purposeId, 'product_purpose', '')}</td>
				<td>${fns:getDictLabel(loanContract.agriculture, 'yes_no', '')}</td>
				<td>${loanContract.loanPeriod}</td>
				<td>${loanContract.loanRate}</td>
				
				<td>${loanContract.needPrincipalMoney}</td>
				<td>${loanContract.currentBackPrincipalMoney}</td>
				<td>${loanContract.currentPrincipalMoney}</td>
				<td>${loanContract.currentInterestMoney}</td>
				<td>${loanContract.feeService}</td>
				<td>${loanContract.feeManage}</td>
				<!-- 19,20 -->
				<td>${loanContract.currentNeedIntePriMoney}</td>
				<td>${loanContract.preiodEndNeedRepay}</td>
				<td>${loanContract.backPrincipalMoney}</td>
				<td>${loanContract.currentRepay}</td>
				<td>${fns:getDictLabel(loanContract.isSquare, 'yes_no', '')}</td>
				<td>${loanContract.squareType}</td>
				<td>${loanContract.areaName}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>