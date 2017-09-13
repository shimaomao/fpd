<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义表单管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/form/builder/assets/js/lib/require.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/main.js?c=11" type="text/javascript"></script>
	<style type="text/css">
		.table-striped tbody > tr.isTpl > td, .table-striped tbody > tr.isTpl > th, .table-striped tbody > tr.isTpl > td, .table-striped tbody > tr.isTpl > th {background-color: #eee;}
	</style>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				var groups = {
					attrId:"data-id",
					btnView:".btn-view",
					btnEdit:".btn-edit",
				};
				$(groups.btnView).click(function(){
					api.jbox.openUrl("${ctx}/form/tpl/dfFormTpl/view?id="+$(this).attr(groups.attrId), "表单-预览", "自定义表单-预览");
				});
				/* $(groups.btnEdit).click(function(){
					api.jbox.openUrl("${ctx}/form/builder/builder/toForm?ftplId="+$(this).attr(groups.attrId), "表单-修改", "自定义表单-修改");
				}); */
			});
		});
	</script>
	<script type="text/javascript">
		$(document).ready(function() {});
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
		<li class="active"><a href="${ctx}/form/tpl/dfFormTpl/syslist">系统表单模板列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="dfFormTpl" action="${ctx}/form/tpl/dfFormTpl/syslist" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表单名称：</label>
				<form:input path="sname" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>模块：</label>
				<form:select path="model" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('df_model')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>表单名称</th>
				<th>表类型</th>
				<th>模块</th>
				<th>子模块</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dfFormTpl">
			<c:if test="${fn:contains(dfFormTpl.name, 'BZQL_')}"><tr class="isTpl"></c:if>
			<c:if test="${!fn:contains(dfFormTpl.name, 'BZQL_')}"><tr></c:if>
				<td><a href="${ctx}/form/tpl/dfFormTpl/form?id=${dfFormTpl.id}">
					${dfFormTpl.sname}
				</a></td>
				<td>
					${fns:getDictLabel(dfFormTpl.type, 'df_table_type', '')}
				</td>
				<td>
					${fns:getDictLabel(dfFormTpl.model, 'df_model', '')}
				</td>
				<td>
					<c:if test="${dfFormTpl.model eq 'business_application' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_bapplication', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'product' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_product', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'employee' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_employee', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'company' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_company', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'advisory' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_advisory', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'guarantee_contract' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_guarantee_contract', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'mortgage_contract' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_mortgage_contract', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'pledge_contract' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_pledge_contract', '')}
					</c:if>
					<c:if test="${dfFormTpl.model eq 'looploan' }">
						${fns:getDictLabel(dfFormTpl.modsub, 'df_model_looploan', '')}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${dfFormTpl.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${dfFormTpl.remarks}
				</td>
				<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><td>
    				<a class="btn-view" href="javascript:void(0);" data-id="${dfFormTpl.id}" title="预览表单布局">预览</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>