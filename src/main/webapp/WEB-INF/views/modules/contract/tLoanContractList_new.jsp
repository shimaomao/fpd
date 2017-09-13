<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>业务办理管理</title>
	<meta name="decorator" content="default"/>
  <script type="text/javascript" src="${ctxStatic}/util.js"></script>
  <script type="text/javascript" src="${ctxStatic}/vow/contract_view.js?v=1"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
    	return false;
    }
		 
		function toAdd(){
			var url = "${ctx}/contract/tLoanContract/form";
			location.href = url;
		}

		function toDelete(){
			 var array = getCheckValue("loanContractId");
			  if(array.length==0){
				  showTip("请选择一条业务合同!");
			  	return;
			  }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_REVIEW%>){
		         		   var url = "${ctx}/contract/tLoanContract/delete?id="+array[0];
		       		       return confirmx('确认要删除该业务合同吗？',url);
		         		}else{
		         			showTip("不是[新增]状态的合同！不能删除！");
		         		}
		         	}
		       });
		}
	
		
		function toUpdate(){
			 var array = getCheckValue("loanContractId");
			  if(array.length==0){
				  showTip("请选择一条业务合同!");
			  	return;
			  }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_REVIEW%> || data == <%=Cons.LoanContractStatus.TO_SUSPENSION%>){
		         			var url = "${ctx}/contract/tLoanContract/form?id="+array[0];
		      			  location.href = url;
		         		}else{
		         			showTip("不是[新增]状态的合同！不能编辑！");
		         		}
		         	}
		       });
			  
		}

		/***申请放款***/
		function requestLoans(){
			var array = getCheckValue("loanContractId");
			if(array.length==0){
				showTip("请选择一个合同模板!");
		  		return;
		  	}
	      	$.ajax({
	         	type: "POST",
	         	url: "${ctx}/lending/tLending/ajaxSave",
	         	data: {id:array[0]},
	         	dataType: "json",
	         	success: function(data){
		         	if(data.status){
		           	location.reload(true);
		         	}else{
		         		alertx("放款申请失败!原因："+data.msg);
		         	}
	         	}
	       });
		}
		
		/***签订合同***/
		function signLoanContract(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			  showTip("请选择一条业务合同!");
		  	return;
		  }
		 	$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:array[0]},
	         	dataType: "json",
	         	success: function(data){
	         		if(data == <%=Cons.LoanContractStatus.TO_SIGN%>){
	         			//var url = "${ctx}/contract/tLoanContract/toSign?id="+array[0];
	         			var url = "${ctx}/contract/contractAudit/toSign?loanContract.id="+array[0];
	         			location.href = url;
	         		}else{
	         			showTip("不是[待签订]状态的合同，不能签订合同！");
	         		}
	         	}
	       });
		}
		
		/***签订合同***/
// 		function signLoanContract(){
// 			var array = getCheckValue("loanContractId");
// 		  if(array.length==0){
// 			  showTip("请选择一条业务合同!");
// 		  	return;
// 		  }
// 		 	$.ajax({
// 	         	type: "POST",
// 	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
// 	         	data: {id:array[0]},
// 	         	dataType: "json",
// 	         	success: function(data){
<%-- 	         		if(data == <%=Cons.LoanContractStatus.TO_SIGN%>){ --%>
// 	         			 var url = "${ctx}/contract/tLoanContract/toSign?id="+array[0];
// 	         			location.href = url;
// 	         		}else{
// 	         			showTip("不是[待签订]状态的合同，不能签订合同！");
// 	         		}
// 	         	}
// 	       });
// 		}
		
		
		/****提交申请****/
		function toExamine(){
			 var array = getCheckValue("loanContractId");
			 if(array.length==0){
				 showTip("请选择一条业务合同!");
			  	return;
			  }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_REVIEW%>){
		         			/* $.ajax({
					         	type: "POST",
					         	url: "${ctx}/contract/tLoanContract/gradeExamina",
					         	data: {id:array[0]},
					         	dataType: "json",
					         	success: function(datastr){
					         		showTip("申请人评分审核结果："+datastr);
					         		return;
					         		if(datastr==0){
					         			alertx("申请人评分审核失败！");
					         		}else{  */
					         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_YWSQ.getKey()%>"},
					         					function(data) {
					         						if(!data){
					         							showTip("无此业务");
					         							return;
					         						}
					         						//alert(data.procDefId);
					         						
					         						if(!data.procDefId){
					         							showTip("此业务没有配置流程");
					         							return;
					         						}
					         						//var url = "${ctx}/contract/tLoanContract/complete?act.procDefId="+data.procDefId+"&id="+array[0];
					         							  $.ajax({
													         	type: "POST",
													         	url: "${ctx}/contract/tLoanContract/getdizhiListStatus",
													         	data: {id:array[0],type:"1"},
													         	dataType: "html",
													         	success: function(datastr){	
													         		if(datastr==1){
													         			//alert("提交异常，请先检查是否已经添加与贷款方式匹配的信息！");
													         			$.ajax({
																         	type: "POST",
																         	url: "${ctx}/contract/tLoanContract/getLoanType",
																         	data: {id:array[0]},
																         	dataType: "html",
																         	success: function(datastr){	
																         			alert(datastr);																         		
																         	}
																       });
													         		}else{
													         			//var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
													         			var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
													         			location.href = url;
													         		}
													         	}
													       });
					         					}
					         				);
					        /*   		}
					         	}
					       }); */
		         			
		         			
		         		}else{
		         			showTip("不是[新增或贷款申请不通过]状态的合同！不能提交申请！");
		         		}
		         	}
		       });
		 }
		
		/****贷前变更****/
		function toDqbg(){
			 var array = getCheckValue("loanContractId");
			 if(array.length==0){
				 showTip("请选择一条业务合同!");
			  	return;
			  }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_LOAN%>){
		         			/* $.ajax({
					         	type: "POST",
					         	url: "${ctx}/contract/tLoanContract/gradeExamina",
					         	data: {id:array[0]},
					         	dataType: "json",
					         	success: function(datastr){
					         		showTip("申请人评分审核结果："+datastr);
					         		return;
					         		if(datastr==0){
					         			alertx("申请人评分审核失败！");
					         		}else{  */
					         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_DQBG.getKey()%>"},
					         					function(data) {
					         						if(!data){
					         							showTip("无此业务");
					         							return;
					         						}
					         						//alert(data.procDefId);
					         						if(!data.procDefId){
					         							showTip("此业务没有配置流程");
					         							return;
					         						}
					         						//var url = "${ctx}/contract/tLoanContract/complete?act.procDefId="+data.procDefId+"&id="+array[0];
					         							 
								         			//var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
					         					  	//location.href = "contract/tLoanContract/toLoanTransfer?id="+array[0], ";
					         					  	//location.href = "${ctx}/contract/tLoanContract/toLoanChangeForm?act.procDefId="+data.procDefId+"act.procDefKey="+data.procDefKey+"&id="+array[0];
					         					  	var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
										         					  	location.href = url;
					         					  	
													         	
					         					}
					         				);
					        /*   		}
					         	}
					       }); */
		         			
		         			
		         		}else{
		         			showTip("不是[待放款]状态的合同！不能提交申请！");
		         		}
		         	}
		       });
		 }
		
		
		
	
		//放款审批
		<%-- function loanApply(){
			 var array = getCheckValue("loanContractId");

			    if(array.length==0){
			    	showTip("请选择一条业务合同!");
			    	return;
			    }
			    //判断状态
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_LOAN_APPROVAL%>){
		         			//判断是否已经提交放款申请
		         			$.ajax({
		        	         	type: "POST",
		        	         	url: "${ctx}/contract/tLoanContract/toCheckIfExistFksq",
		        	         	data: {id:array[0]},
		        	         	dataType: "html",
		        	         	success: function(data){
		        	         		//if(data=='N'){
		        	         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_FKSQ.getKey()%>"},
					         					function(data) {
					         						if(!data){
					         							showTip("无此业务");
					         							return;
					         						}
					         						if(!data.procDefId){
					         							showTip("此业务没有配置流程");
					         							return;
					         						}
					         						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
					         					  	location.href = url;
					         					}
					         				);   
					     				location.href = url;
		        	         		/* }else{
		        	         			showTip("已经提交放款申请，不能重复提交！");
		        	         		} */
		        	         	}
		        	       });
		         		}else{
		         			showTip("不是[放款待审批]状态的合同，不能放款申请！");
		         		}
		         	}
		       });
		} --%>
		//放款审批  添加是否收押测试
		function loanApply(){
			 var array = getCheckValue("loanContractId");

			    if(array.length==0){
			    	showTip("请选择一条业务合同!");
			    	return;
			    }
			    //判断状态
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_LOAN_APPROVAL%>){
						
						 $.ajax({
								type: "POST",
								url: "${ctx}/contract/tLoanContract/getdizhiListStatus",
								data: {id:array[0],type:"2"},
								dataType: "html",
								success: function(data){//data要收押物品的数量
									if(data==2){
										 alert("该笔合同有押物没有收押，不能放款");
									}else{
										//判断是否已经提交放款申请
									$.ajax({
										type: "POST",
										url: "${ctx}/contract/tLoanContract/toCheckIfExistFksq",
										data: {id:array[0]},
										dataType: "html",
										success: function(data){
											//if(data=='N'){
												$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_FKSQ.getKey()%>"},
														function(data) {
															if(!data){
																showTip("无此业务");
																return;
															}
															if(!data.procDefId){
																showTip("此业务没有配置流程");
																return;
															}
															var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_loan_contract&businessId="+array[0];
															location.href = url;
														}
													);   
												location.href = url;
											/* }else{
												showTip("已经提交放款申请，不能重复提交！");
											} */
										}
								   });
									}
								}
						   });
									
		         		}else{
		         			showTip("不是[放款待审批]状态的合同，不能放款申请！");
		         		}
		         	}
		       });
		}
		
		/***风险控制***/
		function riskControl(){
			var array = getCheckValue("loanContractId");
		  if(array.length==0){
			 showTip("请选择一条业务合同!");
		  	 return;
		  }
		  var url = "${ctx}/contract/tLoanContract/toRiskControl?loanContractId="+array[0];
		  location.href = url;
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li>
		<shiro:hasPermission name="contract:tLoanContract:edit"><li><a href="${ctx}/contract/tLoanContract/form">业务办理添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户名称：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label> 
			       <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额 ：
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款金额 ：
	                </c:if>
	            </label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label> 
			        <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限：
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限：
	                </c:if>
	             </label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

  	<shiro:hasPermission name="contract:tLoanContract:edit">
		<table class="table table-bordered">
			<tr>
			  <td>
			    <a class="btn btn-primary" onclick="toAdd();">添加</a>
				<a class="btn btn-primary" onclick="toUpdate();">修改</a>
			  	<a class="btn btn-primary" onclick="toDelete();">删除</a>
			  	<a class="btn btn-primary" onclick="toExamine();">提交申请</a>
				<a class="btn btn-primary" onclick="signLoanContract();">签订合同</a>
<!-- 				<a class="btn btn-primary" onclick="viewLoanContract();">查看合同</a> -->
				<c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                <a class="btn btn-primary" onclick="loanApply();">放款申请</a>
	               <!--  <a class="btn btn-primary" onclick="toDqbg();">贷前变更</a> -->
	            </c:if>
	            <%-- <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                <a class="btn btn-primary" onclick="letterLoanContract();">打印担保函</a>
	            </c:if>
				<a class="btn btn-primary" onclick="riskControl();">风险控制</a> --%>
			 </td>
		 </tr>
	  </table>
	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-center table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>客户姓名</th>
				<!-- <th>产品名称</th> -->
				<th> 
				   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保金额  (元)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款金额 (元)
	                </c:if>	
	            </th>
				<th>
				    <c:if test="${fns:getUser().company.primaryPerson==danbao}">
	                                                                 担保期限
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款期限
	                </c:if>
	           	</th>
				<th> 
				   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
		                                                         担保费率(%)
	                </c:if>
	                <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
	                                                                 贷款利率(%)
	                </c:if>
	             </th>
				<th>申请日期</th>
				<th>贷款方式</th>
				<c:if test="${isadmin}">
					<th>客户经理</th>
				</c:if>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoanContract">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${tLoanContract.id}" data-ukey="${tLoanContract.ukey}" />
				</td>
				<td><a href="${ctx}/contract/tLoanContract/detail?id=${tLoanContract.id}">
					${tLoanContract.contractNumber}
				</a>
				</td>
				<td>
					${tLoanContract.customerName}
				</td>
				<%-- <td>
					${tLoanContract.productname}
				</td> --%>
				<td>
					<fmt:formatNumber value="${tLoanContract.loanAmount}" pattern="#,#00.00#" />
				</td>
				<td>
					${tLoanContract.loanPeriod}
					(${fns:getDictLabel(tLoanContract.periodType, 'period_type', '')})
<%-- 					${(tLoanContract.loanPeriod == 1) ? '年' : ((tLoanContract.loanPeriod == 3) ? '日' : '个月')} --%>
				</td>
				<td>
					<%-- ${tLoanContract.loanRate}(${tLoanContract.loanRateType}) #3662--%>
					${tLoanContract.loanRate}%
				</td>
				<td>
					<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabels(tLoanContract.loanType, 'loan_type', '')}
				</td>
				<c:if test="${not empty tLoanContract.createBy && isadmin}">
					<td>
						${tLoanContract.createBy.name}
					</td>
				</c:if>
				<td>
					${fns:getDictLabel(tLoanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pagination">${page}</div>
</body>
</html>