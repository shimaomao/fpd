<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
<title>业务办理</title>
<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
<script type="text/javascript">
		require(['helper/api','app/repayPlan'], function(api,rp){
			
			
			$(function(){
				var data = ${fns:toJson(data)};
				var form = $("#target");
				var btn = $("#submitViewForm");
				api.form.init(form, eval(data));
				form.attr("method", "post");
				//添加 验证
				$("input[name='loanType']").each(function(){
					$(this).attr("required","required");
				}); 
				
				var divWrap = form.find("#divWrap");

				if(data){
					if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
						form.append("<div id=\"divWrap\"></div>");
						divWrap = form.find("#divWrap");
					}
					 if(data.id==''||typeof(data.id)=='undefined'||data.id == undefined){
							divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
					 }else{
							divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
					 } 
					form.attr("target", "mainFrame");
					form.attr("action", ctx+"${action}");
					form.validate({
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

					/* if((data.id == '') || (data.id == undefined)){
							divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
					}else{
							divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
					} */
					

					$(btn).click(function(){
						var amount = $("input[name='loanAmount']").val();
						var customerId = $("input[name='customerId']").val();
						var applyDate = $("input[name='applyDate']").val();//#3545
						var payPrincipalDate =  $("input[name='payPrincipalDate']").val();
						
						if(!customerId){
							alertx("请选择客户！");
						}else if(!amount){
							alertx("贷款金额不能为空！");
						}else{
							//console.info('data.type='+data.type);
							//alert('data.type='+data.type);
							if(data.type=="1"){//W端的不判断授信
								form.submit();
							}else if('${fns:getUser().company.primaryPerson}'=="1"){//1代表担保，直接过，不判断授信
								form.submit();
							}else{								
								$.post("${ctx}/credit/customerCredit/getCustomerCredit",{"customerId":customerId,"applyDate":applyDate},
										function(data){
											if(data.status==0){
												//alertx(data.message);
												//$("#loanAmount").val("");
												if(confirm("该客户没有进行授信，是否进行自动授信？")){
													 $.post("${ctx}/credit/customerCredit/AutoSaveCustomerCredit",{"credit":amount,"customerId":customerId,"applyDate":applyDate,"overDate":payPrincipalDate},
															function(data){
														         alertx(data.message);
															}
														);
												 }
											}else if(data.balance < amount ){
												alertx("可用授信额度【"+data.balance+"】小于贷款金额【"+amount+"】");
												$("#loanAmount").val(data.balance);
											} else {
												
												 $.post("${ctx}/contract/tLoanContract/getProductAmountLimit",{"amount":amount},
														function(data){
														    if(data.status==0){
																alertx(data.message);
																$("#loanAmount").val(data.amountlimit);
															}else if(data.status==1){
																$("input[name='periodType']").each(function(){
																	$(this).removeAttr("disabled");
																});
																form.submit();
															}
														}
												);
											}
										}
									);
							}
						}
					});
					
					form.find("input[name='customerId']").change(function(){
						var id = $(this).val();
						$.post("${ctx}/company/tCompany/getCustomer",{id:id},function(data){
							if(data.gatheringBank) form.find("input[name='gatheringBank']").val(data.gatheringBank);
							if(data.gatheringName) form.find("input[name='gatheringName']").val(data.gatheringName);
							if(data.gatheringNumber) form.find("input[name='gatheringNumber']").val(data.gatheringNumber);
						});
						
					});
					
					
					 checkPeriodType(data.payType);//修改判断还款方式与还款周期的关系
				}else{
					console.info("data数据加载异常！");
				}
				//变更还款计划
				/* form.find("input[name='loanAmount'],input[name='loanRate'],input[name='loanPeriod'],input[name='loanDate'],"+
						"input[name='payType'],input[name='periodType'],select[name='payDay'],input[name='payOptions']").change(function(){
					initRepayPlan(form, rp);
				}); */
				
				$("#buildRepayPlan").click(function(){
					$("#repayid").show();
					initRepayPlan(form);
				});
			});
			
			function initRepayPlan(form){
				var loanAmount = form.find("input[name='loanAmount']").val();
				var loanRate = form.find("input[name='loanRate']").val();
				var loanRateType = form.find("input[name='loanRateType']:checked").val();//add by srf #3121
				var loanPeriod = form.find("input[name='loanPeriod']").val();
				var loanDate = form.find("input[name='loanDate']").val();
				var payType = form.find("input[name='payType']:checked").val();
				var periodType = form.find("input[name='periodType']:checked").val();
				var payDay = form.find("select[name='payDay']").val();
				var payOptions = getCheckValue('payOptions').join();
				var payPrincipalDate = $("#payPrincipalDate").val();//#3371
				var ifRealityDay=form.find("input[name='ifRealityDay']:checked").val();
				if(payPrincipalDate) payPrincipalDate = payPrincipalDate.substr(0,10);
				if(loanDate) loanDate = loanDate.substr(0,10);
				//console.info('loanRateType='+loanRateType);
				//alert('77');
				rp.initialize({
					businessType : "apply",  //apply|extend|earlyrepay...
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
					ifRealityDay : ifRealityDay //大小月
				},"#showplansDiv");
			}

			
			$("#loanPeriod").blur(function(){//贷款期限
				initPayPrincipalDate();
			});
			$("#loanDate").blur(function(){//放款时间
				initPayPrincipalDate();
			});
			
			var radio_periodType = document.getElementById("radio_periodType");//还款周期
			radio_periodType.addEventListener("click", function() {
				initPayPrincipalDate();
			}); 
			
			var radio_payType = document.getElementById("radio_payType");//还款方式
			radio_payType.addEventListener("click", function() {
				initPayPrincipalDate();
				 var array = getCheckValue("payType");
				 checkPeriodType(array[0]);
			});
		});
		
		
		function checkPeriodType(peytype){//当还款方式为等额本金  等额本息 等本等息时，还款周期只能是月
			if(peytype == "1" || peytype == "2" || peytype == "3"){
			    //还款周期只能是月
				$("input[name='periodType']").each(function(){
					if($(this).val() == "2"){
				    	$(this).attr("checked",true);
					}
					$(this).attr("disabled", "disabled");
				});      //设置不可用
		 }else{
			$("input[name='periodType']").each(function(){
				$(this).removeAttr("disabled");
			});      //设置可用
		 }
		}
		
		
		//计算还本金日期
		function initPayPrincipalDate(){
			var payType = getCheckValue("payType")[0];//还款方式
			var periodType = getCheckValue("periodType")[0];// 还款周期
			var loanDate = $("#loanDate").val();// 放款日期
			var loanPeriod = $("#loanPeriod").val();// 贷款期限
			if(!payType || !loanDate || !loanPeriod) return;
			loanPeriod = Number(loanPeriod);
			var payPrincipalDate = getPayPrincipalDate(payType,periodType,loanDate,loanPeriod);
			$("#payPrincipalDate").val(payPrincipalDate);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract/list">业务办理列表</a></li>
		<shiro:hasPermission name="contract:tLoanContract:edit">
			<li class="active"><a href="${ctx}/contract/tLoanContract/form?id=${data.id}">业务办理${(empty data.id || fn:contains(data.id, 'tmp_')) ? '添加' : '修改'}</a></li>
		</shiro:hasPermission>
	</ul>
	<sys:message content="${message}" />
	<input id="type" name="type" value="${data.type}" type="hidden">
	<input id="wtypeId" name="wtypeId" value="${data.wtypeId}" type="hidden">
	${dfFormTpl.parsehtml }
	<br>

	<!-- 还款计划-- -->
	<div style="padding-left: 0px;">
         <div class="row clearfix" style="display: none" id="repayid">
		 <div class="span12">
			<fieldset>
				<legend>还款计划</legend>
				<div id="showplansDiv"></div>
			</fieldset>
		</div>
	    </div>
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
				     <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
					<input id="buildRepayPlan" type="button" class="btn btn-primary" onclick="" value="初始化还款计划" /> old
					 </c:if>		
					<input id="submitViewForm" type="button" class="btn btn-primary" value="保存" /> 
					<input	id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
				</div>
			</div>
		</div>
	</div>
</body>
</html>