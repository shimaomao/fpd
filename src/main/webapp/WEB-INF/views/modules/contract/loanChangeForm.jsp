<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <c:set var="edit" value="${tLoanContract.act.status == 'finish' ? false : true }" /> --%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<c:set var="edit" value="${tLoanContract.act.status == 'finish' ? true : false }" />
<html>
<head>
	<title>贷前变更</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api','app/repayPlan'], function(api,rp){
		//alert('44');
		//还款计划--
		/* rp.initialize({
			businessType : "apply",  //apply|extend|earlyrepay...
			amount : "${data.loanAmount}",
			loanRate : "${data.loanRate}",
			loanRateType : "${data.loanRateType}",
			loanPeriod : "${data.loanPeriod}",
			loanDate : '<fmt:formatDate value="${data.loanDate}" pattern="yyyy-MM-dd"/>',
			payType : "${data.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
			periodType : "${data.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
			payDay : "${data.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
			payOptions : "${data.payOptions}"//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
		},"#showplansDiv"); */
		
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(data)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
			//页签
			$('.nav-tabs').find('li').click(function(){
				$(this).addClass('active').siblings().removeClass('active');
				$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
			});
			
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
			
			$("#buildRepayPlan").click(function(){		
				var loanAmount = $('#amount').val();
				var flag = true;
				if (loanAmount == null  || loanAmount == '' || isNaN(loanAmount)) {
					showTip("输入正确的变更金额");
					flag = false;
					return ;
				}
				var loanAmount2 = form.find("input[name='loanAmount']").val();
				//alert(loanAmount2);
				if (parseFloat(loanAmount) >= parseFloat(loanAmount2)) {
					showTip("输入变更金额不能超过原金额");
					flag = false;
					return ;
				}
			    if (flag) {
			    	initRepayPlan(form);
			    }
				
			});
			
			$("#btnSubmitForm").click(function(){		
				var loanAmount = $('#amount').val();
				var flag = true;
				if (loanAmount == null  || loanAmount == '' || isNaN(loanAmount)) {
					showTip("输入正确的变更金额");
					flag = false;
					return ;
				}
				var loanAmount2 = form.find("input[name='loanAmount']").val();
				//alert(loanAmount2);
				if (parseFloat(loanAmount) >= parseFloat(loanAmount2)) {
					showTip("输入变更金额不能超过原金额");
					flag = false;
					return ;
				}
				var contentTable = $('#contentTable');
				//alert(contentTable.length);
				if (contentTable.length == 0 ) {
					showTip("请生成贷前变更还款计划");
					flag = false;
					return ;
				}
			    if (flag) {
			    	$('#flag').val('yes');
			    	//alert('');
			    	//form.submit();
			    	$("#inputForm").submit(); 
			    }
				
			});
			
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=380&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
			$("#${nid}filelist").load(url);
		});
		
		
		
		
		function initRepayPlan(form){
			//var loanAmount = form.find("input[name='loanAmount']").val();
			var loanAmount = $('#amount').val();
			//alert(loanAmount);
			var loanRate = form.find("input[name='loanRate']").val();
			var loanRateType =  form.find("input[name='loanRateType']:checked").val();
			var loanPeriod = form.find("input[name='loanPeriod']").val();
			var loanDate = form.find("input[name='loanDate']").val();
			var payType = form.find("input[name='payType']:checked").val();
			var periodType = form.find("input[name='periodType']:checked").val();
			var payDay = form.find("select[name='payDay']").val();
			var payOptions = getCheckValue('payOptions').join();
			var ifRealityDay= form.find("input[name='ifRealityDay']:checked").val();//大小月
			if(loanDate) loanDate = loanDate.substr(0,10);
			
			//alert('77');
			rp.initialize({
				businessType : "apply",  //apply|extend|earlyrepay...
				amount : loanAmount,
				loanRate : loanRate,
				loanRateType : loanRateType,
				loanPeriod : loanPeriod,
				loanDate : loanDate,
				payType : payType,//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
				periodType : periodType,//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
				payDay : payDay,//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
				payOptions : payOptions,//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
				ifRealityDay : ifRealityDay//大小月
			},"#showplansDiv");
		}

		
		
		
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	   <!--  <li class="active"><a  href="javasricpt:void(0);">业务信息</a></li>
		<li><a  href="javasricpt:void(0);">贷前变更申请</a></li> -->
		<li class="active"><a  href="#" onclick="javascript:return false;">业务信息</a></li>
		<li><a  href="#" onclick="javascript:return false;">附件</a></li>
		<li><a  href="#" onclick="javascript:return false;">贷前变更申请</a></li>
	</ul>
	<sys:message content="${message}"/>
	<div class="nav-tab-con" >
		${dfFormTpl.parsehtml }
		<br>
	
	<form:form id="inputForm" modelAttribute="tLoanContractModel" action="${ctx}/contract/tLoanContract/loanChangeFormSave" method="post" class="form-horizontal">
	
	贷前变更的贷款金额：<input id="amount" name="amount" class="input-large required number" >
		<!-- 还款计划-- -->
		<fieldset>
			<legend>贷前变更还款计划</legend> 
			<c:if test="${fns:getUser().company.primaryPerson==daikuan}"></c:if>
					<input id="buildRepayPlan" type="button" class="btn btn-primary" onclick="" value="初始化还款计划" /> 
			
			<div id="showplansDiv">
			</div>
		</fieldset>
	</div>
	<div id="files" class="nav-tab-con" style="display:none;">
		<div class="control-group">
			<!-- <label class="control-label">相关附件：</label> -->
			<div class="control-group" style="height: 550px;">
				<div id="${nid}filelist" style="height: 600px;"></div>
			</div>
		</div>
	</div>
	<div class="nav-tab-con" style="display:none;">
		<br/>
		
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden path="act.businessTable"/>
			<form:hidden id="flag" path="act.flag"/>
				
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
				</div>
			</div>
			<div class="form-actions">
			        <c:if test="${(!edit && empty tLoanContract.act.procInsId) || (tLoanContract.status eq '5')}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="$('#pass').val('1');$('#flag').val('yes');" value="申请"/>&nbsp;
					</c:if>
			        
					<%-- <c:if test="${!edit && !empty tLoanContract.act.procInsId}">
					  <c:if test="${tLoanContract.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#pass').val('0');$('#flag').val('no');"/>&nbsp;
					  </c:if>
					</c:if> --%>
	
				<%-- <c:if test="${edit}">
				<input id="btnSubmitForm" class="btn btn-primary" type="button" value="同 意" />&nbsp;
				<!-- 
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				 -->
				</c:if>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> --%>
			</div>
			<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
		</form:form>
	</div>
</body>
</html>