<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>从业人员工作经历管理</title>
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
		function toAddExperience(){
			showjBox("添加工作经历", "${ctx}/sys/experience/form?employId="+'${experience.employId}');
		}
		
		//修改
		function toUpdataExperience(id){
			showjBox("更新工作经历", "${ctx}/sys/experience/form?id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysOfficeEmployee/form?id=${experience.employId}">从业人员基本信息</a></li>
		<li><a href="${ctx}/sys/study/list?employId=${experience.employId}">学习经历列表</a></li>
		<li class="active"><a href="${ctx}/sys/experience/">工作经历列表</a></li>
		<li><a href="${ctx}/sys/family/list?employId=${experience.employId}"">家庭成员列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="experience" action="${ctx}/sys/experience/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li> -->
		</ul>
	</form:form>
	<table class="table table-bordered">
   	   <tr>
   	    <td>
		    <a  class="btn btn-primary"  onclick="toAddExperience();">增加</a>
   	   </td>
   	   </tr>
   	</table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th><th>时间</th><th>单位</th><th>职务</th>
				<shiro:hasPermission name="sys:experience:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="experience" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td>${experience.workDate}</td>
				<td>
					${experience.unit}
				</td>
				<td>
					${experience.position}
				</td>
				<shiro:hasPermission name="sys:experience:edit"><td>
    				<a onclick="toUpdataExperience('${experience.id}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/sys/experience/delete?id=${experience.id}" onclick="return confirmx('确认要删除该从业人员工作经历吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>