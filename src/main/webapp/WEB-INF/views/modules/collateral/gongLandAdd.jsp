<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

		<input id="gongLand.id" name="gongLand.id" type="hidden" value="${gongLand.id}">
		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="gongLand.address" name="gongLand.address" class="input-xlarge " type="text" value="${gongLand.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="gongLand.area" name="gongLand.area" class="input-xlarge  number" type="text" value="${gongLand.area}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买年份：</label>
			<div class="controls">
				<input id="gongLand.buyYear" name="gongLand.buyYear" class="input-xlarge  digits" type="text" value="${gongLand.buyYear}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地性质：</label>
			<div class="controls">
				<input id="gongLand.chanQuality" name="gongLand.chanQuality" class="input-xlarge " type="text" value="${gongLand.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地抵押贷款余额(元)：</label>
			<div class="controls">
				<input id="gongLand.daiBalance" name="gongLand.daiBalance" class="input-xlarge  number" type="text" value="${gongLand.daiBalance}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="gongLand.dengji" name="gongLand.dengji" class="input-xlarge " type="text" value="${gongLand.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租赁模型估价(元)：</label>
			<div class="controls">
				<input id="gongLand.guMoney" name="gongLand.guMoney" class="input-xlarge  number" type="text" value="${gongLand.guMoney}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价格(元)：</label>
			<div class="controls">
				<input id="gongLand.jiaoMoney" name="gongLand.jiaoMoney" class="input-xlarge  number" type="text" value="${gongLand.jiaoMoney}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<input id="gongLand.man" name="gongLand.man" class="input-xlarge " type="text" value="${gongLand.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="gongLand.quanNum" name="gongLand.quanNum" class="input-xlarge " type="text" value="${gongLand.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="gongLand.remark" name="gongLand.remark" class="input-xlarge " type="text" value="${gongLand.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地价格1(元)：</label>
			<div class="controls">
				<input id="gongLand.tongMoney1" name="gongLand.tongMoney1" class="input-xlarge  number" type="text" value="${gongLand.tongMoney1}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地价格2(元)：</label>
			<div class="controls">
				<input id="gongLand.tongMoney2" name="gongLand.tongMoney2" class="input-xlarge  number" type="text" value="${gongLand.tongMoney2}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售模型估价(元)：</label>
			<div class="controls">
				<input id="gongLand.xiaoMoney" name="gongLand.xiaoMoney" class="input-xlarge  number" type="text" value="${gongLand.xiaoMoney}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="gongLand.yuYear" name="gongLand.yuYear" class="input-xlarge  digits" type="text" value="${gongLand.yuYear}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出租价格1(元)：</label>
			<div class="controls">
				<input id="gongLand.zuMoney1" name="gongLand.zuMoney1" class="input-xlarge  number" type="text" value="${gongLand.zuMoney1}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出租价格2(元)：</label>
			<div class="controls">
				<input id="gongLand.zuMoney2" name="gongLand.zuMoney2" class="input-xlarge  number" type="text" value="${gongLand.zuMoney2}">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">外键关联抵质押信息id：</label>
			<div class="controls">
				<input id="gongLand.dizhiContractId" name="gongLand.dizhiContractId" class="input-xlarge " type="text" value="${gongLand.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="gongLand.remarks" name="gongLand.remarks" maxlength="200" class="input-xxlarge " value="${gongLand.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="gongLand.remarks" name="gongLand.remarks" type="hidden" value="${gongLand.remarks}">
		<input id="gongLand.dizhiContractId" name="gongLand.dizhiContractId" type="hidden" value="${gongLand.dizhiContractId}">