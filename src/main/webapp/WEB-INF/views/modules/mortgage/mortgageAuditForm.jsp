<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.ProcDefKey" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@page import="com.wanfin.fpd.common.config.Cons"%>
<c:set var="blackApplyKey" value="<%=ProcDefKey.BLACK_APPLY %>"/>
<c:set var="edit" value="${tMortgageApplay.act.status == 'finish' ? false : true }" />
<html>
<head>
	<title>押品借出审批</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				//初始化业务数据
			    var data = ${fns:toJson(data)};
			    
				var form = $("#target");
				api.form.init(form, eval(data)); 
				form.attr("method", "post");
				var divWrap = form.find("#divWrap");
				if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
					form.append("<div id=\"divWrap\"></div>");
					divWrap = form.find("#divWrap");
				}
				 if(data.id==''||typeof(data.id)=='undefined'){
						divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }else{
						divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }
				form.attr("target", "mainFrame");
				form.attr("action", ctx+"${action}");
				
				//页签
				$('.nav-tabs').find('li').click(function(){
					$(this).addClass('active').siblings().removeClass('active');
					$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
				});
				
					//添加关联业务的标签
					var $business = $('<div class="control-group"><label class="control-label" for="remarks">关联业务</label></div>');
					var $select = $('<select id="businessId" name="businessId" class="input-xlarge"></select>');
					$.post("${ctx}/contract/tLoanContract/getLoanContracts",{loanType : 2, status : <%=Cons.LoanContractStatus.TO_REVIEW%>},
						function(list){
							if(list){
								$select.append('<option value=""></option>')
								for(var i in list) {
									if(data.businessId==list[i].id){
										$select.append('<option value="'+list[i].id+'" selected="selected">'+list[i].contractNumber+'</option>');
									}else{
										$select.append('<option value="'+list[i].id+'">'+list[i].contractNumber+'</option>');
									}
									
								}
							}
						}
					);
					$business.append($('<div class="controls"></div>').append($select)).append($('<input type="hidden" name="businessTable" value="t_loan_contract">'));
					form.find("fieldset").append($business);
	                $("#pledgeType").val(data.pledgeType);
					$("#pledgeType").change(function(){
						console.info("change");
						var sval = $('#pledgeType').val();//对应值
						if(typeof(sval) == "undefined" || sval.length == 0){
							return;
						}
						var loadhref;
						if(sval == 1){//商铺写字楼
							loadhref = "${ctx}/collateral/building/add?mortgageid=${data.id}";
						}else if(sval == 2){//住宅
							loadhref = "${ctx}/collateral/house/add?mortgageid=${data.id}";
						}else if(sval == 3){//工业用
							loadhref = "${ctx}/collateral/gongLand/add?mortgageid=${data.id}";
						}else if(sval == 4){//商宅用地
							loadhref = "${ctx}/collateral/zhuLand/add?mortgageid=${data.id}";
						}else if(sval == 5){//公寓信息
							loadhref = "${ctx}/collateral/gongyu/add?mortgageid=${data.id}";
						}else if(sval == 6){//独栋别墅
							loadhref = "${ctx}/collateral/singleVilla/add?mortgageid=${data.id}";
						}else if(sval == 7){//住宅信息
							loadhref = "${ctx}/collateral/zhuZhai/add?mortgageid=${data.id}";
						}else if(sval == 8){//联排别墅
							loadhref = "${ctx}/collateral/terraceVilla/add?mortgageid=${data.id}";
						}
						console.log(loadhref);
					    if(typeof(loadhref) == "undefined" || loadhref.length == 0){
						}else{
							var $loanentity = $('<div id="collaContent" class="form-horizontal"></div>');
							form.find("fieldset").append($loanentity);
							$("#collaContent").empty();
							$("#collaContent").load(loadhref);
							$("#collaContent").show();
						}
					});
					$("#pledgeType").change(); 
				
				
				
				//禁用form表单中所有的选项
				disableForm("target",true);
				
				 $("#btnSubmit,#btnSubmit1,#btnSubmit2,#btnSubmit3").click(function(){
						var form = $("#inputForm");
						if(form.validate().form()){
							loading('正在提交，请稍等...');
							form.ajaxSubmit({
								type: "post",
								dataType: "json",
								success: function(result) {
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
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!-- <li class="active"><a href="javasricpt:void(0);">个人客户信息</a></li>
		<li><a href="javasricpt:void(0);">申请/审批</a></li> -->
	    <li class="active"><a href="#" onclick="javascript:return false;">押品合同信息</a></li>
		<li><a href="#" onclick="javascript:return false;">申请/审批</a></li>
	</ul>
	<div class="nav-tab-con" >
		${dfFormTpl.parsehtml }
	</div>
	<div class="nav-tab-con" style="display:none;">
	<br/>
	<form:form id="inputForm" modelAttribute="tMortgageApplay" action="${ctx}/mortgage/tMortgageApplay/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="act.taskId"/>
		<form:hidden path="act.taskName"/>
		<form:hidden path="act.taskDefKey"/>
		<form:hidden path="act.procInsId"/>
		<form:hidden path="act.procDefId"/>
		<form:hidden path="act.procDefKey"/>
		<form:hidden path="act.businessTable"/>
		<form:hidden path="mortgageContractId"/>
		<form:hidden path="auditType"/>
		<form:hidden id="flag" path="act.flag"/>
		<input type="hidden" id="pass" name="act.vars.map['pass']"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
				<label class="control-label">收押类型：</label>
				${fns:getDictLabel(tMortgageApplay.applayType, 'mortgage_applay_type', '')}
				<input type="hidden" name="applayType" value="${tMortgageApplay.applayType}">
		</div>
		 <div class="control-group">
				<label class="control-label">开始时间：</label>
				<div class="controls">
				   <input id="applyDate" name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tMortgageApplay.startTime}" pattern="yyyy-MM-dd"/>" ${edit ? '' : 'disabled'}
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</div>
				
		</div>
		<div class="control-group">
				<label class="control-label">结束时间：</label>
				<div class="controls">
				     <input id="applyDate" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tMortgageApplay.endTime}" pattern="yyyy-MM-dd"/>" ${edit ? '' : 'disabled'}
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</div>
				
		</div> 
		<div class="control-group">
			<label class="control-label">您的意见：</label>
			<div class="controls">
				<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
			</div>
		</div>
		<div class="form-actions">
	
			<c:if test="${edit}">
				<c:if test="${empty tMortgageApplay.act.procInsId}">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="申请" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
				</c:if>
				<c:if test="${!empty tMortgageApplay.act.procInsId}">
				<input id="btnSubmit1" class="btn btn-primary" type="button" value="同 意" onclick="$('#pass').val('1');$('#flag').val('1');"/>&nbsp;
				<input id="btnSubmit2" class="btn btn-primary" type="button" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('0');"/>&nbsp;
				<input id="btnSubmit3" class="btn btn-primary" type="button" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('-1');"/>&nbsp;
				</c:if>
			
			 </c:if> 
		</div>
		<act:histoicFlow procInsId="${tMortgageApplay.act.procInsId}"/>
	</form:form>
	</div>
</body>
</html>