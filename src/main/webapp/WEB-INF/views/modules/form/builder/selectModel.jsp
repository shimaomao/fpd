<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<style type="text/css">.item{width: 150px; height: 150px; line-height: 150px; border:  2px dashed #c1c1c1; border-radius: 8px; margin-bottom: 10px;} </style>
</head>
<body id="ilayer" style="padding-top: 5px;">
    <ul class="nav nav-tabs">
		<li ><a href="${ctx}/form/tpl/dfFormTpl/">自定义表单列表</a></li>
		<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><li class="active"><a href="${ctx}/form/builder/builder/selectModel">表单设计</a></li></shiro:hasPermission>
	</ul>
    <div class="container">
   		<div class="row clearfix">
   			<div class="span12"><h4 class="brand" href="#">表单设计器-模块选择<button id="btnReset" class="btn btn-primary pull-right" onclick="javascript:location.reload(true);" style="margin-right:5px;" >刷新</button></h4><hr /></div>
   		</div>
   		<div class="row clearfix">
   			<div id="messageBox" style="display: block;">${msg }</div>
   		</div>
   		<div class="row clearfix">
   			<c:forEach var="item" items="${list}" varStatus="idx">
	        	<a href="${ctx}/form/builder/builder/selectData?category=${item.category }">
	        	<div class="span3">
	        		<h4 class="text-center img-polaroid item" >${item.categoryName }</h4>
				</div>
				</a>
			</c:forEach>
	    </div>
    </div>
</body>
</html>