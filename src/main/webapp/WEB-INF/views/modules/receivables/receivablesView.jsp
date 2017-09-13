<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
</style>
<title>业务办理管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<%-- <script type="text/javascript" src="${ctxStatic}/jquery/jquery-ui.min.js"></script><!--用户拖动元素，链接：http://api.jqueryui.com/draggable/#method-disable/--> --%>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("#inputForm").validate(
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
				
				//注册新增按钮的事件
				$("#btn_add").click(function () {
					/* if('${loanContract.type}'=='1'){
						alert("此条业务为线上还款，不需要本地操作还款！");
						return;
					} */
				   $("#myModalLabel").text("还款");
				   $('#myModal').modal();
				   $("#home_tab").click();
				});
				$("#home_tab").click(function () {
					   $('#home').show();
					   $('#ios').hide();
					   $('#modalType').val("1");
					});
				$("#ios_tab").click(function (){
					   $('#ios').show();
					   $('#home').hide();
					   $('#modalType').val("2");
					   
					});
				$("#modal_submit").click(function() {
					var modalType=$('#modalType').val();
					var rePrincipal="${rePrincipal}";//当期本金
					var reInterest="${reInterest}";//当期利息
					 var parnt = /^(0|[1-9]\d*)(\.\d{1,2})?$/;
					if(modalType=='2'){
						var interest=$('#reInterest').val();//输入的利息
						var principal=$('#rePrincipal').val();//输入的本金
						var contractDate1=$('#contractDate1').val();//输入的日期
						if(contractDate1 == null || contractDate1 == 'undefined' || contractDate1 == ''){
					        	showTip("还款日期不能为空");
						    	return false;  
					        }
				        if(!parnt.exec(principal) || !parnt.exec(interest)){
				        	showTip("输入本金和利息必须大于0,且最多两位小数");
					    	return false;  
				        }
						if(principal>rePrincipal || interest>reInterest){
							showTip("输入的本金和利息必须分别小于等于当期的本金和利息");
							return false;
						}
						var options = {
								url : '${ctx}/receivables/receivables/singleReceMoney',//单期还款
								type : 'post',
								dataType : 'json',//'text',
								data : $("#inputForm1").serializeArray(),
								success : function(data, textStatus) {
									alert(data.msg);
									var url = "${ctx}/receivables/receivables/receView?message="+encodeURI(encodeURI(data.result))+"&contractId="+data.content;
									location.href = url;
								}
							};
							$.ajax(options);
							return false;
					}else{
					  var contractAmount=$('#contractAmount').val();//输入的金额  本金+利息
					  var contractDate2=$('#contractDate2').val();//输入的日期
					  if(contractDate2 == null || contractDate2 == 'undefined' || contractDate2 == ''){
				        	showTip("还款日期不能为空");
					    	return false;  
				        }
					  if(!parnt.exec(contractAmount)){
				        	showTip("输入还款金额必须大于0,且最多两位小数");
					    	return false;  
				        }
					  var options = {
						url : '${ctx}/receivables/receivables/receMoney',//默认还款
						type : 'post',
						dataType : 'json',//'text',
						data : $("#inputForm2").serializeArray(),
						success : function(data, textStatus) {
							alert(data.msg);
							var url = "${ctx}/receivables/receivables/receView?message="+encodeURI(encodeURI(data.result))+"&contractId="+data.content;
							location.href = url;
						}
					};
					$.ajax(options);
					return false;
					}
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
		<li class="active"><a href="${ctx}/receivables/receivables/receView?message=1&contractId=${contractId}">还款管理</a></li>
		<li><a href="${ctx}/receivables/receivables/freeView?message=1&contractId=${contractId}">收费管理</a></li>
	</ul>
     <input id="modalType" name="modalType" type="hidden" value=""/>
		 <ul class="ul-form">
			<li><label>还款信息：</label>
			      <span id="message_w">${message}</span> <span></span>
			</li>
		</ul>
		<br>
		<button type="button" id="btn_add" >还款</button>
	

 <div  class="modal fade"  id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div id="modalDialog" class="modal-dialog" role="document">
 
  <div class="modal-content">
   <ul id="myTab" class="nav nav-tabs">
    <li>
    <a  data-toggle="tab" id="ios_tab">单期还款</a>
   </li>
   <li class="active">
     <a  data-toggle="tab" id="home_tab">默认还款</a>
   </li>
  </ul>
  </div>
  <div class="modal-body">
 <div id="myTabContent" class="tab-content">
 
 <div class="tab-pane fade in active" id="ios">
  <form:form id="inputForm1" method="post">
   <input id="contractId" name="contractId" type="hidden" value="${contractId}"/>
   <div class="form-group">
   <label for="txt_departmentname">还款时间：</label>
   <input name="contractDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate required " id="contractDate1"
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
   </div>
   <div class="form-group">
   <label for="txt_parentdepartment">还款本金：</label>
   <input type="text" name="rePrincipal" id="rePrincipal" class="form-control" id="rePrincipal" value="${rePrincipal}">
   </div>
    <div class="form-group">
   <label for="txt_parentdepartment">还款利息：</label>
   <input type="text" name="reInterest" id="reInterest" class="form-control" id="reInterest" value="${reInterest}">
   </div>
    <div class="form-group">
   <label for="txt_parentdepartment">还款期数：</label>
   <input type="text" name="num" readonly="readonly" class="form-control" id="num" value="${num}">
   </div>
   <div class="form-group">
   <label for="txt_parentdepartment">是否结清：</label>
                 否：<input type="radio" name="isFinsh" class="form-control" id="isFinsh" value="0" checked>
                 是：<input type="radio" name="isFinsh" class="form-control" id="isFinsh" value="1">
   </div>
   </form:form>
  </div>
  
   <div class="tab-pane fade in active" id="home">
   <form:form id="inputForm2" method="post">
   <input id="contractId" name="contractId" type="hidden" value="${contractId}"/>
   <div class="form-group">
   <label for="txt_departmentname">还款时间：</label>
     <input name="contractDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate required " id="contractDate2"
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
   </div>
   <div class="form-group">
   <label for="txt_parentdepartment">还款金额：</label>
   <input type="text" name="contractAmount" class="form-control" id="contractAmount" placeholder="本金+利息">
   </div>
   </form:form>
 </div>
  </div>
  
  <div class="modal-footer">
   <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
   <button type="button" id="modal_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>提交</button>
  </div>
  </div>
 </div>
</div>
	
	
	
	<form:form id="punishForm" modelAttribute="realIncome" method="post" class="breadcrumb form-search">
	<input id="loanContractId" name="loanContractId" type="hidden" value="${contractId}">
	<input id="guaranteeFee" name="guaranteeFee"  class="input-xlarge  number" type="hidden"  style="width: 170px;" value="0" >
	<input id="reviewFee" name="reviewFee"  class="input-xlarge  number" type="hidden"  style="width: 170px;" value="0">

	</form:form>
	<hr>
	<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td colspan="4">
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
								<td rowspan="2">结清日期</td>
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
							<c:forEach items="${repayPlanList}" var="repayPlan">
								<tr>
									<td>第${repayPlan.num}期</td>
									<td>${repayPlan.csNum}</td>
									<td>${repayPlan.startDate}</td>
									<td>${repayPlan.endDate}</td>
									<td>${repayPlan.accountDate}</td>
									<td>${repayPlan.overDate}</td>
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
	</table>
	<table cellpadding="0" cellspacing="0" width="100%">
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
									<td align="center">&nbsp; 
									   <c:if test="${re.last && loanContract.type != '1'}">
									       <c:if test="${repayRecord.isYuQi ne 'newPlan'}"><!-- 最后一期且是在重新生成还款计划之前的记录才可修改 -->
											<a href="javascript:void(0);"
												onclick="update_real('${repayRecord.id}','${repayRecord.loanContractId}')"
												class="del">修改</a>
										   </c:if>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
				</tr>
	</table>
		<table cellpadding="0" cellspacing="0" width="100%">
		<tr>
		
					
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
		</table>	
		<table cellpadding="0" cellspacing="0" width="100%">	
			<tr>
				<%-- <td width="49" valign="top">
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
				</td> --%>
			
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
				</td>
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