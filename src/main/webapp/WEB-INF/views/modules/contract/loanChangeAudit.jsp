<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${tLoanContract.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>贷前变更-审核</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api','app/repayPlan'], function(api,rp){
		
		$(function(){
			rp.initialize({
				businessType : "apply",  //apply|extend|earlyrepay...
				amount : "${tLoanContract.loanAmount}",
				loanRate : "${tLoanContract.loanRate}",
				loanRateType : "${tLoanContract.loanRateType}",
				loanPeriod : "${tLoanContract.loanPeriod}",
				loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
				payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
				periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
				payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
				payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
				ifRealityDay : "${tLoanContract.ifRealityDay}"//大小月
			},"#showplansDiv");
			
			//页签
			$('.nav-tabs').find('li').click(function(){
				$(this).addClass('active').siblings().removeClass('active');
				$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
			});
			
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
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!-- <li class="active"><a href="javasricpt:void(0);">业务信息</a></li>
		<li><a href="javasricpt:void(0);">审批贷前变更</a></li> -->
		<li class="active"><a href="#" onclick="javascript:return false;">业务信息</a></li>
		<li><a href="#" onclick="javascript:return false;">审批贷前变更</a></li>
	</ul>
	<div class="nav-tab-con" >
		<fieldset>
			<legend>变更前业务信息</legend> 
				<table class="table-form" style="line-height: 25px;">
			    <tr>
					<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">客户基本信息</font></td>
				</tr>
				<tr>
				    <td class="tit">客户姓名</td>
					<td> 
					     ${tLoanContract.customerName}（${fns:getDictLabel(tLoanContract.customerType, 'customer_type', '')}）
					</td>
					<td class="tit">开户名</td>
					<td>
						${tLoanContract.gatheringName}&nbsp;
	    			</td>
				</tr>
				 <tr>
					<td class="tit">开户行</td>
					<td> 
					    ${tLoanContract.gatheringBank}&nbsp;
					</td>
					<td class="tit">开户账号</td>
					<td>
					     ${tLoanContract.gatheringNumber}&nbsp;
					</td>
				</tr>	
				 <tr>
					<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">合同信息</font></td>
				</tr>
				<tr>
				    <td class="tit">合同编号</td>
					<td>
					     ${tLoanContract.contractNumber}
					</td>
					<td class="tit">产品名称</td>
					<td>
					    ${productName}
					 </td>
				</tr>
				<tr> 
				    <td class="tit">申请日期</td>
					<td>
	                      <fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td class="tit">贷款金额</td>
					<td>
					   	${tLoanContract.loanAmount}
					</td>
				</tr>	
				<tr>
				    <td class="tit">贷款利率</td>
					<td>
					    ${tLoanContract.loanRate}
						<form:radiobutton path="tLoanContract.loanRateType" value="年" disabled="true"/>年
						<form:radiobutton path="tLoanContract.loanRateType" value="月" disabled="true"/>月
						<form:radiobutton path="tLoanContract.loanRateType" value="日" disabled="true"/>日
					</td>
				    <td class="tit">贷款期限</td>
					<td>
					    ${tLoanContract.loanPeriod}(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
					</td>
				</tr>	
				<tr>
				     <td class="tit">还款方式</td>
					<td>
					    ${fns:getDictLabel(tLoanContract.periodType, 'product_paytype', '')}
					</td>
					<td class="tit">还款选项</td>
					<td>
						 <form:checkboxes path="tLoanContract.payOptionsList" items="${fns:getDictList('pay_options')}" disabled="true" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</td>
				</tr>	
				<tr>
					<td class="tit">放款日期</td>
					<td>
	                       <fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>	
	                </td>
					<td class="tit">还本金日期</td>
					<td>
						<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>	
			   <tr>
					<td class="tit">贷款地区</td>
					<td>
					       ${tLoanContract.area.name}
					</td>
					<td class="tit">贷款方式</td>
					<td>
						 <form:checkboxes path="tLoanContract.loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
					</td>
				</tr>	
				<tr>
					<td class="tit">贷款用途</td>
					<td>
					    ${fns:getDictLabel(tLoanContract.purposeId, 'product_purpose', '')}
					</td>
					<td class="tit">贷款行业</td>
					<td> 
					  ${fns:getDictLabel(tLoanContract.industryId, 'industry_id', '')}
					</td>
				</tr>	
			</table>			
		</fieldset>
		<br>	
		<!-- 还款计划-- -->
		<fieldset>
			<legend>变更前还款计划</legend> 
			
			<div id="showplansDiv">			
					
			</div>
		</fieldset>
		
		
		<fieldset>
			<legend>变更后业务信息</legend> 
				<table class="table-form" style="line-height: 25px;">
				 <tr>
					<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">合同信息</font></td>
				</tr>
				<tr>
				    <td class="tit">合同编号</td>
					<td>
					     ${contractBak.contractNumber}
					</td>
					<td class="tit">产品名称</td>
					<td>
					    ${productName}
					 </td>
				</tr>
				<tr> 
				    <td class="tit">申请日期</td>
					<td>
	                      <fmt:formatDate value="${contractBak.applyDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td class="tit">贷款金额</td>
					<td>
					   <span style="color: red;">	${contractBak.loanAmount}</span>
					</td>
				</tr>	
				<tr>
				    <td class="tit">贷款利率</td>
					<td>
					    ${contractBak.loanRate}
						<form:radiobutton path="contractBak.loanRateType" value="年" disabled="true"/>年
						<form:radiobutton path="contractBak.loanRateType" value="月" disabled="true"/>月
						<form:radiobutton path="contractBak.loanRateType" value="日" disabled="true"/>日
					</td>
				    <td class="tit">贷款期限</td>
					<td>
					    ${contractBak.loanPeriod}(${fns:getDictLabel(contractBak.periodType, 'period_type', '')})
					</td>
				</tr>	
				<tr>
				     <td class="tit">还款方式</td>
					<td>
					    ${fns:getDictLabel(contractBak.periodType, 'product_paytype', '')}
					</td>
					<td class="tit">还款选项</td>
					<td>
						 <form:checkboxes path="contractBak.payOptionsList" items="${fns:getDictList('pay_options')}" disabled="true" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</td>
				</tr>	
				<tr>
					<td class="tit">放款日期</td>
					<td>
	                       <fmt:formatDate value="${contractBak.loanDate}" pattern="yyyy-MM-dd"/>	
	                </td>
					<td class="tit">还本金日期</td>
					<td>
						<fmt:formatDate value="${contractBak.payPrincipalDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>	
			   <tr>
					<td class="tit">贷款地区</td>
					<td>
					       ${tLoanContract.area.name}
					</td>
					<td class="tit">贷款方式</td>
					<td>
						 <form:checkboxes path="tLoanContract.loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"/>
					</td>
				</tr>	
				<tr>
					<td class="tit">贷款用途</td>
					<td>
					    ${fns:getDictLabel(tLoanContract.purposeId, 'product_purpose', '')}
					</td>
					<td class="tit">贷款行业</td>
					<td> 
					  ${fns:getDictLabel(tLoanContract.industryId, 'industry_id', '')}
					</td>
				</tr>	
			</table>			
		</fieldset>
		<br>	
		<!-- 还款计划-- -->
		<fieldset>
			<legend>变更后还款计划</legend> 
			
			<div id="showplansBakDiv">
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
							<c:forEach items="${planListBak}" var="repayPlan">
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
			</div>
		</fieldset>
	</div>
	<div class="nav-tab-con" style="display:none;">
		<br/>
		<form:form id="inputForm" modelAttribute="tLoanContractModel" action="${ctx}/contract/tLoanContract/saveChangeAudit" method="post" class="form-horizontal">
	
			<form:hidden path="id"/>		
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden path="act.businessTable"/>
			<form:hidden id="flag" path="act.flag"/>
			<input type="hidden" id="pass" name="act.vars.map['pass']"/>
			<sys:message content="${message}"/>		
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
				</div>
			</div>
			<div class="form-actions">
				<c:if test="${edit}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('1')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回上一级" onclick="$('#flag').val('0')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#flag').val('-1')"/>&nbsp;
				</c:if>
		
			</div>
			<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
		</form:form>
	</div>
	
</body>


</html>