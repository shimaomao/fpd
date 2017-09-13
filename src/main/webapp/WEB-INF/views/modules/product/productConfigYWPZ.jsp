<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品业务配置</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/modules/productConfig/front/include/head2.jsp" %>
	<script type="text/javascript" src="${ctxStatic}/checkTable.js"></script>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable21").treeTable({expandLevel : 5}).show();
			$("#treeTable22").treeTable({expandLevel : 5}).show();
			$("#treeTable23").treeTable({expandLevel : 5}).show();
			init();
		});

	    function openWin(url, title){
			top.$.jBox.open("iframe:"+url, 
	    			title, $(top.document).width()-200,$(top.document).height()-150,{
	    		buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
	    				ifrWin.location.reload(true);
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="return"){
	                	ifrWin.history.go(-1);
	                	ifrWin.location.reload();
	    				return false;
	                }
	    		}, loaded:function(h){
	    			$(".jbox-content", top.document).css("overflow-y","hidden");
	    		}
	    	});

		}
	</script>
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
</head>
<body>
	<%-- <c:set var="navPC" value="ywpz"></c:set>
	<%@ include file="/WEB-INF/views/modules/product/include/nav.jsp" %>
	
	<div class="content_block">
		<div class="ax_ico"><img src="${ctxStatic}/images/pz.png" width="20px" height="20px"/></div>
		<div class="ax_title">产品业务流程配置-(产品名称：${tProduct.name})</div>
		<div style="clear:left;"></div>
	</div> --%>
	
	<c:set var="stpsItem" value="2" />
	<c:set var="ptagItem" value="productConfigYWPZ"></c:set>
	<%@ include file="/WEB-INF/views/modules/productConfig/include/ptag.jsp" %>
	<div class="box-down">
		<div class="box">
			<%@ include file="/WEB-INF/views/modules/productConfig/include/stepsCPPZ.jsp" %>
		</div>
	</div>

	<form:form id="inputForm" action="${ctx}/product/tProduct/saveProductConfigYWPZ" method="post">
		<input type="hidden" value="${tProduct.id}" name="id" />
		<input type="hidden" name="modelType" value="step3" />
		<input type="hidden" name="ids" id="dids" />
	
		<table class="table table-bordered table-condensed">
			<thead><tr>
				<th width="10%">业务模块</th>
				<th width="90%"><input class="selAll"  type="checkbox"  name="selAll" style="cursor: pointer;"/>业务功能</th>
			</tr></thead>
			<tbody>
				<tr>
					<td>
						<input class="selAllDQ" type="checkbox"  name="selAllDQ"  style="cursor: pointer;"/>贷前管理
					</td>
					<td>
						<table id="treeTable21" class="table table-bordered table-condensed hide">
							<tbody>
							<c:forEach items="${dqList}" var="tProductBiz">
								<tr id="${tProductBiz.id}" pId="${(tProductBiz.parent.id ne '1')?tProductBiz.parent.id:'0'}" class="bizType${tProductBiz.type }">
									<td width="100%" nowrap><input class="dqIds" type="checkbox" name="dqIds" value="${tProductBiz.id}" <c:forEach items="${ckdqList}" var="ckBiz" varStatus="idx"><c:if test="${ckBiz.id eq tProductBiz.id}">checked="checked"</c:if></c:forEach>/>${tProductBiz.bizName}</td>
									<%-- <shiro:hasPermission name="productbiz:tProductBiz:edit"><td width="5%" nowrap>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}', '贷前管理-修改')" href="javascript:void(0);">修改</a>
										<a href="${ctx}/productbiz/tProductBiz/delete?id=${tProductBiz.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?parentId=${tProductBiz.id}', '贷前管理-添加下级菜单')" href="javascript:void(0);">添加下级菜单</a> 
									</td></shiro:hasPermission>--%>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<input class="selAllDZ" type="checkbox"  name="selAllDZ" value="1" style="cursor: pointer;"/>贷中管理
					</td>
					<td>
						<table id="treeTable22" class="table table-bordered table-condensed hide">
							<tbody><c:forEach items="${dzList}" var="tProductBiz">
								<tr id="${tProductBiz.id}" pId="${(tProductBiz.parent.id ne '1')?tProductBiz.parent.id:'0'}" class="bizType${tProductBiz.type }">
									<td width="100%" nowrap><input class="dzIds" type="checkbox" name="dzIds" value="${tProductBiz.id}" <c:forEach items="${ckdzList}" var="ckBiz" varStatus="idx"><c:if test="${ckBiz.id eq tProductBiz.id}">checked="checked"</c:if></c:forEach>/>${tProductBiz.bizName}</td>
									<%-- <shiro:hasPermission name="productbiz:tProductBiz:edit"><td width="5%" nowrap>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}', '贷中管理-修改')" href="javascript:void(0);">修改</a>
										<a href="${ctx}/productbiz/tProductBiz/delete?id=${tProductBiz.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?parentId=${tProductBiz.id}', '贷中管理-添加下级菜单')" href="javascript:void(0);">添加下级菜单</a> 
									</td></shiro:hasPermission>--%>
								</tr>
							</c:forEach></tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<input class="selAllDH" type="checkbox"  name="selAllDH"  value="1" style="cursor: pointer;"/>贷后管理
					</td>
					<td>
						<table id="treeTable23" class="table table-bordered table-condensed hide">
							<tbody><c:forEach items="${dhList}" var="tProductBiz">
								<tr id="${tProductBiz.id}" pId="${(tProductBiz.parent.id ne '1')?tProductBiz.parent.id:'0'}" class="bizType${tProductBiz.type }">
									<td width="100%" nowrap><input class="dhIds" type="checkbox" name="dhIds" value="${tProductBiz.id}" <c:forEach items="${ckdhList}" var="ckBiz" varStatus="idx"><c:if test="${ckBiz.id eq tProductBiz.id}">checked="checked"</c:if></c:forEach>/>${tProductBiz.bizName}</td>
									<%-- <shiro:hasPermission name="productbiz:tProductBiz:edit"><td width="5%" nowrap>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?id=${tProductBiz.id}', '贷后管理-修改')" href="javascript:void(0);">修改</a>
										<a href="${ctx}/productbiz/tProductBiz/delete?id=${tProductBiz.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
										<a onclick="openWin('${ctx}/productbiz/tProductBiz/form?parentId=${tProductBiz.id}', '贷后管理-添加下级菜单')" href="javascript:void(0);">添加下级菜单</a>
									</td></shiro:hasPermission> --%>
								</tr>
							</c:forEach></tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="container">
			<div class="row clearfix">
				<div class="span12">
					<div class="clearfix" style="margin-bottom: 50px;">
						<input id="btnCancel" class="btn btn-primary" type="button" value="上一步" onclick="history.go(-1)"/>
						<shiro:hasPermission name="product:tProduct:edit">
						<input id="btnCancel" class="btn btn-primary" type="button" value="下一步" onclick="toSubmit()"/>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</form:form>
</body>
</html>