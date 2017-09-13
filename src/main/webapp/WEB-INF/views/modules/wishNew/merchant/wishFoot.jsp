<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="wishUrl" value="<%=Cons.wishUrl.WISH_URL_IP%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
 	$(document).ready(function() {
		var height = document.body.offsetHeight;//获取当前页面总高度
		var top = height - $("#footer").height();//顶部页面的高度（注意height计算的高度没有把顶部嵌套的页面高度加进去）
		$("#footer").css("top", top+50);//给底部页面添加绝对路径距离上面高
		//alert(top);

	}); 
</script>

<body>
<!-- 尾部 -->

	 <div id="footer" class="outside footer" style="position:absolute;bottom:0;">
<!-- 	 <div id="footer" class="outside footer"> -->
		<div class="level01">
			<div class="h_inner">
				<div class="fl">
					<a href="http://www.payeco.com/company.html" target="_black">关于易联</a>
					<a href="http://www.payeco.com/company.html?tab=3" target="_black">联系我们</a>
					<a href="http://weibo.com/payeco/home?topnav=1&amp;wvr=5&amp;from=company" target="_black">官方微博</a>
					<a href="http://www.payeco.com/law.html" target="_black">网站使用条款</a></div>
				<div class="fr">易联支付有限公司©版权所有&nbsp;&nbsp;ICP证：<a href="http://payeco.com/images/certificate/zzdx.jpg" target="_black" class="icp">粤B2-20120038</a> | <a href="http://payeco.com/images/certificate/zzdx.jpg" target="_black" class="icp">B2-20110256</a></div>
			</div>
		</div>
		<div class="level02">
			<div class="h_inner">
				<div class="fl"><a href="http://www.payeco.com/"><img src="${pageContext.request.contextPath}/static/wishNew/web/images/logo_foot.png"></a></div>
				<div class="fr">
					<p>中国人民银行支付业务<br>
						许可证号：Z2006444000010</p>
					<p>业务指导：中国人民银行广州分行<br>
						结算银行：中信银行</p>
				</div>
			</div>
		</div>
	</div>
</body>


</html>