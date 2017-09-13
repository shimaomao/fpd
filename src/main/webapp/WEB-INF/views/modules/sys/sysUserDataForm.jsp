<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>初始化数据管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			// 担保-机构
			var zNodes1=[
					<c:forEach items="${officeList.dbList}" var="office">
					{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},		            
					</c:forEach>];
			// 初始化树结构
			var tree1 = $.fn.zTree.init($("#dbTree"), setting, zNodes1);
			// 不选择父节点
			tree1.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			tree1.expandAll(true);
			
			var zNodes0=[
				{id:"0", pId:"0", name:"全部"},
				<c:forEach items="${formTpls }" var="form">
				{id:"${form.id}", pId:"0", name:"${form.sname}"},		            
				</c:forEach>];
			// 初始化树结构
			var tree0 = $.fn.zTree.init($("#formTree"), setting, zNodes0);
			// 不选择父节点
			tree0.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			tree0.expandAll(true);				
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysUserData/">初始化数据列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysUserData/form?id=${sysUserData.id}">初始化数据<shiro:hasPermission name="sys:sysUserData:edit">${not empty sysUserData.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysUserData:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysUserData" action="${ctx}/sys/sysUserData/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<%-- <div class="control-group" id="dbTreeDiv">
			<label class="control-label">模板：</label>
			<div class="controls">				
				<c:forEach items="${formTpls }" var="data">
					<input type="checkbox" id="${data.id }" name="formTpl">${data.sname }<br>
				</c:forEach>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group" id="dbTreeDiv">
			<label class="control-label">模板：</label>
			<div class="controls">				
				<div id="formTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="dbTreeDiv">
			<label class="control-label">租户：</label>
			<div class="controls">				
				<div id="dbTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">流程文件：</label>
			<div class="controls">
				<select id="fileName" name="fileName" class="input-xlarge " style="width:188px;">
					<c:forEach items="${fileList }" var="file">
						<option value="${file }">${file }</option>
					</c:forEach>
				</select>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">数据项：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:input path="status" htmlEscape="false" maxlength="1" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关联ID：</label>
			<div class="controls">
				<form:input path="relid" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${sysUserData.user.id}" labelName="user.name" labelValue="${sysUserData.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户ID：</label>
			<div class="controls">
				<form:input path="organId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysUserData:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> --%>
	</form:form>
</body>
</html>