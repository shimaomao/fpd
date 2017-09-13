<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>黑名单</title>
	<meta name="decorator" content="default"/>
	 <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//解除黑名单申请
		function removeBlack(){
			 var array = getCheckValue("cid");

		    if(array.length==0){
		    	alertx("请选择一个客户!");
		    	return;
		    }
			<%-- $.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_CUSTOMER_UNBLACK.getKey()%>"},
				function(data) {
					if(!data){
						showTip("无此业务");
						return;
					}
					if(!data.procDefId){
						showTip("此业务没有配置流程");
						return;
					}
					var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_company&businessId="+array[0];
				  	location.href = url;
				}
			);   --%>
		
			var url = "${ctx}/company/tCompany/removeB?id="+array[0];
			location.href = url;					  
		}
		
		
		//------ 产品——客户  关联
		//添加客户与产品的关联
		 function customerTreeselectCallBack(v, h, f){
			var ids = $('#customerId').val();
			if(ids){
				var url = "${ctx}/company/tCompany/addB?ids="+ids;
				location.href = url;
			}
		}
   </script>
</head>
<body>

	<div style="display: none;">
		<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="" extId="1" checked="true"
			title="所有客户" url="/company/tCompany/treeDataB" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/company/tCompany/blackList">黑名单</a></li>
	</ul>
<%--     <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>客户管理>黑名单
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/company/tCompany/blackList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table class="table table-bordered">
		<tr>
			<td>
	   			<input  class="btn btn-primary" type="submit" onclick="$('#customerButton').click();" value="添加黑名"  title="从所有客户里添加"/>
	   			<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="removeBlack();" value="解除黑名"/>
			</td>
		</tr>
    </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
			    <th>选择</th>
				<th>名称</th>
				<th>客户类型</th>
				<th>证件号码</th>
				<th>证件类型</th>
				<th>联系方式</th>
				<th>邮箱</th>
				<th>黑名理由</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customer">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
			    <td>
					 <input type="radio" name="cid" id="cId" value="${customer.id}"/>
				</td>
				<td>
					${customer.name}
				</td>
				<td>
					${fns:getDictLabel(customer.customerType, 'customer_type', '')}
				</td>
				<td>
					${customer.cardNum}
				</td>
				<td>
					${fns:getDictLabel(customer.cardType, 'card_type', '')}
				</td>
				<td>
					${customer.phone}
				</td>
				<td>
					${customer.email}
				</td>
				<td>
					${customer.reason}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>