<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人客户管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				var data = ${fns:toJson(data)};
				var form = $("#target");
				var btn = $("#submitViewForm");
				api.form.init(form, eval(data));
				form.attr("method", "post");
				var divWrap = form.find("#divWrap");
				if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
					form.append("<div id=\"divWrap\"></div>");
					divWrap = form.find("#divWrap");
				}
				 divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
// 				 if(data.id==''||typeof(data.id)=='undefined'){
// 				 		divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
// 				 }else{
// 						divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
// 						$("#employee-id").val(data.id);
// 				 }
				 
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
				$(btn).click(function(){
					var jsonstr="{'spouse':[{'pouseName':'"+$("#pouseName").val()+"','pouseCardNum':'"+$("#pouseCardNum").val()+"','pouseEducation':'"+$("#pouseEducation").val()+"','pouseMonthIncome':'"+$("#pouseMonthIncome").val()+"','cardType':'"+$("#cardType").val()+"','units':'"+$("#units").val()+"','unitsPhone':'"+$("#unitsPhone").val()+"','unitsAddress':'"+$("#unitsAddress").val()+"','position':'"+$("#position").val()+"','politicalStatus':'"+$("#politicalStatus").val()+"','phone':'"+$("#phone").val()+"','address':'"+$("#address").val()+"','birthday':'"+$("#birthday").val()+"','liveTime':'"+$("#liveTime").val()+"','industry':'"+$("#industry").val()+"','unitNature':'"+$("#unitNature").val()+"','unitSize':'"+$("#unitSize").val()+"','unitTime':'"+$("#unitTime").val()+"','political':'"+$("#political").val()+"'}]}";
					divWrap.html('<input type="hidden" name="pouseName" value="'+jsonstr+'"><input type="hidden" name="id" value="${data.id }">');//3421
					form.attr("target", "mainFrame");
					form.attr("action", ctx+"${action}");
					form.submit();
				});
				
				 $("#radio_marriedInfo").click(function(){
					 var marrinfo  = document.getElementsByName("marriedInfo");
					   for(var i=0;i<marrinfo.length;i++){
					      if(marrinfo[i].checked){
					    	   var str = marrinfo[i].value;
					    	   if(str==2){
					    		   $("#pouseinfo").show();
					    	   }else{
					    		   $("#pouseinfo").hide();
					    	   }
					     }
					   }
				});
				 if(data.marriedInfo==2){
					 $("#pouseinfo").show();
				 }
			});
		});
	</script>
</head>
<body>
   	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/employee/tEmployee/list">个人客户列表</a></li>
		<li class="active"><a href="${ctx}/employee/tEmployee/form?id=${data.id}">个人客户添加</a></li>
          <!-- 修改的时候才显示可以添加其他信息 -->
		<c:if test="${fn:contains(data.id, 'new_')==false}">
			<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?employeeId=${data.id}">客户关联</a></li>
			<li><a href="${ctx}/customerintent/tCustomerIntent/list?employeeId=${data.id}">意向调查</a></li>
			<li><a href="${ctx}/files/tContractFiles/list?taskId=${data.id}&type=customer_archives">档案资料</a></li>
			<li><a href="${ctx}/credit/customerCredit/list?customerId=${data.id}&type=1">授信记录</a></li>
			<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${data.id}&type=1">业务记录</a></li>
			<li><a href="${ctx}/contract/tLoanContract/clist?customerId=${data.id}&type=2&status=9">不良记录</a></li>
		</c:if>
	</ul>
	<sys:message content="${message}"/>
	    ${dfFormTpl.parsehtml}
	<br><br>
	<div id="pouseinfo" class="form-horizontal" style="display: none">
		<sys:message content="${message}"/>		
	    <form:form id="inputForm1" modelAttribute="spouse" action="${ctx}/employee/tEmployee/save" method="post" class="form-horizontal">
        <input type="hidden" name="spouse.id">
		<div class="control-group">
			<label class="control-label">配偶姓名：</label>
			<div class="controls">
				<form:input path="pouseName" htmlEscape="false" id="pouseName" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge " id="cardType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="pouseCardNum" htmlEscape="false" maxlength="50" id="pouseCardNum" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学历：</label>
			<div class="controls">
				<form:select path="pouseEducation" class="input-xlarge " id="pouseEducation">
					<form:option value="0" label=""/>
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
			   	<input name="birthday" id="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${spouse.birthday}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" id="phone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">居住地址：</label>
			<div class="controls">
				<form:input path="address" id="address" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现住址入住时间：</label>
			<div class="controls">
			   	<input name="liveTime" id="liveTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${spouse.liveTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍地址：</label>
			<div class="controls">
				<form:input path="politicalStatus" id="politicalStatus" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位：</label>
			<div class="controls">
				<form:input path="units" htmlEscape="false" maxlength="50" id="units" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月收入：</label>
			<div class="controls">
				<form:input path="pouseMonthIncome" htmlEscape="false" maxlength="50" id="pouseMonthIncome" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位电话：</label>
			<div class="controls">
				<form:input path="unitsPhone" htmlEscape="false" maxlength="50" id="unitsPhone" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位地址：</label>
			<div class="controls">
				<form:input path="unitsAddress" id="unitsAddress" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职位：</label>
			<div class="controls">
				<form:input path="position" id="position" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">职称：</label>
			<div class="controls">
				<form:input path="political" id="political" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入职时间：</label>
			<div class="controls">
			   	<input name="unitTime" id="unitTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${spouse.unitTime}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">单位规模：</label>
			<div class="controls">
				<form:input path="unitSize" id="unitSize" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">单位性质：</label>
			<div class="controls">
				<form:input path="unitNature" id="unitNature" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业类别：</label>
			<div class="controls">
				<form:input path="industry" id="industry" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		
	</form:form>
	</div>
	
	<div class="container">
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
					<input id="submitViewForm" type="button" class="btn btn-primary" value="保存" />
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>