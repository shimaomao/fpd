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
				
				//divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 if(data.id==''||typeof(data.id)=='undefined'){
				 		divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }else{
						divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
						$("#employee-id").val(data.id);
				 }
				 
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
					var jsonstr="{'spouse':[{'pouseName':'"+$("#pouseName").val()+"','pouseCardNum':'"+$("#pouseCardNum").val()+"','pouseEducation':'"+$("#pouseEducation").val()+"','pouseMonthIncome':'"+$("#pouseMonthIncome").val()+"','cardType':'"+$("#cardType").val()+"','units':'"+$("#units").val()+"','unitsPhone':'"+$("#unitsPhone").val()+"','unitsAddress':'"+$("#unitsAddress").val()+"','position':'"+$("#position").val()+"','politicalStatus':'"+$("#politicalStatus").val()+"','phone':'"+$("#phone").val()+"','address':'"+$("#address").val()+"'}]}";
					form.attr("target", "mainFrame");
					form.attr("action", ctx+"${action}?pouseName="+jsonstr);
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
		<li><a href="${ctx}/act/task/todo/">待办任务</a></li>
		<li class="active"><a href="${ctx}/act/task/historic/">已办任务</a></li>
	</ul>
	<sys:message content="${message}"/>
	${dfFormTpl.parsehtml }
	
	<br><br>  
	
	
	
	<div id="pouseinfo" class="form-horizontal" style="display: none">
	
	    <form:form id="inputForm1" modelAttribute="spouse" action="${ctx}/employee/tEmployee/save" method="post" class="form-horizontal">
		
        <input type="hidden" name="spouse.id">
		<sys:message content="${message}"/>		
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
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:input path="politicalStatus" id="politicalStatus" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" id="phone" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址：</label>
			<div class="controls">
				<form:input path="address" id="address" htmlEscape="false" maxlength="50" class="input-xlarge "/>
			</div>
		</div>
	</form:form>
	</div>
	
	
	
	
</body>
</html>