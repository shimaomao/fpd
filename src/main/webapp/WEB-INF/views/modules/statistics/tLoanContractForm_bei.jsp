<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>还款预览管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
<script type="text/javascript" src="${ctxStatic}/util.js"></script>
<script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
<script type="text/javascript">
/* 参数Array是一个由模块ID组成的数组。当模块ID所以代表的模块加载完成且可用时，回调函数Function才开始执行，并且只被执行一次。
各个模块按照依赖数组中的位置顺序以参数的形式传入到Function里。 */
/* 第一个参数			webapp/static/form/builder/assets/js/helper/api.js 
 * 第二个参数			webapp/static/form/builder/assets/js/app/repayPlan.js
 */
		require(['helper/api','app/repayPlan'], function(api,rp){
									
						$(function(){
							$("#buildRepayPlan").click(function(){
								$("#repayid").show();
							
									var loanAmount = $("input[name='loanAmount']").val();
									var loanRate = $("input[name='loanRate']").val();
									var loanRateType = $("input[name='loanRateType']:checked").val();//add by srf #3121
									var loanPeriod = $("input[name='loanPeriod']").val();
									var loanDate = $("input[name='loanDate']").val();
									var payType = $("input[name='payType']:checked").val();
									var periodType = $("input[name='periodType']:checked").val();
									var payDay = $("select[name='payDay']").val();
									var payOptions = getCheckValue('payOptions').join();
									var payPrincipalDate = $("#payPrincipalDate").val();//#3371
									var ifRealityDay=$("input[name='ifRealityDay']:checked").val();
									if(payPrincipalDate) payPrincipalDate = payPrincipalDate.substr(0,10);  //2017-04-18
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
								
							});
						});
						
					 	
						$("#loanPeriod").blur(function(){//贷款期限
							initPayPrincipalDate();
						});
						$("#loanDate").blur(function(){//放款时间
							initPayPrincipalDate();
						});
						 
						var radio_periodType = document.getElementById("periodType");//还款周期
						radio_periodType.addEventListener("click", function() {
							initPayPrincipalDate();
						}); 
						
						var radio_payType = document.getElementById("payType");//还款方式
						radio_payType.addEventListener("click", function() {
							//alert("选择还款方式  后计算出  还款最后日期 ")
							initPayPrincipalDate();
							//alert("调用方法结束")
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
			//alert("为空")
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
		<li><b>还款预览列表</b></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tLoanContract"  method="post" class="form-horizontal">	
			

		<div class="control-group">
			<label class="control-label">贷款金额：</label>
			<div class="controls">
				<input name="loanAmount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">贷款期限：</label>
			<div class="controls">
				<input id="loanPeriod" name="loanPeriod" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款利率：</label>
			<div class="controls">
				<input name="loanRate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率类型：</label>
			<div class="controls">
				 <input type="radio" name="loanRateType" value="1" /> 年
 				 <input type="radio" name="loanRateType" value="2" /> 月
				<input  type="radio" name="loanRateType" value="3" /> 日
			</div>
		</div>
	
		
	
<!--    <div class="control-group">
			<label class="control-label">付息日：</label>
			<div class="controls">
				<input name="payDay" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> -->
		
		<div class="control-group">
			<label class="control-label">放款日期：</label>
			<div class="controls">
				<input id="loanDate" name="loanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate  value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls" id="payType">
				<input  type="radio" name="payType" value="1"/>等额本息
				<input  type="radio" name="payType" value="2"/>等额本金
				<input  type="radio" name="payType" value="3"/>等本等息
				<input  type="radio" name="payType" value="5"/>到期一次性还本付息
				<input  type="radio" name="payType" value="4"/>按期付息到期还本
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款选项：</label>
			<div class="controls">
				<input name="payOptions" type="checkbox"/>前置付息
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款周期：</label>
			<div class="controls" id="periodType">
				<input  type="radio" name="periodType" value="1"/>年
				<input  type="radio" name="periodType" value="2"/>月
				<input  type="radio" name="periodType" value="3"/>日
				<input  type="radio" name="periodType" value="4"/>季				
			</div>
		</div>

		
		
		
		<div class="control-group">
			<label class="control-label">贷款到期日：</label>
			<div class="controls">
				<input id="payPrincipalDate" name="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>

	
		<!-- <div class="control-group">
			<label class="control-label">是否大小月：</label>
			<div class="controls">
				<input name="ifRealityDay" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div> -->
	
		<div class="form-actions">
			<input id="buildRepayPlan" type="button" class="btn btn-primary" onclick="" value="预览还款计划" /> 
		</div>
		
	
	</form:form>
	
		<!-- 还款计划-- -->
		<div style="padding-left: 0px;">
	         <div class="row clearfix" style="display: none" id="repayid">
			 <div class="span12">
				<fieldset>
					<legend>还款计划</legend>
					<div id="showplansDiv"></div>
					<!-- 拷贝pay_plan.js文件  到 -->
					<!-- <div id="grid_showplansDiv"></div> -->
				</fieldset>
			</div>
		    </div>
			
		</div>
</body>
</html>