<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构详情管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:choose >
			<c:when test="${user.id == '1' }">
			<c:if test="${office.type == '1' }">
			<li><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
			</c:if>
			<c:if test="${office.type == '2' }">
			<li><a href="${ctx}/sys/office/list?id=${office.parent.id}&parentIds=${office.parentIds}">机构列表</a></li>
			</c:if>
			</c:when>
			<c:otherwise>
			<li><a href="${ctx}/sys/sysOfficeDetail/formForDetail?id=${sysOfficeDetail.pid}">机构基本信息</a></li>
			</c:otherwise>
		</c:choose>
		<li class="active"><a href="${ctx}/sys/sysOfficeDetail/form?id=${sysOfficeDetail.id}">机构详情<shiro:hasPermission name="sys:sysOfficeDetail:edit">${not empty sysOfficeDetail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOfficeDetail:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOfficeDetail" action="${ctx}/sys/sysOfficeDetail/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required input-xlarge" readonly="true"/>
				<span class="help-inline"><font color="red">*必须填写工商注册时的全称</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">经营范围：</label>
			<div class="controls">
				<form:input path="fanWei" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">设立时间：</label>
			<div class="controls">
				<form:input path="signDate" htmlEscape="false" class="Wdate input-xlarge" onClick="WdatePicker()"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">开业时间：</label>
			<div class="controls">
				<form:input path="openDate" htmlEscape="false" class="Wdate input-xlarge" onClick="WdatePicker()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册资金：</label>
			<div class="controls">
				<form:input path="registerMoney" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<span class="help-inline"><font color="black">万元</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法定代表人：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.master}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否互联网小贷:</label>
			<div class="controls">
				<select id="type" name="type" class="select2-offscreen">
					<option value="是" <c:if test="${'是' == sysOfficeDetail.type}">selected</c:if>>是</option>
					<option value="否" <c:if test="${'否' == sysOfficeDetail.type}">selected</c:if>>否</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">场地面积:</label>
			<div class="controls">
				<form:input path="mianJi" htmlEscape="false" maxlength="50" class="required input-xlarge"/>
				<span class="help-inline"><font color="black">平方米</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="licenseNumbers" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">统一信用代码：</label>
			<div class="controls">
				<form:input path="creditNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.fax}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.master}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户名：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.accoName}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.bank}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">账户：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.account}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="" htmlEscape="false" maxlength="255" class="input-xlarge " value="${office.phone}" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织形式:</label>
			<div class="controls">
				<select id="orgForm" name="orgForm" class="select2-offscreen">
					<option value="0" <c:if test="${'0' == sysOfficeDetail.orgForm}">selected</c:if>>有限责任公司</option>
					<option value="1" <c:if test="${'1' == sysOfficeDetail.orgForm}">selected</c:if>>股份有限公司</option>
				</select>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">防范措施：</label>
			<div class="controls">
				<form:input path="fangFan" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照扫描件：</label>
			<div class="controls">
				<form:input path="licNumScan" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照扫描件路径：</label>
			<div class="controls">
				<form:input path="licNumScanPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目的：</label>
			<div class="controls">
				<form:input path="muDi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码：</label>
			<div class="controls">
				<form:input path="orgCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码扫描件：</label>
			<div class="controls">
				<form:input path="orgCodeScan" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">组织机构代码扫描件路径：</label>
			<div class="controls">
				<form:input path="orgCodeScanPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">股东协议书：</label>
			<div class="controls">
				<form:input path="protocol" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股东协议书路径：</label>
			<div class="controls">
				<form:input path="protocolPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地税编码：</label>
			<div class="controls">
				<form:input path="diTaxCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地税税务登记证扫描件附件：</label>
			<div class="controls">
				<form:input path="diTaxScan" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地税税务登记证扫描件路径：</label>
			<div class="controls">
				<form:input path="diTaxScanPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">guo_tax_code：</label>
			<div class="controls">
				<form:input path="guoTaxCode" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国税税务登记证扫描件附件：</label>
			<div class="controls">
				<form:input path="guoTaxScan" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">国税税务登记证扫描件路径：</label>
			<div class="controls">
				<form:input path="guoTaxScanPath" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地税税务登记证扫描件附件重新命名：</label>
			<div class="controls">
				<form:input path="diTaxScanRename" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">guo_tax_scan_rename：</label>
			<div class="controls">
				<form:input path="guoTaxScanRename" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照扫描件重新命名：</label>
			<div class="controls">
				<form:input path="licNumScanRename" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">织机构代码扫描件重新命名：</label>
			<div class="controls">
				<form:input path="orgCodeScanRename" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">股东协议书附件重新命名：</label>
			<div class="controls">
				<form:input path="protocolRename" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">found_date：</label>
			<div class="controls">
				<form:input path="foundDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">juan_money：</label>
			<div class="controls">
				<form:input path="juanMoney" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">faren：</label>
			<div class="controls">
				<form:input path="faren" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">source_flag：</label>
			<div class="controls">
				<form:input path="sourceFlag" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">source_status：</label>
			<div class="controls">
				<form:input path="sourceStatus" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">nif_id：</label>
			<div class="controls">
				<form:input path="nifId" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysOfficeDetail:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>