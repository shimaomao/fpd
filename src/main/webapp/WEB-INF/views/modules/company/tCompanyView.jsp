<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业客户管理</title>
    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/company/tCompany/">企业客户列表</a></li>
		<li class="active"><a href="${ctx}/company/tCompany/form?id=${data.id}">企业客户<shiro:hasPermission name="company:tCompany:edit">${(empty data.id || fn:contains(data.id, 'new_')) ? '添加' : '修改'}</shiro:hasPermission><shiro:lacksPermission name="company:tCompany:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<sys:message content="${message}"/>
	${dfFormTpl.parsehtml }
	<div class="container">
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
					<input id="submitViewForm" type="button" class="btn btn-primary" value="保存" />
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>