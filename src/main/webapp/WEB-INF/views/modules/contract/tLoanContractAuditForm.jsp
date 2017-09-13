<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<c:set var="edit" value="${tLoanContract.act.status == 'finish' ? true : false }" />
<html>
<head>
	<title>业务审批</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api','app/repayPlan'], function(api,rp){
		//还款计划--
		rp.initialize({
			businessId : "${data.id}",
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
			ifRealityDay : "${data.ifRealityDay}"//大小月
		},"#showplansDiv");
		
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(data)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);

			showTab("${param.tab}");
			
			//页签
			/* $('.nav-tabs').find('li').click(function(){
				$(this).addClass('active').siblings().removeClass('active');
				$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
			}); */
			
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
			var url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
			$("#${nid}filelist").load(url);
			
		});
	});
	
	function showTab(tab){
		if((tab == undefined) || (tab == null) || (tab == '')){
			tab = "bd";
		}
		$(tab).show();
		$("."+tab).addClass('active').siblings().removeClass('active');
		$("#"+tab).show().siblings('.nav-tab-con').hide();
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/contract/tLoanContract/list">业务办理列表</a></li> --%>
		<li class="bd"><a href="${ctx}/contract/tLoanContract/auditForm?id=${data.id}&tab=bd&act.procDefId=${tLoanContract.act.procDefId}&act.status=${tLoanContract.act.status}&act.taskId=${tLoanContract.act.taskId}">业务信息</a></li>
		<li class="creditinfo"><a href="${ctx}/contract/tLoanContract/auditForm?id=${data.id}&tab=creditinfo&act.procDefId=${tLoanContract.act.procDefId}&act.status=${tLoanContract.act.status}&act.taskId=${tLoanContract.act.taskId}">授信评审意见</a></li>
		<li class="ginfo"><a href="${ctx}/contract/tLoanContract/auditForm?id=${data.id}&tab=ginfo&act.procDefId=${tLoanContract.act.procDefId}&act.status=${tLoanContract.act.status}&act.taskId=${tLoanContract.act.taskId}">担保及附件信息</a></li>
		<li class="lc"><a href="${ctx}/contract/tLoanContract/auditForm?id=${data.id}&tab=lc&act.procDefId=${tLoanContract.act.procDefId}&act.status=${tLoanContract.act.status}&act.taskId=${tLoanContract.act.taskId}">申请/审批</a></li>
	</ul><br/> 
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
		<br><br>
		<!-- 还款计划-- -->
		<fieldset>
			<legend>还款计划</legend>
			<div id="showplansDiv">
			</div>
		</fieldset>
	</div>
		<div id="creditinfo" class="nav-tab-con" style="display:none;">
		<div class="control-group">
			<!-- <label class="control-label">授信评审意见内容：</label> -->
			<div class="controls">
				 <textarea id="creditApplyComments" name="creditApplyComments" maxlength="20000" style="width:800px;overflow:auto;" class="input-xxlarge " disabled="disabled" rows="30">${data.creditApplyComments}</textarea> 
			</div>
		</div>
	</div>
	<div id="ginfo" class="nav-tab-con" style="display:none;"><%-- ginfo修改需要修改代码 --%>
		<%-- 担保信息 --%>
		<c:if test="${empty mortgageList && empty pledgeList && empty guaranteeList}">
		<h3 align="center">无相关担保信息！</h3>
		<!-- <table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
			<thead>
				<tr>
					<th>无相关担保信息！</th>
				</tr>
			</thead>
		</table> -->
		</c:if>
		<!-- 抵押内容 -->
		<c:if test="${not empty mortgageList}">
		<h3 align="center">抵&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
			<thead>
				<!-- <tr><th colspan="8" style="font-size:22px;">抵&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</th></tr> -->
				<tr>
					<th>关联业务编号</th>
					<th>名称</th>
					<th>存放地点</th>				
					<th>数量及单位</th>
					<th>评估价值(元)</th>
					<th>抵押物的状态</th>
					<th>抵押物类型</th>
					<th>抵押性质</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${mortgageList}" var="mortgageContract">
				<tr>
					<td>
						${mortgageContract.contractNumber}
					</td>				
					<td>
						<shiro:hasPermission name="credit:customerCredit:view"><a href="${ctx}/mortgage/mortgageContract/view?id=${mortgageContract.id}">${mortgageContract.name}</a></shiro:hasPermission>
					</td>
					<td>
						${mortgageContract.address}
					</td>
					<td>
						${mortgageContract.unit}
					</td>
					<td>
						${mortgageContract.worth}
					</td>
					<td>
						${fns:getDictLabel(mortgageContract.struts, 'ypjc_struts_status', '')}
					</td>
					<td>
						${fns:getDictLabel(mortgageContract.pledgeType, 'collateral_mortgage', '')}
					</td>
					<td>
						${mortgageContract.number}
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<br/>
		<br/>
		</c:if>
		<!-- 质押内容 -->
		<c:if test="${not empty pledgeList}">
			<h3 align="center">质&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
				<thead>
					<!-- <tr><th colspan="7" style="font-size:22px;">质&nbsp;&nbsp;押&nbsp;&nbsp;信&nbsp;&nbsp;息</th></tr> -->
					<tr> 
					    <th>关联业务编号</th>
						<th>质押名称</th>
						<th>质押物类型</th>
						<th>数量</th>
						<th>价值</th>
						<th>状态</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${pledgeList}" var="pledgeContract">
					<tr>
						<td>
							${pledgeContract.contractNumber}
						</td>
						<td><a href="${ctx}/pledge/pledgeContract/detail?id=${pledgeContract.id}">
							${pledgeContract.name}
						</a></td>
						<td>
							${fns:getDictLabel(pledgeContract.pledgeType, 'collateral_pledge', '')}
						</td>
						<td>
							${pledgeContract.unit}
						</td>
						<td>
							${pledgeContract.worth}
						</td>
						<td>
							${fns:getDictLabel(pledgeContract.struts, 'ypjc_struts_status', '')}
						</td>
						<td>
							<fmt:formatDate value="${pledgeContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<br/>
			<br/>
		</c:if>
		<!-- 保证内容 -->
		<c:if test="${not empty guaranteeList}">
			<h3 align="center">保&nbsp;&nbsp;证&nbsp;&nbsp;信&nbsp;&nbsp;息</h3>
			<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
				<thead>
					<!-- <tr><th colspan="9" style="font-size:22px;">保&nbsp;&nbsp;证&nbsp;&nbsp;内&nbsp;&nbsp;容</th></tr> -->
					<tr>
						<th>关联业务编号</th>
						<th>担保编号</th>
						<th>保证金额</th>
						<th>证件号码</th>
						<th>担保日期</th>
						<th>联系电话</th>
						<th>资产价值</th>
						<th>担保人姓名</th>
						<th>更新时间</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${guaranteeList}" var="tGuaranteeContract">
					<tr>
						<td><a href="${ctx}/guarantee/tGuaranteeContract/detail?id=${tGuaranteeContract.id}">
						     ${tGuaranteeContract.contractNumber}
						</a></td>
						<td>
							${tGuaranteeContract.guaranteeNumber}
						</td>
						<td>
							${tGuaranteeContract.amount}
						</td>
						<td>
							${tGuaranteeContract.cardNum}
						</td>
						<td>
							<fmt:formatDate value="${tGuaranteeContract.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${tGuaranteeContract.telephone}
						</td>
						<td>
							${tGuaranteeContract.assetsWorth}
						</td>
						<td>
							${tGuaranteeContract.guarantorName}
						</td>
						<td>
							<fmt:formatDate value="${tGuaranteeContract.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
		<br/><br/>
		<h3>相关附件</h3>
		<div class="control-group">
			<div class="control-group" style="height: 250px;">
				<div id="${nid}filelist" style="height: 300px;"></div>
			</div>
		</div>
	</div>
	<div id="lc" class="nav-tab-con" style="display:none;">
		<br/>
		<form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/complete" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>		
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
				</div>
			</div>
			<div class="form-actions">
			        <c:if test="${!edit && empty tLoanContract.act.procInsId}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="$('#pass').val('1');$('#flag').val('yes');" value="申请"/>&nbsp;
					</c:if>
			        
					<c:if test="${!edit && !empty tLoanContract.act.procInsId}">
					  <c:if test="${tLoanContract.act.status == 'todo'}">
					    <c:if test="${creditApply.act.isEnd ne 'yes'}">
						<label class="control-label" style="margin-left: -149px;">是否不走放款审批流程：</label>
						<div class="controls" style="margin-left: -101px;">
							 <form:radiobuttons path="isdirectLoan" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/></br></br></br>
						</div>
						</c:if>
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('no');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('stop');"/>&nbsp;
					  </c:if>
					</c:if>
				<%-- <c:if test="${edit}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意11" onclick="$('#flag').val('yes')"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#flag').val('no')"/>&nbsp;
				</c:if>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> --%>
			</div>
			<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
		</form:form>
	</div>
</body>
</html>