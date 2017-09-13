<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
	<c:forEach var="contractTpl" items="${contractTpls}" varStatus="idx">
		<div style="height: 280px; width: 260px; display: inline-block; text-align: center; font-size: 12px;">
			<a href="${ctx}/contract/tpl/tContractTpl/view?rid=${id}&rkey=${ukey}&id=${contractTpl.id}"><img src="${ctxStatic }/images/contract/ht_${ukey}.png" height="200px"></a>
			<div style="border: 1px solid #ddd;">
				<div>${contractTpl.formName }</div>
				<div>版本：${contractTpl.ftlVersion }</div>
			</div>	
		</div>
	</c:forEach>
</body>
</html>