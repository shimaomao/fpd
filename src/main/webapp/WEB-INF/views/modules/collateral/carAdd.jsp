<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<!-- <ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0)">质押车辆信息</a></li>
		</ul><br/> -->
		<input id="car.id" name="car.id" type="hidden" value="${car.id}">
		<div class="control-group">
			<label class="control-label">事故次数：</label>
			<div class="controls">
				<input id="car.accident" name="car.accident" class="input-xlarge  digits" type="text" value="${car.accident}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车况：</label>
			<div class="controls">
				<input id="car.carKuang" name="car.carKuang" class="input-xlarge " type="text" value="${car.carKuang}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新车价格(元) ：</label>
			<div class="controls">
				<input id="car.carMoney" name="car.carMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${car.carMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车型编号：</label>
			<div class="controls">
				<input id="car.carNum" name="car.carNum" class="input-xlarge " type="text" value="${car.carNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车型：</label>
			<div class="controls">
				<input id="car.carType" name="car.carType" class="input-xlarge " type="text" value="${car.carType}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车系：</label>
			<div class="controls">
				<input id="car.carXi" name="car.carXi" class="input-xlarge " type="text" value="${car.carXi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车辆颜色：</label>
			<div class="controls">
				<input id="car.color" name="car.color" class="input-xlarge " type="text" value="${car.color}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">配置：</label>
			<div class="controls">
				<input id="car.configuration" name="car.configuration" class="input-xlarge " type="text" value="${car.configuration}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">制造商：</label>
			<div class="controls">
				<input id="car.creator" name="car.creator" class="input-xlarge " type="text" value="${car.creator}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="car.dengji" name="car.dengji" class="input-xlarge " type="text" value="${car.dengji}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排量：</label>
			<div class="controls">
				<input id="car.displacement" name="car.displacement" class="input-xlarge " type="text" value="${car.displacement}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发动机型号：</label>
			<div class="controls">
				<input id="car.engine" name="car.engine" class="input-xlarge " type="text" value="${car.engine}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车架号：</label>
			<div class="controls">
				<input id="car.frame" name="car.frame" class="input-xlarge " type="text" value="${car.frame}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂日期：</label>
			<div class="controls">
				<input name="car.madeDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${car.madeDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价格1(元) ：</label>
			<div class="controls">
				<input id="car.market1" name="car.market1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${car.market1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价格2(元) ：</label>
			<div class="controls">
				<input id="car.market2" name="car.market2" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${car.market2}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">里程数：</label>
			<div class="controls">
				<input id="car.mileage" name="car.mileage" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${car.mileage}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元) ：</label>
			<div class="controls">
				<input id="car.moMoney" name="car.moMoney" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${car.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车辆产地：</label>
			<div class="controls">
				<input id="car.producing" name="car.producing" class="input-xlarge " type="text" value="${car.producing}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座位数：</label>
			<div class="controls">
				<input id="car.seating" name="car.seating" class="input-xlarge  digits" type="text" value="${car.seating}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用年限：</label>
			<div class="controls">
				<input id="car.useDate" name="car.useDate" class="input-xlarge  digits" type="text" value="${car.useDate}" maxlength="11">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">外键关联抵质押信息表：</label>
			<div class="controls">
				<input id="car.dizhiContractId" name="car.dizhiContractId" class="input-xlarge  digits" type="text" value="${car.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<input id="car.dizhiContractId" name="car.dizhiContractId" type="hidden" value="${car.dizhiContractId}">
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="car.remarks" name="car.remarks" maxlength="200" class="input-xxlarge " value="${car.remarks}" rows="4"></textarea>
			</div>
		</div> --%>
		<input id="car.remarks" name="car.remarks" type="hidden" value="${car.remarks}">