<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

		<input id="terraceVilla.id" name="terraceVilla.id" type="hidden" value="${terraceVilla.id}">
		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<input id="terraceVilla.address" name="terraceVilla.address" class="input-xlarge " type="text" value="${terraceVilla.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="terraceVilla.area" name="terraceVilla.area" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.area}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按揭余额(元)：</label>
			<div class="controls">
				<input id="terraceVilla.balance" name="terraceVilla.balance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.balance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权性质：</label>
			<div class="controls">
				<input id="terraceVilla.chanQuality" name="terraceVilla.chanQuality" class="input-xlarge " type="text" value="${terraceVilla.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="terraceVilla.dengji" name="terraceVilla.dengji" class="input-xlarge " type="text" value="${terraceVilla.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑结构：</label>
			<div class="controls">
				<input id="terraceVilla.framework" name="terraceVilla.framework" class="input-xlarge " type="text" value="${terraceVilla.framework}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户型结构：</label>
			<div class="controls">
				<input id="terraceVilla.jiegou" name="terraceVilla.jiegou" class="input-xlarge " type="text" value="${terraceVilla.jiegou}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建成年份：</label>
			<div class="controls">
				<input name="terraceVilla.jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${terraceVilla.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共有人：</label>
			<div class="controls">
				<input id="terraceVilla.man" name="terraceVilla.man" class="input-xlarge " type="text" value="${terraceVilla.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="terraceVilla.moMoney" name="terraceVilla.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价1(元)：</label>
			<div class="controls">
				<input id="terraceVilla.money1" name="terraceVilla.money1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.money1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价2(元)：</label>
			<div class="controls">
				<input id="terraceVilla.money2" name="terraceVilla.money2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.money2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价3(元)：</label>
			<div class="controls">
				<input id="terraceVilla.money3" name="terraceVilla.money3" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.money3}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新房单价(元)：</label>
			<div class="controls">
				<input id="terraceVilla.newMoney" name="terraceVilla.newMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${terraceVilla.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="terraceVilla.quanNum" name="terraceVilla.quanNum" class="input-xlarge " type="text" value="${terraceVilla.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="terraceVilla.remark" name="terraceVilla.remark" class="input-xlarge " type="text" value="${terraceVilla.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑样式：</label>
			<div class="controls">
				<input id="terraceVilla.yangshi" name="terraceVilla.yangshi" class="input-xlarge " type="text" value="${terraceVilla.yangshi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="terraceVilla.yearLimit" name="terraceVilla.yearLimit" class="input-xlarge  digits" type="text" value="${terraceVilla.yearLimit}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联外键抵质押信息：</label>
			<div class="controls">
				<input id="terraceVilla.dizhiContractId" name="terraceVilla.dizhiContractId" class="input-xlarge  digits" type="text" value="${terraceVilla.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="terraceVilla.remarks" name="terraceVilla.remarks" maxlength="200" class="input-xxlarge " value="${terraceVilla.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="terraceVilla.remarks" name="terraceVilla.remarks" type="hidden" value="${terraceVilla.remarks}">
		<input id="terraceVilla.dizhiContractId" name="terraceVilla.dizhiContractId" type="hidden" value="${terraceVilla.dizhiContractId}">