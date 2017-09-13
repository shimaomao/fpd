<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>受理咨询管理</title>
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
		
		
		function toUpdate(){
			 var array = getCheckValue("advanceID");
			  if(array.length==0){
			  	alertx("请选择一条咨询!");
			  	return;
			  }
			  var url = "${ctx}/advisory/tAdvisory/form?id="+array[0];
				location.href = url;
		}
		
		
		function toAdd(){
			 var url = "${ctx}/advisory/tAdvisory/form";
				location.href = url;
		}
		
		function toDelete(){
			 var array = getCheckValue("advanceID");
			  if(array.length==0){
			  	alertx("请选择一条咨询!");
			  	return;
			  }
		    var url = "${ctx}/advisory/tAdvisory/delete?id="+array[0];
		    return confirmx('确认要删除该咨询吗？',url);
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/advisory/tAdvisory/">受理咨询列表</a></li>
		<shiro:hasPermission name="advisory:tAdvisory:edit"><li><a href="${ctx}/advisory/tAdvisory/form">受理咨询添加</a></li></shiro:hasPermission>
	</ul>
    <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>咨询受理
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="tAdvisory" action="${ctx}/advisory/tAdvisory/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>联系电话：</label>
				<form:input path="customerTel" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	 	<shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr><td>
			    <a class="btn btn-primary" onclick="toAdd();">添加</a>
				<a class="btn btn-primary" onclick="toUpdate();">修改</a>
			  	<a class="btn btn-primary" onclick="toDelete();">删除</a>
			</td></tr>
	  </table>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>选择</th>
			    <th>客户名称</th>
			    <th>联系电话</th>
			    <th>客户类型</th>
			    <th>证件号码</th>
				<th>更新时间</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tAdvisory">
			<tr>
			     <td>
					 <input type="radio" name="advanceID" id="advanceID" value="${tAdvisory.id}" />
				</td>
				<td><a href="${ctx}/advisory/tAdvisory/form?id=${tAdvisory.id}">
					  ${tAdvisory.customerName}
					  </a>
				</td>
				<td>
					  ${tAdvisory.customerTel}
				</td>
				<td>
					  ${fns:getDictLabel(tAdvisory.customerType, 'customer_type', '')}
				</td>
				<td>
					 ${tAdvisory.cardNo}
				</td>
				<td>
					  <fmt:formatDate value="${tAdvisory.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tAdvisory.remarks}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>