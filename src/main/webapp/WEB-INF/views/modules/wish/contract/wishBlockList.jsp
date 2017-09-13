<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>贷后管理</title>
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
		

		
		//放款审批
		function wishBlock(){
			    var array = getCheckValue("loanContractId");
			    if(array.length==0){
			    	showTip("请选择一条业务合同!");
			    	return;
			    }
			    var tLoanContractIds="";
			    for(var i=0;i<array.length;i++){
			    	  if(i==0){
			    		  tLoanContractIds="'"+array[i]+"'";
			    	  }else{
			    		  tLoanContractIds=tLoanContractIds+","+"'"+array[i]+"'";
			    	  }
			    	  
			    }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/wish/contract/wishContract/wishBlockContract",
		         	data: {tLoanContractIds:tLoanContractIds},
		         	dataType: "json",
		         	success: function(data){
		         		showTip(data+"条数据被更新");
		         		location.reload(true);
		         	}
		       });
		}
		//导出文件
        function toDownload(){
        	var customerName= $("#customerName").val();
        	var starttime= $("#starttime").val();
        	var endtime= $("#endtime").val(); 
       		var url = "${ctx}/wish/contract/wishContract/wishLendExcel?customerName="+customerName+"&starttime="+starttime+"&endtime="+endtime+"&status=4";
       	    location.href = url;
        }
		
	</script>
<script type="text/javascript">
	$(function() {
		//全选/全不选
		$("#checkrev").click(function() {
			$("[name=loanContractId]:checkbox").attr("checked", this.checked);
		});
		$("[name=loanContractId]:checkbox").click(function() {
			var flag = true;
			$("[name=loanContractId]:checkbox").each(function() {
				if (!this.checked) {
					flag = false;
				}
			});
			$("#checkrev").attr("checked", flag);
		})
	})
</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wish/contract/wishContract/wishBlockList">待锁定列表</a></li>
	</ul>
    
	<form:form id="searchForm" modelAttribute="loanContract" action="${ctx}/wish/contract/wishContract/" method="post" class="breadcrumb form-search">
	 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> --%>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
		     <li><label>开始时间：</label>
				<input id="starttime" name="starttime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.starttime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li> 
			<li><label>结束时间：</label>
				<input id="endtime" name="endtime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.endtime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
											&nbsp;&nbsp;<a class="btn btn-primary" onclick="toDownload();">导出</a></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

	
	<sys:message content="${message}"/>
	<table class="table table-bordered">
	  <tr>
		<td>
	         <a class="btn btn-primary" onclick="wishBlock();">模拟wish锁定成功</a>
	   </td>
	 </tr>
	</table>
    <table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>收款人</th>
				<th>申请日期</th>
			    <th>金额</th>
				<th>手续费</th>
				<th>业务状态</th>
			<!-- 	<th>操作</th>	 -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="checkbox" name="loanContractId" id="loanContractId" value="${loanContract.id}" data-ukey="${loanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/moveDetail?id=${loanContract.id}">
					${loanContract.contractNumber}
				</a>
				</td>
				<td>
					${loanContract.customerName}
				</td>
				<td>
					<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatNumber value="${loanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${loanContract.wishCharge}" pattern="#,#00.00#" />
				</td>
				
				<td>
					${fns:getDictLabel(loanContract.status, 'loan_contract_status', '')}
				</td> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<label for="checkrev">全选/全不选<input type="checkbox" id="checkrev"
		onclick="checkrev();"></label>
	<div class="pagination">${page}</div>
</body>
</html>