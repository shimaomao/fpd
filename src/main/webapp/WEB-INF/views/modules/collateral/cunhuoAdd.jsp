<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="cunhuo.id" name="cunhuo.id" type="hidden" value="${cunhuo.id}">
		<div class="control-group">
			<label class="control-label">存货地点：</label>
			<div class="controls">
				<input id="cunhuo.address" name="cunhuo.address" class="input-xlarge " type="text" value="${cunhuo.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">变形能力：</label>
			<div class="controls">
				<input id="cunhuo.bianXian" name="cunhuo.bianXian" class="input-xlarge " type="text" value="${cunhuo.bianXian}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">品牌：</label>
			<div class="controls">
				<input id="cunhuo.brand" name="cunhuo.brand" class="input-xlarge " type="text" value="${cunhuo.brand}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制权人：</label>
			<div class="controls">
				<input id="cunhuo.control" name="cunhuo.control" class="input-xlarge " type="text" value="${cunhuo.control}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通用程度：</label>
			<div class="controls">
				<input id="cunhuo.general" name="cunhuo.general" class="input-xlarge " type="text" value="${cunhuo.general}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价1(元) ：</label>
			<div class="controls">
				<input id="cunhuo.market1" name="cunhuo.market1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${cunhuo.market1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价2(元) ：</label>
			<div class="controls">
				<input id="cunhuo.market2" name="cunhuo.market2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${cunhuo.market2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制器方式：</label>
			<div class="controls">
				<input id="cunhuo.method" name="cunhuo.method" class="input-xlarge " type="text" value="${cunhuo.method}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元) ：</label>
			<div class="controls">
				<input id="cunhuo.moMoney" name="cunhuo.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${cunhuo.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">存货名称：</label>
			<div class="controls">
				<input id="cunhuo.name" name="cunhuo.name" class="input-xlarge " type="text" value="${cunhuo.name}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<input id="cunhuo.num" name="cunhuo.num" class="input-xlarge  digits" type="text" value="${cunhuo.num}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">型号：</label>
			<div class="controls">
				<input id="cunhuo.type" name="cunhuo.type" class="input-xlarge " type="text" value="${cunhuo.type}" maxlength="255">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">外键抵质押表：</label>
			<div class="controls">
				<input id="cunhuo.dizhiContractId" name="cunhuo.dizhiContractId" class="input-xlarge  digits" type="text" value="${cunhuo.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="cunhuo.remarks" name="cunhuo.remarks" maxlength="200" class="input-xxlarge " value="${cunhuo.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="cunhuo.remarks" name="cunhuo.remarks" type="hidden" value="${cunhuo.remarks}">
		<input id="cunhuo.dizhiContractId" name="cunhuo.dizhiContractId" type="hidden" value="${cunhuo.dizhiContractId}">