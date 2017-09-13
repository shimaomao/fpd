<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="readonly" value="${empty extendContract.act.procInsId ? false : true}" ></c:set>
<c:set var="edit" value="${extendContract.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>展期申请管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/pay_plan.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
			
			//生成展期还款计划
			$("#createPlans").click(function(){
				var amount = $("#amount").val();
				var loanRate = $("#loanRate").val();
				var loanPeriod = $("#loanPeriod").val();
				var loanDate = $("#applyDate").val();
				var payDay = $("#payDay").val();
				var payType = getCheckValue("payType")[0];
				var periodType = getCheckValue("periodType")[0];
				var payOptions = getCheckValue("payOptionsList").join();
				//数据校验
				if(!loanDate){
					showTip("请填写申请日期");
					return;								
				}
				if(!loanPeriod){
					showTip("请填写展期期数");
					return;					
				}
				if(!payType){
					showTip("请填写还款方式");
					return;					
				}
				if(!loanRate){
					showTip("请填写利率");
					return;					
				}
					
				createPayPlan({
						 amount : amount,
						 loanRate : loanRate,
						 loanPeriod : Number(loanPeriod) + ${overdueCount},
						 loanDate : loanDate,
						 payType : payType,
						 periodType : periodType,
						 payDay : payDay,
						 payOptions : payOptions
				},"#showplans");
			});
			
			//展现原还款计划
			initOldPlans();
			
			//附件
			var url = "${ctx}/files/tContractFiles/showfilelist/${extendContract.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=${readonly ? 'view' : 'edit'}&nid=${nid}file";
		    $("#${nid}filelist").load(url);
		});
		
		//初始化展示原还款计划
		function initOldPlans(){
			//原还款计划grid
			var oldPlanGrid = $("#oldRepayPlans_grid").jqGrid({
				caption:"原还款计划列表", 
				datatype:"local",
		        height: 150,
		        autowidth: true,
		        shrinkToFit:true,
		        jsonReader: {
		            repeatitems: false,
		            id: "id"
		        },
		        colModel: [
			                 { name: "id", index: "id", width: 0, hidden: true, search: false, sortable: false,editable:true },
			                 { name: "num", index: "num", label:"期数", width: 50, hidden: false,align: "center", search: false, sortable: false},
			                 { name: "csNum", index: "csNum", label:"催收次数", width: 40, hidden: false,align: "center", search: false, sortable: false},
	                 		 { name: "startDate", index: "startDate", label:"计息开始日期", width: 80, hidden: false,align: "center", search: false, sortable: false},
	                 		 { name: "endDate", index: "endDate", label:"计息结束日期", width: 80, hidden: false,align: "center", search: false, sortable: false},
			                 { name: "payInterestDate", index: "payInterestDate", label:"计划到账日", width: 80, hidden: false,align: "center", search: false, sortable: false},
			                 { name: "principal", index: "principal",label:"应还本金", width: 150, hidden: false, search: false, sortable: false,
			                 		editable:true,align: "center", formatter: 'currency',editable:true,editoptions: { money:true,maxlength:'18'} },
			                 { name: "principalReal", index: "principalReal",label:"已还本金", width: 80, hidden: false, search: false, sortable: false,
			                 		editable:true,align: "center", formatter: 'currency',editable:true,editoptions: { money:true,maxlength:'18'} },
			                 { name: "interest", index: "interest",label:"应还利息", width: 80, hidden: false, search: false, sortable: false,
			                 		editable:true,align: "center", formatter: 'currency',editable:true,editoptions: { money:true,maxlength:'18'} },
			                 { name: "interestReal", index: "interestReal",label:"已还利息", width: 80, hidden: false, search: false, sortable: false,
			                 		editable:true,align: "center", formatter: 'currency',editable:true,editoptions: { money:true,maxlength:'18'} },
			                 { name: "status", index: "status", label:"状态", width: 40, hidden: false,align: "center", search: false, sortable: false},
			             ]
		    });
						
			//原还款计划数据
			<c:forEach var="p" items="${extendContract.loanContract.repayPlanList}">
				oldPlanGrid.jqGrid("addRowData", "${p.id}", 
						{ 	id: "${p.id}",
							num: "第${p.num}期",
							csNum: "${p.csNum}",
							startDate: "${p.startDate}",
							endDate: "${p.endDate}",
							payInterestDate: '<fmt:formatDate value="${p.payInterestDate}" pattern="yyyy-MM-dd"/>',
							principal: "${p.principal}",
							principalReal: "${p.principalReal}",
							interestReal: "${p.interestReal}",
							interest: "${p.interest}",
							status: "${fns:getDictLabel(p.status, 'repay_status', '')}"
						
						});
			</c:forEach>
		}
		
		//计算还本金日期
		function initPayPrincipalDate(){
			var payType = getCheckValue("payType")[0];
			var periodType = getCheckValue("periodType")[0];
			var loanDate = $("#applyDate").val();
			var loanPeriod = $("#loanPeriod").val();
			if(!payType || !loanDate || !loanPeriod) return;
			loanPeriod = Number(loanPeriod) + ${overdueCount};
			var payPrincipalDate = getPayPrincipalDate(payType,periodType,loanDate,loanPeriod);
			$("#payPrincipalDate").val(payPrincipalDate);
		}
		
		
	</script>
</head>
<body>
	<br/>
	
	<form:form id="inputForm" modelAttribute="extendContract" action="${ctx}/contract/extendContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="loanContract.id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="vars" name="act.vars.map['pass']" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">展期编号：</label>
			<div class="controls">
				<form:input path="contractNumber" readonly="${readonly}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				<form:input path="loanContract.customerName" readonly="true"  htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请日期：</label>
			<div class="controls">
				<input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${extendContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="loanContract.productId" readonly="true"  htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期金额：</label>
			<div class="controls">
				<form:input path="amount" readonly="true" htmlEscape="false" class="input-xlarge  number"/>&nbsp;单位:元 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">每期还款日：</label>
			<div class="controls">
				<form:select path="payDay" readonly="${readonly}" disabled="${readonly}" class="input-xlarge ">
					<form:option value="" label="按实际放款日"/>
					<c:forEach var="i" begin="1" end="31" step="1"> 
				      	<form:option value="${i}" label="${i}号"/>
				    </c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">展期期限：</label>
			<div class="controls">
				<form:input path="loanPeriod" readonly="${readonly}" onchange="initPayPrincipalDate();" htmlEscape="false" maxlength="5" class="input-mini  digits"/>&nbsp;期
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
			<form:radiobuttons path="payType" readonly="${readonly}" disabled="${readonly}" items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款周期：</label>
			<div class="controls">
				<form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款期限：</label>
			<div class="controls">
				<form:input path="loanContract.loanPeriod" readonly="true"  htmlEscape="false" maxlength="255" class="input-mini "/>&nbsp;期
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">放款日期：</label>
			<div class="controls">
			<input type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${extendContract.loanContract.loanDate}" pattern="yyyy-MM-dd"/>"
					onclick=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还本金日期：</label>
			<div class="controls">
				<input id="payPrincipalDate" name="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${extendContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>"
					onclick=""/>
			</div>
		</div>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">还款选项：</label> -->
<!-- 			<div class="controls"> -->
<%-- 				<form:checkboxes path="payOptionsList" items="${fns:getDictList('pay_options')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		
		<div class="control-group">
			<label class="control-label">展期利率：</label>
			<div class="controls">
				<form:input path="loanRate" readonly="${readonly}" htmlEscape="false" class="input-mini number"/>&nbsp;%&nbsp;/年&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">管理咨询费费率：</label>
			<div class="controls">
				<form:input path="manageRate" readonly="${readonly}" htmlEscape="false" class="input-mini number"/>&nbsp;%&nbsp;/年&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">财务服务费率：</label>
			<div class="controls">
				<form:input path="serviceRate" readonly="${readonly}" htmlEscape="false" class="input-mini number"/>&nbsp;%&nbsp;/年&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">违约金比例：</label>
			<div class="controls">
				贷款总额&nbsp;<form:input path="punishAmountRate" readonly="${readonly}"  htmlEscape="false" class="input-mini number"/>&nbsp;%
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预期罚息利率：</label>
			<div class="controls">
				<form:input path="overdueFineRate" readonly="${readonly}" htmlEscape="false" class="input-mini number"/>&nbsp;%&nbsp;/日&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预期贷款利率：</label>
			<div class="controls">
				<form:input path="overdueLoanRate" readonly="${readonly}" htmlEscape="false" class="input-mini number"/>&nbsp;%&nbsp;/日&nbsp;&nbsp;&nbsp;
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><input id="createPlans" class="btn btn-primary" type="button" value="生成还款计划" /></label>
			<div id="showplans" class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原还款计划</label>
			<div class="controls">
				<table id="oldRepayPlans_grid"></table>
	        	<div id="oldRepayPlans_gridPager"></div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
				<div id="${nid}filelist" class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">你的意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
			</div>
		</div>
		
		<div class="form-actions">
			<c:if test="${edit}">
			<shiro:hasPermission name="contract:extendContract:edit">
				<c:if test="${empty extendContract.act || empty extendContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#vars').val('1');$('#flag').val('1')"/>&nbsp;
				</c:if>
				<c:if test="${not empty extendContract.act && not empty extendContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');$('#flag').val('1')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#vars').val('0');$('#flag').val('0')"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			</c:if>
		</div>
	</form:form>
</body>
</html>