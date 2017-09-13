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
		
    	//放款审批
		function loanApply(){
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
			    //判断状态
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/wish/contract/wishContract/auditWishContract",
		         	data: {tLoanContractIds:tLoanContractIds},
		         	dataType: "json",
		         	success: function(data){
		         		showTip(data+"条数据被更新");
		         		location.reload(true);
		         	}
		       });
		}
		
		 function checkrev(){
		        //实现反选功能  
		        $('[name=loanContractId]:checkbox').each(function(){  
		            this.checked=!this.checked;  
		        });  
		    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/moveContractlist">放款待审核列表</a></li>
		<%-- <shiro:hasPermission name="contract:tLoanContract:edit"><li><a href="${ctx}/contract/tLoanContract/form">业务办理添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/statistics/loanContract/" method="post" class="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/> --%>
		<ul class="ul-form">
			<li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
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
	         <!--     <a class="btn btn-primary" onclick="loanApply();">确认放款</a> -->
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
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="checkbox" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/moveDetail?id=${tLoanContract.id}">
					${tLoanContract.contractNumber}
				</a>
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.wishCharge}" pattern="#,#00.00#" />
				</td>
				
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
    <label for="checkrev">全选<input type="checkbox" id="checkrev" onclick="checkrev();"></label> 
	<div class="pagination">${page}</div>
</body>
</html>