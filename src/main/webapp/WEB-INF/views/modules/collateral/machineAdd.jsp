<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	
		<input id="machine.id" name="machine.id" type="hidden" value="${machine.id}">
		
		<div class="control-group">
			<label class="control-label">变现能力：</label>
			<div class="controls">
				<input id="machine.bianXian" name="machine.bianXian" class="input-xlarge " type="text" value="${machine.bianXian}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制权人：</label>
			<div class="controls">
				<input id="machine.control" name="machine.control" class="input-xlarge " type="text" value="${machine.control}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="machine.dengji" name="machine.dengji" class="input-xlarge " type="text" value="${machine.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通用程度：</label>
			<div class="controls">
				<input id="machine.general" name="machine.general" class="input-xlarge " type="text" value="${machine.general}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂日期：</label>
			<div class="controls">
				<input name="machine.madeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${machine.madeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">控制权方式：</label>
			<div class="controls">
				<input id="machine.method" name="machine.method" class="input-xlarge " type="text" value="${machine.method}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元)：</label>
			<div class="controls">
				<input id="machine.moMoney" name="machine.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${machine.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备型号：</label>
			<div class="controls">
				<input id="machine.model" name="machine.model" class="input-xlarge " type="text" value="${machine.model}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设备名称：</label>
			<div class="controls">
				<input id="machine.name" name="machine.name" class="input-xlarge " type="text" value="${machine.name}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新货价格(元)：</label>
			<div class="controls">
				<input id="machine.newMoney" name="machine.newMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${machine.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性能状况：</label>
			<div class="controls">
				<input id="machine.performance" name="machine.performance" class="input-xlarge" type="text" value="${machine.performance}">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二手价值1(元)：</label>
			<div class="controls">
				<input id="machine.second1" name="machine.second1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${machine.second1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">二手价值2(元)：</label>
			<div class="controls">
				<input id="machine.second2" name="machine.second2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${machine.second2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用年限(年)：</label>
			<div class="controls">
				<input id="machine.useDate" name="machine.useDate" class="input-xlarge  digits" type="text" value="${machine.useDate}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">抵质押表外键id：</label>
			<div class="controls">
				<input id="machine.dizhiContractId" name="machine.dizhiContractId" class="input-xlarge  digits" type="text" value="${machine.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="machine.remarks" name="machine.remarks" maxlength="200" class="input-xxlarge " value="${machine.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="machine.remarks" name="machine.remarks" type="hidden" value="${machine.remarks}">
		<input id="machine.dizhiContractId" name="machine.dizhiContractId" type="hidden" value="${machine.dizhiContractId}">