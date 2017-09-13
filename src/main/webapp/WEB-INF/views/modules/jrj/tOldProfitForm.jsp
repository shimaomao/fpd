<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>旧利润报表管理</title>
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
		<li><a href="${ctx}/profit/oldJrjProfit/">旧损益列表</a></li>
		<li class="active"><a href="${ctx}/profit/oldJrjProfit/form?id=${tBalanceSheep.id}">旧损益查看</a></li>
	</ul><br/>	
	<form:form id="inputForm" modelAttribute="jrjProfit" action="${ctx}/profit/oldJrjProfit/save" method="post" class="form-horizontal">
		 编制单位：<form:input path="companyName" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjProfit.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	 <br></br>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td >
                                              项目
                </td>
                <td>
                                               行次
                </td>
                <td >
                                             本月数                                   
                </td>
                <td>
                                          本年累计数
                </td>                             
           </tr>           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            一、担保业务收入
                </td>
                <td>1</td>
                <td >
                   <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            其中：担保费收入
                </td>
                <td>
                   
                </td>
                <td >
                   <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                        手续费收入
                </td>
                <td>
                
                </td>
                <td >
                   <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          评审费收入
                </td>
                <td>
                
                </td>
                <td >
                   <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            追偿收入
                </td>
                <td></td>
                                
                <td >
                   <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                              其他担保业务收入
                </td>
                <td>                
                </td>
                                
                
                <td >
                   <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          二、担保成本
                </td>
                <td></td>
                
                <td >
                   <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                           其中：担保赔偿支出
                </td>
                <td></td>
                
                <td >
                   <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                           分担保费支出
                </td>
                <td>
                </td>
                                
                <td >
                   <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               手续费支出
                </td>
                <td></td>
                
                <td >
                   <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               其他支出
                </td>
                <td></td>
                
                
                <td >
                   <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                三、担保业务利润
                </td>
                <td></td>
                
                <td >
                   <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                           加：利息收入（净支出以“-”填列）
                </td>
                <td></td>
                <td >
                   <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            四、其他业务收入
                </td>
                <td></td>
                
                <td >
                   <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                  五、其他业务成本
                </td>
                <td></td>
                
                <td >
                   <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               六：其他业务利润
                </td>
                <td></td>                
                
                <td >
                   <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                         七、 营业税金及附加
                </td>
                <td></td>
                
                <td >
                   <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            减：营业费用
                </td>
                <td></td>
                
                <td >
                   <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             管理费用
                </td>
                <td></td>
                
                <td >
                   <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                    财务费用
                </td>
                <td></td>
                
                
                <td >
                   <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            加：投资收益（损失以“-”填列）
                </td>
                <td></td>
                <td >
                   <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               八、营业利润
                </td>
                <td></td>
                
                <td >
                   <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                     加：营业外收入
                </td>
                <td></td>
                <td >
                   <form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               减：营业外支出
                </td>
                <td></td>
                
                <td >
                   <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             加：补贴收入
                </td>
                <td></td>
                
                <td >
                   <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                         九、扣除资产减值损失前利润总额（亏损以“-”）
                </td>
                <td>
                </td>
                                
                <td >
                   <form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row25Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                      减：资产减值损失（转回金额以“-”填列）
                </td>
                <td></td>
                
                <td >
                   <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          十、扣除资产损失后利润总额（亏损以“-”填列）
                </td>
                <td></td>
                
                
                <td >
                   <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row27Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                       减：所得税
                </td>
                <td></td>
                
                
                <td >
                   <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>     
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                       十一、净利润（亏损以“-”填列）
                </td>
                <td></td>                
                
                <td >
                   <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>  
           
           
                                                                                
         </table>		
		
		<div class="form-actions">
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp; -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>