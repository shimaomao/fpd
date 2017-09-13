<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
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
		
		
		//展期申请
		function transfer(){
			 var array = getCheckValue("loanContractId");
			    if(array.length==0){
			    	showTip("请选择一条业务合同!");
			    	return;
			    }
		    	top.$.jBox.open("iframe:${ctx}/contract/tLoanContract/toLoanTransfer?id="+array[0], 
		    			"转理财",500,400,{
		    		buttons:{"提交":"submit", "刷新":"refresh", "关闭":true}, 
		    		bottomText:"",
		    		submit:function(v, h, f){
		    			var ifrWin = h.find("iframe")[0].contentWindow;
		    			if(v=="refresh"){
		    				ifrWin.location.reload(true);
		                	//ifrWin.clearAssign();
		    				return false;
		                }else if(v=="submit"){
		                	ifrWin.btnSubmit.click();
		    				return false;
		                }
		    		}, loaded:function(h){
		    			$(".jbox-content", top.document).css("overflow-y","hidden");
		    		}
		    	});
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/loanTransferList">业务办理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/loanTransferList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>合同编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>贷款金额：</label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>贷款期限：</label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
<!-- 			<li><label>贷款方式：</label> -->
<%-- 				<form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

  	<%-- <shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr><td>
			    <a class="btn btn-primary" onclick="transfer();">转理财</a>
	  </table>
	</shiro:hasPermission> --%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>产品名称</th>
				<th>贷款金额（元）</th>
				<th>贷款期限</th>
				<th>贷款利率（%）</th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}">
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
					${(tLoanContract.loanPeriod == 1) ? '年' : ((tLoanContract.loanPeriod == 3) ? '日' : '个月')}
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