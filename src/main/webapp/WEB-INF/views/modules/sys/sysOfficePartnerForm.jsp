<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构股东管理</title>
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
		<li><a href="${ctx}/sys/sysOfficePartner/partners">股东列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysOfficePartner/form?id=${sysOfficePartner.id}">机构股东<shiro:hasPermission name="sys:sysOfficePartner:edit">${not empty sysOfficePartner.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOfficePartner:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOfficePartner" action="${ctx}/sys/sysOfficePartner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<sys:message content="${message}"/>
		<%-- <div class="control-group">
			<label class="control-label">机构ID：</label>
			<div class="controls">
				<sys:treeselect id="pid" name="pid" value="${sysOfficePartner.pid}" labelName="pid" labelValue="${sysOfficePartner.pid}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">股东名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">法定代表人：</label>
			<div class="controls">
				<form:input path="legalPerson" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">营业执照编号：</label>
			<div class="controls">
				<form:input path="licNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">统一信用编码：</label>
			<div class="controls">
				<form:input path="creditNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">营业地址：</label>
			<div class="controls">
				<form:input path="busAddr" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出资额(万元):</label>
			<div class="controls">
				<form:input path="funds" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出资比例：</label>
			<div class="controls">
				<form:input path="proportion" htmlEscape="false" maxlength="255" class="input-xlarge "/>%
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话/传真:</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮件:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="255" class="input-xlarge " type="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通讯地址：</label>
			<div class="controls">
				<form:input path="regAddr" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否国有企业:</label>
			<div class="controls">
				<select id="isGuoqi" name="isGuoqi" class="select2-offscreen">
					<option value="是" <c:if test="${'是' == sysOfficePartner.isGuoqi}">selected</c:if>>是</option>
					<option value="否" <c:if test="${'否' == sysOfficePartner.isGuoqi}">selected</c:if>>否</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否上市:</label>
			<div class="controls">
				<select id="isShangshi" name="isShangshi" class="select2-offscreen">
					<option value="是" <c:if test="${'是' == sysOfficePartner.isShangshi}">selected</c:if>>是</option>
					<option value="否" <c:if test="${'否' == sysOfficePartner.isShangshi}">selected</c:if>>否</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入股日期：</label>
			<div class="controls">
				<form:input path="signDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经营范围：</label>
			<div class="controls">
				<form:textarea path="fanWei" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">chezhi：</label>
			<div class="controls">
				<form:input path="chezhi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">chigu_date1：</label>
			<div class="controls">
				<form:input path="chiguDate1" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">chigu_date2：</label>
			<div class="controls">
				<form:input path="chiguDate2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zaizhi_date1：</label>
			<div class="controls">
				<form:input path="zaizhiDate1" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">zaizhi_date2：</label>
			<div class="controls">
				<form:input path="zaizhiDate2" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bian_bi：</label>
			<div class="controls">
				<form:input path="bianBi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bian_date：</label>
			<div class="controls">
				<form:input path="bianDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bian_id：</label>
			<div class="controls">
				<form:input path="bianId" htmlEscape="false" maxlength="20" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bian_shi：</label>
			<div class="controls">
				<form:input path="bianShi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bian_zi：</label>
			<div class="controls">
				<form:input path="bianZi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">islisi：</label>
			<div class="controls">
				<form:input path="islisi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ping_jia：</label>
			<div class="controls">
				<form:input path="pingJia" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysOfficePartner:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>