<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加担保信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1 || param.isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
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
		<li><a href="${ctx}/guarantee/tGuaranteeContract/">担保信息列表</a></li>
		<li class="active"><a href="${ctx}/guarantee/tGuaranteeContract/form?id=${tGuaranteeContract.id}">担保信息${not empty tGuaranteeContract.id?'修改':'添加'}</a></li>
	</ul>
 <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>保质抵押>保证>编辑
	   </div>
	</div>
	<br/>
	<br/> --%>
	<form:form id="inputForm" modelAttribute="tGuaranteeContract" action="${ctx}/guarantee/tGuaranteeContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="businessId"/>
		<form:hidden path="businessTable"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">担保合同编号：</label>
			<div class="controls">
				<form:input path="guaranteeNumber" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保日期：</label>
			<div class="controls">
				<input name="contractDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tGuaranteeContract.contractDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">保证金额：</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" class="input-xlarge  number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保期限：</label>
			<div class="controls">
				<form:input path="period" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
				<font color="red">*</font>
				<form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">保证方式：</label>
			<div class="controls">
				<form:select path="guaranteeType" class="input-xlarge">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('guarantee_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">证件类型：</label>
			<div class="controls">
				<form:select path="cardType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码：</label>
			<div class="controls">
				<form:input path="cardNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">与借款人的关系：</label>
			<div class="controls">
				<form:input path="relation" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		
		<%-- 
		<div class="control-group">
			<label class="control-label">担保费率：</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">担保费率类型：</label>
			<div class="controls">
				<form:input path="rateType" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资产价值：</label>
			<div class="controls">
				<form:input path="assetsWorth" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">主要资产：</label>
			<div class="controls">
				<form:input path="mainAssets" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主要范围及主要产品：</label>
			<div class="controls">
				<form:input path="post" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">法定代表人：</label>
			<div class="controls">
				<form:input path="surety" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">月营业额：</label>
			<div class="controls">
				<form:input path="turnover" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		 --%>
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="guarantee:tGuaranteeContract:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>