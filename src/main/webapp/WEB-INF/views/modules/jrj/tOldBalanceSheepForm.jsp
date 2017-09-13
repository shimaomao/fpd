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
		<li><a href="${ctx}/balancesheep/oldJrjBalanceSheep/">旧资产负债列表</a></li>
		<li class="active"><a href="${ctx}/balancesheep/oldJrjBalanceSheep/form?id=${tBalanceSheep.id}">资产负债<shiro:hasPermission name="balancesheep:tBalanceSheep:edit"></shiro:hasPermission><shiro:lacksPermission name="balancesheep:tBalanceSheep:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="tBalanceSheep" action="${ctx}/balancesheep/oldJrjBalanceSheep/save" method="post" class="form-horizontal">
	          编制单位：<form:input path="companyName" htmlEscape="false"  class="input-xlarge " style="width:180px;"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报出日期:<input name="submitDate" id="submitDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate " value="${tBalanceSheep.submitDate}" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});" style="width:183px;"/>	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：元
		
	
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<br></br>		
	    <table  class="table-form">		       
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td >
                                               资产	
                </td>
                <td >
                                             行次 
                </td>
                <td>
                                           年初数
                </td>
                <td>
                                                期末数
                </td>
                <td>
                                             负债与所有者权益
                </td>
                <td>
                                          行次
                </td>
                <td>
                                               年初数
                </td>   
                <td>
                                             期末数
                </td>                              
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                    <strong>流动资产:</strong>
                </td>               
                <td>
                    
                </td>
                <td>
                </td>
                <td>
                </td>                
                
                <td  style="text-align:left;color:black;">
                   <strong> 流动负债:</strong>
                </td>
                <td >
                
                </td>
                <td >
                
                </td>    
                <td >
                
                </td>             
           </tr>
           
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            货币资金
                </td>  
                <td>
                  1
                </td>             
                <td>
                    <form:input path="rowOne" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="rowTwo" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          短期借款
                </td>
                <td>
                  37
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
                                            短期投资
                </td>
                <td>
                  2
                </td>       
                <td>
                    <form:input path="row1One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row1Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        应付股利
                </td>
                <td>
                  38
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
                                            应收票据
                </td>
                <td>
                  3
                </td>       
                <td>
                    <form:input path="row2One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row2Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         应付票据
                </td>
                <td>
                  39
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
                                          应收股利
                </td>
                <td>
                  4
                </td>       
                <td>
                    <form:input path="row3One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row3Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         应付手续费
                </td>
                <td>
                  40
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
                                        应收利息
                </td>
                <td>
                  5
                </td>       
                <td>
                    <form:input path="row4One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row4Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         预收帐款
                </td>
                <td>
                  41
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
                                      应收担保费
                </td>
                <td>
                  6
                </td>       
                <td>
                    <form:input path="row5One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row5Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                    存入保证金
                </td>
                <td>
                  42
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
                                          应收帐款
                </td>    
                <td>
                  7
                </td>           
                <td>
                    <form:input path="row6One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row6Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          应付职工薪酬
                </td>
                <td>
                 43
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
                                         减：坏账准备
                </td>    
                <td>
                 8
                </td>           
                <td>
                    <form:input path="row7One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row7Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                            应付福利费
                </td>
                <td>
                  44
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
                                     应收帐款净额
                </td>  
                <td>9
                </td>             
                <td>
                    <form:input path="row8One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row8Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         应交税费
                </td>
                <td>
                 45
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
                                        预付帐款
                </td>  
                <td>
                 10
                </td>             
                <td>
                    <form:input path="row9One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row9Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        其中：应交税金
                </td>
                <td>
                  46
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
                                           应收代偿款
                </td>    
                <td>11</td>           
                <td>
                    <form:input path="row10One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row10Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           预提费用
                </td>
                <td>47</td>
                <td >
                    <form:input path="row10Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row10Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          其他应收款
                </td>      
                <td>12</td>         
                <td>
                    <form:input path="row11One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row11Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        其他应付款
                </td>
                <td>48</td>
                <td >
                    <form:input path="row11Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row11Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            待摊费用
                </td>  
                <td>13</td>             
                <td>
                    <form:input path="row12One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row12Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                           担保赔偿准备
                </td>
                <td>49</td>
                <td >
                    <form:input path="row12Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row12Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            存出保证金
                </td>  
                <td>14</td>             
                <td>
                    <form:input path="row13One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row13Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        短期责任准备
                </td>
                <td>50</td>
                <td >
                    <form:input path="row13Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row13Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            委托贷款
                </td>  
                <td>15</td>             
                <td>
                    <form:input path="row14One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row14Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                       预计负债
                </td>
                <td>51</td>
                <td >
                    <form:input path="row14Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row14Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           其他流动资产
                </td>    
                <td>16</td>           
                <td>
                    <form:input path="row15One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row15Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                          一年内到期的长期负债
                </td>
                <td>52</td>
                <td >
                    <form:input path="row15Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row15Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
          
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                      <strong>流动资产合计</strong> 
                </td>      
                <td>17</td>         
                <td>
                    <form:input path="row16One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row16Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                            其他流动负债
                </td>
                <td>53</td>
                <td >
                    <form:input path="row16Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row16Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                     <strong> 长期投资</strong> 
                </td>  
                <td></td>             
                <td>
                       </td>
                <td>
                      </td>
                <td style="text-align:left;color:black;">
                                         
                    <strong> 流动负债合计</strong> 
                </td>
                <td>54</td>
                <td >
                    <form:input path="row17Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row17Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                           可供出售金融资产
                </td>  
                <td>18</td>             
                <td>
                    <form:input path="row18One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row18Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td style="text-align:left;color:black;">
                                          
                    <strong> 长期负债</strong> 
                </td>
                <td></td>
                <td >
                    </td>
                <td >
                 </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            长期股权投资
                </td>     
                <td>19</td>          
                <td>
                    <form:input path="row19One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row19Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                        长期借款
                </td>
                <td>55</td>
                <td >
                    <form:input path="row19Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row19Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                     <!-- <strong> 流动资产合计</strong> -->
                                                   长期债券投资
                </td> 
                <td>
                  20
                </td>                        
                <td>
                    <form:input path="row20One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row20Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                         应付债券
                </td>
                <td>56</td>
                <td >
                    <form:input path="row20Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row20Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                   
                <td style="text-align:left;color:black;">
                     <strong>长期投资合计</strong>
                </td>      
                <td>21</td>                   
                <td>
                    <form:input path="row21One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row21Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                
                <td style="text-align:left;color:black;">
                     <strong>长期应付款</strong>
                </td> 
                <td>57</td>
                <td >
                    <form:input path="row21Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row21Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                 <td style="text-align:left;color:black;">
                     <strong>固定资产</strong>
                </td>          
                <td></td>     
                <td>	
                     </td>
                <td>
                  </td>
                 <td style="text-align:left;color:black;">
                     <strong>专项应付款</strong>
                </td> 
                <td>58</td>
                <td >
                    <form:input path="row22Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row22Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                            固定资产原价
                </td>   
                <td>22</td>            
                <td>
                    <form:input path="row23One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row23Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td >
                                                 长期责任准备
                </td> 
                <td>59</td>
                <td >
                    <form:input path="row23Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row23Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                          减：累计折旧
                </td>  
                <td>23</td>             
                <td>
                    <form:input path="row24One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row24Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                 <td >
                                          递延所得税负债
                </td> 
                <td>60</td>
                <td >
                    <form:input path="row24Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row24Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                      固定资产净值
                </td> 
                <td>24</td>              
                <td>
                    <form:input path="row25One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row25Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                            其它长期负债
                </td>
                <td>61</td>
                <td >
                    <form:input path="row25Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row25Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                     减：固定资产减值准备
                </td>     
                <td>25</td>          
                <td>
                    <form:input path="row26One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row26Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td style="text-align:left;color:black;">
                      <strong> 长期负债合计 </strong>
                </td>
                <td>62</td>
                <td >
                    <form:input path="row26Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row26Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                     固定资产净额
                </td>   
                <td>26</td>            
                <td>
                    <form:input path="row27One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row27Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td style="text-align:left;color:black;">
                     <strong>    负债合计 </strong>
                </td>
                <td>63</td>
                <td >
                    <form:input path="row27Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row27Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                      固定资产清理
                </td>
                <td>27</td>               
                <td>
                    <form:input path="row28One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row28Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td style="text-align:left;color:black;">
                    <strong>所有者权益</strong>
                </td>
                <td></td>
                <td >
                    </td>
                <td >
                   </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">             
                       <strong>   固定资产合计</strong>
                </td>     
                <td>28</td>          
                <td>
                    <form:input path="row29One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row29Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                     实收资本
                </td>
                <td>64</td>
                <td >
                    <form:input path="row29Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row29Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
                    
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">               
                      <strong>   无形资产及其它资产</strong>
                </td>   
                <td>
                
                </td>            
                <td>  </td>
                <td>
                     </td>
              
                <td >
                	    资本公积
                </td> 
                <td>65</td>
                <td >
                    <form:input path="row30Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row30Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        无形资产
                </td>
                <td>29</td>               
                <td>
                    <form:input path="row31One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row31Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>                
                <td >
                 	  担保扶持基金
                </td> 
                <td>66</td>
                <td >
                    <form:input path="row31Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row31Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        累计摊销
                </td>      
                <td>30</td>         
                <td>
                    <form:input path="row32One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row32Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                     	盈余公积               
                </td>
                <td>67</td>
                <td >
                    <form:input path="row32Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row32Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                     无形资产净值                      
                </td>      
                <td>31</td>         
                <td>
                    <form:input path="row33One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row33Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                	其中：公益金
                </td>
                 <td>68</td>
                <td >
                    <form:input path="row33Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row33Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                     长期待摊费用                   
                </td>      
                <td>32</td>         
                <td>
                    <form:input path="row34One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row34Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                  一般风险准备金
                </td>
                <td>69</td>
                <td >
                    <form:input path="row34Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row34Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
           
           
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        低债资产
                </td>    
                <td>33</td>           
                <td>
                    <form:input path="row35One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row35Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>  
                <td>
                                       未分配利润
                </td>
                <td>70</td>
                <td >
                    <form:input path="row35Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row35Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
             <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td>
                                        其它长期资产
                </td>   
                <td>34</td>            
                <td>
                    <form:input path="row36One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row36Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td>
                                   
                </td>
                <td>71</td>
                <td >
                    </td>
                <td >
                    </td>                 	
           </tr>
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
                <td style="text-align:left;color:black;">
                  <strong>  无形资产及其它资产合计</strong>
                </td>    
                <td>35</td>           
                <td>
                    <form:input path="row37One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row37Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>
                <td style="text-align:left;color:black;">
                      <strong>  所有者权益合计</strong>
                </td>
                <td>72</td>
                <td >
                    <form:input path="row37Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row37Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>
                     
           
            <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">                 
                 <td style="text-align:left;color:black;">
                     <strong>  资产总计</strong>
                </td>  
                <td>36</td>            
                <td>
                    <form:input path="row38One" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/>
                </td>
                <td>
                    <form:input path="row38Two" htmlEscape="false" maxlength="64" class="input-xlarge "  style="width:180px;"/> 
                </td>               
                <td style="text-align:left;color:black;">
                     <strong>负债和所有者权益合计</strong>
                </td> 
                 <td>73</td>   
                <td >
                    <form:input path="row38Three" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>
                <td >
                    <form:input path="row38Four" htmlEscape="false"  maxlength="64" class="input-xlarge " style="width:180px;"/>
                </td>                 
           </tr>                                                                                                 
         </table>
		 <br></br>
		
		<div class="form-actions">
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/> -->
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>