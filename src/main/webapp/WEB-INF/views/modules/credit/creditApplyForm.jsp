<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${creditApply.act.status == 'finish' ? true : false }" />
<html>
<head>
	<title>授信申请</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api'], function(api){
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(customer)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
			showTab("${param.tab}");
			
			$("#getfkcredit").click(function(){
				$.post("${ctx}/credit/creditApply/getFkCredit", {"customerId":"${creditApply.customerId}"},
					function(data) {
						if(data.status == 1){
							if(data.credit){
								$("#fkcredit").text(data.credit+" 元");
							}else{
								$("#fkcredit").text("0.00 元");
							}
						}else{
							$("#fkcredit").text("0.00 元");
							alertx("征信接口："+data.message);
							console.info("征信系统消息："+data.message+"详情请F12查看控制台！");
							console.info("解决方式：方案1、需要去录入数据；方案2、征信系统开发接口拉取业务系统的数据处理（开发中）！");
						}
					}
				);
				
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
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
		    $("#${nid}filelist").load(url); --%>
		  
		});
	});
	
	function showTab(tab){
		if((tab == undefined) || (tab == null) || (tab == '')){
			tab = "bd";
		}
		$(tab).show();
		$("."+tab).addClass('active').siblings().removeClass('active');
		$("#"+tab).show().siblings('.nav-tab-con').hide();
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	    <li class="bd"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=bd">客户信息</a></li>
		<li class="lc"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=lc">申请/审批</a></li>
	</ul><br/> 
	
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
	</div>
	<div id="lc" class="nav-tab-con"  style="display:none;">
		<form:form id="inputForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="customerId" /> 
			<form:hidden path="customerType" /> 
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
	
			<div class="control-group">
				<label class="control-label">申请额度：</label>
				<div class="controls">
					<form:input path="credit" htmlEscape="false" class="input-large required number"/>
					<span class="help-inline"><font color="red">*</font> 
					<!-- <a href="javascript:void(0);" id="getfkcredit">获取风控授信额度>></a>&nbsp;<font color="red" id="fkcredit"></font></span> -->
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">授信额度有效截止日期：</label>
				<div class="controls">
					<input name="expirationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						<%-- ${empty creditApply.act.procInsId ? '' : 'disabled="disabled"'} --%>
						value="<fmt:formatDate value="${creditApply.expirationDate}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						<span class="help-inline"><font color="red">*</font> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">备注信息：</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " 
						disabled="${empty creditApply.act.procInsId ? false : true}" />
				</div>
			</div>
			<%-- <c:if test="${edit && not empty creditApply.act.procInsId}"> --%>
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
				</div>
			</div>
			<%-- <c:if test="${creditApply.act.isEnd ne 'yes'}">
			<div class="control-group">
			     <label class="control-label">审批人：</label>
                <div class="controls">
                
                       <form:hidden id="userIdId" path="act.assigneeName"/>
    	              <!--  <input id="userIdId" name="userIds" value="" type="hidden"> -->
		               <textarea  id="userIdName" name="" readonly="readonly" value="" checked="true" class="input-xlarge" onclick="openTreeSelectBox('userId','userId','审批人','','','','/sys/user/treeDataByCompany','sysUser');" type="text"></textarea>
		               <a id="userIdButton" href="javascript:"  checked="true" onclick="openTreeSelectBox('userId','userId','审批人','','','','/sys/user/treeDataByCompany','sysUser');" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
               </div>
    
             </div>
             </c:if> --%>
          <%--  <label class="control-label">相关附件${nid}：</label>
	       <div class="control-group" style="height: 500px;">
			     <div id="${nid}filelist" style="height: 550px;"></div>
		   </div> --%>
	
			<div class="form-actions">
				<shiro:hasPermission name="credit:creditApply:edit">
				          <!--  发起人发起时或者被驳回时 显示申请按钮 -->
					<c:if test="${!edit && empty creditApply.act.procInsId && creditApply.remarks != '自动授信'}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="申请"/>&nbsp;
					</c:if>
			
					<c:if test="${!edit && !empty creditApply.act.procInsId}">
					  <c:if test="${creditApply.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
					  </c:if>
					</c:if>
				<!-- 	<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
				</shiro:hasPermission>
			</div>
			<act:histoicFlow procInsId="${creditApply.act.procInsId}"/>
			</div>
			</div>
			<table class="table table-bordered">
          </table>
			
			
		</form:form>
	</div>
</body>
</html>