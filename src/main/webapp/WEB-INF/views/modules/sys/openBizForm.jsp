<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务激活申请</title>
	<meta name="decorator" content="default"/>
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
			
			$("#count").change(function(){
				$("#amount").val((${openBiz.feeBiz.unitPrice} * $(this).val()));
			}).change();
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/openBiz/feeBizList">付费业务列表</a></li>
		<li class="active"><a href="${ctx}/sys/openBiz/form?feeBiz.id=${openBiz.feeBiz.id}&id=${openBiz.id}">业务开通申请</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="openBiz" action="${ctx}/sys/openBiz/apply" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="feeBiz.id"/>
		<sys:message content="${message}"/>
		<c:set var="categoryMessage" value="<%=Cons.FeeBizCategory.MESSAGE %>"/>		
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select path="feeBiz.category" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('feebiz_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" style="display: ${openBiz.feeBiz.category eq categoryMessage ? '' : 'none'};">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="count" htmlEscape="false" class="input-xlarge number"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">费用（元）：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge number" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:openBiz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>