<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联系人管理</title>
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
			showjBox("新增高管信息", "${ctx}/tcompanyinfo/tCompanyInfor/form?companyId="+$("#companyId").val());
		}
		
		function toUpdata(id,companyId){
			showjBox("修改高管信息", "${ctx}/tcompanyinfo/tCompanyInfor/form?companyId="+companyId+"&id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/company/tCompany/form?id=${tCompanyInfor.companyId}">客户基本信息</a></li>
		<li class="active"><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCompanyInfor.companyId}">高管\股东\实际控制人\其他人信息</a></li>
		<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?companyId=${tCompanyInfor.companyId}">客户关联</a></li>
		<li><a href="${ctx}/customerintent/tCustomerIntent/list?companyId=${tCompanyInfor.companyId}">意向调查</a></li>
		<li><a href="${ctx}/files/tContractFiles/list?taskId=${tCompanyInfor.companyId}&type=customer_archives&mark=1">档案\财务资料</a></li>
<%--         <li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${tCompanyInfor.companyId}">财务报告</a></li> --%>
        <li><a href="${ctx}/credit/customerCredit/list?customerId=${tCompanyInfor.companyId}&type=2">授信记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCompanyInfor.companyId}&type=3">业务记录</a></li>
	    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${tCompanyInfor.companyId}&type=4&status=9">不良记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tCompanyInfor" action="${ctx}/tcompanyinfo/tCompanyInfor/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:input path="companyId" htmlEscape="false" maxlength="255" class="input-medium" type="hidden" value="${tCompanyInfor.companyId}"/>
		<ul class="ul-form">
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>姓名</th>
				<th>出生日期</th>
				<th>证件类型</th>
				<th>证件号码</th>
				<th>角色</th>
				<th>联系电话</th>
				<th>性别</th>
				<th>是否在职</th>
				<th>电子邮件</th>
				<shiro:hasPermission name="tcompanyinfo:tCompanyInfor:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCompanyInfor">
			<tr>
				<td>
					${tCompanyInfor.name}
				</td>
				<td>
					<fmt:formatDate value="${tCompanyInfor.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(tCompanyInfor.cardType, 'card_type', '')}
				</td>
				<td>
					${tCompanyInfor.cardid}
				</td>
				<td>
					${fns:getDictLabels(tCompanyInfor.twomenu, 'company_role', '')}
				</td>
				<td>
					${tCompanyInfor.phone}
				</td>
				<td>
					${fns:getDictLabels(tCompanyInfor.sex, 'sex', '')}
				</td>
				<td>
					${fns:getDictLabels(tCompanyInfor.status, 'yes_no', '')}
				</td>
				<td>
					${tCompanyInfor.email}
				</td>
				<shiro:hasPermission name="tcompanyinfo:tCompanyInfor:edit"><td>
    				<a onclick="toUpdata('${tCompanyInfor.id}','${tCompanyInfor.companyId}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/tcompanyinfo/tCompanyInfor/delete?id=${tCompanyInfor.id}" onclick="return confirmx('确认要删除吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>