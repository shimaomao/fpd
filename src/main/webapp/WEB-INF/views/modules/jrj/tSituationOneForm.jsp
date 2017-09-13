<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经营情况月报一管理</title>
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
		<li><a href="${ctx}/business/situationOne/">经营月报情况一列表</a></li>
		<li class="active"><a href="${ctx}/business/situationOne/form?id=${jrjBusinessSituationOne.id}">经营月报情况一<shiro:hasPermission name="balancesheep:tBalanceSheep:edit">${not empty jrjBusinessSituationOne.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<p>表号 ：          担保01表</p>
	<p>制表机关 ：          中国银行监督管委员会</p>	
	<p>数据单位：          万元、户、笔</p>	
	<span ></span>
	<form:form id="inputForm" modelAttribute="jrjBusinessSituationOne" action="${ctx}/business/situationOne/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td rowspan=2 colspan=2>
                                               项目
                </td>
                <td >
                    A
                </td>
                <td>
                    B
                </td>
                <td>
                    C 
                </td>
                <td>
                    D
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                   上月数
                </td>    
                <td >
                                                 本月新增
                </td>    
                <td >
                                                本月解保   
                </td>    
                <td >
                                             在保余额
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td rowspan=8>
                                             融资担保业务
                </td>
                <td >
                 1.贷款担保
                </td>
                <td >
                  <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="rowThree" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="rowFour" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
                2.票据承兑担保
                </td>
                <td >
                  <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row1Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row1Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
                3.信用证担保
                </td>
                <td >
                  <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row2Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row2Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
               4.其他融资性担保项
                </td>
                <td >
                  <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row3Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row3Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
               5.担保金额小计
                </td>
                <td >
                  <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row4Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row4Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
             6.担保户数
                </td>
                <td >
                  <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row5Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row5Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
                    7.担保笔数
                </td>
                <td >
                  <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row6Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row6Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">               
                <td >
                   8.平均担保费率
                </td>
                <td >
                  <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td>
                  <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row7Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>     
                 <td>
                  <form:input path="row7Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                              
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:center;color:black;" rowspan=7>
                                                    非融资担保业务
                </td>               
                <td >
                   9.诉讼保全担保 
                </td>
                <td >
                  <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row8Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row8Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                   10.履约担保
                </td>
                <td >
                  <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row9Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row9Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                  11.其他非融资性担保
                </td>
                <td >
                  <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row10Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row10Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                  12.担保金额小计
                </td>
                <td >
                  <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row11Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row11Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                 13.担保户数
                </td>
                <td >
                  <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row12Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row12Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                 14.担保笔数
                </td>
                <td >
                  <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row13Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row13Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                              
                <td >
                 15.平均担保费率
                </td>
                <td >
                  <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td> 
                <td >
                  <form:input path="row14Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row14Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                                      
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">  
                 <td style="text-align:center;color:black;" rowspan=4>
                                                     债券发行担保
                 </td>                            
                <td >
                 16.担保金额
                </td>
                <td >
                  <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row15Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row15Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                        
                <td >
                 17.担保户数
                </td>
                <td >
                  <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row16Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row16Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                        
                <td >
                 18.担保笔数
                </td>
                <td >
                  <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row17Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row17Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                        
                <td >
                19.平均担保费率
                </td>
                <td >
                  <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row18Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row18Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:center;color:black;" rowspan=4>
                                             再担保
                </td>                                                    
                <td >
                20.担保金额
                </td>
                <td >
                  <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row19Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row19Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                 
                <td >
                21.担保户数
                </td>
                <td >
                  <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row20Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row20Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                 
                <td >
                22.担保笔数
                </td>
                <td >
                  <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row21Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row21Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                 
                <td >
                23.平均担保费率
                </td>
                <td >
                  <form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row22Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row22Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:center;color:black;" rowspan=4>
                                             担保业务合计
                </td>                                                                             
                <td >
                24.担保金额合计
                </td>
                <td >
                  <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row23Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row23Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                          
                <td >
                25.担保户数合计
                </td>
                <td >
                  <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row24Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row24Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                          
                <td >
                26.担保笔数合计
                </td>
                <td >
                  <form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row25Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row25Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row25Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                          
                <td >
                27.平均担保费率
                </td>
                <td >
                  <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>  
                 <td >
                  <form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                <td >
                  <form:input path="row26Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>    
                <td >
                  <form:input path="row26Four" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:center;color:black;" rowspan=4>
                                               担保业务累计
                </td>                                                                                          
                <td >
                28.融资性担保业务累计
                </td>
                <td >
                  <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                </td>   
                <td>
                </td>
                <td>
                </td>                                       
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                                      
                <td >
                29.非融资性担保业务累计
                </td>
                <td >
                  <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                 <td>
                </td>
                
                <td>
                </td>   
                <td>
                </td>                                         
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                                         
                <td >
                30.担保户数累计
                </td>
                <td >
                  <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>   
                 <td>
                </td>
                
                <td>
                </td>   
                <td>
                </td>                                      
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                                                                                                         
                <td >
                31.担保笔数累计
                </td>
                <td >
                  <form:input path="row30One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td> 
                <td>
                </td>
                
                <td>
                </td> 
                <td>
                </td>                                        
           </tr>
                                                                                        
         </table>
		 <br></br>
		 负责人：<form:input path="principal" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填表人:<form:input path="fitOut" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tBalanceSheep.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
		<br></br>
		
		<p>说明：   (1)每月3日前报送省金融办，遇节假日顺延。</p>
	    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(2)1+2+3+4=5   9+10+11=12   5+12+16+20=24   6+13+17+21=25  7+14+18+22=26 </p>	
	    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      (3)A+B-C=D</p>	
	    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      (3)A+B-C=D</p>	
	    <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (4)担保业务累计指公司成立至今的业务</p>
		<div class="form-actions">
			<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>