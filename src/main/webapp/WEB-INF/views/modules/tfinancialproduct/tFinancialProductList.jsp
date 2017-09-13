<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>理财产品管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/tfinancialproduct/tFinancialProduct/">理财产品列表</a></li>
		<shiro:hasPermission name="tfinancialproduct:tFinancialProduct:edit"><li><a href="${ctx}/tfinancialproduct/tFinancialProduct/form">理财产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tFinancialProduct" action="${ctx}/tfinancialproduct/tFinancialProduct/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="productName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>产品类型：</label>
				<form:select path="productType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年化率：</label>
				<form:input path="yearConversion" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label>融资期限：</label>
				<form:input path="limitTime" htmlEscape="false" maxlength="10" class="input-medium"/>
			</li>
			<li><label>期限单位：</label>
				<form:radiobuttons path="danwei" items="${fns:getDictList('cycleType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li><label>产品状态:：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('Productstatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>产品名称</th>
				<th>产品类型</th>
				<th>年化率(%)</th>
				<th>融资金额</th>
				<th>融资期限</th>
				<th>融资进度(%)</th>
				<th>已融资金额</th>
				<th>回款方式</th>
				<th>产品状态:</th>
				<th>融资状态</th>
				<shiro:hasPermission name="tfinancialproduct:tFinancialProduct:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tFinancialProduct">
			<tr>
				<td>
					<a href="${ctx}/tfinancialproduct/tFinancialProduct/detail?id=${tFinancialProduct.id}">${tFinancialProduct.productName}</a>
				</td>
				<td>
					${fns:getDictLabel(tFinancialProduct.productType, 'RzproductType', '')}
				</td>
				<td>
					${tFinancialProduct.yearConversion}
				</td>
				<td>
					${tFinancialProduct.amount}
				</td>
				<td>
					${tFinancialProduct.limitTime}(${fns:getDictLabel(tFinancialProduct.danwei, 'period_type', '')})
				</td>
				<td>
					${tFinancialProduct.progress}
				</td>
				<td>
					${tFinancialProduct.kemoney}
				</td>
				<td>
					${fns:getDictLabel(tFinancialProduct.repayType, 'product_paytype', '')}
				</td>
				<td>
					${fns:getDictLabel(tFinancialProduct.status, 'Productstatus', '')}
				</td>
				<td>
					${fns:getDictLabel(tFinancialProduct.rzstatus, 'rzstatus', '')}
				</td>
				<shiro:hasPermission name="tfinancialproduct:tFinancialProduct:edit">
				   <td>
                    <!-- 新增的理财产品，才能进行修改删除，以及进行审核操作 -->
				    <c:if test="${tFinancialProduct.status==0}">
				        <a href="${ctx}/tfinancialproduct/tFinancialProduct/form?id=${tFinancialProduct.id}">修改</a>
				        <a href="${ctx}/tfinancialproduct/tFinancialProduct/duditform?id=${tFinancialProduct.id}">审核</a>
				        <a href="${ctx}/tfinancialproduct/tFinancialProduct/delete?id=${tFinancialProduct.id}" onclick="return confirmx('确认要删除该理财产品吗？', this.href)">删除</a>
				    </c:if>
				   </td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>