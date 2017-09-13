<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费规则管理</title>
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
		<li class="active"><a href="${ctx}/billing/rule/biRule/">收费规则列表</a></li>
		<shiro:hasPermission name="billing:rule:biRule:edit">
		<li><a href="${ctx}/billing/rule/biRule/form?price.type=2">数量计价收费规则添加</a></li>
		<li><a href="${ctx}/billing/rule/biRule/form?price.type=1">时间计价收费规则添加</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="biRule" action="${ctx}/billing/rule/biRule/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" class="input-medium "/>
			</li>
			<li><label>规则类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('biling_rule_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>名称</th>
				<th>收费项</th>
				<th>单价</th>
				<th>平均价</th>
				<th>数量/单位</th>
				<th>总价</th>
				<th>折后价</th>
				<th>优惠率</th>
				<th>总使用时间（小时）</th>
				<th>规则类型</th>
				<th>备注</th>
				<th>更新时间</th>
				<shiro:hasPermission name="billing:rule:biRule:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="biRule">
			<tr>
				<td><a href="${ctx}/billing/rule/biRule/form?id=${biRule.id}">
					${biRule.name}
				</a></td>
				<td><a href="${ctx}/billing/element/biElement/form?id=${biRule.price.element.id}">
					${biRule.price.element.name}
				</a></td>
				<td><a href="${ctx}/billing/price/biPrice/form?id=${biRule.price.id}">
					${biRule.price.price}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}
				</a></td>
				<td><a href="${ctx}/billing/rule/biRule/form?id=${biRule.id}">
					${biRule.averagePrice}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}
				</a></td>
				<td>
					 <c:if test="${biRule.price.type eq 1}"><span  title="时间数据仅供参考，时间时间以客户下单日时间计算">${biRule.unitVal}${fns:getDictLabel(biRule.unit, 'biling_rule_unit', '')}=${biRule.number}小时</span></c:if>
					 <c:if test="${biRule.price.type eq 2}">${biRule.number}${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</c:if>
				</td>
				<td>${biRule.price.price*biRule.number}</td>
				<td>${biRule.totalPrice}</td>
				<td>${fns:getDictLabel(biRule.rate, 'biling_rule_rate', '')}</td>
				<td>
					 <c:if test="${biRule.price.type eq 1}"><span  title="时间数据仅供参考，时间时间以客户下单日时间计算">${biRule.totalTime}</span></c:if>
					 <c:if test="${biRule.price.type eq 2}">∞</c:if>
				</td>
				<td>${fns:getDictLabel(biRule.type, 'biling_rule_type', '')}</td>
				<td>
					${biRule.price.remarks}<br/>
					${biRule.remarks}
				</td>
				<td>
					<fmt:formatDate value="${biRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="billing:rule:biRule:edit"><td>
    				<a href="${ctx}/billing/rule/biRule/form?id=${biRule.id}">修改</a>
					<a href="${ctx}/billing/rule/biRule/delete?id=${biRule.id}" onclick="return confirmx('确认要删除该收费规则吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>