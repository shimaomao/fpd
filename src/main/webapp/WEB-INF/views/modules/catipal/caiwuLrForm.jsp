<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小贷财务报表管理</title>
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
		<li><a href="${ctx}/catipal/tCaiwu/listLr">小贷利润表列表</a></li>
		<li class="active"><a href="${ctx}/catipal/tCaiwu/form?informFilingType=2&id=${caiwu.id}">小贷利润表<shiro:hasPermission name="catipal:tCaiwu:edit">${not empty caiwu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="catipal:tCaiwu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form id="inputForm"  action="${ctx}/catipal/tCaiwu/save" method="post" enctype="multipart/form-data" class="form-horizontal" >		
		<sys:message content="${message}"/>			
		
		<input id="caiwu.id" name="id"   type="hidden" value="${caiwu.id}"   />
		<input id="caiwu.informFilingType" name="informFilingType"   type="hidden" value="${caiwu.informFilingType == null  ? 2 : caiwu.informFilingType}"   />
	
	
		
		<table class="table-form">	
							
	      <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
             
             <td   style="width: 60px;"></td>
          
    		 <td  >所属月份：<input id="caiwu.baoDate" name="baoDate"  maxlength="20" type="text" value="${caiwu.baoDate}" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt: 'yyyy-MM'})" /></td>
            <td  colspan="2"><!-- 附件:--> </td> 
            <td >
            <!-- 
            	 <c:if test="${caiwu.filePath eq null}">
				    	<input id="file" name="file" class="inputbox set_form_text" maxlength="200" type="file" >	 
				    	<span style="color: red">请上传excel版本</span>
				  </c:if>
				  <c:if test="${caiwu.filePath ne null}">					
					    	<a href="${ct}${caiwu.filePath}" >${caiwu.fileName}</a><a href="${ctx}/catipal/tCaiwu/deleteCWFile?informFilingType=2&id=${caiwu.id}">删除</a>    
					   
				  </c:if>
            -->
            </td>  
          
            <td >金额单位：元	</td>
          </tr>	
          
          
          <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td style="width: 60px;" colspan="3" >项目</td>
            <td  style="width: 20px;" colspan="1" >行次</td>
            <td  style="width: 10px;" >本月数</td>
            <td  style="width: 10px;" >本年累计数</td>
           
           
          </tr>
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" >一、营业收入</td>
            <td colspan="1">1</td>
            <td ><input id="" name="c1"  type="text" value="${ caiwu.c1}" /></td>
            <td ><input id="" name="c1t"  type="text" value="${ caiwu.c1t}" /></td>
            
          
           
          </tr>              	
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" >  利息净收入</td>
            <td colspan="1">2</td>
            <td ><input id="" name="c2"  type="text" value="${ caiwu.c2}" /></td>
            <td ><input id="" name="c2t"  type="text" value="${ caiwu.c2t}" /></td>
            
         
           
          </tr>
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" >利息收入</td>
            <td colspan="1">3</td>
            <td ><input id="" name="c3"  type="text" value="${ caiwu.c3}" /></td>
            <td ><input id="" name="c3t"  type="text" value="${ caiwu.c3t}" /></td>
            
        
           
          </tr>
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" >存款利息收入</td>
            <td >4</td>
            <td ><input id="" name="c4"  type="text" value="${ caiwu.c4}" /></td>
            <td ><input id="" name="c4t"  type="text" value="${ caiwu.c4t}" /></td>
            
         
           
          </tr>
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" > 贷款利息收入</td>
            <td >	5</td>
            <td ><input id="" name="c5"  type="text" value="${ caiwu.c5}" /></td>
            <td ><input id="" name="c5t"  type="text" value="${ caiwu.c5t}" /></td>
           
           
          </tr>
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" > 利息支出</td>
            <td >	6</td>
            <td ><input id="" name="c6"  type="text" value="${ caiwu.c6}" /></td>
            <td ><input id="" name="c6t"  type="text" value="${ caiwu.c6t}" /></td>
            
          
          </tr>
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3" >手续费及佣金净收入</td>
            <td >	7</td>
            <td ><input id="" name="c7"  type="text" value="${ caiwu.c7}" /></td>
            <td ><input id="" name="c7t"  type="text" value="${ caiwu.c7t}" /></td>
            
           
           
          </tr>
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  > 手续费及佣金收入</td>
            <td >	8</td>
            <td ><input id="" name="c8"  type="text" value="${ caiwu.c8}" /></td>
            <td ><input id="" name="c8t"  type="text" value="${ caiwu.c8t}" /></td>
            
         
           
          </tr>
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 手续费及佣金支出</td>
            <td >	9</td>
            <td ><input id="" name="c9"  type="text" value="${ caiwu.c9}" /></td>
            <td ><input id="" name="c9t"  type="text" value="${ caiwu.c9t}" /></td>
         
          </tr>
          			
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3"  >投资收益（损失以“-”号填列）</td>
            <td >	10</td>
            <td ><input id="" name="c10"  type="text" value="${ caiwu.c10}" /></td>
            <td ><input id="" name="c10t"  type="text" value="${ caiwu.c10t}" /></td>
         
          </tr>		
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 其中：对联营企业和合营企业的投资收益</td>
            <td >	11</td>
            <td ><input id="" name="c11"  type="text" value="${ caiwu.c11}" /></td>
            <td ><input id="" name="c11t"  type="text" value="${ caiwu.c11t}" /></td>
            
         
           
          </tr>	
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 公允价格变动收益（损失以“-”号填列）</td>
            <td >	12	</td>
            <td ><input id="" name="c12"  type="text" value="${ caiwu.c12}" /></td>
            <td ><input id="" name="c12t"  type="text" value="${ caiwu.c12t}" /></td>         
           
          </tr>	
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >汇兑收益（损失以“-”号填列）</td>
            <td >	13	</td>
            <td ><input id="" name="c13"  type="text" value="${ caiwu.c13}" /></td>
            <td ><input id="" name="c13t"  type="text" value="${ caiwu.c13t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >其他业务收入</td>
            <td >	14	</td>
            <td ><input id="" name="c14"  type="text" value="${ caiwu.c14}" /></td>
            <td ><input id="" name="c14t"  type="text" value="${ caiwu.c14t}" /></td>         
           
          </tr>	
          
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >二、营业支出</td>
            <td >	15	</td>
            <td ><input id="" name="c15"  type="text" value="${ caiwu.c15}" /></td>
            <td ><input id="" name="c15t"  type="text" value="${ caiwu.c15t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >营业税金及附加</td>
            <td >	16	</td>
            <td ><input id="" name="c16"  type="text" value="${ caiwu.c16}" /></td>
            <td ><input id="" name="c16t"  type="text" value="${ caiwu.c16t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3"  >业务及管理费</td>
            <td >	17	</td>
            <td ><input id="" name="c17"  type="text" value="${ caiwu.c17}" /></td>
            <td ><input id="" name="c17t"  type="text" value="${ caiwu.c17t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 资产减值损失</td>
            <td >	18	</td>
            <td ><input id="" name="c18"  type="text" value="${ caiwu.c18}" /></td>
            <td ><input id="" name="c18t"  type="text" value="${ caiwu.c18t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >其他业务成本</td>
            <td >	19	</td>
            <td ><input id="" name="c19"  type="text" value="${ caiwu.c19}" /></td>
            <td ><input id="" name="c19t"  type="text" value="${ caiwu.c19t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 财务费用</td>
            <td >	20	</td>
            <td ><input id="" name="c20"  type="text" value="${ caiwu.c20}" /></td>
            <td ><input id="" name="c20t"  type="text" value="${ caiwu.c20t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >三、营业利润（亏损以“-”号填列）</td>
            <td >	21	</td>
            <td ><input id="" name="c21"  type="text" value="${ caiwu.c21}" /></td>
            <td ><input id="" name="c21t"  type="text" value="${ caiwu.c21t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >加：营业外收入</td>
            <td >	22	</td>
            <td ><input id="" name="c22"  type="text" value="${ caiwu.c22}" /></td>
            <td ><input id="" name="c22t"  type="text" value="${ caiwu.c22t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >  减：营业外支出</td>
            <td >	23	</td>
            <td ><input id="" name="c23"  type="text" value="${ caiwu.c23}" /></td>
            <td ><input id="" name="c23t"  type="text" value="${ caiwu.c23t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >四、利润总额（亏损总额以“-”号填列）</td>
            <td >	24	</td>
            <td ><input id="" name="c24"  type="text" value="${ caiwu.c24}" /></td>
            <td ><input id="" name="c24t"  type="text" value="${ caiwu.c24t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >减：所得税费用</td>
            <td >	25	</td>
            <td ><input id="" name="c25"  type="text" value="${ caiwu.c25}" /></td>
            <td ><input id="" name="c25t"  type="text" value="${ caiwu.c25t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  >五、净利润（净亏损以“-”号填列）</td>
            <td >	26	</td>
            <td ><input id="" name="c26"  type="text" value="${ caiwu.c26}" /></td>
            <td ><input id="" name="c26t"  type="text" value="${ caiwu.c26t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > 加：（一）年初未分配利润</td>
            <td >	27	</td>
            <td ><input id="" name="c27"  type="text" value="${ caiwu.c27}" /></td>
            <td ><input id="" name="c27t"  type="text" value="${ caiwu.c27t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（二）盈余公积补亏 </td>
            <td >	28	</td>
            <td ><input id="" name="c28"  type="text" value="${ caiwu.c28}" /></td>
            <td ><input id="" name="c28t"  type="text" value="${ caiwu.c28t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（三）其他调整因素</td>
            <td >	29	</td>
            <td ><input id="" name="c29"  type="text" value="${ caiwu.c29}" /></td>
            <td ><input id="" name="c29t"  type="text" value="${ caiwu.c29t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  >六、可供分配利润</td>
            <td >	30	</td>
            <td ><input id="" name="c30"  type="text" value="${ caiwu.c30}" /></td>
            <td ><input id="" name="c30t"  type="text" value="${ caiwu.c30t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  >减：（一）提取法定盈余公积</td>
            <td >	31	</td>
            <td ><input id="" name="c31"  type="text" value="${ caiwu.c31}" /></td>
            <td ><input id="" name="c31t"  type="text" value="${ caiwu.c31t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  > （二）提取一般风险准备</td>
            <td >	32	</td>
            <td ><input id="" name="c32"  type="text" value="${ caiwu.c32}" /></td>
            <td ><input id="" name="c32t"  type="text" value="${ caiwu.c32t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（三）提取职工奖励及福利费基金</td>
            <td >	33	</td>
            <td ><input id="" name="c33"  type="text" value="${ caiwu.c33}" /></td>
            <td ><input id="" name="c33t"  type="text" value="${ caiwu.c33t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（四）提取企业发展基金</td>
            <td >	34	</td>
            <td ><input id="" name="c34"  type="text" value="${ caiwu.c34}" /></td>
            <td ><input id="" name="c34t"  type="text" value="${ caiwu.c34t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（五）利润归还投资</td>
            <td >	35	</td>
            <td ><input id="" name="c35"  type="text" value="${ caiwu.c35}" /></td>
            <td ><input id="" name="c35t"  type="text" value="${ caiwu.c35t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（六）其他</td>
            <td >	36</td>
            <td ><input id="" name="c36"  type="text" value="${ caiwu.c36}" /></td>
            <td ><input id="" name="c36t"  type="text" value="${ caiwu.c36t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >七、可供投资者分配的利润</td>
            <td >	37</td>
            <td ><input id="" name="c37"  type="text" value="${ caiwu.c37}" /></td>
            <td ><input id="" name="c37t"  type="text" value="${ caiwu.c37t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >减：（一）应付优先股股利</td>
            <td >	38	</td>
            <td ><input id="" name="c38"  type="text" value="${ caiwu.c38}" /></td>
            <td ><input id="" name="c38t"  type="text" value="${ caiwu.c38t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td colspan="3"  >（二）提取任意盈余公积</td>
            <td >	39	</td>
            <td ><input id="" name="c39"  type="text" value="${ caiwu.c39}" /></td>
            <td ><input id="" name="c39t"  type="text" value="${ caiwu.c39t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（三）应付普通股股利（应付利润）</td>
            <td >	40	</td>
            <td ><input id="" name="c40"  type="text" value="${ caiwu.c40}" /></td>
            <td ><input id="" name="c40t"  type="text" value="${ caiwu.c40t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（四）转作资本（股本）的普通股股利</td>
            <td >	41	</td>
            <td ><input id="" name="c41"  type="text" value="${ caiwu.c41}" /></td>
            <td ><input id="" name="c41t"  type="text" value="${ caiwu.c41t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（五）其他</td>
            <td >	42	</td>
            <td ><input id="" name="c42"  type="text" value="${ caiwu.c42}" /></td>
            <td ><input id="" name="c42t"  type="text" value="${ caiwu.c42t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >八、未分配利润</td>
            <td >	43	</td>
            <td ><input id="" name="c43"  type="text" value="${ caiwu.c43}" /></td>
            <td ><input id="" name="c43t"  type="text" value="${ caiwu.c43t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >九、每股收益</td>
            <td >	44	</td>
            <td ><input id="" name="c44"  type="text" value="${ caiwu.c44}" /></td>
            <td ><input id="" name="c44t"  type="text" value="${ caiwu.c44t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" >（一）基本每股收益</td>
            <td >	45	</td>
            <td ><input id="" name="c45"  type="text" value="${ caiwu.c45}" /></td>
            <td ><input id="" name="c45t"  type="text" value="${ caiwu.c45t}" /></td>         
           
          </tr>	
          <tr  style="text-align:center;line-height: 23px;" class="tit_left_bg">
            <td  colspan="3" > （二）稀释每股收益</td>
            <td >	46	</td>
            <td ><input id="" name="c46"  type="text" value="${ caiwu.c46}" /></td>
            <td ><input id="" name="c46t"  type="text" value="${ caiwu.c46t}" /></td>         
           
          </tr>	
          
          
          
       
        </table>	
        
         <br></br>
		 单位负责人：<input id="" name="cf"  type="text" value="${ caiwu.cf}" class="input-xlarge " style="width:180px;" />
		 
		 &nbsp;&nbsp;
		 财务主管：<input id="" name="cj"  type="text" value="${ caiwu.cj}" class="input-xlarge " style="width:180px;" />
		  &nbsp;&nbsp;
		  复核：<input id="" name="cd"  type="text" value="${ caiwu.cd}" class="input-xlarge " style="width:180px;" />
		  &nbsp;&nbsp;
		制表：<input id="" name="cz"  type="text" value="${ caiwu.cz}"  class="input-xlarge " style="width:180px;"/>
		
		
		
		
		<div class="form-actions">
			<shiro:hasPermission name="catipal:tCaiwu:edit"></shiro:hasPermission><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>