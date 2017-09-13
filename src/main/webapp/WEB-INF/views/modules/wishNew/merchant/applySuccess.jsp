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
		<div class="inner">
			<div class="apply-result clearfix">
				<div class="left-cont fl">
					<img src="${pageContext.request.contextPath}/static/wish/img/checkright.png" alt="">
				</div>
				<div class="right-cont fl">
					<p class="title-t sh-item">恭喜您申请成功!</p>
					<p class="tip tip2">预计您的贷款会于24小时内完成审核并到账!</p>
					<a href="${pageContext.request.contextPath}/wish/contract/wishContract/contractList">进入贷款审核>></a>
				</div>
			</div>
		</div>
	</div>
 <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%> 
</div>
</body>
</html>