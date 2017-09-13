<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务功能管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">.bizType1{background-color: #eee;} .bizType2{background-color: #eed;} .bizType3{background-color: #dee;} </style>
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
		<li class="active"><a href="${ctx}/productbiz/tProductBiz/list">业务功能列表</a></li>
		<shiro:hasPermission name="productbiz:tProductBiz:edit"><li><a href="${ctx}/productbiz/tProductBiz/form">业务功能添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tProductBiz" action="${ctx}/productbiz/tProductBiz/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>业务操作名：</label>
				<form:input path="bizName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>业务编码：</label>
				<form:input path="bizCode" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>父业务</th>
				<th>业务操作名</th>
				<th>业务编码</th>
				<th>状态</th>
				<th>显示</th>
				<th>流程节点</th>
				<th>系统表单</th>
				<th>排序</th>
				<th>资源路径</th>
				<th>资源参数</th>
				<th>租户编号</th>
				<th>功能类别</th>
				<shiro:hasPermission name="productbiz:tProductBiz:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tProductBiz">
			<tr class="bizType${tProductBiz.type }">
				<td>
					${tProductBiz.parent.bizName}
				</td>
				<td><a href="${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}">
					${tProductBiz.bizName}
				</a></td>
				<td>
					${tProductBiz.bizCode}
				</td>
				<td>
					${fns:getDictLabel(tProductBiz.status, 'productbiz_status', '')}
				</td>
				<td>
					${fns:getDictLabel(tProductBiz.isShow, 'show_hide', '')}
				</td>
				<td>
					${fns:getDictLabel(tProductBiz.isFlow, 'is_flow', '')}
				</td>
				<td>
					<c:if test="${not empty tProductBiz.formtpl}">
						${tProductBiz.formtpl}
					</c:if><c:if test="${empty tProductBiz.formtpl}">否</c:if>
				</td>
				<td>
					${tProductBiz.sort}
				</td>
				<td>
					${tProductBiz.bizUrl}
				</td>
				<td>
					${tProductBiz.bizParam}
				</td>
				<td>
					${tProductBiz.organId}
				</td>
				<td>
					${fns:getDictLabel(tProductBiz.type, 'productbiz_type', '')}
				</td>
				<shiro:hasPermission name="productbiz:tProductBiz:edit"><td>
    				<a href="${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}">修改</a>
					<a href="${ctx}/productbiz/tProductBiz/delete?id=${tProductBiz.id}" onclick="return confirmx('确认要删除该业务功能吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>