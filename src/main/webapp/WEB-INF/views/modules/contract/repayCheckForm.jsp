<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>还款确认信息管理</title>
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
			
			var url = "${ctx}/files/tContractFiles/showfilelist/${repayCheck.id}.html?height=120&businesstype=repayCheck&oper=edit&nid=${nid}file";
		    $("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active">还款确认</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="repayCheck" action="${ctx}/contract/repayCheck/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="status"/>
		<form:hidden path="organId"/>
		<%-- <form:hidden path="loanContractId"/> --%>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="pass" name="act.vars.map['pass']"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="35" class="input-xlarge required" disabled="${show}"/>
				<span class="help-inline"><font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行名称：</label>
			<div class="controls">
				<form:input path="bankName" htmlEscape="false" maxlength="100" class="input-xlarge" disabled="${show}"/>
				<!-- <span class="help-inline"><font color="red">*</font> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">银行账户：</label>
			<div class="controls">
				<form:input path="backCount" htmlEscape="false" maxlength="30" class="input-xlarge" disabled="${show}"/>
				<!-- <span class="help-inline"><font color="red">*</font> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款金额：</label>
			<div class="controls">
				<form:input path="repayMoney" htmlEscape="false" class="input-xlarge number required" disabled="${show}"/>元
				<span class="help-inline"><font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款时间：</label>
			<div class="controls">
				<input name="repayTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					<c:if test="${show}">
						disabled="true"
					</c:if>
					value="<fmt:formatDate value="${repayCheck.repayTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge" disabled="${show}"/>
				<!-- <span class="help-inline"><font color="red">*</font> -->
			</div>
		</div>
		<c:if test="${!empty repayCheck.replyMessage || (!empty subType && subType == 'one')}">
		<div class="control-group">
			<label class="control-label">关联业务:</label>
			<div class="controls">
				<c:if test="${empty subType || subType != 'one'}">
					<form:input path="loanContract.contractNumber" htmlEscape="false" class="input-xlarge required" disabled="true"/>
					<%-- <sys:treeselect id="loanContract" name="loanContract.id" value="${repayCheck.loanContract.id}" labelName="loanContract.contractNumber" labelValue="${repayCheck.loanContract.contractNumber}"
					title="关联业务a" url="/contract/tLoanContract/treeDataLoanContracts?status=repaycheck" cssClass="required disabled"/> --%>
				</c:if>
				<c:if test="${!empty subType && subType == 'one'}">
                	<sys:treeselect id="loanContract" name="loanContract.id" value="${repayCheck.loanContract.id}" labelName="loanContract.contractNumber" labelValue="${repayCheck.loanContract.contractNumber}"
					title="关联业务" url="/contract/tLoanContract/treeDataLoanContracts?status=repaycheck" cssClass="required"/><span class="help-inline"><font color="red">*</font>
				</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认信息：</label>
			<div class="controls">
				<%-- <form:input path="replyMessage" htmlEscape="false" class="input-xlarge required"/> disabled="true"  --%>
				<c:if test="${empty subType || subType != 'one'}">
					<form:textarea path="replyMessage" class="required" disabled="true"
					rows="5" maxlength="3800" cssStyle="width:500px"/>
					<span class="help-inline"><font color="red">*</font>
				</c:if>
				<c:if test="${!empty subType && subType == 'one'}">
				<form:textarea path="replyMessage" class="required" 
				rows="5" maxlength="3800" cssStyle="width:500px"/>
				<span class="help-inline"><font color="red">*</font>
				</c:if>
			</div>
		</div>
		</c:if>
		<c:if test="${!empty subType}">
		<div class="control-group">
			<label class="control-label">您的意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="3800" cssStyle="width:500px"/>
				<span class="help-inline"><font color="red">*</font>
			</div>
		</div>
		</c:if>
		<div class="form-actions">
			<%-- <c:if test="${empty repayCheck.id && empty repayCheck.act.procInsId}"> --%>
			<c:if test="${empty repayCheck.act.procInsId}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步"/>&nbsp;
			</c:if>
			<c:if test="${!empty repayCheck.act.procInsId}">
				<c:choose>
					<c:when test="${!empty subType && subType == 'start'}">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
					</c:when>
					<c:when test="${!empty subType}">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
						<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回给上一步" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
					</c:when>
					<%-- <c:otherwise>
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="确 认" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
						<input id="btnSubmit" class="btn btn-inverse" type="submit" value="退回修改" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
					</c:otherwise> --%>
				</c:choose>
			</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div class="control-group">
			<label class="control-label"><h5>相关附件：</h5></label>
			<div class="control-group" style="height: 200px;">
				<div id="${nid}filelist" style="height: 250px;"></div>
			</div>
		</div>
		<c:if test="${not empty repayCheck.id}">
			<act:histoicFlow procInsId="${repayCheck.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>