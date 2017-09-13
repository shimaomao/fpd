<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户初始化失败</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/initData?id=${result.user.id}">初始化数据</a></li>
	</ul>
	<table id="contentTable" class="table" >
		<tr><td style="height:500px; text-align: center;">
			<c:if test="${!result.istrue}">
				${result.msg}
			</c:if>
		</td></tr>
	</table>
</body>
</html>