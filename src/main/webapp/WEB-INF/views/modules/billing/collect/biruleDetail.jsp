<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费服务详细信息</title>
	<meta name="decorator" content="default"/>
	    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
	<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
	<script src="${ctxStatic}/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<style type="text/css">
		.group .title{ min-height: 30px; border-bottom:1px solid #eee; margin-bottom: 5px; }
		.group .module{ min-height: 150px; margin-bottom: 30px; }
	</style>
	<script type="text/javascript">
		$(document).ready(function() {});
		function ajaxOpenServer(){
	 		var jsonstr="{'message':[{'desc':'"+$("#desc").val()+"','processCode':'"+$("#processCode").val()+"','SynAddress':'"+$("#SynAddress").val()+"','AsynAddress':'"+$("#AsynAddress").val()+"','version':'"+$("#version").val()+"','merchantno':'"+$("#merchantno").val()+"','merchantPwd':'"+$("#merchantPwd").val()+"','remark':'"+$("#remark").val()+"','terminalno':'"+$("#terminalno").val()+"','merchantname':'"+$("#merchantname").val()+"','phone':'"+$("#phone").val()+"','card':'"+$("#card").val()+"','idcard':'"+$("#idcard").val()+"','name':'"+$("#name").val()+"','bankAddress':'"+$("#bankAddress").val()+"','beneficiary':'"+$("#beneficiary").val()+"','TransData':'"+$("#TransData").val()+"','id':'"+$("#id").val()+"','money':'"+$("#money").val()+"','idcardtype':'"+$("#idcardtype").val()+"','curcode':'"+$("#curcode").val()+"',}]}";
	 		$.ajax({
	         	type: "POST",
	         	url: "${ctx}/billing/collect/biCollect/paymoney",
	         	data: {message:jsonstr},
	         	dataType: "text",
	         	success: function(data){
	         		alert("支付成功之后，去已开通服务菜单查看当前服务开通情况");
	         		//$("#btnCancel").trigger("click");
	         		window.open("https://test.payeco.com:9443/DnaOnlineTest/servlet/DnaPayB2C?request_text="+data);  
	         	}
	        });
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">收费服务&gt;</a>
				<a href="#">收费服务&gt;</a>
				<a href="#">支付</a>
			</div>
		</div>
	</div>
<%-- 	<form:form id="inputForm" modelAttribute="biCollect" action="${ctx}/billing/collect/biCollect/paymoney" method="post" class="form-horizontal"> --%>
		<input type="hidden" name="desc" id="desc" value="Test Decription"/>
	    <input type="hidden" name="processCode" id="processCode" value="190011"/>
	    <input type="hidden" name="SynAddress" id="SynAddress" value="http://localhost:8081/fpd/servlet/CallBack?m=font"/>
	    <input type="hidden" name="AsynAddress" id="AsynAddress" value="http://localhost:8081/fpd/servlet/CallBack?m=back"/>
		<input type="hidden" name="version" id="version" value="2.0.0"/>
		<input type="hidden" name="merchantno" id="merchantno" value="302020000114"/>
		<input type="hidden" name="merchantPwd" id="merchantPwd" value="123456"/>
		<input type="hidden" name="remark" id="remark" value=""/>
		<input type="hidden" name="terminalno" id="terminalno" value=""/>
		<input type="hidden" name="merchantname" id="merchantname" value=""/>
		<input type="hidden" name="phone" id="phone" value=""/>
		<input type="hidden" name="card" id="card" value=""/>
		<input type="hidden" name="idcard" id="idcard" value=""/>
		<input type="hidden" name="name" id="name" value=""/>
		<input type="hidden" name="bankAddress" id="bankAddress" value=""/>
	    <input type="hidden" name="beneficiary" id="beneficiary" value=""/>
	    <input type="hidden" name="TransData" id="TransData" value=""/>
		<input type="hidden" name="id" id="id" value="${biRule.id}"/>
		
		<div class="box-down" >
		<div class="box group">
			    <div class="col-sm-6 col-md-4 col-md-4 col-lg-3 " style="width: 100%">
		          <div class="thumbnail">
		            <div class="caption">
		              <h4 class="text-center"><a href="#" title="万众服务"  >${biRule.name}</a></h4>
		              <hr/>
	              		<div style="padding-top: 4px;">单价：${biRule.price.price}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</div>
						<div style="padding-top: 4px;">平均价：${biRule.averagePrice}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</div>
						<div style="padding-top: 4px;">数量/单位： <c:if test="${biRule.price.type eq 1}">${biRule.unitVal}${fns:getDictLabel(biRule.unit, 'biling_rule_unit', '')}</c:if>
							 <c:if test="${biRule.price.type eq 2}">${biRule.number}${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</c:if>
						</div>
						<div style="padding-top: 4px;">
							总价:${biRule.price.price*biRule.number}元
						</div>
						<div style="padding-top: 4px;">
							折后价:${biRule.totalPrice}
							<input type="text" name="money" id="money" value="0.3" style="width: 140px;"/>
						</div>
						<div style="padding-top: 4px;">
							优惠率：${fns:getDictLabel(biRule.rate, 'biling_rule_rate', '')}
						</div>
						<div style="padding-top: 4px;">
							  期限：<c:if test="${biRule.price.type eq 1}">${biRule.totalTime}</c:if>
							 <c:if test="${biRule.price.type eq 2}">不限</c:if>
						</div>
						<div style="padding-top: 4px;">
							 说明：${biRule.remarks}
						</div>
						<div style="padding-top: 4px;">
							发布时间：<fmt:formatDate value="${biRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</div>
						<div style="padding-top: 4px;">
						    证件类型：
						    <select name="idcardtype" id="idcardtype" style="height: 24px;width: 140px;">
							    <option value="01">身份证</option>
							    <option value="02">护照</option>
							    <option value="03">军人证</option>
							    <option value="04">台胞证</option>
							    <option value="05">回乡证</option>
							    <option value="06">港澳通行证</option>
							</select><span style="color: red;font-size:25px;">*</span>
						</div>
						<div style="padding-top: 4px;">
						    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;币种：
						    <select name="curcode" id="curcode" style="height: 24px;width: 140px;">
								 <option value="CNY">CNY-人民币</option>
								 <option value="HKD">HKD-港币</option>
								 <option value="USD">USD-美元</option>
							 </select><span style="color: red;font-size:25px;">*</span>
						</div>
						<hr/>
						<div><code><small>${biRule.price.element.name}</small></code></div>
		            	<div class="text-right">
							<input id="btnSubmit" class="btn btn-primary" type="submit" onclick="ajaxOpenServer();" value="提交"/>
							<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
						</div>
		            </div>
		          </div>
		        </div>
		        
		        
		        <div class="col-sm-6 col-md-4 col-md-4 col-lg-3 " style="width: 100%;">
		          <div class="thumbnail">
		            <div class="caption">
						<h4 class="text-center">注意事项</h4><hr/>
						<div>1.商户系统将下单报文发送到DNA Online系统；</div><hr/>
						<div>2.DNA Online系统将下单请求提交到易联手机支付平台；</div><hr/>
						<div>3.易联手机支付平台将下单结果返回给 Online系统；</div><hr/>
						<div>4.用户在Online系统填写支付信息(卡号,手机号，身份证号，姓名，银行地址等)</div><hr/>
						<div>5.Online系统将用途填写的支付信息提交到易联手机支付平台校验；</div><hr/>
						<div>6.易联手机支付平台将校验结果返回给Online系统；</div><hr/>
						<div>7.如果易联手机支付平台需要风险控制资料，Online系统提示用户输入风险控制资料（短信、身份证户籍地址、身份证照片等）；</div><hr/>
						<div>8.Online系统将支付请求提交到易联手机支付平台；</div><hr/>
						<div>9.易联手机支付平台将支付结果返回给Online系统；</div><hr/>
						<div>10.Online系统向用户显示支付结果；</div><hr/>
						<div>11.Online系统将支付结果同步返回给商户系统；</div><hr/>
						<div>12.易联手机支付平台将支付结果异步通知给商户系统，最终支付结果以此为准；</div><hr/>
						<div>13.一切解释权归万众所有。</div><hr/>
		            </div>
		          </div>
		        </div>
		</div>
	</div>
<%-- 	</form:form> --%>
</body>
</html>