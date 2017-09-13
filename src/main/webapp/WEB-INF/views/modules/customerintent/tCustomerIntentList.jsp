<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意向调查管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			var isClose = ${isClose} + "";
			if(isClose){
				parent.window.mainFrame.location.reload(); 
				parent.$.jBox.close(true); 
			}
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//新增
		function toUseTracking(){
			showjBox("意向调查信息", "${ctx}/customerintent/tCustomerIntent/form?companyId="+$("#companyId").val());
		}
		
		function toUpdata(id,companyId){
			showjBox("意向调查信息", "${ctx}/customerintent/tCustomerIntent/form?companyId="+companyId+"&id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/company/tCompany/form?id=${tCustomerIntent.companyId}">客户基本信息</a></li>
		<li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCustomerIntent.companyId}">高管\股东\实际控制人\其他人信息</a></li>
		<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?companyId=${tCustomerIntent.companyId}">客户关联</a></li>
		<li class="active"><a href="${ctx}/customerintent/tCustomerIntent/list?companyId=${tCustomerIntent.companyId}">意向调查</a></li>
		<li><a href="${ctx}/files/tContractFiles/list?taskId=${tCustomerIntent.companyId}&type=customer_archives&mark=1">档案\财务资料</a></li>
<%--         <li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCustomerIntent.companyId}">财务报告</a></li> --%>
	    <li><a href="${ctx}/credit/customerCredit/list?customerId=${tCustomerIntent.companyId}&type=2">授信记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCustomerIntent.companyId}&type=3">业务记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCustomerIntent.companyId}&type=4&status=9">不良记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tCustomerIntent" action="${ctx}/customerintent/tCustomerIntent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="companyId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tCustomerIntent.companyId}"/>
		<ul class="ul-form">
			<li><label>客户名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="tel" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>还款方式：</label>
				<form:select path="repaymentMode" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	 <table class="table table-bordered">
   	   <tr>
   	    <td>
		    <a  class="btn btn-primary"  onclick="toUseTracking();">增加</a>
   	   </td>
   	   </tr>
   	</table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户名称</th>
				<th>联系电话</th>
				<th>产品类型</th>
				<th>还款方式</th>
				<th>贷款额度</th>
				<th>还款期数</th>
				<th>年利率</th>
				<th>担保方式</th>
				<th>起止时间</th>
				<shiro:hasPermission name="customerintent:tCustomerIntent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCustomerIntent">
			<tr>
				<td>
					${tCustomerIntent.name}
				</td>
				<td>
					${tCustomerIntent.tel}
				</td>
				<td>
					${tCustomerIntent.productId}
				</td>
				<td>
					${fns:getDictLabels(tCustomerIntent.repaymentMode, 'product_paytype', '')}
				</td>
				<td>
					${tCustomerIntent.loanLimit}
				</td>
				<td>
					${tCustomerIntent.repaymentPeriod}
				</td>
				<td>
					${tCustomerIntent.annualRate}
				</td>
				<td>
					${fns:getDictLabels(tCustomerIntent.guaranteeMode, 'guarantWay', '')}
				</td>
				<td>
					起：<fmt:formatDate value="${tCustomerIntent.startTime}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;止：<fmt:formatDate value="${tCustomerIntent.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="customerintent:tCustomerIntent:edit"><td>
    				<a onclick="toUpdata('${tCustomerIntent.id}','${tCustomerIntent.companyId}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/customerintent/tCustomerIntent/delete?id=${tCustomerIntent.id}+&companyId=${tCustomerIntent.companyId}" onclick="return confirmx('确认要删除该意向调查吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>