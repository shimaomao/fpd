<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
	<%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>贷后管理>坏账处理
	   </div>
	</div> --%>
	<br>
		<sys:message content="${message}"/>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr><td>
				<h3 align="center">坏账处理申请查看</h3>
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
			</tbody>
		</table>
		<div class="form-actions">
			<input id="btnBadAuditCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty badDebtRegist.id}">
			<act:histoicFlow procInsId="${badDebtRegist.act.procInsId}" />
		</c:if>
</body>
</html>