<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效所需项目管理</title>
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
		<li class="active"><a href="${ctx}/statistics/achievements/tongjiAchievements/">绩效所需项目列表</a></li>
		<shiro:hasPermission name="statistics:achievements:tongjiAchievements:edit"><li><a href="${ctx}/statistics/achievements/tongjiAchievements/form">绩效所需项目添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="tongjiAchievements" action="${ctx}/statistics/achievements/tongjiAchievements/" method="post" class="breadcrumb form-search">
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
				<th>客户经理</th>
				<th>备注信息</th>
				<th>删除标记</th>
				<th>项目</th>
				<th>序号</th>
				<th>借款人</th>
				<th>贷款金额</th>
				<th>贷款期限</th>
				<th>年化利率</th>
				<th>月利率</th>
				<th>还款方式</th>
				<th>年化利率余额</th>
				<th>还款是否正常</th>
				<th>计提比例</th>
				<th>个人计提40%部分</th>
				<th>个人计提其中70%个人奖金</th>
				<th>个人计提10月已支出个人费用</th>
				<th>个人计提前月已支出未扣减费用</th>
				<th>个人计提本次发放个人奖励</th>
				<th>首次计提---其中30%营销费用额度</th>
				<th>60%部分</th>
				<th>到期对现其中70%个人奖金</th>
				<th>到期兑现---其中30%营销费用额度</th>
				<th>合计</th>
				<th>部门计提比例</th>
				<th>计提金额</th>
				<th>已支出部门费用</th>
				<th>可用部门费用额度</th>
				<th>公司计提比例</th>
				<th>公司计提金额</th>
				<th>计提合计</th>
				<shiro:hasPermission name="statistics:achievements:tongjiAchievements:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tongjiAchievements">
			<tr>
				<td><a href="${ctx}/statistics/achievements/tongjiAchievements/form?id=${tongjiAchievements.id}">
					${tongjiAchievements.dept}
				</a></td>
				<td>
					${tongjiAchievements.customerManager}
				</td>
				<td>
					${tongjiAchievements.remarks}
				</td>
				<td>
					${fns:getDictLabel(tongjiAchievements.delFlag, 'del_flag', '')}
				</td>
				<td>
					${tongjiAchievements.manage}
				</td>
				<td>
					${tongjiAchievements.number}
				</td>
				<td>
					${tongjiAchievements.borrower}
				</td>
				<td>
					${tongjiAchievements.loanMoney}
				</td>
				<td>
					<fmt:formatDate value="${tongjiAchievements.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${tongjiAchievements.yearRate}
				</td>
				<td>
					${tongjiAchievements.manthRate}
				</td>
				<td>
					${tongjiAchievements.repayMent}
				</td>
				<td>
					${tongjiAchievements.yearRateMoney}
				</td>
				<td>
					${tongjiAchievements.repaymentStatus}
				</td>
				<td>
					${tongjiAchievements.provisionRatio}
				</td>
				<td>
					${tongjiAchievements.fortyPercentage}
				</td>
				<td>
					${tongjiAchievements.personalMoney}
				</td>
				<td>
					${tongjiAchievements.tenPersonalMoney}
				</td>
				<td>
					${tongjiAchievements.preMonthMoney}
				</td>
				<td>
					${tongjiAchievements.personMoney}
				</td>
				<td>
					${tongjiAchievements.marketMoneyStart}
				</td>
				<td>
					${tongjiAchievements.sixPercentage}
				</td>
				<td>
					${tongjiAchievements.sevenPercentage}
				</td>
				<td>
					${tongjiAchievements.marketMoneyEnd}
				</td>
				<td>
					${tongjiAchievements.total}
				</td>
				<td>
					${tongjiAchievements.deptAccruedProportion}
				</td>
				<td>
					${tongjiAchievements.deptAccruedMoney}
				</td>
				<td>
					${tongjiAchievements.deptMoney}
				</td>
				<td>
					${tongjiAchievements.deptMoneyUse}
				</td>
				<td>
					${tongjiAchievements.compAccruedProportion}
				</td>
				<td>
					${tongjiAchievements.compAccruedMoney}
				</td>
				<td>
					${tongjiAchievements.accruedTotal}
				</td>
				<shiro:hasPermission name="statistics:achievements:tongjiAchievements:edit"><td>
    				<a href="${ctx}/statistics/achievements/tongjiAchievements/form?id=${tongjiAchievements.id}">修改</a>
					<a href="${ctx}/statistics/achievements/tongjiAchievements/delete?id=${tongjiAchievements.id}" onclick="return confirmx('确认要删除该绩效所需项目吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>