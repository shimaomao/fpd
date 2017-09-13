<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${reimburse.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>申请退款审核</title>
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
			
			
			//附件
			var oper = "view";//view表示只看，edit表示可以上传
			if(${reimburse.status}=="1"){
				//alert("可以上传");
				oper = "edit";
			}
			var url = "${ctx}/files/tContractFiles/showfilelist/${reimburse.id}.html?height=300&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper="+oper+"&nid=${nid}file";
			 $("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="reimburse" action="${ctx}/refund/reimburse/complete"  method="post" class="form-horizontal">
		<sys:message content="${message}"/>
				        <h3 align="center">申请退款</h3>
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
									${fns:getDictLabel(reimburse.businessType, 'ReimbBusinessStatus', '')}
								</td>
								<td>贷款金额(元)：</td>
								<td>${loanContract.loanAmount}</td>
							</tr>
							<tr>
								<td>还款月数：</td>
								<td>${reimburse.backMonth}</td>
								<td>超前天数：</td>
								<td>${reimburse.outDay}</td>
							</tr>
							<tr>
								<td>收款日期：</td>
								<td>
									<fmt:formatDate value="${reimburse.reimburseyDate}" pattern="yyyy-MM-dd"/>
								</td>
								<td>超支费用：</td>
								<td>${reimburse.outPrice}</td>
							</tr>
							<tr>
								<td>用款天数：</td>
								<td>${reimburse.yongDay}</td>
								<td> </td>
								<td> </td>
							</tr>
							<tr>
								<td>申请人：</td>
								<td>${reimburse.customerName}</td>
								<td>申请时间：</td>
								<td>
									<fmt:formatDate value="${reimburse.insertTime}" pattern="yyyy-MM-dd"/>
								</td>
							</tr>
							<tr>
								<td>提前还款本金(元)：</td>
								<td>${reimburse.tiPrice}</td>
								<td>退回还款金额：</td>
								<td>${reimburse.backPrice}</td>
							</tr>
							<tr>
								<td>退款状态：</td>
								<td>
									${fns:getDictLabel(reimburse.status, 'ReimburseStatus', '')}
								</td>
								<td>是否提醒：</td>
								<td>
									${fns:getDictLabel(reimburse.isRead, 'ReimReadStatus', '')}
								</td>
							</tr>
							<tr>
								<td>实际退费时间：</td>
								<td>
									<c:if test="${reimburse.status=='2'}">
										<input name="returnTime" type="text" readonly="readonly" maxlength="10" class="input-medium Wdate "
											onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
									</c:if>
								</td>
								<td> </td>
								<td> </td>
							</tr>
							<tr>
								<td>开户银行：</td>
								<td>${reimburse.bankname}</td>
								<td>开户账号：</td>
								<td>${reimburse.banknumber}</td>
							</tr>
							<tr>
								<td>开户人名：</td>
								<td>${reimburse.bankusername}</td>
								<td> </td>
								<td> </td>
							</tr>
							<tr>
								<td>备注：</td>
								<td colspan="3">${reimburse.remarks}</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="${nid}filelist" class=""  align="left">
									</div>
								</td>
							</tr>
						</tbody>
						</table>
		         <div   class="form-actions" style="padding-left: 0px;">
					<form:hidden path="id"/>
					<form:hidden path="act.taskId"/>
					<form:hidden path="act.taskName"/>
					<form:hidden path="act.taskDefKey"/>
					<form:hidden path="act.procInsId"/>
					<form:hidden path="act.procDefId"/>
					<form:hidden path="act.procDefKey"/>
					<form:hidden id="flag" path="act.flag"/>
					<sys:message content="${message}"/>		
					<div class="control-group">
						<label class="control-label">您的意见：</label>
						<div class="controls">
							<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
						</div>
					</div>
					<div class="form-actions">
						<c:if test="${edit}">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
						<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#flag').val('no')"/>&nbsp;
						<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#flag').val('stop')"/>&nbsp;
						</c:if>
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
					</div>
					<act:histoicFlow procInsId="${reimburse.act.procInsId}"/>
				</div>
	</form:form>
</body>
</html>