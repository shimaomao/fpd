<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>creditResult管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active"><a href="${ctx}/credit/cifQhValidate">征信查询</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="cifQhValidate" action="${ctx}/credit/cifQhValidate/search" method="post" class="breadcrumb form-search">
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<li><label>征信服务名称：</label>
				<form:select path="searchCode" class="input-xlarge required"> 
 					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('credit_service')}" itemLabel="label" itemValue="value" htmlEscape="false"/> 
				</form:select>
			</li>
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
	</form:form>
</body>
</html>