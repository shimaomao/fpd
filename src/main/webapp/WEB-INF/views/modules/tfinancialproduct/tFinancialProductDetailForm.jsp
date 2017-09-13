<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>理财产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
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
			
			$("#btnSubmit").click(function(){
				var yesOrNo = $("input[name='yesOrNo']").val();
				alert(yesOrNo);
					return false;
			});
		});
		
		//附件图片
		function updateImg(){
			var financialProductId = $("#id").val();
			top.$.jBox.open(
					"iframe:${ctx}/tfinancialproduct/tFinancialProduct/showImgList?id="+financialProductId, "附件材料处理",850,385,
					{
						buttons : {
							"确定" : "return",
						},
						bottomText : "",
						submit : function(v, h, f) {
							var ifrWin = h.find("iframe")[0].contentWindow;
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y","hidden");
						},
						closed : function(){}
					});
		};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tfinancialproduct/tFinancialProduct/">理财产品列表</a></li>
		<li class="active"><a href="${ctx}/tfinancialproduct/tFinancialProduct/detail?id=${tFinancialProduct.id}">理财产品详情</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tFinancialProduct" action="${ctx}/tfinancialproduct/tFinancialProduct/auditsave" method="post" class="form-horizontal" >
		<form:hidden path="id"/>
		<form:hidden path="loancontractIds" id="loancontractIds"/>
		<form:hidden path="shiConversion"/>
		<form:hidden path="progress"/>
		<form:hidden path="kemoney"/>
		<form:hidden path="zrmoney"/>
		<form:hidden path="rzstatus" />
		<form:hidden path="status"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><h3>产品基本信息</h3></label>
			<div class="controls" >
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品分类：</label>
			<div class="controls">
				<form:select path="productType" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('RzproductType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="50" class="input-xlarge required" disabled="true"/>(产品标题名称长度至少3个字符，最长20个字符)
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">融资期限：</label>
			<div class="controls">
				<form:input path="limitTime" htmlEscape="false" maxlength="10" class="input-xlarge required" disabled="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期限单位：</label>
			<div class="controls">
				<form:radiobuttons path="danwei" items="${fns:getDictList('financial_period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" disabled="true"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化率：</label>
			<div class="controls">
				<form:input path="yearConversion" htmlEscape="false" class="input-xlarge required" disabled="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买截止日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tFinancialProduct.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" disabled="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品说明：</label>
			<div class="controls">
				<form:textarea path="productDetail" htmlEscape="false" rows="4" class="input-xxlarge " disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><h3>债权信息</h3></label>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><h5 onclick="selectLoan()">资产包管理</h5></label>
			   <table id="contentTable" class="table table-center table-striped table-bordered table-condensed" style="width: 60%">
					<thead>
						<tr>
							<th>合同编号</th>
							<th>客户姓名</th>
							<th>产品名称</th>
							<th>贷款金额 (元)</th>
							<th>贷款期限</th>
							<th> 贷款利率(%)</th>
							<th>申请日期</th>
							<th>贷款方式</th>
							<th>状态</th>
						</tr>
					</thead>
				<tbody>
					<c:forEach items="${loanList}" var="tLoanContract">
						<tr>
							<td>
								${tLoanContract.contractNumber}
							</a>
							</td>
							<td>
								${tLoanContract.customerName}
							</td>
							<td>
								${tLoanContract.productname}
							</td>
							<td>
								<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
							</td>
							<td>
								${tLoanContract.loanPeriod}(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
							</td>
							<td>
								${tLoanContract.loanRate}(${tLoanContract.loanRateType})
							</td>
							<td>
								<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>
								${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
							</td>
							<td>
								${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
				<form:input path="amount" id="amount" htmlEscape="false" class="input-xlarge " readonly="true"/>元（债权资产总和，对应的债权信息会展示给投资人）
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证明材料：</label>
			<div class="controls" style="padding-top: 8px;">
				共<font id="zmTotal" color="red">${tFinancialProduct.materialTotal}</font>张，已处理<font id="zmDeal" color="red">${tFinancialProduct.materialDeal}</font>张&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><a href="javascript:void(0)" onclick="updateImg()">查看材料</a></strong>
			</div>
		</div>
		<br>
		<div class="control-group">
			<label class="control-label"><h3>限制条件</h3></label>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发行对象：</label>
			<div class="controls">
				<%-- <form:select path="releasesObje" class="input-xlarge required"  disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				 <form:checkboxes path="ReleasesObjeList" items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="buyAmountMin" htmlEscape="false" class="input-xlarge required" disabled="true"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高购买金额：</label>
			<div class="controls">
				<form:input path="buyAmountMax" htmlEscape="false" class="input-xlarge required" disabled="true"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可转让：</label>
			<div class="controls">
				<form:radiobuttons path="ifRansfer" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" disabled="true"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(投资人在投资期限内是否允许将债权（收益权）转让给平台的其他用户。)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转让限定期：</label>
			<div class="controls">
				<form:input path="transferLimit" htmlEscape="false" maxlength="10" class="input-xlarge " disabled="true"/>日(从开始起息到转让限定期结束之前，不允许转让。)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转让收费费率：</label>
			<div class="controls">
				<form:input path="rateDiscount" htmlEscape="false" maxlength="10" class="input-xlarge " disabled="true"/>%(手续费 = 转让的本金 × 转让手续费费率)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回款方式：</label>
			<div class="controls">
				<form:select path="repayType" class="input-xlarge "  disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		  <div class="control-group">
			<label class="control-label">逾期罚息利率(天)：</label>
			<div class="controls">
				<form:input path="yuqiFree" htmlEscape="false" maxlength="10" class="input-xlarge " disabled="true"/>%(违约费用（未还本金*预设违约利率*时长），时长按天算)
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label"><h3>审核意见</h3></label>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">审核状态：</label>
			<div class="controls" style="padding-top:8px;">
					${fns:getDictLabel(tFinancialProduct.status, 'Productstatus', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注（审核意见）：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "  disabled="true"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>