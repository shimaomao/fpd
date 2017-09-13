<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请退款信息查看</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var url = "${ctx}/files/tContractFiles/showfilelist/${reimburse.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
			 $("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
		<sys:message content="${message}"/>
		<table cellpadding="0" cellspacing="0" width="50%">
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
						<td>用款天数：</td>
						<td>${reimburse.yongDay}</td>
						<td> </td>
						<td> </td>
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
						<td>提前还款本金(元)：</td>
						<td>${reimburse.tiPrice}</td>
						<td>退回还款金额：</td>
						<td>${reimburse.backPrice}</td>
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
						<td>实际退费时间：</td>
						<td>
							<c:if test="${not empty reimburse.returnTime}">
								<fmt:formatDate value="${reimburse.returnTime}" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td> </td>
						<td> </td>
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
						<td> </td>
						<td> </td>
					</tr>
					<tr>
						<td>备注：</td>
						<td colspan="3">${reimburse.remarks}</td>
					</tr>
				</tbody>
				</table>
				<div id="${nid}filelist" class=""  align="left"></div>
				<div  align="center" class="form-actions">
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</td></tr>
			</tbody>
		</table>
</body>
</html>