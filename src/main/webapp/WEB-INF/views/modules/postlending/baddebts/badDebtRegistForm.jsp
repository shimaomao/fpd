<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${badDebtRegist.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>坏账处理申请</title>
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
		
		$("#btnBadSubmit").click(function(){
			alert("ajax使用");
			var options = {
				url : '${ctx}/postlending/baddebts/badDebtRegist/save',
				type : 'post',
				dataType : 'json',//'text',
				data : $("#inputBadDebtForm").serializeArray(),
				success : function(data, textStatus) {
					alert(data.msg);
					if(data.params == "paramError"){
						
					}else{
						history.go(-1);
					}
				}
			};
			$.ajax(options);
			return false;
		})
		
	</script>
</head>
<body>
	<div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>贷后管理>坏账处理
	   </div>
	</div>
	<br>
	
	<form:form id="inputBadDebtForm" modelAttribute="badDebtRegist" action="${ctx}/postlending/baddebts/badDebtRegist/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />
		<form:hidden path="loanContractId" />
		<form:hidden path="registName" />
		<form:hidden path="department" />
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">贷款合同：</label>
			<div class="controls">
				${badDebtRegist.loanContract.contractNumber }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报日期：</label>
			<div class="controls">
				<input name="registerDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${badDebtRegist.registerDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">填报人名称：</label>
			<div class="controls">
				${badDebtRegist.registName }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报部门：</label>
			<div class="controls">
				${badDebtRegist.department }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申报状态：</label>
			<div class="controls">
				待提交
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目前欠款本金额：</label>
			<div class="controls">
				<form:input path="lossMoney" htmlEscape="false" maxlength="255" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已收回本金额：</label>
			<div class="controls">
				<form:input path="gainedMoney" htmlEscape="false" maxlength="255" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目前欠利息额：</label>
			<div class="controls">
				<form:input path="lossInterest" htmlEscape="false" maxlength="255" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">已收回利息额：</label>
			<div class="controls">
				<form:input path="gainedInterest" htmlEscape="false" maxlength="255" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">债务人现况：</label>
			<div class="controls">
				<form:textarea path="currentInfo" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期原因：</label>
			<div class="controls">
				<form:textarea path="reason" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期时间：</label>
			<div class="controls">
				<input name="exceedTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${badDebtRegist.exceedTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">诉讼及委托情况：</label>
			<div class="controls">
				<form:textarea path="info" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">催收责任人：</label>
			<div class="controls">
				<form:input path="debtCollecter" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">催收责任人联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后一次还款时间：</label>
			<div class="controls">
				<input name="lastRepay" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${badDebtRegist.lastRepay}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注说明：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">申请意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${edit}">
			<shiro:hasPermission name="postlending:baddebts:badDebtRegist:edit"><input id="btnBadSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="$('#flag').val('yes')"/>&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnBadCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<c:if test="${not empty badDebtRegist.id}">
			<act:histoicFlow procInsId="${badDebtRegist.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>