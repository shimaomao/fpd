<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申请退款管理</title>
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
			
			$("#btnCancel").click(function(){
				top.$.jBox.close(true);
			});
			
			$("#btnSubmit").click(function(){
				$('#flag').val('yes');
				 var options = {
							url : '${ctx}/refund/reimburse/save',
							type : 'post',
							dataType : 'json',//'text', 
							data : $("#inputForm").serializeArray(),
							success : function(data, textStatus) {
								alert(data.msg);
								top.$.jBox.close(true);
							}
						};
						$.ajax(options);
						return false;
			});
			
			<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${reimburse.id}.html?height=380&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
			 $("#${nid}filelist").load(url); --%>
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="reimburse" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden id="flag" path="act.flag"/>
		
		<form:hidden path="customerName"/>
		<form:hidden path="insertTime"/>
		<sys:message content="${message}"/>
		<table cellpadding="0" cellspacing="0" width="100%">
			<tbody>
			<tr><td>
				<h3 align="center">申请退款</h3>
				<input id="loanContractId" name="loanContractId" type="hidden" value="${loanContract.id}"/><!-- 合同ID -->
				<input id="loanPeriod" name="loanPeriod" type="hidden" value="${loanContract.loanPeriod}"/><!-- 贷款期限 -->
				<input id="backName" name="backName" type="hidden" value="${loanContract.customerName}"/><!-- 借款人 -->
				<input id="jiePrice" name="jiePrice" type="hidden" value="${loanContract.loanAmount}"/><!-- 贷款金额 -->
				<input id="bankname" name="bankname" type="hidden" value="${tEmployee.gatheringBank}"/><!-- 开户银行 -->
				<input id="banknumber" name="banknumber" type="hidden" value="${tEmployee.gatheringNumber}"/><!-- 开户账号 -->
				<input id="bankusername" name="bankusername" type="hidden" value="${tEmployee.gatheringName}"/><!-- 开户名 -->
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<tbody>
					<tr>
						<td>业务编号：</td>
						<td>${loanContract.contractNumber}</td>
						<td>贷款期限：</td>
						<td>${loanContract.loanPeriod}</td>
					</tr>
					<tr>
						<td>业务类型：</td>
						<td>
							<form:select path="businessType" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ReimbBusinessStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</td>
						<td>贷款金额(元)：</td>
						<td>${loanContract.loanAmount}</td>
					</tr>
					<tr>
						<td>还款月数：</td>
						<td><form:input path="backMonth" htmlEscape="false" minlength="1" maxlength="255" class="input-xlarge number"/></td>
						<td>超前天数：</td>
						<td><form:input path="outDay" htmlEscape="false" minlength="1" maxlength="11" class="input-xlarge  digits"/></td>
					</tr>
					<tr>
						<td>收款日期：</td>
						<td><input name="reimburseyDate" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate "
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
						<td>超支费用：</td>
						<td><form:input path="outPrice" htmlEscape="false" minlength="1" class="input-xlarge  number"/></td>
					</tr>
					<tr>
						<td>用款天数：</td>
						<td><form:input path="yongDay" htmlEscape="false" minlength="1" maxlength="255" class="input-xlarge "/></td>
						<td>申请人：</td>
						<td>${reimburse.customerName }</td>
					</tr>
					<%-- <tr>
						<td>申请人：</td>
						<td><form:input path="customerName" htmlEscape="false" minlength="1" maxlength="255" class="input-xlarge "/></td>
						<td>申请时间：</td>
						<td>
							<input name="insertTime" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate "
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						</td>
					</tr> --%>
					<tr>
						<td>提前还款本金(元)：</td>
						<td><form:input path="tiPrice" htmlEscape="false" minlength="1" class="input-xlarge number"/></td>
						<td>退回还款金额：</td>
						<td><form:input path="backPrice" htmlEscape="false" minlength="1" class="input-xlarge  number"/></td>
					</tr>
					<tr>
						<td>退款状态：</td>
						<td>等待申请
							<%-- <form:select path="status" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ReimburseStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select> --%>
						</td>
						<td>是否提醒：</td>
						<td>
							<form:select path="isRead" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ReimReadStatus')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td>实际退费时间：</td>
						<td>
							<!-- <input name="returnTime" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate "
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> -->
						</td>
						<td> </td>
						<td> </td>
					</tr>
					<tr>
						<td>开户银行：</td>
						<td>${tEmployee.gatheringBank}</td>
						<td>开户账号：</td>
						<td>${tEmployee.gatheringNumber}</td>
					</tr>
					<tr>
						<td>开户人名：</td>
						<td>${tEmployee.gatheringName}</td>
						<td> </td>
						<td> </td>
					</tr>
					<tr>
						<td>备注：</td>
						<td colspan="3">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
						</td>
					</tr>
					<%-- <tr>
						<td colspan="4">
							<div id="${nid}filelist" class=""  align="left">
							</div>
						</td>
					</tr> --%>
				</tbody>
				</table>
				<div  align="center" class="form-actions">
					<shiro:hasPermission name="refund:reimburse:view_re">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/>&nbsp;
					</shiro:hasPermission>
					<input id="btnCancel" class="btn" type="button" value="返 回"/>
				</div>
			</td></tr>
			</tbody>
		</table>
	</form:form>
</body>
</html>