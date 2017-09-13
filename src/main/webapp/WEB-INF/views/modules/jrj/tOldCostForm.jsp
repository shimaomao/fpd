<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>费用表管理</title>
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
		<li><a href="${ctx}/cost/oldCost/">费用表列表</a></li>
		<li class="active"><a href="${ctx}/cost/oldCost/form?id=${jrjOldCost.id}">费用表<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"></shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="jrjOldCost" action="${ctx}/cost/oldCost/save" method="post" class="form-horizontal">
	          编制单位：<form:input path="companyName" htmlEscape="false"  class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjOldCost.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<br></br>		
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td >
                                             
                </td>
                <td >
                                              本月数
                </td>
                <td>
                                              累计数
                </td>
                <td>
                                               
                </td>
                <td>
                                             本月数
                </td>
                <td>
                                              累计数
                </td>                                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                    <strong>管理费用:</strong>
                </td>               
                <td>
                    <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td  style="text-align:left;color:black;">
                   <strong> 营业费用:</strong>
                </td>
                <td >
                    <form:input path="rowThree" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="rowFour" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           业务招待费
                </td>               
                <td>
                    <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        运输支出
                </td>
                <td >
                    <form:input path="row1Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row1Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            职工教育经费
                </td>               
                <td>
                    <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          运杂费
                </td>
                <td >
                    <form:input path="row2Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row2Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          交通费
                </td>               
                <td>
                    <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           经营人员工资
                </td>
                <td >
                    <form:input path="row3Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row3Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          取暖费
                </td>               
                <td>
                    <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          经营人员福利费
                </td>
                <td >
                    <form:input path="row4Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row4Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        水电费
                </td>               
                <td>
                    <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           差旅费
                </td>
                <td >
                    <form:input path="row5Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row5Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         办公费
                </td>               
                <td>
                    <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         广告费
                </td>
                <td >
                    <form:input path="row6Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row6Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          电讯费
                </td>               
                <td>
                    <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          责任准备金
                </td>
                <td >
                    <form:input path="row7Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row7Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                       低值易耗品摊销
                </td>               
                <td>
                    <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        担保赔偿准备
                </td>
                <td >
                    <form:input path="row8Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row8Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            诉讼费
                </td>               
                <td>
                    <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                      
                </td>
                <td >
                    <form:input path="row9Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row9Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          物业费
                </td>               
                <td>
                    <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           
                </td>
                <td >
                    <form:input path="row10Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row10Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        租赁费
                </td>               
                <td>
                    <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          财务费用
                </td>
                <td >
                    <form:input path="row11Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row11Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           职工保险及公积金
                </td>               
                <td>
                    <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        利息收入
                </td>
                <td >
                    <form:input path="row12Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row12Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                         费用性税金
                </td>               
                <td>
                    <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                             利息支出
                </td>
                <td >
                    <form:input path="row13Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row13Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           折旧费
                </td>               
                <td>
                    <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                            手续费
                </td>
                <td >
                    <form:input path="row14Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row14Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            工会经费
                </td>               
                <td>
                    <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                      
                </td>
                <td >
                    <form:input path="row15Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row15Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                     <strong> 其他</strong>
                </td>          
                         
                <td>
                    <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           
                </td>
                <td >
                    <form:input path="row16Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row16Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                   
                 <td style="text-align:left;color:black;">
                     <strong>审计费：</strong>
                </td>                         
                <td>
                    <form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                   
                </td>
                <td >
                    <form:input path="row17Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row17Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           无形资产摊销
                </td>               
                <td>
                    <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                      
                </td>
                <td >
                    <form:input path="row18Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row18Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                             劳动保护费
                </td>               
                <td>
                    <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
               
                <td >
                    
                </td> 
                
                <td >
                    <form:input path="row19Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row19Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          评估费
                </td>               
                <td>
                    <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                 <td >
                     
                </td> 
                <td >
                    <form:input path="row20Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row20Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                      律师费
                </td>               
                <td>
                    <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                             
                </td>
                <td >
                    <form:input path="row21Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row21Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
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