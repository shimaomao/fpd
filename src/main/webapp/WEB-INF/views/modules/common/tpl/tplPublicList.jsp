<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公共模板库表管理</title>
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
		<li class="active"><a href="${ctx}/common/tpl/tplPublic/">公共模板库表列表</a></li>
		<shiro:hasPermission name="common:tpl:tplPublic:edit"><li><a href="${ctx}/common/tpl/tplPublic/form">公共模板库表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tplPublic" action="${ctx}/common/tpl/tplPublic/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模板编码：</label>
				<form:input path="tplCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>模板名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="25" class="input-medium"/>
			</li>
			<li><label>模板状态：</label>
				<form:radiobuttons path="tplStatus" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>模板编码</th>
				<th>模板名称</th>
				<!-- <th>模板状态</th> -->
				<th>是否启用</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="common:tpl:tplPublic:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tplPublic">
			<tr>
				<td><a href="${ctx}/common/tpl/tplPublic/form?id=${tplPublic.id}">
					${tplPublic.tplCode}
				</a></td>
				<td>
					${tplPublic.name}
				</td>
				<td>
					${fns:getDictLabel(tplPublic.tplStatus, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${tplPublic.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tplPublic.remarks}
				</td>
				<shiro:hasPermission name="common:tpl:tplPublic:edit"><td>
					<c:if test="${!empty tplPublic.tplStatus}">
						<c:if test="${tplPublic.tplStatus=='0'}">
							<a href="${ctx}/common/tpl/tplPublic/chageState?tplStatus=1&id=${tplPublic.id}">启用</a>
						</c:if>
						<c:if test="${tplPublic.tplStatus=='1'}">
							<a href="${ctx}/common/tpl/tplPublic/chageState?tplStatus=0&id=${tplPublic.id}">停用</a>
						</c:if>
					</c:if>
    				<a href="${ctx}/common/tpl/tplPublic/form?id=${tplPublic.id}">修改</a>
					<a href="${ctx}/common/tpl/tplPublic/delete?id=${tplPublic.id}" onclick="return confirmx('确认要删除该公共模板库表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>