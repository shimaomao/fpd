<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>产品业务功能配置</title>
		<meta name="decorator" content="default"/>
		<%@include file="/WEB-INF/views/include/treeview.jsp" %>
		<style type="text/css">
			.content_block {
				width:100%;
				border-bottom:0px #999999 solid;
				margin-top:0px;
				margin-bottom:10px;
				padding-bottom:10px;
			}
			.content_block .ax_ico {
				float:left;
				margin-right: 10px;
			}
			.content_block .ax_title {
			    font-family: '微软雅黑 Regular','微软雅黑';
			    font-weight: 400;
			    font-style: normal;
			    font-size: 13px;
			    color: rgb(0, 0, 0);
			    text-align: center;
			    line-height: 20px;
				height:20px;
				float:left;
			}	
		</style>
		
		<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					/* var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2); */
					/* loading('正在提交，请稍等...'); */
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
			var ids = "${productMenus}".split(",");
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
	<c:set var="navPC" value="qxpz"></c:set>
	<%@ include file="/WEB-INF/views/modules/product/include/nav.jsp" %>
		<div class="content_block">
			<div class="ax_title">产品菜单配置-(产品名称：${tProduct.name})</div>
			<div style="clear:left;"></div>
		</div>
		<form:form id="inputForm" modelAttribute="role" action="${ctx}/product/tProduct/savebuness" method="post" class="form-horizontal">
		<input type="hidden" name="tProductId" value="${tProduct.id }" />
		<input type="hidden" name="modelType" value="step5" />
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品菜单配置:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="menuIds"/>
			</div>
		</div>
		<div class="container">
			<div class="row clearfix">
				<div class="span12">
					<div class="clearfix" style="margin-bottom: 50px;">
						<input id="btnCancel" class="btn btn-primary" type="button" value="上一步" onclick="history.go(-1)"/>
						<shiro:hasPermission name="product:tProduct:edit">
						<input id="btnSubmit" class="btn btn-primary" type="submit"  value="下一步"/>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>