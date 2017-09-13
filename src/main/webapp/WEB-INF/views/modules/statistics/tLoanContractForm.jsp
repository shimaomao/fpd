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
				//console.info("测试json值"+data.id);
				var form = $("#target");
				/* var btn = $("#submitViewForm"); */
				api.form.init(form, eval(data));
				form.attr("method", "post");
				//添加 验证
				$("input[name='loanType']").each(function(){
					$(this).attr("required","required");
				}); 
				
				var divWrap = form.find("#divWrap");

				if(true){
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
					 checkPeriodType(data.payType);//修改判断还款方式与还款周期的关系
				}else{
					console.info("data数据加载异常！");
				}
				
				
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
	
	<sys:message content="${message}" />


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
					<input id="buildRepayPlan" type="button" class="btn btn-primary" onclick="" value="预览还款计划" /> 123132
					 </c:if>		
				</div>
			</div>
		</div>
	</div>
</body>
</html>