<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关联模板初始化</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					debug:true;
					var ids1 = "",nodes1 = relatetree.getCheckedNodes(true);
					for(var i=1; i<nodes1.length; i++) {
						ids1 += nodes1[i].id + ";";
					}						
					$('#relate').val(ids1);
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
			//关联模板
			var relateNodes=[
				{id:"0", pId:"0", name:"全部"},
				<c:forEach items="${DfformTpl}" var="form">
				{id:"${form.id}", pId:"0", name:"${form.sname}-${form.name}"},		            
				</c:forEach>];				
			// 初始化树结构
			var relatetree = $.fn.zTree.init($("#relateTree"), setting, relateNodes);
			// 不选择父节点
			relatetree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			relatetree.expandAll(true);		
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/init/sysInitFormtpl/">模板初始化列表</a></li>
		<li class="active"><a>关联模板初始化</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysInitFormtpl" action="${ctx}/init/sysInitFormtpl/saveRelate" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">关联模板：</label>
			<div class="controls">				
				<div id="relateTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<input type="hidden" id="relate" name="relate"/>		
		<div class="form-actions">
			<shiro:hasPermission name="init:sysInitFormtpl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>