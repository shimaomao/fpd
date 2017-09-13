<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务激活申请</title>
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
			
			$("#category").change(function(){
				$("#bizCodeId").val("");
				$("#bizCodeName").val("");
			}).change();
		});
		
		//加载关联业务数据的参数
		function bizCodeExtendData(){
			return {
				category: $("#category").val()
			};
		}
		
		//回调方法
		function bizCodeTreeselectCallBack(v, h, f){
			$("#bizName").val($("#bizCodeName").val());
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/openBiz/feeBizList">付费业务列表</a></li>
		<li class="active"><a href="${ctx}/sys/openBiz/feeBizForm?id=${feeBiz.id}">添加付费业务</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="feeBiz" action="${ctx}/sys/openBiz/saveFeeBiz" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">业务类型：</label>
			<div class="controls">
				<form:select path="category" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('feebiz_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="bizName" htmlEscape="false" class="input-xlarge"/>
			</div>
		</div>
		
		<div class="control-group" id="linkBusiness">
			<label class="control-label">关联业务：</label>
			<div class="controls">
				<sys:treeselect  id="bizCode" isAll="false" name="bizCode" value="${tLoanContract.customerId}" labelName="bizName" labelValue="${feeBiz.bizName}"
						title="关联业务" url="/sys/openBiz/bizCodeTreeData" allowClear="true" notAllowSelectParent="true" />
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">单价（元）：</label>
			<div class="controls">
				<form:input path="unitPrice" htmlEscape="false" class="input-xlarge number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:feeBiz:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>