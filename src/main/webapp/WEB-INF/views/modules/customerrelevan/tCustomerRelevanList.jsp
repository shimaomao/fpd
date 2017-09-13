<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户关联管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
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
			showjBox("客户关联信息", "${ctx}/customerrelevan/tCustomerRelevan/form?companyId="+$("#companyId").val());
		}
		
		function toUpdata(id,companyId){
			showjBox("客户关联信息", "${ctx}/customerrelevan/tCustomerRelevan/form?companyId="+companyId+"&id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/company/tCompany/form?id=${tCustomerRelevan.companyId}">客户基本信息</a></li>
		<li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCustomerRelevan.companyId}">高管\股东\实际控制人\其他人信息</a></li>
		<li  class="active"><a href="${ctx}/customerrelevan/tCustomerRelevan/list?companyId=${tCustomerRelevan.companyId}">客户关联</a></li>
		<li><a href="${ctx}/customerintent/tCustomerIntent/list?companyId=${tCustomerRelevan.companyId}">意向调查</a></li>
		<li><a href="${ctx}/files/tContractFiles/list?taskId=${tCustomerRelevan.companyId}&type=customer_archives&mark=1">档案\财务资料</a></li>
<%--         <li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCustomerRelevan.companyId}">财务报告</a></li> --%>
	    <li><a href="${ctx}/credit/customerCredit/list?customerId=${tCustomerRelevan.companyId}&type=2">授信记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCustomerRelevan.companyId}&type=3">业务记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCustomerRelevan.companyId}&type=4&status=9">不良记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tCustomerRelevan" action="${ctx}/customerrelevan/tCustomerRelevan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="companyId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tCustomerRelevan.companyId}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>联系人：</label>
				<form:input path="linkMan" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>企业名称</th>
				<th>组织机构代码证</th>
				<th>营业执照</th>
				<th>联系人</th>
				<th>联系电话</th>
				<th>更新时间</th>
				<th>备注</th>
				<shiro:hasPermission name="customerrelevan:tCustomerRelevan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCustomerRelevan">
			<tr>
				<td>
					${tCustomerRelevan.name}
				</td>
				<td>
					${tCustomerRelevan.zzjgCard}
				</td>
				<td>
					${tCustomerRelevan.yyzzCard}
				</td>
				<td>
					${tCustomerRelevan.linkMan}
				</td>
				<td>
					${tCustomerRelevan.phone}
				</td>
				<td>
					<fmt:formatDate value="${tCustomerRelevan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tCustomerRelevan.remarks}
				</td>
				<shiro:hasPermission name="customerrelevan:tCustomerRelevan:edit"><td>
    				<a onclick="toUpdata('${tCustomerRelevan.id}','${tCustomerRelevan.companyId}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/customerrelevan/tCustomerRelevan/delete?id=${tCustomerRelevan.id}+&companyId=${tCustomerRelevan.companyId}" onclick="return confirmx('确认要删除该客户关联吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>