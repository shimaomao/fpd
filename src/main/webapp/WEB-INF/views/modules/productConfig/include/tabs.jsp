<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="tabItem" value="${(empty tabItem)?'dfFormTplList':tabItem}" />
<div class="tabsGroup">
	<div class="tbox">
		<label <c:if test="${tabItem eq 'dfFormTplList'}"> class="active"</c:if>> <a href="./ftplList">产品信息表单模板</a></label>
		<label <c:if test="${tabItem eq 'selectModel'}"> class="active"</c:if>> <a href="./ftplSelectModel">表单设计</a></label>
	</div>
</div>