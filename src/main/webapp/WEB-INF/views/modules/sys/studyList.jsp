<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学习经历管理</title>
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
		function toAddStudy(){
			showjBox("添加学习经历", "${ctx}/sys/study/form?employId="+'${study.employId}');
		}
		
		//修改
		function toUpdataStudy(id){
			showjBox("更新学习经历", "${ctx}/sys/study/form?id="+id);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	    <li><a href="${ctx}/sys/sysOfficeEmployee/form?id=${study.employId}">从业人员基本信息</a></li>
		<li class="active"><a href="${ctx}/sys/study/">学习经历列表</a></li>
		<li><a href="${ctx}/sys/experience/list?employId=${study.employId}">工作经历</a></li>
		<li><a href="${ctx}/sys/family/list?employId=${study.employId}">家庭主要成员情况</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="study" action="${ctx}/sys/study/" method="post" class="breadcrumb form-search">
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
		    <a  class="btn btn-primary"  onclick="toAddStudy();">增加</a>
   	   </td>
   	   </tr>
   	</table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<tr><th>序号</th><th>时间</th><th>学校</th><th>专业</th><th>学历</th>
				<shiro:hasPermission name="sys:study:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="study" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<td>${study.studyDate} 至 ${study.studyEndDate}</td>
				<td>
					${study.school}
				</td>
				<td>
					${study.professional}
				</td>
				<td>
					${study.professional}
				</td>
				<shiro:hasPermission name="sys:study:edit">
				<td>
    				<a onclick="toUpdataStudy('${study.id}');" style="cursor: pointer;">修改</a>
					<a href="${ctx}/sys/study/delete?id=${study.id}" onclick="return confirmx('确认要删除该学习经历吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>