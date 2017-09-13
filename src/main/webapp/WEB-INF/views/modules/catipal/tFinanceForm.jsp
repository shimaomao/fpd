<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>融资列表管理</title>
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
		//外部融资金额
		function checkRongzi(){
			var rongziamount = Number($('#rongziamount').val());
		    var rongzimoney = Number($('#rongzimoney').val());
		    var type = $("input[name='rongzitype']:checked").val();
		    if(type==2&&rongzimoney>rongziamount){
		    	alert('选择‘归还’，融资金额不能超过外部融资金额');
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
		<li><a href="${ctx}/catipal/tFinance/">融资列表列表</a></li>
		<li class="active"><a href="${ctx}/catipal/tFinance/form?id=${tFinance.id}">融资列表<shiro:hasPermission name="catipal:tFinance:edit">${not empty tFinance.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="catipal:tFinance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tFinance" action="${ctx}/catipal/tFinance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">外部融资金额：</label>
			<div class="controls">
				<input type="text" id="rongziamount" value="${tCapital.rongziamount }" readonly="readonly" class="input-xlarge "/>
			</div>
		</div>			
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
				<form:input path="rongzimoney" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资时间：</label>
			<div class="controls">
				<input name="rongzitime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tFinance.rongzitime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:radiobuttons path="rongzitype" items="${fns:getDictList('zibe_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资途径：</label>
			<div class="controls">
				<form:input path="rongziway" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="catipal:tFinance:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="if(checkRongzi()){$('#inputForm').submit();}"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>