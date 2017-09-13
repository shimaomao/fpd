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
      		
        	/* var endLastPayDate= $("#p_beginLastPayDate").val();
        	console.info("开始还款月:"+beginLastPayDate);
        	if(endLastPayDate== "" || endLastPayDate== undefined || endLastPayDate== null){
        		alertx("开始还款月");
        	}else if(endLastPayDate== "" || endLastPayDate== undefined || endLastPayDate== null){
        		alertx("结束还款月");
        	}
        	else{ */
       		var url = "${ctx}/statistics/loanContract/loanDatail";
			location.href = url;        	
        	/* } */
        	
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
			<li><label>开始月份：</label>
				<input id="p_beginLastPayDate" name="beginLastPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.beginLastPayDate}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</li>
			<li><label>结束月份：</label>
				<input id="p_endLastPayDate" name="endLastPayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.endLastPayDate}" pattern="yyyy-MM"/>"
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
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
											&nbsp;&nbsp;<a class="btn btn-primary" onclick="toDownload();">导出</a></li>
			<li class="clearfix"></li>
		</ul>
		
		<!--添加一个导入按钮    但是没有做处理-->
	<%-- <form action="${ctx}/excelUpload/ExcelUpload/upload" enctype="multipart/form-data" method="post">    
                        上传excel：<input type="file" name="file"><br/>
         <input type="submit" value="提交">
     </form> --%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" style="border-collapse:inherit;text-align:center;line-height:1.5em;" border="1" cellpadding="1" cellspacing="1" >
			<tr>
				<td style="width:32px;text-align:center;">序号</td>
				<!--2-->
				<td style="width:150px;">业务编号</td>
				<td style="width:80px;">放款日期</td>
				<td style="width:80px;">所属部门</td>
				<td style="width:60px;">客户经理</td>
				<!--5-->
				<td style="width:60px;">客户</td>
				<!-- <td style="width:100px;">借款主体</td> -->
				<td style="width:60px;">贷款本金</td>
				<td style="width:60px;">产品类型</td>
				<!-- 8 -->
				<td style="width:120px;">贷款用途</td>
				<!-- <td style="width:60px;">是否涉农</td> -->
				<td style="width:60px;">贷款期限(月)</td>
				<td style="width:60px;">年贷款利率</td>
				<!--12 -->
				<td style="width:60px;">期初未还本金余额</td>
				<td style="width:60px;">期间提前还本金</td>
				<td style="width:60px;">期间应收本金</td>
				<td style="width:60px;">期间应收利息</td>
				<!--16 -->
				<!-- 放款日期   那个月  才显示 -->
				<%-- <c:if test="${loanContract.beginLastPayDate < loanContract.loanDate and loanContract.loanDate <loanContract.endLastPayDate  }"> --%>
					<td style="width:60px;">前期服务费<br>(本月应收)</td>
					<td style="width:60px;">管理费<br>(本月应收)</td>
				<%-- </c:if> --%>
				<!--18 -->
				<!-- 逾期才显示 -->
				<td style="width:60px;">罚息<br>(本月应收)</td>
				<td style="width:60px;">逾期违约金<br>(本月应收)</td>
				
				
				<!-- 19,20 -->
				<td style="width:60px;">期末应收本息合计</td>
				<td style="width:60px;">期末未还本金余额</td>
				<td style="width:60px;">当前已收本金</td>
				<td style="width:60px;">当前已收本和息</td>
				<td style="width:60px;">是否已结清贷款</td>
				<td style="width:60px;">结清状态</td>
				<td style="width:60px;">客户地区</td>
			</tr>
		<c:forEach items="${list}" var="loanContract">
			<tr>
				<td>${loanContract.serial}</td>
				<!--2-->
				<td>${loanContract.contractNumber}</td>
				<td><fmt:formatDate value="${loanContract.loanDate}" pattern="yyyy-MM-dd"/></td>
				<td>${loanContract.createOfficeName}</td>
				<td>${loanContract.createName}</td>
				<!--5-->
				<td>${loanContract.customerName}</td>
				<%-- <td>${fns:getDictLabel(loanContract.borrower, 'borrower', '')}</td> --%>
				<td>${loanContract.loanAmount}</td>
				<td>${loanContract.productNname}</td>
				<!-- 8 -->
				<td>${fns:getDictLabel(loanContract.purposeId, 'product_purpose', '')}</td>
				<%-- <td>${fns:getDictLabel(loanContract.agriculture, 'yes_no', '')}</td> --%>
				<td>${loanContract.loanPeriod}</td>
				<td>${loanContract.loanRate}</td>
				<!-- 12 -->
				<td>${loanContract.needPrincipalMoney}</td>
				<td>${loanContract.adviceBackPrincipalMoney}</td>
				<td>${loanContract.currentPrincipalMoney}</td>
				<td>${loanContract.currentInterestMoney}</td>
				<!--16 -->
				<!-- 放款日期   那个月  才显示 -->
				<%-- <c:if test="${loanContract.beginLastPayDate < loanContract.loanDate and loanContract.loanDate <loanContract.endLastPayDate  }"> --%>
					<td>${loanContract.feeService}</td>
					<td>${loanContract.feeManage}</td>
				<%-- </c:if> --%>
				<!--18 -->
				<!-- 逾期才显示 -->
				<td>${loanContract.latePenalty}</td>
				<td>${loanContract.lateFee}</td>
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
	</table><br><br><br><br><br>
	<div class="pagination">${page}</div>
</body>
</html>