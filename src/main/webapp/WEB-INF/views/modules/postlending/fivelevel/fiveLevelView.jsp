<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="nid" value="fiveLevelForm" />
<html>
<head>
	<title>五级分类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//附件
			var url = "${ctx}/files/tContractFiles/showfilelist/${fiveLevel.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=view&nid=${nid}file";
			$("#${nid}filelist").load(url);
			
		});
	</script>
</head>
<body>
	<!-- 合同部分 -->
	<table cellpadding="0" cellspacing="0" width="100%"
		id="loanContractTable"
		class="table table-striped table-bordered table-condensed">
		<h3 align="center">贷款合同信息</h3>
		<tbody>
			<tr>
				<td align="right">合同编号：</td>
				<td>${fiveLevel.loanContract.contractNumber}</td>
				<td align="right">借款人：</td>
				<td>${fiveLevel.loanContract.customerName}</td>
				<td align="right">贷款行业：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.industryId, 'industry_id', '')}</td>
			</tr>
			<tr>
				<td align="right">贷款期限：</td>
				<td>${fiveLevel.loanContract.loanPeriod}</td>
				<td align="right">贷款用途：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.purposeId, 'product_purpose', '')}</td>
				<td align="right">贷款方式：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.loanType, 'loan_type', '')}</td>
			</tr>
			<tr>
				<td align="right">借款区域：</td>
				<td>${fiveLevel.loanContract.area.name}</td>
				<td align="right">申请日期：</td>
				<td><c:if test="${!empty fiveLevel.loanContract.applyDate}">
						<fmt:formatDate value="${fiveLevel.loanContract.applyDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				<td align="right">签合同日期：</td>
				<td><c:if test="${!empty fiveLevel.loanContract.signDate}">
						<fmt:formatDate value="${fiveLevel.loanContract.signDate}" pattern="yyyy-MM-dd" />
					</c:if></td>
			</tr>
			<tr>
				<td align="right">贷款金额：</td>
				<td>${fiveLevel.loanContract.loanAmount}</td>
				<td align="right">合同状态：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.status, 'loan_contract_status', '')}</td>
				<td align="right">还款方式：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.payType, 'product_paytype', '')}</td>
			</tr>
			<tr>
				<td align="right">还款周期：</td>
				<td>${fns:getDictLabel(fiveLevel.loanContract.periodType, 'period_type', '')}</td>
				<td align="right">放款日期：</td>
				<td><c:if test="${!empty fiveLevel.loanContract.loanDate}">
						<fmt:formatDate value="${fiveLevel.loanContract.loanDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				<td align="right">还本金日期：</td>
				<td><c:if test="${!empty fiveLevel.loanContract.payPrincipalDate}">
						<fmt:formatDate value="${fiveLevel.loanContract.payPrincipalDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
			</tr>
			<tr>
				<td align="right">还款选项：</td>
				<td>${fiveLevel.loanContract.payOptions}</td>
				<td align="right">每期还款日：</td>
				<td>${fiveLevel.loanContract.payDayType}</td>
				<td align="right">贷款利率：</td>
				<td>${fiveLevel.loanContract.loanRate}</td>
			</tr>
			<tr>
				<td align="right">收款人：</td>
				<td>${fiveLevel.loanContract.gatheringName}</td>
				<td align="right">收款账号：</td>
				<td>${fiveLevel.loanContract.gatheringNumber}</td>
				<td align="right">收款银行：</td>
				<td>${fiveLevel.loanContract.gatheringBank}</td>
			</tr>
		</tbody>
	</table>
	<br>
	<br>
	<!-- 五级分类部分 -->
	<form:form id="inputForm" modelAttribute="fiveLevel" action="${ctx}/postlending/fivelevel/fiveLevel/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />

		<form:hidden path="loanContractId" />
		<form:hidden path="oldFiveLevel" />
		<form:hidden path="fiveLevel"/>
		<form:hidden path="remarks"/>
		<sys:message content="${message}"/>	
		<table cellpadding="0" cellspacing="0" width="80%" class="table table-striped table-bordered table-condensed">
			<tbody>
				<tr>
					<td width= "15%">五级分类：</td>
					<td width= "35%">
						${fns:getDictLabel(fiveLevel.fiveLevel, 'five_level', '')}
					</td>
					<!-- <td width= "15%">状态：</td>
					<td width= "35%">待审批</td> -->
					<td width= "15%"></td>
					<td width= "35%"></td>
				</tr>
				<tr>
					<td>评估依据：</td>
					<td colspan="3">
						${fiveLevel.remarks }
					</td>
				</tr>
				<tr>
					<td colspan="4"><div id="${nid}filelist" ></div></td>
				</tr>
			</tbody>
		</table>
		
	<h3 align="center">审批记录</h3>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<td>流程名称</td>
					<td>审批意见</td>
					<td>审批人</td>
					<td>得到任务时间</td>
					<td>审批完结时间</td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${!empty fiveLevellist}">
				<c:forEach items="${fiveLevellist}" var="examine" varStatus="ri">
					<tr>
						<td>${examine[0]}</td>
						<td>${examine[1]}</td>
						<td>${examine[2]}</td>
						<td>
							${examine[3]}
                     			</td>
                     			<td>
						   ${examine[4]}
                     			</td>
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
		</table>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>