<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="option" value="${empty param.option ? 'edit' : param.option}"/>	option = view|sign|edit  查看|签订|申请审核
<c:set var="editable" value="${empty tLoanContract.act.procInsId ? true : false}" ></c:set>

<html>
<head>
	<title>业务办理管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
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
			//还款计划
			initPlans()
			
			//展现原还款计划
			initOldPlans();
			
			//附件
			var businesstype = "<%=FileType.FILE_TYPE_EXTEND%>";
			<c:if test="${option == 'sign'}">
				businesstype = "<%=FileType.FILE_TYPE_EXTEND_SIGN%>";
			</c:if>
			var url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?height=80&businesstype="+businesstype+"&oper=${(option == 'sign' || editable) ? 'edit' : 'view'}&nid=${nid}file";
		    $("#${nid}filelist").load(url);
			
			//生成展期还款计划
			$("#createPlans").click(function(){
				var amount = $("#loanAmount").val();
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
			
		});
		
		//初始化还款计划
		function initPlans(){
			<c:if test="${fn:length(tLoanContract.repayPlanList) > 0}">
				var plangrid = createRepayPlansGrid("#showplans");
				//原还款计划数据
				<c:forEach var="p" items="${tLoanContract.repayPlanList}">
					plangrid.jqGrid("addRowData", "${p.id}", 
						{ 	id: "${p.id}",
							num: "${p.num}",
							startDate: "${p.startDate}",
							endDate: "${p.endDate}",
							accountDate: "${p.accountDate}",
							principal: "${p.principal}",
							interest: "${p.interest}"
						
						});
				</c:forEach>
			</c:if>
		}
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
			                 { name: "accountDate", index: "accountDate", label:"计划到账日", width: 80, hidden: false,align: "center", search: false, sortable: false},
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
			<c:forEach var="p" items="${tLoanContract.parent.repayPlanList}">
				oldPlanGrid.jqGrid("addRowData", "${p.id}", 
						{ 	id: "${p.id}",
							num: "第${p.num}期",
							csNum: "${p.csNum}",
							startDate: "${p.startDate}",
							endDate: "${p.endDate}",
							accountDate: "${p.accountDate}",
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
		
		//签订展期合同
		function extendSign(id){
			//检查是否上传附件
			var fileIds = $("#grid_${nid}file").getDataIDs();
			if(!fileIds || fileIds.length == 0) {
				showTip("请添加附件");
				return;
			}
			$.get('${ctx}/contract/tLoanContract/extendSign' ,{id: id}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签订完成');
		            location = '${ctx}/index/todo/todoList';
				}else{
		        	top.$.jBox.tip('签订失败');
				}
		    });
		}
		
	</script>
</head>
<body>
	
	<form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/extendSave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="parent.id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="vars" name="act.vars.map['pass']" />
		
		<form:hidden path="customerId"/>
		<form:hidden path="customerType"/>
		<form:hidden path="loanRateType"/>
		<form:hidden path="loanType"/>
		<form:hidden path="productId"/>
		<form:hidden path="area.id"/>
		<form:hidden path="purposeId"/>
		<form:hidden path="industryId"/>
		<br/>
		展期申请信息
		<p/>
		<sys:message content="${message}"/>		
		<table class="table-form">
			<tr>
				<td class="tit">产品</td>
				<td>
				    <form:input path="productname" readonly="true"/>
				</td>
				<td class="tit">申请日期</td>
				<td>
				     <input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>" ${editable ? '' : 'disabled'}
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/>
				</td>
			</tr>
			<tr>
				<td class="tit">合同编号</td>
				<td>
				      <form:input path="contractNumber" readonly="true" htmlEscape="false" maxlength="245" class="input-xlarge "/>
				</td>
				<td class="tit">客户</td>
				<td> 
				      <form:input path="customerName" readonly="true" htmlEscape="false" maxlength="245" class="input-xlarge "/>
				</td>
			</tr>
			<tr>
				<td class="tit">贷款金额</td>
				<td>
				   	<form:input path="loanAmount" id="loanAmount" readonly="true" htmlEscape="false" class="input-xlarge "/>
				</td>
				<td class="tit">展期利率</td>
				<td>
					<form:input path="loanRate" id="loanRate" readonly="${!editable}" htmlEscape="false" class="input-xlarge" />&nbsp;%&nbsp;/年
				</td>
			</tr>	
			<tr>
			    <td class="tit">展期期限</td>
				<td>
					<form:input path="loanPeriod" readonly="${!editable}" htmlEscape="false" maxlength="11" class="input-xlarge "/>&nbsp;期
				</td>
			    <td class="tit">还款周期</td>
				<td>
				    <form:radiobuttons path="periodType" disabled="${!editable}"  items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</td>
			
			</tr>	
			<tr>
				
				<td class="tit">放款日期</td>
				<td>
				   	<input name="loanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>" />
				</td>
				<td class="tit">还本金日期</td>
				<td>
					<input name="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>" />
				</td>
			</tr>	
		   <tr>
				
				<td class="tit">贷款地区</td>
				<td>
					<form:input path="area.name" readonly="true"/>
				</td>
				<td class="tit">贷款方式</td>
				<td>
					 <form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" readonly="true" disabled="true" htmlEscape="false" class="" />
				</td>
			</tr>	
			<tr>
				<td class="tit">贷款用途</td>
				<td>
					<form:select path="purposeId" class="input-xlarge " readonly="true" disabled="true">
					   <form:option value="" label=""/>
					   <form:options items="${fns:getDictList('product_purpose')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td class="tit">贷款行业</td>
				<td>
					   <form:select path="industryId" class="input-xlarge " readonly="true" disabled="true">
						 <form:option value="" label=""/>
						 <form:options items="${fns:getDictList('industry_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					   </form:select>
				</td>
			</tr>	
			<tr>
				<td class="tit">还款选项</td>
				<td>
					<form:checkboxes path="payOptionsList" items="${fns:getDictList('pay_options')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</td>
				<td class="tit">每期还款日</td>
				<td>
					 <form:select path="payDay" readonly="${!editable}" disabled="${!editable}"  class="input-xlarge ">
						<form:option value="" label="按实际放款日"/>
						<c:forEach var="i" begin="1" end="31" step="1"> 
					      	<form:option value="${i}" label="${i}号"/>
					    </c:forEach>
					</form:select>
				</td>
			</tr>	
			<tr>
				<td class="tit">还款方式</td>
				<td>
				     <form:radiobuttons path="payType"  disabled="${!editable}"  items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</td>
				<td class="tit">开户名</td>
				<td>
				   <form:input path="gatheringName" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
	         <tr>
				<td class="tit">开户行</td>
				<td>
					<form:input path="gatheringBank" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">开户账号</td>
				<td>
					<form:input path="gatheringNumber" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
			
		   <tr>
				<td class="tit">前期服务费(%)</td>
				<td>
					<form:input path="serverFee" id="serverFee" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				</td>
				<td class="tit">管理费（%）</td>
				<td>
					<form:input path="mangeFee" id="mangeFee" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		   <tr>
				<td class="tit">是否可提前还款</td>
				<td>
				 	<form:radiobuttons path="ifAdvance" items="${fns:getDictList('yes_no')}" readonly="${!editable}" disabled="${!editable}"  itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   	违约金%<form:input path="advanceDamages" id="advanceDamages" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">滞纳金</td>
				<td>
					<form:input path="lateFee" id="lateFee" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		    <tr>
				<td class="tit">费率优惠率（%）</td>
				<td>
					<form:input path="rateDiscont" id="rateDiscont" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">允许利息减免（%）</td>
				<td>
					<form:input path="ifInterestRelief" id="ifInterestRelief" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
			<tr>
				<td class="tit">宽限期（天）</td>
				<td>
					<form:input path="gracePeriod" id="gracePeriod" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">宽限期罚息（%）</td>
				<td>
					<form:input path="gracePeriodPenalty" id="gracePeriodPenalty" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		     <tr>
				<td class="tit">逾期罚息（%）</td>
				<td>
					<form:input path="latePenalty" id="latePenalty" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">逾期罚费（%）</td>
				<td>
					<form:input path="latePenaltyFee" id="latePenaltyFee" readonly="${!editable}" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>				
		    <tr>
				<td class="tit">备注</td>
				<td colspan="3">
				   <form:textarea path="remarks" readonly="${!editable}" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</td>
			</tr>	
		</table>
		        <form:input path="isExtend" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="fiveLevel" cssStyle="display:none"  htmlEscape="false" maxlength="11" class="input-xlarge " value="正常"/>
				<form:input path="maxNumber" cssStyle="display:none" htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<form:input path="guarantNumber"  cssStyle="display:none" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<form:input path="signDate" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="status" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="${tLoanContract.status}"/>
		
		<br>
		 <%-- <shiro:hasPermission name="contract:tLoanContract:extend"> --%>
		 	  <c:if test="${editable}">
		      <input id="createPlans" class="btn btn-primary" type="button" value="生成还款计划" />&nbsp;
		      </c:if>
		 <%-- </shiro:hasPermission> --%>
	    <br>
			<div id="showplans">
			</div>
	    <p/>
		<div>
			<table id="oldRepayPlans_grid"></table>
        	<div id="oldRepayPlans_gridPager"></div>
		</div>
		
		<p/>
		<div id="${nid}filelist" ></div>
		<p/>
		<c:if test="${option == 'edit' }">
		<table class="table-form ">
			<tr>
				<td class="tit">你的意见</td>
				<td>
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20" cssStyle="width:500px"/>
				</td>
			</tr>
		</table>
		</c:if>
		<c:if test="${option != 'view' }">		
		<div class="form-actions">
			<c:if test="${option == 'edit' }">
				<c:if test="${empty tLoanContract.act || empty tLoanContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
				</c:if>
				<c:if test="${not empty tLoanContract.act && not empty tLoanContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#vars').val('0');$('#flag').val('0');"/>&nbsp;
				</c:if>
			</c:if>
			<c:if test="${option == 'sign' }">
				<input id="btnSign" class="btn btn-primary" type="button" value="签订" onclick="extendSign('${tLoanContract.id}');"/>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</c:if>
		</div>
		</c:if>
		<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
	</form:form>
</body>
</html>