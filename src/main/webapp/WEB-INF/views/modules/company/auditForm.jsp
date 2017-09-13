<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="blackApplyKey" value="<%=ProcDefKey.BLACK_APPLY %>"/>
<c:set var="edit" value="${tCompany.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>加入黑名单审批</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				//初始化业务数据
				var data = ${fns:toJson(tCompany)};
				var form = $("#target");
				api.form.init(form, eval(data));
				//禁用form表单中所有的选项
				disableForm("target",true);
				
				//页签
				$('.nav-tabs').find('li').click(function(){
					$(this).addClass('active').siblings().removeClass('active');
					$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
				});
				
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!-- <li class="active"><a href="javasricpt:void(0);">个人客户信息</a></li>
		<li><a href="javasricpt:void(0);">申请/审批</a></li> -->
	    <li class="active"><a href="#" onclick="javascript:return false;">个人客户信息</a></li>
		<li><a href="#" onclick="javascript:return false;">申请/审批</a></li>
	</ul>
	<div class="nav-tab-con" >
		${dfFormTpl.parsehtml }
	</div>
	<div class="nav-tab-con" style="display:none;">
	<br/>
	<form:form id="inputForm" modelAttribute="tCompany" action="${ctx}/company/tCompany/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden path="act.businessTable"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="pass" name="act.vars.map['pass']"/>
		<sys:message content="${message}"/>		
		<c:if test="${tCompany.act.procDefKey != blackApplyKey}">
			<!-- 解除黑名单流程，显示黑名原因 -->
			<div class="control-group">
				<label class="control-label">黑名原因：</label>
				<div class="controls">
					<form:textarea path="reason" class="required"  rows="5" maxlength="250" cssStyle="width:500px"/>
				</div>
			</div>
		</c:if>
		
		<div class="control-group">
			<label class="control-label">您的意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
			</div>
		</div>
		<div class="form-actions">
			<c:if test="${edit }">
			<shiro:hasPermission name="contract:tLoanContract:edit">
				<c:if test="${empty tCompany.act.procInsId}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="申请"/>&nbsp;
				</c:if>
				<c:if test="${!empty tCompany.act.procInsId}">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
				<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			</c:if>
		</div>
		<act:histoicFlow procInsId="${tCompany.act.procInsId}"/>
	</form:form>
	</div>
</body>
</html>