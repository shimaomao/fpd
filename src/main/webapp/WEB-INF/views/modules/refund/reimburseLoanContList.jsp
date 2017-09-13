<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>贷后管理</title>
	<meta name="decorator" content="default"/>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//退费申请
		function toRefund(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:cid[0]},
	         	dataType: "html",
	         	success: function(data){
	         		if(data == <%=Cons.LoanContractStatus.UN_CLEARED%>||data == <%=Cons.LoanContractStatus.CLEARED%>){
	         			showjBox("退费申请", "${ctx}/refund/reimburse/form?contractId="+cid[0]);
	         		}else{
	         			showTip("不是未结清和已结清状态的合同，无法申请退费！");
	         		}
	         	}
	       });
		}
		
		//用途跟踪
		function toUseTracking(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:cid[0]},
	         	dataType: "html",
	         	success: function(data){
	         		if(data == <%=Cons.LoanContractStatus.UN_CLEARED%>||data == '<%=Cons.LoanContractStatus.DB_TO_CHECK%>'){
	         			showjBox("检查记录", "${ctx}/postlending/usetracking/actualPurpose/list?loanContractId="+cid[0]);
	         		}else{
	         			showTip("该笔业务无需贷后检查！");
	         		}
	         	}
	       });
		}
		
		//五级分类
		function toFiveLevel(){
			var cid = getCheckValue("loanContractId");
			
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:cid[0]},
	         	dataType: "html",
	         	success: function(data){
	         		if(data == '<%=Cons.LoanContractStatus.EXTENDED%>'){
	         			showTip("已展期的合同无需五级分类处理！");
	         		}else{
	         			location.href = "${ctx}/postlending/fivelevel/fiveLevel/form?loanContractId="+cid[0];
	         		}
	         	}
	       });
			
		}
		
		//坏账处理 badDebts
		function tobadDebts(){
				var array = getCheckValue("loanContractId");
				if(array.length==0){
			  		alertx("请选择一笔数据!");
			  		return;
			  	}
		      	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/postlending/baddebts/badDebtRegist/ajaxUpdate",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
			         	if(data.status){
			         		alertx("处理成功！");
			           	     location.reload(true);
			         	}else{
			         		alertx("处理失败!原因："+data.msg);
			         	}
		         	}
		       });
			 
		}
		
		//提前还款
		function advanceApply(){
			var cid = getCheckValue("loanContractId");
			var procDefId="";
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:cid[0]},
	         	dataType: "html",
	         	success: function(data){
	         		if(data == <%=Cons.LoanContractStatus.UN_CLEARED%>){
	         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_TQHK.getKey()%>"},
	         					function(data) {
	         						if(!data){
	         							showTip("无此业务");
	         							return;
	         						}
	         						if(!data.procDefId){
	         							showTip("此业务没有配置流程");
	         							return;
	         						}
	         						procDefId=data.procDefId;
	         						$.ajax({
	         				         	type: "POST",
	         				         	url: "${ctx}/contract/tLoanContract/getNoPays",
	         				         	data: {id:cid[0]},
	         				         	dataType: "html",
	         				         	success: function(data){
	         				         		if(data==-1){
	         				         			showTip("申请失败，原还款计划不存在!");
	         				         		   }else if(data==0){
	         				         			 showTip("申请失败，原还款计划中存在未结清笔数!");
	         				         		   }else if(data==1){
	         				         			 var url = "${ctx}/act/task/form?procDefId="+procDefId+"&businessTable=t_advance&businessId="+cid[0];
	         	         					  	 //alert(url);
	         				         			 location.href = url;
	         				         		   }
	         				         		}
	         				       });
	         						
	         					  }
	         				); 
	         		}else{
	         			showTip("未结清的合同才支持提前还款！");
	         		}
	         	}
	       });
		}
		
		//展期申请
		function extendApply(){
			 var array = getCheckValue("loanContractId");
			 var procDefId="";
			    if(array.length==0){
			    	alertx("请选择一条业务合同!");
			    	return;
			    }
			    $.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getContractStatus",
		         	data: {id:array[0]},
		         	dataType: "html",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.UN_CLEARED%>){
		         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_ZQSQ.getKey()%>"},
		    						function(data) {
		    							if(!data){
		    								showTip("无此业务");
		    								return;
		    							}
		    							if(!data.procDefId){
		    								showTip("此业务没有配置流程");
		    								return;
		    							}
		    							procDefId=data.procDefId;
		         						$.ajax({
		         				         	type: "POST",
		         				         	url: "${ctx}/contract/tLoanContract/getNoPays",
		         				         	data: {id:array[0]},
		         				         	dataType: "html",
		         				         	success: function(data){
		         				         		if(data==-1){
		         				         			showTip("申请失败，原还款计划不存在!");
		         				         		   }else if(data==0){
		         				         			 showTip("申请失败，原款计划中存在未结清笔数!");
		         				         		   }else if(data==1){
		         				         			 top.$.jBox.open("iframe:${ctx}/contract/tLoanContract/extendForm?act.procDefId="+procDefId+"&parent.id="+array[0], 
		     		    					    			"展期申请",$(window).width()-100,$(top.document).height()-150,{
		     		    					    		buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
		     		    					    		bottomText:"",
		     		    					    		submit:function(v, h, f){
		     		    					    			var ifrWin = h.find("iframe")[0].contentWindow;
		     		    					    			if(v=="refresh"){
		     		    					    				ifrWin.location.reload(true);
		     		    					                	//ifrWin.clearAssign();
		     		    					    				return false;
		     		    					                }else if(v=="return"){
		     		    					                	ifrWin.history.go(-1);
		     		    					                	ifrWin.location.reload();
		     		    					    				return false;
		     		    					                }
		     		    					    		}, loaded:function(h){
		     		    					    			$(".jbox-content", top.document).css("overflow-y","hidden");
		     		    					    		}
		     		    					    	});
		         				         			 
		         				         		   }
		         				         		}
		         				       });
		    							
		    						}
		    					);
		         		}else{
		         			showTip("未结清的合同才支持展期申请！");
		         		}
		         	}
		       });
		}
		
		
		function showjBox(title, url){
			top.$.jBox.open(
					"iframe:"+url, title,
					$(window).width()-100,//$(top.document).width()-100,
					$(top.document).height()-100,//$(top.document).height()-100, 
					{
						buttons : {
							//"刷新" : "refresh",
							"关闭" : true
						},
						bottomText : "",
						submit : function(v, h, f) {
							var ifrWin = h.find("iframe")[0].contentWindow;
							if (v == "refresh") {
								ifrWin.location.reload(true);
								//ifrWin.clearAssign();
								return false;
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y",
									"hidden");
						}
					});
		}
		
		
		
		
		//资料归档
		function topigeonhole(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/contract/tLoanContract/getContractStatus",
	         	data: {id:cid[0]},
	         	dataType: "html",
	         	success: function(data){
	         		if(data == '<%=Cons.LoanContractStatus.EXTENDED%>'){
	         			showTip("已展期的合同无需资料归档！");
	         		}else{
	         			showjBox("资料归档", "${ctx}/fonds/tFiling/form?loancontractId="+cid[0]);
	         		}
	         	}
	       });
		}
		
		
		
		//解除担保
		function removeDanbao(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个对应的合同");
				return;
			}
			if(confirm("是否确认解除担保？")){
				$.ajax({
		         	type: "POST",
		         	url: "${ctx}/contract/tLoanContract/getdizhiListStatus",
		         	data: {id:cid[0],type:"3"},
		         	dataType: "html",
		         	success: function(datastr){	
		         		if(datastr==3){
		         			alert("解除失败，请检查抵质押物是否已经解押！");
		         		}else{
		         			$.ajax({
		        	         	type: "POST",
		        	         	url: "${ctx}/contract/tLoanContract/removeDanbao",
		        	         	data: {id:cid[0]},
		        	         	dataType: "json",
		        	         	success: function(data){	
		        	         		if(data.status=='1'){
		        	         			alertx("处理成功！");
						           	    location.reload(true);
		        	         		}else{
		        	         			alertx(data.message);
		        	         		}
		        	         	}
		        	       });
		         			
		         		}
		         	}
		       });
			}			
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/refund/reimburse">业务变更列表</a></li>
	</ul>
    
	<form:form id="searchForm" modelAttribute="loanContract" action="${ctx}/refund/reimburse/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>业务编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>客户姓名：</label>
				<form:input path="customerName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>
			         <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                            贷款金额：
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            担保金额：
			         </c:if>
			    </label>
				<form:input path="loanAmount" htmlEscape="false" class="input-medium"/>
			</li>
			<li><label> <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                            贷款期限：
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            担保期限：
			         </c:if></label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>

<%--    	<shiro:hasPermission name="refund:reimburse:edit"> --%>
   	<table class="table table-bordered">
   	   <tr>
   	    <td>
   	        <shiro:hasPermission name="refund:reimburse:view_re">
				<a  class="btn btn-primary"  onclick="toRefund();">退费申请</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="postlending:usetracking:actualPurpose:view">
				<a  class="btn btn-primary"  onclick="toUseTracking();">
                     <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                           贷后检查
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            保后检查
			         </c:if>
                </a>
			</shiro:hasPermission>
			<shiro:hasPermission name="contract:tLoanContract:view_extend">	
				<a  class="btn btn-primary"  onclick="extendApply();">展期申请</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="postlending:advance:advance:view">	
				<a  class="btn btn-primary"  onclick="advanceApply();">提前还款</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="postlending:fivelevel:fiveLevel:view">	
				<a  class="btn btn-primary"  onclick="toFiveLevel();">五级分类</a>
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="postlending:baddebts:badDebtRegist:view">
				<a  class="btn btn-primary"  onclick="tobadDebts();">坏账处理</a>
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="fonds:tFiling:view">
				<a  class="btn btn-primary"  onclick="topigeonhole();">资料归档</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="contract:tLoanContract:remove_DB">	
			     <a  class="btn btn-primary"  onclick="removeDanbao();">解除担保</a>
			 </shiro:hasPermission>
   	   </td>
   	   </tr>
   	</table>
<%-- 	</shiro:hasPermission> --%>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>业务编号</th>
				<th>申请日期</th>
				<th>客户姓名</th>
				<th>客户类型</th>
				 <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
					 <th>贷款金额（元）</th>
					<th>贷款期限</th>
					<th>贷款利率（%）</th>
					<th>贷款方式</th>                
				 </c:if>
				<c:if test="${fns:getUser().company.primaryPerson==danbao}">
					<th>担保金额（元）</th>
					<th>担保期限</th>
					<th>担保费率（%）</th>
					<th>反担保方式</th>                
				 </c:if>
				
				<th>五级分类</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr>
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${loanContract.id}"/>
				</td>
				<td ><a href="${ctx}/contract/tLoanContract/detail?id=${loanContract.id}">
					${loanContract.contractNumber}
					</a>
				</td>
				<td>
					<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${loanContract.customerName}
				</td>
				<td>
				   ${fns:getDictLabels(loanContract.customerType, 'customer_type', '')}
				</td>
				<td>
					${loanContract.loanAmount}
				</td>
				<td>
					${loanContract.loanPeriod}
					(${fns:getDictLabel(loanContract.periodType, 'period_type', '')})
				</td>
				<td>
					${loanContract.loanRate}(${loanContract.loanRateType})
				</td>
				<td>
					${fns:getDictLabels(loanContract.loanType, 'loan_type', '')}
				</td>
				<td>
					${fns:getDictLabel(loanContract.fiveLevel, 'five_level', '')}
				</td>
				<td>
					${fns:getDictLabel(loanContract.status, 'loan_contract_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>