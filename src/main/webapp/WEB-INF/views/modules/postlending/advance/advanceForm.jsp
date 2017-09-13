<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="isApply" value=""/>
<%-- <c:set var="edit" value="${advance.act.status == 'finish' ? false : true }" /> --%>
<c:set var="editable" value="${empty fiveLevel.act.procInsId ? true : false}" ></c:set>
<html>
<head>
	<title>提前还款申请</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['app/repayPlan'], function(rp){
		if(parseFloat("${advance.advanceamount}")!=parseFloat("${nowPrincipal}")){//申请全部还清不需要生成新的还款计划
			//初始化还款计划--
			rp.initialize({
				businessType : "earlyrepay",  //apply|extend|earlyrepay...
				businessId : "${tLoanContract.id}",
				amount : "${tLoanContract.loanAmount}",
				loanRate : "${tLoanContract.loanRate}",
				loanRateType : "${tLoanContract.loanRateType}",
				loanPeriod : "${tLoanContract.loanPeriod}",
				loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
				payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
				periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
				payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
				payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			//	payAmount : "${advance.applyDate}",
				payAmount : "${advance.advanceamount}",
				payDate : '<fmt:formatDate value="${advance.applyDate}" pattern="yyyy-MM-dd"/>',
				payPrincipalDate : '<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>',
				ifRealityDay : "${tLoanContract.ifRealityDay}"
			},"#showplansDiv");
			if("${advance.act.isEnd}"=='yes'){
				ifShowAdvance();
			}
		}
		
		//初始化原还款计划--
		rp.initLocalPlans("${tLoanContract.id}","#oldRepayPlansDiv");
		
		$(function(){
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if("${advance.act.isEnd}"=='yes'){
						if(confirm("财务确认是否已收到提前还款金额?")){
							loading('正在提交，请稍等...');
							form.submit();
						 }else{
							 return false;
						 }
						
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
					
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
			
			//IE
            if($.browser.msie){
                  $("#advanceamount").get(0).attachEvent("onpropertychange",function (o){
                	  //alert(o.srcElement.value);
                      if( isNaN( o.target.value ) ){//不是数字
                    	  $("#surplusamount").val( $("#nowPrincipal").val() )
                      }else if( o.target.value &&　$("#nowPrincipal").val()){
                          var  sumAmount = $("#nowPrincipal").val() - o.target.value;
                          $("#surplusamount").val(sumAmount)
                      }
                    });
            //非IE
            }else{
            $("#advanceamount").get(0).addEventListener("input",function(o){
                    //alert(o.target.value);
            	 if( isNaN( o.target.value ) ){
               	 	 $("#surplusamount").val( $("#nowPrincipal").val() )
                 }else if( o.target.value &&　$("#nowPrincipal").val()){
                	 var  sumAmount = $("#nowPrincipal").val() - o.target.value;
                     $("#surplusamount").val(sumAmount)
                 }
                },false);
            }
			
			//生成新的还款计划
			$("#advanceamount").change(function(){
				
				var advanceamount = $("#advanceamount").val();//提前还款金额
				
				var surplusamount =  $("#surplusamount").val();//剩余还款金额
				var applyDate = $("#applyDate").val();//提前还款申请日期
				var leftAmount = $("#nowPrincipal").val();//剩余还款本金
				var floatrate = $("#floatrate").val();//违约金比例
				
				var loanPeriod = "${surplusPeriod}";//$("#surplusPeriod").val();//期限(剩余的期数)
				var interestDate = $("#interestDate").val();//开始计息时间
				
				//----------------------------------
				//数据校验
				if(!applyDate){
					return;								
				}
				
				if(applyDate<interestDate){
					showTip("提前还款申请日期小于当期还款日期,请重新选择！");
					$("#applyDate").val('');
					return;
				}
				
				
				if(!advanceamount || isNaN( advanceamount ) || advanceamount <= 0){
					showTip("请填写提前还款金额数且不能小于0,请重新填写");
					$("#advanceamount").val('');
					return;					
				}
				if(isNaN( advanceamount )){
					return;					
				}
				
				if(parseFloat(advanceamount)>parseFloat(leftAmount)){
					showTip("您提前还款金额已经超过了本金部分,请重新填写");
					$("#advanceamount").val('');
					return;		
				}
				if(parseFloat(advanceamount)==parseFloat(leftAmount)){
					alertx("提前还款金额等于合同剩余全部未还本金,不需要生成新的还款计划即可提交申请");
					return;		
				}
				
				rp.initialize({
					businessType : "earlyrepay",  //apply|extend|earlyrepay...
					businessId : "${tLoanContract.id}",
					amount : "${tLoanContract.loanAmount}",
					loanRate : "${tLoanContract.loanRate}",
					loanRateType : "${tLoanContract.loanRateType}",
					loanPeriod : "${tLoanContract.loanPeriod}",
					loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
					payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
					periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
					payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
					payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
					payAmount : advanceamount,
					payDate : applyDate,
					payPrincipalDate : '<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>',
					ifRealityDay:"${tLoanContract.ifRealityDay}"
				},"#showplansDiv");
			});
			
			ifShowAdvance();
		});
	});
	
	
	function ifShowAdvance(){
		    var payType =  getCheckValue("ifAdvance");//是否可提前还款,是的话显示提前还款违约金一框
		    if(payType==1){
		    	$("#showAdvanceDamages").show();
		    }else{
		    	$("#showAdvanceDamages").hide();
		    }
		     if("${advance.act.isEnd}"=='yes'){
		    	var days = '${days}';//天数
		    	var noInterest = '${noInterest}';//未收利息
		    	var loanRateType='${tLoanContract.loanRateType}';//利率类型
		    	var loanRate=${tLoanContract.loanRate}/100;//年利率
		    	var leftAmount = $("#nowPrincipal").val();//剩余还款本金
		    	var advanceInterest=0;
		    	if(loanRateType=='年'){
		    		advanceInterest=leftAmount*days*loanRate/360;
		    	}else if(loanRateType=='月'){
		    		advanceInterest=leftAmount*days*loanRate*12/360;
		    	}else if(loanRateType=='日'){
		    		advanceInterest=leftAmount*days*loanRate*360/360;
		    	}
		    	advanceInterest=noInterest-advanceInterest;//Bug #4805
		    	$("#advanceInterest").val(advanceInterest.toFixed(2));
		    } 
		}
	
	</script>
</head>
<body>
	<%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>贷后管理>提前还款
	   </div>
	</div> --%>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/refund/reimburse">业务变更</a></li>
		<li class="active"><a href="#" onclick="javascript:location.reload();">提前还款</a></li>
	</ul>
	<sys:message content="${message}"/>
	<!-- ================ -->
	<div class="form-horizontal" style="width: 100%;">
		<table class="table-form" style="line-height: 25px;">
		    <tr>
				<td class="tit" colspan="4"><h3 align="center">客户基本信息</h3></td>
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
				<td class="tit" colspan="4"><h4 align="center">合同信息</h4></td>
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
			    <td class="tit">贷款利率(%)</td>
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
				    ${fns:getDictLabel(tLoanContract.payType, 'product_paytype', '')}
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
				<td>
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
				<td class="tit" colspan="4"><h3 align="center">合同各项费用信息</h3></td>
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
		 
	</div>
	<br/>
	<!-- ================ -->
	<form:form id="inputForm" modelAttribute="advance" action="${ctx}/postlending/advance/advance/save" method="post" class="form-horizontal">
		<input id="totPrincipal" name="totPrincipal" style="display:none" value="${totPrincipal}" class="input-xlarge " type="text">
		<input id="nowPrincipal" name="nowPrincipal" style="display:none" value="${nowPrincipal}" class="input-xlarge " type="text">
		<input id="nowInterest" name="nowInterest" style="display:none" value="${nowInterest}" class="input-xlarge " type="text">
		<input id="surplusPeriod" name="surplusPeriod" style="display:none" value="${surplusPeriod}" class="input-xlarge " type="text">
		<input id="interestDate" name="interestDate" style="display:none" value="${interestDate}" class="input-xlarge " type="text">
		
		<form:hidden path="id"/>
		<form:hidden path="act.businessTable"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="vars" name="act.vars.map['pass']" />
		
		<form:hidden path="loanContractId" />

		<h3 align="center">提前还款信息</h3>
		<table class="table-form">
			<tr>
				<td class="tit">提前还款本金(元)${advance.act.isEnd}：</td>
				<td><form:input path="advanceamount" disabled="${not empty advance.id}" htmlEscape="false" class="input-xlarge number required"/></td>
				<td class="tit">剩余还款本金(元)：</td>
				<td><form:input path="surplusamount" readonly="true" htmlEscape="false"  class="input-xlarge  number"/></td>
			</tr>
			<c:if test="${advance.act.isEnd eq 'yes'}">
			   <tr>
				<td class="tit">提前还款利息(元)：</td>
				<td><form:input path="advanceInterest" readonly="true" htmlEscape="false" class="input-xlarge number"/></td>
				<td class="tit">提前还款违约金(元)：</td>
				<td><form:input path="advanceDamages" htmlEscape="false"  class="input-xlarge  number"/></td>
			   </tr>
			</c:if>
			
			<tr>
				<td class="tit">申请日期：</td>
				<td>
					<input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					${not empty advance.id ? 'disabled' : ''} value="<fmt:formatDate value="${advance.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:function(){$('#advanceamount').change();}});"/>
				</td>
				<td class="tit">目前未还本金</td>
				<td>${nowPrincipal}</td>
			</tr>
			<tr>
				<td class="tit">违约金比率（%）：</td>
				<td>
					<form:input path="floatrate" disabled="${not empty advance.id}"  htmlEscape="false" class="input-xlarge required number" value="${tLoanContract.advanceDamages}"/>
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
				</td>
				<td class="tit">申请状态：</td>
				<td>待提交</td>
			</tr>
			<tr>
				<td class="tit">备注：</td>
				<td colspan="3">
					<form:textarea path="remarks" disabled="${not empty advance.id}" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
				</td>
			</tr>
			<tr>
				<td class="tit">申请意见：</td>
				<td colspan="3">
					<form:textarea path="act.comment" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge required"/>
				</td>
			</tr>
		</table>
		<br/>
		<h3 align="center">新生成还款计划</h3>
		<div id="showplansDiv"></div>
		<div>
	<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td colspan="3">
					<h3 align="center">原还款计划</h3>
					<div id="oldRepayPlansDiv">
					</div>
				</td>
			</tr>
			<tr>
				<td width="49" valign="top">
					<h3 align="center">还款记录</h3>
					<table width="99%" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">序号</td>
								<td>日期</td>
								<td>金额</td>
								<td>操作</td>
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
									<td align="center">&nbsp; <c:if test="${re.last}">
											<a href="javascript:void(0);"
												onclick="update_real('${repayRecord.id}','${repayRecord.loanContractId}')"
												class="del">修改</a>
										</c:if>
									</td>
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
										<td>${overRepay.yuQi }</a>
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
							<!-- <tr>
								<td colspan="5" align="center">待建立好相应的库表后再处理</td>
							</tr> -->
							<c:if test="${!empty realIncomeList}">
							<c:forEach items="${realIncomeList}" var="realIncome" varStatus="ri">
								<tr>
									<td>${ri['index']+1 }</td>
									<td>
										<c:if test="${!empty realIncome.payRealDate}">
											<fmt:formatDate value="${realIncome.payRealDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
									<td>${realIncome.punishAmount }</a>
									<td>${realIncome.consultancyAmount }</a>
									<td>${realIncome.interestPenalties }</a>
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
		</div>
		<br/>
		<div class="form-actions">
		<c:if test="${!edit && empty advance.act.procInsId}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="申请"/>&nbsp;
					</c:if>
			
					<c:if test="${!edit && !empty advance.act.procInsId}">
					  <c:if test="${advance.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#vars').val('0');$('#flag').val('0');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#vars').val('-1');$('#flag').val('-1');"/>&nbsp;
					  </c:if>
					</c:if>
		<%-- <c:if test="${edit}">
			<c:if test="${empty advance.act || empty advance.act.procInsId }">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
			</c:if>
			<c:if test="${not empty advance.act && not empty advance.act.procInsId }">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#vars').val('0');$('#flag').val('0');"/>&nbsp;
			</c:if>
		</c:if> --%>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
		<c:if test="${not empty advance.id}">
			<act:histoicFlow procInsId="${advance.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>