<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>利润分析管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/balanceprofit/tBalanceProfit/">利润分析列表</a></li>
		<li class="active"><a href="${ctx}/balanceprofit/tBalanceProfit/form?id=${tBalanceProfit.id}">利润分析<shiro:hasPermission name="balanceprofit:tBalanceProfit:edit">${not empty tBalanceProfit.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balanceprofit:tBalanceProfit:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tBalanceProfit" action="${ctx}/balanceprofit/tBalanceProfit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
	
		
		<table class="table-form">
		   <tr>
              <td>报表名称
              </td>
              <td colspan="2">
                   <form:input path="reportName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
              </td>
           </tr>   
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                                项目
                </td>
                <td>
                                                 本期数
                </td>
                <td>
                                                 本年数
                </td>
           </tr>
           <tr>
              <td>  
                                           一、营业收入       
              </td>
              <td>
                <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>
              </td>
              <td>
                 <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>
              </td>       
           </tr>
           <tr>
              <td style="padding-left:14px;">贷款利息收入                  
              </td>
              <td><form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>
              <td><form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>        
           </tr>
           <tr>
              <td style="padding-left:14px;">存款利息收入                   
              </td>
              <td><form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>
              <td><form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>             
           </tr>
           <tr>
              <td style="padding-left:14px;">手续费及佣金收入                   
              </td>
              <td><form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>
              <td><form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                      
              </td>             
           </tr>
           <tr>
              <td style="padding-left:14px;">租赁收益                
              </td>
              <td><form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>          
           </tr>
           <tr>
              <td style="padding-left:14px;">公允价值变动收益               
              </td>
              <td><form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>        
           </tr>
           <tr>
              <td style="padding-left:14px;">咨询收入               
              </td>
              <td><form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>         
           </tr>
           <tr>
              <td style="padding-left:14px;">汇兑收益                   
              </td>
              <td><form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr>
           <tr>
              <td style="padding-left:14px;">其他营业收入            
              </td>
              <td><form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr>
           <tr>
              <td>二、营业支出         
              </td>
              <td><form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr>   
           <tr>
              <td style="padding-left:14px;">利息支出
              </td>
              <td><form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr> 
           <tr>
              <td style="padding-left:14px;">资产减值损失
              </td>
              <td><form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr> 
           <tr>
              <td style="padding-left:14px;">手续费及佣金支出
              </td>
              <td><form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                 
           </tr>
           <tr>
              <td style="padding-left:14px;">业务及管理费用
              </td>
              <td><form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>             
           </tr>
           <tr>
              <td style="padding-left:14px;">汇兑损失
              </td>
              <td><form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr>  
           <tr>
              <td style="padding-left:14px;">其他营业支出
              </td>
              <td><form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>           
           </tr>   
           <tr>
              <td>三、营业税金及附加
              </td>
              <td><form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>               
           </tr>    
           <tr>
              <td>四、营业利润
              </td>
              <td><form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr>   
           <tr>
              <td style="padding-left:14px;">加：投资收益
              </td>
              <td><form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr> 
           <tr>
              <td style="padding-left:14px;">加：营业外收入
              </td>
              <td><form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                
           </tr>  
           <tr>
              <td style="padding-left:14px;">减：营业外支出
              </td>
              <td><form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                 
           </tr> 
           <tr>
              <td style="padding-left:14px;">减：以前年度损益调整
              </td>
              <td><form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>               
           </tr>  
           <tr>
              <td>五：利润总额
              </td>
              <td><form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>                 
           </tr>   
           <tr>
              <td style="padding-left:14px;">减：所得税
              </td>
              <td><form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr>  
           <tr>
              <td>六、净利润
              </td>
              <td><form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr>   
            <tr>
              <td>备注
              </td>
              <td colspan="2">
                   <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
              </td>
           </tr>                                                                                                         
         </table>
		
		
		
		<div class="form-actions">
			<shiro:hasPermission name="balanceprofit:tBalanceProfit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>