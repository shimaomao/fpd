<%@ page contentType="text/html;charset=UTF-8" import="java.util.*"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="option" value="${empty param.option ? 'edit' : param.option}"/>	option = view|sign|edit  查看|签订|申请审核
<c:set var="editable" value="${empty tLoanContract.act.procInsId ? true : false}" ></c:set>
<html>
<head>
	<title>业务办理管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/pay_plan.js?v=5"></script>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['app/repayPlan'], function(rp){
		//初始化展期还款计划--
		rp.initialize({
			businessType : "extend",  //apply|extend|earlyrepay...
			businessId : "${tLoanContract.parent.id}",
			amount : "${tLoanContract.loanAmount}",
			loanRate : "${tLoanContract.loanRate}",
			payPrincipalDate:'<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>',
			loanRateType : "${tLoanContract.loanRateType}",
			loanPeriod : "${tLoanContract.loanPeriod}",
			//loanDate : '<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>',
			loanDate : '<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>',
			payType : "${tLoanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : "${tLoanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : "${tLoanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : "${tLoanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			ifRealityDay : "${tLoanContract.ifRealityDay}",//大小月
			extendDays : "${tLoanContract.extendDays}" //展期天数
		},"#showplansDiv");
		
		//初始化原还款计划--
		rp.initLocalPlans("${tLoanContract.parent.id}","#oldRepayPlansDiv");
		
		$(function(){
			<c:if test="${isClose == 1}">
				showTip("${message}");
				//top.window.location.href="${ctx}/refund/reimburse";
				window.parent.document.getElementById("mainFrame").src= "${ctx}/refund/reimburse";
				//window.location.reload();
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
			
			//附件
			var businesstype = "<%=FileType.FILE_TYPE_EXTEND%>";
			<c:if test="${option == 'sign'}">
				businesstype = "<%=FileType.FILE_TYPE_EXTEND_SIGN%>";
			</c:if>
			var url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?height=80&businesstype="+businesstype+"&oper=${(option == 'sign' || editable) ? 'edit' : 'view'}&nid=${nid}file";
		    $("#${nid}filelist").load(url);
			
			//生成展期还款计划
			$("#loanAmount,#loanRate,#loanPeriod,#applyDate,#payDay,#extendDays,input[name='payType'],input[name='periodType'],#payOptionsList,input[name='ifRealityDay']").change(function(){
				var amount = $("#loanAmount").val();
				var loanRate = $("#loanRate").val();
				var loanPeriod = $("#loanPeriod").val();
				//var loanDate = $("#applyDate").val();
				var loanDate = $("#loanDate").val();//还本金日期
				var payDay = $("#payDay").val();
				var payType = getCheckValue("payType")[0];
				var periodType = getCheckValue("periodType")[0];
				var loanRateType =$("#loanRateType").val();
				var ifRealityDay = getCheckValue("ifRealityDay")[0];//大小月
				var payOptions = getCheckValue("payOptionsList").join();
				var extendDays = $("#extendDays").val();//展期天数
				console.info($("#inputForm").serialize());
				/* overdueCount */
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
				
				if(payType=='4' || payType=='5'){
					if(!periodType){
						showTip("请填写还款周期");
						return;					
					}
				}
				//还款计划--
				rp.initialize({
					businessType : "extend",  //apply|extend|earlyrepay...
					businessId : "${tLoanContract.parent.id}",
					payPrincipalDate:$("#payPrincipalDate").val(),
					amount : amount,//未还本金在TLoanContractController中已处理
					loanRate : loanRate,
					loanRateType: loanRateType,
					loanPeriod : loanPeriod,
					loanDate : loanDate,
					payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
					periodType : periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
					payDay : payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
					payOptions : payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
					ifRealityDay :ifRealityDay,//大小月
					extendDays :extendDays//展期天数
				},"#showplansDiv");
				
			});
			
		});
	});
		
	//计算还本金日期
	function initPayPrincipalDate(){
		var payType = getCheckValue("payType")[0];
		var periodType = getCheckValue("periodType")[0];
		//var loanDate = $("#applyDate").val();
		var loanDate = $("#loanDate").val();
		var loanPeriod = $("#loanPeriod").val();
		if(!payType || !loanDate || !loanPeriod) return;
		//loanPeriod = Number(loanPeriod) + ${overdueCount};//展期期数+未还期数(到期一次性还本付息为未还期数即为申请期数overdueCount在TLoanContractController中已处理)
		
		//var payPrincipalDate = getPayPrincipalDate(payType,periodType,loanDate,loanPeriod);
		//$("#payPrincipalDate").val(payPrincipalDate);
		//$("#payPrincipalDate").val(addDate(payPrincipalDate,1));//解决计算还本金日期比KIE少一天问题
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
		
	// 日期，在原有日期基础上，增加days天数，默认增加1天
    function addDate(date, days) {
        if (days == undefined || days == '') {
            days = 1;
        }
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
    }
    // 日期月份/天的显示，如果是1位数，则在前面加上'0'
    function getFormatDate(arg) {
        if (arg == undefined || arg == '') {
            return '';
        }

        var re = arg + '';
        if (re.length < 2) {
            re = '0' + re;
        }

        return re;
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
		<sys:message content="${message}"/>		
		<legend>展期申请信息</legend>
		<table class="table-form">
			<tr>
				<td class="tit">产品</td>
				<td>
				    <form:input path="productname" readonly="true"/>
				</td>
				<td class="tit">申请日期</td>
				<td>
                   <%--   <input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="<%=new java.util.Date() %>" pattern="yyyy-MM-dd"/>" ${editable ? '' : 'disabled'}
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/> --%>
				   <%--  <input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>" ${editable ? '' : 'disabled'}
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/> --%>
					<input id="applyDate" name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>" />
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
					<form:input path="loanPeriod" readonly="${!editable}" htmlEscape="false" maxlength="11" class="input-xlarge " onchange="initPayPrincipalDate();"/>&nbsp;期
				</td>
			    <td class="tit">展期天数</td>
				<td>
			       <form:select path="extendDays" class="input-xlarge" disabled="${!editable}">
					   <form:options items="${fns:getDictList('extends_days')}"  itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
			    </td> 
			</tr>	
			<tr>
			    <td class="tit">是否大小月</td>
				<td>
				    <form:radiobuttons path="ifRealityDay" disabled="${!editable}"  items="${fns:getDictList('yes_no')}"  itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				</td>
				<td class="tit">还款周期</td>
				<td>
					<c:forEach var="item" items="${fns:getDictList('period_type')}">
						${item.label}<input name="periodType" type="radio" value="${item.value}"  <c:if test='${item.value eq tLoanContract.periodType }'>checked="checked"</c:if> <c:if test='${item.value ne tLoanContract.periodType }'>disabled="disabled" </c:if> />  
					</c:forEach>
				   <%--  <form:radiobuttons path="periodType"  readonly="true" disabled="true" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				 --%></td>
			</tr>	
			<tr>
				
				<td class="tit">放款日期</td>
				<td>
				   	<input id="loanDate" name="loanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>" />
				</td>
				<td class="tit">还本金日期</td>
				<td>
					<input name="payPrincipalDate" id="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
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
				<td class="tit">固定还款日</td>
				<td>
					  <form:select path="payDay" class="input-xlarge " readonly="true" disabled="true">
						 <form:option value="" label=""/>
						 <form:options items="${fns:getDictList('dfDays')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<form:input path="fiveLevel" cssStyle="display:none"  htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<form:input path="maxNumber" cssStyle="display:none" htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<form:input path="guarantNumber"  cssStyle="display:none" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<form:input path="signDate" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="status" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="${tLoanContract.status}"/>
		
		<br>
	    <!-- 还款计划-- -->
		<fieldset>
			<legend>展期还款计划</legend>
			<div id="showplansDiv">
			</div>
		</fieldset>
	    <!-- 还款计划-- -->
		<fieldset>
			<legend>原还款计划</legend>
			<div id="oldRepayPlansDiv">
			</div>
		</fieldset>
		
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
			<%-- <c:if test="${option == 'edit' }">
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
			</c:if> --%>
			<c:if test="${option == 'edit' }">
				<c:if test="${empty tLoanContract.act || empty tLoanContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
				</c:if>
				<c:if test="${not empty tLoanContract.act && not empty tLoanContract.act.procInsId }">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');$('#flag').val('1');"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#vars').val('0');$('#flag').val('0');"/>&nbsp;
					<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#vars').val('-1');$('#flag').val('-1');"/>&nbsp;
				</c:if>
			</c:if>
			<c:if test="${option == 'sign' }">
				<%-- <input id="btnSign" class="btn btn-primary" type="button" value="签订" onclick="extendSign('${fiveLevel.id}');"/> --%>
				<input id="btnSign" class="btn btn-primary" type="button" value="签订" onclick="extendSign('${tLoanContract.id}');"/>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</c:if>
			
		</div>
		</c:if>
		<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
	</form:form>
</body>
<script type="text/JavaScript">
var workorderParamParentCode=document.getElementById("workorderParamParentCode");
var i=workorderParamParentCode.selectedIndex;//这里最好放到页面最下面
function setDefault() {
workorderParamParentCode.selectedIndex=i;
}
</script>
</html>