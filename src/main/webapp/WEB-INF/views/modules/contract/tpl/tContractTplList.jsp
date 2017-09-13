<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同模板管理</title>
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
		<li class="active"><a href="${ctx}/contract/tpl/tContractTpl/">合同模板列表</a></li>
		<shiro:hasPermission name="contract:tpl:tContractTpl:edit"><li><a href="${ctx}/contract/tpl/tContractTpl/form">合同模板添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tContractTpl" action="${ctx}/contract/tpl/tContractTpl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表单名：</label>
				<form:input path="formName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:input path="type" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>创建名：</label>
				<form:input path="createName" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:input path="ftlStatus" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>表单名</th>
				<th>类型</th>
				<th>创建名</th>
				<th>状态</th>
				<th>版本</th>
				<th>上传word布局模板的URL</th>
				<th>更新时间</th>
				<!-- <th>内容描述</th> -->
				<th>备注信息</th>
				<shiro:hasPermission name="contract:tpl:tContractTpl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tContractTpl">
			<tr>
				<td><a href="${ctx}/contract/tpl/tContractTpl/form?id=${tContractTpl.id}">
					${tContractTpl.formName}
				</a></td>
				<td>
					${fns:getDictLabel(tContractTpl.type, 'contract_type', '')}
				</td>
				<td>
					${tContractTpl.createName}
				</td>
				<td>
					${fns:getDictLabel(tContractTpl.ftlStatus, 'contract_ftlStatus', '')}
				</td>
				<td>
					${fns:getDictLabel(tContractTpl.ftlVersion, 'contract_ftlVersion', '')}
				</td>
				<td>
					${tContractTpl.ftlWordUrl}
				</td>
				<td>
					<fmt:formatDate value="${tContractTpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <td>
					${tContractTpl.ftlContent}
				</td> --%>
				<td>
					${tContractTpl.remarks}
				</td>
				<shiro:hasPermission name="contract:tpl:tContractTpl:edit"><td>
    				<a href="${ctx}/contract/tpl/tContractTpl/form?id=${tContractTpl.id}">修改</a>
					<a href="${ctx}/contract/tpl/tContractTpl/delete?id=${tContractTpl.id}" onclick="return confirmx('确认要删除该合同模板吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>