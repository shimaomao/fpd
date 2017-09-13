<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>函件模板管理</title>
	<meta name="decorator" content="default"/>
  	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${not empty message}">
				alertx("${message}");
			</c:if>
		});
	</script>
</head>
<body>
	<c:forEach var="letterTpl" items="${letterTpls}" varStatus="idx">
		<a href="${ctx}/lettertpl/letterTpl/view?rid=${id}&type=${ukey}&id=${letterTpl.id}">
		<div style="height: 280px; width: 260px; display: inline-block; text-align: center; font-size: 12px;">
			<img src="${ctxStatic }/images/contract/cs_${ukey}.png" style="height: 200px;">
			<div style="border: 1px solid #ddd;">
				<div>${letterTpl.name }</div>
				<div>版本：${letterTpl.version }</div>
			</div>	
		</div>
		</a>
	</c:forEach>
</body>
</html>