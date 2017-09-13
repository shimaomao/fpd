<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<body>
		<input id="building.id" name="building.id" type="hidden"  value="${building.id}">
		<%-- <sys:message content="${message}"/> --%>		
		<div class="control-group">
			<label class="control-label">房地产地点：</label>
			<div class="controls">
				<input id="building.address" name="building.address" value="${building.address}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑面积 (㎡)：</label>
			<div class="controls">
				<input id="building.area" name="building.area" value="<fmt:formatNumber value="${building.area}" pattern="#0.00"/>" class="input-xlarge  number" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">按揭余额(元) ：</label>
			<div class="controls">
				<input id="building.balance" name="building.balance" value="<fmt:formatNumber value="${building.balance}" pattern="#0.00"/>" class="input-xlarge  number" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权人：</label>
			<div class="controls">
				<input id="building.chanMan" name="building.chanMan" value="${building.chanMan}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产权性质：</label>
			<div class="controls">
				<input id="building.chanQuality" name="building.chanQuality" value="${building.chanQuality}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">登记情况：</label>
			<div class="controls">
				<input id="building.dengji" name="building.dengji" value="${building.dengji}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑结构：</label>
			<div class="controls">
				<input id="building.framework" name="building.framework" value="${building.framework}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">层高：</label>
			<div class="controls">
				<input id="building.gao" name="building.gao" value="${building.gao}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">共有人：</label>
			<div class="controls">
				<input id="building.gongMan" name="building.gongMan" value="${building.gongMan}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租赁模型估算(元) ：</label>
			<div class="controls">
				<input id="building.guMoney" name="building.guMoney" value="<fmt:formatNumber value="${building.guMoney}" pattern="#0.00"/>" class="input-xlarge  number" type="text">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户型结构：</label>
			<div class="controls">
				<input id="building.jiegou" name="building.jiegou" value="${building.jiegou}" class="input-xlarge " type="text" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建成年份：</label>
			<div class="controls">
				<input name="building.jyear" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "  value="<fmt:formatDate value="${building.jyear}" pattern="yyyy-MM-dd HH:mm:ss"/>"" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">模型估价(元) ：</label>
			<div class="controls">
				<input id="building.moMoney" name="building.moMoney" class="input-xlarge  number" type="text"  value="<fmt:formatNumber value="${building.moMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产单位单价(元) ：</label>
			<div class="controls">
				<input id="building.money1" name="building.money1" class="input-xlarge  number" type="text"  value="<fmt:formatNumber value="${building.money1}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新房单价(元) ：</label>
			<div class="controls">
				<input id="building.newMoney" name="building.newMoney" class="input-xlarge  number" type="text"  value="<fmt:formatNumber value="${building.newMoney}" pattern="#0.00"/>">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">权证编号：</label>
			<div class="controls">
				<input id="building.quanNum" name="building.quanNum" class="input-xlarge " type="text"  value="${building.quanNum}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地段描述：</label>
			<div class="controls">
				<input id="building.remark" name="building.remark" class="input-xlarge " type="text" value="${building.remark}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建筑样式：</label>
			<div class="controls">
				<input id="building.yangshi" name="building.yangshi" class="input-xlarge " type="text" value="${building.yangshi}" maxlength="255">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">剩余年限：</label>
			<div class="controls">
				<input id="building.yearLimit" name="building.yearLimit" class="input-xlarge  digits" type="text" value="${building.yearLimit}" maxlength="11">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">同等房产租金(元) ：</label>
			<div class="controls">
				<input id="building.zumoney1" name="building.zumoney1" class="input-xlarge  number" type="text" value="<fmt:formatNumber value="${building.zumoney1}" pattern="#0.00"/>">
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">抵质押信息id：</label>
			<div class="controls">
				<input id="building.dizhiContractId" name="building.dizhiContractId" class="input-xlarge " type="text"  value="${building.dizhiContractId}" maxlength="11">
			</div>
		</div> --%>
		<input id="building.dizhiContractId" name="building.dizhiContractId" type="hidden"  value="${building.dizhiContractId}">
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<textarea id="building.remarks" name="building.remarks" value="${building.remarks}" maxlength="200" class="input-xxlarge " rows="4"></textarea>
			</div>
		</div> --%>
		<input id="building.remarks" name="building.remarks" type="hidden"  value="${building.remarks}">
</body>
</html>