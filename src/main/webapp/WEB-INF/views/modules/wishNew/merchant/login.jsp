<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
		<div class="title">
			<h1>服务协议</h1>
		</div>
			<div class="deal">
				<div class="text" id="u91">
					<p>
						提前收款融资服务协议</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;特别提醒：青海汇金金融服务有限公司与其合作金融机构（以下简称“乙方”），将共同为您（甲方）提供本合同下的人民币贷款服务。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;为了保障您的合法权益，您应该阅读并遵守本协议，请您务必审慎阅读、充分理解本协议条款内容，特别是免除或者减轻责任的条款。您以网络页面点击确认本协议，即视为您已阅读并接受本授权协议所有条款。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;鉴于甲方作为WISH平台上的卖家，通过WISH平台进行网络销售，买家通过WISH平台购买甲方所售产品，买家预支付货款将由WISH平台代为收取，待买家收货后并确认授权WISH平台进行支付后，甲方的销售回款/未来应收货款将由甲方、WISH平台共同授权委托的第三方支付公司-易联支付有限公司（以下简称“易联支付”）代收，在这一过程中存在一定的账期，基于这一交易背景下，乙方为甲方提供提前收款业务（人民币现金贷款），甲方承诺愿意将其由易联支付代收的在WISH平台上的未来应收货款作为质押物，向乙方申请提前收款融资业务。甲乙双方遵照有关法律规定，经协商一致，订立本合同：</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;第一条 申请及信息授权</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;1.1甲方需亲自通过其在易联支付服务平台注册账号校验登录向乙方发起融资贷款服务申请。甲方应妥善保管甲方的登录设备、注册账号及密码等信息，确保不向任何人泄露甲方的以上信息。因账号、密码泄露导致损失的，甲方应自行承担。甲方如发现他人冒用或盗用其账号申请贷款服务的，应当立即通知乙方，要求暂停相关服务申请。同时，甲方理解，乙方对甲方的请求采取行动需要合理期限，在此之前，贷款人对已执行的指令及（或）所导致的甲方的损失不承担任何责任。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;1.2甲方授权乙方向合法留存甲方信息和数据的第三方（包括但不限于公安机关、税务机关、工商部门等行政机关、司法机关等国家机构、服务提供方的其他关联公司、合作方）查询、了解、核实、获取有关甲方的信息和数据，包括但不限于身份、住所地、还款能力、交易的真实性、财务状况、税务信息和诉讼信息（如有）和相关证照等，并有权要求甲方提供相关文件资料，且甲方同意服务提供方有权将甲方的服务申请相关信息反馈给前述第三方。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;1.3甲方授权乙方向依法设立的征信机构、资信评估机构或有关法律、监管机构许可的类似机构（以下统称“征信机构”）查询甲方的信用信息，并同意服务提供方将甲方的服务申请相关信息提供给征信机构，服务提供方因业务需要或法律法规规章规定也可合理使用或披露该等信息。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;1.4除上述方式外，甲方同意服务提供方有权通过其他合法途径了解、核实有关甲方及关甲方关联方的信息和数据，包括但不限于身份、住所地、还款能力、交易的真实性、财务状况和诉讼信息（如有）等，并有权要求甲方提供相关文件资料。</p>
					<p>
						&nbsp;</p>
					<p>
						第二条融资事项</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.1融资金额：人民币<font color="#FF0000">${vo.money}</font>元整(大写：<font color="#FF0000">${vo.moneyCapital}</font>)</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.2融资综合服务费：</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(1)月利率：<font color="#FF0000">${vo.monthRate}%</font></p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;计算方式为：月利息=本合同规定的月利率×融资金额。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(2)月利息：人民币<font color="#FF0000">${vo.monthFee}</font>元</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(3)手续费：人民币<font color="#FF0000">${vo.wishCharge}</font>元</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(4)融资综合服务费：人民币<font color="#FF0000">${vo.sumFee}</font>元</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;计算方式为：融资综合服务费=月利息+手续费。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;收取方式：按次收取，乙方将在融资金额里直接扣除</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.3实际到账金额：</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(1)实际到账金额：人民币<font color="#FF0000">${vo.realMoney}</font>元</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;(2)实际到账金额=融资金额-融资综合服务费。乙方将委托易联支付有限公司将融资资金代付至甲方指定银行账户。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.4还款方式：甲方承诺愿意授权乙方委托易联支付有限公司将其在WISH平台上总金额为人民币【<font color="#FF0000">${vo.orderMoney}</font>】元的未来应收货款不可撤销地授权易联支付代付到乙方规定账户内。代付还款时的汇差风险由甲方自行承担，划付还款时，将以代付还款划扣时的汇率为准，扣足相应应收货款。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.5代付方式：甲方融资款放款后，乙方将同步甲方融资信息至易联支付，甲方不可撤销地授权易联支付从融资方放款当日开始，将其未来不定期从WISH平台中获取的应收货款优先代付至甲方规定账户内，直至所有融资款偿还为止。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;2.6融资期限：<font color="#FF0000">${vo.days}</font>天</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;第三条质物</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;3.1甲方用作质押的资产是指在甲方通过WISH平台进行网络销售，买家通过WISH平台购买甲方所售产品，买家预支付货款将由WISH平台代为收取，待买家收货后并确认授权WISH平台进行支付后的甲方销售回款/未来应收货款。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;3.2甲方承诺并保证本项下用于质押的应收货款真实、完整、合法、有效，甲方合法拥有应收货款并享有处分权，甲方提供的质押担保不损害任何第三人的合法利益，不违背甲方的法定与约定义务，应收货款不存在任何权利瑕疵，包括但不限于：应收货款不存在任何(包括但不限于法定、合同约定的)限制;甲方从未向，且不会向任何第三方转让或赠与该项应收货款;应收货款未被设定质押或其他任何形式的担保，未被设定为任何第三方名下的财产;应收货款将不会遭致抵销、反诉、赔偿损失或作其他扣减等;甲方在该应收货款质押后，不得作任何形式的处分(包括但不限于转让、设定质押或其他任何形式的担保等)。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;3.3甲方保证在WISH平台上进行网络销售的商业活动在甲方正常的经营范围内，通过WISH平台上与买家建立的销售关系合法有效。甲方保证质押应收货款均代表真实、合法、善意的货物(或服务) 销售且非寄售、试用、行纪或代销等交易，均处于正常、未逾期状态。甲方保证其与应收货款付款人之间不存在任何纠纷，甲方将严格按照基础交易合同的约定履行其交货等全部义务。未经甲方事先书面同意，甲方不得同意对基础交易合同(包括但不限于结算方式与结算金额)进行任何可能对甲方质权造成不利影响的变更。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;甲方保证，应收货款付款人不会主张抵销或任何其他抗辩，且该应收货款付款人与甲方之间的任何约定不会限制甲方质权的实现。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;3.4 甲方用于质押的未来应收货款不存在任何权利瑕疵，包括但不限于：未被且将不会被设置抵押、质押或其他任何形式的担保;未被设定为任何信托项下的财产;未被且将不会被任何当事人予以留置、扣押、查封;未被其他任何当事人在与甲方的任何合同约定对该货物的所有权保留。　　</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;第四条违约责任</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;4.1本协议签定后，甲、乙双方必须全面适当地履行本协议项下各自的义务及责任。</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;4.2 双方同意，如果一方违反其在本协议中所作的陈述、保证、承诺或任何其他义务，致使其他方遭受或发生损害、损失等责任，违约方须向守约方赔偿守约方因此遭受的一切经济损失。</p>
						
						
						
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;4.3甲方向乙方提供的质物合法性、无争议的处分权、及其他资料不真实，乙方可责令对方限期纠正。甲方拒不纠正时，乙方可停止发放融资，提前收回已发放的部分或全部融资，并要求甲方补偿乙方相应的损失;</p>
						
						
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;4.4在本合同有效期内，甲方擅自转让本合同项下权利、义务或与第三者发生债务纠纷，危及融资安全时，乙方可停止发放融资，并可提前收回已发放的融资本、息、费，甲方承担由此造成的一切损失。</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;4.5甲方超过融资期限仍未归还提前收回的应收货款的，需向乙方按0.065%每日支付违约金。违约金计算办法如下：违约金额=融资余额*违约天数*0.065%。</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;第五条其他条款</p>
					<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;5.1争议的解决方式：</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;甲方、乙方确认：甲方、乙方在融资、还款过程产生纠纷时，各方应当友好协商解决。协商不成时，任何一方均同意提请中国青海西宁仲裁委员会，按照申请仲裁时该会现行有效的网络仲裁规则进行网络仲裁。仲裁裁决是终局的，对双方均有约束力，任何一方均服从仲裁裁决结果，并自觉履行仲裁裁决的义务。本条款具有独立生效的效力，不因其他条款的无效而影响法律效力。</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;5.2本协议采用电子文本形式制成，并永久保存在专用服务器上备查，各方均认可该形式的协议效力。各方在该平台上点击“确认”、“提交”、“同意”等按钮，代表各方的真实意思表示。</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;5.3本协议自双方签署之日生效。甲方理解并同意通过平台上点击“确认”、“提交”、“同意”等确认方式签署本合同后即视为甲方本人真实意愿和以甲方本人名义签署，对甲方具有法律效力。甲方不得以其账户密码等账户信息被盗用或其他理由否认通过前述方式订立的电子合同对其具有的法律约束力或不按照该等合同履行义务。</p>
						<p>
						&nbsp;</p>
					<p>
						&nbsp;&nbsp;&nbsp;&nbsp;5.4甲方确认，在本协议生效且乙方已依本协议发放融资款的前提下，本协议可以作为甲方对授权易联支付有限公司进行委托代付的书面凭证。</p>
						<p>
						&nbsp;</p>
				</div>
				<br />
			</div>
			<div>
				<form:form class="vform" modelAttribute="vo" waitTip="提交中..." action="${pageContext.request.contextPath}/wish/contract/wishContract/sureWishContract" method="post">
				  <form:input path="money" type="hidden"  />
				  <form:input path="moneyCapital" type="hidden" />
				  <form:input path="monthFee" type="hidden"   />
				  <form:input path="monthRate" type="hidden"  />
				  <form:input path="orderMoney" type="hidden"  />
				  <form:input path="realMoney" type="hidden" />
				  <form:input path="sumFee" type="hidden"  />
				  <form:input path="days" type="hidden" />
				  <form:input path="wishCharge" type="hidden" />
				  
				<%--   <p class="p-text deal-title">请仔细阅读上述《易联秒收货款服务协议》，并严格遵守。若同意该服务协议，请您耐心输入以下文字：</p>
				  <p class="p-text p-color"><img src="${pageContext.request.contextPath}/static/wishNew/img/msg.png" alt=""></p>
				  <div>
				      <!--      本人已阅读全部申请材料，充分了解并清楚知晓该秒收贷款服务的相关信息，愿意遵守领用协议的各项规则 -->
					  <input type="text" class="ipt02 w_70" datatype="checkMsg" nullmsg="请填写以上信息！">
					  <span class="Validform_checktip"></span>
				  </div>   --%>
				 <div class="btnrow" style="text-align: center">
						<input id="parentIframe" class="btn01" style="margin-right:180px;" name="" type="submit" value="我已阅读并同意遵守上述协议">
				</div>
			</form:form>
		</div>
	</div>
</div>

  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%>  
</div>
</body>
</html>