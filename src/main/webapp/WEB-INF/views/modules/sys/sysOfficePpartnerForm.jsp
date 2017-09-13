<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构个人股东管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysOfficePpartner/form?id=${sysOfficePpartner.id}">机构个人股东<shiro:hasPermission name="sys:sysOfficePpartner:edit">${not empty sysOfficePpartner.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOfficePpartner:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOfficePpartner" action="${ctx}/sys/sysOfficePpartner/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<select id="gender" name="gender" class="required select2-offscreen">
					<option value="男" <c:if test="${'男' == sysOfficePpartner.gender}">selected</c:if> >男</option>
					<option value="女" <c:if test="${'女' == sysOfficePpartner.gender}">selected</c:if> >女</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="idCard" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户口所在地：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">工作单位：</label>
			<div class="controls">
				<form:input path="workUnit" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册地：</label>
			<div class="controls">
				<form:input path="regAddr" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
			<label class="control-label">电子邮件：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="255" class="input-xlarge " type="email"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通讯地址：</label>
			<div class="controls">
				<form:input path="messAddr" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入股时间：</label>
			<div class="controls">
				<form:input path="signDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge " id="cardType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">是否在职:</label>
			<div class="controls">
				<select id="status" name="status" class="select2-offscreen">
					<option value="在职" <c:if test="${'在职' == sysOfficePpartner.status}">selected</c:if>>在职</option>
					<option value="离职" <c:if test="${'离职' == sysOfficePpartner.status}">selected</c:if>>离职</option>
				</select>
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
		</div>--%>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysOfficePpartner:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div> 
	</form:form>
</body>
</html>