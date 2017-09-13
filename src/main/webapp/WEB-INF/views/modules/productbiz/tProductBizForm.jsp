<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务功能管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
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
			
			// 用户-菜单
			var zNodes=[<c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${not empty menu.parent.id?menu.parent.id:0}", name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 不选择父节点
			tree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认选择节点
			var ids = "${tProductBiz.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/productbiz/tProductBiz/list">业务列表</a></li> --%>
		<li><a href="${ctx}/productbiz/tProductBiz/tree">业务功能列表</a></li>
		<li class="active"><a href="${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}&parentId=${tProductBiz.parent.id}">业务功能<shiro:hasPermission name="productbiz:tProductBiz:edit">${not empty tProductBiz.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="productbiz:tProductBiz:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tProductBiz" action="${ctx}/productbiz/tProductBiz/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">主业务:</label>
			<div class="controls">
                <sys:treeselect id="tProductBiz" name="parent.id" value="${tProductBiz.parent.id}" labelName="parent.bizName" labelValue="${tProductBiz.parent.bizName}"
					title="业务" url="/productbiz/tProductBiz/treeData" extId="${tProductBiz.id}" />
					<span class="help-inline">* 若为空则为根业务节点</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务操作名：</label>
			<div class="controls">
				<form:input path="bizName" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务编码：</label>
			<div class="controls">
				<form:select path="bizCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('df_model_bapplication')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('productbiz_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge " >
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('productbiz_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统表单：</label>
			<div class="controls">
				<form:input path="formtpl" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">表单模块：</label>
			<div class="controls">
				<form:select path="category" class="required input-xlarge " >
					<form:option value="" label="" />
					<c:forEach items="${categoryList}" var="item">
						<form:option value="${item.category }" label="${item.categoryName }" />
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流程节点 ：</label>
			<div class="controls">
				<form:select path="isFlow" class="input-xlarge " >
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('is_flow')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源路径：</label>
			<div class="controls">
				<form:input path="bizUrl" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资源参数：</label>
			<div class="controls">
				<form:input path="bizParam" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型:</label>
			<div class="controls">
				<form:input path="isYwtype" htmlEscape="false" maxlength="50" class="required digits input-small"/>
				<span class="help-inline">0:默认；1：担保机构；2：贷款机构 </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50" class="required digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">可见:</label>
			<div class="controls">
				<form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline">是否显示</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品菜单配置:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="menuIds"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="productbiz:tProductBiz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>