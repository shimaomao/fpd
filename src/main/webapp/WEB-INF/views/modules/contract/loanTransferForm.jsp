<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>转理财</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
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
	<form:form id="inputForm" action="${ctx}/contract/tLoanContract/loanTransfer" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${id}"/>
		<sys:message content="${message}"/>		
		<br/>
		<br/>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<select name="cat_id" class="input-middle required">
					<c:forEach items="${list}" var="cat">
						<option value="${cat.id}">${cat.name}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分期：</label>
			<div class="controls">
				<input name="installment" value="${installment}" class="input-middle digits required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款说明：</label>
			<div class="controls">
				<textarea name="use" cols="80" rows="5" class="required">${use}</textarea>
			</div>
		</div>
		<div class="form-actions" style="display: none;">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>
		</div>
	</form:form>
</body>
</html>