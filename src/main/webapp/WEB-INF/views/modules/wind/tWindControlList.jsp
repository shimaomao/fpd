<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控模型管理</title>
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
		
		
		
		
		
		function toAdd(){
			 var url = "${ctx}/wind/tWindControl/form";
				location.href = url;
		}
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条数据!");
			    	return;
			    }
			    var url = "${ctx}/wind/tWindControl/form?id="+array[0];
				location.href = url;
		}
		
		function toDelete(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条数据!");
			    	return;
			    }
		       var url = "${ctx}/wind/tWindControl/delete?id="+array[0];
		       return confirmx('确认要删除该数据吗？',url);
		}
		
		
		
		
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/wind/tWindControl/">风控模型列表</a></li> --%>
<%-- 		<shiro:hasPermission name="wind:tWindControl:edit"><li><a href="${ctx}/wind/tWindControl/form">风控模型添加</a></li></shiro:hasPermission> --%>
<!-- 	</ul> -->
	
	<div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
<%-- 		 <%=Cons.LocationUrl.URL.replace("-",">")%> --%>
		              系统设置>系统设置>风控模型
	   </div>
	</div>
	
	
	
	<form:form id="searchForm" modelAttribute="tWindControl" action="${ctx}/wind/tWindControl/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模型名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<shiro:hasPermission name="product:tProduct:edit">
	  <table class="table table-bordered">
			<tr><td>
			    <a class="btn btn-primary" onclick="toAdd();">添加</a>
				<a class="btn btn-primary" onclick="toUpdate();">修改</a>
			  	<a class="btn btn-primary" onclick="toDelete();">删除</a>
			</td></tr>
	  </table>
	</shiro:hasPermission>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>选择</th>
				<th>模型名称</th>
				<th>路径</th>
				<th>参数</th>
				<th>状态</th>
				<th>备注</th>
<%-- 				<shiro:hasPermission name="wind:tWindControl:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tWindControl">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
			      <td>
					 <input type="radio" name="cid" id="cId" value="${tWindControl.id}"/>
				</td>
				<td><a href="${ctx}/wind/tWindControl/form?id=${tWindControl.id}">
					${tWindControl.name}
				</a></td>
				<td>
					${tWindControl.url}
				</td>
				<td>
					${tWindControl.param}
				</td>
				<td>
					${fns:getDictLabel(tWindControl.status, 'wind_status', '')}
				</td>
				<td>
					${tWindControl.remarks}
				</td>
<%-- 				<shiro:hasPermission name="wind:tWindControl:edit"><td> --%>
<%--     				<a href="${ctx}/wind/tWindControl/form?id=${tWindControl.id}">修改</a> --%>
<%-- 					<a href="${ctx}/wind/tWindControl/delete?id=${tWindControl.id}" onclick="return confirmx('确认要删除该风控模型吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>