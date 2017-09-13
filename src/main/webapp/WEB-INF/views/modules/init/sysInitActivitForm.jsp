<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>流程初始化管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var type = $("input[name='officeType']:checked").val();
					if(type==1){
						var ids1 = "",nodes1 = tree1.getCheckedNodes(true);
						for(var i=0; i<nodes1.length; i++) {
							ids1 += nodes1[i].id + ";";
						}
						$('#officeName').val(ids1);
						$('#fileName').val($('#fileNameDb').val());
					}else if(type==2){
						var ids2 = "",nodes2 = tree2.getCheckedNodes(true);
						for(var i=0; i<nodes2.length; i++) {
							ids2 += nodes2[i].id + ";";
						}
						$('#officeName').val(ids2);
						$('#fileName').val($('#fileNameXd').val());
					}
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
			
			// 小贷-机构
			var zNodes2=[
					<c:forEach items="${officeList.xdList}" var="office">
					{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},		            
					</c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#xdTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			tree2.expandAll(true);
			
		});
		
		function changeOfficeType(){
			var type = $("input[name='officeType']:checked").val();
			if(type==1){
				$('#dbTreeDiv').css("display","");
				$('#xdTreeDiv').css("display","none");
				$('#fileDivDb').css("display","");
				$('#fileDivXd').css("display","none");
			}else if(type==2){
				$('#dbTreeDiv').css("display","none");
				$('#xdTreeDiv').css("display","");
				$('#fileDivDb').css("display","none");
				$('#fileDivXd').css("display","");
			}
		}
				
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/init/sysInitActivit/">流程初始化列表</a></li>
		<li class="active"><a href="${ctx}/init/sysInitActivit/form?id=${sysInitActivit.id}">流程初始化<shiro:hasPermission name="init:sysInitActivit:edit">${not empty sysInitActivit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="init:sysInitActivit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysInitActivit" action="${ctx}/init/sysInitActivit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">租户类型：</label>
			<div class="controls">
				<form:radiobuttons path="officeType" items="${fns:getDictList('company_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onclick="changeOfficeType()"/><span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="fileDivDb" style="display: none;">
			<label class="control-label">流程文件名：</label>
			<div class="controls">
				<select id="fileNameDb" name="fileNameDb" class="input-xlarge " style="width:188px;" required="required">
					<c:forEach items="${fileDb }" var="file">
						<option value="${file }">${file }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="fileDivXd" style="display: none;">
			<label class="control-label">流程文件名：</label>
			<div class="controls">
				<select id="fileNameXd" name="fileNameXd" class="input-xlarge " style="width:188px;" required="required">
					<c:forEach items="${fileXd }" var="file">
						<option value="${file }">${file }</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<input type="hidden" value="" id="fileName" name="fileName"/>	

		<div class="control-group" id="dbTreeDiv" style="display: none;">
			<label class="control-label">租户：</label>
			<div class="controls">				
				<div id="dbTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" id="xdTreeDiv" style="display: none;">
			<label class="control-label">租户：</label>
			<div class="controls">				
				<div id="xdTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<input type="hidden" value="" id="officeName" name="officeName"/>
		<div class="control-group">
			<label class="control-label">流程名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required" required="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="init:sysInitActivit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>