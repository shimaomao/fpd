<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
		<form:hidden path="gongLand.id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="gongLand.address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">面积(㎡)：</label>
			<div class="controls">
				<form:input path="gongLand.area" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买年份：</label>
			<div class="controls">
				<form:input path="gongLand.buyYear" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地性质：</label>
			<div class="controls">
				<form:input path="gongLand.chanQuality" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">土地抵押贷款余额(元)：</label>
			<div class="controls">
				<form:input path="gongLand.daiBalance" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<form:input path="gongLand.dengji" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租赁模型估价(元)：</label>
			<div class="controls">
				<form:input path="gongLand.guMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场交易价格(元)：</label>
			<div class="controls">
				<form:input path="gongLand.jiaoMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<form:input path="gongLand.man" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<form:input path="gongLand.quanNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<form:input path="gongLand.remark" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地价格1(元)：</label>
			<div class="controls">
				<form:input path="gongLand.tongMoney1" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等土地价格2(元)：</label>
			<div class="controls">
				<form:input path="gongLand.tongMoney2" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">销售模型估价(元)：</label>
			<div class="controls">
				<form:input path="gongLand.xiaoMoney" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<form:input path="gongLand.yuYear" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出租价格1(元)：</label>
			<div class="controls">
				<form:input path="gongLand.zuMoney1" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出租价格2(元)：</label>
			<div class="controls">
				<form:input path="gongLand.zuMoney2" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外键关联抵质押信息：</label>
			<div class="controls">
				<form:input path="gongLand.dizhiContractId" htmlEscape="false" maxlength="11" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="gongLand.remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
</body>
</html>