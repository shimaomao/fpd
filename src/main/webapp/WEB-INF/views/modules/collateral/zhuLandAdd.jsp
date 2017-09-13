<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<input id="zhuLand.id" name="zhuLand.id" type="hidden" value="${zhuLand.id}">
		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="zhuLand.address" name="zhuLand.address" class="input-xlarge " type="text" value="${zhuLand.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="zhuLand.area" name="zhuLand.area" class="input-xlarge  number" type="text" value="${zhuLand.area}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地性质：</label>
			<div class="controls">
				<input id="zhuLand.chanQuality" name="zhuLand.chanQuality" class="input-xlarge " type="text" value="${zhuLand.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地抵质押贷款余额(元)：</label>
			<div class="controls">
				<input id="zhuLand.daiBalance" name="zhuLand.daiBalance" class="input-xlarge  number" type="text" value="${zhuLand.daiBalance}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="zhuLand.dengji" name="zhuLand.dengji" class="input-xlarge " type="text" value="${zhuLand.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价1(元)：</label>
			<div class="controls">
				<input id="zhuLand.landMoney1" name="zhuLand.landMoney1" class="input-xlarge  number" type="text" value="${zhuLand.landMoney1}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价2(元)：</label>
			<div class="controls">
				<input id="zhuLand.landMoney2" name="zhuLand.landMoney2" class="input-xlarge  number" type="text" value="${zhuLand.landMoney2}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<input id="zhuLand.man" name="zhuLand.man" class="input-xlarge " type="text" value="${zhuLand.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="zhuLand.quanNum" name="zhuLand.quanNum" class="input-xlarge " type="text" value="${zhuLand.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="zhuLand.remark" name="zhuLand.remark" class="input-xlarge " type="text" value="${zhuLand.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预规划住宅面积(㎡)：</label>
			<div class="controls">
				<input id="zhuLand.yuArea" name="zhuLand.yuArea" class="input-xlarge  number" type="text" value="${zhuLand.yuArea}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋每月出租价格(元)：</label>
			<div class="controls">
				<input id="zhuLand.housingMonMoney" name="zhuLand.housingMonMoney" class="input-xlarge  number" type="text" value="${zhuLand.housingMonMoney}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋价格(元)：</label>
			<div class="controls">
				<input id="zhuLand.housingMoney" name="zhuLand.housingMoney" class="input-xlarge  number" type="text" value="${zhuLand.housingMoney}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租赁模型估值(元)：</label>
			<div class="controls">
				<input id="zhuLand.leaseValuation" name="zhuLand.leaseValuation" class="input-xlarge  number" type="text" value="${zhuLand.leaseValuation}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估值(元)：</label>
			<div class="controls">
				<input id="zhuLand.modelValuation" name="zhuLand.modelValuation" class="input-xlarge  number" type="text" value="${zhuLand.modelValuation}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售租赁估值(元)：</label>
			<div class="controls">
				<input id="zhuLand.salesValuation" name="zhuLand.salesValuation" class="input-xlarge  number" type="text" value="${zhuLand.salesValuation}">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联外键抵质押信息id：</label>
			<div class="controls">
				<input id="zhuLand.dizhiContractId" name="zhuLand.dizhiContractId" class="input-xlarge " type="text" value="${zhuLand.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="zhuLand.remarks" name="zhuLand.remarks" maxlength="200" class="input-xxlarge " value="${zhuLand.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="zhuLand.remarks" name="zhuLand.remarks" type="hidden" value="${zhuLand.remarks}">
		<input id="zhuLand.dizhiContractId" name="zhuLand.dizhiContractId" type="hidden" value="${zhuLand.dizhiContractId}">