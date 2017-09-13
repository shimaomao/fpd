<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>权益变动管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",	
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		
	    //检查输入的是否为金额数字
	    function checkNumber(value,inputId,textId){    	
	       value = value.replace(",","");
	       if(isNaN(value)){
	         alert("请输入正确的金额数字！");
	         return;
	       }else{    	   
	    	  var twoValue = $("#"+inputId).val();  
	    	  var totalVal = Number(value) + Number(twoValue);
	    	  $("#"+textId).val(totalVal);	    	  
	       } 
	    }
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/interests/chang/">权益变动列表</a></li>
		<li class="active"><a href="${ctx}/interests/chang/form?id=${jrjInterestsChang.id}">权益变动${not empty tBalanceSheep.id?'修改':'添加'}查看</a></li>
	</ul><br/>	
	<form:form id="inputForm" modelAttribute="jrjInterestsChang" action="${ctx}/interests/chang/save" method="post" class="form-horizontal">
		 编制单位：<form:input path="companyName" htmlEscape="false"  class="input-xlarge " style="width:90px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjProfit.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	 <br></br>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				
		
	    <table class="table-form" >	
		   <tr style="text-align:center;line-height: 23px;color:black;" >	              
	             <td colspan="9">
	                                                  本年报表                       
	             </td>                                      
	        </tr>   	       
           <tr style="text-align:center;line-height: 23px;" >
                 <td rowspan="2" width="90px">
                                                      项目
                </td>
                <td colspan="8">
                                                  本年金额                          
                </td>                                      
           </tr>           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">              
                <td >
                                               实收资本
                </td>
                <td >
                                              资本公积
                </td>  
                 
                <td >
                                               减：库存股
                </td>  
                 <td >
                                            盈余公积
                </td>  
                <td >
                                            一般风险准备
                </td> 
                               
                <td >
                                            未分配利润
                </td>
                <td >
                                            担保扶持基金
                </td>
                 <td >
                                            所有者权益合计
                </td>
                
                
                                            
           </tr>
           
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            一、上年年末余额
                </td>
                <td >
                   <form:input path="rowOne" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="rowTwo" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowThree" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="rowFour" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="rowFive" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowSix" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="rowSeven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="rowEight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            加:会计政策变更
                </td>
                <td >
                   <form:input path="row1One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row1Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row1Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row1Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row1Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row1Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                     
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            前期差错更正
                </td>
                <td >
                   <form:input path="row2One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row2Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row2Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row2Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row2Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row2Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>                          
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            二、本年年初余额
                </td>
                <td >
                   <form:input path="row3One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row3Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row3Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row3Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row3Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row3Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>                       
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          三、本年增减变动金额(减少以“-”号填列)
                </td>
                <td >
                   <form:input path="row4One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row4Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row4Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row4Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row4Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row4Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                   
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (一)净利润
                </td>
                <td >
                   <form:input path="row5One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row5Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row5Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row5Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row5Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row5Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                 
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  (二)其他综合收益
                </td>
                <td >
                   <form:input path="row6One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row6Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row6Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row6Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row6Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row6Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                       
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               上述(一)和(二)小计
                </td>
                <td >
                   <form:input path="row7One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row7Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row7Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row7Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row7Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row7Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>                   
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (三)所有者投入和减少资本
                </td>
                <td >
                   <form:input path="row8One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row8Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row8Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row8Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row8Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row8Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                      
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.所有者投入资本
                </td>
                <td >
                   <form:input path="row9One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row9Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row9Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row9Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row9Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row9Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                      
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   2.股份支付计入所有者权益的金额
                </td>
                <td >
                   <form:input path="row10One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row10Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row10Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row10Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row10Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row10Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                      
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   3.其他
                </td>
                <td >
                   <form:input path="row11One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row11Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row11Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row11Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row11Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row11Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                  
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (四)利润分配
                </td>
                <td >
                   <form:input path="row12One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >	
                   <form:input path="row12Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row12Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row12Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row12Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row12Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                     
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.提取盈余公积
                </td>
                <td >
                   <form:input path="row13One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row13Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row13Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row13Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row13Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row13Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                   
           </tr>
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   2.提取一般风险准备
                </td>
                <td >
                   <form:input path="row14One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row14Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row14Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row14Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row14Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row14Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                  
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   3.对所有者的分配
                </td>
                <td >
                   <form:input path="row15One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row15Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row15Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row15Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row15Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row15Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>                  
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   4.其他
                </td>
                <td >
                   <form:input path="row16One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row16Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row16Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row16Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row16Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row16Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                     
           </tr>        
          
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (五)所有者权益内部结转
                </td>
                <td >
                   <form:input path="row17One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row17Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row17Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row17Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row17Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row17Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.资本公积转增资本
                </td>
                <td >
                   <form:input path="row18One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row18Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row18Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row18Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row18Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row18Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                 
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  2.盈余公积转增资本
                </td>
                <td >
                   <form:input path="row19One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row19Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row19Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row19Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row19Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row19Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>             
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  3.盈余公积弥补亏损
                </td>
                <td >
                   <form:input path="row20One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row20Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row20Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row20Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row20Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row20Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                  
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  4.一般风险准备弥补亏损
                </td>
                <td >
                   <form:input path="row21One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row21Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row21Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row21Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row21Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row21Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                    
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  5.其他
                </td>
                <td >
                   <form:input path="row22One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row22Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row22Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row22Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row22Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row22Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
               
           </tr>
           
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                    四、本年年末余额
                </td>
                <td >
                   <form:input path="row23One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row23Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row23Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row23Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row23Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row23Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>                  
           </tr>                                                                                 
         </table>		
         
         
         <br> </br>         
         <br> </br>  
       
         <table class="table-form" >
           <tr style="text-align:center;line-height: 23px;color:black;" >                            
                <td colspan="9">
                                                 上年报表
                </td>                             
           </tr>       
         		       
           <tr style="text-align:center;line-height: 23px;" >
                 <td rowspan="2" width="90px">
                                                      项目
                </td>               
                <td colspan="8">
                                                 上年金额
                </td>                             
           </tr>           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">   
                <td >
                                               实收资本
                </td>
                <td >
                                              资本公积
                </td>  
                 
                <td >
                                               减：库存股
                </td>  
                 <td >
                                            盈余公积
                </td>  
                <td >
                                            一般风险准备
                </td> 
                               
                <td >
                                            未分配利润
                </td>
                <td >
                                            担保扶持基金
                </td>
                 <td >
                                            所有者权益合计
                </td>                                
           </tr>
           
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            一、上年年末余额
                </td>
                <%-- <td >
                   <form:input path="rowOne" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="rowTwo" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowThree" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="rowFour" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="rowFive" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowSix" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="rowSeven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="rowEight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                 <td >
                   <form:input path="rowNine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowTen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowEleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                 <td >
                   <form:input path="rowTwelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowThirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowFourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="rowFifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="rowSixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            加:会计政策变更
                </td>
               <%--  <td >
                   <form:input path="row1One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row1Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row1Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row1Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row1Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row1Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row1Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row1Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                 <td >
                   <form:input path="row1Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>           
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            前期差错更正
                </td>
                <%-- <td >
                   <form:input path="row2One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row2Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row2Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row2Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row2Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row2Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row2Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row2Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row2Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            二、本年年初余额
                </td>
                <%-- <td >
                   <form:input path="row3One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row3Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row3Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row3Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row3Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row3Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row3Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row3Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row3Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row3Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>        
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          三、本年增减变动金额(减少以“-”号填列)
                </td>
                <%-- <td >
                   <form:input path="row4One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row4Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row4Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row4Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row4Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row4Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row4Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>
                <td >
                   <form:input path="row4Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row4Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row4Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row4Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (一)净利润
                </td>
                <%-- <td >
                   <form:input path="row5One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row5Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row5Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row5Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row5Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row5Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row5Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row5Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row5Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>        
                <td >
                   <form:input path="row5Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  (二)其他综合收益
                </td>
               <%--  <td >
                   <form:input path="row6One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row6Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                 <td >
                   <form:input path="row6Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row6Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row6Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row6Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row6Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row6Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row6Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row6Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>           
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               上述(一)和(二)小计
                </td>
                <%-- <td >
                   <form:input path="row7One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row7Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row7Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row7Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row7Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row7Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row7Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row7Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row7Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row7Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>        
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (三)所有者投入和减少资本
                </td>
               <%--  <td >
                   <form:input path="row8One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row8Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row8Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row8Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row8Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row8Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row8Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row8Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row8Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>          
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.所有者投入资本
                </td>
                <%-- <td >
                   <form:input path="row9One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row9Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row9Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row9Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row9Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row9Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row9Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row9Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row9Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row9Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   2.股份支付计入所有者权益的金额
                </td>
                <%-- <td >
                   <form:input path="row10One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row10Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row10Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row10Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row10Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row10Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row10Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row10Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row10Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row10Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   3.其他
                </td>
               <%--  <td >
                   <form:input path="row11One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row11Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row11Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row11Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row11Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row11Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row11Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row11Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row11Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row11Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (四)利润分配
                </td>
                <%-- <td >
                   <form:input path="row12One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >	
                   <form:input path="row12Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row12Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row12Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row12Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row12Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row12Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row12Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row12Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row12Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.提取盈余公积
                </td>
                <%-- <td >
                   <form:input path="row13One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row13Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row13Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row13Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row13Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row13Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row13Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row13Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row13Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>          
           </tr>
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   2.提取一般风险准备
                </td>
                <%-- <td >
                   <form:input path="row14One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row14Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row14Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row14Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row14Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row14Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row14Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row14Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row14Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row14Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   3.对所有者的分配
                </td>
                <%-- <td >
                   <form:input path="row15One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row15Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row15Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row15Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row15Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row15Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row15Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row15Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row15Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
                <td >
                   <form:input path="row15Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   4.其他
                </td>
                <%-- <td >
                   <form:input path="row16One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row16Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row16Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row16Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row16Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row16Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row16Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row16Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row16Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row16Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>        
          
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   (五)所有者权益内部结转
                </td>
                <%-- <td >
                   <form:input path="row17One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row17Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row17Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row17Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row17Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row17Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row17Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row17Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row17Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row17Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                   1.资本公积转增资本
                </td>
                <%-- <td >
                   <form:input path="row18One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row18Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row18Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row18Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row18Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row18Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row18Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row18Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row18Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row18Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  2.盈余公积转增资本
                </td>
                <%-- <td >
                   <form:input path="row19One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row19Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row19Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row19Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row19Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row19Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row19Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row19Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row19Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
                <td >
                   <form:input path="row19Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  3.盈余公积弥补亏损
                </td>
                <%-- <td >
                   <form:input path="row20One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row20Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row20Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row20Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row20Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row20Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row20Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row20Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row20Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row20Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  4.一般风险准备弥补亏损
                </td>
                <%-- <td >
                   <form:input path="row21One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row21Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row21Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row21Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row21Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row21Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row21Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row21Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row21Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>     
                <td >
                   <form:input path="row21Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>       
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                  5.其他
                </td>
                <%-- <td >
                   <form:input path="row22One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row22Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row22Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row22Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row22Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row22Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row22Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row22Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row22Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>      
                <td >
                   <form:input path="row22Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>      
           </tr>
           
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                    四、本年年末余额
                </td>
                <%-- <td >
                   <form:input path="row23One" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                     
                </td>
                <td >
                   <form:input path="row23Two" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Three" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                 <td >
                   <form:input path="row23Four" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                <td >
                   <form:input path="row23Five" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Six" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
                 <td >
                   <form:input path="row23Seven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>    
                <td >
                   <form:input path="row23Eight" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   --%>
                <td >
                   <form:input path="row23Nine" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Ten" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Eleven" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Twelve" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td> 
                <td >
                   <form:input path="row23Thirteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Fourteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>  
                <td >
                   <form:input path="row23Fifteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>         
                <td >
                   <form:input path="row23Sixteen" htmlEscape="false"  class="input-xlarge "  style="width:90px;"/>                                               
                </td>   
           </tr>                                                                                 
         </table>        
         
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>