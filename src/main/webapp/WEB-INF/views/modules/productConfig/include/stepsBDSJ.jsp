<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="stpsItem" value="${(empty stpsItem)?'1':stpsItem}" />
<div class="stepGroup">
	<div class="row-fluid linebox stepBox">
		<div class="step <c:if test="${stpsItem eq '1'}">curStep</c:if>" ><span>1、选择模块</span></div>
		<div class="step <c:if test="${stpsItem eq '2'}">curStep</c:if>" ><span>2、选择属性</span></div>
		<div class="step <c:if test="${stpsItem eq '3'}">curStep</c:if>" ><span>3、设计</span></div>
	</div>
	
	<script type="text/javascript">
		$(function(){
			setStepWidth(".stepBox", ".step");
		});
	</script>
</div>