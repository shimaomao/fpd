<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授信信息管理</title>
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
		<li><a href="${ctx}/looploan/tLoopLoan/">授信信息列表</a></li>
		<li class="active"><a href="${ctx}/looploan/tLoopLoan/form?id=${tLoopLoan.id}">授信信息<shiro:hasPermission name="looploan:tLoopLoan:edit">${not empty tLoopLoan.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="looploan:tLoopLoan:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tLoopLoan" action="${ctx}/looploan/tLoopLoan/complete" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">客户：</label>
				<div class="controls">
					${tLoopLoan.customerName}
				</div>
			<div class="controls">
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">申请日期：</label>
			<div class="controls">
				<fmt:formatDate value="${tLoopLoan.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				${tLoopLoan.cardType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
			${tLoopLoan.cardNum}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">授信金额：</label>
			<div class="controls">
			${tLoopLoan.creditPrice}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信编号：</label>
			<div class="controls">
			${tLoopLoan.loopNumber}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信期限：</label>
			<div class="controls">
				${tLoopLoan.period}${tLoopLoan.periodType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信利率：</label>
			<div class="controls">
			${tLoopLoan.floatRate}% /${tLoopLoan.loanRateType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款方式：</label>
			<div class="controls">
			${tLoopLoan.loanType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
			${tLoopLoan.payType}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">投向行业：</label>
			<div class="controls">
			${tLoopLoan.industry}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款区域：</label>
			<div class="controls">
			${tLoopLoan.area}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款用途：</label>
			<div class="controls">
			${tLoopLoan.purpose}
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
			${tLoopLoan.remarks}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<act:histoicFlow procInsId="${tLoopLoan.act.procInsId}"/>
	</form:form>
</body>
</html>