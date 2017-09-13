<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<!-- WOrder 测试================================================== -->
			<div class="span4 module">
				<div class="top-box">WOrder 下订单</div>
				<script type="text/javascript">
				  	$(function(){
				  		var wOrderModel = {
					   		Post:{
					   		  model:"wOrder",
					   		  url:"${ctxApi}/api/wOrders"
						   	  //url:"http://192.168.1.2:8080/fpd/api/wOrders"
					   		  //,data:{"customerName":"W端客户A","productname":"W端订单产品","loanPeriod":"11","loanRate":"11","loanRateType":"1","payDay":"5","periodType":"1","status":"1","id":"123456789","orderSn":"SN10000","loanId":"122222222","agencyId":"fd5a746df8724316a3318a21d016c893","catId":"122222","uid":"1222222","userType":"2","loanFee":"11","repayWay":"1","applyAmount":"212","installment":"11","rstatus":"1","product":{"name":"W端订单产品","type":"1","payType":"1","rate":"1.2","wtypeId":"123456789"},"employee":{"name":"W端客户个人A","cardNum":"421182198902112511","organId":"1","customerSource":"1","status":"1","wtypeId":"123456789"},"company":{"name":"W端客户公司A","cardType":"1","cardNum":"ORGNO111","address":"广州","surety":"法人张三","suretyCardnum":"421182198902112511","organId":"1","customerSource":"1","properties":"1","suretySex":"1","status":"1","wtypeId":"123456789"}}
					   		},
					   	  	Put:{
					   		  model:"wOrder",
					 	   	  url:"${ctxApi}/api/wOrders/"
					 	   		//,data:{"customerName":"W端客户A","productname":"W端订单产品","loanPeriod":"11","loanRate":"11","loanRateType":"1","payDay":"5","periodType":"1","status":"1","id":"123456789","orderSn":"SN10000","loanId":"122222222","agencyId":"fd5a746df8724316a3318a21d016c893","catId":"122222","uid":"1222222","userType":"2","loanFee":"11","repayWay":"1","applyAmount":"212","installment":"11","rstatus":"1","product":{"name":"W端订单产品","type":"1","payType":"1","rate":"1.2","wtypeId":"123456789"},"employee":{"name":"W端客户个人A","cardNum":"421182198902112511","organId":"1","customerSource":"1","status":"1","wtypeId":"123456789"},"company":{"name":"W端客户公司A","cardType":"1","cardNum":"ORGNO111","address":"广州","surety":"法人张三","suretyCardnum":"421182198902112511","organId":"1","customerSource":"1","properties":"1","suretySex":"1","status":"1","wtypeId":"123456789"}}
				   			},
					   	   	Delete:{
					   	  	  url:"${ctxApi}/api/tLoanContracts/"
					   		}
					   	 };
				  		$("#btnWOrderPost").click(function(){ apiTool.ajaxPost(wOrderModel.Post);});
				  		$("#btnWOrderPut").click(function(){ apiTool.ajaxPut(wOrderModel.Put, $("#wOrderId").val());});
				  		$("#btnWOrderDelete").click(function(){ apiTool.ajaxDelete(wOrderModel.Delete, $("#wOrderId").val());});
				  	});
				</script>
				<div class="box-tom">
                   	<div class="inbox">
                   		 <a id="btnWOrderPost" class="btna co1">post</a>
                            <a id="btnWOrderPut" class="btna co2">put</a>
                            <a id="btnWOrderDelete" class="btna co3">delete</a>
                            <hr/>
                            <div class="cont-box"><input class="ids" id="wOrderId" type="text" value="" placeholder="ID值" /></div>
                            <div class="cont-box">
	                            <textarea class="jsons" id="wOrderJson" placeholder="新增Json值" >
	                            	{"isAuto":"1","customerName":"W端客户A","productname":"W端订单产品","loanPeriod":"11","loanRate":"11","loanRateType":"1","payDay":"5","periodType":"1","gatheringBank":"1","gatheringName":"张三","gatheringNumber":"422256315662626","status":"1","id":"123456789","orderSn":"SN10000","loanId":"122222222","agencyId":"7b506be60942497db0b02b97e70dd21c","catId":"122222","uid":"1222222","userType":"2","loanFee":"11","repayWay":"1","applyAmount":"212","installment":"11","rstatus":"1","product":{"name":"W端订单产品","type":"1","payType":"1","rate":"1.2","wtypeId":"123456789"},"employee":{"name":"W端客户个人A","cardNum":"421182198902112511","organId":"1","customerSource":"1","status":"1","wtypeId":"123456789"},"company":{"name":"W端客户公司A","cardType":"1","cardNum":"ORGNO111","address":"广州","surety":"法人张三","suretyCardnum":"421182198902112511","organId":"1","customerSource":"1","properties":"1","suretySex":"1","status":"1","wtypeId":"123456789"}}
	                            </textarea>
				  			</div>
                        </div>
                    </div>
			</div>