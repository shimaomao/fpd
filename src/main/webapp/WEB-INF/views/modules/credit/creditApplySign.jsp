<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="nid" value="creditApplySign" />
<c:set var="edit" value="${creditApply.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>授信申请-签订合同</title>
<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api'], function(api){
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(customer)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
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
			
			//附件
			var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?businesstype=<%=Cons.FileType.FILE_TYPE_CREDIT_SIGN%>&oper=${edit ? 'edit': 'view'}?height=100";
		    $("#${nid}filelist").load(url);
		});
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/credit/creditApply/">授信申请列表</a></li>
		<li class="active"><a href="${ctx}/credit/creditApply/toSign?id=${creditApply.id}">签订合同</a></li>
	</ul><br/>
		${dfFormTpl.parsehtml }
	<br/>
	<label class="control-label">相关附件：</label>
	<div id="${nid}filelist" class="controls" ></div>
	<form:form id="inputForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="status" value="<%=Cons.CreditApplyStatus.CREDIT_SUCCESS %>" />
		<div class="form-actions">
			<c:if test="${edit }">
				<shiro:hasPermission name="credit:creditApply:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="签订合同"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>