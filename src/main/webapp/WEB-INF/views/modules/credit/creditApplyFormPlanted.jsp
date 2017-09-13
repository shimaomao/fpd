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
			//console.info("creditApplyTab="+creditApplyTab);
			if((creditApplyTab == undefined) || (creditApplyTab == null) || (creditApplyTab == '')){
				showTab("${param.tab}");
			}else{
				showTab(creditApplyTab);
			}
			
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
			var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=380&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
			$("#${nid}filelist").load(url);
			
			<%-- var acturl = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_ACTIVI%>&oper=edit&nid=file";
			$("#fileactivilist").load(acturl);  --%>
		    
		    HTCkeditor = CKEDITOR.replace("reportPlanted.ftlContent");
		    //CKEDITOR.config.readOnly = true;//设置只读
		    var ifEdit = "${ifEdit}";
		    //console.info("ifEdit="+ifEdit);
		    if(ifEdit == "edit"){
		    	//console.info("可以编辑");
		    	
		    	<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=edit&nid=${nid}file";
			    $("#${nid}filelist").load(url); --%>
		    }else{
		    	//console.info("不可以编辑");
		    	disableForm("reportForm",true);//设置id为reportForm的不可编辑
		    	CKEDITOR.config.readOnly = true;//设置只读
		    	
		    	<%-- var url = "${ctx}/files/tContractFiles/showfilelist/${creditApply.id}.html?height=120&businesstype=<%=FileType.FILE_TYPE_CREDIT_SIGN%>&oper=view&nid=${nid}file";
			    $("#${nid}filelist").load(url); --%>
		    }
		    
		    doChange();
		});
	});
	
	//通过模板导入内容进行编辑
	function toAddTplPublicContent(tplPublicId, tplCode){
		if(tplPublicId && tplPublicId.length > 0){
			$.post("${ctx}/common/tpl/tplPublic/getTplPublic",{"id":tplPublicId, "tplCode":tplCode},
				function(data){
					if(data.status ===1){
						$("#bc_tplCode").val(data.tplPublic.tplCode);
						HTCkeditor.setData(data.tplPublic.ftlContent);//合同内容
					}else{
						alertx(data.message);
					}
				}
			);
		}
	};
	
	//授信标识点击后处理
	function doChange(){
		var sval = $('#creditType').val();//对应值
		//console.info("sval="+sval);
		
		if(typeof(sval) == "undefined" || sval.length == 0){
			return;
		}
		if(sval == 0){//授信
			//console.info("授信");
			$('#creditSinglePeriod_div').show();
			//$('#creditSinglePeriod').show();
			$('#creditMoney_div').show();
			$('#creditPeriod_div').show();
			
			$('#loanMoney_div').hide();
			$('#loanPeriod_div').hide();
		}else if(sval == 1){//非授信
			//console.info("非授信");
			$('#loanMoney_div').show();
			$('#loanPeriod_div').show();
		
			$('#creditSinglePeriod_div').hide();
			//$('#creditSinglePeriod').hide();
			$('#creditMoney_div').hide();
			$('#creditPeriod_div').hide();
		}
	}
	
	//添加共同借款人用
	function customerTreeselectCallBack(v, h, f){
		var creditApplyId = $('#creditApplyId').val();
		var customerId = $('#customerId').val();
		var customerName = $('#customerName').val();
		var customertype = ($("#customerpId").val());//1企业   2个人
		//console.info("添加共同借款人 creditApplyId="+creditApplyId);
		//console.info("添加共同借款人 customerId="+customerId);
		//console.info("添加共同借款人 customerName="+customerName);
		//console.info("添加共同借款人 customertype="+customertype);
		if(!customerId){
			alertx("请选择一个共同借款人!");
			return;
		}
		$.post("${ctx}/credit/creditCoborrower/addJsonCoborrower",
	         	{creditApplyId:creditApplyId,customerId:customerId,customerName:customerName,customerType:customertype},
	         	function(data){
	         		//console.info("data.status="+data.status);
	         		//console.info("data.message="+data.message);
	         		//console.info("----------------------------");
	         		var coborContent = "";
	         		$.each(data.colist,function(idx,item){
         				//console.info(idx+",id:"+item.id+",creditApplyId:"+item.creditApplyId+",customerName:"+item.customerName+",customerType:"+item.customerType); 
         				if(idx==0){
         					coborContent += "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">";
         					coborContent += "<thead><tr><th>客户姓名</th><th>客户类型</th><th>操作</th></tr></thead><tbody>";
         				}
         				coborContent += "<tr><td>";
         				coborContent += item.customerName;
         				coborContent += "</td><td>";
         				if(item.customerType == '1'){
         					coborContent += "企业";
         				}else if(item.customerType == '2'){
         					coborContent += "个人";
         				}else{
         					coborContent += item.customerType;
         				}
         				coborContent += "</td>";
         				coborContent += "<td>";
         				if("${ifEdit}" == "edit"){
         					coborContent += "<a onclick=\"toDelCo('" + item.id + "','" + item.creditApplyId + "');\" style=\"cursor: pointer;\">删除</a>";
         				}else{
         					coborContent += "无";
         				}
         				coborContent += "</td>";
         				coborContent += "</tr>";
     				}); 
	         		//console.info("----------------------------");
	         		if(coborContent.length>10){
	         			coborContent += "</tbody></table>";
	         		}else{
	         			coborContent = "&nbsp;";
	         		}
	         		//console.info(coborContent);
	         		$("#coborrowerInfo").html(coborContent);
	         		/*
	         		---------------------------- 
					0,creditApplyId:d49a7f321a424c669731d81e34208f3a,customerName:测试客户2  form:249:14
					1,creditApplyId:d49a7f321a424c669731d81e34208f3a,customerName:测试企业  form:249:14
					2,creditApplyId:d49a7f321a424c669731d81e34208f3a,customerName:测试个人  form:249:14
					3,creditApplyId:d49a7f321a424c669731d81e34208f3a,customerName:测试个人B  form:249:14
					----------------------------
	         		*/
	         		//console.info("----------------------------");
	         		if(data.status==1){
	         			console.info("成功==");
	         		}else{
	         			console.info("失败==");
	         			showTip(data.message);
	         		}
	         		return;
	         	}
	         );
	}
	
	//删除共同借款人
	function toDelCo(coborrowerId, creditApplyId){
		//console.info("删除共同借款人 coborrowerId="+coborrowerId);
		//console.info("删除共同借款人 creditApplyId="+creditApplyId);
		if(!coborrowerId || !creditApplyId){
			alertx("删除共同借款人失败!");
			return;
		}
		$.post("${ctx}/credit/creditCoborrower/delJsonCoborrower",
	         	{id:coborrowerId,creditApplyId:creditApplyId},
	         	function(data){
	         		//console.info("data.status="+data.status);
	         		//console.info("data.message="+data.message);
	         		//console.info("data.colist="+data.colist);
	         		//console.info("data.colist.length="+data.colist.length);
	         		if(data.status==1){
	         			//console.info("成功==");
	         			var coborContent = "";
		         		$.each(data.colist,function(idx,item){
	         				if(idx==0){
	         					coborContent += "<table id=\"contentTable\" class=\"table table-striped table-bordered table-condensed\">";
	         					coborContent += "<thead><tr><th>客户姓名</th><th>客户类型</th><th>操作</th></tr></thead><tbody>";
	         				}
	         				coborContent += "<tr><td>";
	         				coborContent += item.customerName;
	         				coborContent += "</td><td>";
	         				if(item.customerType == '1'){
	         					coborContent += "企业";
	         				}else if(item.customerType == '2'){
	         					coborContent += "个人";
	         				}else{
	         					coborContent += item.customerType;
	         				}
	         				coborContent += "</td>";
	         				coborContent += "<td>";
	         				coborContent += "<a onclick=\"toDelCo('" + item.id + "','" + item.creditApplyId + "');\" style=\"cursor: pointer;\">删除</a>";
	         				coborContent += "</td>";
	         				coborContent += "</tr>";
	     				}); 
		         		if(coborContent.length>10){
		         			coborContent += "</tbody></table>";
		         		}else{
		         			coborContent = "&nbsp;";
		         		}
		         		$("#coborrowerInfo").html(coborContent);
	         		}else{
	         			//console.info("失败==");
	         			//showTip(data.message);
	         		}
	         		return;
	         	}
	         );
	}
	
	function showTab(tab){
		if((tab == undefined) || (tab == null) || (tab == '')){
			tab = "bd";
		}
		//console.info("tab="+tab);
		$(tab).show();
		$("."+tab).addClass('active').siblings().removeClass('active');
		$("#"+tab).show().siblings('.nav-tab-con').hide();
	};
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	    <li class="bd"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=bd">客户信息</a></li>
	    <li class="report"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=report">调查报告</a></li>
	    <li class="files"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=files">附件</a></li>
	    <c:if test="${not empty creditApply.id && (!fn:contains(creditApply.id,'temp_') || (not empty creditApply.tab && creditApply.tab eq 'lc'))}">
			<li class="lc"><a href="${ctx}/credit/creditApply/form?id=${creditApply.id}&customerId=${creditApply.customerId}&customerType=${creditApply.customerType}&act.procDefId=${creditApply.act.procDefId}&act.status=${creditApply.act.status}&act.taskId=${creditApply.act.taskId}&tab=lc">申请/审批</a></li>
		</c:if>
	</ul>
	<div style="display: none;"><!-- 共同借款人用 -->
		<sys:treeselect id="customer" isAll="false" name="" value="" labelName="customerName" labelValue="" 
			title="客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
	</div>
	<br/> 
	<div id="bd" class="nav-tab-con" style="display:none;">
		${dfFormTpl.parsehtml }
	</div>
	<div id="report" class="nav-tab-con" style="display:none;">
		<!-- 调查报告内容 -->
		<form:form id="reportForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/saveReport" method="post" class="form-horizontal">
			<form:hidden id="creditApplyId" path="id"/>
			<form:hidden path="customerId" />
			<form:hidden path="customerName" />
			<form:hidden path="customerType" /> 
			<form:hidden path="productId" />
			<form:hidden path="productname" /> 
			<form:hidden path="act.taskId"/>
			<form:hidden path="act.status"/>
			<form:hidden path="act.taskName"/>
			<form:hidden path="act.taskDefKey"/>
			<form:hidden path="act.procInsId"/>
			<form:hidden path="act.procDefId"/>
			<form:hidden path="act.procDefKey"/>
			<form:hidden path="act.businessTable"/>
			<sys:message content="${message}"/>
			<!--  -->
			<form:hidden path="tab"/>
			<form:hidden path="reportPlanted.id"/>
			<form:hidden path="reportPlanted.creditApplyId"/>
			<form:hidden id="bc_tplCode" path="reportPlanted.tplCode"/>
			<form:hidden path="reportPlanted.status"/>
			<input type="hidden" id="tmp" name="reportPlanted.tmp"/>
		<div class="control-group">
			<label class="control-label">授信标识：</label>
			<div class="controls">
				<form:select id="creditType" path="reportPlanted.creditType" class="input-xlarge required" onchange="doChange()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('credit_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">产品类别：</label>
			<div class="controls">
				<form:select path="reportPlanted.productType" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nk_product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">审核类型：</label>
			<div class="controls">
				<form:select path="auditType" class="input-xlarge required" disabled="${empty creditApply.act.procInsId ? false : true}">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('audit_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">部门：</label>
			<div class="controls">
				<form:input path="reportPlanted.department" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">借款人：</label>
			<div class="controls">
				<form:input path="reportPlanted.name" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">客户经理：</label>
			<div class="controls">
				<form:input path="reportPlanted.salesman" htmlEscape="false" maxlength="50" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">受理日期：</label>
			<div class="controls">
				<input name="reportPlanted.acceptDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${creditApply.reportPlanted.acceptDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="creditMoney_div" class="control-group">
			<label class="control-label">授信金额：</label>
			<div class="controls">
				<form:input path="reportPlanted.creditMoney" htmlEscape="false" class="input-xlarge required number"/>
				元<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="creditPeriod_div" class="control-group">
			<label class="control-label">授信期限：</label>
			<div class="controls">
				<form:input path="reportPlanted.creditPeriod" htmlEscape="false" maxlength="10" class="input-xlarge digits required"/>
				月<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div  id="creditSinglePeriod_div" class="control-group">
			<label class="control-label">单笔贷款期限最长：</label>
			<div class="controls">
				<form:input id="creditSinglePeriod" path="reportPlanted.creditSinglePeriod" htmlEscape="false" maxlength="10" class="input-xlarge digits required"/>
				月<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年利率：</label>
			<div class="controls">
				<form:input path="reportPlanted.rate" htmlEscape="false" class="input-xlarge required number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="loanMoney_div" class="control-group">
			<label class="control-label">贷款金额：</label>
			<div class="controls">
				<form:input path="reportPlanted.loanMoney" htmlEscape="false" class="input-xlarge required number"/>
				元<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="loanPeriod_div" class="control-group">
			<label class="control-label">申请贷款期限：</label>
			<div class="controls">
				<form:input path="reportPlanted.loanPeriod" htmlEscape="false" maxlength="10" class="input-xlarge  digits"/>
				月<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">咨询费：</label>
			<div class="controls">
				<form:input path="reportPlanted.consultingFee" htmlEscape="false" class="input-xlarge required number"/>
				%<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">还款方式：</label>
			<div class="controls">
				<form:radiobuttons path="reportPlanted.repayType" items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">业务来源：</label>
			<div class="controls">
				<%-- <form:input path="reportPlanted.businessSource" htmlEscape="false" maxlength="5" class="input-xlarge required"/> --%>
				<form:select path="reportPlanted.businessSource" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('nk_business_source')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${not empty ifEdit && ifEdit == 'edit' && not empty tplPublicList}">
		<br>
		<div class="control-group">
			<label class="control-label"><h5>调查报告模板列表：</h5></label>
			<!-- <div class="control-group"> -->
				<ul class="ul-form">
					<c:forEach items="${tplPublicList}" var="tplPublic">
						<a class="btn btn-primary" onclick="toAddTplPublicContent('${tplPublic.id}','${tplPublic.tplCode}');">${tplPublic.name}</a>&nbsp;&nbsp;&nbsp;
					</c:forEach>
				</ul>
			<!-- </div> -->
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">调查报告内容：</label>
			<div class="controls">
				<%-- <form:input path="reportPlanted.ftlContent" htmlEscape="false" class="input-xlarge "/> --%>
				<textarea id="reportPlanted.ftlContent" name="reportPlanted.ftlContent" rows="4" maxlength="200" class="input-xxlarge required" >${creditApply.reportPlanted.ftlContent }</textarea>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="reportPlanted.status" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<c:if test="${!empty ifEdit && ifEdit == 'edit'}">
		<div class="form-actions">
			<shiro:hasPermission name="credit:creditApply:edit">
				<input id="reportSubmit" class="btn btn-primary" type="submit" value="暂存报告内容" onclick="$('#tmp').val('1');"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input id="reportSubmit" class="btn btn-primary" type="submit" value="保存提交报告"/>&nbsp;
			</shiro:hasPermission>
		</div>
		</c:if>
		
		</form:form>
		<!-- 调查报告内容 -->
	</div>
	<div id="files" class="nav-tab-con" style="display:none;">
		<div class="control-group">
			<!-- <label class="control-label">相关附件：</label> -->
			<div class="control-group" style="height: 550px;">
				<div id="${nid}filelist" style="height: 600px;"></div>
			</div>
		</div>
	</div>
	<div id="lc" class="nav-tab-con"  style="display:none;">
		<form:form id="inputForm" modelAttribute="creditApply" action="${ctx}/credit/creditApply/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<form:hidden path="subType"/>
			<form:hidden path="expirationDate"/>
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
			<input type="hidden" id="pass" name="act.vars.map['pass']"/><!-- 控制进程流转 -->
			<sys:message content="${message}"/>
			<%-- <div class="control-group">
				<label class="control-label">授信标识：</label>
				<div class="controls">
					<form:select path="creditType" class="input-xlarge required" disabled="${empty creditApply.act.procInsId ? false : true}">
						<form:option value="" label=""/>
						<form:options items="${fns:getDictList('credit_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div> --%>
			<div class="control-group">
				<label class="control-label">授信额度：</label>
				<div class="controls">
					<%-- <form:input path="credit" htmlEscape="false" class="input-large required number" disabled="${(empty creditApply.act.procInsId ? false : true) && (empty subType || subType != 'start')}"/> --%>
					<form:input path="credit" htmlEscape="false" class="input-large required number"/>
					元<span class="help-inline"><font color="red">*(如已经有授信额度，则旧的额度会被更新)</font> 
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">授信期限：</label>
				<div class="controls">
					<%-- <form:input path="credit" htmlEscape="false" class="input-large required number" disabled="${(empty creditApply.act.procInsId ? false : true) && (empty subType || subType != 'start')}"/> --%>
					<form:input path="validMonth" htmlEscape="false" class="input-large required digits"/>
					月<span class="help-inline"><font color="red">*</font> 
				</div>
			</div>
			
			<%-- <input value="${creditApply.customerName}">
			<input value="${creditApply.productId}">
			<input value="${creditApply.productname}">
			<input value="${creditApply.creditType}">
			<input value="${creditApply.auditType}"> --%>
			<%-- <div class="control-group">
				<label class="control-label">授信额度有效截止日期：</label>
				<div class="controls">
					<input name="expirationDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
						${empty creditApply.act.procInsId ? false : true ? '' : 'disabled="disabled"'}
						<c:if test="${empty creditApply.act.procInsId ? false : true && (empty subType || subType != 'start')}">
							disabled="disabled"
						</c:if>
						value="<fmt:formatDate value="${creditApply.expirationDate}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
						<span class="help-inline"><font color="red">*</font> 
				</div>
			</div> --%>
			<%-- <c:if test="${!empty subType && subType == 'four'}">
				<div class="control-group">
					<label class="control-label">是否开贷审会：</label>
					<div class="controls">
						<form:select path="approve" class="input-xlarge required" >
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
			</c:if> --%>
			<%-- <c:if test="${not empty creditApply.remarks || (!empty subType && subType == 'submit')}"> --%>
		<%-- 	<c:if test="${not empty creditApply.remarks || (!empty subType && (subType == 'psyj' || subType == 'submit' || subType == 'four'))}"> --%>
			<div class="control-group">
				<label class="control-label">评审意见书：</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" style="width:700px;overflow:auto;" rows="10" maxlength="20000" class="input-xxlarge required" 
						/>
					<span class="help-inline"><font color="red">*</font>
				</div>
			</div>
		<%-- 	</c:if> --%>
			<%-- <c:if test="${edit && not empty creditApply.act.procInsId}"> --%>
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<form:textarea path="act.comment" class="required" rows="5" maxlength="3800" cssStyle="width:500px"/>
					<span class="help-inline"><font color="red">*</font>
				</div>
			</div>
			<c:if test="${!edit && !empty creditApply.act.procInsId}">
				<c:if test="${creditApply.act.status == 'todo' && !empty subType && subType == 'submit' && (creditApply.apprAgree>0 || creditApply.apprDisAgree>0)}">
					<div class="control-group">
						<label class="control-label">评审会意见：</label>
						<div class="controls">
							投票总人数：${creditApply.apprTotal}；&nbsp;同意授信人数：${creditApply.apprAgree}；&nbsp;不同意授信人数：${creditApply.apprDisAgree}
						</div>
					</div>
				</c:if>
			</c:if>
			
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
			<!-- <div class="control-group">
				<label class="control-label">相关附件：</label>
				<div class="control-group" style="height: 250px;">
					<div id="fileactivilist" style="height: 300px;"></div>
				</div>
			</div> -->
			<act:histoicFlow procInsId="${creditApply.act.procInsId}"/>
			</div>
			</div>
			<table class="table table-bordered">
          </table>
			
			
		</form:form>
	</div>
</body>
</html>