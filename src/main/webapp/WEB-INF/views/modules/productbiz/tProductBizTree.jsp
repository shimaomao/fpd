<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务功能管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
		function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/productbiz/tProductBiz/updateSort");
	    	$("#listForm").submit();
    	}
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
		<li class="active"><a href="${ctx}/productbiz/tProductBiz/tree">业务功能列表</a></li>
		<shiro:hasPermission name="productbiz:tProductBiz:edit"><li><a href="${ctx}/productbiz/tProductBiz/form">业务功能添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tProductBiz" action="${ctx}/productbiz/tProductBiz/tree" method="post" class="breadcrumb form-search">
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
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead>
			<tr>
				<th>业务操作名</th>
				<th>功能类别</th>
				<th>状态</th>
				<th>流程节点</th>
				<th>系统表单</th>
				<th>显示</th>
				<th>业务类型</th>
				<th>排序</th>
				<th>业务编码</th>
				<th>资源路径</th>
				<th>资源参数</th>
				<th>租户编号</th>
			<shiro:hasPermission name="productbiz:tProductBiz:edit"><th>操作</th></shiro:hasPermission></tr></thead>
			<tbody><c:forEach items="${list}" var="tProductBiz">
				<tr id="${tProductBiz.id}" pId="${(tProductBiz.parent.id ne '1')?tProductBiz.parent.id:'0'}">
					<td nowrap>${tProductBiz.bizName}</td>
					<td>
						${fns:getDictLabel(tProductBiz.type, 'productbiz_type', '')}
					</td> 
					<td>
						${fns:getDictLabel(tProductBiz.status, 'productbiz_status', '')}
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
						${fns:getDictLabel(tProductBiz.isShow, 'show_hide', '')}
					</td>
					<td>
						<c:if test="${(tProductBiz.isYwtype eq '0') || (empty tProductBiz.isYwtype)}">全部</c:if>
						<c:if test="${tProductBiz.isYwtype eq '1'}">担保</c:if>
						<c:if test="${tProductBiz.isYwtype eq '2'}">贷款</c:if>
					</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="productbiz:tProductBiz:edit">
							<input type="hidden" name="ids" value="${tProductBiz.id}"/>
							<input name="sorts" type="text" value="${tProductBiz.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="productbiz:tProductBiz:edit">
							${tProductBiz.sort}
						</shiro:lacksPermission>
					</td>
					<td nowrap><a href="${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}">${tProductBiz.bizCode}</a></td>
					<td>
						${tProductBiz.bizUrl}
					</td>
					<td>
						${tProductBiz.bizParam}
					</td>
					<td>
						${tProductBiz.organId}
					</td>
					<shiro:hasPermission name="productbiz:tProductBiz:edit"><td nowrap>
						<a href="${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}">修改</a>
						<a href="${ctx}/productbiz/tProductBiz/delete?id=${tProductBiz.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<a href="${ctx}/productbiz/tProductBiz/form?parentId=${tProductBiz.id}">添加下级菜单</a> 
					</td></shiro:hasPermission>
				</tr>
			</c:forEach></tbody>
		</table>
		<shiro:hasPermission name="productbiz:tProductBiz:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	 </form>
</body>
</html>