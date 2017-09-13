<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <c:set var="nid" value="loanContractForm" /> --%>
<html>                  
<head>
	<title>签订合同</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
	require(['helper/api','app/repayPlan'], function(api,rp){
		
		
		$(function(){
			//info的时候才需要获取还款信息
			var tab = "${param.tab}";
			//console.info("int tab="+tab);
			if(tab === "files" || tab === "lc"){
				
			}else{
				//还款计划--
				rp.initialize({
					businessId : "${contractAudit.loanContract.id}",
					businessType : "apply",  //apply|extend|earlyrepay...
					amount : "${contractAudit.loanContract.loanAmount}",
					loanRate : "${contractAudit.loanContract.loanRate}",
					loanRateType : "${contractAudit.loanContract.loanRateType}",
					loanPeriod : "${contractAudit.loanContract.loanPeriod}",
					loanDate : '<fmt:formatDate value="${contractAudit.loanContract.loanDate}" pattern="yyyy-MM-dd"/>',
					payType : "${contractAudit.loanContract.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
					periodType : "${contractAudit.loanContract.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
					payDay : "${contractAudit.loanContract.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
					payOptions : "${contractAudit.loanContract.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
					ifRealityDay:"${contractAudit.loanContract.ifRealityDay}"
				},"#showplansDiv");
				
				var data = ${fns:toJson(contractAudit.loanContract)};
				var form = $("#target");
				api.form.init(form, eval(data));
				//禁用form表单中所有的选项
				disableForm("target",true);
			}
			//初始化业务数据
			
			showTab("${param.tab}");
			
			$("#inputFormContract").validate({
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
			$("#inputFormSign").validate({
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
			
			var ifEdit = '${ifEdit}'
			console.info("ifEdit="+ifEdit);
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			var url = "${ctx}/files/tContractFiles/showfilelist/${contractAudit.loanContract.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
			$("#${nid}filelist").load(url);
			//if(ifEdit == "edit"){
				//富文本内容
				HTCkeditor = CKEDITOR.replace("contractContent",{height: '300px', width: '1000px'});
				//所有人都要有权限上传附件，故下屏蔽
				<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${contractAudit.loanContract.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
			    $("#${nid}filelist").load(url);
			}else if(ifEdit == "upload"){
				var url = "${ctx}/files/tContractFiles/showfilelist/${contractAudit.loanContract.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit&nid=${nid}file";
			    $("#${nid}filelist").load(url);
			}else{
				var url = "${ctx}/files/tContractFiles/showfilelist/${contractAudit.loanContract.id}.html?height=350&businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=view&nid=${nid}file";
		   		$("#${nid}filelist").load(url); --%>
			//}
		});
	});
	
	
	function showTab(tab){
		if((tab == undefined) || (tab == null) || (tab == '')){
			tab = "info";
		}
		//console.info("tab="+tab);
		$(tab).show();
		$("."+tab).addClass('active').siblings().removeClass('active');
		$("#"+tab).show().siblings('.nav-tab-con').hide();
	};
	//通过模板导入编辑
	function toAddContract(contractTplId, loanContractId){
		//console.info("合同模板id="+contractTplId);
		if(contractTplId && contractTplId.replace(/(^s*)|(s*$)/g, "").length > 0){
			$.post("${ctx}/contract/tpl/tContractTpl/getTContractTpl",{"id":contractTplId, "loanContractId":loanContractId},
				function(data){
					//console.info("data.status="+data.status);
					if(data.status ===1){
						//console.info("data.contractTpl="+data.contractTpl);
						//console.info("data.contractTpl.id="+data.contractTpl.id);
						//console.info("data.contractTpl.type="+data.contractTpl.type);
						//console.info("data.contractTpl.createName="+data.contractTpl.createName);
						//console.info("data.contractTpl.ftlStatus="+data.contractTpl.ftlStatus);
						//console.info("============================");
						//console.info("data.contractTpl.ftlContent="+data.contractTpl.ftlContent);
						//console.info("============================");
						
						//$("#bc_id").val(data.contractTpl);//合同ID
						$("#bc_contractName").val(data.contractTpl.createName);//合同名称
						$("#bc_tplId").val(data.contractTpl.id);//模板ID
						$("#bc_contractType").val(data.contractTpl.type);//合同类型
						$("#bc_crosswise").val(data.contractTpl.crosswise);//是否横向
						//$("#bc_loanContractId").val(data.contractTpl);//业务ID
						//$("#bc_contractAuditId").val(data.contractTpl);//合同审核表ID
						//$("#bc_organId").val(data.contractTpl);//租户
						HTCkeditor.setData(data.contractTpl.ftlContent);//合同内容
					}else{
						alertx(data.message);
					}
				}
			);
		}
	};
	//修改已经生成的合同
	function toEditContract(businessContractId){
		//console.info("生成合同id="+businessContractId);
		if(businessContractId && businessContractId.replace(/(^s*)|(s*$)/g, "").length > 0){
			$.post("${ctx}/contract/businessContract/getBusinessContract",{"id":businessContractId},
					function(data){
						//console.info("data.status="+data.status);
						if(data.status ===1){
							//console.info("============================");
							//console.info("data.businessContract.id="+data.businessContract.id);
							//console.info("data.businessContract.contractName="+data.businessContract.contractName);
							//console.info("data.businessContract.tplId="+data.businessContract.tplId);
							//console.info("data.businessContract.contractType="+data.businessContract.contractType);
							//console.info("data.businessContract.loanContract.id="+data.businessContract.loanContract.id);
							//console.info("data.businessContract.contractAuditId="+data.businessContract.contractAuditId);
							//console.info("data.businessContract.organId="+data.businessContract.organId);
							//console.info("----------------------------");
							//console.info("data.businessContract.ftlContent="+data.businessContract.contractContent);
							//console.info("============================");
							
							$("#bc_id").val(data.businessContract.id);//合同ID
							$("#bc_contractName").val(data.businessContract.contractName);//合同名称
							$("#bc_tplId").val(data.businessContract.tplId);//模板ID
							$("#bc_crosswise").val(data.businessContract.crosswise);//是否横向
							$("#bc_contractType").val(data.businessContract.contractType);//合同类型
							$("#bc_loanContractId").val(data.businessContract.loanContract.id);//业务ID
							$("#bc_contractAuditId").val(data.businessContract.contractAuditId);//合同审核表ID
							$("#bc_organId").val(data.businessContract.organId);//租户
							HTCkeditor.setData(data.businessContract.contractContent);//合同内容
						}else{
							alertx(data.message);
						}
					}
				);
		}
	};
	
	//导出编辑的PDF文件
	function toPdf(){
		var datas = HTCkeditor.getData();
		datas = datas.replace("/(^/s*)|(/s*$)/g","");
		datas = datas.replace("<br />","");
		datas = datas.replace("<br>","");
		if(datas == ""){
			alert("内容不能为空！");
		}else{
			var pdfUrl = "";
			$.post("${ctx}/contract/businessContract/toCreatePdf",{"fileName":$("#bc_contractName").val(),"crosswise":$("#bc_crosswise").val(), "content":HTCkeditor.getData()},
					function(data){
						if(data.status === 1){
							window.open("${ct}"+data.pdfpath, "_blank");
						}else{
							alertx(data.message);
						}
					}
				);
		}
	};
	//通过id导出PDF文件
	function toViewContract(businessContractId){
		//console.info("通过id导出PDF文件--》businessContractId="+businessContractId);
		if(businessContractId && businessContractId.length > 0){
			$.post("${ctx}/contract/businessContract/exportBusinessContract",{"id":businessContractId},
					function(data){
						if(data.status ===1){
							//console.info("============================");
							//console.info("data.businessContract.id="+data.businessContract.id);
							//console.info("============================");
							window.open("${ct}"+data.pdfpath, "_blank");
						}else{
							alertx(data.message);
						}
					}
				);
		}else{
			alert("导出错误！");
		}
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li> --%>
		<li  class="info"><a href="${ctx}/contract/contractAudit/toSign?id=${contractAudit.id}&loanContract.id=${contractAudit.loanContract.id}&tab=info&act.taskId=${contractAudit.act.taskId}&act.taskName=${contractAudit.act.taskName}&act.taskDefKey=${contractAudit.act.taskDefKey}&act.procInsId=${contractAudit.act.procInsId}&act.procDefId=${contractAudit.act.procDefId}&act.procDefKey=${contractAudit.act.procDefKey}&act.status=${contractAudit.act.status}">业务详情</a></li>
		<li  class="creditinfo"><a href="${ctx}/contract/contractAudit/toSign?id=${contractAudit.id}&loanContract.id=${contractAudit.loanContract.id}&tab=creditinfo&act.taskId=${contractAudit.act.taskId}&act.taskName=${contractAudit.act.taskName}&act.taskDefKey=${contractAudit.act.taskDefKey}&act.procInsId=${contractAudit.act.procInsId}&act.procDefId=${contractAudit.act.procDefId}&act.procDefKey=${contractAudit.act.procDefKey}&act.status=${contractAudit.act.status}">授信评审意见</a></li>
		<li class="files"><a href="${ctx}/contract/contractAudit/toSign?id=${contractAudit.id}&loanContract.id=${contractAudit.loanContract.id}&tab=files&act.taskId=${contractAudit.act.taskId}&act.taskName=${contractAudit.act.taskName}&act.taskDefKey=${contractAudit.act.taskDefKey}&act.procInsId=${contractAudit.act.procInsId}&act.procDefId=${contractAudit.act.procDefId}&act.procDefKey=${contractAudit.act.procDefKey}&act.status=${contractAudit.act.status}&tLoanContract=${date}">合同及附件</a></li>
		
		<%-- <li    class="lc"><a href="${ctx}/contract/contractAudit/toSign?id=${contractAudit.id}&loanContract.id=${contractAudit.loanContract.id}&tab=lc&act.taskId=${contractAudit.act.taskId}&act.taskName=${contractAudit.act.taskName}&act.taskDefKey=${contractAudit.act.taskDefKey}&act.procInsId=${contractAudit.act.procInsId}&act.procDefId=${contractAudit.act.procDefId}&act.procDefKey=${contractAudit.act.procDefKey}&act.status=${contractAudit.act.status}">申请/审批</a></li> --%>
	</ul>
	<sys:message content="${message}"/>		
	<div id="info" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
		<br>
		<!-- 还款计划-- -->
		<fieldset>
			<legend>还款计划</legend>
			<div id="showplansDiv">
			</div>
		</fieldset>
	</div>
	<div id="creditinfo" class="nav-tab-con" style="display:none;">
		<div class="control-group">
			<!-- <label class="control-label">授信评审意见内容：</label> -->
			<div class="controls">
				<textarea id="creditApplyComments" name="creditApplyComments" maxlength="20000" style="width:800px;overflow:auto;" class="input-xxlarge " disabled="disabled" rows="30">${contractAudit.loanContract.creditApplyComments}</textarea>
			</div>
		</div>
	</div>
	<!-- 业务合同内容开始 -->
	<div id="files" class="nav-tab-con" style="display:none;">
		<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
		<label class="control-label"><h5>合同模板列表：</h5></label><br><br>
		<div class="control-group">
			<ul class="ul-form">
				<c:forEach items="${contractTplList}" var="contractTpl">
					<a class="btn btn-primary" onclick="toAddContract('${contractTpl.id}','${contractAudit.loanContract.id}');">${contractTpl.createName}</a>&nbsp;&nbsp;&nbsp;
				</c:forEach>
			</ul>
			<br>
		</div>
		</c:if>
		<div id="showEdit">
		<label class="control-label"><h5>合同编辑：</h5></label><br><br>
		<div class="control-group">
			<form:form id="inputFormContract" modelAttribute="businessContract" action="${ctx}/contract/contractAudit/saveContract?tab=files" method="post" class="form-horizontal">
				<form:hidden id="bc_id" path="id"/>
				<form:hidden id="bc_tplId" path="tplId"/>
				<form:hidden id="bc_contractType" path="contractType"/>
				<form:hidden id="bc_loanContractId" path="loanContract.id"/>
				<form:hidden id="bc_contractAuditId" path="contractAuditId"/>
				<form:hidden id="bc_crosswise" path="crosswise"/>
				<form:hidden id="bc_organId" path="organId"/>
				<input id="act.taskId" name="act.taskId" value="${contractAudit.act.taskId}" type="hidden">
				<input id="act.taskName" name="act.taskName" value="${contractAudit.act.taskName}" type="hidden">
				<input id="act.taskDefKey" name="act.taskDefKey" value="${contractAudit.act.taskDefKey}" type="hidden">
				<input id="act.procInsId" name="act.procInsId" value="${contractAudit.act.procInsId}" type="hidden">
				<input id="act.procDefId" name="act.procDefId" value="${contractAudit.act.procDefId}" type="hidden">
				<input id="act.procDefKey" name="act.procDefKey" value="${contractAudit.act.procDefKey}" type="hidden">
				<input id="act.status" name="act.status" value="${contractAudit.act.status}" type="hidden">
				<div class="control-group">
					<label class="control-label">合同名称：</label>
					<div class="controls">
						<form:input id="bc_contractName" path="contractName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font>
					</div>
				</div>
				<div class="control-group">
						<textarea id="contractContent" name="contractContent" rows="4" maxlength="200" class="input-xxlarge" >${businessContract.contractContent }</textarea>
					<br>
						<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="生 成"/>&nbsp;&nbsp;&nbsp;
						</c:if>
						<a class="btn btn-primary" onclick="toPdf();">PDF预览</a>
				</div>
			</form:form>
			<br>
			<br>
		</div>
		</div>
		<%-- </c:if> --%>
		<c:if test="${!empty businessContractList}">
		<label class="control-label"><h5>已经生成合同列表：</h5></label><br><br>
		<div class="control-group">
			<%-- <ul class="ul-form">
				<c:forEach items="${businessContractList}" var="businessContractEntity">
					<a class="btn btn-primary" onclick="toEditContract('${businessContractEntity.id}');">${businessContractEntity.contractName}</a>&nbsp;&nbsp;&nbsp;
				</c:forEach>
			</ul> --%>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>合同名称</th>
						<th>更新时间</th>
						<%-- <shiro:hasPermission name="contract:tLoanContract:edit"> --%><th>操作</th><%-- </shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${businessContractList}" var="businessContractEntity">
						<tr>
							<td>
								${businessContractEntity.contractName}
							</td>
							<td>
								<fmt:formatDate value="${businessContractEntity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td>
								<a style="cursor:pointer" onclick="toViewContract('${businessContractEntity.id}');">PDF预览</a>&nbsp;
			    				<a style="cursor:pointer" onclick="toEditContract('${businessContractEntity.id}');">修改</a>&nbsp;
								<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
								<a href="${ctx}/contract/contractAudit/delBusinessContract?tab=files&businessContractId=${businessContractEntity.id}">删除</a>
								</c:if>
								<%-- <c:if test="${!empty ifEdit && ifEdit == 'edit'}">
								</c:if> --%>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<br>
		</div>
		<br>
		</c:if>
		<label class="control-label"><h5>相关附件：</h5></label>
		<div class="control-group" style="height: 500px;">
			<div id="${nid}filelist" style="height: 550px;"></div>
		</div>
		
		<form:form id="inputForm" modelAttribute="contractAudit" action="${ctx}/contract/contractAudit/sign" method="post" class="form-horizontal">
			
			 <input type="hidden" name="loanContract.id" value="${contractAudit.loanContract.id}" /> 
			
			<div class="form-actions">
				<shiro:hasPermission name="contract:tLoanContract:edit">
				     <input  class="btn btn-primary" type="submit" value="签订合同"/>&nbsp;
				</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</form:form>
	</div>
	<!-- 业务附件部分结束 -->
	<%-- <div id="lc" class="nav-tab-con" style="display:none;">
		<br><br>
		<form:form id="inputFormSign" modelAttribute="contractAudit" action="${ctx}/contract/contractAudit/sign" method="post" class="form-horizontal">
			<input type="hidden" name="id" value="${data.id}" />
			<form:hidden path="id"/>
			<form:hidden path="subType"/>
			<form:hidden path="loanContract.id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden id="flag" path="act.flag"/>
			<input type="hidden" id="pass" name="act.vars.map['pass']"/>
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="3800" cssStyle="width:500px"/>
				</div>
			</div>
			<div class="form-actions">
				<shiro:hasPermission name="contract:tLoanContract:edit">
				     <input id="btnSubmit" class="btn btn-primary" type="submit" value="签订合同"/>&nbsp;
				</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				<shiro:hasPermission name="contract:tLoanContract:edit">
				<c:if test="${(!edit && empty contractAudit.act.procInsId) || (contractAudit.loanContract.status eq '3')}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="$('#pass').val('1');$('#flag').val('yes');" value="提交给下一步"/>&nbsp;
				</c:if>
				<c:if test="${!edit && !empty contractAudit.act.procInsId}">
					<c:if test="${contractAudit.act.status == 'todo'}">
					<c:choose>
						<c:when test="${!empty subType && subType == 'start'}">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步"/>&nbsp;
							<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止本流程" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
						</c:when>
						<c:when test="${!empty subType && subType == 'one'}">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步" onclick="$('#pass').val('1');$('#flag').val('1');"/>
						</c:when>
						<c:when test="${!empty subType && subType == 'end'}">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
							<!-- <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回给上一步" onclick="$('#pass').val('0');$('#flag').val('0');"/> -->
						</c:when>
						<c:otherwise>
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交给下一步" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
							<input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳回给上一步" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
							<input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止本流程" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
							<input id="btnSubmit" class="btn btn-inverse" type="submit" value="退回发起人" onclick="$('#pass').val('9');$('#flag').val('9');"/>&nbsp;
						</c:otherwise>
					</c:choose>
					</c:if>
				</c:if>
				</shiro:hasPermission>
			</div>
			<act:histoicFlow procInsId="${contractAudit.act.procInsId}"/>
		</form:form>
	</div> --%>
	
</body>
</html>