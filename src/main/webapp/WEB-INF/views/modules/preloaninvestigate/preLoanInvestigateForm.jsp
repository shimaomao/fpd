<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>立项调查管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
		$(document).ready(function() {
			var HTCkeditor = CKEDITOR.replace("content");
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
			
			var str = '${preLoanInvestigate.loantype}';
            var chk_value =[];   
			$("input[name='loantype']").each(function(){    
				   chk_value.push($(this).val()); 
				   if(str.indexOf($(this).val())>=0){
					    document.getElementById($(this).attr("id")).checked = true;
				   }
			});  
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/preloaninvestigate/preLoanInvestigate/">立项调查列表</a></li>
		<li class="active"><a href="${ctx}/preloaninvestigate/preLoanInvestigate/form?id=${preLoanInvestigate.id}">立项调查<shiro:hasPermission name="preloaninvestigate:preLoanInvestigate:edit">${not empty preLoanInvestigate.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="preloaninvestigate:preLoanInvestigate:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="preLoanInvestigate" action="${ctx}/preloaninvestigate/preLoanInvestigate/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">立项名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">立项编号</label>
			<div class="controls">
				   <form:input path="investigateNumber" id="investigateNumber" htmlEscape="false" class="input-xlarge required"/>
				   <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">客户名称：</label>
			<div class="controls">
				 <sys:treeselect  id="customer" isAll="false" name="customerId" value="${preLoanInvestigate.customerId}" labelName="customerName" labelValue="${preLoanInvestigate.customerName}"
						parentName="customerType" parentValue="${preLoanInvestigate.customerType}" title="客户" url="/company/tCompany/treeData" allowClear="true" notAllowSelectParent="true"/>

				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户联系电话</label>
			<div class="controls">
				   <form:input path="customerPhone" id="customerPhone" htmlEscape="false" class="input-xlarge" />
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">
                <c:if test="${fns:getUser().company.primaryPerson==danbao}">
                                                                 担保金额  
                </c:if>
                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
                                                                 贷款金额
                </c:if>			     
			</label>         
			<div class="controls">
				 <form:input path="loanamount" id="loanamount" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">
                 <c:if test="${fns:getUser().company.primaryPerson==danbao}">
                                                                    担保费率(%)
                </c:if>
                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
                                                                 贷款利率(%)
                </c:if>	
             </label>
			<div class="controls">
				 <form:input path="rate" id="rate" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>			
		<div class="control-group">
			<label class="control-label">
                <c:if test="${fns:getUser().company.primaryPerson==danbao}">
                                                                 担保期限
                </c:if>
                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
                                                                 贷款期限
                </c:if>	
             </label>
			<div class="controls">
				 <form:input path="period" id="period" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">
                <c:if test="${fns:getUser().company.primaryPerson==danbao}">
                                                                 反担保方式
                </c:if>
                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
                                                               贷款方式
                </c:if>	
             </label>
			<div class="controls">
					<form:checkboxes path="loantype" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">贷款用途</label>
			<div class="controls">
                    <form:select path="purpose" class="input-xlarge ">
					   <form:option value="" label=""/>
					   <form:options items="${fns:getDictList('product_purpose')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>		
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">还款方式</label>
			<div class="controls">
					<form:radiobuttons path="paytype" items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onchange=""/>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">调查时间 ：</label>
			<div class="controls">
				   	<input name="investigaDate" id="investigaDate"  type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${preLoanInvestigate.investigaDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">调查报告：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge " style="width:80%;" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="preloaninvestigate:preLoanInvestigate:edit">
                  <!--  新增和审核失败的才可以进行修改保存 -->
			     <c:if test="${preLoanInvestigate.status==1||preLoanInvestigate.status==4||preLoanInvestigate.status==null}">
			        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			     </c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>