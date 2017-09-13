<%@ page language="java" import="com.wanfin.fpd.common.config.Cons"
	pageEncoding="UTF-8"%>
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
				<div class="title">
				<h1></h1>
				</div>

			<div class="sk-footer-box">

				<div class="footer-box-inner clearfix">
				
					<div class="fl right-cont">
						<div class="title">
							<h1>融资信息</h1>
						</div>
						<ul>
							<li class="clearfix applicat-two-box">
								<div class="fl left-item te-le" style="height:44px;line-height: 44px;">
									选择您想融资的期限
								</div>
								<div class="fl">
									<span class="span-item span-item-active" value="30">30日</span>
									<span class="span-item" value="60">60日</span>
									<span class="span-item" value="90">90日</span>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item te-le">
									融资金额( 人民币 )
								</div>
								<div class="fl right-item">
									<p>
									    <input type="hidden" id="days" value="30">
									  <!--   <input id="quota" type="text" class="ipt02" value="0.00" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"> 元
									     -->
									    <input type="text" id="quota" class="ipt02" value="0" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" 
                                         onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'1000')}else{this.value=this.value.replace(/\D/g,'')}" /> 元
									    
									    
									</p>
									<p class="tip">
										本次最多可提前结算<lable id="maxQuota">${quota}</lable>元人民币。最低贷款金额为大于等于1000元的整数。
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									月息（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="interest">0</lable>元
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									手续费（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="fee">0</lable>元
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									综合服务费（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="sumfee">0</lable>元
									</p>
								</div>
							</li>
							<li class="clearfix">
								<div class="fl left-item">
									实际到账（人民币）
								</div>
								<div class="fl right-item">
									<p>
										<lable id="diff">0</lable>元
									</p>
								</div>
							</li>
							<!-- <li class="clearfix">
								<div class="fl left-item" style="margin-top: 6px;">
									手机短信验证码
								</div>
								<div class="fl right-item">
									<p>
										<input type="text" style="width:100px;" class="ipt02"><button   id="J-send" type="button" class="btn01" style="padding:0 10px;margin-left:20px;font-size: 14px;">点击获取短信验证码</button>
									</p>
								</div>
							</li> -->
						</ul>
					</div>
				</div>
				</div>
				<script type="text/javascript">
					$(function(){
						$('.span-item').on('click',function(){
								var _that = $(this);
								_that.addClass('span-item-active').siblings('span').removeClass('span-item-active');
								$("#days").val($(this).attr("value"));
								//alert($(this).attr("value"));
								$.ajax({
									type : "POST",
									url : "${pageContext.request.contextPath}/wish/contract/wishContract/getDiffQuota",
									data : {
										'days' : $(this).attr("value")
									},
									dataType : "json",
									success : function(data) {
										document.getElementById("maxQuota").innerHTML=data.quota;
										document.getElementById("diff").innerHTML=0; 
							            document.getElementById("sumfee").innerHTML=0; 
							            document.getElementById("interest").innerHTML=0; 
							            $("#quota").val("");
									}
								});
								
								
								
								
						})	
						//验证码
						var confgData = {
							time:60,
							startTime:function(){
								var self = this;
								$('#J-send').attr('disabled',true).css('background','#dedede');
								var time = this.time;
									var timeVal = setInterval(function(){
										time --;
										if(time == 0){
											self.removeTime();
											clearInterval(timeVal);
											return;
										}
										$('#J-send').text(time+'s后可再获取');
									},1000)
							},
							removeTime:function(){
								$('#J-send').text('点击获取短信验证码');
								$('#J-send').removeAttr('disabled').css('background','');
							}
						}

						$('#J-send').on('click',function(){
							confgData.startTime();
						})
						
						 var content = $('#J-hi-content').html();

					    $('#J-pop').on('click',function(){
					        zShow(content,'秒收贷款协议','#J-pop',600,200);
					        $('.aui_state_focus .aui_content').css({
					            'height': '400px',
					            'overflow-y': 'auto'
					        })
					    })

						
						



					})
				</script>

				<div class="xy-box" style="margin-top: 10px;">
					<input id="sureId" datatype="checkSure" type="checkbox"><label class="label-btn" for="">我已阅读并同意<span class="blue-color" id="J-pop"><<订单贷业务服务协议>></span></label>
				</div>
				<div class="btnrow" style="margin-bottom: 100px;">
					<input id="parentIframe" class="btn01" name="" type="button" onclick="saveLoan();" value="确认申请">
				</div> 
     
				<div style="display: none;overflow-y: auto;" id="J-hi-content">
					<div class="text" id="u91" style="overflow-y: auto">
						<p>
							服务申请及授权协议</p>
						<p>&nbsp;</p>
					    <p>重要提示</p>
                         <p>
							&nbsp;</p>
                         <p>&nbsp;&nbsp;&nbsp;&nbsp;您正通过易联支付有限公司服务平台（下称本服务平台）向第三方资金方及其合作金融机构（以下合称“放款人”）申请“秒收货款”服务。</p>
                         <p>
							&nbsp;</p>
                         <p>&nbsp;&nbsp;&nbsp;&nbsp;为了保障您的合法权益，您应该阅读并遵守本协议，请您务必审慎阅读、充分理解本协议条款内容，特别是免除或者减轻易联支付有限公司（以下简称“易联支付”）
                                                                                     及放款人责任，或限制您权利的条款。您点击同意、使用、登录等行为即视为您已阅读并接受本授权协议所有条款。</p>
                         <p>
							&nbsp;</p>
						<p>&nbsp;“秒收货款”服务，是基于您在WISH平台上的销售货款有一定的回款账期，为提前收回货款，您主动向放款人申请并开通的现金贷款服务。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;1. 您同意并授权，易联支付可以将您所提交的申请信息（您的姓名、身份证号、联系方式、银行卡信息及相关必要信息）、资料及您在使用易联支付中的相关历史信息、支付数据信息、WISH平台的相关交易信息传送给放款人，并由其判断是否向您提供借款及具体的借款金额。您已清楚并了解，本服务平台只是为您及放款人进行信息的传递与输送，不介入您与放款人之间的相关资金借贷协议的订立与履行，您与放款人之间的合同、资金及其他事宜与本服务平台无关。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;2. 您在线填写并提交的申请信息或资料均真实、完整、有效、合法，且符合放款人提供服务的前提要求，否则，服务方有权拒绝您的申请。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;3．您同意，无论服务申请是否成功，本服务平台均无需退还您提交的身份信息及相关材料。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;4. 您授权易联支付及放款人向合法留存您信息和数据的第三方（包括但不限于公安机关、税务机关、工商部门等行政机关、司法机关等国家机构、第三方数据服务方、您的关联方）查询、了解、核实、验证、获取并留存有关您的信息和数据，包括但不限于身份、住所地、还款能力、交易的真实性、财务状况、税务信息和诉讼信息（如有）和相关证照等，并有权要求您提供相关文件资料，且您同意易联支付及放款人有权将您的服务申请相关信息反馈给前述第三方。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;　5．为便于向您提供更加优质的服务和产品，您同意并授权放款人将您享受放款人提供服务所产生的信息分享给易联支付。</p>
						<p>
							&nbsp;</p>
						
						
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;6．您知晓，由于互联网金融服务的特殊性，在为您提供服务的过程中，放款人将可能借助本服务平台专业服务能力向您提供信息展示、通知、信息收集、咨询等服务。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;7.您确认，您通过本服务平台向放款人申请贷款时，放款人将根据您提供的信息（包括平台历史交易记录、历史退货率、平台合作年限、产品妥投周期、商品确认付款周期等），对您进行综合分析评估，审核您的贷款申请并将结果反馈给本服务平台。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;8.经借款人审核通过后，放款人将委托易联支付将贷款资金直接代付至您指定账户。如发生由于贷款人系统原因或放款通道路由问题等导致放款人重复放款，您不可撤销地授权放款人委托易联支付从您在WISH平台上的未来应收账款中对重复发放的多余款项进行划回至放款人。</p>
						
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;9.为偿还贷款，您承诺将您在WISH平台上的未来应收账款用作还款质押。您同意并授权放款人委托易联支付将您在WISH平台上的未来应收账款代付到放款人指定账户内用于还款（下称“委托代付还款”）直至借款全部还完。易联支付有限公司将根据返款人的还款划扣时间、金额指令通知将您在WISH平台应收货款划付至放款人。您承诺前述还款划扣指令视同您本人发出，易联支付不介入您与放款人之间的任何纠纷。因委托代付还款产生的任何纠纷，由您与放款人自行解决。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;10. 为了持续营运本平台服务，本公司有权向您收取服务费用，具体费用标准为【  *  】。本公司拥有制订及调整服务费用项目及标准之权利。 您对本服务的使用，代表您已接受本服务的服务费用收费标准。除非另有说明或约定，您同意本公司有权自您在WISH平台上的应收货款中直接扣除上述服务费用。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;11.本授权书是您向易联支付及放款人作出的单方面授权，效力具有独立性，不因您与放款人签订的相关借款协议的任何条例无效而无效。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;12.本协议发生变更的，将通过本服务平台予以更新。若您继续使用本服务的，表示您接受变更后的协议。</p>
						<p>
							&nbsp;</p>
						<p>
							&nbsp;&nbsp;&nbsp;&nbsp;13.本协议之订立、生效、解释、履行等均适用中华人民共和国法律。因本协议而产生或与本协议相关的一切争议，不能协商解决的，均应依照中华人民共和国法律予以处理，并提请中国广州仲裁委员会裁决。</p>
						<p>
							&nbsp;</p>
						
					</div>
					<br />
				</div>

			</div>
		</div>
	</div>
  <%@ include file="/WEB-INF/views/modules/wishNew/merchant/wishFoot.jsp"%> 
</div>
</body>
<script type="text/javascript">
        //动态监听计算手续费
        function OnInput (event) {
        	
            var quota=event.target.value;
            document.getElementById("interest").innerHTML=(quota*0.01).toFixed(2); 
            var fee=$("#fee").html();
            var interest=$("#interest").html();
            document.getElementById("diff").innerHTML=(quota-(fee+interest)).toFixed(2); 
            document.getElementById("sumfee").innerHTML=(parseFloat(fee)+parseFloat(interest)); 
           
          
            //$("#sumfee").val(fee+interest);
            
        }
      
        function OnPropChanged (event) {
            if (event.propertyName.toLowerCase() == "value") {
            	 var quota=event.target.value;
                 document.getElementById("interest").innerHTML=(quota*0.01).toFixed(2); 
                 var fee=$("#fee").html();
                 var interest=$("#interest").html();
                 document.getElementById("diff").innerHTML=(quota-(fee+interest)).toFixed(2); 
                 document.getElementById("sumfee").innerHTML=(parseFloat(fee)+parseFloat(interest)); 
                 //$("#sumfee").val(fee+interest);
              }
        }
    	
    	function saveLoan(){
    		var amount=$("#quota").val();
    		//var rate = "${rate}";
    		//var quota='${quota}';
    		var quota=$("#maxQuota").html().trim();
    		
    		var days=$("#days").val();
    		var rate=0.125;
            var wishCharge=$("#interest").html().trim();
           /*  alert(amount);
            alert(quota);
            alert(rate); */
           // alert(if(parseFloat(amount)>=1000 && parseFloat(amount)<=parseFloat(quota) && rate>0));
           
            if(parseFloat(amount)>=1000 && parseFloat(amount)<=parseFloat(quota) && rate>0){
            	var sureId = $("#sureId");  
            	if(sureId.is(":checked")){ 
            		
                    $.ajax({
                     	type: "POST",
                     	url: "${pageContext.request.contextPath}/wish/contract/wishContract/getDiffTime",
                     	dataType: "json",
                     	success: function(data){
                     	    if(data.minutes>6){
                     	    	var newUrl="${pageContext.request.contextPath}/wish/contract/wishContract/submitApply?amount="+amount+"&wishCharge="+wishCharge+"&days="+days;
                       	    	location.href = newUrl;
                     	       }else{
                     	    	alertMsg('两笔借款申请的时间差不能少于6分钟！');
                     	    }   
                     	}
                   });

            	}else{
            		alertMsg('请确认服务协议！');
            	}
            }else{
            	
            	if(parseFloat(amount)<1000){
            		alertMsg('借款额度不能小于1000,无法申请借款！');
            	}else if(parseFloat(amount)>parseFloat(quota)){
            		alertMsg('借款金额不能大于可用额度,无法申请借款！');
            	}else if(rate<=0){
            		alertMsg('汇率获取失败,无法申请借款！');
            	}
            	
            }
    		
    	}
</script>
</html>