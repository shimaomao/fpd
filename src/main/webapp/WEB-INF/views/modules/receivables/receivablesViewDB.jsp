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
	var divWrap = form.find("#divWrap");

	if(data){
		if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
			form.append("<div id=\"divWrap\"></div>");
			divWrap = form.find("#divWrap");
		}
		 if(data.id==''||typeof(data.id)=='undefined'){
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

		if((data.id == '') || (data.id == undefined)){
				divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
		}else{
				divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
		}
		
		if(data.area){
			$("#areaName").val(data.area.name);//如果选了地区的话，修改时回值
		}
	}else{
		console.info("data数据加载异常！");
	}
	
	$("#punishSubmit").click(function(){
		var options ={
			url : '${ctx}/receivables/receivables/punish',
			type : 'post',
			dataType : 'json',//'text',
			data : $("#punishForm").serializeArray(),
			success : function(data, textStatus) {
				if(data.params == "paramNull"){
					alert(data.msg);
				}else{
					alert(data.msg);
					location.reload();
				}
			}	
		};
		$.ajax(options);
		return false;
	})
});


});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/receivables/receivables/">收费管理</a></li>
	</ul>
	<form:form id="punishForm" modelAttribute="realIncome" method="post" class="breadcrumb form-search">
	<input id="loanContractId" name="loanContractId" type="hidden" value="${data.id}">
	<input id="interestPenalties" name="interestPenalties"  class="input-xlarge  number" type="hidden"  value="0" style="width: 170px;">
		<ul class="ul-form">
			<li>
				<label>收费时间：</label> <input name="payRealDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate "
				value=""
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
			</li>
			<li><label>担保费：</label> <input id="guaranteeFee"
				name="guaranteeFee"  class="input-xlarge  number" type="text"  style="width: 170px;">
			</li>
			<li><label>评审费：</label> <input id="reviewFee"
				name="reviewFee"  class="input-xlarge  number" type="text"  style="width: 170px;">
			</li>
			<li><label>服务费：</label> <input id="consultancyAmount"
				name="consultancyAmount"  class="input-xlarge  number" type="text" maxlength="20"  style="width: 170px;">
			</li>
			<li><label>违约金：</label> 
			        <input id="punishAmount" name="punishAmount"  class="input-xlarge  number" type="text" maxlength="10"  style="width: 170px;">
			</li>
			<li class="btns"><input id="punishSubmit" class="btn btn-primary"
				type="submit" value="收取" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<br><br>
	<h3 align="center">担保费、评审费、服务费、违约金收款记录</h3>
	<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<td>序号</td>
								<td>日期</td>
								<td>担保费</td>
								<td>评审费</td>
								<td>违约金</td>
								<td>服务费</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty realIncomeList}">
							<c:forEach items="${realIncomeList}" var="realIncome" varStatus="ri">
								<tr>
									<td>${ri['index']+1 }</td>
									<td>
										<c:if test="${!empty realIncome.payRealDate}">
											<fmt:formatDate value="${realIncome.payRealDate}" pattern="yyyy-MM-dd" />
                        				</c:if>
                        			</td>
                        			<td>${realIncome.guaranteeFee}</td>
                        			<td>${realIncome.reviewFee}</td>
									<td>${realIncome.punishAmount }</td>
									<td>${realIncome.consultancyAmount }</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
					</table>
	<hr>
	${dfFormTpl.parsehtml }
	<br/>
	<div class="form-actions">
		<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</body>
</html>