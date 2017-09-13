<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
	/* $(document).ready(function() {
		var HTCkeditor = CKEDITOR.replace("reportCulture.ftlContent");
	}); */
</script>
		<form:hidden path="reportCulture.creditApplyId"/>
		<form:hidden path="reportCulture.status"/>
		<form:hidden path="reportCulture.tplCode"/>
		<%-- <input id="reportCulture.creditApplyId" name="reportCulture.creditApplyId" type="hidden" value="${reportCulture.creditApplyId}">
		<input id="reportCulture.status" name="reportCulture.status" type="hidden" value="${reportCulture.status}">
		<input id="reportCulture.tplCode" name="reportCulture.tplCode" type="hidden" value="${reportCulture.tplCode}"> --%>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="reportCulture.department" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款人：</label>
			<div class="controls">
				<form:input path="reportCulture.name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户经理：</label>
			<div class="controls">
				<form:input path="reportCulture.salesman" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理日期：</label>
			<div class="controls">
				<input name="reportCulture.acceptDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${creditApply.reportCulture.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信金额：</label>
			<div class="controls">
				<form:input path="reportCulture.creditMoney" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信期限：</label>
			<div class="controls">
				<form:input path="reportCulture.creditPeriod" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率：</label>
			<div class="controls">
				<form:input path="reportCulture.rate" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款金额：</label>
			<div class="controls">
				<form:input path="reportCulture.loanMoney" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款期限：</label>
			<div class="controls">
				<form:input path="reportCulture.loanPeriod" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">咨询费：</label>
			<div class="controls">
				<form:input path="reportCulture.consultingFee" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:radiobuttons path="reportCulture.repayType" items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务来源：</label>
			<div class="controls">
				<form:input path="reportCulture.businessSource" htmlEscape="false" maxlength="5" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">富文本内容：</label>
			<div class="controls">
				<%-- <form:input path="reportCulture.ftlContent" htmlEscape="false" class="input-xlarge "/> --%>
				<textarea id="reportCulture.ftlContent" name="reportCulture.ftlContent" rows="4" maxlength="200" class="input-xxlarge required" >${creditApply.reportCulture.ftlContent}</textarea>
			</div>
		</div>
