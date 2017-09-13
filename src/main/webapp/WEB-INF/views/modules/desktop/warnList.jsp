<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.LoanContractStatus" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>待办任务</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		/**
		 * 签收任务
		 */
		function claim(taskId) {
			$.get('${ctx}/act/task/claim' ,{taskId: taskId}, function(data) {
				if (data == 'true'){
		        	top.$.jBox.tip('签收完成');
		            location = '${ctx}/act/task/todo/';
				}else{
		        	top.$.jBox.tip('签收失败');
				}
		    });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/index/todo/todoList">待办任务</a></li>
		<li><a href="${ctx}/index/todo/historic/">已办任务</a></li>
		<shiro:hasPermission name="lending:tLending:view">
			<li ><a href="${ctx}/index/todo/financeTodoList">财务</a></li>
		</shiro:hasPermission>
	     <li  class="active"><a href="${ctx}/index/todo/warnList">提醒</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="40px;">序号</th>
				<th>提醒内容</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${new_warnloanlist}" var="contract" varStatus="st">
			      <tr>
			        <td>${st.index+1}</td>
			        <td>合同编号：${contract.contractNumber}的合同已经结清，需要资料归档，请及时处理！</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
