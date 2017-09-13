<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>逾期金额管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wish/order/wishOverdue/">逾期金额列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="wishOverdue" action="${ctx}/wish/order/wishOverdue/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>创建时间</th>
        <th>更新时间</th>
        <th>用户id</th>
        <th>逾期金额</th>
        <th>贷款订单id</th>
        <th>状态</th>
        <th>备注</th>
        <shiro:hasPermission name="wish:order:wishOverdue:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="wishOverdue">
        <tr>
            <td><a href="${ctx}/wish/order/wishOverdue/form?id=${wishOverdue.id}">
                <fmt:formatDate value="${wishOverdue.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </a></td>
            <td><a href="${ctx}/wish/order/wishOverdue/form?id=${wishOverdue.id}">
                <fmt:formatDate value="${wishOverdue.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </a></td>
            <td>
                    ${wishOverdue.userId}
            </td>
            <td>
                    ${wishOverdue.money}
            </td>
            <td>
                    ${wishOverdue.loanContractId}
            </td>
            <td>
                    ${fns:getDictLabels(wishOverdue.status, 'wish_is_overdue', '')}
            </td>
            <td>
                    ${wishOverdue.remarks}
            </td>
            <shiro:hasPermission name="wish:order:wishOverdue:edit">
                <td>
                    <a href="${ctx}/wish/order/wishOverdue/form?id=${wishOverdue.id}">修改</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>