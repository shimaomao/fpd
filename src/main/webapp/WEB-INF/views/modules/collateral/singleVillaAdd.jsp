<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

		<input id="singleVilla.id" name="singleVilla.id" type="hidden" value="${singleVilla.id}">
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="singleVilla.address" name="singleVilla.address" class="input-xlarge " type="text" value="${singleVilla.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="singleVilla.area" name="singleVilla.area" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.area}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按揭余额(元)：</label>
			<div class="controls">
				<input id="singleVilla.balance" name="singleVilla.balance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.balance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权性质：</label>
			<div class="controls">
				<input id="singleVilla.chanQuality" name="singleVilla.chanQuality" class="input-xlarge " type="text" value="${singleVilla.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="singleVilla.dengji" name="singleVilla.dengji" class="input-xlarge " type="text" value="${singleVilla.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑结构：</label>
			<div class="controls">
				<input id="singleVilla.framework" name="singleVilla.framework" class="input-xlarge " type="text" value="${singleVilla.framework}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户型结构：</label>
			<div class="controls">
				<input id="singleVilla.jiegou" name="singleVilla.jiegou" class="input-xlarge " type="text" value="${singleVilla.jiegou}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建成年份：</label>
			<div class="controls">
				<input name="singleVilla.jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${singleVilla.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共有人：</label>
			<div class="controls">
				<input id="singleVilla.man" name="singleVilla.man" class="input-xlarge " type="text" value="${singleVilla.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="singleVilla.moMoney" name="singleVilla.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价1(元)：</label>
			<div class="controls">
				<input id="singleVilla.money1" name="singleVilla.money1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.money1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价2(元)：</label>
			<div class="controls">
				<input id="singleVilla.money2" name="singleVilla.money2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.money2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价3(元)：</label>
			<div class="controls">
				<input id="singleVilla.money3" name="singleVilla.money3" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.money3}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新房单价(元)：</label>
			<div class="controls">
				<input id="singleVilla.newMoney" name="singleVilla.newMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${singleVilla.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="singleVilla.quanNum" name="singleVilla.quanNum" class="input-xlarge " type="text" value="${singleVilla.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="singleVilla.remark" name="singleVilla.remark" class="input-xlarge " type="text" value="${singleVilla.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑样式：</label>
			<div class="controls">
				<input id="singleVilla.yangshi" name="singleVilla.yangshi" class="input-xlarge " type="text" value="${singleVilla.yangshi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="singleVilla.yearLimit" name="singleVilla.yearLimit" class="input-xlarge  digits" type="text" value="${singleVilla.yearLimit}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联外键抵质押信息：</label>
			<div class="controls">
				<input id="singleVilla.dizhiContractId" name="singleVilla.dizhiContractId" class="input-xlarge  digits" type="text" value="${singleVilla.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="singleVilla.remarks" name="singleVilla.remarks" maxlength="200" class="input-xxlarge " value="${singleVilla.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="singleVilla.remarks" name="singleVilla.remarks" type="hidden" value="${singleVilla.remarks}">
		<input id="singleVilla.dizhiContractId" name="singleVilla.dizhiContractId" type="hidden" value="${singleVilla.dizhiContractId}">