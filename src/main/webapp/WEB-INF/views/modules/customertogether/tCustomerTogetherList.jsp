<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>共同借款管理</title>
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
		<li class="active"><a href="${ctx}/customertogether/tCustomerTogether/?mainEmployeeid=${tCustomerTogether.mainEmployeeid}&mainCompanyid=${tCustomerTogether.mainCompanyid}">共同借款列表</a></li>
		<shiro:hasPermission name="customertogether:tCustomerTogether:edit"><li><a href="${ctx}/customertogether/tCustomerTogether/form?mainEmployeeid=${tCustomerTogether.mainEmployeeid}&mainCompanyid=${tCustomerTogether.mainCompanyid}">共同借款添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tCustomerTogether" action="${ctx}/customertogether/tCustomerTogether/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="mainEmployeeid" name="mainEmployeeid" type="hidden"  value="${tCustomerTogether.mainEmployeeid}"/>
		<input id="mainCompanyid" name="mainCompanyid"  type="hidden" value="${tCustomerTogether.mainCompanyid}"/>
		<ul class="ul-form">
		    <li><label>姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>客户姓名</th>
				<th>客户类型</th>
				<th>共同借款金额</th>
				<th>单人借款金额</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="customertogether:tCustomerTogether:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCustomerTogether">
			<tr>
				<td>
				    <a href="${ctx}/customertogether/tCustomerTogether/form?id=${tCustomerTogether.id}">
						<c:choose>
							<c:when test="${tCustomerTogether.customerId == tCustomerTogether.mainEmployeeid}">
								 ${tCustomerTogether.customerName}（主借款人）
							</c:when>
							<c:when test="${tCustomerTogether.customerId == tCustomerTogether.mainCompanyid}">
								 ${tCustomerTogether.customerName}（主借款人）
							</c:when>
							<c:otherwise>
								 ${tCustomerTogether.customerName}
							</c:otherwise>
						</c:choose>
				    </a>
				</td>
				<td>
					${fns:getDictLabel(tCustomerTogether.customerType, 'customer_type', '')}
				</td>
				<td>
					${tCustomerTogether.togetherMoney}
				</td>
				<td>
					${tCustomerTogether.loanMoney}
				</td>
				<td>
					<fmt:formatDate value="${tCustomerTogether.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCustomerTogether.remarks}
				</td>
				<shiro:hasPermission name="customertogether:tCustomerTogether:edit"><td>
    				<a href="${ctx}/customertogether/tCustomerTogether/form?id=${tCustomerTogether.id}">修改</a>
					<a href="${ctx}/customertogether/tCustomerTogether/delete?id=${tCustomerTogether.id}&mainEmployeeid=${tCustomerTogether.mainEmployeeid}&mainCompanyid=${tCustomerTogether.mainCompanyid}" onclick="return confirmx('确认要删除该共同借款吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>