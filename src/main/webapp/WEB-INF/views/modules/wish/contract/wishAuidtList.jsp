<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>业务办理管理</title>
<%-- 	<link href="${ctxStatic}/bootstrap/3.3.5/css/bootstrap.min.css" type="text/css" rel="stylesheet" /> --%>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/vow/contract_view.js?v=1"></script>
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function updateMoveStatus(loanContractId, moveStatus) {

		$.ajax({
			type : "POST",
			url : "${ctx}/contract/tLoanContract/updateMoveStatus",
			data : {
				'loanContractId' : loanContractId,
				"moveStatus" : moveStatus
			},
			dataType : "json",
			success : function(data) {
				if (data.status == '1') {
					location.reload(true);
				} else {
					showTip(data.message);
				}
			}
		});

	}
	function sel(value) {
		var selectedOption=value.options[value.selectedIndex];    
		if(selectedOption.value!=-1){
			var url = "${ctx}/wish/contract/wishContract/wishAuidtList?status="+selectedOption.value;
			location.href = url;
		}
		/* } */
	}

	//放款审批
	function loanApply(status) {
		var array = getCheckValue("loanContractId");
		if (array.length == 0) {
			showTip("请选择一条业务合同!");
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ctx}/wish/contract/wishContract/auditWishContract",
			data : {
				"contractId" : array[0],"status":status
			},
			dataType : "json",
			success : function(data) {
				alertx(data.msg);
				location.reload(true);
				//alertx(data);
			}
		});
	}
	
	
	//获取回款计划
	function getPlan(){
	   var array = getCheckValue("loanContractId");
	   if (array.length == 0) {
		    showTip("请选择一条业务合同!");
			return;
		}
		
	   $.ajax({
         	type: "POST",
         	url: "${ctx}/wish/contract/wishContract/operationMsg",
         	data : {
         		contractId : array[0]
			},
         	dataType: "json",
         	beforeSend:function(XMLHttpRequest){
                 //alert('远程调用开始...');
                 $("#loading").html("..............风控模型处理中..........请稍后................");
            },
         	success: function(data){
         		$("#loading").empty();
         		if(data.status=='1'){
         			showTip(data.msg);
         			location.reload(true);
         		}else{
         			showTip("风控模型审核失败！");
         			location.reload(true);
         		}
         		
         		
         	}
       }); 

	}
</script>

</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/wish/contract/wishContract/wishAuidtList">待审核列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/wish/contract/wishContract/wishAuidtList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<ul class="ul-form">
		<%-- 	<li><label>业务编号：</label> <form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium" /></li> --%>
		<%-- 	<li><label>客户名称：</label> <form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium" /></li> --%>
		    <li><label>业务类型：</label><select onclick="sel(this)">
		        <option  value ="-1" selected>--请选择--</option> 
                <option  value ="2">--待审批--</option>
                <option  value ="1">--新增--</option>
                <option  value="0">--中止--</option>
            </select></li>
		<!-- 	<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" /> -->
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
   
	<table class="table table-bordered">
		<tr>
			<td><a class="btn btn-primary" onclick="loanApply(1);">审核通过</a>
			    <a class="btn btn-primary" onclick="loanApply(0);">审核不通过</a>
			  <a class="btn btn-primary" onclick="getPlan();">获取风控回款计划</a> 
			 <div id="loading"></div>
		</td>
		</tr>
	</table>
	<table id="contentTable"
		class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>收款人</th>
				<th>申请日期</th>
				<th>金额</th>
				<th>手续费</th>
				<th>业务状态</th>
				<th>备注</th>
				<!-- 	<th>操作</th>	 -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="tLoanContract">
				<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
					<td><input type="radio" name="loanContractId"
						id="loanContractId" value="${tLoanContract.id}"
						data-ukey="${tLoanContract.ukey}" /></td>
					<td><a
						href="${ctx}/wish/contract/wishContract/wishLoanContractView?contractId=${tLoanContract.id}">
							${tLoanContract.contractNumber} </a></td>
					<td>${tLoanContract.customerName}</td>
					<td><fmt:formatDate value="${tLoanContract.applyDate}"
							pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatNumber value="${tLoanContract.loanAmount}"
							pattern="#,#00.00#" /></td>
					<td><fmt:formatNumber value="${tLoanContract.wishCharge}"
							pattern="#,#00.00#" /></td>

					<td>${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
					</td>
					<td>${tLoanContract.remarks}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<!-- 	<label for="checkrev">全选/全不选<input type="checkbox" id="checkrev"
		onclick="checkrev();"></label> -->
	<div class="pagination">${page}</div>
</body>
</html>