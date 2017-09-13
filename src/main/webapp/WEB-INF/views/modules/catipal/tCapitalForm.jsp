<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资本信息管理</title>
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
		//贷款上限限制
		function countLimitamount(){
			var count = 0;
			var zhucea = Number($('#zhuceamount').val());
			var rongzia = Number($('#rongziamount').val());
			var profita = Number($('#profitamount').val());
			count = zhucea + rongzia + profita;
			$('#limitamount').val(count);
		}
		//放贷注入资本限制
		function checkLoanamount(){
			var loanamount = Number($('#loanamount').val());
		    var limitamount = Number($('#limitamount').val());
		    if(loanamount>limitamount){
		    	alert('放贷注入资本不能超过贷款上限金额');
		    	//$('#loanamount').val(limitamount);
		    	//$('#btnSubmit').attr("disabled","disabled");
		    	return false;
		    }
		    else{
		    	//$('#btnSubmit').removeAttr("disabled");
		    	return true;
		    }
		}

	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/catipal/tCapital/">资本信息列表</a></li>
		<li class="active"><a href="${ctx}/catipal/tCapital/form?id=${tCapital.id}">资本信息<shiro:hasPermission name="catipal:tCapital:edit">${not empty tCapital.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="catipal:tCapital:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tCapital" action="${ctx}/catipal/tCapital/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">描述：</label>
			<div class="controls">
				<form:input path="des" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资本：</label>
			<div class="controls">
				<form:input path="zhuceamount"  htmlEscape="false" class="input-xlarge money" required="required" onchange="countLimitamount()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外部融资：</label>
			<div class="controls">
				<c:if test="${empty tCapital.rongziamount}">
				<form:input path="rongziamount"  htmlEscape="false" class="input-xlarge money" required="required" onchange="countLimitamount()"/>
			    </c:if>
			    <c:if test="${not empty tCapital.rongziamount}">
				<form:input path="rongziamount"  htmlEscape="false" class="input-xlarge money" required="required" onchange="countLimitamount()"/>
			    </c:if>
			</div>
		</div>
		<!-- Bug #3142 未分配利润、外部融资首次添加可编辑，以后不可以编辑 -->
		<div class="control-group">
			<label class="control-label">未分配利润：</label>
			<div class="controls">
			<c:if test="${empty tCapital.profitamount}">
				<form:input path="profitamount"  htmlEscape="false" class="input-xlarge money" required="required" onchange="countLimitamount()"/>
			</c:if>
			<c:if test="${not empty tCapital.profitamount}">
				<form:input path="profitamount"  htmlEscape="false" class="input-xlarge money" required="required" onchange="countLimitamount()"/>
			</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款上限金额：</label>
			<div class="controls">
				<form:input path="limitamount" htmlEscape="false" class="input-xlarge " readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">放贷注入资本：</label>
			<div class="controls">
				<form:input path="loanamount" htmlEscape="false" class="input-xlarge money" required="required" onchange="checkLoanamount()"/>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">注入时间：</label>
			<div class="controls">
					<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tCapital.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" required="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="catipal:tCapital:edit"><input id="btnSubmit" class="btn btn-primary" type="button" value="保 存" onclick="if(checkLoanamount()){$('#inputForm').submit();}"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>