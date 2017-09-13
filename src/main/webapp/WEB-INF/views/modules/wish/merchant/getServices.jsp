<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/common.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/gathering.css">
</head>
<script type="text/javascript">
	$(function() {

		var lang = "zh_CN";
		if (lang == "zh_CN") {
			lang = "zh";
		} else {
			lang = "en";
		}
		$.i18n.properties({
			name : 'cas',// 资源文件名称
			path : '/web/i18n/',// 资源文件所在目录路径
			mode : 'both',// 模式：变量或 Map
			language : lang,// 对应的语言
			cache : false,
			encoding : 'UTF-8',
			async : true,
			callback : function() {// 回调方法

			}
		});
	});
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
					<span>欢迎您，1366****@qq.com</span> <a
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
			function localeChange() {
				var sel = $("#localSelect").find("option:selected");
				$.post("index.do", {
					'tradeId' : 'changeLocale',
					'locale' : sel.val()
				}, function(retData) {
					location.reload(true)
				});
			}
			$(function() {
				var loc = "zh_CN";
				if (loc) {
					var sel = document.getElementById(loc);
					sel.selected = true;
					// 			$("#localSelect option[value="+loc+"]").attr("selected", true);
				}
			})
		</script>




		<!-- 主体内容 -->
		<div class="outside mainbody">
			<div class="inner">
				<div class="publicity-box">
					<div class="publicity-inner">
						<img src="${pageContext.request.contextPath}/static/wish/img/default.png" alt="">
					</div>
					<p class="btn-box">
						<a class="btn01 padd" href="${pageContext.request.contextPath}/wish/author/preLogin">开通服务</a>
					</p>
				</div>
			</div>
		</div>



		<!-- 尾部 -->
		<div id="footer" class="outside footer">
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
							href="http://payeco.com/images/certificate/zzdx.jpg"
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
</body>
</html>