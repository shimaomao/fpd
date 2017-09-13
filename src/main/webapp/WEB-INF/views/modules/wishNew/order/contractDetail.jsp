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
						<a href="${pageContext.request.contextPath}/wish/contract/wishContract/saveWishContract">
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
						<table class="table-detail">
							<tr>
								<td>业务编号</td>
								<td>${vo.number}</td>
							</tr>
							<tr>
								<td>产品类型</td>
								<td>订单贷</td>
							</tr>
							<tr>
								<td>业务类型</td>
								<td>${vo.days}日</td>
							</tr>
							<tr>
								<td>贷款金额（元）</td>
								<td>${vo.amount}</td>
							</tr>
							<tr>
								<td>申请时间</td>
								<td>${vo.appayDate}</td>
							</tr>
							<tr>
								<td>手续费</td>
								<td>${vo.charge}</td>
							</tr>
							<tr>
								<td>实际到账金额</td>
								<td>${vo.amount-vo.charge}</td>
							</tr>
							<tr>
								<td>放款时间</td>
								<td>${vo.loanDate}</td>
							</tr>
							<tr>
								<td>放款银行账户</td>
								<td>${vo.bankNum}</td>
							</tr>
							<tr>
								<td>第一个还款日应还金额（元)</td>
								<td>${vo.firstMoney}</td>
							</tr>
							<tr>
								<td>第二个还款日应还金额（元）</td>
								<td>${vo.secondMoney}</td>
							</tr>
							<tr>
								<td>实际第一个还款日期</td>
								<td>${vo.firstAccountDate}</td>
							</tr>
							<tr>
								<td>第一个还款日实际还款额（元）</td>
								<td>${vo.realFirstMoney}</td>
							</tr>
							<tr>
								<td>实际第二个还款日期</td>
								<td>${vo.secondAccountDate}</td>
							</tr>
							<tr>
								<td>第二个还款日实际还款额（元）</td>
								<td>${vo.realSecondMoney}</td>
							</tr>
							<tr>
								<td>剩余未还金额（元）</td>
								<td>${vo.diffAmount}</td>
							</tr>
							<tr>
								<td>逾期金额（元）</td>
								<td>${vo.overAmount}</td>
							</tr>
							<tr>
								<td>逾期罚息（元）</td>
								<td>${vo.overFee}</td>
							</tr>
							<tr>
								<td>业务状态</td>
								<td>${vo.status}</td>
							</tr>
						</table>
				</div>
		</div>
	</div>
	</div>
  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%> 
</div>
</body>
</html>