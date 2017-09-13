<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>计费汇总管理</title>
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
		<li class="active"><a href="${ctx}/billing/collect/biCollect/">计费汇总列表</a></li>
		<%-- <shiro:hasPermission name="billing:collect:biCollect:edit"><li><a href="${ctx}/billing/collect/biCollect/form">计费汇总添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="biCollect" action="${ctx}/billing/collect/biCollect/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>收费规则：</label>
				<form:select path="rule.id" class="input-medium">
					<form:option value="" label=""/>
					<c:forEach var="biRule" items="${biRules}">
						<form:option value="${biRule.name }" label="${biRule.name }"/>
					</c:forEach> 
				</form:select>
			</li>
			<li><label>可用状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('biling_collect_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>提醒标识：</label>
				<form:select path="txFlag" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('biling_collect_txflag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>业务</th>
				<th>总付费</th>
				<th>总数量</th>
				<th>总时间</th>
				<th>剩余数量</th>
				<th>剩余时间</th>
				<th>可用状态</th>
				<th>提醒标识</th>
				<th>更新时间</th>
				<th>备注</th>
				<%-- <shiro:hasPermission name="billing:collect:biCollect:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="biCollect">
			<tr>
				<%-- <td><a href="${ctx}/billing/element/biElement/form?id=${biCollect.element.id}">
					${biCollect.element.name}
				</a></td>
				<td><a href="${ctx}/billing/collect/biCollect/form?id=${biCollect.id}">
					${biCollect.totalPrice}
				</a></td> --%>
				<td>${biCollect.element.name}</td>
				<td>${biCollect.totalPrice}</td>
				<td>${biCollect.number}</td>
				<td>${biCollect.totalTime}</td>
				<td>
					${biCollect.surplusNumber}
				</td>
				<td>
					${biCollect.surplusTime}
				</td>
				<td>
					${fns:getDictLabel(biCollect.status, 'biling_collect_status', '')}
				</td>
				<td>
					${fns:getDictLabel(biCollect.txFlag, 'biling_collect_txflag', '')}
				</td>
				<td>
					<fmt:formatDate value="${biCollect.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${biCollect.remarks}
				</td>
				<shiro:hasPermission name="billing:collect:biCollect:edit"><td>
    				<a href="${ctx}/billing/collect/biCollect/form?id=${biCollect.id}">修改</a>
					<a href="${ctx}/billing/collect/biCollect/delete?id=${biCollect.id}" onclick="return confirmx('确认要删除该计费汇总吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>