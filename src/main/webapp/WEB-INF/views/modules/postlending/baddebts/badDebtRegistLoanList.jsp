<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>坏账处理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//查看明细
		function tobdDetail(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			
			location.href = "${ctx}/postlending/baddebts/badDebtRegist/detail?loanContractId="+cid[0];
		}
		
		//坏账处理申请
		function toApplyDeal(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/postlending/baddebts/badDebtRegist/getAuditStatus?cid="+cid,
		         	success: function(data){//data要收押物品的数量
		         		if(data){
		         			location.href = "${ctx}/postlending/baddebts/badDebtRegist/form?loanContractId="+cid[0];
		         		}else{
		         			alert("本合同状态为已核销状态,不可再提交审批");
		         		}
		         	}
		       });
			
		
		}
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/postlending/baddebts/badDebtRegist/list">坏账核销</a></li>
	</ul>
    <br><br>
   <%-- 	<shiro:hasPermission name="refund:reimburse:edit"> --%>
   	<table class="table table-bordered">
   	   <tr>
   	      <td>
			<a  class="btn btn-primary"  onclick="toApplyDeal();">坏账申报</a>
			<a  class="btn btn-primary"  onclick="tobdDetail();">查看明细</a>
   	   	  </td>
   	   </tr>
   	</table>
	<%-- </shiro:hasPermission> --%>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>借款人</th>
				<th>贷款金额（元）</th>
				<th>贷款利率（%）</th>
				<th>贷款期限</th>
				<th>申请日期</th>
				<th>状态</th>
				<th>五级分类</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr>
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${loanContract.id}"/>
				</td>
				<td ><a href="${ctx}/contract/tLoanContract/detail?id=${loanContract.id}">
					${loanContract.contractNumber}
					</a>
				</td>
				<td>
					${loanContract.customerName}
				</td>
				<td>
					${loanContract.loanAmount}
				</td>
				<td>
					${loanContract.loanRate}(${loanContract.loanRateType})
				</td>
				<td>
					${loanContract.loanPeriod}
				</td>
				<td>
					<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(loanContract.status, 'loan_contract_status', '')}
				</td>
				<td>
				    ${fns:getDictLabel(loanContract.fiveLevel, 'five_level', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<br/>
</body>
</html>