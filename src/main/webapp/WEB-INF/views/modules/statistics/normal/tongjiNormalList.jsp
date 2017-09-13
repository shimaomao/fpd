<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>正常结清期末兑现管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/statistics/normal/tongjiNormal/">正常结清期末兑现列表</a></li>
		<shiro:hasPermission name="statistics:normal:tongjiNormal:edit"><li><a href="${ctx}/statistics/normal/tongjiNormal/form">正常结清期末兑现添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tongjiNormal" action="${ctx}/statistics/normal/tongjiNormal/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部门</th>
				<th>序号</th>
				<th>贷款金额</th>
				<th>原贷款期限</th>
				<th>年化利率</th>
				<th>原还款方式</th>
				<th>年化贷款余额</th>
				<th>结清日期</th>
				<th>计提比例</th>
				<th>60%部分</th>
				<th>70%个人奖金</th>
				<th>本次到期发放</th>
				<th>其中30%</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>
				<shiro:hasPermission name="statistics:normal:tongjiNormal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tongjiNormal">
			<tr>
				<td><a href="${ctx}/statistics/normal/tongjiNormal/form?id=${tongjiNormal.id}">
					${tongjiNormal.dept}
				</a></td>
				<td>
					${tongjiNormal.number}
				</td>
				<td>
					${tongjiNormal.loanAmount}
				</td>
				<td>
					<fmt:formatDate value="${tongjiNormal.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tongjiNormal.yearRate}
				</td>
				<td>
					${tongjiNormal.oldPayPlan}
				</td>
				<td>
					${tongjiNormal.yearMoney}
				</td>
				<td>
					<fmt:formatDate value="${tongjiNormal.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tongjiNormal.ratio}
				</td>
				<td>
					${tongjiNormal.sixP}
				</td>
				<td>
					${tongjiNormal.sevenP}
				</td>
				<td>
					${tongjiNormal.duePayment}
				</td>
				<td>
					${tongjiNormal.treeP}
				</td>
				<td>
					${tongjiNormal.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${tongjiNormal.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tongjiNormal.updateBy.id}
				</td>
				<shiro:hasPermission name="statistics:normal:tongjiNormal:edit"><td>
    				<a href="${ctx}/statistics/normal/tongjiNormal/form?id=${tongjiNormal.id}">修改</a>
					<a href="${ctx}/statistics/normal/tongjiNormal/delete?id=${tongjiNormal.id}" onclick="return confirmx('确认要删除该正常结清期末兑现吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>