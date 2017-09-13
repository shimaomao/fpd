<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
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

		/***放款***/
		function loan(){
			var array = getCheckValue("contractId");
		    if(array.length==0){
		    	alert("请选择一个放款申请!");
		    	return;
		    }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getdizhiListStatus",
		         	data: {id:array[0],type:"2"},
		         	dataType: "html",
		         	success: function(data){//data要收押物品的数量
		         		if(data==2){
		         			 alert("该笔合同有押物没有收押，不能放款");
		         		}else{
		         			var url = "${ctx}/lending/tLending/toLoan?contract.id="+array[0];
		         			location.href = url;
		         		}
		         	}
		       });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/lending/tLending/listIng">放款待审列表</a></li>
<%-- 		<li><a href="${ctx}/lending/tLending/listEd">已放款列表</a></li> --%>
		<%-- <li><a href="${ctx}/lending/tLending/list">放款列表</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tLending" action="${ctx}/lending/tLending/listIng" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="contract.applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLending.contract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>合同编号：</label>
				<form:input path="contract.contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>
                     <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                            贷款金额：
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            担保金额：
			         </c:if> 
                </label>
				<form:input path="contract.loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>
                     <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                            贷款期限：
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            担保期限：
			         </c:if>
                </label>
				<form:input path="contract.loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
<!-- 			<li><label>贷款方式：</label> -->
<%-- 				<form:radiobuttons path="contract.loanType" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table class="table table-bordered">
		<tr><td>
			<input class="btn btn-primary" type="button" onclick="loan();" value="放款"/>
		</td></tr>
    </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				 <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
					 <th>贷款金额（元）</th>
					<th>贷款期限</th>
					<th>贷款利率(%)</th>
					<th>贷款方式</th>                
				 </c:if>
				<c:if test="${fns:getUser().company.primaryPerson==danbao}">
					<th>担保金额（元）</th>
					<th>担保期限</th>
					<th>担保费率(%)</th>
					<th>反担保方式</th>                
				 </c:if>
				<th>申请日期</th>
				<th>经办人</th>
				<th>经办部门</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="contract">
			<tr>
				<td>
					<input type="radio" name="contractId" id="contractId" value="${contract.id}"/>
				</td>
				<td>
					${contract.contractNumber}
				</td>
				<td>
					${contract.customerName}
				</td>
				<td>
					${contract.loanAmount}
				</td>
				<td>
					${contract.loanPeriod}(${fns:getDictLabel(contract.periodType, 'period_type', '')})
				</td>
				<td>
					${contract.loanRate}(${contract.loanRateType})
				</td>
				<td>
					${fns:getDictLabels(contract.loanType, 'loan_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${contract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${contract.creatByerName}
				</td>
				<td>
					${contract.deptname}
				</td>
				<td>
					${fns:getDictLabel(contract.status, 'loan_contract_status', '')} 
				</td>
<%-- 				<shiro:hasPermission name="contract:tLoanContract:edit"><td> --%>
    				<%-- <a href="${ctx}/contract/tLoanContract/form?id=${tLending.contract.id}">修改</a>
					<a href="${ctx}/contract/tLoanContract/delete?id=${tLending.contract.id}" onclick="return confirmx('确认要删除该业务办理吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>