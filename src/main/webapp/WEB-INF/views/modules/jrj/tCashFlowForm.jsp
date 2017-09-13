<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>担保现金流量管理</title>
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
		<li><a href="${ctx}/cash/jrjCashFlow/">现金流量列表</a></li>
		<li class="active"><a href="${ctx}/cash/jrjCashFlow/form?id=${tBalanceSheep.id}">现金流量添加${not empty tBalanceSheep.id?'修改':'添加'}查看</a></li>
	</ul><br/>	
	<form:form id="inputForm" modelAttribute="jrjCashFlow" action="${ctx}/cash/jrjCashFlow/save" method="post" class="form-horizontal">
		 编制单位：<form:input path="companyName" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjCashFlow.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	 <br></br>
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
				
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td >
                                              项目
                </td>
                <td >
                                             本月发生数                                   
                </td>
                <td>
                                          本年累计发生数
                </td>                             
           </tr>           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                        一、经营活动产生的现金流量：
                </td>
                <td >
                   <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            收到担保费收入
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
                                            收到分担保费收入
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
                                         收到的税费返还
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
                                             收到其他与经营活动有关的现金
                </td>
                <td >
                   <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               经营活动现金流入小计
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
                                               支付担保业务支出的现金
                </td>
                <td >
                   <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            支付给职工以及为职工支付的现金
                </td>
                <td >
                   <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               支付的各项税费
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
                                                  支付其他与经营活动有关的现金
                </td>
                <td >
                   <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                   经营活动现金流出小计
                </td>
                <td >
                   <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                经营活动产生的现金流量净额
                </td>
                <td >
                   <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                              二、投资活动产生的现金流量：
                </td>
                <td >
                   <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             收回投资收到的现金
                </td>
                <td >
                   <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                  取得投资收益收到的现金
                </td>
                <td >
                   <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                 处置固定资产、无形资产和其他长期资产收回的现金净额
                </td>
                <td >
                   <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                   处置子公司及其他营业单位收回的现金
                </td>
                <td >
                   <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                              收到其他与投资活动有关的现金
                </td>
                <td >
                   <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               投资活动现金流入小计
                </td>
                <td >
                   <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                     购建固定资产、无形资产和其他长期资产支付的现金
                </td>
                <td >
                   <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             投资支付的现金
                </td>
                <td >
                   <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                       取得子公司及其他营业单位支付的现金净额
                </td>
                <td >
                   <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                     支付其他与投资活动有关的现金  
                </td>
                <td >
                   <form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                  投资活动现金流出小计
                </td>
                <td >
                   <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                                   投资活动产生的现金流量净额
                </td>
                <td >
                   <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             三、筹资活动产生的现金流量：
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
                                                吸收投资收到的现金
                </td>
                <td >
                   <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                         其中：子公司吸收少数股东投资收到的现金
                </td>
                <td >
                   <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row27Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                     取得借款收到的现金
                </td>
                <td >
                   <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr>   
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                               收到其他与筹资活动有关的现金
                </td>
                <td >
                   <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             筹资活动现金流入小计
                </td>
                <td >
                   <form:input path="row30One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row30Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            偿还债务支付的现金
                </td>
                <td >
                   <form:input path="row31One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row31Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             分配股利、利润或偿付利息支付的现金
                </td>
                <td >
                   <form:input path="row32One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row32Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             其中：子公司支付少数股东投资的股利和利润
                </td>
                <td >
                   <form:input path="row33One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row33Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
             
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             支付其他与筹资活动有关的现金
                </td>
                <td >
                   <form:input path="row34One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row34Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             筹资活动现金流出小计
                </td>
                <td >
                   <form:input path="row35One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row35Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                             筹资活动产生的现金流量净额
                </td>
                <td >
                   <form:input path="row36One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row36Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            四、汇率变动对现金及现金等价物的影响
                </td>
                <td >
                   <form:input path="row37One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row37Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                            五、现金及现金等价物净增加额
                </td>
                <td >
                   <form:input path="row38One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row38Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                           加：期初现金及现金等价物余额
                </td>
                <td >
                   <form:input path="row39One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row39Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
                </td>                             
           </tr> 
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                          六、期末现金及现金等价物余额
                </td>
                <td >
                   <form:input path="row40One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                     
                </td>
                <td >
                   <form:input path="row40Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>                                               
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