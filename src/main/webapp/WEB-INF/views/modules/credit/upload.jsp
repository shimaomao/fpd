<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="default"/>
<title>EXCEL数据导入</title>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/nkCredit/">微信白名单信息列表</a></li>
		<shiro:hasPermission name="credit:nkCredit:edit">
			<li><a href="${ctx}/credit/nkCredit/form">微信白名单信息添加</a></li>
			<li class="active"><a href="${ctx}/credit/nkCredit/upload">EXCEL数据导入</a></li>
		</shiro:hasPermission>
	</ul>
	<form action="${ctx}/credit/nkCredit/xlsDeals"
		enctype="multipart/form-data" method="post">
		EXCEL数据导入：<input type="file" name="file"><br /> <input
			type="submit" value="提交">
	</form>
	<!--打印错误信息  -->
	<div id="div">
		<c:if test="${not empty listMsg}">
			<c:forEach var="dealmsg" items="${listMsg}"> 
 				${dealmsg}<br>
			</c:forEach>
		</c:if>
		<%-- <c:if test="${not empty list2}">
			<c:forEach var="listZ2" items="${list2}"> 
		${listZ2} <br>
				<br> --%>
				<%-- <c:forEach var="map2" items="${listZ2} "> 
	 	
		${map2.key} ： ${map2.value} <br> 
		</c:forEach>  --%>
			<%-- </c:forEach>
		</c:if> --%>
	</div>
</body>
</html>