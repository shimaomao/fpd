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
	    function checkNumber(value){
	       value = value.replace(",","");
	       if(isNaN(value)){
	         alert("请输入正确的金额数字！");
	         return;
	       }
	    }
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/balancesheep/tBalanceSheep/">资产负债列表</a></li>
		<li class="active"><a href="${ctx}/balancesheep/tBalanceSheep/form?id=${tBalanceSheep.id}">资产负债<shiro:hasPermission name="balancesheep:tBalanceSheep:edit">${not empty tBalanceSheep.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tBalanceSheep" action="${ctx}/balancesheep/tBalanceSheep/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		 <table  class="table-form">
		   <tr>
              <td>报表名称
              </td>
              <td colspan="5">
                   <form:input path="reportName" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
              </td>
           </tr>       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                                资产
                </td>
                <td>
                                                 期初数
                </td>
                <td>
                                                 期末数
                </td>
                <td>
                                                  负债及净资产
                </td>
                <td>
                                                 期初数
                </td>
                <td>
                                                期末数
                </td>
           </tr>
           <tr style="line-height: 23px;">
              <td>  
                                           流动资产：                 
              </td><td>&nbsp;</td><td>&nbsp;</td>
              <td> 
                                            流动负债：                  
              </td>
              <td>&nbsp;</td><td>&nbsp;</td>              
           </tr>
           <tr>
              <td>现金及银行存款                  
              </td>
              <td><form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>               
              </td>
              <td><form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>         
              </td>
              <td>短期借款                   
              </td>
              <td><form:input path="rowThree" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                
              </td>
              <td><form:input path="rowFour" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>               
              </td>              
           </tr>
           <tr>
              <td>存放中央银行款项                   
              </td>
              <td><form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>           
              </td>
              <td><form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>
              <td>交易性金融负债                   
              </td>
              <td><form:input path="row1Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>             
              </td>
              <td><form:input path="row1Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>         
              </td>              
           </tr>
           <tr>
              <td>存放同业款项                   
              </td>
              <td><form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>              
              </td>
              <td><form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                     
              </td>
              <td>应付票据                   
              </td>
              <td><form:input path="row2Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                    
              </td>
              <td><form:input path="row2Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                   
              </td>              
           </tr>
           <tr>
              <td>拆放金融性公司                   
              </td>
              <td><form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                
              </td>
              <td><form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付账款                   
              </td>
              <td><form:input path="row3Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row3Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>
           <tr>
              <td>短期贷款                   
              </td>
              <td><form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>         
              </td>
              <td><form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>  
              <td>预收款项                   
              </td>
              <td><form:input path="row4Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row4Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>
           <tr>
              <td>其中：抵押、质押贷款                   
              </td>
              <td><form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>存入短期保证金                   
              </td>
              <td><form:input path="row5Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row5Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>                
           </tr>
           <tr>
              <td>应收利息                   
              </td>
              <td><form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付手续费及佣金                   
              </td>
              <td><form:input path="row6Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row6Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>
           <tr>
              <td>减：坏收准备                   
              </td>
              <td><form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付利息                   
              </td>
              <td><form:input path="row7Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row7Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>
           <tr>
              <td>其他应收款               
              </td>
              <td><form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>其他应付款      
              </td>
              <td><form:input path="row8Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row8Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>   
           <tr>
              <td>预付账款
              </td>
              <td><form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付职工薪酬   
              </td>
              <td><form:input path="row9Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row9Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr> 
           <tr>
              <td>短期投资
              </td>
              <td><form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付福利费
              </td>
              <td><form:input path="row10Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row10Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr> 
           <tr>
              <td>其中：国库券
              </td>
              <td><form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应交税费
              </td>
              <td><form:input path="row11Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row11Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>
           <tr>
              <td>其他流动资产
              </td>
              <td><form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>应付股利
              </td>
              <td><form:input path="row12Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row12Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>
           <tr>
              <td>流动资产合计
              </td>
              <td><form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>预提费用
              </td>
              <td><form:input path="row13Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row13Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>  
           <tr>
              <td>长期资产：
              </td>
              <td>                    
              </td>
              <td>              
              </td>  
              <td>发行短期债券
              </td>
              <td><form:input path="row14Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row14Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>   
           <tr>
              <td>中长期贷款
              </td>
              <td><form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>一年内到期的长期负债
              </td>
              <td><form:input path="row15Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row15Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>    
           <tr>
              <td>其中：抵押质押贷款
              </td>
              <td><form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>其他流动负债
              </td>
              <td><form:input path="row16Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row16Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr>   
           <tr>
              <td>逾期贷款
              </td>
              <td><form:input path="row17One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row17Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>流动负债合计
              </td>
              <td><form:input path="row17Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row17Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr> 
           <tr>
              <td>减：贷款呆账准备
              </td>
              <td><form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>长期负债
              </td>
              <td><form:input path="row18Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row18Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>  
           <tr>
              <td>长期投资
              </td>
              <td><form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>专项应付款
              </td>
              <td><form:input path="row19Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row19Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>                
           </tr> 
           <tr>
              <td>其中：国库券
              </td>
              <td><form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>预计负债
              </td>
              <td><form:input path="row20Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row20Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>  
           <tr>
              <td>减：投资风险准备
              </td>
              <td><form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>存入长期保证金
              </td>
              <td><form:input path="row21Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row21Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>   
           <tr>
              <td>固定资产原值
              </td>
              <td><form:input path="row22One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row22Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>发行长期债券
              </td>
              <td><form:input path="row22Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row22Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>  
           <tr>
              <td>减：累计折扣
              </td>
              <td><form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>长期借款
              </td>
              <td><form:input path="row23Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row23Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr> 
           <tr>
              <td>固定资产净值
              </td>
              <td><form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>长期应付款
              </td>
              <td><form:input path="row24Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row24Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>
           <tr>
              <td>固定资产清理
              </td>
              <td><form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row25Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>其他长期负债
              </td>
              <td><form:input path="row25Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row25Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr> 
           <tr>
              <td>在建工程
              </td>
              <td><form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>其中：住房周转金
              </td>
              <td><form:input path="row26Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row26Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>   
           <tr>
              <td>待处理固定资产净损失
              </td>
              <td><form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row27Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>长期负债合计
              </td>
              <td><form:input path="row27Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row27Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr>  
           <tr>
              <td>其他长期资产
              </td>
              <td><form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>负债合计
              </td>
              <td><form:input path="row28Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row28Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr>  
           <tr>
              <td>长期资产合计
              </td>
              <td><form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>所有者权益：
              </td>
              <td>                
              </td>
              <td>             
              </td>              
           </tr>  
           <tr>
              <td>无形及其他资产：
              </td>
              <td>                  
              </td>
              <td>            
              </td>  
              <td>实收资本
              </td>
              <td><form:input path="row30Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row30Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr>  
           <tr>
              <td>无形资产
              </td>
              <td><form:input path="row31One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row31Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>资本公积
              </td>
              <td><form:input path="row31Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row31Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>               
           </tr>  
           <tr>
              <td>其中：土地使用权
              </td>
              <td><form:input path="row32One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row32Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>盈余公积
              </td>
              <td><form:input path="row32Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row32Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>    
           <tr>
              <td>长期待摊费用
              </td>
              <td><form:input path="row33One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row33Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>一般准备
              </td>
              <td><form:input path="row33Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row33Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>         
           </tr>  
           <tr>
              <td>其他资产
              </td>
              <td><form:input path="row34One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row34Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>未分配利润
              </td>
              <td><form:input path="row34Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row34Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr> 
           <tr>
              <td>其他资产合计
              </td>
              <td><form:input path="row35One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row35Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>所有者权益合计
              </td>
              <td><form:input path="row35Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row35Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>              
           </tr>    
           <tr>
              <td>资产总计
              </td>
              <td><form:input path="row36One" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row36Two" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>  
              <td>负债及所有者权益合计
              </td>
              <td><form:input path="row36Three" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                  
              </td>
              <td><form:input path="row36Four" htmlEscape="false" maxlength="64" class="input-xlarge " onchange="checkNumber(this.value);" style="width:180px;"/>                 
              </td>             
           </tr>    
             <tr>
              <td>备注
              </td>
              <td colspan="5">
                   <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
              </td>
           </tr>                                                                                                    
         </table>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>