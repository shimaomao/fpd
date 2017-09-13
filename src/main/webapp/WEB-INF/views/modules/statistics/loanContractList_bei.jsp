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
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务编号</th>
				<th>客户姓名</th>
				<th>贷款金额</th>
				<th>贷款利率</th>
				<th>利息总计</th>
				<th>已还本金</th>
				<th>已还利息</th>
				<th>未还本金</th>
				<th>未还利息</th>
				<th>当期已还本金</th>
				<th>当期已还利息</th>
				<th>当期未还本金</th>
				<th>当期未还利息</th>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr>
				<td>
					${loanContract.contractNumber}
				</td>
				<td>
					${loanContract.customerName}
				</td>
				<td>
					${loanContract.loanAmount}
				</td>
				<td>
					${loanContract.loanRate}%
				</td>
				<td>
					${loanContract.backInterestTotal}
				</td>
				<td>
					${loanContract.backPrincipalMoney}
				</td>
				<td>
					${loanContract.backInterestMoney}
				</td>
				<td>
					${loanContract.needPrincipalMoney}
				</td>
				<td>
					${loanContract.needInterestMoney}
				</td>
				
				<td>
					${loanContract.currentPrincipalMoney}
				</td>
				<td>
					${loanContract.currentInterestMoney}
				</td>
				<td>
					${loanContract.currentBackPrincipalMoney}
				</td>
				<td>
					${loanContract.currentBackInterestMoney}
				</td>
				<%-- <td>
    				<a href="${ctx}/statistics/loanContract/detail?id=${loanContract.id}">查看详情</a>
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<%-- <div class="pagination">${page}</div> --%>
</body>
</html>