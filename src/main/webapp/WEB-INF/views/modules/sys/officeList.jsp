<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
	    var offid = '${fns:getUser().company.parent.id}';//所在公司父ID
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '"+offid+"'}";
			addRow("#treeTableList", tpl, data, rootId, true);
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						   dict: {type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type),primaryPerson: getDictLabel(${fns:toJson(fns:getDictList('company_type'))}, row.primaryPerson)},
						   pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	
		<li class="active"><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
		<c:if test="${empty office.id }">
			<c:if test="${curUser.admin}"><shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${curoffice.id}">机构添加</a></li></shiro:hasPermission></c:if>
			<c:if test="${!curUser.admin}"><shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${curoffice.id}">机构添加</a></li></shiro:hasPermission></c:if>
		</c:if><c:if test="${not empty office.id }">
			<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/form?parent.id=${office.id}">机构添加</a></li></shiro:hasPermission>
		</c:if>
		<li><a href="${ctx}/sys/sysOfficeDetail/form?id=${sysOfficeDetail.id}&Pid=${office.id}">机构详情${not empty sysOfficeDetail.id?'查看':'添加'}</a></li>
		<li><a href="${ctx}/sys/sysOfficePartner/partners?pid=${office.id}">股东信息</a></li>
		<li><a href="${ctx}/sys/sysOfficeEmployee/list?pid=${office.id}">从业人员信息</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>机构名称</th><th>归属区域</th><th>机构编码</th><th>公司类型</th><th>机构类型</th><th>备注</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<c:set var="curUser" value="${fns:getUser()}"></c:set>
	<c:if test="${curUser.admin}">
	<div>
		<input type="button" value="初始化青海数据" onclick="ajaxInitQH()"/>
	</div>
	</c:if>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/sys/office/form?id={{row.id}}">{{row.name}}</a></td>
			<td>{{row.area.name}}</td>
			<td>{{row.code}}</td>
			<td>{{dict.primaryPerson}}</td>
			<td>{{dict.type}}</td>
			<td>{{row.remarks}}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="${ctx}/sys/office/form?id={{row.id}}">修改</a>
				{{^row.bsys}}
					<a href="${ctx}/sys/office/delete?id={{row.id}}" onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)">删除</a>
				{{/row.bsys}}
				<a href="${ctx}/sys/office/form?parent.id={{row.id}}">添加下级机构</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
	<script type="text/javascript">
		function ajaxInitQH(){
			$.ajax({
			    type:'POST',
			    url: "${ctx}/sys/office/ajaxInitQH?type=2",
			    success: function(data){
			    	if(data == true){
			    		alert("初始化成功！");
			    	}
			    }
			});
		}
	</script>
</body>
</html>