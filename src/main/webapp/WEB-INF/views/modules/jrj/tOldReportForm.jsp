<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报表说明(对外)管理</title>
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
		<li><a href="${ctx}/tOldReportForm.jsp/">报表说明(对外)列表</a></li>
		<li class="active"><a href="${ctx}/tOldReportForm.jsp/form?id=${jrjOldReport.id}">报表说明(对外)<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"></shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="jrjOldReport" action="${ctx}/report/oldReport/save" method="post" class="form-horizontal">
	          编制单位：<form:input path="companyName" htmlEscape="false"  class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjOldReport.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<br></br>		
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td >
                                            科目
                </td>
                <td >
                                           单位名称
                </td>
                <td>
                                           月初余额
                </td>
                <td>
                                              借方
                </td>
                <td>
                                             贷方
                </td>
                <td>
                                               月末余额
                </td>                                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                其他应付款
                </td>  
                <td>
                    <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="rowThree" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="rowFour" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                 <td >
                    <form:input path="rowFive" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>              
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         其他应付款
                </td>     
                <td> 
                    <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row1Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row1Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row1Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                  
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         其他应付款
                </td>                            
                <td>
                    <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row2Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row2Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                 <td >
                    <form:input path="row2Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应付款
                </td>               
                <td>
                    <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row3Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row3Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                 <td >
                    <form:input path="row3Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>               
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应付款
                </td>               
                <td>
                    <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row4Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row4Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                 <td >
                    <form:input path="row4Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        其他应付款
                </td>               
                <td>
                    <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row5Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row5Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                 <td >
                    <form:input path="row5Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应付款
                </td>               
                <td>
                    <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row6Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row6Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                <td >
                    <form:input path="row6Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         其他应付款
                </td>               
                <td>
                    <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row7Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row7Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td >
                    <form:input path="row7Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应付款
                </td>               
                <td>
                    <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row8Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row8Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                 <td >
                    <form:input path="row8Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应收款
                </td>               
                <td>
                    <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                <td >
                    <form:input path="row9Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row9Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                 <td >
                    <form:input path="row9Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            合计
                </td>               
                <td>
                    <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row10Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row10Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                 <td >
                    <form:input path="row10Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                 </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        其他应收款
                </td>               
                <td>
                    <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row11Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row11Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                <td >
                    <form:input path="row11Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应收款
                </td>               
                <td>
                    <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row12Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row12Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row12Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                   
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           其他应收款
                </td>               
                <td>
                    <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row13Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row13Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                  <td >
                    <form:input path="row13Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应收款
                </td>               
                <td>
                    <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row14Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row14Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                 <td >
                    <form:input path="row14Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                  
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                             其他应收款
                </td>               
                <td>
                    <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row15Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row15Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                <td >
                    <form:input path="row15Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               其他应收款
                </td>          
                         
                <td>
                    <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row16Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row16Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                 <td >
                    <form:input path="row16Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                   
                <td >
                                             其他应收款
                </td>                         
                <td>
                    <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row17Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row17Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                 <td >
                    <form:input path="row17Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应收款
                </td>               
                <td>
                    <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row18Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row18Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row18Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应收款
                </td>               
                <td>
                    <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>          
                
                <td >
                    <form:input path="row19Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row19Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                <td >
                    <form:input path="row19Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>               
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应收款
                </td>               
                <td>
                    <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row20Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row20Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                 <td >
                    <form:input path="row20Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                   
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                       其他应收款
                </td>               
                <td>
                    <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row21Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row21Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                 <td >
                    <form:input path="row21Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                  
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                      其他应收款
                </td>               
                <td>
                    <form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                <td >
                    <form:input path="row22Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row22Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row22Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        其他应收款
                </td>               
                <td>
                    <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row23Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row23Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                  <td >
                    <form:input path="row23Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>               
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        其他应收款
                </td>               
                <td>
                    <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row24Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row24Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                 <td >
                    <form:input path="row24Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            其他应收款
                </td>               
                <td>
                    <form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row25Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row25Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row25Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                <td >
                    <form:input path="row25Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>             
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         合计
                </td>               
                <td>
                    <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                <td >
                    <form:input path="row26Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row26Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row26Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                       长期股权投资
                </td>               
                <td>
                    <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row27Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row27Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row27Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>     
                <td >
                    <form:input path="row27Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>              
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        长期股权投资
                </td>               
                <td>
                    <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row28Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row28Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>    
                 <td >
                    <form:input path="row28Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>              
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                       长期股权投资
                </td>               
                <td>
                    <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row29Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row29Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row29Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                  
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                      合计
                </td>               
                <td>
                    <form:input path="row30One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row30Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row30Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row30Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>     
                <td >
                    <form:input path="row30Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>              
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        应收代偿款
                </td>               
                <td>
                    <form:input path="row31One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row31Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row31Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row31Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>  
                <td >
                    <form:input path="row31Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                       应收代偿款
                </td>               
                <td>
                    <form:input path="row32One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row32Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                    <form:input path="row32Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row32Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>   
                <td >
                    <form:input path="row32Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>               
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                                 合计
                </td>               
                <td>
                    <form:input path="row33One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row33Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>              
                <td >
                    <form:input path="row33Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row33Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>     
                 <td >
                    <form:input path="row33Five" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>              
           </tr>                                                                                                         
         </table>
		 <br></br>
		
		<div class="form-actions">
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp; -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>