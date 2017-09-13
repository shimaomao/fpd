<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字段库维护管理</title>
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
		
		function toAdd(){
			 var url = "${ctx}/form/formtype/dfColumnDefine/form";
				location.href = url;
		}

		function toDelete(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alertx("请选择一条数据!");
			  	return;
			  }
		    var url = "${ctx}/form/formtype/dfColumnDefine/delete?id="+array[0];
		    return confirmx('确认要删除该数据吗？',url);
		}
		
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			  if(array.length==0){
			  	alertx("请选择一条数据!");
			  	return;
			  }
			  var url = "${ctx}/form/formtype/dfColumnDefine/form?id="+array[0];
				location.href = url;
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/form/formtype/dfColumnDefine/">字段库维护列表</a></li>
		<shiro:hasPermission name="form:formtype:dfColumnDefine:edit"><li><a href="${ctx}/form/formtype/dfColumnDefine/form">字段库维护添加</a></li></shiro:hasPermission>
	</ul>

  <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		              产品中心>代码生成>字段库
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="dfColumnDefine" action="${ctx}/form/formtype/dfColumnDefine/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>模块：</label>
				<form:select path="category" class="input-medium ">
					<form:option value=""></form:option>
					<c:forEach items="${categoryList}" var="cg">
						<form:option value="${cg.category}" label="${cg.categoryName }"></form:option>
					</c:forEach>
				</form:select>
			</li>
			<li><label>字段名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>字段名称</th>
				<th>产品模块标识</th>
				<th>字段所属模块名称</th>
				<th>实体类属性</th>
				<th>数据表字段</th>
				<th>字段的表单数据源url</th>
<%-- 				<shiro:hasPermission name="form:formtype:dfColumnDefine:edit"><th>操作</th></shiro:hasPermission> --%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="dfColumnDefine">
			<tr>
			    <td>
					 <input type="radio" name="cid" id="cid" value="${dfColumnDefine.id}"/>
				</td>
				<td><a href="${ctx}/form/formtype/dfColumnDefine/form?id=${dfColumnDefine.id}">
					${dfColumnDefine.name}
				</a></td>
				<td>
					${dfColumnDefine.category}
				</td>
				<td>
					${dfColumnDefine.categoryname}
				</td>
				<td>
					${dfColumnDefine.filed}
				</td>
				<td>
					${dfColumnDefine.colname}
				</td>
				<td>
					${dfColumnDefine.formurl}
				</td>
<%-- 				<shiro:hasPermission name="form:formtype:dfColumnDefine:edit"><td> --%>
<%--     				<a href="${ctx}/form/formtype/dfColumnDefine/form?id=${dfColumnDefine.id}">修改</a> --%>
<%-- 					<a href="${ctx}/form/formtype/dfColumnDefine/delete?id=${dfColumnDefine.id}" onclick="return confirmx('确认要删除该字段库维护吗？', this.href)">删除</a> --%>
<%-- 				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>