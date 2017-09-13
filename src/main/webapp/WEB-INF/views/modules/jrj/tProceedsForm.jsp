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
		<li><a href="${ctx}/proceeds/jrjProceeds/">收益情况列表</a></li>
		<li class="active"><a href="${ctx}/proceeds/jrjProceeds/form?id=${jrjProceeds.id}">收益情况<shiro:hasPermission name="balancesheep:tBalanceSheep:edit">${not empty jrjProceeds.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<p>表号 ：          c3表</p>
	<p>制表机关 ：          中国银行监督管委员会</p>
	<p>批准机关：          国家统计局</p>
	<p>批准文号：          国统制[2010]97号</p>
	<p>数据单位：          万元</p>
	<p>填报单位：          ${officeName}</p>
	<p>填报时间：          ${createTime}</p>
	
	<form:form id="inputForm" modelAttribute="jrjProceeds" action="${ctx}/proceeds/jrjProceeds/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		 <table  class="table-form">		    
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td rowspan=3>
                                               序号
                </td>
                <td rowspan=3 >
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
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td colspan=3>
                                                本期累计数(发生额)
                </td>                             
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td >
                                              公司制
                </td>
                <td >
                                             非公司制
                </td>
                <td >
                                              合计
                </td>                             
           </tr>
           
           
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    1
                </td>
                <td >
                    1.担保收入
                </td>
                <td>
                    <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'rowTwo','rowThree');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'rowOne','rowThree');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="rowThree" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    2
                </td>
                <td >
                                              其中:融资性担保费收入
                </td>
                <td>
                    <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row1Two','row1Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row1One','row1Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row1Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    3
                </td>
                <td >
                    2.担保业务成本 
                </td>
                <td>
                    <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row2Two','row2Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row2One','row2Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row2Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    4
                </td>
                <td >
                                                其中:融资性担保赔偿支出                   
                </td>
                <td>
                    <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row3Two','row3Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row3One','row3Three');" style="width:180px;"/> 
                </td>
                <td >
                     <form:input path="row3Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    5
                </td>
                <td>
                                                     融资性分担保费支出                                                 
                </td>
                
                <td>
                    <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row4Two','row4Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row4One','row4Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row4Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    6
                </td>                
                <td >
                                            营业税金及附加                   
                </td>
                <td>
                    <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row5Two','row5Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row5One','row5Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row5Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    7
                </td>                
                <td >
                   3.担保业务利润                                               
                </td>
                <td>
                    <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row6Two','row6Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row6One','row6Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row6Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    8
                </td>                
                <td >
                   4.利息净收入(净支出则前加"-"号填列)                                                 
                </td>
                <td>
                    <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row7Two','row7Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row7One','row7Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row7Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    9
                </td>       
                <td >
                     5.其它业务利润
                </td>                      
                <td>
                    <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row8Two','row8Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row8One','row8Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row8Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    10
                </td>                            
                <td >
                   6.业务及管理费                                                                  
                </td>
                <td>
                    <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row9Two','row9Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row9One','row9Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row9Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
            
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    11
                </td>                            
                <td >
                    7.投资收益(投资损失则前加"-"号填列)                                                                      
                </td>
                <td>
                    <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row10Two','row10Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row10One','row10Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row10Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    12
                </td>                            
                <td >
                    8:营业利润                                                                    
                </td>
                <td>
                    <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row11Two','row11Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row11One','row11Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row11Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    13
                </td>                            
                <td >
                    9.营业外净收入(净亏损则前加"-"号填列)                                                                  
                </td>
                <td>
                    <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row12Two','row12Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row12One','row12Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row12Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    14
                </td>                            
                <td >
                   10.资产减值损失(转回的金额则前加"-"号填列)                                                             
                </td>
                <td>
                    <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row13Two','row13Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row13One','row13Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row13Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    15
                </td>                            
                <td >
                    11.所得税                                                                  
                </td>
                <td>
                    <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row14Two','row14Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row14One','row14Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row14Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                    16
                </td>                            
                <td >
                   12.净利润(净亏损则前加"-"号填列)                                                          
                </td>
                <td>
                    <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row15Two','row15Three');" style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value,'row15One','row15Three');" style="width:180px;"/> 
                </td>
                <td >
                    <form:input path="row15Three" htmlEscape="false" readOnly='yes' maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>                                                                            
         </table>
		 <br></br>
		 负责人：<form:input path="principal" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;统计负责人: <form:input path="statistics" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;填表人:<form:input path="fitOut" htmlEscape="false" maxlength="64" class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${jrjProceeds.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>