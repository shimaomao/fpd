<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<title>产品业务功能配置</title>
		<meta name="decorator" content="default"/>
			<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
    	function updateSort(mdType) {
    		if(mdType == 'refresh'){
    			$("#modelType").val("step5");
    		}else{
    			$("#modelType").val("step6");
    		}
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort?ftype=product&relId=${tProduct.id}");
	    	$("#listForm").submit();
    	}
    	
    	function toOpenUrl(menuId){
    		$.jBox.open("iframe:${ctx}/sys/menu/form?ftype=product&relId=${tProduct.id}&id="+menuId, "产品-菜单修改", 830, $(top.document).height()-200,{
    			buttons:{"刷新":"refresh", "关闭":true/* , "保存":"save" */}, 
    			bottomText:"产品-菜单",
    			submit:function(v, h, f){
    				var ifrWin = h.find("iframe")[0].contentWindow;
    				if(v=="refresh"){
    					ifrWin.location.reload(true);
    					return false;
    	            }else if(v=="return"){
    	            	ifrWin.history.go(-1);
    	            	ifrWin.location.reload();
    					return false;
    	            }else if(v=="save"){
    	            	/* ifrWin.btnSubmit.click(); */
    					return true;
    	            }
    			}, loaded:function(h){
    				$(".jbox-content", top.document).css("overflow-y","hidden");
    			}
    		});
    	}
	</script>
</head>
<body>
	<c:set var="navPC" value="cdpz"></c:set>
	<%@ include file="/WEB-INF/views/modules/product/include/nav.jsp" %>
		<div class="content_block">
			<div class="ax_title">产品菜单配置-(产品名称：${tProduct.name})</div>
			<div style="clear:left;"></div>
		</div>
		<hr/>
		<form id="listForm" method="post">
		<input id="modelType" type="hidden" name="modelType" value="step6" />
		<table id="treeTable" class="table table-striped table-bordered table-condensed hide">
			<thead><tr><th>名称</th><th>链接</th><th style="text-align:center;">排序</th><th>可见</th><th>权限标识</th><shiro:hasPermission name="sys:menu:edit"><th>操作</th></shiro:hasPermission></tr></thead>
			<tbody><c:forEach items="${list}" var="menu">
				<tr id="${menu.id}" pId="${menu.parent.id ne '4973a2e8a4e645c8bee88f1fe89286d7'?menu.parent.id:'0'}">
				<%-- <tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}"> --%>
					<td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i><a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a></td>
					<td title="${menu.href}">${fns:abbr(menu.href,30)}</td>
					<td style="text-align:center;">
						<shiro:hasPermission name="sys:menu:edit">
							<input type="hidden" name="ids" value="${menu.id}"/>
							<input name="sorts" type="text" value="${menu.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="sys:menu:edit">
							${menu.sort}
						</shiro:lacksPermission>
					</td>
					<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
					<td title="${menu.permission}">${fns:abbr(menu.permission,30)}</td>
					<shiro:hasPermission name="sys:menu:edit"><td nowrap>
						<a href="javascript:void(0);" onclick="toOpenUrl('${menu.id}')" >修改</a>
						<%-- <a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
						<a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a>  --%>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach></tbody>
		</table>
		<div class="container">
			<div class="row clearfix">
				<div class="span12">
					<div class="clearfix" style="margin-bottom: 50px;">
						<input id="btnCancel" class="btn btn-primary" type="button" value="上一步" onclick="history.go(-1)"/>
						<shiro:hasPermission name="sys:menu:edit">
						<input id="btnSave" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort('refresh');"/>
						</shiro:hasPermission>
						<shiro:hasPermission name="product:tProduct:edit">
						<input id="btnSubmit" class="btn btn-primary" type="button"  value="下一步" onclick="updateSort();"/>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	 </form>
</body>
</html>