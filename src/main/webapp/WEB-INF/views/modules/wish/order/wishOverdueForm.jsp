<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>逾期金额管理</title>
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
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/wish/order/wishOverdue/">逾期金额列表</a></li>
    <li class="active"><a href="${ctx}/wish/order/wishOverdue/form?id=${wishOverdue.id}">逾期金额<shiro:hasPermission name="wish:order:wishOverdue:edit">${not empty wishOverdue.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wish:order:wishOverdue:edit">查看</shiro:lacksPermission></a></li>
</ul><br/>
<form:form id="inputForm" modelAttribute="wishOverdue" action="${ctx}/wish/order/wishOverdue/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">易联用户id：</label>
        <div class="controls">
            <form:input path="userId" htmlEscape="false" readonly="true"  maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">逾期金额：</label>
        <div class="controls">
            <form:input path="money" htmlEscape="false" readonly="true"  maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">贷款订单id：</label>
        <div class="controls">
            <form:input path="loanContractId" htmlEscape="false" readonly="true" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">逾期状态：</label>
        <div class="controls">
            <form:select path="status" class="input-medium">
                <form:option value="" label=""/>
                <form:options items="${fns:getDictList('wish_is_overdue')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wish:order:wishOverdue:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>