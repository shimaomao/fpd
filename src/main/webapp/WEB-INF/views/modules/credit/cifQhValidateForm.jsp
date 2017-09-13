<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>creditResult管理</title>
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
		<li><a href="${ctx}/credit/cifQhValidate/">creditResult列表</a></li>
		<li class="active"><a href="${ctx}/credit/cifQhValidate/form?id=${cifQhValidate.id}">creditResult<shiro:hasPermission name="credit:cifQhValidate:edit">${not empty cifQhValidate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="credit:cifQhValidate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cifQhValidate" action="${ctx}/credit/cifQhValidate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">res_struct主键：</label>
			<div class="controls">
				<form:input path="resId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="idNo" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:input path="idType" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主体名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码：</label>
			<div class="controls">
				<form:input path="mobileNo" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序列号：</label>
			<div class="controls">
				<form:input path="seqNo" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否真实身份：</label>
			<div class="controls">
				<form:input path="isRealIdentity" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否本人真实活动地址：</label>
			<div class="controls">
				<form:input path="isValidAddress" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址类型：</label>
			<div class="controls">
				<form:input path="addressType" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否真实工作单位：</label>
			<div class="controls">
				<form:input path="isRealCompany" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位匹配度分值：</label>
			<div class="controls">
				<form:input path="companySimDeg" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位最初出现时间：</label>
			<div class="controls">
				<form:input path="appear1ThDate" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位最近一次出现时间：</label>
			<div class="controls">
				<form:input path="appearLastDate" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机验证结果：</label>
			<div class="controls">
				<form:input path="isOwnerMobile" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机状态：</label>
			<div class="controls">
				<form:input path="ownerMobileStatus" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用时间分数：</label>
			<div class="controls">
				<form:input path="useTimeScore" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否存在关系：</label>
			<div class="controls">
				<form:input path="isExistRel" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关系力度：</label>
			<div class="controls">
				<form:input path="relLevel" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车辆验证结果：</label>
			<div class="controls">
				<form:input path="carChkResult" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否异步返回结果：</label>
			<div class="controls">
				<form:input path="isAsyned" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车型：</label>
			<div class="controls">
				<form:input path="carTypeInc" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">厂牌：</label>
			<div class="controls">
				<form:input path="carFactoryInc" htmlEscape="false" maxlength="128" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">新车购置价：</label>
			<div class="controls">
				<form:input path="carPrice" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行驶证状态查询Id号：</label>
			<div class="controls">
				<form:input path="drvStatusQryId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房屋验证结果：</label>
			<div class="controls">
				<form:input path="houseChkResult" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">房屋数据获取时间：</label>
			<div class="controls">
				<form:input path="houseDataDate" htmlEscape="false" maxlength="24" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相片比对结果：</label>
			<div class="controls">
				<form:input path="photoCmpResult" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相片相似度：</label>
			<div class="controls">
				<form:input path="photoSimDeg" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否本人真实最高学历：</label>
			<div class="controls">
				<form:input path="isHighestEduBkg" htmlEscape="false" maxlength="2" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数据获取时间：</label>
			<div class="controls">
				<form:input path="dataDate" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="graduateSchool" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业专业：</label>
			<div class="controls">
				<form:input path="graduateMajor" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业年份：</label>
			<div class="controls">
				<form:input path="graduateYear" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机验证II结果：</label>
			<div class="controls">
				<form:input path="isOwnerMobileTwo" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机状态II：</label>
			<div class="controls">
				<form:input path="ownerMobileStatusTwo" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用时间分数II：</label>
			<div class="controls">
				<form:input path="useTimeScoreTwo" htmlEscape="false" maxlength="8" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">错误信息：</label>
			<div class="controls">
				<form:input path="errorInfo" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="50" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司ID：</label>
			<div class="controls">
				<form:input path="organId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">部门ID：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${cifQhValidate.office.id}" labelName="office.name" labelValue="${cifQhValidate.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="credit:cifQhValidate:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>