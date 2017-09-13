<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<html>
<head>
<title>业务办理</title>
<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
<script type="text/javascript">
        require(['helper/api','app/repayPlan'], function(api,rp){
        	$(document).ready(function(){ //#3435 生产环境网速差，要阻塞一下，让上下文先加载完，不然数据加载不全
        		var data = ${fns:toJson(tLoanContract)};
 				var form = $("#target");
 				var btn = $("#submitViewForm");
 				api.form.init(form, eval(data));
 				//禁用form表单中所有的选项
 				disableForm("target",true);
 				//1	新增	
				//2	待审批	
				//3	待签订	
				//4	放款待审批	
				//5待放款(贷款)
				//var ddd = '0,1,2,3,4,5'.indexOf('${tLoanContract.status}');
				//alert('${tLoanContract.status}');
 				if ('1,2,3,4,5'.indexOf('${tLoanContract.status}') >= 0) {
 					//alert('create');
 					rp.initialize({
 	 					businessType : "apply",  //apply|extend|earlyrepay...
 	 					amount : "${tLoanContract.loanAmount}",
 	 					loanRate : "${tLoanContract.loanRate}",
 	 					loanRateType : "${tLoanContract.loanRateType}",//add by srf #3121
 	 					loanPeriod : "${tLoanContract.loanPeriod}",
 	 					loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
 	 					payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
 	 					periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
 	 					payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
 	 					payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
 	 					ifRealityDay: "${tLoanContract.ifRealityDay}"
 	 				},"#showplansDiv");
 				}
 				url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?oper=view";
 				$("#${nid}filelist").load(url);
 				
    		})
        	   
				//放在这里加载不到，必须移到document.ready里 lzn 2016-11-22 
				//url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?oper=view";
			    //$("#${nid}filelist").load(url);
         })
		<%-- $(document).ready(function() {
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
			 ifShowAdvance();
			url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit";
		    $("#${nid}filelist").load(url);
		    } --%>
		/* function ifShowAdvance(){
		  var payType =  getCheckValue("ifAdvance");//是否可提前还款,是的话显示提前还款违约金一框
		    if(payType==1){
		    	$("#showAdvanceDamages").show();
		    }else{
		    	$("#showAdvanceDamages").hide();
		    }
		} */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/lending/tLending/listIng">放款待审列表</a></li> --%>
<%-- 		<li><a href="${ctx}/lending/tLending/listEd">已放款列表</a></li> --%>
		<li class="active"><a href="${ctx}/lending/tLending/lendDetail?id=${tLoanContract.id}">业务详细</a></li>
		<li><a href="${ctx}/lending/tLending/toLoan?contract.id=${tLoanContract.id}">放款</a></li>
	</ul>
	
       <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
                                             业务中心>我的业务>业务明细查看
	   </div>
	  </div>
	  <br/>
	  <br/> --%>
	  <div class="form-horizontal" style="width: 100%;">
		<table class="table-form" style="line-height: 25px;">
		    <tr>
				<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">客户基本信息</font></td>
			</tr>
			<tr>
			    <td class="tit">客户姓名</td>
				<td> 
				     ${tLoanContract.customerName}（${fns:getDictLabel(tLoanContract.customerType, 'customer_type', '')}）
				</td>
				<td class="tit">证件号</td>
				<td>
					${csPersonVo.cardNo}&nbsp;
    			</td>
			</tr>
			 <tr>
				<td class="tit">联系方式</td>
				<td> 
				    ${csPersonVo.phone}&nbsp;
				</td>
				<td class="tit">地址</td>
				<td>
				     ${csPersonVo.address}&nbsp;
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
			<tr>
				<td class="tit">合同状态</td>
				<td colspan="3">
				      ${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>	
			  <tr>
				<td class="tit">备注</td>
				<td colspan="3">
				   <form:textarea path="tLoanContract.remarks" disabled="true" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</td>
			</tr>	
				 <tr>
				<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">合同各项费用信息</font></td>
			</tr>
		   <tr>
				<td class="tit">前期服务费(%)</td>
				<td>
					${tLoanContract.serverFee}	
             	</td>
				<td class="tit">管理费（%）</td>
				<td>
				    ${tLoanContract.mangeFee}	
				</td>
			</tr>	
		   <tr>
				<td class="tit">是否可提前还款</td>
				<td>
				 	<form:radiobuttons path="tLoanContract.ifAdvance" disabled="true" onchange="ifShowAdvance();"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <nobr>
					   <q style="display: none" id="showAdvanceDamages">
				        	提前还款违约金（%）：${tLoanContract.advanceDamages}
			           </q>
			        </nobr>
				</td>
				<td class="tit">滞纳金</td>
				<td>
				    ${tLoanContract.lateFee}
				</td>
			</tr>	
		    <tr>
				<td class="tit">费率优惠率（%）</td>
				<td>
					${tLoanContract.rateDiscont}
				</td>
				<td class="tit">允许利息减免（%）</td>
				<td>
				   ${tLoanContract.ifInterestRelief}
				</td>
			</tr>	
			<tr>
				<td class="tit">宽限期（天）</td>
				<td>
				    ${tLoanContract.gracePeriod}
				</td>
				<td class="tit">宽限期罚息（%）</td>
				<td>
				     ${tLoanContract.gracePeriodPenalty}
				</td>
			</tr>	
		     <tr>
				<td class="tit">逾期罚息（%）</td>
				<td>
				   ${tLoanContract.latePenalty}
				</td>
				<td class="tit">逾期罚费（%）</td>
				<td>
				    ${tLoanContract.latePenaltyFee}
				</td>
			</tr>				
		</table> 
		 <br>
	    <br>
	     <table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td colspan="3">
					<h3 align="center">还款计划</h3>
					
					<c:if test="${fn:contains('1,2,3,4,5', tLoanContract.status)}">	
						
						<div id="showplansDiv">	
						
						</div>					
					</c:if>
					
					<c:if test="${!fn:contains('1,2,3,4,5', tLoanContract.status)}">	
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
					</c:if>
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
					<h3 align="center">提前还款记录</h3>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td>序号</td>
								<td>申请日期</td>
								<td>提前还款本金(元)</td>
								<td>提前还款利息(元)</td>
								<td>提前还款违约金(元)</td>
								<td>违约金比率</td>
								<td>合同状态</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty advanceList}">
							<c:forEach items="${advanceList}" var="advance" varStatus="al">
								<tr>
									<td>${al['index']+1 }</td>
									<td>
										<c:if test="${!empty advance.applyDate}">
											<fmt:formatDate value="${advance.applyDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
									<td>${advance.advanceamount }</td>
									<td>${advance.advanceInterest}</td>
									<td>${advance.advanceDamages}</td>
									<td>${advance.floatrate }</td>
									<td>
									    <c:if test="${advance.status eq '0'}">
											待审批
                        				</c:if>
                        				 <c:if test="${advance.status eq '1'}">
											审批不通过
                        				</c:if>
                        				 <c:if test="${advance.status eq '2'}">
											审批通过
                        				</c:if>
									</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td>
			</tr>
		<%-- 	<tr>
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
				<!-- <td width="2">&nbsp;</td>
				<td width="49" valign="top"></td> -->
			</tr> --%>
		</tbody>
	</table>
	
	
	<h3 align="center">放款记录</h3>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td>序号</td>
				<td>放款日期</td>
				<td>金额</td>
				<td>操作人</td>
			</tr>
		<c:forEach items="${lendRecord}" var="data" varStatus="status">
			<tr>
				<td>${status.count}</td>
				<td>${data[0]}</td>
				<td>${data[1]}</td>
				<td>${data[2]}</td>
			</tr>
		   </c:forEach>
		</table>
	
	<%-- <h3 align="center">退费历史记录</h3>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td>还款月数</td>
				<td>借款人</td>
				<td>业务类型</td>
				<td>申请人</td>
				<td>申请时间</td>
				<td>贷款期限</td>
				<td>超支费用</td>
				<td>收款日期</td>
				<td>退费实际时间</td>
				<td>提醒</td>
				<td>借款金额(元)</td>
				<td>提前还款本金(元)</td>
			</tr>
		<c:forEach items="${reimburseList}" var="reimburse" varStatus="index">
			<tr>
				<td>${reimburse.backMonth}</td>
				<td>${reimburse.backName}</td>
				<td>${fns:getDictLabel(reimburse.businessType, 'ReimbBusinessStatus', '')}</td>
				<td>${reimburse.customerName}</td>
				<td><fmt:formatDate value="${reimburse.insertTime}" pattern="yyyy-MM-dd"/></td>
				<td>${reimburse.loanPeriod}</td>
				<td>${reimburse.outPrice}</td>
				<td><fmt:formatDate value="${reimburse.reimburseyDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${reimburse.returnTime}" pattern="yyyy-MM-dd"/></td>
				<td>${reimburse.isRead}</td>
				<td>${reimburse.jiePrice}</td>
				<td>${reimburse.tiPrice}</td>
			</tr>
		   </c:forEach>
		</table> --%>
	
	
	<%-- <h3 align="center">业务申请及放款审批记录</h3>
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
				<c:if test="${!empty loanexaminelist}">
				<c:forEach items="${loanexaminelist}" var="examine" varStatus="ri">
					<tr>
						<td>${examine[0]}</td>
						<td>${examine[1]}</td>
						<td>${examine[2]}</td>
						<td>${examine[3]}</td>
                     	<td>${examine[4]}</td>
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
		</table> --%>
	
		<!-- 引用不同贷款方式对应的表单信息 -->
		<jsp:include page="/WEB-INF/views/common/loanTypeInfo.jsp">
			<jsp:param name="loanTypeInputName" value="loanTypeList"/>
			<jsp:param name="nid" value="${nid}"/>
			<jsp:param name="height" value="80"/>
			<jsp:param name="businessTable" value="t_loan_contract"/>
			<jsp:param name="businessId" value="${tLoanContract.id}"/>
			<jsp:param name="oper" value="edit"/>
		</jsp:include>
		
		<div id="${nid}filelist" ></div>
		
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		</div>
</body>
</html>