<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${badDebtRegist.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>坏账处理审批</title>
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
	<div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>贷后管理>坏账处理
	   </div>
	</div>
	<br>
	
	<form:form id="inputBadDebtForm" modelAttribute="badDebtRegist" action="${ctx}/postlending/baddebts/badDebtRegist/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />
		<form:hidden path="loanContractId" />
		<form:hidden path="registName" />
		<form:hidden path="department" />
		<sys:message content="${message}"/>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr><td>
				<h3 align="center">坏账处理审核</h3>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<tbody>
				<tr>
						<td>贷款合同：</td>
						<td>
							${badDebtRegist.loanContract.contractNumber }
						</td>
						<td>申报日期：</td>
						<td>
							<fmt:formatDate value="${badDebtRegist.registerDate}" pattern="yyyy-MM-dd"/>
						</td>
				</tr>
				<tr>
						<td>填报人名称：</td>
						<td>
							${badDebtRegist.registName }
						</td>
						<td>申报部门：</td>
						<td>
							${badDebtRegist.department }
						</td>
				</tr>
				<tr>
						<td>目前欠款本金额：</td>
						<td>
							${badDebtRegist.lossMoney }
						</td>
						<td>已收回本金额：</td>
						<td>
							${badDebtRegist.gainedMoney }
						</td>
				</tr>
				<tr>
						<td>目前欠利息额：</td>
						<td>
							${badDebtRegist.lossInterest }
						</td>
						<td>已收回利息额：</td>
						<td>
							${badDebtRegist.gainedInterest }
						</td>
				</tr>
				<tr>
					<td>债务人现况：</td>
					<td colspan="3">${badDebtRegist.currentInfo}</td>
				</tr>
				<tr>
					<td>逾期原因：</td>
					<td colspan="3">${badDebtRegist.reason}</td>
				</tr>
				<tr>
						<td>期时间：</td>
						<td>
							<fmt:formatDate value="${badDebtRegist.exceedTime}" pattern="yyyy-MM-dd"/>
						</td>
						<td>最后一次还款时间</td>
						<td>
							<fmt:formatDate value="${badDebtRegist.lastRepay}" pattern="yyyy-MM-dd"/>
						</td>
				</tr>
				<tr>
					<td>诉讼及委托情况：</td>
					<td colspan="3">${badDebtRegist.info}</td>
				</tr>
				<tr>
						<td>催收责任人：</td>
						<td>${badDebtRegist.debtCollecter}</td>
						<td>催收责任人联系电话：</td>
						<td>${badDebtRegist.phone}</td>
				</tr>
				<tr>
					<td>备注说明：</td>
					<td colspan="3">${badDebtRegist.remarks}</td>
				</tr>
				<tr>
					<td>审核意见：</td>
					<td colspan="3">
						<form:textarea path="act.comment" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="form-actions">
			<c:if test="${edit}">
			<shiro:hasPermission name="postlending:baddebts:badDebtRegist:edit">
			<input id="btnBadAuditSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
			<input id="btnBadAuditCanSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#flag').val('no')"/>&nbsp;
			<input id="btnBadAuditCanSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#flag').val('stop')"/>&nbsp;
			</shiro:hasPermission>
			</c:if>
			<!-- <input id="btnBadAuditCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
		<c:if test="${not empty badDebtRegist.id}">
			<act:histoicFlow procInsId="${badDebtRegist.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>