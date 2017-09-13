<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科目余额管理</title>
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
		<li><a href="${ctx}/accountbalance/tAccountBalance/">科目余额列表</a></li>
		<li class="active"><a href="${ctx}/accountbalance/tAccountBalance/form?id=${tAccountBalance.id}">科目余额<shiro:hasPermission name="accountbalance:tAccountBalance:edit">${not empty tAccountBalance.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="accountbalance:tAccountBalance:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tAccountBalance" action="${ctx}/accountbalance/tAccountBalance/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		
		<table class="table-form">
			<tr>
				<td class="tit">上级科目</td>
				<td>
				   <sys:treeselect  id="tAccountBalance" isAll="false" name="parent.id" value="${tAccountBalance.parent.id}" labelName="parent.subjectName" labelValue="${tAccountBalance.parent.subjectName}"
				     title="上级科目" url="/accountbalance/tAccountBalance/treeData" extId="${tAccountBalance.id}"  allowClear="true" notAllowSelectParent="true"/>
				</td>
				<td class="tit">科目编号</td>
				<td>
					<form:input path="subjectNumber" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
				</td>
				<td class="tit">科目名称</td>
				<td>
				     <form:input path="subjectName" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="tit">级数</td>
				<td>
				    <form:input path="level" htmlEscape="false" maxlength="19" class="input-xlarge " style="width:205px;"/>
				</td>
					<td class="tit">明细</td>
				<td>
				     <form:input path="detail" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
				</td>
				<td class="tit">创建时间</td>
				<td>
                   	<%-- <input name="createDate" id="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tAccountBalance.createDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" style="width:183px;"/> --%>
                        <fmt:formatDate value="${tAccountBalance.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                </td>
			</tr>
			<tr style="line-height: 2;">
				<td class="tit" colspan="2" style="margin-right: 15%;padding-right: 18%;">期初余额</td>
				<td class="tit" colspan="2" style="margin-right: 15%;padding-right: 15%;">本期发生</td>
				<td class="tit" colspan="2" style="margin-right: 15%;padding-right: 15%;">当前余额</td>
			</tr>
			<tr  style="line-height: 2;">
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">方向</td>
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">金额</td>
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">借方</td>
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">贷方</td>
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">方向</td>
				<td class="tit" style="margin-right: 15%;padding-right: 8%;">金额</td>
			</tr>
			<tr>
				<td style="margin-right: 15%;padding-left:  2%;">
				   <form:select path="beginDirec" class="input-xlarge " style="width:180px;">
						 <form:option value="借" label="借"/>
						 <form:option value="贷" label="贷"/>
						 <form:option value="平" label="平"/>
				   </form:select>
				</td>
				<td style="margin-right: 15%;padding-left:  2%;"><form:input path="beginBalance" htmlEscape="false" maxlength="19" class="input-xlarge " style="width:180px;"/></td>
				<td style="margin-right: 15%;padding-left:  2%;"><form:input path="currentDebit" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/></td>
				<td style="margin-right: 15%;padding-left:  2%;"><form:input path="currentLender" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/></td>
				<td style="margin-right: 15%;padding-left:  2%;">
				     <form:select path="currentDirec" class="input-xlarge " style="width:180px;">
						 <form:option value="借" label="借"/>
						 <form:option value="贷" label="贷"/>
						 <form:option value="平" label="平"/>
				   </form:select>
				</td>
				<td style="margin-right: 15%;padding-left:  2%;"><form:input path="currentBalance" htmlEscape="false" maxlength="255" class="input-xlarge " style="width:180px;"/></td>
			</tr>
         </table>
         
		<div class="form-actions">
			<shiro:hasPermission name="accountbalance:tAccountBalance:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>