<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="yongLand.id" name="yongLand.id" type="hidden" value="${yongLand.id}">
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="yongLand.address" name="yongLand.address" class="input-xlarge " type="text" value="${yongLand.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="yongLand.area" name="yongLand.area" class="input-xlarge " type="text" value="${yongLand.area}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买年份：</label>
			<div class="controls">
				<input id="yongLand.buyYear" name="yongLand.buyYear" class="input-xlarge  digits" type="text" value="${yongLand.buyYear}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地性质：</label>
			<div class="controls">
				<input id="yongLand.chanQuality" name="yongLand.chanQuality" class="input-xlarge " type="text" value="${yongLand.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地抵押贷款余额(元)：</label>
			<div class="controls">
				<input id="yongLand.daiBalance" name="yongLand.daiBalance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.daiBalance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="yongLand.dengji" name="yongLand.dengji" class="input-xlarge " type="text" value="${yongLand.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租赁模型估值(元)：</label>
			<div class="controls">
				<input id="yongLand.guMoney" name="yongLand.guMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.guMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价格(元)：</label>
			<div class="controls">
				<input id="yongLand.jiaoMoney" name="yongLand.jiaoMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.jiaoMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地单价(元)：</label>
			<div class="controls">
				<input id="yongLand.landMoney" name="yongLand.landMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.landMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<input id="yongLand.man" name="yongLand.man" class="input-xlarge " type="text" value="${yongLand.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="yongLand.moMoney" name="yongLand.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="yongLand.quanNum" name="yongLand.quanNum" class="input-xlarge " type="text" value="${yongLand.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="yongLand.remark" name="yongLand.remark" class="input-xlarge " type="text" value="${yongLand.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋价格1(元)：</label>
			<div class="controls">
				<input id="yongLand.tongMoney1" name="yongLand.tongMoney1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.tongMoney1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋价格2(元)：</label>
			<div class="controls">
				<input id="yongLand.tongMoney2" name="yongLand.tongMoney2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.tongMoney2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售模型估值(元)：</label>
			<div class="controls">
				<input id="yongLand.xiaoMoney" name="yongLand.xiaoMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.xiaoMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">预规划住宅面积(㎡)：</label>
			<div class="controls">
				<input id="yongLand.yuArea" name="yongLand.yuArea" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.yuArea}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="yongLand.yuYear" name="yongLand.yuYear" class="input-xlarge  digits" type="text" value="${yongLand.yuYear}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋每月出租价格1(元)：</label>
			<div class="controls">
				<input id="yongLand.zuMoney1" name="yongLand.zuMoney1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.zuMoney1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等商业房屋每月出租价格2(元)：</label>
			<div class="controls">
				<input id="yongLand.zuMoney2" name="yongLand.zuMoney2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${yongLand.zuMoney2}" pattern="#0.00"/>">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联抵质押信息表主键id：</label>
			<div class="controls">
				<input id="yongLand.dizhiContractId" name="yongLand.dizhiContractId" class="input-xlarge  digits" type="text" value="${yongLand.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="yongLand.remarks" name="yongLand.remarks" maxlength="200" class="input-xxlarge " value="${yongLand.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="yongLand.remarks" name="yongLand.remarks" type="hidden" value="${yongLand.remarks}">
		<input id="yongLand.dizhiContractId" name="yongLand.dizhiContractId" type="hidden" value="${yongLand.dizhiContractId}">