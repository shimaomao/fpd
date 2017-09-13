<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <c:set var="edit" value="${act.status == 'finish' ? false : true }" /> --%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<c:set var="edit" value="${tLoanContract.act.status == 'finish' ? true : false }" />
<html>
<head>
	<title>放款审批</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api','app/repayPlan'], function(api,rp){
			//还款计划--
			rp.initialize({
				businessId : "${data.id}",
				businessType : "apply",  //apply|extend|earlyrepay...
				amount : "${data.loanAmount}",
				loanRate : "${data.loanRate}",
				loanRateType : "${data.loanRateType}",
				loanPeriod : "${data.loanPeriod}",
				loanDate : '<fmt:formatDate value="${data.loanDate}" pattern="yyyy-MM-dd"/>',
				payType : "${data.payType}",//还款方式 	1|2|3 = 等额本息|等额本金|按月付息到期还款
				periodType : "${data.periodType}",//还款周期（payType=3时才会用到，等额本息和等额本金周期只能是“月”） 1|2|3  = 年|月|日 ,
				payDay : "${data.payDay}",//每期还款日期（payType=3时才会用到，等额本息和等额本金的还款日只能是放款日）	为null默认为放款日,
				payOptions : "${data.payOptions}",//还款选项（payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			    ifRealityDay : "${data.ifRealityDay}"//大小月
			},"#showplansDiv");
			
			
			$(function(){
				//初始化业务数据
				var data = ${fns:toJson(data)};
				var form = $("#target");
				api.form.init(form, eval(data));
				//禁用form表单中所有的选项
				disableForm("target",true);
				
				showTab("${param.tab}");
				//页签
				/* $('.nav-tabs').find('li').click(function(){
					$(this).addClass('active').siblings().removeClass('active');
					$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
				}); */
				
				$("#inputForm").validate({
					submitHandler: function(form){
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
				
				  $("#btnSubmit,#btnReBack").click(function(){
					var form = $("#inputForm");
					if(form.validate().form()){
						loading('正在提交，请稍等...');
						form.ajaxSubmit({
							type: "post",
							dataType: "json",
							success: function(result, status) {
								$("#${nid}btnSubmit").removeAttr("disabled");
								closeLoading();
								showTip(result);
								if(result == true){
									location.href = "${ctx}${empty endUrl ? '/act/task/historic/' : 'endUrl' }";
				            	}
				   			}
				       });
					}
				}); 
				  
				//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
					// businesstype 附件类型、oper= edit|其他   是否可编辑
					var url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?height=380&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
					$("#${nid}filelist").load(url);
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
		<li class="bd"><a href="${ctx}/contract/tLoanContract/loanAuditForm?id=${data.id}&tab=bd&act.procDefId=${act.procDefId}&act.status=${act.status}&act.taskId=${act.taskId}&act.businessTable=${act.businessTable}&act.businessId=${act.businessId}">业务信息</a></li>
		<li class="lc"><a href="${ctx}/contract/tLoanContract/loanAuditForm?id=${data.id}&tab=lc&act.procDefId=${act.procDefId}&act.status=${act.status}&act.taskId=${act.taskId}&act.businessTable=${act.businessTable}&act.businessId=${act.businessId}">申请/审批</a></li>
		<li class="files"><a href="${ctx}/contract/tLoanContract/loanAuditForm?id=${data.id}&tab=files&act.procDefId=${act.procDefId}&act.status=${act.status}&act.taskId=${act.taskId}&act.businessTable=${act.businessTable}&act.businessId=${act.businessId}">附件</a></li>
	</ul>
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
		<br>
		<!-- 还款计划-- -->
		<fieldset>
			<legend>还款计划</legend>
			<div id="showplansDiv">
			</div>
		</fieldset>
	</div>
	
	<div id="files" class="nav-tab-con" style="display:none;">
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
				    				<%-- <a style="cursor:pointer" onclick="toEditContract('${businessContractEntity.id}');">修改</a>&nbsp; --%>
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
		<div class="control-group">
			<!-- <label class="control-label">相关附件：</label> -->
			<div class="control-group" style="height: 550px;">
				<div id="${nid}filelist" style="height: 600px;"></div>
			</div>
		</div>
	</div>
	<div id="lc" class="nav-tab-con" style="display:none;">
		<br/>
		<%-- <form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/act/task/complete" method="post" class="form-horizontal"> --%>
		<form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/makeLoans" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden id="flag" path="act.flag"/>
			<sys:message content="${message}"/>		
			<!-- <input type="hidden" id="vars" name="vars.map['pass']" /> -->
			<%-- <div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<textarea name="comment" class="required" rows="5" maxlength="20" style="width:500px">${act.comment}</textarea>
				</div>
			</div> --%>
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
				</div>
			</div>
			<div class="form-actions">
			        <c:if test="${(!edit && empty tLoanContract.act.procInsId) || (tLoanContract.status eq '4')}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="$('#pass').val('1');$('#flag').val('yes');" value="申请"/>&nbsp;
					</c:if>
			        
					<c:if test="${!edit && !empty tLoanContract.act.procInsId}">
					  <c:if test="${tLoanContract.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('no');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('stop');"/>&nbsp;
					  </c:if>
					</c:if>
					<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
				
			</div>
			<act:histoicFlow procInsId="${tLoanContract.act.procInsId}"/>
		</form:form>
	</div>
</body>
</html>