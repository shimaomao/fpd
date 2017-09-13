<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>		
		<%-- <div>${result.params}</div> --%>
		<div>${result.content}</div>
	</form:form>
</body>
</html>