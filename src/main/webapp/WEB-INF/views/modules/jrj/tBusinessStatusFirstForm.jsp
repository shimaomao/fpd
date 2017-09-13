<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>资产负债管理</title>
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
                                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
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
                 <td >
                       <form:input path="rowFour" htmlEscape="false" maxlength="64" class="input-xlarge required" style="width:180px;"/>                       
                </td>                        
           </tr>
           
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    2
                </td>
                <td >
                    1.1.2票据承兑担保
                </td>
                <td>
                    <form:input path="row1Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>
                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    3
                </td>
                <td >
                    1.1.3信用证担保
                </td>
                <td>
                    <form:input path="row2Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    4
                </td>
                <td >
                    1.1.4其它融资性担保项
                </td>
                <td>
                    <form:input path="row3Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    5
                </td>
                <td >
                    1.1担保金额小计
                </td>
                <td>
                    <form:input path="row4Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    6
                </td>
                <td >
                    1.2担保户数
                </td>
                <td>
                    <form:input path="row5Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    7
                </td>
                <td >
                    1.3代偿金额
                </td>
                <td>
                    <form:input path="row6Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    8
                </td>
                <td >
                    1.4损失金额
                </td>
                <td>
                    <form:input path="row7Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    9
                </td>
                <td rowspan=6>
                   2.非融资性担保业务
                </td>
                <td >
                    2.1.1诉讼保全担保
                </td>
                <td>
                    <form:input path="row8Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    10
                </td>             
                <td >
                    2.1.2履约担保
                </td>
                <td>
                    <form:input path="row9Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    11
                </td>             
                <td >
                    2.1.3其它非融资性担保
                </td>
                <td>
                    <form:input path="row10Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    12
                </td>             
                <td >
                    2.1担保金额小计
                </td>
                <td>
                    <form:input path="row11Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    13
                </td>             
                <td >
                    2.2代偿金额
                </td>
                <td>
                    <form:input path="row12Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    14
                </td>             
                <td >
                    2.3损失金额
                </td>
                <td>
                    <form:input path="row13Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    15
                </td>
                <td rowspan=4>
                    3.债券发行担保
                </td>             
                <td >
                    3.1担保金额
                </td>
                <td>
                    <form:input path="row14Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    16
                </td>                            
                <td >
                    3.2担保户数
                </td>
                <td>
                    <form:input path="row15Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    17
                </td>                            
                <td >
                    3.3代偿金额
                </td>
                <td>
                    <form:input path="row16Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
            </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    18
                </td>                            
                <td >
                    3.4损失金额
                </td>
                <td>
                    <form:input path="row17Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
             </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    19
                </td>
                <td rowspan=3> 
                    4.再担保
                </td>                            
                <td >
                    4.1担保金额
                </td>
                <td>
                    <form:input path="row18Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
             </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    20
                </td>                                           
                <td >
                    4.2代偿金额
                </td>
                <td>
                    <form:input path="row19Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
             </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    21
                </td>                                           
                <td >
                    4.3损失金额
                </td>
                <td>
                    <form:input path="row20Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
              </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    22
                </td> 
                <td rowspan=3>
                    5.担保业务合计
                </td>                                           
                <td >
                    5.1担保金额合计
                </td>
                <td>
                    <form:input path="row21Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
              </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    23
                </td>                                                           
                <td >
                    5.2代偿金额合计
                </td>
                <td>
                    <form:input path="row22Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
                </td>                             
              </tr>
              <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    24
                </td>                                                           
                <td >
                    5.3损失金额合计
                </td>
                <td>
                    <form:input path="row23Four" htmlEscape="false" maxlength="64" class="input-xlarge required"  style="width:180px;"/>
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