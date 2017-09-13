<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人客户管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty message}">
			showTip("${message}");
			</c:if>
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
		<li class="active"><a href="${ctx}/company/tCompany/listAll">企业客户列表</a></li>
	</ul>
	 
	<div style="display: none;">
		<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="" extId="1" checked="true"
			title="目标客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<form:form id="searchForm" modelAttribute="tCompany" action="${ctx}/company/tCompany/listAll" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>证件号码：</label>
				<form:input path="cardNum" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>企业性质：</label>
				<form:input path="properties" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
				<th>企业名称</th>
				<th>证件号码</th>
				<th>企业性质</th>
				<th>法定代表人</th>
				<th>法人手机号码</th> 
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCompany">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>${tCompany.name}</td>
				<td>
					${tCompany.cardNum}
				</td>
				<td>
					${fns:getDictLabel(tCompany.properties, 'nature_of_unit', '')}
				</td>
				<td>
					${tCompany.surety}
				</td>
				<td>
					${tCompany.suretyMobile}
				</td>
				<td>
					${tCompany.remarks}
				</td>
				<td>
					<a href="${ctx}/company/tCompany/view?id=${tCompany.id}">查看征信</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>