<%@page import="org.json.JSONObject"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>征信管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnUpdatDB").click(function(){
				$.ajax({
					type:"POST",
		        	url: "${ctx}/wind/creditchecking/tCreditChecking/ajaxInitDB",
		            dataType: "json",
		            success: function(data){
		            	if(data){
		            		top.$.jBox.tip("同步成功!");
		            	}else{
		            		top.$.jBox.tip("同步失败!");
		            	}
		            }
				});
			});
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
		<li class="active"><a href="${ctx}/wind/creditchecking/tCreditChecking/list?db=${tCreditChecking.db}&type=${tCreditChecking.type}">征信列表</a></li>
		<%-- <shiro:hasPermission name="wind:creditchecking:tCreditChecking:edit"><li><a href="${ctx}/wind/creditchecking/tCreditChecking/form">征信添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tCreditChecking" action="${ctx}/wind/creditchecking/tCreditChecking/list" method="post" class="breadcrumb form-search">
		<input name="type" type="hidden" value="${tCreditChecking.type}"/>
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>征信类型：</label>
				<select name="type" class="input-medium">
					<option value=""></option>
					<c:forEach var="ieType" items="${ieAdapterTypes}">
					<option value="${ieType['key'] }" <c:if test="${ieType['key'] eq tCreditChecking.type}"> selected </c:if>>${ieType.name }</option>
					</c:forEach>
				</select>
			</li> --%>
			<li><label>数据源：</label>
				<select name="db" class="input-medium">
					<option value=""></option>
					<c:forEach var="ieDB" items="${ieAdapterDBs}">
					<option value="${ieDB['key'] }" <c:if test="${ieDB['key'] eq tCreditChecking.db}"> selected </c:if>>${ieDB.name }</option>
					</c:forEach>
				</select>
			</li>
			<li class="dis"><label>子类型：</label>
				<select name="typeSub" class="input-medium">
					<option value=""></option>
					<c:forEach var="ieSub" items="${ieAdapters}">
					<option value="${ieSub['sub'] }" <c:if test="${ieSub['sub'] eq tCreditChecking.typeSub}"> selected </c:if>>${ieSub.remark }</option>
					</c:forEach>
				</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnUpdatDB" class="btn btn-primary" type="button" title="同步征信系统" value="更新同步"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"></sys:message>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>关联对象</th>
				<th>征信类型</th>
				<th>子类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="wind:creditchecking:tCreditChecking:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tCreditChecking">
			<tr>
				<td><a href="${ctx}/wind/creditchecking/tCreditChecking/view?id=${tCreditChecking.id}">
					<c:if test="${tCreditChecking.type eq 'person'}">
						${tCreditChecking.employee.name}
					</c:if>
					<c:if test="${tCreditChecking.type eq 'corporation'}">
						${tCreditChecking.company.name}
					</c:if>
				</a></td>
				<td>
					${fns:getDictLabel(tCreditChecking.type , 'c_type', '')}
				</td>
				<td>
					<c:if test="${tCreditChecking.type eq 'person'}">
						${fns:getDictLabel(tCreditChecking.typeSub , 'c_person', '')}
					</c:if>
					<c:if test="${tCreditChecking.type eq 'corporation'}">
						${fns:getDictLabel(tCreditChecking.typeSub , 'c_corporation', '')}
					</c:if>
				</td>
				<td>
					<fmt:formatDate value="${tCreditChecking.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<%-- <td>
					${tCreditChecking.data}
				</td> --%>
				<td>
					${tCreditChecking.remarks}
				</td>
				<shiro:hasPermission name="wind:creditchecking:tCreditChecking:edit"><td>
    				<a href="${ctx}/wind/creditchecking/tCreditChecking/view?id=${tCreditChecking.id}&db=${tCreditChecking.db}&type=${tCreditChecking.type}">查看</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>