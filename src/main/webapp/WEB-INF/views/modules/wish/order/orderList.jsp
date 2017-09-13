<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易联支付跨境账户系统</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/web/style/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/web/style/zDialog-min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/web/style/ui-dialog.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/web/style/validform.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/web/style/upload.css">
<!--独立的css-->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/wish/css/common.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/static/wish/css/gathering.css">

<script
	src="${pageContext.request.contextPath}/static/swagger/lib/jquery-1.8.0.min.js"
	type='text/javascript'></script>
</head>
<script type="text/javascript">
	$(function() {
	
		var height=document.body.offsetHeight;//获取当前页面总高度
		var top=height-$("#footer").height();//顶部页面的高度（注意height计算的高度没有把顶部嵌套的页面高度加进去）
		$("#footer").css("top",top);//给底部页面添加绝对路径距离上面高度
		
		var userId = "${userId}";
	<%-- 	$.ajax({
		         	type: "POST",
		         	url: "${ctx}/wish/order/wishOrder/getWishList",
		         	data: {userId:userId},
		         	dataType: "json",
		         	success: function(data){
		         		if(data == <%=Cons.LoanContractStatus.TO_REVIEW%> || data == <%=Cons.LoanContractStatus.ENDED%>){
		         		   var url = "${ctx}/contract/tLoanContract/delete?id="+array[0];
		       		       return confirmx('确认要删除该业务合同吗？',url);
		         		}else{
		         			showTip("[新增]或[中止]状态的合同才能删除！");
		         		}
		         	}
		       }); --%> 		
		
	});
	
	function applayLoan(){
		 var url="${pageContext.request.contextPath}/wish/contract/wishContract/saveWishContract?sumAmount=${sumAmount}";
		 location.href = url;
	}
	
	
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
    	return false;
    }
	
</script>
<body>
	<!--布局头部-->
	<div id="wrap">
		<div class="outside header seller">
			<div class="inner">
				<div class="logo">
					<a href="javascript:void(0)" style="cursor: default"><img
						src="${pageContext.request.contextPath}/static/web/images/logo.png"></a>
				</div>
				<div class="site_type">卖家</div>
				<div class="signout">
					<span>欢迎您，<%=session.getAttribute("wishUserName") %></span> <a
						href="https://120.197.59.147:10001/index.do?tradeId=userLogout">退出</a>
					&nbsp;&nbsp; <select id="localSelect" onchange="localeChange()">
						<option id="zh_CN" value="zh_CN">中文</option>
						<option id="en_US" value="en_US">English</option>
					</select>
				</div>
				<div class="menu">
					<ul>
						<li class=""><a
							href="https://120.197.59.147:10001/mer/balance.do?tradeId=queryBalance">账户余额</a></li>
						<li class=""><a
							href="https://120.197.59.147:10001/mer/account.do?tradeId=SellerBankCardInfo">提现银行账户</a></li>
						<li class=""><a
							href="https://120.197.59.147:10001/mer/trans.do?tradeId=blank">交易记录</a></li>
						<li class=""><a
							href="https://120.197.59.147:10001/mer/info.do?tradeId=SellerInfo">账户管理</a></li>
						<li class=""><a
							href="https://120.197.59.147:10001/mer/seller.do?tradeId=SellerRankInfo">卖家管理</a></li>
						<li class="on"><a href="">秒收贷款</a></li>
					</ul>
				</div>
			</div>
		</div>

		<script type="text/javascript">
		function localeChange(){
			var sel = $("#localSelect").find("option:selected");
			$.post("index.do",{'tradeId':'changeLocale','locale':sel.val()},function(retData){location.reload(true)});
		}
		$(function(){
			var loc = "zh_CN";
			if(loc){
				var sel = document.getElementById(loc);
				sel.selected = true;
// 			$("#localSelect option[value="+loc+"]").attr("selected", true);
			}
		})
	</script>

        <form:form id="searchForm" modelAttribute="wishOrder" action="${pageContext.request.contextPath}/wish/order/wishOrder" method="post" class="breadcrumb form-search">
		   <form:input id="pageNo" path="page.pageNo" type="hidden" value="${page.pageNo}"/>
		   <form:input id="pageSize" path="page.pageSize" type="hidden" value="${page.pageSize}"/>
		</form:form>


		<!-- 主体内容 -->
		<div class="outside mainbody">
			<div class="inner">
				<div class="title">
					<h1>当前可立即收款的订单</h1>
				</div>
				<div class="table-box">
					<div class="tbbox">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="tb01">
							<colgroup>
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
								<col class="">
							</colgroup>
							<tbody>
								<tr>
									<th>交易流水号</th>
									<th>付款方(商户id)</th>
									<th>交易金额</th>
									<!-- <th>币种</th> -->
									<th>交易时间</th>
								</tr>
								<c:forEach items="${page.list}" var="wishOrder">
									<tr>

										<td>${wishOrder.orderId}</td>
										<td>${wishOrder.merchantId}</td>
										<td>${wishOrder.paymentAmount}</td>
										<td><fmt:formatDate value="${wishOrder.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

					</div>
					<form class="pageForm" action="/mer/transData.do" targetfor=".data">

						<input type="hidden" name="seller_id" value="-1"> <input
							type="hidden" name="tradeId" value="blank">

						<!-- pagination -->
						 <div class="pagination pagination-left">
							<ul class="pager">${page}
							</ul>
							<!-- <div class="results"> <span>总共0条记录</span> </div> -->
						</div> 
						<!-- end pagination -->
						<!-- <div class="pagination pagination-left">
							<ul class="pager"><ul>
							<li class="disabled"><a href="javascript:">« 上一页</a>
							<li class="current"><a href="javascript:" >1</a>
							<li><a href="javascript:" class="toruk-jump-page">2</a>
							<li class="disabled"><a href="javascript:">下一页 »</a>
							<li class="disabled controls"><a href="javascript:">当前 <input type="text" value="1" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(this.value,30,'');" onclick="this.select();"> / <input type="text" value="30" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(1,this.value,'');" onclick="this.select();"> 条，共 1 条</a>
							</ul>
							<div style="clear:both;"></div>
							</ul>
							<div class="results"> <span>总共0条记录</span> </div>
						</div> -->

					</form>
				</div>
				<div class="sk-footer-box">
					<div class="title" style="padding-top: 0px;">
						<h1>当前可收款订单总金额</h1>
					</div>
					<div class="footer-box-inner clearfix">
						<ul class="fl font-web le-ul">
							<%-- 	<li class="clearfix">
								<div class="fl li-le">
									<img
										src="${pageContext.request.contextPath}/static/wish/img/img1.png"
										alt="">
								</div>
								<div class="fl li-re">
									<span>美元</span>
								</div>
							</li>
							 <li class="clearfix">
								<div class="fl li-le">
									<img
										src="${pageContext.request.contextPath}/static/wish/img/img1.png"
										alt="">
								</div>
								<div class="fl li-re">
									<span>港币</span>
								</div>
							</li> --%>
							<li class="clearfix">
								<div class="fl li-le">
									<img
										src="${pageContext.request.contextPath}/static/wish/img/img1.png"
										alt="">
								</div>
								<div class="fl li-re">
									<span>美元</span>
								</div>
							</li>
						</ul>
						<ul class="fl font-web re-ul">
							<!-- 	<li>0.00</li>
							<li>0.00</li> -->
							<li><fmt:formatNumber value="${sumAmount}" pattern="#,#00.00#" /></li>
						</ul>
					</div>
					<div class="btnrow"
						style="text-align: center; margin-bottom: 100px;">
						<input id="btn01" class="btn01" name="" type="submit"
							onclick="applayLoan();" value="申请收款">
					</div>
				</div>
			</div>
		</div>



		<!-- 尾部 -->
		<div id="footer" class="outside footer" style="position:absolute;bottom:0;">
			<div class="level01">
				<div class="h_inner">
					<div class="fl">
						<a href="http://www.payeco.com/company.html" target="_black">关于易联</a>
						<a href="http://www.payeco.com/company.html?tab=3" target="_black">联系我们</a>
						<a
							href="http://weibo.com/payeco/home?topnav=1&amp;wvr=5&amp;from=company"
							target="_black">官方微博</a> <a href="http://www.payeco.com/law.html"
							target="_black">网站使用条款</a>
					</div>
					<div class="fr">
						易联支付有限公司©版权所有&nbsp;&nbsp;ICP证：<a
							href="${pageContext.request.contextPath}/static/web/images/certificate/zzdx.jpg"
							target="_black" class="icp">粤B2-20120038</a> | <a
							href="http://payeco.com/images/certificate/zzdx.jpg"
							target="_black" class="icp">B2-20110256</a>
					</div>
				</div>
			</div>
			<div class="level02">
				<div class="h_inner">
					<div class="fl">
						<a href="http://www.payeco.com/"><img
							src="${pageContext.request.contextPath}/static/web/images/logo_foot.png"></a>
					</div>
					<div class="fr">
						<p>
							中国人民银行支付业务<br> 许可证号：Z2006444000010
						</p>
						<p>
							业务指导：中国人民银行广州分行<br> 结算银行：中信银行
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>