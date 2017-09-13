<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" %>
<c:set var="edit" value="${actualPurpose.act.status == 'finish' ? false : true }" />
<c:set var="company" value="<%=Cons.CustomerType.CUST_COMPANY_TXT %>"/><!-- 企业客户 -->
<html>
<head>
<title>贷后检查管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			/* $("#inputApplyForm").validate({//del by #2895
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
			}); */
			
			$("#btnApplyCancel").click(function(){
				top.$.jBox.close(true);
			});
			
			$("#btnApplySubmit").click(function(){// #2895
				$("#inputApplyForm").validate({
					submitHandler: function(form){
						alert("提交申请验证通过");
						$('#flag').val('yes');
						 $(form).ajaxSubmit({
						        type:"post",
						        url:"${ctx}/postlending/usetracking/actualPurpose/save",
						        //beforeSubmit: showRequest,
						        success: function(data, textStatus) {
									alert(data.msg);
									top.$.jBox.close(true);
								}
						      });
						return false;
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
				//alert("提交申请");  //old #2895
				/* $('#flag').val('yes');
				 var options = {
							url : '${ctx}/postlending/usetracking/actualPurpose/save',
							type : 'post',
							dataType : 'json',//'text',
							data : $("#inputApplyForm").serializeArray(),
							success : function(data, textStatus) {
								alert(data.msg);
								top.$.jBox.close(true);
							}
						};
						$.ajax(options);
						return false; */
			});
			
			//附件
			var url = "${ctx}/files/tContractFiles/showfilelist/${actualPurpose.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
						$("#${nid}filelist").load(url);
					});
</script>
</head>
<body>
	<form:form id="inputApplyForm" modelAttribute="actualPurpose" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />

		<form:hidden path="customerId" />
		<form:hidden path="customerName" />
		<form:hidden path="customerType" />
		<form:hidden path="loanContractId" />
		<form:hidden path="contractNumber" />
		<sys:message content="${message}" />
		<table cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td>
						<h3 align="center">检查申请</h3>
						<table id="contentTable"
							class="table table-striped table-bordered table-condensed">
							<tbody>
							<tr>
								<td>标题：</td>
								<td colspan="3"><form:input path="title" htmlEscape="false" maxlength="255" class="input-xlarge required" /><span class="help-inline"><font color="red">*</font> </span></td>
								
							</tr>
							<tr>
								<td>合同编号：</td>
								<td>${actualPurpose.contractNumber}</td>
								<td>贷款用途：</td>
								<td><form:input path="purpose" htmlEscape="false" maxlength="255" class="input-xlarge " /></td>
							</tr>
							<tr>
								<td>用途跟踪内容：</td>
								<td colspan="3">
									<form:textarea path="content" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required" />
									<span class="help-inline"><font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td>用户名称：</td>
								<td>${actualPurpose.customerName}</td>
								<td>申请时间：</td>
								<td>
									<input name="applyDate" type="text" readonly="readonly"
										maxlength="20" class="input-medium Wdate required"
										value="<fmt:formatDate value="${actualPurpose.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /><span class="help-inline"><font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td>地址：</td>
								<td colspan="3">
									<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge " />
								</td>
							</tr>
							<tr>
								<td>证件类型：</td>
								<td><input name="cardType" value="${fns:getDictLabel(actualPurpose.cardType, 'card_type', '')}" readonly="readonly"/></td>
								<td>证件号码：</td>
								<td><form:input path="cardNum" htmlEscape="false" maxlength="255" class="input-xlarge required" /><span class="help-inline"><font color="red">*</font></td>
							</tr>
							<tr>
								<td>客户类型：</td>
								<td>${fns:getDictLabel(actualPurpose.customerType, 'customer_type', '')}</td>
								<td><c:if test="${fns:getDictLabel(actualPurpose.customerType, 'customer_type', '') eq company}">法定代表人：</c:if></td>
								<td><c:if test="${fns:getDictLabel(actualPurpose.customerType, 'customer_type', '') eq company}"><form:input path="surety" htmlEscape="false" maxlength="255" class="input-xlarge required" /><span class="help-inline"><font color="red">*</font></c:if></td>
							</tr>
							<tr>
								<td>借款笔数：</td>
								<td><form:input path="loanNum" htmlEscape="false" maxlength="11" class="input-xlarge digits required" /><span class="help-inline"><font color="red">*</font></td>
								<td>借款金额：</td>
								<td><form:input path="loanAmount" htmlEscape="false" class="input-xlarge number required" /><span class="help-inline"><font color="red">*</font></td>
							</tr>
							<tr>
								<td>结欠笔数：</td>
								<td><form:input path="lackNum" htmlEscape="false" maxlength="11" class="input-xlarge digits required" /><span class="help-inline"><font color="red">*</font></td>
								<td>结欠金额：</td>
								<td><form:input path="lackAmount" htmlEscape="false" class="input-xlarge number required" /><span class="help-inline"><font color="red">*</font></td>
							</tr>
							<tr>
								<td>不良贷款笔数：</td>
								<td><form:input path="badNum" htmlEscape="false" maxlength="11" class="input-xlarge digits required" /><span class="help-inline"><font color="red">*</font></td>
								<td>不良贷款金额：</td>
								<td><form:input path="badAmount" htmlEscape="false" class="input-xlarge  number required" /><span class="help-inline"><font color="red">*</font></td>
							</tr>
							<tr>
								<td>结欠利息：</td>
								<td><form:input path="badLixi" htmlEscape="false" class="input-xlarge number" /></td>
								<td>结息截止日期：</td>
								<td><input name="lixiEndDate" type="text" readonly="readonly" maxlength="20"
										class="input-medium Wdate required"
										value="<fmt:formatDate value="${actualPurpose.lixiEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /></td>
							</tr>
							<tr>
								<td>检查日期：</td>
								<td><input name="insertTime" type="text" readonly="readonly"
									maxlength="20" class="input-medium Wdate required"
									value="<fmt:formatDate value="${actualPurpose.insertTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /><span class="help-inline"><font color="red">*</font>
								</td>
<!-- 								<td>结束日期：</td> -->
<!-- 								<td><input name="endDate" type="text" readonly="readonly" maxlength="20" -->
<!-- 									class="input-medium Wdate " -->
<%-- 									value="<fmt:formatDate value="${actualPurpose.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" --%>
<!-- 									onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" /> -->
<!-- 								</td> -->
							      <td>检查金额：</td>
								<td><form:input path="checkAmount" htmlEscape="false" class="input-xlarge  number required" /><span class="help-inline"><font color="red">*</font></td>
							      
							</tr>
							<tr>
								<td>检查笔数：</td>
								<td><form:input path="checkNum" htmlEscape="false" maxlength="11" class="input-xlarge  digits required" /><span class="help-inline"><font color="red">*</font></td>
							   <td>其中：不良贷款金额：</td>
								<td><form:input path="checkBadAmount" htmlEscape="false" class="input-xlarge  number required" /><span class="help-inline"><font color="red">*</font></td>
							   
							</tr>
							<tr>
								<td>贷款使用情况：</td>
								<td colspan="3">
									<form:textarea path="usages" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>经营状况分析：</td>
								<td colspan="3">
									<form:textarea path="runAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>还款意愿分析：</td>
								<td colspan="3">
									<form:textarea path="hkyyAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>偿债能力分析：</td>
								<td colspan="3">
									<form:textarea path="cznlAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>其他影响偿还贷款的因素分析：</td>
								<td colspan="3">
									<form:textarea path="otherAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>代偿能力分析：</td>
								<td colspan="3">
									<form:textarea path="tcnlAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>代偿意愿分析：</td>
								<td colspan="3">
									<form:textarea path="dcyyAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>保证效力分析：</td>
								<td colspan="3">
									<form:textarea path="bzxlAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>变现能力分析：</td>
								<td colspan="3">
									<form:textarea path="bxnlAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>权属状况分析：</td>
								<td colspan="3">
									<form:textarea path="qszkAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>担保品价值分析：</td>
								<td colspan="3">
									<form:textarea path="jzAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>担保品现状分析：</td>
								<td colspan="3">
									<form:textarea path="xzAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>其他影响执行担保的因素分析：</td>
								<td colspan="3">
									<form:textarea path="dbqtAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
								</td>
							</tr>
							<tr>
								<td>司法纠纷和经济纠纷分析：</td>
								<td colspan="3">
									<form:textarea path="lawAnalysis" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " />
								</td>
							</tr>
							<%-- <tr>
								<td>检查负责人意见：</td>
								<td colspan="3">
									<form:textarea path="jcrIdea" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " />
								</td>
							</tr> --%>
							<tr>
								<td>您的意见：</td>
								<td colspan="3">
									<form:textarea path="act.comment" htmlEscape="false" rows="4" maxlength="20000" class="input-xxlarge required" />
									<span class="help-inline"><font color="red">*</font>
								</td>
							</tr>
							<tr>
								<td>状态：</td>
								<td>待提交</td>
								<td>是否提醒：</td>
								<td>
									<form:select path="isRead" class="input-xlarge required">
										<form:option value="" label="" />
										<form:options items="${fns:getDictList('ReimReadStatus')}"
											itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select>
									<span class="help-inline"><font color="red">*</font>
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
		<%-- <div class="control-group">
			<label class="control-label">负责人意见：</label>
			<div class="controls">
				<form:input path="fzrIdea" htmlEscape="false" maxlength="255"
					class="input-xlarge " />
			</div>
		</div> --%>
		<div class="form-actions"  align="center">
			<c:if test="${edit}">
			<shiro:hasPermission
				name="postlending:usetracking:actualPurpose:edit">
				<input id="btnApplySubmit" class="btn btn-primary" type="submit"
					value="提 交" />&nbsp;</shiro:hasPermission>
			</c:if>
			<input id="btnApplyCancel" class="btn" type="button" value="返 回" />
		</div>
		<c:if test="${not empty actualPurpose.id}">
			<act:histoicFlow procInsId="${actualPurpose.act.procInsId}" />
		</c:if>
	</form:form>
</body>
</html>