<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"%>
<html>
<head>
	<title>律师函</title>
	<meta name="decorator" content="default"/>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
    <script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		
		function sendmess(){
		 	$.ajax({
	         	type: "POST",
	         	url: "${ctx}/index/todo/send",
	         	data: {phone:$("#phone").val(),content:$("#sendmessage").val(),type:2},
	         	dataType: "json",
	         	success: function(data){
	         		if(data>0){
	         			alert("发送成功！");
	         		}
	         	}
	       });
		}
	</script>
</head>
<body>
    <div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">业务中心&gt;</a>
				<a href="#">我的业务&gt;</a>
				<a href="#">贷后管理&gt;</a>
				<a href="#">催收提醒</a>
			</div>
		</div>
	</div>
	<br/>
	<form:form id="inputForm" modelAttribute="tLoanContract" action="" method="post" class="form-horizontal">
		
		<table class="table-form" style="line-height: 25px;">
			 <tr>
				<td class="tit" colspan="2"><font style="float: left;font-weight: bold;color: #317eac;">合同信息</font></td>
			</tr>
			<tr>
			    <td class="tit" style="width: 260px;">业务编号</td>
				<td>
				     ${tLoanContract.contractNumber}
				</td>
			</tr>
			<tr>
				<td class="tit">贷款金额</td>
				<td>
				   	${tLoanContract.loanAmount}
				</td>
			</tr>	
			<tr>
			    <td class="tit">客户姓名</td>
				<td>
					${tLoanContract.customerName}
				</td>
			</tr>	
				<tr>
			    <td class="tit">联系电话(多个号码请 用','[英文逗号]号隔开)</td>
				<td>
					<textarea rows="4" maxlength="255" id="phone" class="input-xxlarge ">${company.suretyMobile}${employee.mobile}</textarea>
				</td>
			</tr>	
			<tr>
			    <td class="tit">短信内容</td>
				<td>
				    <textarea rows="4" maxlength="255" id="sendmessage" class="input-xxlarge ">${letter}</textarea>
				</td>
			</tr>	
		</table>
		
		 <br>
		 <div class="form-actions" style="padding-left: 20px;">
			<input id="btnSubmit" class="btn btn-primary" type="submit"  value="发送" onclick="sendmess();"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	    <br>
	     <table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td colspan="3">
					<h3 align="center">还款计划</h3>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td rowspan="2">期数</td>
								<td rowspan="2">催收次数</td>
								<td rowspan="2">计息开始日期</td>
								<td rowspan="2">计息结束日期</td>
								<td rowspan="2">计划到账日</td>
								<td colspan="2">本金</td>
								<td colspan="2">利息</td>
								<td rowspan="2">状态</td>
							</tr>
							<tr align="center" class="tit_center_bg">
								<td>应还</td>
								<td>已还</td>
								<td>应还</td>
								<td>已还</td>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${repayplanList}" var="repayPlan">
								<tr>
									<td>第${repayPlan.num}期</td>
									<td>${repayPlan.csNum}</td>
									<td>${repayPlan.startDate}</td>
									<td>${repayPlan.endDate}</td>
									<td>${repayPlan.accountDate}</td>
									<td>${repayPlan.principal}</td>
									<td><c:if test="${empty repayPlan.principalReal}">0</c:if>${repayPlan.principalReal}</td>
									<td>${repayPlan.interest}</td>
									<td><c:if test="${empty repayPlan.interestReal}">0</c:if>${repayPlan.interestReal}</td>
									<td>${fns:getDictLabel(repayPlan.status, 'repay_status', '')}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td width="49" valign="top">
					<h3 align="center">还款记录</h3>
					<table width="99%"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">序号</td>
								<td>日期</td>
								<td>金额</td>
								<!-- <td>操作</td> -->
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${repRecordList}" var="repayRecord"
								varStatus="re">
								<tr>
									<td>${re['index']+1}</td>
									<td><fmt:formatDate value="${repayRecord.repayDate}"
											pattern="yyyy-MM-dd" /></td>
									<td>${repayRecord.money}</td>
									<!-- <td align="center">&nbsp;
									</td> -->
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
				<td width="2">&nbsp;</td>
				<td width="49" valign="top">
					<h3 align="center">逾期记录</h3>
					<table width="99%"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">序号</td>
								<td>还款截止日期</td>
								<td>结清日期</td>
								<td>逾期天数</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty overdueList}">
								<c:forEach items="${overdueList}" var="overRepay" varStatus="ov">
									<tr>
										<td>${ov['index']+1 }</td>
										<td><c:if test="${!empty overRepay.endDate}">
											${overRepay.endDate}
                        				</c:if></td>
										<td><c:if test="${!empty overRepay.overDate}">
											${overRepay.overDate}
                        				</c:if></td>
										<td>${overRepay.yuQi }
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td width="49" valign="top">
					<h3 align="center">违约金、咨询费、罚息实收款记录</h3>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td>序号</td>
								<td>日期</td>
								<td>违约金</td>
								<td>咨询服务费</td>
								<td>罚息</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty realIncomeList}">
							<c:forEach items="${realIncomeList}" var="realIncome" varStatus="ri">
								<tr>
									<td>${ri['index']+1 }</td>
									<td>
										<c:if test="${!empty realIncome.payRealDate}">
											<fmt:formatDate value="${realIncome.payRealDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
									<td>${realIncome.punishAmount }
									<td>${realIncome.consultancyAmount }
									<td>${realIncome.interestPenalties }
									</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
				<td width="2">&nbsp;</td>
				<td width="49" valign="top"></td>
			</tr>
		</tbody>
	</table>
	</form:form>
</body>
</html>