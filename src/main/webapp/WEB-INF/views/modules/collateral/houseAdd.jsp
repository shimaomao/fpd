<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="house.id" name="house.id" type="hidden" value="${house.id}">
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="house.address" name="house.address" class="input-xlarge " type="text" value="${house.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="house.area" name="house.area" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.area}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按揭余额(元)：</label>
			<div class="controls">
				<input id="house.balance" name="house.balance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.balance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权性质：</label>
			<div class="controls">
				<input id="house.chanQuality" name="house.chanQuality" class="input-xlarge " type="text" value="${house.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="house.dengji" name="house.dengji" class="input-xlarge " type="text" value="${house.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑结构：</label>
			<div class="controls">
				<input id="house.framework" name="house.framework" class="input-xlarge " type="text" value="${house.framework}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户型结构：</label>
			<div class="controls">
				<input id="house.jiegou" name="house.jiegou" class="input-xlarge " type="text" value="${house.jiegou}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建成年份：</label>
			<div class="controls">
				<input name="house.jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${house.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共有人：</label>
			<div class="controls">
				<input id="house.man" name="house.man" class="input-xlarge " type="text" value="${house.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="house.moMoney" name="house.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价1(元)：</label>
			<div class="controls">
				<input id="house.money1" name="house.money1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.money1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价2(元)：</label>
			<div class="controls">
				<input id="house.money2" name="house.money2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.money2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价3(元)：</label>
			<div class="controls">
				<input id="house.money3" name="house.money3" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.money3}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新房单价(元)：</label>
			<div class="controls">
				<input id="house.newMoney" name="house.newMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${house.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="house.quanNum" name="house.quanNum" class="input-xlarge " type="text" value="${house.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="house.remark" name="house.remark" class="input-xlarge " type="text" value="${house.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑样式：</label>
			<div class="controls">
				<input id="house.yangshi" name="house.yangshi" class="input-xlarge " type="text" value="${house.yangshi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="house.yearLimit" name="house.yearLimit" class="input-xlarge " type="text" value="${house.yearLimit}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联外键抵质押信息：</label>
			<div class="controls">
				<input id="house.dizhiContractId" name="house.dizhiContractId" class="input-xlarge " type="text" value="${house.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="house.remarks" name="house.remarks" maxlength="200" class="input-xxlarge " value="${house.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="house.remarks" name="house.remarks" type="hidden" value="${house.remarks}">
		<input id="house.dizhiContractId" name="house.dizhiContractId" type="hidden" value="${house.dizhiContractId}">