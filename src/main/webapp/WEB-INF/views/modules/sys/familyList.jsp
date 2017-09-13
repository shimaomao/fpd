<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>从业人员家庭成员管理</title>
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
		function toAddFamily(){
			showjBox("添加家庭成员", "${ctx}/sys/family/form?employId="+'${family.employId}');
		}
		
		//修改
		function toUpdataFamily(id){
			showjBox("更新家庭成员", "${ctx}/sys/family/form?id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	 	<li><a href="${ctx}/sys/sysOfficeEmployee/form?id=${family.employId}">从业人员基本信息</a></li>
		<li><a href="${ctx}/sys/study/list?employId=${family.employId}"">学习经历列表</a></li>
		<li><a href="${ctx}/sys/experience/list?employId=${family.employId}">工作经历</a></li>
		<li class="active"><a href="${ctx}/sys/family/">从业人员家庭成员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="family" action="${ctx}/sys/family/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li> --%>
		</ul>
	</form:form>
	<table class="table table-bordered">
   	   <tr>
   	    <td>
		    <a  class="btn btn-primary"  onclick="toAddFamily();">增加</a>
   	   </td>
   	   </tr>
   	</table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th><th>关系</th><th>姓名</th><th>性别</th><th>现工作或学习单位</th><th>现任职务</th>
				<shiro:hasPermission name="sys:family:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="family" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td>${family.type}</td>
				<td>${family.name}</td>
				<td>
					${family.sex}
				</td>
				<td>
					${family.unit}
				</td>
				<td>
					${family.job}
				</td>
				<shiro:hasPermission name="sys:family:edit"><td>
    				<a onclick="toUpdataFamily('${family.id}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/sys/family/delete?id=${family.id}" onclick="return confirmx('确认要删除该从业人员家庭成员吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>