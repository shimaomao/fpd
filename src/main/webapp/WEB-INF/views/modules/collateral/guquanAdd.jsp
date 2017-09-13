<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<input id="guquan.id" name="guquan.id" type="hidden" value="${guquan.id}">
		
		<div class="control-group">
			<label class="control-label">注册资本(元)：</label>
			<div class="controls">
				<input id="guquan.capital" name="guquan.capital" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${guquan.capital}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制权人：</label>
			<div class="controls">
				<input id="guquan.control" name="guquan.control" class="input-xlarge " type="text" value="${guquan.control}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记信息：</label>
			<div class="controls">
				<input id="guquan.dengji" name="guquan.dengji" class="input-xlarge " type="text" value="${guquan.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行业类别：</label>
			<div class="controls">
				<input id="guquan.hangType" name="guquan.hangType" class="input-xlarge " type="text" value="${guquan.hangType}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照号：</label>
			<div class="controls">
				<input id="guquan.license" name="guquan.license" class="input-xlarge " type="text" value="${guquan.license}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="guquan.moMoney" name="guquan.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${guquan.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标公司名称：</label>
			<div class="controls">
				<input id="guquan.name" name="guquan.name" class="input-xlarge " type="text" value="${guquan.name}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">净资产(元)：</label>
			<div class="controls">
				<input id="guquan.netAssets" name="guquan.netAssets" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${guquan.netAssets}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营期限：</label>
			<div class="controls">
				<input id="guquan.runDate" name="guquan.runDate" class="input-xlarge  digits" type="text" value="${guquan.runDate}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营状况：</label>
			<div class="controls">
				<input id="guquan.runStatus" name="guquan.runStatus" class="input-xlarge " type="text" value="${guquan.runStatus}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册时间：</label>
			<div class="controls">
				<input name="guquan.zhuDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${guquan.zhuDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">抵质押信息外键id：</label>
			<div class="controls">
				<input id="guquan.dizhiContractId" name="guquan.dizhiContractId" class="input-xlarge  digits" type="text" value="${guquan.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="guquan.remarks" name="guquan.remarks" maxlength="200" class="input-xxlarge " value="${guquan.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="guquan.remarks" name="guquan.remarks" type="hidden" value="${guquan.remarks}">
		<input id="guquan.dizhiContractId" name="guquan.dizhiContractId" type="hidden" value="${guquan.dizhiContractId}">