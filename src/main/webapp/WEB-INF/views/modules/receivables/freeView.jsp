<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
</style>
<title>业务办理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});

				$("#btnSubmit").click(function() {
					var options = {
						url : '${ctx}/receivables/receivables/receMoney',
						type : 'post',
						dataType : 'json',//'text',
						data : $("#inputForm").serializeArray(),
						success : function(data, textStatus) {
							alert(data.msg);
							//$("#message").val(data.result);
							//location.reload();
							
							var url = "${ctx}/receivables/receivables/receView?message="+encodeURI(encodeURI(data.result))+"&contractId="+data.content;
							location.href = url;
						}
					};
					$.ajax(options);
					return false;
				});
				
				
				$("#punishSubmit").click(function(){
					var options ={
						url : '${ctx}/receivables/receivables/punish',
						type : 'post',
						dataType : 'json',//'text',
						data : $("#punishForm").serializeArray(),
						success : function(data, textStatus) {
							if(data.params == "paramNull"){
								alert(data.msg);
							}else{
								alert(data.msg);
								location.reload(true);
							}
						}	
					};
					$.ajax(options);
					return false;
				})
			});

	function update_real(repayRecoId, loanContractId) {
		//alert("repayRecoId="+repayRecoId+"，loanContractId="+loanContractId);
		if (repayRecoId.length != 0 && loanContractId != 0) {
			top.$.jBox.open(
					"iframe:${ctx}/receivables/receivables/getReceRecord?repayRecId="
							+ repayRecoId, "修改还款记录",
					500,//$(top.document).width() - 300,
					300,//$(top.document).height() - 150, 
					{
						buttons : {
							//"刷新" : "refresh",
							"返回" : "return"//,
							//"关闭" : true
						},
						bottomText : "",
						submit : function(v, h, f) {
							var ifrWin = h.find("iframe")[0].contentWindow;
							//console.log("v = " + v);
							if (v == "refresh") {
								ifrWin.location.reload(true);
								//ifrWin.clearAssign();
								return false;
							} else if (v == "return") {
								//ifrWin.history.go(-1);
								//ifrWin.location.reload();
								top.$.jBox.close(true);
								location.reload();
								return false;
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");
						},
						closed : function(){
							//alert("closing");
							location.reload(true);
						}
					});
		}
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/receivables/receivables/receView?message=1&contractId=${contractId}">还款管理</a></li>
		<li class="active"><a href="${ctx}/receivables/receivables/freeView?message=1&contractId=${contractId}">收费管理</a></li>
	</ul>
	<form:form id="inputForm" method="post" class="breadcrumb form-search">
	<input id="contractId" name="contractId" type="hidden" value="${contractId}"/>
		<%--  <ul class="ul-form">
			<li><label>还款信息：</label>
			      <span id="message_w">${message}</span> 
			</li>
		</ul>
		<br>
		<ul class="ul-form">
			<li><label>还款时间：</label> <input name="contractDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate "
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</li>
			<li><label>还款金额：</label> <input id="contractAmount"
				name="contractAmount" class="input-xlarge  number" type="text" maxlength="20" style="width: 170px;">
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="还款" /></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<form:form id="punishForm" modelAttribute="realIncome" method="post" class="breadcrumb form-search">
	<input id="loanContractId" name="loanContractId" type="hidden" value="${contractId}">
	<input id="guaranteeFee" name="guaranteeFee"  class="input-xlarge  number" type="hidden"  style="width: 170px;" value="0" >
	<input id="reviewFee" name="reviewFee"  class="input-xlarge  number" type="hidden"  style="width: 170px;" value="0">
		
	
	
		<ul class="ul-form">
			<li>
				<label>收费时间：</label> <input name="payRealDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate "
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</li>
			<li><label>咨询服务费：</label> <input id="consultancyAmount"
				name="consultancyAmount"  class="input-xlarge  number" type="text" maxlength="20"  style="width: 170px;">
			</li>
			<li><label>违约金：</label> <input id="punishAmount"
				name="punishAmount"  class="input-xlarge  number" type="text" maxlength="10"  style="width: 170px;">
			</li>
			<li><label>罚息：</label> <input id="interestPenalties"
				name="interestPenalties"  class="input-xlarge  number" type="text"  style="width: 170px;">
			</li>
			<li class="btns"><input id="punishSubmit" class="btn btn-primary"
				type="submit" value="收取" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<hr>
	<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			
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
			
			   <%--  <td width="49" valign="top">
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
										<td>
											<fmt:formatDate value="${overRepay.payInterestDate}" pattern="yyyy-MM-dd"/>
                        				</td>
										<td>${overRepay.yuQi }
										</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</td> --%>
				<!-- <td width="2">&nbsp;</td>
				<td width="49" valign="top"></td> -->
			</tr>
		</tbody>
	</table>
	<hr>
	<!-- 以下合同部分 -->
	<table cellpadding="0" cellspacing="0" width="100%"
		id="loanContractTable"
		class="table table-striped table-bordered table-condensed">
		<h3 align="center">贷款合同信息</h3>
		<tbody>
			<tr>
				<td align="right">合同编号：</td>
				<td>${loanContract.contractNumber}</td>
				<td align="right">借款人：</td>
				<td>${loanContract.customerName}</td>
				<td align="right">贷款行业：</td>
				<td>${fns:getDictLabel(loanContract.industryId, 'industry_id', '')}</td>
			</tr>
			<tr>
				<td align="right">贷款期限：</td>
				<td>${loanContract.loanPeriod}</td>
				<td align="right">贷款用途：</td>
				<td>${fns:getDictLabel(loanContract.purposeId, 'product_purpose', '')}</td>
				<td align="right">贷款方式：</td>
				<td>${fns:getDictLabel(loanContract.loanType, 'loan_type', '')}</td>
			</tr>
			<tr>
				<td align="right">借款区域：</td>
				<td>${loanContract.area.name}</td>
				<td align="right">申请日期：</td>
				<td><c:if test="${!empty loanContract.applyDate}">
						<fmt:formatDate value="${loanContract.applyDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				<td align="right">签合同日期：</td>
				<td><c:if test="${!empty loanContract.signDate}">
						<fmt:formatDate value="${loanContract.signDate}" pattern="yyyy-MM-dd" />
					</c:if></td>
			</tr>
			<tr>
				<td align="right">贷款金额：</td>
				<td>${loanContract.loanAmount}</td>
				<td align="right">合同状态：</td>
				<td>${fns:getDictLabel(loanContract.status, 'loan_contract_status', '')}</td>
				<td align="right">还款方式：</td><!-- loanContract.loanType -->
				<td>${fns:getDictLabel(loanContract.payType, 'product_paytype', '')}</td>
			</tr>
			<tr>
				<td align="right">还款周期：</td>
				<td>${fns:getDictLabel(loanContract.periodType, 'period_type', '')}</td>
				<td align="right">放款日期：</td>
				<td><c:if test="${!empty loanContract.loanDate}">
						<fmt:formatDate value="${loanContract.loanDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				<td align="right">还本金日期：</td>
				<td><c:if test="${!empty loanContract.payPrincipalDate}">
						<fmt:formatDate value="${loanContract.payPrincipalDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
			</tr>
			<tr>
				<td align="right">还款选项：</td>
				<td>${loanContract.payOptions}</td>
				<td align="right">每期还款日：</td>
				<td>${loanContract.payDayType}</td>
				<td align="right">贷款利率：</td>
				<td>${loanContract.loanRate}</td>
				<%-- <td align="right">违约金比例：</td>
				<td>${loanContract.punishAmountRate}</td> --%>
			</tr>
			<%-- <tr>
				<td align="right">逾期罚息利率：</td>
				<td>${loanContract.punishInterestRate}</td>
				<td align="right">违约金：</td>
				<td>${loanContract.punishAmount}</td>
				<td align="right">违约金比例：</td>
				<td>${loanContract.punishAmountRate}</td>
			</tr> --%>
			<tr>
				<td align="right">收款人：</td>
				<td>${loanContract.gatheringName}</td>
				<td align="right">收款账号：</td>
				<td>${loanContract.gatheringNumber}</td>
				<td align="right">收款银行：</td>
				<td>${loanContract.gatheringBank}</td>
			</tr>
			<tr>
				<td align="right">产品类型：</td>
				<td colspan="5">${product.name}</td>
			</tr>
			<%-- <tr>
				<td align="right">贷款利率：</td>
				<td colspan="5">${loanContract.loanRate}</td>
			</tr> --%>
		</tbody>
	</table>
	<br/>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>