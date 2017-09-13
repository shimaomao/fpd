<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="zhuZhai.id" name="zhuZhai.id" type="hidden" value="${zhuZhai.id}">
		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="zhuZhai.address" name="zhuZhai.address" class="input-xlarge " type="text" value="${zhuZhai.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="zhuZhai.area" name="zhuZhai.area" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.area}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地性质：</label>
			<div class="controls">
				<input id="zhuZhai.chanQuality" name="zhuZhai.chanQuality" class="input-xlarge " type="text" value="${zhuZhai.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地抵押贷款余额(元)：</label>
			<div class="controls">
				<input id="zhuZhai.daiBalance" name="zhuZhai.daiBalance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.daiBalance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="zhuZhai.dengji" name="zhuZhai.dengji" class="input-xlarge " type="text" value="${zhuZhai.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价1(元)：</label>
			<div class="controls">
				<input id="zhuZhai.landMoney1" name="zhuZhai.landMoney1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.landMoney1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价2(元)：</label>
			<div class="controls">
				<input id="zhuZhai.landMoney2" name="zhuZhai.landMoney2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.landMoney2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价3(元)：</label>
			<div class="controls">
				<input id="zhuZhai.landMoney3" name="zhuZhai.landMoney3" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.landMoney3}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<input id="zhuZhai.man" name="zhuZhai.man" class="input-xlarge " type="text" value="${zhuZhai.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="zhuZhai.moMoney1" name="zhuZhai.moMoney1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.moMoney1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价2(元)：</label>
			<div class="controls">
				<input id="zhuZhai.moMoney2" name="zhuZhai.moMoney2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.moMoney2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="zhuZhai.quanNum" name="zhuZhai.quanNum" class="input-xlarge " type="text" value="${zhuZhai.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="zhuZhai.remark" name="zhuZhai.remark" class="input-xlarge " type="text" value="${zhuZhai.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预规划住宅面积(㎡)：</label>
			<div class="controls">
				<input id="zhuZhai.yuArea" name="zhuZhai.yuArea" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${zhuZhai.yuArea}" pattern="#0.00"/>">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联外键抵质押合同id：</label>
			<div class="controls">
				<input id="zhuZhai.dizhiContractId" name="zhuZhai.dizhiContractId" class="input-xlarge  digits" type="text" value="${zhuZhai.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="zhuZhai.remarks" name="zhuZhai.remarks" maxlength="200" class="input-xxlarge " value="${zhuZhai.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="zhuZhai.remarks" name="zhuZhai.remarks" type="hidden" value="${zhuZhai.remarks}">
		<input id="zhuZhai.dizhiContractId" name="zhuZhai.dizhiContractId" type="hidden" value="${zhuZhai.dizhiContractId}">