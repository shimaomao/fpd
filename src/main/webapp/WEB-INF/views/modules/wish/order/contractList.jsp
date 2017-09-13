<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>易联支付跨境账户系统</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/style/main.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/style/zDialog-min.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/style/ui-dialog.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/style/validform.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/style/upload.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/laydate/laydate.js"></script>
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/web/js/laydate/need/laydate.css">
	<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/web/js/laydate/skins/default/laydate.css" id="LayDateSkin">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/layer/layer.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/web/js/layer/skin/layer.css" id="layui_layer_skinlayercss">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/jquery.i18n.properties.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/Validform_zh_v5.3.2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/Validform_toruk_expand_zh.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/mdialog.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/page.js?v=13"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/dialog-min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/widget/My97DatePicker/WdatePicker.js"></script>
	<link href="/web/js/widget/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/widget/artDialog/jquery.artDialog.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/js/widget/artDialog/skins/default.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/artDialogExt.js"></script>
	<!--独立的css-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/gathering.css">
</head>

<script type="text/javascript">
	$(function() {
		
		var height=document.body.offsetHeight;//获取当前页面总高度
		var top=height-$("#footer").height();//顶部页面的高度（注意height计算的高度没有把顶部嵌套的页面高度加进去）
		$("#footer").css("top",top);//给底部页面添加绝对路径距离上面高度
		

		var lang = "zh_CN";
		if(lang=="zh_CN"){
			lang="zh";
		}else{
			lang = "en";
		}
		$.i18n.properties({
			name:'cas',// 资源文件名称
			path:'/web/i18n/',// 资源文件所在目录路径
			mode:'both',// 模式：变量或 Map
			language:lang,// 对应的语言
			cache:false,
			encoding: 'UTF-8',
			async: true,
			callback: function() {// 回调方法

			}
		});
	});
	
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
			<div class="logo"><a href="javascript:void(0)" style="cursor:default"><img src="${pageContext.request.contextPath}/static/web/images/logo.png"></a></div>
			<div class="site_type">卖家</div>
			<div class="signout">
				<span>欢迎您，<%=session.getAttribute("wishUserName") %></span>
				<a href="https://120.197.59.147:10001/index.do?tradeId=userLogout">退出</a>
				&nbsp;&nbsp;
				<select id="localSelect" onchange="localeChange()">
					<option id="zh_CN" value="zh_CN">中文</option>
					<option id="en_US" value="en_US">English</option>
				</select>
			</div>
			<div class="menu">
				<ul>
					<li class=""><a href="https://120.197.59.147:10001/mer/balance.do?tradeId=queryBalance">账户余额</a></li>
					<li class=""><a href="https://120.197.59.147:10001/mer/account.do?tradeId=SellerBankCardInfo">提现银行账户</a></li>
					<li class=""><a href="https://120.197.59.147:10001/mer/trans.do?tradeId=blank">交易记录</a></li>
					<li class=""><a href="https://120.197.59.147:10001/mer/info.do?tradeId=SellerInfo">账户管理</a></li>
					<li class=""><a href="https://120.197.59.147:10001/mer/seller.do?tradeId=SellerRankInfo">卖家管理</a></li>
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




	<!-- 主体内容 -->
	<div id="tbody" class="outside mainbody" >
		<div class="inner">
			<div class="search-box">
				<div class="search-item">
				
				 <form:form id="searchForm" modelAttribute="tLoanContract" action="${pageContext.request.contextPath}/wish/contract/wishContract/contractList" method="post" class="breadcrumb form-search">
		             <form:input id="pageNo" path="page.pageNo" type="hidden" value="${page.pageNo}"/>
		             <form:input id="pageSize" path="page.pageSize" type="hidden" value="${page.pageSize}"/>
		             <p class="f_input">
						交易日期
						<input type="text" name="add_time_start" style="height:34px;" class="ipt-date Wdate ipt02 w_15" onfocus="WdatePicker({lang:'zh_CN',readOnly:true,dateFmt:'yyyyMMdd'})" value="" readonly="">
						-
						<input type="text" name="add_time_end" style="height:34px;" class="ipt-date Wdate ipt02 w_15" onfocus="WdatePicker({lang:'zh_CN',readOnly:true,dateFmt:'yyyyMMdd'})" value="">
					</p>
					<p class="search-btn">
					   <input id="parentIframe" class="btn01" name="" type="submit" value="查询">
				    </p>
		         </form:form>
				</div>
				
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
						</colgroup><tbody><tr>
						<th>业务编号</th>
						<th>业务类型</th>
						<th>贷款金额( 元 )</th>
						<th>申请时间</th>
						<th>放款时间</th>
						<th>订单状态</th>
					</tr>
					
					
					<c:forEach items="${page.list}" var="contract">
						<tr>
							<td>${contract.contractNumber}</td>
							<td>易借</td>
							<td>${contract.loanAmount}</td>
							<td><fmt:formatDate value="${contract.applyDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td><fmt:formatDate value="${contract.loanDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td> 
							   <c:if test="${contract.status eq '0'}">
	                                                                                   拒绝(中止)
	                            </c:if>
	                            <c:if test="${contract.status eq '1'}">
	                                                                                   审核中
	                            </c:if>
							    <c:if test="${contract.status eq '2'}">
	                                                                                   审核中
	                            </c:if>
							    <c:if test="${contract.status eq '4'}">
	                                                                                   审核中
	                            </c:if>
	                             <c:if test="${contract.status eq '5'}">
	                                                                                        审核中
	                            </c:if>
							    <c:if test="${contract.status eq '6'}">
	                                                                                        已放款
	                            </c:if>
							    <c:if test="${contract.status eq '7'}">
	                                                                                   已结清
	                            </c:if>
	                        </td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			
				</div>
				<form class="pageForm" action="/mer/transData.do" targetfor=".data">

					<input type="hidden" name="seller_id" value="-1">

					<input type="hidden" name="tradeId" value="blank">


					<!-- pagination -->
					<div class="pagination pagination-left">
						<ul class="pager">
                            ${page}
						</ul>
						<!-- <div class="results"> <span>总共0条记录</span> </div> -->
					</div>
					<!-- end pagination -->

				</form>
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
					<a href="http://weibo.com/payeco/home?topnav=1&amp;wvr=5&amp;from=company" target="_black">官方微博</a>
					<a href="http://www.payeco.com/law.html" target="_black">网站使用条款</a></div>
				<div class="fr">易联支付有限公司©版权所有&nbsp;&nbsp;ICP证：<a href="http://payeco.com/images/certificate/zzdx.jpg" target="_black" class="icp">粤B2-20120038</a> | <a href="http://payeco.com/images/certificate/zzdx.jpg" target="_black" class="icp">B2-20110256</a></div>
			</div>
		</div>
		<div class="level02">
			<div class="h_inner">
				<div class="fl"><a href="http://www.payeco.com/"><img src="${pageContext.request.contextPath}/static/web/images/logo_foot.png"></a></div>
				<div class="fr">
					<p>中国人民银行支付业务<br>
						许可证号：Z2006444000010</p>
					<p>业务指导：中国人民银行广州分行<br>
						结算银行：中信银行</p>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>