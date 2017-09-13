<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<html>
<head>
	<title>还款提醒</title>
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
	         	data: {phone:$("#phone").val(),content:$("#sendmessage").val(),type:1},
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
				<a href="#">还款提醒</a>
			</div>
		</div>
	</div>
	<br/>
	<form:form id="inputForm" modelAttribute="tLoanContract" action="" method="post" class="form-horizontal">
		<table class="table-form">
			<tr>
				<td class="tit" width="200px;">合同编号</td>
				<td>
				   	${tLoanContract.contractNumber}
				</td>
			</tr>	
			<tr>
			    <td class="tit">客户姓名</td>
				<td>
					${tLoanContract.customerName}
				</td>
			</tr>	
			<tr>
			    <td class="tit">贷款金额（元）</td>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
			</tr>	
			<tr>
			    <td class="tit">贷款期限</td>
				<td>
					 ${tLoanContract.loanPeriod}(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
				</td>
			</tr>	
			<tr>
			    <td class="tit">还款日期</td>
				<td>
					 ${repayplan.accountDate}
				</td>
			</tr>	
			<tr>
			    <td class="tit">还款金额(本金+利息)</td>
				<td>
					${repayplan.principal+repayplan.interest-repayplan.interestReal-repayplan.principalReal}
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
				    <textarea rows="4" maxlength="255" id="sendmessage" class="input-xxlarge ">${repaywarn}</textarea>
				</td>
			</tr>	
		</table>
	</form:form>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"  value="发送" onclick="sendmess();"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
</body>
</html>