<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<title>易联支付跨境账户系统</title>
</head>

<body>
<!--布局头部-->
<div id="wrap">

  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishHead.jsp"%> 

	<!-- 主体内容 -->
	<div class="outside mainbody">
		<div class="inner clearfix">

			<div class="left-content-in fl">
				<div class="title"></div>
				<ul class="menu-list">
					<li>
						<a href="${pageContext.request.contextPath}/wish/order/wishOrder/list">
							提前收款
						</a>
					</li>
					<li>
						<a href="${pageContext.request.contextPath}/wish/contract/wishContract/contractList">
							融资记录
						</a>
					</li>
				</ul>
			</div>
			<div class="right-content-in fl">
				<div class="title">
				<h1>当前可用于申请贷款的订单</h1>
			</div>
			<div class="table-box">
				<div class="tbbox">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tb01">
						<colgroup><col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
							<col class="">
						</colgroup>
					<tbody>
					<tr>
							<th>交易流水号</th>
							<th>付款方(商户id)</th>
							<th>交易金额</th>
							<th>币种</th>
							<th>交易时间</th>
					</tr>
						<c:forEach items="${page.list}" var="wishOrder">
							<tr>
								<td>${wishOrder.orderId}</td>
								<td>${wishOrder.merchantId}</td>
								<td>${wishOrder.paymentAmount}</td>
								<td>美元</td>
								<td><fmt:formatDate value="${wishOrder.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							</tr>
					   </c:forEach>
					</tbody>
				</table>
				</div>
					<form class="pageForm" action="/mer/transData.do" targetfor=".data">

					<input type="hidden" name="seller_id" value="-1">

					<input type="hidden" name="tradeId" value="blank">


					<div class="page-box">
						<p>共${page.count}条记录，当前显示10条，订单总金额为<fmt:formatNumber value="${sumAmount}" pattern="#,#00.00#" />美元。</p>
					</div>

				</form>
			</div>
			<div class="sk-footer-box">
				<div class="btnrow" style="margin-bottom: 100px;">
				    <input id="btn01" class="btn01" name="" type="submit" onclick="applayLoan();" value="申请收款">
				</div>
			</div>
			</div>
	



			
		</div>
	</div>

  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%> 
</div>
</body>
</html>