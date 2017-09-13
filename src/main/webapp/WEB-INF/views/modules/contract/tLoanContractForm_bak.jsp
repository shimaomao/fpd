<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"%>
<html>
<head>
	<title>业务办理管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
    <script type="text/javascript" src="${ctxStatic}/pay_plan.js"></script>
    <script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['app/loanType'], function(lt){
			var loanTypeList = getCheckValue("loanTypeList");
			lt.initialize({
				loanTypesStr:loanTypeList.toString(),
				height:80,
				businessTable: "t_loan_contract",
				businessId: "${tLoanContract.id}",
				oper: "edit",
				nid: "${nid}"
			},"#${nid}dualLoanType");
			
			$("input[name='loanTypeList']").click(function(){
				var loanTypeList = getCheckValue("loanTypeList");
				lt.loanTypeChange({
					loanTypesStr:loanTypeList.toString(),
					height:80,
					businessTable: "t_loan_contract",
					businessId: "${tLoanContract.id}",
					oper: "edit",
					nid: "${nid}"
				});
			});


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
			//initPayDay('${tLoanContract.payDay}');//初始化付息日
			 ifShowAdvance();
			 initPlans();//初始化还款计划
			
			//附件${ctx}/files/tContractFiles/showfilelist/{业务id}
			// businesstype 附件类型、oper= edit|其他   是否可编辑
			url = "${ctx}/files/tContractFiles/showfilelist/${tLoanContract.id}.html?businesstype=<%=FileType.FILE_TYPE_LOANCONTRACT_1_1%>&oper=edit";
		    $("#${nid}filelist").load(url);
		    
// 		    var type = $("#customerType").val();
// 		    if(type.length>0){
// 		    	$("#customerTree").show();
// 		    }


		  //生成还款计划
			$("#createPlans").click(function(){
				var amount = $("#loanAmount").val();
				var loanRate = $("#loanRate").val();
				var loanPeriod = $("#loanPeriod").val();
				var loanDate = $("#loanDate").val();
				var payDay = $("#payDay").val();
				var payType = getCheckValue("payType")[0];
				var periodType = getCheckValue("periodType")[0];
				var payOptions = getCheckValue("payOptionsList").join();
				//数据校验
				if(!loanDate){
					showTip("请填写贷款申请日期");
					return;								
				}
				if(!loanPeriod){
					showTip("请填写贷款期数");
					return;					
				}
				if(!payType){
					showTip("请填写还款方式");
					return;					
				}
				if(!loanRate){
					showTip("请填写贷款利率");
					return;					
				}
				if(payType=="3" && !periodType){//按月付息到期还本 必须填还款周期
					showTip("请填写还款周期");
					return;					
				}
					
				createPayPlan({
						 amount : amount,
						 loanRate : loanRate,
						 loanPeriod : Number(loanPeriod),
						 loanDate : loanDate,
						 payType : payType,
						 periodType : periodType,
						 payDay : payDay,
						 payOptions : payOptions
				},"#showplans");
			});
		});
		
		//初始化还款计划
		function initPlans(){
			<c:if test="${fn:length(tLoanContract.repayPlanList) > 0}">
				var plangrid = createRepayPlansGrid("#showplans");
				//原还款计划数据
				<c:forEach var="p" items="${tLoanContract.repayPlanList}">
					plangrid.jqGrid("addRowData", "${p.id}", 
						{ 	id: "${p.id}",
							num: "${p.num}",
							startDate: "${p.startDate}",
							endDate: "${p.endDate}",
							accountDate: "${p.accountDate}",
							principal: "${p.principal}",
							interest: "${p.interest}"
						
						});
				</c:forEach>
			</c:if>
		}
		
		//计算还本金日期
		function initPayPrincipalDate(){
			var payType = getCheckValue("payType")[0];
			var periodType = getCheckValue("periodType")[0];
			var loanDate = $("#loanDate").val();
			var loanPeriod = $("#loanPeriod").val();
			if(!payType || !loanDate || !loanPeriod) return;
			loanPeriod = Number(loanPeriod);
			var payPrincipalDate = getPayPrincipalDate(payType,periodType,loanDate,loanPeriod);
			$("#payPrincipalDate").val(payPrincipalDate);
		}
		
		
		function getProduct(){
			var array = $("#productId").val();
	       	$.ajax({
	         	type: "POST",
	         	url: "${ctx}/product/tProduct/getProduct",
	         	data: {id:array},
	         	dataType: "json",
	         	success: function(data){
	         		if(data!=null){
	         			//document.getElementById("year_loan").checked=true; 
	         			$("#loanAmount").val(data.amountMin);
	         			$("#loanRate").val(data.rate);
	         			$("#serverFee").val(data.serverFee);	
	         			$("#mangeFee").val(data.mangeFee);	
	         			$("#advanceDamages").val(data.breakFee);	
	         			$("#lateFee").val(data.lateFee);	
	         			$("#rateDiscont").val(data.rateDiscount);	
	         			$("#ifInterestRelief").val(data.iflixiRedu);	
	         			$("#gracePeriod").val(data.gracePeriod);	
	         			$("#gracePeriodPenalty").val(data.graceFaxi);	
	         			$("#latePenalty").val(data.yuqiFaxi);	
	         			$("#latePenaltyFee").val(data.yuqiFee);	
	         			if(data.advanceRepay==1){
	         				document.getElementById("ifAdvance1").checked=true; 
	         				$("#showAdvanceDamages").show();
	         			}else{
	         				document.getElementById("ifAdvance2").checked=true; 
	         				$("#showAdvanceDamages").hide();
	         			}
	         			var strs= new Array();
	         			strs=data.loanType.split(",");       
	         			for (i=0;i<strs.length ;i++ ){    
	         				if(strs[i]==1){
	         					document.getElementById("loanTypeList1").checked=true; 
	         				}else if(strs[i]==2){
	         					document.getElementById("loanTypeList2").checked=true; 
	         				}else if(strs[i]==3){
	         					document.getElementById("loanTypeList3").checked=true; 
	         				}else if(strs[i]==4){
		         				document.getElementById("loanTypeList4").checked=true; 
	         				}
         			    } 
	         			loanTypeChange();
	         			
	         		}
	         	}
	       });
		}
		
// 		function showCustomerSelect(){
// 		 	$.ajax({
// 	         	type: "POST",
// 	         	url: "${ctx}/company/tCompany/sCustomerType",
// 	         	data: {id:$("#customerType").val()},
// 	         	dataType: "json",
// 	         	success: function(data){
// 	         	}
// 	       });
// 			$("#customerTree").show();
// 			$("#customerName").val("");
// 		}
		
		
		function ifShowAdvance(){
		  var payType =  getCheckValue("ifAdvance");//是否可提前还款,是的话显示提前还款违约金一框
		    if(payType==1){
		    	$("#showAdvanceDamages").show();
		    }else{
		    	$("#showAdvanceDamages").hide();
		    }
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/contract/tLoanContract/">业务办理列表</a></li>
		<li class="active"><a href="${ctx}/contract/tLoanContract/form?id=${tLoanContract.id}">业务办理<shiro:hasPermission name="contract:tLoanContract:edit">${not empty tLoanContract.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="contract:tLoanContract:edit">查看</shiro:lacksPermission></a></li>
	</ul>
       <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		         <%=LocationUrl.URL%>
                                             业务中心>我的业务>业务办理>编辑
	   </div>
	  </div> --%>
	<br/>
	<form:form id="inputForm" modelAttribute="tLoanContract" action="${ctx}/contract/tLoanContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<table class="table-form">
			<tr>
				<td class="tit">产品</td>
				<td>
				    <select name="productId" id="productId" class="input-xlarge " onchange="getProduct();">
					     <option value=""></option>
					     <c:forEach items="${productList}" var="product">
					         <option value="${product.id}" <c:if test="${product.id==tLoanContract.productId}">selected="selected"</c:if>>${product.name}</option>
					    </c:forEach>
					</select>
				</td>
				<td class="tit">申请日期</td>
				<td>
				     <input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${tLoanContract.applyDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
			</tr>
			<tr>
				<td class="tit">合同编号</td>
				<td>
                       <c:if test="${tLoanContract.applyDate!=null}">
                            ${tLoanContract.contractNumber}
                            <form:input path="contractNumber" cssStyle="display:none" htmlEscape="false" maxlength="245" class="input-xlarge "/>
                       </c:if>
                       <c:if test="${tLoanContract.applyDate==null}">
                            <font color="red">业务保存时自动生成</font>
                       </c:if>
                       
				</td>
				<td class="tit">客户</td>
				<td> 
<%-- 				     <form:select path="customerType" id="customerType" class="input-xlarge" style="width: 70px;" onchange="showCustomerSelect();"> --%>
<%-- 						<form:option value="" label=""/> --%>
<%-- 						<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<%-- 					</form:select>	 --%>
					<nobr>
					   <q style="display: online" id="customerTree">
					   <sys:treeselect  id="customer" isAll="false" name="customerId" value="${tLoanContract.customerId}" labelName="customerName" labelValue="${tLoanContract.customerName}"
						parentName="customerType" parentValue="${tLoanContract.customerType}" title="客户" url="/company/tCompany/treeData" allowClear="true" notAllowSelectParent="true"/>
					   </q>
					</nobr>
				</td>
			</tr>
			<tr>
				<td class="tit">贷款金额</td>
				<td>
				   	<form:input path="loanAmount" id="loanAmount" htmlEscape="false" class="input-xlarge "/>
				</td>
				<td class="tit">贷款利率</td>
				<td>
					<form:input path="loanRate" id="loanRate" htmlEscape="false" class="input-xlarge" />&nbsp;%&nbsp;/年
					<form:hidden path="loanRateType"/>
				</td>
			</tr>	
			<tr>
			    <td class="tit">贷款期限</td>
				<td>
					<form:input path="loanPeriod" id="loanPeriod" htmlEscape="false" maxlength="11" class="input-xlarge " onchange="initPayPrincipalDate();"/>
				</td>
			   	<td class="tit">还款方式</td>
				<td>
				     <form:radiobuttons path="payType"   items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" onclick="initPayPrincipalDate();initToPayUI(this.value);"/>
				</td>
			   
			   
			</tr>	
			<tr>
			 <td class="tit">还款周期</td>
				<td>
				    <form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" onclick="initPayPrincipalDate();"/>
				</td>
				<td class="tit">还款选项</td>
				<td>
				<form:checkboxes path="payOptionsList" items="${fns:getDictList('pay_options')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</td>
			</tr>	
			<tr>
				<td class="tit">放款日期</td>
				<td>
				   	<input name="loanDate" id="loanDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="<fmt:formatDate value="${tLoanContract.loanDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onchange="initPayPrincipalDate();"/>
				</td>
				<td class="tit">还本金日期</td>
				<td>
					<input name="payPrincipalDate" id="payPrincipalDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tLoanContract.payPrincipalDate}" pattern="yyyy-MM-dd"/>" />
				</td>
			</tr>	
		   <tr>
				<td class="tit">贷款地区</td>
				<td>
				   <sys:treeselect id="area" name="area.id" value="${tLoanContract.area.id}" labelName="area.name" labelValue="${tLoanContract.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				</td>
				<td class="tit">贷款方式</td>
				<td>
					 <form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onchange=""/>
				</td>
			</tr>	
			<tr>
				<td class="tit">贷款用途</td>
				<td>
					<form:select path="purposeId" class="input-xlarge ">
					   <form:option value="" label=""/>
					   <form:options items="${fns:getDictList('product_purpose')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td class="tit">贷款行业</td>
				<td>
					   <form:select path="industryId" class="input-xlarge ">
						 <form:option value="" label=""/>
						 <form:options items="${fns:getDictList('industry_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					   </form:select>
				</td>
			</tr>	
			<tr>
				<td class="tit">每期还款日</td>
				<td>
					 <form:select path="payDay"  class="input-xlarge ">
						<form:option value="" label="按实际放款日"/>
						<c:forEach var="i" begin="1" end="31" step="1"> 
					      	<form:option value="${i}" label="${i}号"/>
					    </c:forEach>
					</form:select>
				</td>
				<td class="tit">开户名</td>
				<td>
				   <form:input path="gatheringName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
	         <tr>
				<td class="tit">开户行</td>
				<td>
					<form:input path="gatheringBank" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">开户账号</td>
				<td>
					<form:input path="gatheringNumber" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		   <tr>
				<td class="tit">前期服务费(%)</td>
				<td>
					<form:input path="serverFee" id="serverFee" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				</td>
				<td class="tit">管理费（%）</td>
				<td>
					<form:input path="mangeFee" id="mangeFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		   <tr>
				<td class="tit">是否可提前还款</td>
				<td>
				 	<form:radiobuttons path="ifAdvance" onchange="ifShowAdvance();"  items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <nobr>
					   <q style="display: none" id="showAdvanceDamages">
				        	违约金%<form:input path="advanceDamages" id="advanceDamages" style="width: 130px;" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			           </q>
			        </nobr>
				</td>
				<td class="tit">滞纳金</td>
				<td>
					<form:input path="lateFee" id="lateFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		    <tr>
				<td class="tit">费率优惠率（%）</td>
				<td>
					<form:input path="rateDiscont" id="rateDiscont" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">允许利息减免（%）</td>
				<td>
					<form:input path="ifInterestRelief" id="ifInterestRelief" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
			<tr>
				<td class="tit">宽限期（天）</td>
				<td>
					<form:input path="gracePeriod" id="gracePeriod" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">宽限期罚息（%）</td>
				<td>
					<form:input path="gracePeriodPenalty" id="gracePeriodPenalty" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>	
		     <tr>
				<td class="tit">逾期罚息（%）</td>
				<td>
					<form:input path="latePenalty" id="latePenalty" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">逾期罚费（%）</td>
				<td>
					<form:input path="latePenaltyFee" id="latePenaltyFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>				
		    <tr>
				<td class="tit">备注</td>
				<td colspan="3">
				   <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</td>
			</tr>	
		</table>
		        <form:input path="isExtend" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="fiveLevel" cssStyle="display:none"  htmlEscape="false" maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="maxNumber" cssStyle="display:none" htmlEscape="false" maxlength="11" class="input-xlarge "/>
				<form:input path="guarantNumber"  cssStyle="display:none" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				<form:input path="signDate" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="1"/>
				<form:input path="status" cssStyle="display:none" htmlEscape="false"  maxlength="11" class="input-xlarge " value="${tLoanContract.status}"/>
		
		 <br>
		  <shiro:hasPermission name="contract:tLoanContract:edit">
		        <input id="createPlans" class="btn btn-primary" type="button" value="生成还款计划" />&nbsp;
		        <br>
					<div id="showplans">
				</div>
		 </shiro:hasPermission>
	    <br>
	     <table id="plan_table" class="table table-bordered" style="display: none;">
			<tr id="planTool" style="height:35px;">
			       <td>			    	
						<div id="add_delete_div" style="width:250px; float:left;">
							<a class="btn btn-primary" href="javascript:createOneRow()">增加一期</a>
							<a class="btn btn-primary" href="javascript:deleteOneRow()">删除一期</a>
						</div>
						<div id="createPayPlan-msg-Div" style="display: inline;"></div>
				   </td>
			 </tr>	
			  <tr>
			  	<td colspan="2">
				  	<!-- 用于动态产生还款计划的表格 -->
				  	<table id="planPayTable" class="table_style table_font_center">
					  	<tr>
							<td class="tit_top_bg" >期数</td>
							<td class="tit_top_bg">计划到账日</td>
							<td class="tit_top_bg">计划收入本金</td> 
							<td class="tit_top_bg">计划收入利息</td>
							<td class="tit_top_bg">计息开始日期</td>
							<td class="tit_top_bg">计息结束日期</td>
						</tr>
					</table>
					<div id="total_div" style="text-align:center;font-weight:bold;"></div>
				</td>
			</tr>	
		</table>
		
		<!-- 引用不同贷款方式对应的表单信息 -->
		<div id="${nid}dualLoanType"></div>
		
		<div id="${nid}filelist" ></div>
		
		<div class="form-actions">
			<shiro:hasPermission name="contract:tLoanContract:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>