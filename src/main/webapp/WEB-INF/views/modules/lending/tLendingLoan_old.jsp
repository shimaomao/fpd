<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>放款记录管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api','app/repayPlan'], function(api,rp){
		//还款计划--
		rp.initialize({
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
		},"#showplansDiv");
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(tLending.contract)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
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
			//变更还款计划
			$("#buildRepayPlan").click(function(){
// 				WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,onpicked:function(){
					var loanDate = $("#loanTime").val();
					if(loanDate){
						loanDate = loanDate.substr(0,10);
						//还款计划--
						rp.initialize({
							businessType : "apply",  //apply|extend|earlyrepay...
							amount : "${tLending.contract.loanAmount}",
							loanRate : "${tLending.contract.loanRate}",
							loanRateType : "${tLending.contract.loanRateType}",
							loanPeriod : "${tLending.contract.loanPeriod}",
							loanDate : loanDate,
							payType : "${tLending.contract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
							periodType : "${tLending.contract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
							payDay : "${tLending.contract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
							payOptions : "${tLending.contract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
							ifRealityDay : "${tLending.contract.ifRealityDay}"
						},"#showplansDiv");
					}
// 				   }
// 				});
			});
			
			//添加账户操作
			$("#bindAcountOper").click(function(){
				var btn = $(this);
				var flag = eval(btn.attr("data-flag"));
				var btnText = ["添加账户", "移除账户"];
				var domTpl = $("#addAccountTpl");
				var domDiv = $("#addAccountDiv");
				var domForm = $("#addAccountForm");
				
				if(flag){
					domDiv.html(domTpl);
					domTpl.show();
					btn.val(btnText[1]);
					btn.attr("data-flag", false);
				}else{
					domTpl.hide();
					domForm.html(domTpl);
					btn.val(btnText[0]);
					btn.attr("data-flag", true);
				}
			});
			
		});

	});
	
		
	
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract">待放款列表</a></li>
		<li class="active"><a href="${ctx}/lending/tLending/toLoan?contract.id=${tLending.contract.id}">业务信息</a></li>
		<li><a href="${ctx}/lending/tLending/toLend?contract.id=${tLending.contract.id}">放款信息</a></li>
	</ul>
	<sys:message content="${message}"/>		
	<legend>业务信息</legend>
	${dfFormTpl.parsehtml }
</body>
</html>