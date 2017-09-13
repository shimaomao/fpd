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
		<li class="active"><a href="${ctx}/employee/tEmployee/listAll">个人客户列表</a></li>
	</ul>
	 
	<div style="display: none;">
		<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="" extId="1" checked="true"
			title="目标客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<form:form id="searchForm" modelAttribute="tEmployee" action="${ctx}/employee/tEmployee/listAll" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>身份证号码：</label>
				<form:input path="cardNum" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li><label>手机号码：</label>
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
				<th>姓名</th>
				<th>身份证号码</th>
				<th>现住址</th>
				<th>手机号码</th>
				<th>联系电话</th>
				<th>是否本地户口</th>
				<th>个人职务</th>
				<th>更新时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tEmployee">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>${tEmployee.name}</td>
				<td>
					${tEmployee.cardNum}
				</td>
				<td>
					${tEmployee.currentLiveAddress}
				</td>
				<td>
					${tEmployee.mobile}
				</td>
				<td>
					${tEmployee.telephone}
				</td>
				<td>
					${fns:getDictLabel(tEmployee.isLocalHousehold, 'is_local_household', '')}
				</td>
				<td>
					${tEmployee.post}
				</td>
				<td>
					<fmt:formatDate value="${tEmployee.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tEmployee.remarks}
				</td>
				<td>
					<a href="${ctx}/employee/tEmployee/view?id=${tEmployee.id}">查看征信</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>