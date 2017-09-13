<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>放款记录管理</title>
<%-- <%@ include file="/WEB-INF/views/include-builder/head.jsp"%> --%>
<meta name="decorator" content="default" />
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/jqGrid/4.6/css/metor/jquery-ui.css"></link>
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css"></link>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.extend.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/jqGrid/4.6/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript" src="${ctxStatic}/customRepayPlans.js"></script>
<script type="text/javascript">
	//require(['helper/api','app/repayPlan'], function(api,rp){
	//还款计划--
	/* rp.initialize({
		businessType : "apply",  //apply|extend|earlyrepay...
		amount : "${tLending.contract.loanAmount}",
		loanRate : "${tLending.contract.loanRate}",
		loanRateType : "${tLending.contract.loanRateType}",
		loanPeriod : "${tLending.contract.loanPeriod}",
		loanDate : '<fmt:formatDate value="${tLending.contract.loanDate}" pattern="yyyy-MM-dd"/>',
		payType : "${tLending.contract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
		periodType : "${tLending.contract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
		payDay : "${tLending.contract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
		payOptions : "${tLending.contract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
		ifRealityDay: "${tLending.contract.ifRealityDay}"
	},"#showplansDiv"); */
	$(function() {
		//初始化业务数据
		var form = $("#target");
		//禁用form表单中所有的选项
		//disableForm("target",true);

		//生成还款计划
		$("#repayid").show();
		initRepayPlan();
		ver = "2";

		$("#inputForm").validate(
				{
					submitHandler : function(form) {
						loading('正在提交，请稍等...');
						form.submit();
					},
					errorContainer : "#messageBox",
					errorPlacement : function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox") || element.is(":radio")
								|| element.parent().is(".input-append")) {
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
		//变更还款计划
		/* $("#buildRepayPlan").click(function(){
			//console.info('===变更还款计划===');
			var loanDate = $("#loanTime").val();
			//console.info('===loanDate==='+loanDate);
			if(loanDate){
				$("#repayid").html('<div class="span12"><fieldset><legend>还款计划</legend><div id="showplansDiv"></div><div id="jqGridPager"></div></fieldset></div>');
				$("#repayid").show();
				
				loanDate = loanDate.substr(0,10);
				
				updateRepayPlan(loanDate);
			}
		}); */
		$("#buildRepayPlan").click(function() {
					$("#repayid").html('<div class="span12"><fieldset><legend>还款计划</legend><div id="showplansDiv"></div><div id="jqGridPager"></div></fieldset></div>');
					$("#repayid").show();
					updateLoanMsg(form, "new");
			});

		function updateLoanMsg(form, readType) {
			var id = form.find("input[name='contract.id']").val();
			var loanAmount = form.find("input[name='contract.loanAmount']").val();
			var loanRate = form.find("input[name='contract.loanRate']").val();
			var loanRateType = form.find("input[name='contract.loanRateType']:checked").val();//add by srf #3121
			var loanPeriod = form.find("input[name='contract.loanPeriod']").val();
			var loanDate = form.find("input[name='loanDate']").val();
			var payType = form.find("input[name='contract.payType']:checked").val();
			var periodType = form.find("input[name='contract.periodType']:checked").val();
			var payDay = form.find("select[name='payDay']").val();
			var payOptions = getCheckValue('payOptions').join();
			var payPrincipalDate = $("#payPrincipalDate").val();//#3371
			var ifRealityDay = form.find("input[name='ifRealityDay']:checked").val();
			if (payPrincipalDate)
				payPrincipalDate = payPrincipalDate.substr(0, 10);
			if (loanDate)
				loanDate = loanDate.substr(0, 10);
			var dealType = readType;//"new";
			var editContent = true;
			createRepayPlansGrid({
				editContent : editContent,//是否可编辑，是：true；否：false
				dealType : dealType,//"new",//new表示直接新生成；否则读取数据库中为先，没有则新生成
				businessId : id,
				businessType : "apply", //apply|extend|earlyrepay...
				amount : loanAmount,
				loanRate : loanRate,
				loanRateType : loanRateType,//add by srf #3121
				loanPeriod : loanPeriod,
				loanDate : loanDate,
				payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
				periodType : periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
				payDay : payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
				payOptions : payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
				payPrincipalDate : payPrincipalDate,//#3371
				ifRealityDay : ifRealityDay
			//大小月
			}, "#showplansDiv");

		};

		//添加账户操作
		$("#bindAcountOper").click(function() {
			var btn = $(this);
			var flag = eval(btn.attr("data-flag"));
			var btnText = [ "添加账户", "移除账户" ];
			var domTpl = $("#addAccountTpl");
			var domDiv = $("#addAccountDiv");
			var domForm = $("#addAccountForm");

			if (flag) {
				domDiv.html(domTpl);
				domTpl.show();
				btn.val(btnText[1]);
				btn.attr("data-flag", false);
			} else {
				domTpl.hide();
				domForm.html(domTpl);
				btn.val(btnText[0]);
				btn.attr("data-flag", true);
			}
		});

		
		$("input[name='contract.loanPeriod']").change(function(){//贷款期限
			initPayPrincipalDate();
		});
		$("input[name='loanDate']").change(function(){//放款时间
			initPayPrincipalDate();
		});
		
		$("input[name='contract.periodType']").click(function(){
			initPayPrincipalDate();
	    });
		$("input[name='contract.payType']").click(function(){
			initPayPrincipalDate();
			var array = getCheckValue("payType");
			checkPeriodType(array[0]);
	    });
		
		function checkPeriodType(peytype){//当还款方式为等额本金  等额本息 等本等息时，还款周期只能是月
			if(peytype == "1" || peytype == "2" || peytype == "3"){
			    	//还款周期只能是月
					$("input[name='contract.periodType']").each(function(){
						if($(this).val() == "2"){
					    	$(this).attr("checked",true);
						}
					});      //设置不可用
			 }else{
					$("input[name='contract.periodType']").each(function(){
						$(this).removeAttr("disabled");
					});      //设置可用
			 }
		}
		
		//计算还本金日期
		function initPayPrincipalDate(){
			var payType = form.find("input[name='contract.payType']:checked").val();//还款方式
			var periodType = form.find("input[name='contract.periodType']").val();// 还款周期
			var loanDate = form.find("input[name='loanDate']").val();// 放款日期
			var loanPeriod = form.find("input[name='contract.loanPeriod']").val();// 贷款期限
			if(!payType || !loanDate || !loanPeriod) return;
			loanPeriod = Number(loanPeriod);
			var payPrincipalDate = getPayPrincipalDate(payType,periodType,loanDate,loanPeriod);
			//console.info("还本金日期：" + payPrincipalDate);
			$("#payPrincipalDate").val(payPrincipalDate);
		};
		
		
	});


	
	function updateRepayPlan(loanDate) {
		//还款计划--
		var payType = "${tLending.contract.payType}";
		var dealType = "new";
		var editContent = false;
		if (",1,2,3,4,5,".indexOf(payType) > 0) {
			editContent = false;
		} else {
			editContent = true;
		}

		createRepayPlansGrid({
			editContent : editContent,//是否可编辑，是：true；否：false
			dealType : dealType,//"new",//new表示直接新生成；否则读取数据库中为先，没有则新生成
			businessId : "${tLending.contract.id}",
			businessType : "apply", //apply|extend|earlyrepay...
			amount : "${tLending.contract.loanAmount}",
			loanRate : "${tLending.contract.loanRate}",
			loanRateType : "${tLending.contract.loanRateType}",
			loanPeriod : "${tLending.contract.loanPeriod}",
			loanDate : loanDate,
			payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : "${tLending.contract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : "${tLending.contract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : "${tLending.contract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			ifRealityDay : "${tLending.contract.ifRealityDay}"
		}, "#showplansDiv");
	};

	function initRepayPlan() {
		//console.info('===initRepayPlan===');
		var id = "${tLending.contract.id}";
		var loanAmount = "${tLending.contract.loanAmount}";
		var loanRate = "${tLending.contract.loanRate}";
		var loanRateType = "${tLending.contract.loanRateType}";//add by srf #3121
		var loanPeriod = "${tLending.contract.loanPeriod}";
		var loanDate = '<fmt:formatDate value="${tLending.contract.loanDate}" pattern="yyyy-MM-dd"/>';
		var payType = "${tLending.contract.payType}";
		var periodType = "${tLending.contract.periodType}";
		var payDay = "${tLending.contract.payDay}";
		var payOptions = "${tLending.contract.payOptions}";
		var payPrincipalDate = $("#tLending.contract.payPrincipalDate").val();//#3371
		var ifRealityDay = "${tLending.contract.ifRealityDay}";
		if (payPrincipalDate)
			payPrincipalDate = payPrincipalDate.substr(0, 10);
		if (loanDate)
			loanDate = loanDate.substr(0, 10);
		var dealType = "old";
		var editContent = true;

		/* if(",1,2,3,4,5,".indexOf(payType) > 0){
			editContent = false;
		}else{
			editContent = true;
		} */

		createRepayPlansGrid({
			editContent : editContent,//是否可编辑，是：true；否：false
			dealType : dealType,//"new",//new表示直接新生成；否则读取数据库中为先，没有则新生成
			businessId : id,
			businessType : "apply", //apply|extend|earlyrepay...
			amount : loanAmount,
			loanRate : loanRate,
			loanRateType : loanRateType,//add by srf #3121
			loanPeriod : loanPeriod,
			loanDate : loanDate,
			payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			payPrincipalDate : payPrincipalDate,//#3371
			ifRealityDay : ifRealityDay
		//大小月
		}, "#showplansDiv");
	};

	//});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/lending/tLending/listIng">放款待审列表</a></li>
		<%-- 		<li><a href="${ctx}/lending/tLending/listEd">已放款列表</a></li> --%>
		<li class="active"><a
			href="${ctx}/lending/tLending/toLoan?contract.id=${tLending.contract.id}">放款信息</a></li>
	</ul>
	<sys:message content="${message}" />
	<legend>业务信息</legend>
	<form:form id="target" modelAttribute="tLending.contract"
		action="${ctx}/contract/tLoanContract/" method="post"
		class="form-horizontal">
		<div style="padding-left: 0px;">
			<form:hidden path="id" />
			<form:hidden path="act.procInsId" />
			<sys:message content="${message}" />
			<table class="table-form" style="line-height: 25px;">
				<tr>
					<td class="tit" colspan="4"><font
						style="float: left; font-weight: bold; color: #317eac;">客户基本信息</font></td>
				</tr>
				<tr>
					<td class="tit">客户类型：</td>
					<td>
							<form:select id="customerType" path="customerType"
								class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('customer_type')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">客户：</td>
					<td>
							<sys:treeselect id="customer" isAll="false" name="customerId"
								value="${tLoanContract.customerId}" labelName="customerName"
								labelValue="${tLoanContract.customerName}"
								parentName="customerType" cssClass="required"
								extId="__customerType"
								parentValue="${tLoanContract.customerType}" title="客户"
								url="/company/tCompany/treeData" allowClear="true"
								notAllowSelectParent="true" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">地区:</td>
					<td>
							<sys:treeselect id="area" name="area.id"
								value="${tLoanContract.area.id}" labelName="area.name"
								labelValue="${tLoanContract.area.name}" title="区域"
								url="/sys/area/treeData" allowClear="true"
								notAllowSelectParent="true" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">行业:</td>
					<td>
							<form:select path="industryId" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('industry_id')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit" colspan="4"><font
						style="float: left; font-weight: bold; color: #317eac;">合同业务信息</font></td>
				</tr>
				<tr>
					<td class="tit">申请日期：</td>
					<td>
							<input name="applyDate" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款方式：</td>
					<td>
							<c:if test="${empty tLoanContract.loanType}">
								<form:checkboxes path="loanType"
									items="${fns:getDictList('loan_type')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
							</c:if>
							<c:if test="${not empty tLoanContract.loanType}">
								<form:checkboxes path="loanTypeItem"
									items="${fns:getDictList('loan_type')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
							</c:if>
					</td>
				</tr>
				<tr>
					<td class="tit">贷款金额(元)：</td>
					<td>
							<form:input path="loanAmount" htmlEscape="false"
								class="input-xlarge number required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款利率(%)：</td>
					<td>
								<form:input path="loanRate" htmlEscape="false"
									class="input-xlarge number required" />
								<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">利率类型：</td>
					<td>
							<form:radiobuttons path="loanRateType"
								items="${fns:getDictList('cycleType')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">贷款期数(期)：</td>
					<td>
							<form:input path="loanPeriod" htmlEscape="false" maxlength="50"
								class="input-xlarge digits required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">贷款用途：</td>
					<td>
							<form:select path="purposeId" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('product_purpose_new')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
					</td>

					<td class="tit">放款日期：</td>
					<td>
							<input id="loanDate" name="loanDate" type="text"
								readonly="readonly" maxlength="20"
								class="input-medium Wdate required"
								value="<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">还款方式：</td>
					<td>
							<form:radiobuttons path="payType"
								items="${fns:getDictList('product_paytype')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
					<td class="tit">还款周期：</td>
					<td>
							<form:radiobuttons id="radio_periodType" path="periodType"
								items="${fns:getDictList('period_type')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
					</td>
				</tr>
				<tr>
					<td class="tit">固定还款日：</td>
					<td>
							<form:select path="payDay"
								items="${fns:getDictList('extends_days')}" itemLabel="label"
								itemValue="value" class="input-xlarge required"></form:select>
					</td>

					<td class="tit">还款选项：</td>
					<td>
							<form:checkboxes path="payOptions"
								items="${fns:getDictList('pay_options')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="" />
					</td>
				</tr>
				<tr>
					<td class="tit">是否大小月：</td>
					<td>
							<c:if test="${empty tLoanContract.ifRealityDay}">
								<form:radiobuttons path="ifRealityDay"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifRealityDay}">
								<form:radiobuttons path="ifRealityDay"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>

					</td>
					<td class="tit">是否可提前还款：</td>
					<td>
							<c:if test="${empty tLoanContract.ifAdvance}">
								<form:radiobuttons path="ifAdvance"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifAdvance}">
								<form:radiobuttons path="ifAdvance"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>
					</td>
				</tr>
				<tr>
					<td class="tit">允许利息减免(%)：</td>
					<td>
							<c:if test="${empty tLoanContract.ifInterestRelief}">
								<form:radiobuttons path="ifInterestRelief"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" checked="false" class="" />
							</c:if>
							<c:if test="${not empty tLoanContract.ifInterestRelief}">
								<form:radiobuttons path="ifInterestRelief"
									items="${fns:getDictList('yes_no')}" itemLabel="label"
									itemValue="value" htmlEscape="false" class="" />
							</c:if>
					</td>
					<td class="tit">贷款到期日：</td>
					<td>
							<input id="payPrincipalDate" name="payPrincipalDate" type="text"
								readonly="readonly" maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
					</td>
				</tr>
			</table>
		</div>
	</form:form>
	<%-- ${dfFormTpl.parsehtml } --%>

	<form:form id="inputForm" modelAttribute="tLending"
		action="${ctx}/lending/tLending/loan" method="post"
		class="form-horizontal">

		<form:hidden path="id" />
		<form:hidden path="contract.id" />
		<table class="table-form" style="line-height: 25px;">
			<tr>
				<td class="tit" colspan="4"><font
					style="float: left; font-weight: bold; color: #317eac;">放款信息</font></td>
			</tr>
			<tr>
				<td class="tit">经办人：</td>
				<td>
					${tLending.contract.creatByerName}
				</td>
				<td class="tit">经办部门：</td>
				<td>
					${tLending.contract.deptname}
				</td>
			</tr>
			<tr>
				<td class="tit">放款金额：</td>
				<td>
					
						<input name="amount" id="amount" type="text" value=""
							maxlength="255" class="input-xlarge required moneyPlus" />元，
						剩余放款金额：<span style="background-color: red;">${tLend.syAmount}</span>元
					
				</td>
				<td class="tit">放款时间：</td>
				<td>
					
						<input id="loanTime" name="time" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${tLending.time}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
					
				</td>
			</tr>
			<tr>
				<td class="tit">开户行：</td>
				<td>
					
						<form:input path="contract.gatheringBank" htmlEscape="false"
							maxlength="255" class="input-xlarge" />
					
				</td>
				<td class="tit">开户名：</td>
				<td>
					
						<form:input path="contract.gatheringName" htmlEscape="false"
							maxlength="255" class="input-xlarge" />
				
				</td>
			</tr>
			<tr>
				<td class="tit">开户账号：</td>
				<td>
					
						<form:input path="contract.gatheringNumber" htmlEscape="false"
							maxlength="255" class="input-xlarge " />
					
				</td>
				<td class="tit"></td>
				<td></td>
			</tr>
			<tr>
				<td class="tit">备注</td>
				<td colspan="3">
					
						<form:textarea path="remarks" htmlEscape="false" rows="4"
							maxlength="200" class="input-xxlarge " />
					
				</td>
			</tr>



			<c:if
				test="${tLending.contract.type eq '1' && !empty tLending.contract.authUserId}">
				<div class="control-group">
					<label class="control-label">是否同步W端资金：</label>
					<div class="controls">
						<form:radiobuttons path="ifOnline"
							items="${fns:getDictList('yes_no')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class="required" />
					</div>
				</div>
			</c:if>
		</table>

		<div id="repayid" class="row clearfix">
			<div class="span12">
				<fieldset>
					<legend>
						<input id="buildRepayPlan" type="button" class="btn btn-primary" onclick="" value="更新业务信息及还款计划" />
					</legend>
					<div id="showplansDiv"></div>
					<div id="jqGridPager"></div> 
				</fieldset>
			</div>
		</div>
		<!-- <div id="addAccountDiv"></div> -->
		<div class="form-actions">
			<shiro:hasPermission name="lending:tLending:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="放款" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>