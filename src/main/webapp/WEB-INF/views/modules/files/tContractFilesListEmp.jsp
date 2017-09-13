<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>附件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var isClose = ${isClose} + "";
			if(isClose){
				parent.window.mainFrame.location.reload(); 
				parent.$.jBox.close(true); 
			}
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		
		//新增
		function toUseTracking(){
			showjBox("客户档案", "${ctx}/files/tContractFiles/add?type=customer_archives&taskId="+$("#taskId").val());
		}
		
		//预览
		function toLook(filePath,title){
			showjBox("客户档案预览", "${pageContext.request.contextPath}"+filePath+"?outFileName="+title+"");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/employee/tEmployee/form?id=${tContractFiles.taskId}">客户基本信息</a></li>
		<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?employeeId=${tContractFiles.taskId}">客户关联</a></li>
		<li><a href="${ctx}/customerintent/tCustomerIntent/list?employeeId=${tContractFiles.taskId}">意向调查</a></li>
		<li class="active"><a href="${ctx}/files/tContractFiles/list?taskId=${tContractFiles.taskId}&type=customer_archives">档案资料</a></li>
		<li><a href="${ctx}/credit/customerCredit/list?customerId=${tContractFiles.taskId}&type=1">授信记录</a></li>
		<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tContractFiles.taskId}&type=1">业务记录</a></li>
		<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tContractFiles.taskId}&type=2&status=9">不良记录</a></li>
	</ul>
	
	<form:form id="searchForm" modelAttribute="tContractFiles" action="${ctx}/files/tContractFiles/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="taskId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tContractFiles.taskId}"/>
		<ul class="ul-form">
			<li><label>源文件名：</label>
				<form:input path="sourceName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>文件标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	 <table class="table table-bordered">
   	   <tr>
   	    <td>
		    <a  class="btn btn-primary"  onclick="toUseTracking();">增加</a>
   	   </td>
   	   </tr>
   	</table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>文件标题</th>
				<th>文件名</th>
				<th>源文件名</th>
				<th>文件类型</th>
				<th>更新时间</th>
				<shiro:hasPermission name="files:tContractFiles:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tContractFiles">
			<tr>
			    <td>
					${tContractFiles.title}
				</td>
				<td>
					${tContractFiles.fileName}
				</td>
				<td>
					${tContractFiles.sourceName}
				</td>
				<td>
					${tContractFiles.type}
				</td>
				<td>
					<fmt:formatDate value="${tContractFiles.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<shiro:hasPermission name="files:tContractFiles:edit">
				<td>
<%-- 				   <a onclick="toLook('${tContractFiles.filePath}','${tContractFiles.title}');" style="cursor: pointer;">预览</a> --%>
                        <a style="TEXT-DECORATION:underline" target="_blank" href="${pageContext.request.contextPath}${tContractFiles.filePath}?outFileName=${tContractFiles.title}">预览</a>

				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>