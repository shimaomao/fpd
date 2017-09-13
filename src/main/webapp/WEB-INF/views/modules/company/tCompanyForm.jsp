<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业客户管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				var data = ${fns:toJson(data)};
				var form = $("#target");
				var btn  = $("#submitViewForm");
				api.form.init(form, eval(data));
				form.attr("method", "post");
				var divWrap = form.find("#divWrap");
				if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
					form.append("<div id=\"divWrap\"></div>");
					divWrap = form.find("#divWrap");
				}
				divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				form.attr("target", "mainFrame");
				form.attr("action", ctx+"${action}");
				form.validate({
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
				$(btn).click(function(){
					form.submit();
				});
			});
		});
	</script>
</head>
<body>
    <sys:message content="${message}"/>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javasricpt:void(0);">客户基本信息</a></li>
          <!-- 修改的时候才显示可以添加其他信息 -->
		<c:if test="${fn:contains(data.id, 'new_')==false}">
			<li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${data.id}">高管\股东\实际控制人\其他人信息</a></li>
			<li><a href="${ctx}/customerrelevan/tCustomerRelevan/list?companyId=${data.id}">客户关联</a></li>
			<li><a href="${ctx}/customerintent/tCustomerIntent/list?companyId=${data.id}">意向调查</a></li>
			<li><a href="${ctx}/files/tContractFiles/list?taskId=${data.id}&type=customer_archives&mark=1">档案\财务资料</a></li>
<%--             <li><a href="${ctx}/tcompanyinfo/tCompanyInfor/list?companyId=${data.id}">财务报告</a></li> --%>
	        <li><a href="${ctx}/credit/customerCredit/list?customerId=${data.id}&type=2">授信记录</a></li>
		    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${data.id}&type=3">业务记录</a></li>
		    <li><a href="${ctx}/contract/tLoanContract/clist?customerId=${data.id}&type=4&status=9">不良记录</a></li>
		</c:if>
	</ul>
	<sys:message content="${message}"/>
    <div>
		<div>${dfFormTpl.parsehtml }</div>
		<div style="margin-bottom: 50px;margin-left: 135px;margin-top: 20px;">
			<input id="submitViewForm" type="button" class="btn btn-primary" value="保存" />
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</div>
</body>
</html>