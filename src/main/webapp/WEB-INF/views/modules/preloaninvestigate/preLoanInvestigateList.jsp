<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>立项调查管理</title>
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
		
		
		/****提交申请****/
		function toExamine(){
			 var array = getCheckValue("loanContractId");
			 if(array.length==0){
				 showTip("请选择一条业务合同!");
			  	return;
			  }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/preloaninvestigate/preLoanInvestigate/getInvestiStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.PreLoanInvesStatus.TO_REVIEW%>){
		         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_DQDC.getKey()%>"},
		         					function(data) {
		         						if(!data){
		         							showTip("无此业务");
		         							return;
		         						}
		         						if(!data.procDefId){
		         							showTip("此业务没有配置流程");
		         							return;
		         						}
		         						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_pre_loan_investigate&businessId="+array[0];
		         					  	location.href = url;
		         					}
		         				);
		         		}else{
		         			showTip("不是[新增]状态的申请！不能提交审批！");
		         		}
		         	}
		       });
		 }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/preloaninvestigate/preLoanInvestigate/">立项调查列表</a></li>
		<shiro:hasPermission name="preloaninvestigate:preLoanInvestigate:edit"><li><a href="${ctx}/preloaninvestigate/preLoanInvestigate/form">立项调查添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="preLoanInvestigate" action="${ctx}/preloaninvestigate/preLoanInvestigate/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>立项编号：</label>
				<form:input path="investigateNumber" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
		<table class="table table-bordered">
		  <tr>
			<td>
			  	<a class="btn btn-primary" onclick="toExamine();">提交审批</a>
			</td>
		 </tr>
	  </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>选择</th>
				<th>立项名称</th>
				<th>立项编号</th>
				<th>客户名称</th>
				<th>联系电话</th>
				<th>
			        <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额  
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款金额
	                </c:if>	
		        </th>
				<th>
				    <c:if test="${fns:getUser().company.primaryPerson==danbao}">
		                                                         担保费率(%)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款利率(%)
	                </c:if>
		        </th>
				<th>
					<c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限
	                </c:if>	
                </th>
				<th>调查时间</th>
				<th>状态</th>
				<shiro:hasPermission name="preloaninvestigate:preLoanInvestigate:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="preLoanInvestigate">
			<tr>
			    <td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${preLoanInvestigate.id}"/>
				</td>
				<td><a href="${ctx}/preloaninvestigate/preLoanInvestigate/form?id=${preLoanInvestigate.id}">
					${preLoanInvestigate.title}
				</a></td>
             	<td>
				   ${preLoanInvestigate.investigateNumber}
             	</td>
             	<td>
				    ${preLoanInvestigate.customerName}
             	</td>
             	<td>
				    ${preLoanInvestigate.customerPhone}
             	</td>
             	<td>
				    ${preLoanInvestigate.loanamount}
             	</td>
             	<td>
				    ${preLoanInvestigate.rate}
             	</td>
             	<td>
				    ${preLoanInvestigate.period}
             	</td>
				<td>
					<fmt:formatDate value="${preLoanInvestigate.investigaDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
				    	${fns:getDictLabels(preLoanInvestigate.status, 'pre_status', '')}
             	</td>
				<td>
				    <c:if test="${preLoanInvestigate.status==1||preLoanInvestigate.status==4}">
					    <a href="${ctx}/preloaninvestigate/preLoanInvestigate/form?id=${preLoanInvestigate.id}">修改</a>
						<a href="${ctx}/preloaninvestigate/preLoanInvestigate/delete?id=${preLoanInvestigate.id}" onclick="return confirmx('确认要删除该立项调查吗？', this.href)">删除</a>
				    </c:if>
    			 </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>