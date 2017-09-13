<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons" pageEncoding="UTF-8"  %>
<%@page import="com.wanfin.fpd.common.config.Cons.FModel"%>

<html>
<head>
	<title>申请退款管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var isClose = ${isClose} + "";
			if(isClose){
				parent.window.mainFrame.location.reload(); 
				parent.$.jBox.close(true); 
			}
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		
		/****提交申请****/
		function toExamine(){
			 var array = getCheckValue("reimburseID");
			 if(array.length==0){
				 showTip("请选择一条业务合同!");
			  	return;
			  }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/refund/reimburse/getReimburseStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.PreLoanInvesStatus.TO_REVIEW%>){
		         			$.post("${ctx}/product/tProduct/getBizCfgByBizCode", { "bizCode": "<%=FModel.M_BUSINESS_APPLICATION_TFSQ.getKey()%>"},
		         					function(data) {
		         						if(!data){
		         							showTip("无此业务");
		         							return;
		         						}
		         						if(!data.procDefId){
		         							showTip("此业务没有配置流程");
		         							return;
		         						}
		         						var url = "${ctx}/act/task/form?procDefId="+data.procDefId+"&businessTable=t_reimburse&businessId="+array[0];
		         					  	location.href = url;
		         					}
		         				);
		         		}else{
		         			showTip("不是[新增\审批失败]状态的申请！不能提交审批！");
		         		}
		         	}
		       });
		 }
		
		
		function refund(){
			var array = getCheckValue("reimburseID");
			  if(array.length==0){ 
			  	alert("请选择一条业务！");
			  	return;
			  }
			 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/refund/reimburse/getReimburseStatus",
		         	data: {id:array[0]},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.ReimburseStatus.REFUND%>){
		         			 showjBox("退款处理", "${ctx}/refund/reimburse/refund?id="+array[0],800,650);
		         		}else{
		         			showTip("不是[待退费]状态的申请！不能退费！");
		         		}
		         	}
		       });
			
			
		}
		
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/refund/reimburse/viewList">退费管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="reimburse" action="${ctx}/refund/reimburse/viewList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<!-- 		<ul class="ul-form"> -->
<!-- 			<li><label>立项编号：</label> -->
<%-- 				<form:input path="investigateNumber" htmlEscape="false" maxlength="64" class="input-medium"/> --%>
<!-- 			</li> -->
<!-- 			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
<!-- 			<li class="clearfix"></li> -->
<!-- 		</ul> -->
	</form:form>
		<table class="table table-bordered">
		  <tr>
			<td>
			  	<a class="btn btn-primary" onclick="toExamine();">提交审批</a>
		  	 	<shiro:hasPermission name="lending:tLending:view">
		  	 	   <a class="btn btn-primary" onclick="refund();">退费</a>
		  	    </shiro:hasPermission>
			</td>
		 </tr>
	  </table>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			     <th>选择</th>
				<th>还款月数</th>
				<th>借款人</th>
				<th>业务编号</th>
				<th>申请人</th>
				<th>申请时间</th>
				<th>贷款期限</th>
				<th>超支费用(退款金额)</th>
				<th>收款日期</th>
				<th>退费实际时间</th>
				<th>状态</th>
				<th>借款金额(元)</th>
				<shiro:hasPermission name="refund:reimburse:view"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reimburse">
			<tr>
			     <td>
					 <input type="radio" name="reimburseID" id="reimburseID" value="${reimburse.id}"/>
				</td>
				<td>
					${reimburse.backMonth}
				</td>
				<td>
					${reimburse.backName}
				</td>
				<td>
					${reimburse.contractNumber}
				</td>
				<td>
					${reimburse.customerName}
				</td>
				<td>
					<fmt:formatDate value="${reimburse.insertTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${reimburse.loanPeriod}
				</td>
				<td>
					${reimburse.backPrice}
				</td>
				<td>
					<fmt:formatDate value="${reimburse.reimburseyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${reimburse.returnTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${fns:getDictLabel(reimburse.status, 'ReimburseStatus', '')}
				</td>
				<td>
					${reimburse.jiePrice}
				</td>
				<td>
				    <shiro:hasPermission name="refund:reimburse:view">
    				    <a href="${ctx}/refund/reimburse/view?id=${reimburse.id}">查看</a>
    				</shiro:hasPermission>
    				<c:if test="${reimburse.status=='1'}">
    				    <shiro:hasPermission name="refund:reimburse:edit">
	    				  <a href="${ctx}/refund/reimburse/delete?id=${reimburse.id}">删除</a>
					    </shiro:hasPermission>
    				</c:if>
				</td>
					
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>