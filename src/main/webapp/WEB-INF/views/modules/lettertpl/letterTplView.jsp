<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<body>
<script type="text/javascript">
function printme(){
	document.body.innerHTML=document.getElementById('div1').innerHTML+'<br/>';
	window.print();
}
</script>
	<form:form class="form-horizontal">
	    <a onclick="javascrīpt:printme()" style="cursor: pointer;float: right;" >打印</a>
		<sys:message content="${message}"/>		
		<%-- <div>${result.params}</div> --%>
		<div id="div1">
                  ${result.content}
        </div>
	</form:form>
</body>
</html>
