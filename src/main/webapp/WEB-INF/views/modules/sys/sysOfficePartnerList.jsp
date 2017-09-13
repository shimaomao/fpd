<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构股东管理</title>
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
		
		function pPage(n,s){
			$("#pPageNo").val(n);
			$("#pPageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${user.id == '1' }">
			<li><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
		</c:if>
		<li class="active"><a href="${ctx}/sys/sysOfficePartner/">股东列表</a></li>
		<%-- <shiro:hasPermission name="sys:sysOfficePartner:edit"><li><a href="${ctx}/sys/sysOfficePartner/form">机构股东添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="sysOfficePartner" action="${ctx}/sys/sysOfficePartner/partners" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		
		<input id="pPageNo" name="pPageNo" type="hidden" value="${pPage.pageNo}"/>
		<input id="pPageSize" name="pPageSize" type="hidden" value="${pPage.pageSize}"/>
		<%-- <ul class="ul-form">
			<li><label>股东名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<sys:message content="${message}"/>
	<h5>法人股东列表</h5>
	<a class="btn btn-primary" style="float: right;" href="${ctx}/sys/sysOfficePartner/form">法人股东添加</a>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th><th class="sort-column name">股东名称</th><th>法定代表人</th><th>入股日期</th><th>出资额(万元)</th><th>出资比例</th>
				<shiro:hasPermission name="sys:sysOfficePartner:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysOfficePartner" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td><a href="${ctx}/sys/sysOfficePartner/form?id=${sysOfficePartner.id}">
					${sysOfficePartner.name}
				</a></td>
				<td>${sysOfficePartner.legalPerson}</td>
				<td>
					${sysOfficePartner.signDate}
				</td>
				<td>
					${sysOfficePartner.funds}
				</td>
				<td>
					${sysOfficePartner.proportion}
				</td>
				<shiro:hasPermission name="sys:sysOfficePartner:edit"><td>
    				<a href="${ctx}/sys/sysOfficePartner/form?id=${sysOfficePartner.id}">修改</a>
					<a href="${ctx}/sys/sysOfficePartner/delete?id=${sysOfficePartner.id}" onclick="return confirmx('确认要删除该机构股东吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<h5>自然人股东列表</h5>
	<a class="btn btn-primary" style="float: right;" href="${ctx}/sys/sysOfficePpartner/form">自然人股东添加</a>
	<table id="contentTable2" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th>序号</th><th>姓名</th><th>性别</th><th>入股日期</th><th>出资额(万元)</th><th>出资比例</th>
		<shiro:hasPermission name="sys:sysOfficePpartner:edit"><th>操作</th></shiro:hasPermission>
		</tr></thead>
		<tbody>
		<c:forEach items="${pPage.list}" var="sysOfficePpartner" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td><a href="${ctx}/sys/sysOfficePpartner/form?id=${sysOfficePpartner.id}">
					${sysOfficePpartner.name}
				</a></td>
				<td>${sysOfficePpartner.gender}</td>
				<td>
					${sysOfficePpartner.signDate}
				</td>
				<td>
					${sysOfficePpartner.funds}
				</td>
				<td>
					${sysOfficePpartner.proportion}
				</td>
				<shiro:hasPermission name="sys:sysOfficePpartner:edit"><td>
    				<a href="${ctx}/sys/sysOfficePpartner/form?id=${sysOfficePpartner.id}">修改</a>
					<a href="${ctx}/sys/sysOfficePpartner/delete?id=${sysOfficePpartner.id}" onclick="return confirmx('确认要删除该机构个人股东吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${pPage}</div>
</body>
</html>