<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="edit" value="${creditApply.act.status == 'finish' ? true : false }" />
<html>
<head>
	<title>授信申请</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
	require(['helper/api'], function(api){
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(customer)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
			var creditApplyTab = "${creditApply.tab}";
			console.info("creditApplyTab="+creditApplyTab);
			if((creditApplyTab == undefined) || (creditApplyTab == null) || (creditApplyTab == '')){
				showTab("${param.tab}");
			}else{
				showTab(creditApplyTab);
			}
			
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
			
			$("#reportForm").validate({
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
			<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=80&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
		    $("#${nid}filelist").load(url); --%>
		    
		    var HTCkeditor = CKEDITOR.replace("reportCulture.ftlContent");
		    var ifEdit = "${ifEdit}";
		    console.info("ifEdit="+ifEdit);
		    if(ifEdit == "edit"){
		    	//console.info("可以编辑");
		    	var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
			    $("#${nid}filelist").load(url);
		    }else{
		    	//console.info("不可以编辑");
		    	disableForm("reportForm",true);//设置id为reportForm的不可编辑
		    	CKEDITOR.config.readOnly = true;//设置只读
		    	
		    	var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=view&nid=${nid}file";
			    $("#${nid}filelist").load(url);
		    }
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
	    <li class="report"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=report">调查报告</a></li>
		<li class="lc"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=lc">申请/审批</a></li>
	</ul><br/> 
	
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
	</div>
	<div id="report" class="nav-tab-con" style="display:none;">
		<!-- 调查报告内容 -->
		<form:form id="reportForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/saveReport" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="customerId" />
			<form:hidden path="customerName" />
			<form:hidden path="customerType" /> 
			<form:hidden path="productId" />
			<form:hidden path="productname" /> 
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden path="act.businessTable"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>
			<!--  -->
			<form:hidden path="tab"/>
			<form:hidden path="reportCulture.id"/>
			<form:hidden path="reportCulture.creditApplyId"/>
			<form:hidden path="reportCulture.tplCode"/>
			<form:hidden path="reportCulture.status"/>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="reportCulture.department" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款人：</label>
			<div class="controls">
				<form:input path="reportCulture.name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户经理：</label>
			<div class="controls">
				<form:input path="reportCulture.salesman" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理日期：</label>
			<div class="controls">
				<input name="reportCulture.acceptDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${creditApply.reportCulture.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信金额：</label>
			<div class="controls">
				<form:input path="reportCulture.creditMoney" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">授信期限：</label>
			<div class="controls">
				<form:input path="reportCulture.creditPeriod" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
				月<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年利率：</label>
			<div class="controls">
				<form:input path="reportCulture.rate" htmlEscape="false" class="input-xlarge required number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款金额：</label>
			<div class="controls">
				<form:input path="reportCulture.loanMoney" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">贷款期限：</label>
			<div class="controls">
				<form:input path="reportCulture.loanPeriod" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
				月<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">咨询费：</label>
			<div class="controls">
				<form:input path="reportCulture.consultingFee" htmlEscape="false" class="input-xlarge required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:radiobuttons path="reportCulture.repayType" items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务来源：</label>
			<div class="controls">
				<%-- <form:input path="reportCulture.businessSource" htmlEscape="false" maxlength="5" class="input-xlarge required"/> --%>
				<form:select path="reportCulture.businessSource" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nk_business_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">调查报告内容：</label>
			<div class="controls">
				<%-- <form:input path="reportCulture.ftlContent" htmlEscape="false" class="input-xlarge "/> --%>
				<textarea id="reportCulture.ftlContent" name="reportCulture.ftlContent" rows="4" maxlength="200" class="input-xxlarge required" >${creditApply.reportCulture.ftlContent }</textarea>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="reportCulture.status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
		<div class="form-actions">
			<shiro:hasPermission name="credit:creditApply:edit">
				<input id="reportSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">相关附件：</label>
			<div class="control-group" style="height: 250px;">
				<div id="${nid}filelist" style="height: 300px;"></div>
			</div>
		</div>
		</form:form>
		<!-- 调查报告内容 -->
	</div>
	<div id="lc" class="nav-tab-con"  style="display:none;">
		<form:form id="inputForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="customerId" />
			<form:hidden path="customerName" />
			<form:hidden path="customerType" /> 
			<form:hidden path="productId" />
			<form:hidden path="productname" /> 
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
				<label class="control-label">申请授信额度：</label>
				<div class="controls">
					<form:input path="credit" htmlEscape="false" class="input-large required number" disabled="${(empty creditApply.act.procInsId ? false : true) && (empty subType || subType != 'start')}"/>
					<span class="help-inline"><font color="red">*(如已经有授信额度，则旧的额度会被更新)</font> 
					<!-- <a href="javascript:void(0);" id="getfkcredit">获取风控授信额度>></a>&nbsp;<font color="red" id="fkcredit"></font></span> -->
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">授信额度有效截止日期：</label>
				<div class="controls">
					<input name="expirationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						<%-- ${empty creditApply.act.procInsId ? false : true ? '' : 'disabled="disabled"'} --%>
						<c:if test="${empty creditApply.act.procInsId ? false : true && (empty subType || subType != 'start')}">
							disabled="disabled"
						</c:if>
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
					<form:textarea path="act.comment" class="required" rows="5" maxlength="3800" cssStyle="width:500px"/>
					<span class="help-inline"><font color="red">*</font>
				</div>
			</div>
			<c:if test="${!edit && !empty creditApply.act.procInsId}">
				<c:if test="${creditApply.act.status == 'todo' && !empty subType && subType == 'submit'}">
					<div class="control-group">
						<label class="control-label">评审会意见：</label>
						<div class="controls">
							投票总人数：${creditApply.apprTotal}；&nbsp;同意授信人数：${creditApply.apprAgree}；&nbsp;不同意授信人数：${creditApply.apprDisAgree}
						</div>
					</div>
				</c:if>
			</c:if>
			<%-- <div class="control-group">
				<label class="control-label">相关附件：</label>
				<div class="control-group" style="height: 200px;">
					<div id="${nid}filelist" style="height: 250px;"></div>
				</div>
			</div> --%>
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
					  <%-- <c:if test="${creditApply.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="不同意" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
					  </c:if> --%>
					  <c:if test="${creditApply.act.status == 'todo'}">
						  <c:choose>
						  	<c:when test="${!empty subType && subType == 'submit'}">
						  		<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交" onclick="$('#pass').val('1');$('#flag').val('1');"/>
						  	</c:when>
						  	<c:when test="${!empty subType && subType == 'two'}">
						  		<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('3');$('#flag').val('3');"/>&nbsp;
						    	<input id="btnSubmit" class="btn btn-inverse" type="submit" value="不同意" onclick="$('#pass').val('2');$('#flag').val('2');"/>&nbsp;
						  	</c:when>
						  	<c:when test="${!empty subType && subType == 'start'}">
						  		<input id="btnSubmit" class="btn btn-primary" type="submit" value="重新申请"/>&nbsp;
						    	<input id="btnSubmit" class="btn btn-inverse" type="submit" value="撤销申请" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
						  	</c:when>
						  	<c:otherwise>
						  		<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
						    	<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回修改" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
						    	<input id="btnSubmit" class="btn btn-inverse" type="submit" value="拒绝" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
						  	</c:otherwise>
						  </c:choose>
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