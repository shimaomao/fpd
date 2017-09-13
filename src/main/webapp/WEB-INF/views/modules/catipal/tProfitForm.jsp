<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润列表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		});
		//检查未分配利润金额
		function checkProfit(){
			var profitamount = Number($('#profitamount').val());
		    var profitmoney = Number($('#profitmoney').val());
		    var type = $("input[name='profittype']:checked").val();
		    if(type==1&&profitmoney>profitamount){
		    	alert('选择‘已分配’，利润金额不能超过未分配利润金额');
		    	return false;
		    }
		    else{
		    	return true;
		    }
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/catipal/tProfit/">利润列表列表</a></li>
		<li class="active"><a href="${ctx}/catipal/tProfit/form?id=${tProfit.id}">利润列表<shiro:hasPermission name="catipal:tProfit:edit">${not empty tProfit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="catipal:tProfit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tProfit" action="${ctx}/catipal/tProfit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">未分配利润金额：</label>
			<div class="controls">
				<input type="text" id="profitamount" value="${tCapital.profitamount }" readonly="readonly" class="input-xlarge "/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">利润金额：</label>
			<div class="controls">
				<form:input path="profitmoney" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利润时间：</label>
			<div class="controls">
				<input name="profittime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tProfit.profittime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="profittype" items="${fns:getDictList('profit_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利润途径：</label>
			<div class="controls">
				<form:input path="profitway" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="catipal:tProfit:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="if(checkProfit()){$('#inputForm').submit();}"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>