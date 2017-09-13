<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
		<input id="quanli.id" name="quanli.id" type="hidden" value="${quanli.id}">
		<div class="control-group">
			<label class="control-label">已经经营权利时间：</label>
			<div class="controls">
				<input id="quanli.already" name="quanli.already" class="input-xlarge  digits" type="text" value="${quanli.already}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">享有权重比例：</label>
			<div class="controls">
				<input id="quanli.biRate" name="quanli.biRate" class="input-xlarge " type="text" value="${quanli.biRate}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="quanli.dengji" name="quanli.dengji" class="input-xlarge " type="text" value="${quanli.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">流通性：</label>
			<div class="controls">
				<input id="quanli.liuTong" name="quanli.liuTong" class="input-xlarge " type="text" value="${quanli.liuTong}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="quanli.moMoney" name="quanli.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${quanli.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权利名称：</label>
			<div class="controls">
				<input id="quanli.name" name="quanli.name" class="input-xlarge " type="text" value="${quanli.name}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权利质量：</label>
			<div class="controls">
				<input id="quanli.quality" name="quanli.quality" class="input-xlarge " type="text" value="${quanli.quality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营状况：</label>
			<div class="controls">
				<input id="quanli.runStatus" name="quanli.runStatus" class="input-xlarge " type="text" value="${quanli.runStatus}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">享有权利剩余期限：</label>
			<div class="controls">
				<input id="quanli.shengDate" name="quanli.shengDate" class="input-xlarge  digits" type="text" value="${quanli.shengDate}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收益：</label>
			<div class="controls">
				<input id="quanli.shouYi" name="quanli.shouYi" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${quanli.shouYi}" pattern="#0.00"/>">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">外键关联抵质押信息id：</label>
			<div class="controls">
				<input id="quanli.dizhiContractId" name="quanli.dizhiContractId" class="input-xlarge  digits" type="text" value="${quanli.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="quanli.remarks" name="quanli.remarks" maxlength="200" class="input-xxlarge " value="${quanli.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="quanli.remarks" name="quanli.remarks" type="hidden" value="${quanli.remarks}">
		<input id="quanli.dizhiContractId" name="quanli.dizhiContractId" type="hidden" value="${quanli.dizhiContractId}">