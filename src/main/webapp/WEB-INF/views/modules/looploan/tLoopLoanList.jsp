<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>授信信息管理</title>
	<meta name="decorator" content="default"/>
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
			 var url = "${ctx}/looploan/tLoopLoan/form";
			 location.href = url;
		}
		
		//删除
		function toDelete(){
			var array = getCheckValue("loopLoanId");
		    if(array.length==0){
		    	alert("请选择一条授信合同!");
		    	return;
		    }
		    $.post("${ctx}/looploan/tLoopLoan/getStatus",{i:array[0]},function(data){
			    if(data == <%=Cons.LoopLoanStatus.TO_APPROVE%>){
			    	showTip("授信审批中！不能删除！");
         		}else{
         			var url = "${ctx}/looploan/tLoopLoan/delete?id="+array[0];
     		       return confirmx('确认要删除该授信信息吗？',url);
         		}
		    });
		       
		}
		
		//修改
		function toUpdate(){
			var array = getCheckValue("loopLoanId");
		    if(array.length==0){
		    	alert("请选择一条授信合同!");
		    	return;
		    }
		    $.post("${ctx}/looploan/tLoopLoan/getStatus",{i:array[0]},function(data){
		    	if(data == <%=Cons.LoopLoanStatus.TO_APPLY%>){
		    		var url = "${ctx}/looploan/tLoopLoan/form?id="+array[0];
					location.href = url;
         		}else{
         			showTip("不是[新增]状态！不能修改！");
         		}
		    });
		}
		
		//提交申请
		function toComplete(){
			var array = getCheckValue("loopLoanId");
		    if(array.length==0){
		    	alert("请选择一条授信合同!");
		    	return;
		    }
		    $.post("${ctx}/looploan/tLoopLoan/getStatus",{i:array[0]},function(data){
		    	if(data == <%=Cons.LoopLoanStatus.TO_APPLY%>){
		    		//var url = "${ctx}/act/task/dform?procDefKey=loop_loan&businessTable=t_loop_loan&businessId="+array[0];
				    var url = "${ctx}/act/task/form?procDefId=loop_loan:3:ddc804890a9d42bb8e9d0069fd825481&businessTable=t_loop_loan&businessId="+array[0];
					location.href = url;
         		}else{
         			showTip("不是[新增]状态！不能提交申请！");
         		}
		    });
			  
			    
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/looploan/tLoopLoan/">授信信息列表</a></li>
		<shiro:hasPermission name="looploan:tLoopLoan:edit"><li><a href="${ctx}/looploan/tLoopLoan/form">授信信息添加</a></li></shiro:hasPermission>
	</ul>
  <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>授信管理
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="tLoopLoan" action="${ctx}/looploan/tLoopLoan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>授信区域：</label>
				<sys:treeselect id="area" name="" value="" labelName="area" labelValue="${tLoopLoan.area}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>授信期限：</label>
				<form:input path="period" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
				<form:radiobuttons path="periodType" items="${fns:getDictList('period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</li>
		</ul>
		<ul class="ul-form">	
			<li><label>客户名称：</label>
				<sys:treeselect id="customer" name="" value="" labelName="customerName" labelValue="${tLoopLoan.customerName}"
						title="客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>授信编号：</label>
				<form:input path="loopNumber" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<shiro:hasPermission name="looploan:tLoopLoan:edit">
	<table class="table table-bordered">
		<tr><td>
		<a  class="btn btn-primary"  onclick="toAdd();">添加</a>
		<a  class="btn btn-primary"  onclick="toUpdate();">修改</a>
	   	<a  class="btn btn-primary"  onclick="toDelete();">删除</a>
	   	<a  class="btn btn-primary"  onclick="toComplete();">申请</a>
	   	</td></tr>
	</table>
 	</shiro:hasPermission>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-center">
		<thead>
			<tr>
				<th>选择</th>
				<th>授信编号</th>
				<th>授信期限</th>
				<th>客户名称</th>
				<th>授信利率(%)</th>
				<th>申请日期</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tLoopLoan">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<td>
					 <input type="radio" name="loopLoanId" value="${tLoopLoan.id}" />
				</td>
				<td><a href="${ctx}/looploan/tLoopLoan/form?id=${tLoopLoan.id}">
					${tLoopLoan.loopNumber}
				</a></td>
				<td>
					${tLoopLoan.period}(${fns:getDictLabel(tLoopLoan.periodType,'period_type','')})
				</td>
				<td>
					${tLoopLoan.customerName}
				</td>
				<td>
					${tLoopLoan.floatRate}(${fns:getDictLabel(tLoopLoan.periodType,'period_type','')})
				</td>
				<td>
					<fmt:formatDate value="${tLoopLoan.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${tLoopLoan.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${tLoopLoan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fns:getDictLabel(tLoopLoan.status, 'looploan_status', '')}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>