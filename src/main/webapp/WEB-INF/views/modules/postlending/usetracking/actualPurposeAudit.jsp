<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${actualPurpose.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>贷后检查审核</title>
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
			var url = "${ctx}/files/tContractFiles/showfilelist/${actualPurpose.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=view&nid=${nid}file";
			$("#${nid}filelist").load(url);
		});
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="actualPurpose" action="${ctx}/postlending/usetracking/actualPurpose/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />
		<sys:message content="${message}"/>		
		<table cellpadding="0" cellspacing="0" width="100%">
		<tbody>
			<tr>
				<td>
					<h3 align="center">检查审核</h3>
					<table id="contentTable"
						class="table table-striped table-bordered table-condensed">
						<tbody>
							<tr>
								<td>标题：</td>
								<td colspan="3">${actualPurpose.title}</td>

							</tr>
							<tr>
								<td>合同编号：</td>
								<td>${actualPurpose.contractNumber}</td>
								<td>贷款用途：</td>
								<td>${actualPurpose.purpose}</td>
							</tr>
							<tr>
								<td>用途跟踪内容：</td>
								<td colspan="3">${actualPurpose.content}</td>
							</tr>
							<tr>
								<td>用户名称：</td>
								<td>${actualPurpose.customerName}</td>
								<td>申请时间：</td>
								<td>
									<fmt:formatDate value="${actualPurpose.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td>地址：</td>
								<td colspan="3">${actualPurpose.address}</td>
							</tr>
							<tr>
								<td>证件类型：</td>
								<td>${fns:getDictLabel(actualPurpose.cardType, 'card_type', '')}</td>
								<td>证件号码：</td>
								<td>${actualPurpose.cardNum}</td>
							</tr>
							<tr>
								<td>客户类型：</td>
								<td>${fns:getDictLabel(actualPurpose.customerType, 'customer_type', '')}</td>
								<td>法定代表人：</td>
								<td>${actualPurpose.surety}</td>
							</tr>
							<tr>
								<td>借款笔数：</td>
								<td>${actualPurpose.loanNum}</td>
								<td>借款金额：</td>
								<td>${actualPurpose.loanAmount}</td>
							</tr>
							<tr>
								<td>结欠笔数：</td>
								<td>${actualPurpose.lackNum}</td>
								<td>结欠金额：</td>
								<td>${actualPurpose.lackAmount}</td>
							</tr>
							<tr>
								<td>不良贷款笔数：</td>
								<td>${actualPurpose.badNum}</td>
								<td>不良贷款金额：</td>
								<td>${actualPurpose.badAmount}</td>
							</tr>
							<tr>
								<td>结欠利息：</td>
								<td>${actualPurpose.badLixi}</td>
								<td>结息截止日期：：</td>
								<td>
									<fmt:formatDate value="${actualPurpose.lixiEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td>申请时间：</td>
								<td>
									<fmt:formatDate value="${actualPurpose.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td>结束日期：</td>
								<td>
									<fmt:formatDate value="${actualPurpose.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
							</tr>
							<tr>
								<td>检查金额：</td>
								<td>
									${actualPurpose.checkAmount}
								</td>
								<td>检查笔数：</td>
								<td>
									${actualPurpose.checkNum}
								</td>
							</tr>
							<tr>
								<td>其中：不良贷款金额：</td>
								<td>${actualPurpose.checkBadAmount}</td>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>贷款使用情况：</td>
								<td colspan="3">${actualPurpose.usages }</td>
							</tr>
							<tr>
								<td>经营状况分析：</td>
								<td colspan="3">${actualPurpose.runAnalysis }</td>
							</tr>
							<tr>
								<td>还款意愿分析：</td>
								<td colspan="3">${actualPurpose.hkyyAnalysis }</td>
							</tr>
							<tr>
								<td>偿债能力分析：</td>
								<td colspan="3">${actualPurpose.cznlAnalysis }</td>
							</tr>
							<tr>
								<td>其他影响偿还贷款的因素分析：</td>
								<td colspan="3">${actualPurpose.otherAnalysis }</td>
							</tr>
							<tr>
								<td>代偿能力分析：</td>
								<td colspan="3">${actualPurpose.tcnlAnalysis }</td>
							</tr>
							<tr>
								<td>代偿意愿分析：</td>
								<td colspan="3">${actualPurpose.dcyyAnalysis }</td>
							</tr>
							<tr>
								<td>保证效力分析：</td>
								<td colspan="3">${actualPurpose.bzxlAnalysis }</td>
							</tr>
							<tr>
								<td>变现能力分析：</td>
								<td colspan="3">${actualPurpose.bxnlAnalysis }</td>
							</tr>
							<tr>
								<td>权属状况分析：</td>
								<td colspan="3">${actualPurpose.qszkAnalysis }</td>
							</tr>
							<tr>
								<td>担保品价值分析：</td>
								<td colspan="3">${actualPurpose.jzAnalysis}</td>
							</tr>
							<tr>
								<td>担保品现状分析：</td>
								<td colspan="3">${actualPurpose.xzAnalysis }</td>
							</tr>
							<tr>
								<td>其他影响执行担保的因素分析：</td>
								<td colspan="3">${actualPurpose.dbqtAnalysis }</td>
							</tr>
							<tr>
								<td>司法纠纷和经济纠纷分析：</td>
								<td colspan="3">${actualPurpose.lawAnalysis }</td>
							</tr>
							<%-- <tr>
								<td>检查负责人意见：</td>
								<td colspan="3">${actualPurpose.jcrIdea }</td>
							</tr>
							<tr>
								<td>负责人意见：</td>
								<td colspan="3">
									<form:input path="fzrIdea" htmlEscape="false" maxlength="255" class="input-xlarge " />
								</td>
							</tr> --%>
							<tr>
								<td>您的意见：</td>
								<td colspan="3">
									<form:textarea path="act.comment" htmlEscape="false" rows="4" maxlength="20000" class="input-xxlarge " />
								</td>
							</tr>
							<tr>
								<td>状态：</td>
								<td>待提交</td>
								<td>是否提醒：</td>
								<td>
									${fns:getDictLabel(actualPurpose.isRead, 'ReimReadStatus', '')}
								</td>
							</tr>
							<tr>
								<td colspan="4">
									<div id="${nid}filelist" class="" align="left"></div>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
		<div class="form-actions">
			<c:if test="${edit}">
			<shiro:hasPermission name="postlending:usetracking:actualPurpose:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#flag').val('yes')"/>&nbsp;
			<input id="btnCanSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#flag').val('no')"/>&nbsp;
			<input id="stopSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#flag').val('stop')"/>&nbsp;
			</shiro:hasPermission>
			</c:if>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
		<c:if test="${not empty actualPurpose.id}">
			<act:histoicFlow procInsId="${actualPurpose.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>