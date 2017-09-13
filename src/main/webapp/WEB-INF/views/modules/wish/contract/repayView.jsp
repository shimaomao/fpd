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

				/* $("#btnSubmit").click(function() {
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
				}); */
				
				//注册新增按钮的事件
				$("#btn_add").click(function () {
					/* if('${loanContract.type}'=='1'){
						alert("此条业务为线上还款，不需要本地操作还款！");
						return;
					} */
				   $("#myModalLabel").text("还款");
				   $('#myModal').modal();
				  // $("#home_tab").click();
				});
			
				$("#modal_submit").click(function() {
					var modalType=$('#modalType').val();
					var rePrincipal="${rePrincipal}";//当期本金
					var reInterest="${reInterest}";//当期利息
					var noMoney="${noMoney}";//当期利息
					 var parnt = /^(0|[1-9]\d*)(\.\d{1,2})?$/;
					
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
					  if(contractAmount>noMoney){
				        	showTip("输入还款金额不能大于剩余还款金额："+noMoney);
					    	return false;  
				        }
					  var options = {
						url : '${ctx}/wish/contract/wishContract/repayMoney',//默认还款
						type : 'post',
						dataType : 'json',//'text',
						data : $("#inputForm2").serializeArray(),
						success : function(data, textStatus) {
							alert(data.msg);
							var url = "${ctx}/wish/contract/wishContract/repayView?contractId="+data.contractId;
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

	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wish/contract/wishContract/repayView?message=1&contractId=${contractId}">还款管理</a></li>
		<%-- <li><a href="${ctx}/receivables/receivables/freeView?message=1&contractId=${contractId}">收费管理</a></li> --%>
	</ul>
     <input id="modalType" name="modalType" type="hidden" value=""/>
	<button type="button" id="btn_add" >收款</button>
  <div  class="modal fade"  id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div id="modalDialog" class="modal-dialog" role="document">
 
  <div class="modal-content">
   <ul id="myTab" class="nav nav-tabs">

   <li class="active">
     <a  data-toggle="tab" id="home_tab">收款</a>
   </li>
  </ul>
  </div>
  <div class="modal-body">
 <div id="myTabContent" class="tab-content">
   <div class="tab-pane fade in active" id="home">
   <form:form id="inputForm2" method="post">
   <input id="contractId" name="contractId" type="hidden" value="${contractId}"/>
   <input id="noMoney" name="noMoney" type="hidden" value="${noMoney}"/>
   <div class="form-group">
   <label for="txt_departmentname">还款时间：</label>
     <input name="contractDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate required " id="contractDate2"
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
   </div>
   <div class="form-group">
   <label for="txt_parentdepartment">还款金额：</label>
   <input type="text" name="contractAmount" class="form-control" id="contractAmount" placeholder="剩余应还:${noMoney}">
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
	<table cellpadding="0" cellspacing="0" width="100%"
		id="loanContractTable"
		class="table table-striped table-bordered table-condensed">
		<h3 align="center">业务信息</h3>
		<tbody>
			<tr>
				<td align="right">合同编号：</td>
				<td>${loanContract.contractNumber}</td>
				<td align="right">借款人：</td>
				<td>${loanContract.customerName}</td>
				
			</tr>
			<tr>
				<td align="right">贷款期限：</td>
				<td>${loanContract.loanPeriod}</td>
				<td align="right">申请日期：</td>
				<td><c:if test="${!empty loanContract.applyDate}">
						<fmt:formatDate value="${loanContract.applyDate}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				
			</tr>
			
			<tr>
				<td align="right">贷款金额：</td>
				<td>${loanContract.loanAmount}</td>
				<td align="right">合同状态：</td>
				<td>${fns:getDictLabel(loanContract.status, 'loan_contract_status', '')}</td>
				
			</tr>
			<tr>
			   <td align="right">还款方式：</td><!-- loanContract.loanType -->
				<td>${fns:getDictLabel(loanContract.payType, 'product_paytype', '')}</td>
				<td align="right">还款周期：</td>
				<td>${fns:getDictLabel(loanContract.periodType, 'period_type', '')}</td>
				
			</tr>
			<tr>
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
			   <td align="right">手续费：</td>
				<td>${loanContract.wishCharge}</td>
				<td align="right">贷款利率：</td>
				<td>${loanContract.loanRate}</td>
			</tr> 
			<tr>
				<td align="right">收款人：</td>
				<td>${loanContract.gatheringName}</td>
				<td align="right">收款账号：</td>
				<td>${loanContract.gatheringNumber}</td>
				
			</tr>
			 <tr>
			    <td align="right">收款银行：</td>
				<td colspan="2">${loanContract.gatheringBank}</td>
				
			</tr> 
		</tbody>
	</table>
	
<%-- 	<hr>
<table cellpadding="0" cellspacing="0" width="100%">
	     <tr>
				<td width="49" valign="top">
					<h3 align="center">收款记录</h3>
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
	<br/> --%>
	
   <table cellpadding="0" cellspacing="0" width="100%">
	     <tr>
				<td width="49" valign="top">
					<h3 align="center">还款记录</h3>
					<table width="99%"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr class="tit_center_bg">
								<td width="10%">期数</td>
							<!-- 	<td>日期</td> -->
								<td>应还款金额</td>
								<td>已还款金额</td>
								<td>是否逾期</td>
								<td>逾期天数</td>
							
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${planList}" var="plan"
								varStatus="re">
								<tr>
									<td>${plan.num}</td>
									<%-- <td><fmt:formatDate value="${repayRecord.repayDate}"
											pattern="yyyy-MM-dd" /></td> --%>
									<td>${plan.principal}</td>
									<td>${plan.isYuQi}</td>
									<td>${plan.yuQi}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
				</tr>
	</table>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>