<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风险指标管理</title>
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
		<li><a href="${ctx}/risk/jrjRiskIndicator/">风险指标列表</a></li>
		<li class="active"><a href="${ctx}/risk/jrjRiskIndicator/form?id=${jrjRiskIndicator.id}">风险指标<shiro:hasPermission name="balancesheep:tBalanceSheep:edit">${not empty jrjRiskIndicator.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<p>表号 ：          c3表</p>
	<p>制表机关 ：          中国银行监督管委员会</p>
	<p>批准机关：          国家统计局</p>
	<p>批准文号：          国统制[2010]97号</p>
	<p>数据单位：          万元</p>
	<p>填报单位：          ${officeName}</p>
	<p>填报时间：          ${createTime}</p>	
	
	<form:form id="inputForm" modelAttribute="jrjRiskIndicator" action="${ctx}/risk/jrjRiskIndicator/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		 <table  class="table-form">		    
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td rowspan=2>
                                               序号
                </td>                
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
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                              期初数
                </td>    
                <td>
                                          本期期间数
                </td>                 
                 <td>
                                            期末数
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    1
                </td>
                <td rowspan=3>
                   1.流动性
                </td>
                <td >
                  1.1流动性资产
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="rowThree" htmlEscape="false" maxlength="64" style="width:180px;"/> 
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    2
                </td>               
                <td >
                  1.2流动性资产
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row1Three" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/> 
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    3
                </td>               
                <td >
                  1.3流动性资产
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row2Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    4
                </td>  
                 <td rowspan=3>
                    2.放大倍数
                </td>              
                <td >
                  2.1融资性担保责任余额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row3Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    5
                </td>  
                            
                <td >
                  2.2净资产
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row4Three" htmlEscape="false" maxlength="64" style="width:180px;"/> 
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    6
                </td>  
                            
                <td >
                  2.3净资产
                </td> 
                <td>
                   -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row5Three" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/> 
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    7
                </td>  
                <td rowspan=6>
                    3.代偿额
                </td>                               
                <td >
                  3.1本年度累计担保代偿额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    8
                </td>  
                                               
                <td >
                                          其中:本年度累计融资性担保代偿额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    9
                </td>  
                                               
                <td >
                  3.2本年度累计解除的担保额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    10
                </td>  
                                               
                <td >
                                           其中:本年度累计解除的融资性担保额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    11
                </td>  
                                               
                <td >
                   3.3担保代偿额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    12
                </td>  
                                               
                <td >
                   3.4融资性担保代偿额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    13
                </td>  
                <td rowspan=6>
                    4.代偿回收率
                </td>                          
                <td >
                   4.1本年度累计代偿回收额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    14
                </td>                                           
                <td >
                                             其中:本年度累计融资性担保代偿回收额
                </td> 
                <td>
                   -
                </td> 
                <td>
                  <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    15
                </td>                                           
                <td >
                  4.2年初担保代偿余额
                </td> 
                <td>
                <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td> 
                <td>
                  -
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    16
                </td>                                           
                <td >
                                          其中:年初融资性担保代偿余额
                </td> 
                <td>
                <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td> 
                <td>
                  -
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    17
                </td>                                           
                <td >
                  4.3代偿回收率                        
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    18
                </td>                                           
                <td >
                  4.4融资性担保代偿回收率                        
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    19
                </td>  
                <td rowspan=4>
                    5.损失费
                </td>                                           
                <td >
                  5.1本年度累计担保损失费                     
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    20
                </td>                                                           
                <td >
                                           其中:本年度累计融资性担保损失额                     
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    21
                </td>                                                           
                <td >
                   5.2担保损失率                    
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    22
                </td>                                                           
                <td >
                   5.3融资性担保损失率                    
                </td> 
                <td>
                  -
                </td> 
                <td>
                  <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                  -
                </td>                                    
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    23
                </td> 
                 <td rowspan=3>
                    6.拨备覆盖率
                </td>                                                           
                <td >
                   6.1担保准备金                    
                </td> 
                <td>
                  -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row22Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    24
                </td>                                                                           
                <td >
                   6.2担保代偿余额                  
                </td> 
                <td>
                  -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row23Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                                    
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                    25
                </td>                                                                           
                <td >
                   6.3拨备覆盖率                 
                </td> 
                <td>
                  -
                </td> 
                <td>
                  -
                </td>  
                <td>
                  <form:input path="row24Three" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                                    
           </tr>                                                               
         </table>
		 <br></br>
		 负责人：<form:input path="principal" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;统计负责人: <form:input path="statistics" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;填表人:<form:input path="fitOut" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjRiskIndicator.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>