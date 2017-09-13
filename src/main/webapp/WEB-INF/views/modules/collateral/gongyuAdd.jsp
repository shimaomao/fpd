<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="gongyu.id" name="gongyu.id" type="hidden" value="${gongyu.id}">
		<div class="control-group">
			<label class="control-label">房地产地址：</label>
			<div class="controls">
				<input id="gongyu.address" name="gongyu.address" class="input-xlarge " type="text" value="${gongyu.address}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<input id="gongyu.area" name="gongyu.area" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.area}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按揭余额(元)：</label>
			<div class="controls">
				<input id="gongyu.balance" name="gongyu.balance" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.balance}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权性质：</label>
			<div class="controls">
				<input id="gongyu.chanQuality" name="gongyu.chanQuality" class="input-xlarge " type="text" value="${gongyu.chanQuality}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="gongyu.dengji" name="gongyu.dengji" class="input-xlarge " type="text" value="${gongyu.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑结构：</label>
			<div class="controls">
				<input id="gongyu.framework" name="gongyu.framework" class="input-xlarge " type="text" value="${gongyu.framework}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户型结构：</label>
			<div class="controls">
				<input id="gongyu.jiegou" name="gongyu.jiegou" class="input-xlarge " type="text" value="${gongyu.jiegou}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建成年份：</label>
			<div class="controls">
				<input name="gongyu.jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${gongyu.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共有人：</label>
			<div class="controls">
				<input id="gongyu.man" name="gongyu.man" class="input-xlarge " type="text" value="${gongyu.man}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="gongyu.moMoney" name="gongyu.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位价格1(元)：</label>
			<div class="controls">
				<input id="gongyu.money1" name="gongyu.money1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.money1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位价格2(元)：</label>
			<div class="controls">
				<input id="gongyu.money2" name="gongyu.money2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.money2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位价格3(元)：</label>
			<div class="controls">
				<input id="gongyu.money3" name="gongyu.money3" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.money3}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新房单价(元)：</label>
			<div class="controls">
				<input id="gongyu.newMoney" name="gongyu.newMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${gongyu.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="gongyu.quanNum" name="gongyu.quanNum" class="input-xlarge " type="text" value="${gongyu.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="gongyu.remark" name="gongyu.remark" class="input-xlarge " type="text" value="${gongyu.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑样式：</label>
			<div class="controls">
				<input id="gongyu.yangshi" name="gongyu.yangshi" class="input-xlarge " type="text" value="${gongyu.yangshi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="gongyu.yearLimit" name="gongyu.yearLimit" class="input-xlarge " type="text" value="${gongyu.yearLimit}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">外键关联抵质押信息表id：</label>
			<div class="controls">
				<input id="gongyu.dizhiContractId" name="gongyu.dizhiContractId" class="input-xlarge " type="text" value="${gongyu.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="gongyu.remarks" name="gongyu.remarks" maxlength="200" class="input-xxlarge " value="${gongyu.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="gongyu.remarks" name="gongyu.remarks" type="hidden" value="${gongyu.remarks}">
		<input id="gongyu.dizhiContractId" name="gongyu.dizhiContractId" type="hidden" value="${gongyu.dizhiContractId}">