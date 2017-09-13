<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>账户资产</title>
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
		<li class="active"><a href="${ctx}/asset/asset/userAsset">账户资产列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table class="table table-bordered">
			<tr><td>
			    <a class="btn btn-primary" href="${ctx}/trading/tradingRecord/toRecharge">充值</a>
				<a class="btn btn-primary" href="${ctx}/trading/tradingRecord/toWithdraw">提现</a>
			</td></tr>
	 </table>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户登录名</th>
				<th>总资产</th>
				<th>余额</th>
				<th>可用余额</th>
				<th>冻结资金</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					${asset.loginName}
				<td>
					<fmt:formatNumber value="${asset.totalAsset}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${asset.balance}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${asset.availableBalance}" pattern="#,#00.00#" />
				</td>
				<td>
					<fmt:formatNumber value="${asset.freezeAmount}" pattern="#,#00.00#" />
				</td>
			</tr>
		</tbody>
	</table>
	<form:form id="searchForm" modelAttribute="tradingRecord" action="${ctx}/asset/asset/userAsset" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>交易类型：</label>
				<form:select path="tradingType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('trading_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>交易时间：</label>
				<input name="beginTradingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tradingRecord.beginTradingTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				~<input name="endTradingTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tradingRecord.endTradingTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<legend>交易记录</legend>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>流水号</th>
				<th>交易类型|交易方式</th>
				<th>交易时间</th>
				<th>金额（元）</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tradingRecord">
			<tr>
				<td>${tradingRecord.seqNo }</td>
				<td>${fns:getDictLabels(tradingRecord.tradingType, 'trading_type', '')}|${tradingRecord.tradingWay}</td>
				<td><fmt:formatDate value="${tradingRecord.tradingTime }" pattern="yyyy-MM-dd"/></td>
				<td class="${tradingRecord.amount >= 0 ? 'red' : 'green' }">${tradingRecord.amount >= 0 ? '+' : '' }<fmt:formatNumber value="${tradingRecord.amount }" pattern="#,#00.00#" /></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>