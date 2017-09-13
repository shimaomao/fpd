<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费单价管理</title>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/billing/price/biPrice/list">收费单价列表</a></li>
		<li class="active"><a href="${ctx}/billing/price/biPrice/form?id=${biPrice.id}">收费单价<shiro:hasPermission name="billing:price:biPrice:edit">${not empty biPrice.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="billing:price:biPrice:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="biPrice" action="${ctx}/billing/price/biPrice/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">收费项标识：</label>
			<div class="controls">
				<form:select path="element.id" class="input-xlarge required">
					<form:option value="" label=""/>
					<c:forEach var="element" items="${elements}">
						<form:option value="${element.id }" label="${element.name }"/>
					</c:forEach> 
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<form:select path="unit" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('biling_price_unit')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计价类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required">
					<form:options items="${fns:getDictList('biling_price_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="billing:price:biPrice:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div class="alert alert-message"><button data-dismiss="alert" class="close">×</button>
	<pre>单价制定注意事项：
			1、数量计算：
				直接填(1)，然后带上单位即可
			2、时间计算：
				所有时间计算都以小时制（最小单位），出现天数不一致情况以最多计算，
				1天=24小时；
				1周=24*7天=168小时；
				1月=24*31天=744小时(月{28-31}天)；
				1季=24*92天=2208小时(季度{90-92}天)；
				1年=24*366天=8784小时(季度{365-366}天)；
	</pre>
	</div> 
</body>
</html>