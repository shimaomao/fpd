<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <c:set var="edit" value="${preLoanInvestigate.act.status == 'finish' ? false : true }" /> --%>
<c:set var="edit" value="${preLoanInvestigate.act.status == 'finish' ? true : false }" />
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>业务审批</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
		$(function(){
			var HTCkeditor = CKEDITOR.replace("content");
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javasricpt:void(0);">立项申请信息</a></li>
	</ul>
	<div class="nav-tab-con" >
	  <div class="form-horizontal" style="width: 80%;">
	      <form:form id="inputForm" modelAttribute="preLoanInvestigate" action="${ctx}/preloaninvestigate/preLoanInvestigate/complete" method="post" class="form-horizontal">
			<table class="table-form" style="line-height: 25px;">
			    <!-- <tr>
					<td class="tit" colspan="4"><font style="float: left;font-weight: bold;color: #317eac;">立项申请信息</font></td>
				</tr> -->
				<tr>
				    <td class="tit">客户姓名</td>
					<td> 
					     ${preLoanInvestigate.customerName}（${fns:getDictLabel(preLoanInvestigate.customerType, 'customer_type', '')}）
					</td>
					<td class="tit">联系电话</td>
					<td>
					     ${preLoanInvestigate.customerPhone}&nbsp;
					</td>
				</tr>
				 <tr>
				     <td class="tit">立项名称</td>
					<td>
						${preLoanInvestigate.title}&nbsp;
	    			</td>
					<td class="tit">立项编号</td>
					<td> 
					    ${preLoanInvestigate.investigateNumber}&nbsp;
					</td>
				</tr>	
				<tr>
				     <td class="tit">
                         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
		                                                                 担保金额  
		                </c:if>
		                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
		                                                                 贷款金额
		                </c:if>		

                     </td>
					<td>
						${preLoanInvestigate.loanamount}&nbsp;
	    			</td>
					<td class="tit">
						<c:if test="${fns:getUser().company.primaryPerson==danbao}">
		                                                                    担保费率(%)
		                </c:if>
		                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
		                                                                 贷款利率(%)
		                </c:if>	
                   </td>
					<td> 
					    ${preLoanInvestigate.rate}&nbsp;
					</td>
				</tr>	
				  <tr>
				     <td class="tit"> 
				     <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限
	                </c:if>	
	                </td>
					<td>
						${preLoanInvestigate.period}&nbsp;
	    			</td>
					<td class="tit"> 
					<c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 反担保方式
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                               贷款方式
	                </c:if>
	               	</td>
					<td> 
					    ${fns:getDictLabels(preLoanInvestigate.loantype, 'loan_type', '')} &nbsp;
					</td>
				</tr>	
				 <tr>
				     <td class="tit">还款方式</td>
					<td>
						${fns:getDictLabels(preLoanInvestigate.paytype, 'product_paytype', '')}&nbsp;
	    			</td>
					  <td class="tit">贷款用途</td>
					<td>
					    ${fns:getDictLabels(preLoanInvestigate.purpose, 'product_purpose', '')}&nbsp;
	    			</td>
				</tr>	
				 <tr>
					<td class="tit">调查时间</td>
					<td colspan="3"> 
					    <fmt:formatDate value="${preLoanInvestigate.investigaDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>	
				 <tr> 
				     <td class="tit">调查报告</td>
					<td colspan="3">
						<form:textarea path="content" htmlEscape="false" rows="4" class="input-xxlarge " style="width:100%;" />
	    			</td>
				</tr>	
			</table>
<%-- 		<div id="${nid}filelist" ></div> --%>
             <br/>
			
				<form:hidden path="id"/>
				<form:hidden path="act.taskId"/>
				<form:hidden path="act.taskName"/>
				<form:hidden path="act.taskDefKey"/>
				<form:hidden path="act.procInsId"/>
				<form:hidden path="act.procDefId"/>
				<form:hidden path="act.procDefKey"/>
				<form:hidden id="flag" path="act.flag"/>
				<sys:message content="${message}"/>		
				<div class="control-group">
					<label class="control-label">您的意见：</label>
					<div class="controls">
						<form:textarea path="act.comment" class="required" rows="5" maxlength="20000" cssStyle="width:500px"/>
					</div>
				</div>
				<%-- <c:if test="${preLoanInvestigate.act.isEnd ne 'yes'}">
			    <div class="control-group">
			     <label class="control-label">审批人：</label>
                  <div class="controls">
                       <form:hidden id="userIdId" path="act.assigneeName"/>
		               <textarea  id="userIdName" name="" readonly="readonly" value="" checked="true" class="input-xlarge" onclick="openTreeSelectBox('userId','userId','审批人','','','','/sys/user/treeDataByCompany','sysUser');" type="text"></textarea>
		               <a id="userIdButton" href="javascript:"  checked="true" onclick="openTreeSelectBox('userId','userId','审批人','','','','/sys/user/treeDataByCompany','sysUser');" class="btn">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
                  </div>
                </div>
                </c:if> --%>
                
				<div class="form-actions">
					
				  <c:if test="${!edit && empty preLoanInvestigate.act.procInsId}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="申请" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;
					</c:if>
			
					<c:if test="${!edit && !empty preLoanInvestigate.act.procInsId}">
					  <c:if test="${preLoanInvestigate.act.status == 'todo'}">
					    <input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#pass').val('1');$('#flag').val('yes');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="驳 回上一级" onclick="$('#pass').val('0');$('#flag').val('no');"/>&nbsp;
					    <input id="btnSubmit" class="btn btn-inverse" type="submit" value="终止任务" onclick="$('#pass').val('-1');$('#flag').val('stop');"/>&nbsp;
					  </c:if>
					</c:if> 
					<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
					
				<%-- 动态生成审批按钮功能已实现，各个流程的审批页面存在flag不一致问题，待优化
				<c:if test="${empty preLoanInvestigate.act.taskId}">
				    <input id="btnSubmit" class="btn btn-primary" type="submit" value="申请"/>&nbsp;
				</c:if>
				<c:if test="${not empty preLoanInvestigate.act.taskId}">
				   <act:lineNameFlow taskId="${preLoanInvestigate.act.taskId}"/>
				</c:if> --%>
				
				</div>
				<act:histoicFlow procInsId="${preLoanInvestigate.act.procInsId}"/>
			</form:form>
	</div>
	</div>
</body>
</html>