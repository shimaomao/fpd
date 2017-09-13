<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风控模型管理</title>
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
		});
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li><a href="${ctx}/wind/tWindControl/">风控模型列表</a></li> --%>
<%-- 		<li class="active"><a href="${ctx}/wind/tWindControl/form?id=${tWindControl.id}">风控模型<shiro:hasPermission name="wind:tWindControl:edit">${not empty tWindControl.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wind:tWindControl:edit">查看</shiro:lacksPermission></a></li> --%>
<!-- 	</ul> -->
	
	  <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
<%-- 		 <%=Cons.LocationUrl.URL.replace("-",">")%> --%>
		              系统设置>系统设置>风控模型>编辑
	   </div>
	</div>
	
	<br/>
	<form:form id="inputForm" modelAttribute="tWindControl" action="${ctx}/wind/tWindControl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">模型名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型路径：</label>
			<div class="controls">
				<form:input path="url" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型类别：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型参数：</label>
			<div class="controls">
				<form:input path="param" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('wind_status')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">创建时间：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<input name="createTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" -->
<%-- 					value="<fmt:formatDate value="${tWindControl.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" --%>
<!-- 					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -->
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">更新时间：</label> -->
<!-- 			<div class="controls"> -->
<!-- 				<input name="updateTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" -->
<%-- 					value="<fmt:formatDate value="${tWindControl.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" --%>
<!-- 					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -->
<!-- 				<span class="help-inline"><font color="red">*</font> </span> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">租户编号：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:input path="organid" htmlEscape="false" maxlength="64" class="input-xlarge "/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="wind:tWindControl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>