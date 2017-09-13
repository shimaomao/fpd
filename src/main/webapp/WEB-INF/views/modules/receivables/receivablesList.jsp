<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<c:set var="danbao" value="<%=Cons.CompanyType.DAN_BAO%>"/>
<c:set var="daikuan" value="<%=Cons.CompanyType.DAI_KUAN%>"/>
<html>
<head>
	<title>还款业务管理</title>
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
		
		//收款收息
		function toReceive(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个要查看的合同");
				return;
			}
			var url = "${ctx}/receivables/receivables/receView?message=1&contractId="+cid[0];
			location.href = url;
		}
		
		//查看明细
		function toDetail(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个要查看的合同");
				return;
			}
			
				top.$.jBox.open(
						"iframe:${ctx}/receivables/receivables/detail?contractId="+cid[0], "查看明细",
						$(top.document).width()-100,
						$(top.document).height()-100,
						
						{
							buttons : {
								"关闭" : true
							},
							bottomText : "",
							submit : function(v, h, f) {
								
							},
							loaded : function(h) {
								$(".jbox-content", top.document).css("overflow-y",
										"hidden");
							}
						});
			
		}
		
		
		//查看明细
		function toPingzheng(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个要查看的合同");
				return;
			}
			top.$.jBox.open(
				"iframe:${ctx}/receivables/receivables/pingzheng?contractId="+cid[0], "财务凭证",
				$(top.document).width()-500,
				$(top.document).height()-200,
				{
					buttons : {
						"关闭" : true
					},
					bottomText : "",
					submit : function(v, h, f) {},
					loaded : function(h) {
						$(".jbox-content", top.document).css("overflow-y","hidden");
					}
				});
	  	  }
		
		
		//收费，担保
		function toReceiveDB(){
			var cid = getCheckValue("loanContractId");
			if(cid.length==0){
				alert("请选择一个要查看的合同");
				return;
			}
			var url = "${ctx}/receivables/receivables/receViewDB?message=1&contractId="+cid[0];
			location.href = url;
		}
		
		
		//业务冲正
		/* function toCorrecting(){
			alert("请选择一个要查看的合同");
			new jBox('Modal', {
				attach : jQuery('#test'),
				theme : 'ModalBorder',
				width : 350,
				height : 200,
				blockScroll : false,
				animation : 'zoomIn',
				draggable : 'title',
				closeButton : true,
				content : 'You can move this modal window',
				title : 'Click here to drag me around',
				overlay : false
			});
		} */
		
	</script>
</head>
<body>
     <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/receivables/receivables/">
            <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                   还款管理
			</c:if>
          <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                     担保收费
		  </c:if>
          </a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="loanContract" action="${ctx}/receivables/receivables/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>申请日期：</label>
				<input name="applyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>合同编号：</label>
				<form:input path="contractNumber" htmlEscape="false" maxlength="255" class="input-medium"/>
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
			<li><label>
                    <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
			                                            贷款期限：
			         </c:if>
			         <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			                                            担保期限：
			         </c:if>
                 </label>
				<form:input path="loanPeriod" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
<!-- 			<li><label>贷款方式：</label> -->
<%-- 				<form:radiobuttons path="loanType" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
<!-- 			</li> -->
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	
	<table class="table table-bordered">
		<tr><td>
   	<shiro:hasPermission name="receivables:receivables:edit">
   	    <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
		    <a  class="btn btn-primary"  onclick="toReceive();">收款收息</a>
		   	<a  class="btn btn-primary"  onclick="toDetail();">查看明细</a>
		   	<a  class="btn btn-primary"  onclick="toPingzheng();">业务凭证</a>                                    
		</c:if>
   	   <c:if test="${fns:getUser().company.primaryPerson==danbao}">
			<a  class="btn btn-primary"  onclick="toReceiveDB();">收费</a>     
	   </c:if>
		
	   	<!-- <a  id="test" class="btn btn-primary"  onclick="toCorrecting();">业务冲正</a> -->
	</shiro:hasPermission>
	</td></tr>
    </table>
	
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>选择</th>
				<th>合同编号</th>
				<th>申请日期</th>
				<th>客户姓名</th>
				<th>客户类型</th>
				 <c:if test="${fns:getUser().company.primaryPerson==daikuan}">
					 <th>贷款金额（元）</th>
					<th>贷款期限</th>
					<th>贷款利率(%)</th>
					<th>贷款方式</th>                
				 </c:if>
				<c:if test="${fns:getUser().company.primaryPerson==danbao}">
					<th>担保金额（元）</th>
					<th>担保期限</th>
					<th>担保费率(%)</th>
					<th>反担保方式</th>                
				 </c:if>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="loanContract">
			<tr>
				<td>
					 <input type="radio" name="loanContractId" id="loanContractId" value="${loanContract.id}"/>
				</td>
				<td>
					${loanContract.contractNumber}
				</td>
				<td>
					<fmt:formatDate value="${loanContract.applyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${loanContract.customerName}
				</td>
				<td>
				    ${fns:getDictLabel(loanContract.customerType, 'customer_type', '')}
				</td>
				<td>
					${loanContract.loanAmount}
				</td>
				<td>
					${loanContract.loanPeriod}
				</td>
				<td>
					${loanContract.loanRate}(${loanContract.loanRateType})
				</td>
				<td>
					${fns:getDictLabel(loanContract.loanType, 'loan_type', '')}
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