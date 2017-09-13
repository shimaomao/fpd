<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请退款信息查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<sys:message content="${message}"/>
		<form:form id="inputForm" modelAttribute="reimburse" action="${ctx}/refund/reimburse/saveRefund" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<table cellpadding="0" cellspacing="0" width="100%">
			<tbody>
			<tr><td>
				<h3 align="center">申请退款详细信息</h3>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<tbody>
					<tr>
						<td>业务编号：</td>
						<td>${loanContract.contractNumber}</td>
						<td>贷款期限：</td>
						<td>${loanContract.loanPeriod}</td>
					</tr>
					<tr>
						<td>业务类型：</td>
						<td>
							${fns:getDictLabel(reimburse.businessType, 'ReimbBusinessStatus', '')}
						</td>
						<td>贷款金额(元)：</td>
						<td>${loanContract.loanAmount}</td>
					</tr>
					<tr>
						<td>还款月数：</td>
						<td>${reimburse.backMonth}</td>
						<td>超前天数：</td>
						<td>${reimburse.outDay}</td>
					</tr>
					<tr>
						<td>收款日期：</td>
						<td>
							<c:if test="${!empty reimburse.reimburseyDate}">
								<fmt:formatDate value="${reimburse.reimburseyDate}" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>超支费用：</td>
						<td>${reimburse.outPrice}</td>
					</tr>
					<tr>
						<td>申请人：</td>
						<td>${reimburse.customerName}</td>
						<td>申请时间：</td>
						<td>
							<c:if test="${!empty reimburse.insertTime}">
								<fmt:formatDate value="${reimburse.insertTime}" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>退款状态：</td>
						<td>
							${fns:getDictLabel(reimburse.status, 'ReimburseStatus', '')}
						</td>
						<td>是否提醒：</td>
						<td>${fns:getDictLabel(reimburse.isRead, 'ReimReadStatus', '')}
						</td>
					</tr>
					<tr>
						<td>开户银行：</td>
						<td>${reimburse.bankname}</td>
						<td>开户账号：</td>
						<td>${reimburse.banknumber}</td>
					</tr>
					<tr>
						<td>开户人名：</td>
						<td>${reimburse.bankusername}</td>
						<td>用款天数：</td>
						<td>${reimburse.yongDay}</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td colspan="3">${reimburse.remarks}</td>
					</tr>
				</tbody>
				</table>
				<table>
				    <tr>
                         <td>退费金额：</td>
						<td><form:input path="backPrice" htmlEscape="false" minlength="1" class="input-xlarge  number" style="width:150px;"/></td>
				    </tr>
				    <tr>
				       <td>退费时间</td>
				       <td>
				          <input name="returnTime" id="returnTime"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${reimburse.returnTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				       </td>
				    </tr>
				</table>
				
				
				
				<div  style="padding-left: 40%;" class="form-actions">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="退费"/>&nbsp;
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</td></tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>