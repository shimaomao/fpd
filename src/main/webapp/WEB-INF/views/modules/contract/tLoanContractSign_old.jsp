<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <c:set var="nid" value="loanContractForm" /> --%>
<html>                  
<head>
	<title>签订合同</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api','app/repayPlan'], function(api,rp){
		//还款计划--
		rp.initialize({
			businessType : "apply",  //apply|extend|earlyrepay...
			amount : "${data.loanAmount}",
			loanRate : "${data.loanRate}",
			loanRateType : "${data.loanRateType}",
			loanPeriod : "${data.loanPeriod}",
			loanDate : '<fmt:formatDate value="${data.loanDate}" pattern="yyyy-MM-dd"/>',
			payType : "${data.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : "${data.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : "${data.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : "${data.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			ifRealityDay:"${data.ifRealityDay}"
		},"#showplansDiv");
		
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(data)};
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
			
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			var url = "${ctx}/files/tContractFiles/showfilelist/${data.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
		    $("#${nid}filelist").load(url);
		});
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li>
		<li class="active"><a href="${ctx}/contract/tLoanContract/toSign?id=${tLoanContract.id}">业务办理<shiro:hasPermission name="contract:tLoanContract:edit">签订合同</shiro:hasPermission><shiro:lacksPermission name="contract:tLoanContract:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<sys:message content="${message}"/>		
	
	${dfFormTpl.parsehtml }
	<br>
	<!-- 还款计划-- -->
	<fieldset>
		<legend>还款计划</legend>
		<div id="showplansDiv">
		</div>
	</fieldset>
	<br/>
	<label class="control-label">相关附件：</label>
<%-- 	<div id="${nid}filelist" class="controls" style="height: 500px;"></div> --%>
	  <div class="control-group" style="height: 500px;">
			<div id="${nid}filelist" style="height: 550px;"></div>
		</div>
	
	
	<form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/sign" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${data.id}" />
		<div class="form-actions">
			<shiro:hasPermission name="contract:tLoanContract:edit">
			     <input id="btnSubmit" class="btn btn-primary" type="submit" value="签订合同"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>