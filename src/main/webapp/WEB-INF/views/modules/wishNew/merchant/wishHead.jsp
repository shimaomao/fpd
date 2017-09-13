<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="wishUrl" value="<%=Cons.wishUrl.WISH_URL_IP%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="renderer" content="webkit">
	<title>易联支付跨境账户系统</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/style/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/style/zDialog-min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/style/ui-dialog.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/style/validform.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/style/upload.css">

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/laydate/laydate.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/web/js/laydate/need/laydate.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/web/js/laydate/skins/default/laydate.css" id="LayDateSkin">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/layer/layer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/web/js/layer/skin/layer.css" id="layui_layer_skinlayercss">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/jquery.i18n.properties.min.js"></script>



	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/Validform_zh_v5.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/Validform_toruk_expand_zh.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/mdialog.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/page.js?v=13"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/dialog-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/widget/My97DatePicker/WdatePicker.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/web/js/widget/My97DatePicker/skin/WdatePicker.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/widget/artDialog/jquery.artDialog.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/wishNew/web/js/widget/artDialog/skins/default.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/web/js/artDialogExt.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/js/gathering.js"></script>
     <script type="text/javascript" src="${pageContext.request.contextPath}/static/wishNew/js/gathering_two.js"></script>
	<!--独立的css-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wishNew/css/gathering.css">
</head>
<script type="text/javascript">
	$(function() {

		var lang = "zh_CN";
		if(lang=="zh_CN"){
			lang="zh";
		}else{
			lang = "en";
		}
		$.i18n.properties({
			name:'cas',// 资源文件名称
			path:'${pageContext.request.contextPath}/static/wishNew/web/i18n/',// 资源文件所在目录路径
			mode:'both',// 模式：变量或 Map
			language:lang,// 对应的语言
			cache:false,
			encoding: 'UTF-8',
			async: true,
			callback: function() {// 回调方法

			}
		});
	});
</script>
<body>

	<div class="outside header seller">
		<div class="inner">
			<div class="logo"><a href="${wishUrl}/index.do" style="cursor:default"><img src="${pageContext.request.contextPath}/static/wishNew/web/images/logo.png"></a></div>
			<div class="site_type">卖家</div>
			<div class="signout">
				<span>欢迎您，<%=session.getAttribute("wishUserName") %></span>
				<a href="${wishUrl}/index.do?tradeId=userLogout">退出</a>
				&nbsp;&nbsp;
				<select id="localSelect" onchange="localeChange()">
					<option id="zh_CN" value="zh_CN">中文</option>
					<option id="en_US" value="en_US">English</option>
				</select>
			</div>
			<div class="menu">
				<ul>
					<li class=""><a href="${wishUrl}/mer/balance.do?tradeId=queryBalance">账户余额</a></li>
					<li class=""><a href="${wishUrl}/mer/account.do?tradeId=SellerBankCardInfo">提现银行账户</a></li>
					<li class=""><a href="${wishUrl}/mer/trans.do?tradeId=blank">交易记录</a></li>
					<li class=""><a href="${wishUrl}/mer/info.do?tradeId=SellerInfo">账户管理</a></li>
					<li class=""><a href="${wishUrl}/mer/seller.do?tradeId=SellerRankInfo">卖家管理</a></li>
					<li class="on"><a href="">金融服务</a></li>
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
			}
		})
	</script>
</body>
</html>