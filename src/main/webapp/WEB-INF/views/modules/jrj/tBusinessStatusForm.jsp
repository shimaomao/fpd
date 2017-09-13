<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产负债管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	    function checkNumber(value,oneId,inputId,targetId,flag){    	
	       value = value.replace(",","");
	       if(isNaN(value)){
	         alert("请输入正确的金额数字！");
	         return;
	       }else{    	   
	    	   if(flag == 'false'){//增加
	    		    var oneValue = $("#"+oneId).val();  
			    	var inputValue = $("#"+inputId).val();	    	  
			    	var totalVal = Number(value) + Number(oneValue) - Number(inputValue);
			    	$("#"+targetId).val(totalVal);
	    	   }else if(flag == 'true'){
	    		   var oneValue = $("#"+oneId).val();  
			    	var inputValue = $("#"+inputId).val();	    	  
			    	var totalVal =Number(oneValue) - Number(value) + Number(inputValue);
			    	$("#"+targetId).val(totalVal);
	    	   }	  
	       } 
	    }
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/bussiness/businessStatus/">担保业务状况列表</a></li>
		<li class="active"><a href="${ctx}/bussiness/businessStatus/form?id=${jrjBusinessStatus.id}">担保业务状况<shiro:hasPermission name="balancesheep:tBalanceSheep:edit">${not empty jrjBusinessStatus.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="jrjBusinessStatus" action="${ctx}/bussiness/businessStatus/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		 <table  class="table-form">		    
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td rowspan=2>
                                               序号
                </td>
                <td rowspan=2>
                
                </td>
                <td rowspan=2>
                                              项目
                </td>
                <td>
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
                <td>
                                              期初数
                </td>    
                <td>
                                            本期增加(发生额)
                </td> 
                <td>
                                           本期减少/解除(发生额)
                </td>
                 <td>
                                            期末数
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    1
                </td>
                <td rowspan=8>
                   1.融资性担保业务
                </td>
                <td >
                  1.1.1贷款担保
                </td> 
                <td>
                   <form:input path="rowOne" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'rowOne','rowThree','rowFour','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="rowThree" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'rowOne','rowTwo','rowFour','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="rowFour" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    2
                </td>                
                <td >
                  1.1.2贷款担保
                </td> 
                <td>
                   <form:input path="row1One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row1One','row1Three','row1Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row1Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row1One','row1Two','row1Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row1Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    3
                </td>                
                <td >
                  1.1.3信用证担保
                </td> 
                <td>
                   <form:input path="row2One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row2One','row2Three','row2Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row2Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row2One','row2Two','row2Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row2Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    4
                </td>                
                <td >
                  1.1.4其它融资性担保项
                </td> 
                <td>
                   <form:input path="row3One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row3One','row3Three','row3Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row3Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row3One','row3Two','row3Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row3Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    5
                </td>                
                <td >
                  1.1担保金额小计
                </td> 
                <td>
                   <form:input path="row4One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row4One','row4Three','row4Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row4Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row4One','row4Two','row4Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row4Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    6
                </td>                
                <td >
                  1.2担保户数
                </td> 
                <td>
                   <form:input path="row5One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row5One','row5Three','row5Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row5Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row5One','row5Two','row5Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row5Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    7
                </td>                
                <td >
                  1.3代偿金额
                </td> 
                <td>
                   <form:input path="row6One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row6One','row6Three','row6Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row6Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row6One','row6Two','row6Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row6Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    8
                </td>                
                <td >
                  1.4损失金额
                </td> 
                <td>
                   <form:input path="row7One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row7One','row7Three','row7Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row7Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row7One','row7Two','row7Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row7Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    9
                </td> 
                <td rowspan=6>
                    2.非融资性担保业务
                </td>                
                <td >
                  2.1.1诉讼保全担保
                </td> 
                <td>
                   <form:input path="row8One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row8One','row8Three','row8Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row8Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row8One','row8Two','row8Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row8Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>         
         
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    10
                </td> 
                            
                <td >
                  2.1.2履约担保
                </td> 
                <td>
                   <form:input path="row9One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row9One','row9Three','row9Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row9Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row9One','row9Two','row9Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row9Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    11
                </td> 
                            
                <td >
                  2.1.3其它非融资性担保
                </td> 
                <td>
                   <form:input path="row10One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row10One','row10Three','row10Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row10Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row10One','row10Two','row10Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row10Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    12
                </td> 
                            
                <td >
                  2.1担保金额小计
                </td> 
                <td>
                   <form:input path="row11One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row11One','row11Three','row11Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row11Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row11One','row11Two','row11Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row11Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    13
                </td> 
                            
                <td >
                  2.2代偿金额
                </td> 
                <td>
                   <form:input path="row12One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row12One','row12Three','row12Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row12Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row12One','row12Two','row12Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row12Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    14
                </td> 
                            
                <td >
                  2.3损失金额
                </td> 
                <td>
                   <form:input path="row13One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row13One','row13Three','row13Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row13Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row13One','row13Two','row13Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row13Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    15
                </td> 
                <td rowspan=4>
                   3.债券发行担保
                </td>            
                <td >
                  3.1担保金额
                </td> 
                <td>
                   <form:input path="row14One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row14One','row14Three','row14Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row14Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row14One','row14Two','row14Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row14Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    16
                </td> 
                      
                <td >
                  3.2担保户数
                </td> 
                <td>
                   <form:input path="row15One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row15One','row15Three','row15Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row15Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row15One','row15Two','row15Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row15Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    17
                </td> 
                      
                <td >
                  3.3代偿金额
                </td> 
                <td>
                   <form:input path="row16One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row16One','row16Three','row16Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row16Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row16One','row16Two','row16Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row16Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    18
                </td> 
                      
                <td >
                  3.4损失金额
                </td> 
                <td>
                   <form:input path="row17One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row17One','row17Three','row17Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row17Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row17One','row17Two','row17Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row17Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    19
                </td> 
                <td rowspan=3>
                   4.再担保
                </td>
                      
                <td >
                  4.1担保金额
                </td> 
                <td>
                   <form:input path="row18One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row18One','row18Three','row18Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row18Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row18One','row18Two','row18Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row18Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    20
                </td>                       
                <td >
                  4.2代偿金额
                </td> 
                <td>
                   <form:input path="row19One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row19One','row19Three','row19Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row19Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row19One','row19Two','row19Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row19Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    21
                </td>                       
                <td >
                  4.3损失金额
                </td> 
                <td>
                   <form:input path="row20One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row20One','row20Three','row20Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row20Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row20One','row20Two','row20Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row20Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    22
                </td> 
                <td rowspan=3>
                  5.担保业务合计
                </td>                      
                <td >
                  5.1担保金额合计
                </td> 
                <td>
                   <form:input path="row21One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row21One','row21Three','row21Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row21Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row21One','row21Two','row21Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row21Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    23
                </td> 
                                     
                <td >
                  5.2代偿金额合计
                </td> 
                <td>
                   <form:input path="row22One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row22One','row22Three','row22Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row22Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row22One','row22Two','row22Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row22Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    24
                </td> 
                                     
                <td >
                  5.3损失金额合计
                </td> 
                <td>
                   <form:input path="row23One" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td> 
                <td>
                  <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row23One','row23Three','row23Four','false');" style="width:180px;"/> 
                </td>  
                <td>
                  <form:input path="row23Three" htmlEscape="false" maxlength="64" class="input-xlarge required" onchange="checkNumber(this.value,'row23One','row23Two','row23Four','true');" style="width:180px;"/> 
                </td>      
                <td>
                  <form:input path="row23Four" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                    
           </tr>                                                                                  
         </table>
		 <br></br>
		 负责人：<form:input path="principal" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;统计负责人: <form:input path="statistics" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;填表人:<form:input path="fitOut" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required" value="${jrjBusinessStatus.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>