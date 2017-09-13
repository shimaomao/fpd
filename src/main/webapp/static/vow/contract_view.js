/** 用于处理查看合同 ****/
function viewLoanContract(){
	var array = getCheckValue("loanContractId");
	var ukey_array = $("#loanContractId").attr("data-ukey");
    if(array.length==0){
    	alertx("请选择一条业务合同!");
    	return;
    }
    
    $.ajax({
     	type: "POST",
     	url:  ctx+"/contract/tLoanContract//getContractStatus",
     	data: {id:array[0]},
     	dataType: "json",
     	success: function(data){
     		if(data<=3){
     			alert("还未签订合同，无法查看！");
     			return;
     		}
     		
     	    top.$.jBox.open("iframe:"+ctx+"/contract/tpl/tContractTpl/toView?id="+array[0]+"&ukey="+ukey_array[0], "查看合同模板",830,$(top.document).height()-240,{
     			buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
     			bottomText:"选择合同模板，然后查看合同填充数据预览。",
     			submit:function(v, h, f){
     				var ifrWin = h.find("iframe")[0].contentWindow;
     				if(v=="refresh"){
     					ifrWin.location.reload(true);
     	            	//ifrWin.clearAssign();
     					return false;
     	            }else if(v=="return"){
     	            	ifrWin.history.go(-1);
     	            	ifrWin.location.reload();
     					return false;
     	            }
     			}, loaded:function(h){
     				$(".jbox-content", top.document).css("overflow-y","hidden");
     			}
     		});
     		
     		
     		
     		
     		
     		
     		
     		
     	}
   });
	

}


function letterLoanContract(){
	var array = getCheckValue("loanContractId");
	var ukey_array = $("#loanContractId").attr("data-ukey");
    if(array.length==0){
    	alertx("请选择一条业务合同!");
    	return;
    }
    $.ajax({
     	type: "POST",
     	url: ctx+"/contract/tLoanContract/getdizhiListStatus",
     	data: {id:array[0],type:"2"},
     	dataType: "json",
     	success: function(datastr){
     		if(datastr==2){//有未收押的押品
     			alert("该笔合同选择了抵质押贷，还未落实反担保措施，无法出具担保函！");
     		}else{
     			
     			  top.$.jBox.open("iframe:"+ctx+"/lettertpl/letterTpl/toView?id="+array[0]+"&ukey=danbaorecord", "查看函件模板",900,$(top.document).height()-240,{
     					buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
     					bottomText:"选择合同模板，然后查看合同填充数据预览。",
     					submit:function(v, h, f){
     						var ifrWin = h.find("iframe")[0].contentWindow;
     						if(v=="refresh"){
     							ifrWin.location.reload(true);
     							return false;
     			            }else if(v=="return"){
     			            	ifrWin.history.go(-1);
     			            	ifrWin.location.reload();
     							return false;
 			            }
 					}, loaded:function(h){
 						$(".jbox-content", top.document).css("overflow-y","hidden");
 					}
 				});
     		}
     	}
   });
}