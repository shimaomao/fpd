<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改还款记录</title>
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
			
			$("#btnSubmit").click(function() {
				//alert("提交新的数据！");
				 var options = {
					url : '${ctx}/receivables/receivables/upDateReceMoney',
					type : 'post',
					dataType : 'json',//'text',
					data : $("#inputForm").serializeArray(),
					success : function(data, textStatus) {
						alert(data.msg);
						//parent.window.location.reload();//刷新父页面有效
						top.$.jBox.close(true);
					}
				};
				$.ajax(options);
				return false;
			});
		});
		
		
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="repayRecord" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="loanContractId"/>
		<form:hidden path="isYuQi"/>
		<sys:message content="${message}"/>
		<div class="control-group" style="margin-top:20px">
			<label class="control-label">还款金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" maxlength="255"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款日期：</label>
			<div class="controls">
				<input name="repayDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${repayRecord.repayDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group" style="margin-top:5px">
			<div class="alert alert-info">提示！修改还款金额为0.0,相当于重置上次操作。</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="修 改"/>
		</div>
	</form:form>
</body>
</html>