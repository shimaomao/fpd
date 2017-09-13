<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>从业人员管理</title>
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
		
		function tips(){
			alert("请先保存基本信息。");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysOfficeEmployee/">从业人员列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysOfficeEmployee/form?id=${sysOfficeEmployee.id}">从业人员<shiro:hasPermission name="sys:sysOfficeEmployee:edit">${not empty sysOfficeEmployee.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOfficeEmployee:edit">查看</shiro:lacksPermission></a></li>
		<c:choose >
			<c:when test="${sysOfficeEmployee.id != null}">
			<li><a href="${ctx}/sys/study/list?employId=${sysOfficeEmployee.id}">学习经历</a></li>
			<li><a href="${ctx}/sys/experience/list?employId=${sysOfficeEmployee.id}">工作经历</a></li>
			<li><a href="${ctx}/sys/family/list?employId=${sysOfficeEmployee.id}">家庭主要成员情况</a></li>
			</c:when>
			<c:otherwise>
			<li><a onclick="tips();"  href="#">学习经历</a></li>
			<li><a onclick="tips();"  href="#">工作经历</a></li>
			<li><a onclick="tips();"  href="#">家庭主要成员情况</a></li>
		</c:otherwise>
		</c:choose>
		
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOfficeEmployee" action="${ctx}/sys/sysOfficeEmployee/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="pid"/>
		<sys:message content="${message}"/>	
		<%-- <div class="control-group">
			<label class="control-label">机构标识：</label>
			<div class="controls">
				 <sys:treeselect id="pid" name="pid" value="${sysOfficeEmployee.pid}" labelName="pid" labelValue="${sysOfficeEmployee.pid}"
					title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<select id="gender" name="gender" class="required select2-offscreen">
					<option value="男" <c:if test="${'男' == sysOfficeEmployee.gender}">selected</c:if> >男</option>
					<option value="女" <c:if test="${'女' == sysOfficeEmployee.gender}">selected</c:if> >女</option>
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
			<label class="control-label">现任职务：</label>
			<div class="controls">
				<form:input path="position" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高学历：</label>
			<div class="controls">
				<form:select path="xueLi" class="input-xlarge " id="pouseEducation" maxlength="255">
					<form:option value="0" label=""/>
					<form:options items="${fns:getDictList('education')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				<form:input path="professional" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">毕业院校：</label>
			<div class="controls">
				<form:input path="school" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">技术职称：</label>
			<div class="controls">
				<form:input path="jishuzhicheng" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:input path="politicalAffiliation" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
			<label class="control-label">状态:</label>
			<div class="controls">
				<select id="status" name="status" class="select2-offscreen">
					<option value="在职" <c:if test="${'在职' == sysOfficeEmployee.status}">selected</c:if>>在职</option>
					<option value="离职" <c:if test="${'离职' == sysOfficeEmployee.status}">selected</c:if>>离职</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入职日期：</label>
			<div class="controls">
				<form:input path="signDate" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<form:input path="birthday" htmlEscape="false" maxlength="255" class="input-xlarge Wdate" onClick="WdatePicker()"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge " id="cardType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简历：</label>
			<div class="controls">
				<form:input path="jianLi" htmlEscape="false" maxlength="5000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">从业禁止情形：</label>
			<div class="controls">
				<form:input path="jinzhi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:input path="national" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯：</label>
			<div class="controls">
				<form:input path="nativePlace" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">董事-监事-高管-其他从业人员：</label>
			<div class="controls">
				<form:input path="twomenu" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">学历,文化程度：</label>
			<div class="controls">
				<form:input path="xueLi" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">工作年限：</label>
			<div class="controls">
				<form:input path="jobYear" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">婚否:</label>
			<div class="controls">
				<select id="marry" name="marry" class="select2-offscreen">
					<option value="已婚" <c:if test="${'已婚' == sysOfficeEmployee.marry}">selected</c:if>>已婚</option>
					<option value="未婚" <c:if test="${'未婚' == sysOfficeEmployee.marry}">selected</c:if>>未婚</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">相关工作年限：</label>
			<div class="controls">
				<form:input path="xiangguanJobYear" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div> --%>
		
		<%-- <div class="control-group">
			<label class="control-label">li_date：</label>
			<div class="controls">
				<form:input path="liDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">ru_date：</label>
			<div class="controls">
				<form:input path="ruDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">bi_date：</label>
			<div class="controls">
				<form:input path="biDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">depart：</label>
			<div class="controls">
				<form:input path="depart" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
			<label class="control-label">approval_date：</label>
			<div class="controls">
				<form:input path="approvalDate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
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
			<shiro:hasPermission name="sys:sysOfficeEmployee:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>