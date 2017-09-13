<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<link href="${pageContext.request.contextPath}/static/web/js/widget/My97DatePicker/skin/WdatePicker.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/widget/artDialog/jquery.artDialog.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/web/js/widget/artDialog/skins/default.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/web/js/artDialogExt.js"></script>
	<script src="./js/gathering_two.js"></script>
	<!--独立的css-->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/wish/css/gathering.css">
</head>
<script type="text/javascript">
    //页面加载执行 获取R脚本中loanMinDate和loanMaxDate
/* 	$(function() {
		var amount = "${quota}";
        $.ajax({
         	type: "POST",
         	url: "${ctx}/fpd/wish/contract/wishContract/getDate",
         	data: {amount:amount},
         	dataType: "json",
         	success: function(data){
         	    if(data.quota>0){
         	    	$("#loanMinDate").val(data.loanMinDate);
         	    	$("#loanMaxDate").val(data.loanMaxDate);
         	    	$("#loanMediumDate1").val(data.loanMediumDate1);
         	    	$("#loanMediumDate2").val(data.loanMediumDate2);
         	    	$("#expirationDate").val(data.expirationDate);
         	    }else{
         	    	// $("#quota").attr("disabled",disabled); 
         	    	 $("#quota").attr("disabled","disabled");
         	    }
         	}
       });
		
        
       
        
	}); */ 
    
	function saveLoan(){
		var amount=$("#quota").val();
		//var rate = "${rate}";
		var rate="0.00125";
		
        var wishCharge=$("#interest").html().trim();
       
        if(amount>0 && rate>0){
        	 $.ajax({
              	type: "POST",
              	url: "${ctx}/fpd/wish/contract/wishContract/sureWishContract",
              	data: {amount:amount,wishCharge:wishCharge},
              	dataType: "text",//返回值非json  此处指定josn导致success不执行
              	success: function(data){
              	    if(data!=""){
              	    	var newUrl="${ctx}/fpd/wish/contract/wishContract/applySuccess";
              	    	location.href = newUrl;
              	    }
              	}
            }); 
        }else{
        	if(amount<=0){
        		alert("贷款额度不足,无法申请借款！");
        	}else if(rate<=0){
        		alert("汇率获取失败,无法申请借款！");
        	}
        	
        }
		
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
	<div class="outside mainbody">
		<div class="inner">
			<div class="sk-footer-box">

				<div class="footer-box-inner clearfix">
					<div class="fl left-cont clearfix">
						<div class="title">
							<h1>当前可收款订单总金额</h1>
						</div>
						<ul class="fl font-web le-ul">
							<li class="clearfix">
								<%-- <div class="fl li-le">
									<img src="${pageContext.request.contextPath}/static/wish/img/img1.png" alt="">
								</div> --%>
								<div class="fl li-re">
									<span>美元</span>
								</div>
							</li>
						</ul>
						<ul class="fl font-web re-ul">
							<li><fmt:formatNumber value="${sumAmount}" pattern="#,#00.00#" /></li>
						</ul>
						 <div class="title">
							<h1>&nbsp;</h1>
						</div> 
						<ul class="fl font-web le-ul">
							<li class="clearfix">
								
								<div class="fl li-re">
									<span>当前汇率</span>
								</div>
							</li>
						</ul>
						<ul class="fl font-web re-ul">
							<li><fmt:formatNumber value="${rate}" pattern="#,#00.00#" /></li>
						</ul>
						 <div class="title">
							<h1>&nbsp;</h1>
						</div> 
						<ul class="fl font-web le-ul">
							<li class="clearfix">
								
								<div class="fl li-re">
									<span>折合人民币</span>
								</div>
							</li>
						</ul>
						<ul class="fl font-web re-ul">
							<li><fmt:formatNumber value="${chianMoney}" pattern="#,#00.00#" /></li>
						</ul>
					</div>
					<div class="fl right-cont">
						<div class="title">
							<h1>收款信息</h1>
						</div>
						<ul>
							<li class="clearfix">
								<div class="fl left-item te-le">
									您想收款的金额（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<input id="quota" type="text" class="ipt02" value="${quota}" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"> 元
									</p>
									<p class="tip">
										最多可提金额<fmt:formatNumber value="${quota}" pattern="#,#00.00#" />元人民币，订单中其他2000.00元${loanMinDate},${loanMaxDate}
										人民币货款将于一般结算日期到账
									</p>
									<!-- <input id="loanMinDate" type="hidden" name="loanMinDate" value="">
									<input id="loanMaxDate" type="hidden"  name="loanMaxDate" value="">
									
									<input id="loanMediumDate1" type="hidden" name="loanMediumDate1" value="">
									<input id="loanMediumDate2" type="hidden" name="loanMediumDate2" value="">
									
									<input id="expirationDate" type="hidden" name="expirationDate" value=""> -->
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									手续费（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="interest"><fmt:formatNumber value="${interest}" pattern="#,#00.00#" /></lable>元
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									实际到账（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="diff"><fmt:formatNumber value="${quota-interest}" pattern="#,#00.00#" /></lable>元
									</p>
								</div>
							</li>
						</ul>
					</div>
				</div>

				<div class="xy-box">
					<input type="checkbox"><label class="label-btn" for="">我已阅读并同意<span class="blue-color" id="J-pop"><<秒收贷款业务服务协议>></span></label>
				</div>
				<div class="btnrow" style="text-align: center;margin-bottom: 100px;">
					<input id="parentIframe" class="btn01" name="" type="button" onclick="saveLoan();" value="申请收款">
				</div>

				<div style="display: none;overflow-y: auto;" id="J-hi-content">
					<div class="text" id="u91" style="overflow-y: auto">
						<p>
							淘宝网服务协议</p>
						<p>
							&nbsp;</p>
						<p>
							一、本服务协议双方为浙江淘宝网络有限公司（下称&ldquo;淘宝&rdquo;）与淘宝网用户，本服务协议具有合同效力。</p>
						<p>
							&nbsp;</p>
						<p>
							本服务协议内容包括协议正文及所有淘宝已经发布的或将来可能发布的各类规则。所有规则为协议不可分割的一部分，与协议正文具有同等法律效力。</p>
						<p>
							&nbsp;</p>
						<p>
							　　在本服务协议中没有以&ldquo;规则&rdquo;字样表示的链接文字所指示的文件不属于本服务协议的组成部分，而是其它内容的协议或有关参考数据，与本协议没有法律上的直接关系。</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户在使用淘宝提供的各项服务的同时，承诺接受并遵守各项相关规则的规定。淘宝有权根据需要不时地制定、修改本协议或各类规则，如本协议有任何变更，淘宝将在网站上刊载公告，通知予用户。如用户不同意相关变更，必须停止使用&ldquo;服务&rdquo;。经修订的协议一经在淘宝网公布后，立即自动生效。各类规则会在发布后生效，亦成为本协议的一部分。登录或继续使用&ldquo;服务&rdquo;将表示用户接受经修订的协议。除另行明确声明外，任何使&ldquo;服务&rdquo;范围扩大或功能增强的新内容均受本协议约束。</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户确认本服务协议后，本服务协议即在用户和淘宝之间产生法律效力。请用户务必在注册之前认真阅读全部服务协议内容，如有任何疑问，可向淘宝咨询。 1)无论用户事实上是否在注册之前认真阅读了本服务协议，只要用户点击协议正本下方的&ldquo;确认&rdquo;按钮并按照淘宝注册程序成功注册为用户，用户的行为仍然表示其同意并签署了本服务协议。 2)本协议不涉及用户与淘宝其它用户之间因网上交易而产生的法律关系及法律纠纷。</p>
						<p>
							&nbsp;</p>
						<p>
							二、 定义</p>
						<p>
							&nbsp;</p>
						<p>
							淘宝网上交易平台：有关淘宝网上交易平台上的术语或图示的含义，详见淘宝帮助。</p>
						<p>
							&nbsp;</p>
						<p>
							用户及用户注册：用户必须是具备完全民事行为能力的自然人，或者是具有合法经营资格的实体组织。无民事行为能力人、限制民事行为能力人以及无经营或特定经营资格的组织不当注册为淘宝用户或超过其民事权利或行为能力范围从事交易的，其与淘宝之间的服务协议自始无效，淘宝一经发现，有权立即注销该用户，并追究其使用淘宝网&ldquo;服务&rdquo;的一切法律责任。用户注册是指用户登陆淘宝网，并按要求填写相关信息并确认同意履行相关用户协议的过程。用户因进行交易、获取有偿服务或接触淘宝网服务器而发生的所有应纳税赋，以及一切硬件、软件、服务及其它方面的费用均由用户负责支付。淘宝网站仅作为交易地点。淘宝仅作为用户物色交易对象，就货物和服务的交易进行协商，以及获取各类与贸易相关的服务的地点。淘宝不能控制交易所涉及的物品</p>
						<p>
							&nbsp;</p>
						<p>
							的质量、安全或合法性，商贸信息的真实性或准确性，以及交易方履行其在贸易协议项下的各项义务的能力。淘宝并不作为买家或是卖家的身份参与买卖行为的本身。淘宝提醒用户应该通过自己的谨慎判断确定登录物品及相关信息的真实性、合法性和有效性。</p>
						<p>
							&nbsp;</p>
						<p>
							三、 用户权利和义务：</p>
						<p>
							&nbsp;</p>
						<p>
							用户有权利拥有自己在淘宝网的用户名及交易密码，并有权利使用自己的用户名及 密码随时登陆淘宝网交易平台。用户不得以任何形式擅自转让或授权他人使用自己的淘宝网用户名；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户有权根据本服务协议的规定以及淘宝网上发布的相关规则利用淘宝网上交易平台查询物品信息、发布交易信息、登录物品、参加网上物品竞买、与其它用户订立物品买卖合同、评价其它用户的信用、参加淘宝的有关活动以及有权享受淘宝提供的其它的有关信息服务；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户在淘宝网上交易过程中如与其他用户因交易产生纠纷，可以请求淘宝从中予以协调。用户如发现其他用户有违法或违反本服务协议的行为，可以向淘宝进行反映要求处理。如用户因网上交易与其他用户产生诉讼的，用户有权通过司法部门要求淘宝提供相关资料；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户有义务在注册时提供自己的真实资料，并保证诸如电子邮件地址、联系电话、联系地址、邮政编码等内容的有效性及安全性，保证淘宝及其他用户可以通过上述联系方式与自己进行联系。同时，用户也有义务在相关资料实际变更时及时更新有关注册资料。用户保证不以他人资料在淘宝网进行注册或认证；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户应当保证在使用淘宝网网上交易平台进行交易过程中遵守诚实信用的原则，不在交易过程中采取不正当竞争行为，不扰乱网上交易的正常秩序，不从事与网上交易无关的行为；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户不应在淘宝网网上交易平台上恶意评价其他用户，或采取不正当手段提高自身的信用度或降低其他用户的信用度；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户在淘宝网网上交易平台上不得发布各类违法或违规信息；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户在淘宝网网上交易平台上不得买卖国家禁止销售的或限制销售的物品、不得买卖侵犯他人知识产权或其它合法权益的物品，也不得买卖违背社会公共利益或公共道德的、或是淘宝认为不适合在淘宝网上销售的物品。具体内容详见《禁止和限制销售物品规则》；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户承诺自己在使用淘宝网时实施的所有行为均遵守国家法律、法规和淘宝的相关规定以及各种社会公共利益或公共道德。如有违反导致任何法律后果的发生，用户将以自己的名义独立承担所有相应的法律责任；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户同意，不对淘宝网上任何数据作商业性利用，包括但不</p>
						<p>
							&nbsp;</p>
						<p>
							限于在未经淘宝事先书面批准的情况下，以复制、传播等方式使用在淘宝网站上展示的任何资料。</p>
						<p>
							&nbsp;</p>
						<p>
							四、淘宝的权利和义务：</p>
						<p>
							&nbsp;</p>
						<p>
							淘宝有义务在现有技术上维护整个网上交易平台的正常运行，并努力提升和改进技术，使用户网上交易活动得以顺利进行；</p>
						<p>
							&nbsp;</p>
						<p>
							　　对用户在注册使用淘宝网上交易平台中所遇到的与交易或注册有关的问题及反映的情况，淘宝应及时作出回复；</p>
						<p>
							&nbsp;</p>
						<p>
							　　对于用户在淘宝网网上交易平台上的不当行为或其它任何淘宝认为应当终止服务的情况，淘宝有权随时作出删除相关信息、终止服务提供等处理，而无须征得用户的同意；</p>
						<p>
							&nbsp;</p>
						<p>
							　　因网上交易平台的特殊性，淘宝没有义务对所有用户的注册数据、所有的交易行为以及与交易有关的其它事项进行事先审查，但如存在下列情况：</p>
						<p>
							&nbsp;</p>
						<p>
							①用户或其它第三方通知淘宝，认为某个具体用户或具体交易事项可能存在重大问题；</p>
						<p>
							&nbsp;</p>
						<p>
							②用户或其它第三方向淘宝告知交易平台上有违法或不当行为的，淘宝以普通非专业交易者的知识水平标准对相关内容进行判别，可以明显认为这些内容或行为具有违法或不当性质的；</p>
						<p>
							&nbsp;</p>
						<p>
							淘宝有权根据不同情况选择保留或删除相关信息或继续、停止对该用户提供服务，并追究相关法律责任。</p>
						<p>
							&nbsp;</p>
						<p>
							用户在淘宝网上交易过程中如与其它用户因交易产生纠纷，请求淘宝从中予以调处，经淘宝审核后，淘宝有权通过电子邮件联系向纠纷双方了解情况，并将所了解的情况通过电子邮件互相通知对方；</p>
						<p>
							&nbsp;</p>
						<p>
							　　用户因在淘宝网上交易与其它用户产生诉讼的，用户通过司法部门或行政部门依照法定程序要求淘宝提供相关数据，淘宝应积极配合并提供有关资料；</p>
						<p>
							&nbsp;</p>
						<p>
							　　淘宝有权对用户的注册数据及交易行为进行查阅，发现注册数据或交易行为中存在任何问题或怀疑，均有权向用户发出询问及要求改正的通知或者直接作出删除等处理；</p>
						<p>
							&nbsp;</p>
						<p>
							　　经国家生效法律文书或行政处罚决定确认用户存在违法行为，或者淘宝有足够事实依据可以认定用户存在违法或违反服务协议行为的，淘宝有权在淘宝交易平台及所在网站上以网络发布形式公布用户的违法行为；</p>
						<p>
							&nbsp;</p>
						<p>
							　　对于用户在淘宝交易平台发布的下列各类信息，淘宝有权在不通知用户的前提下进行删除或采取其它限制性措施，包括但不限于以规避费用为目的的信息；以炒作信用为目的的信息；淘宝有理由相信存在欺诈等恶意或虚假内容的信息；淘宝有理由相信与网上交易无关或不是以交易为目的的信息；淘宝有理由相信存在恶意竞价或其它试图扰乱正常交易秩序因素的信息；淘宝有理由相信该信息违反公共利益或可能严重损害淘宝和其它用户</p>
					</div>
					<br />
				</div>

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
<script type="text/javascript">
        //动态监听计算手续费
        function OnInput (event) {
            var quota=event.target.value;
            document.getElementById("interest").innerHTML=(quota*0.01).toFixed(2); 
            document.getElementById("diff").innerHTML=(quota-quota*0.01).toFixed(2); 
            /* $.ajax({
             	type: "POST",
             	url: "${ctx}/fpd/wish/contract/wishContract/getDate",
             	data: {amount:quota},
             	dataType: "json",
             	success: function(data){
             	    if(data.quota>0){
             	    	document.getElementById("interest").innerHTML=(data.interest).toFixed(2); 
             	    	document.getElementById("diff").innerHTML=(data.quotaRate).toFixed(2); 
             	    	$("#loanMinDate").val(data.loanMinDate);
             	    	$("#loanMaxDate").val(data.loanMaxDate);
             	    	$("#loanMediumDate1").val(data.loanMediumDate1);
             	    	$("#loanMediumDate2").val(data.loanMediumDate2);
             	    	$("#expirationDate").val(data.expirationDate);
             	    }else{
             	    	// $("#quota").attr("disabled",disabled); 
             	    	 $("#quota").attr("disabled","disabled");
             	    }
             	}
           }); */
    		
            
            
        }
      
        function OnPropChanged (event) {
            if (event.propertyName.toLowerCase() == "value") {
                var quota=event.srcElement.value;
                document.getElementById("interest").innerHTML=(quota*0.01).toFixed(2); 
                document.getElementById("diff").innerHTML=(quota-quota*0.01).toFixed(2); 
            }
        }
    
</script>
</body>
</html>