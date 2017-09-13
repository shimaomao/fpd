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
		<li><a href="${ctx}/catipal/tCaiwu/listZcfz">小货资产负债列表</a></li>
		<li class="active"><a href="${ctx}/catipal/tCaiwu/form?informFilingType=1&id=${caiwu.id}">小货资产负债<shiro:hasPermission name="catipal:tCaiwu:edit">${not empty caiwu.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="catipal:tCaiwu:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form id="inputForm"  action="${ctx}/catipal/tCaiwu/save" method="post" enctype="multipart/form-data" class="form-horizontal" >		
		<sys:message content="${message}"/>			
		
		<input id="caiwu.id" name="id"   type="hidden" value="${caiwu.id}"   />
		<input id="caiwu.informFilingType" name="informFilingType"   type="hidden" value="${caiwu.informFilingType == null  ? 2 : caiwu.informFilingType}"   />
	
	
		
		<table class="table-form">	
							
	      <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
             
             <td   colspan="2" style="width: 60px;"></td>
            <td >所属月份：</td>    
    		 <td  ><input id="caiwu.baoDate" name="baoDate"  maxlength="20" type="text" value="${caiwu.baoDate}" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt: 'yyyy-MM'})" /></td>
            <td  colspan="2" > <!-- 附件:--> </td> 
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
          
          
          <tr>
            <td >资 产</td>
            <td >行次</td>
            <td >年初数</td>
            <td >期末数</td>
            <td >负债及所有者权益</td>
            <td >行次</td>
            <td >年初数</td>
            <td >期末数</td>
           
          </tr>
                   	
          
          <tr>
            <td >流动资金:</td>
            <td ></td>
            <td ></td>
            <td ></td>
            
            <td >流动负债：</td>
            <td ></td>
            <td ></td>
            <td ></td>
           
          </tr>
          <tr>
            <td >货币资金</td>
            <td >1</td>
            <td ><input id="" name="c1"  type="text" value="${ caiwu.c1}" /></td>
            <td ><input id="" name="c1t"  type="text" value="${ caiwu.c1t}" /></td>
            
            <td >短期拆借款</td>
            <td >31	</td>
            <td ><input id="" name="c31"  type="text" value="${ caiwu.c31}" /></td>
            <td ><input id="" name="c31t"  type="text" value="${ caiwu.c31t}" /></td>
           
          </tr>
          <tr>
            <td >交易性金融资产</td>
            <td >2</td>
            <td ><input id="" name="c2"  type="text" value="${ caiwu.c2}" /></td>
            <td ><input id="" name="c2t"  type="text" value="${ caiwu.c2t}" /></td>
            
            <td >贴现负债</td>
            <td >32</td>
            <td ><input id="" name="c32"  type="text" value="${ caiwu.c32}" /></td>
            <td ><input id="" name="c32t"  type="text" value="${ caiwu.c32t}" /></td>
           
          </tr>
          
          <tr>
            <td >买入返售金融资产</td>
            <td >	3</td>
            <td ><input id="" name="c3"  type="text" value="${ caiwu.c3}" /></td>
            <td ><input id="" name="c3t"  type="text" value="${ caiwu.c3t}" /></td>
            
            <td >交易性金融资产</td>
            <td >33	</td>
            <td ><input id="" name="c33"  type="text" value="${ caiwu.c33}" /></td>
            <td ><input id="" name="c33t"  type="text" value="${ caiwu.c33t}" /></td>
           
          </tr>
          
          <tr>
            <td > 应收票据</td>
            <td >	4</td>
            <td ><input id="" name="c4"  type="text" value="${ caiwu.c4}" /></td>
            <td ><input id="" name="c4t"  type="text" value="${ caiwu.c4t}" /></td>
            
            <td >卖出回购金融资产款</td>
            <td >	34</td>
            <td ><input id="" name="c34"  type="text" value="${ caiwu.c34}" /></td>
            <td ><input id="" name="c34t"  type="text" value="${ caiwu.c34t}" /></td>
           
          </tr>
          <tr>
            <td >应收账款</td>
            <td >	5</td>
            <td ><input id="" name="c5"  type="text" value="${ caiwu.c5}" /></td>
            <td ><input id="" name="c5t"  type="text" value="${ caiwu.c5t}" /></td>
            
            <td >应付票据</td>
            <td >	35</td>
            <td ><input id="" name="c35"  type="text" value="${ caiwu.c35}" /></td>
            <td ><input id="" name="c35t"  type="text" value="${ caiwu.c35t}" /></td>
           
          </tr>
          
          <tr>
            <td > 预付账款</td>
            <td >	6</td>
            <td ><input id="" name="c6"  type="text" value="${ caiwu.c6}" /></td>
            <td ><input id="" name="c6t"  type="text" value="${ caiwu.c6t}" /></td>
            
            <td >应付账款	</td>
            <td >36	</td>
            <td ><input id="" name="c36"  type="text" value="${ caiwu.c36}" /></td>
            <td ><input id="" name="c36t"  type="text" value="${ caiwu.c36t}" /></td>
           
          </tr>
          
          <tr>
            <td > 应收股利</td>
            <td >	7</td>
            <td ><input id="" name="c7"  type="text" value="${ caiwu.c7}" /></td>
            <td ><input id="" name="c7t"  type="text" value="${ caiwu.c7t}" /></td>
            
            <td >	预收款项</td>
            <td >	37</td>
            <td ><input id="" name="c37"  type="text" value="${ caiwu.c37}" /></td>
            <td ><input id="" name="c37t"  type="text" value="${ caiwu.c37t}" /></td>
           
          </tr>
          			
          <tr>
            <td >应收利息</td>
            <td >	8</td>
            <td ><input id="" name="c8"  type="text" value="${ caiwu.c8}" /></td>
            <td ><input id="" name="c8t"  type="text" value="${ caiwu.c8t}" /></td>
            
            <td >应付职工薪酬</td>
            <td >	38</td>
            <td ><input id="" name="c38"  type="text" value="${ caiwu.c38}" /></td>
            <td ><input id="" name="c38t"  type="text" value="${ caiwu.c38t}" /></td>
           
          </tr>		
          
          <tr>
            <td > 其他应收款</td>
            <td >	9</td>
            <td ><input id="" name="c9"  type="text" value="${ caiwu.c9}" /></td>
            <td ><input id="" name="c9t"  type="text" value="${ caiwu.c9t}" /></td>
            
            <td >应交税费</td>
            <td >	39</td>
            <td ><input id="" name="c39"  type="text" value="${ caiwu.c39}" /></td>
            <td ><input id="" name="c39t"  type="text" value="${ caiwu.c39t}" /></td>
           
          </tr>	
          
          <tr>
            <td >贴现资产</td>
            <td >	10	</td>
            <td ><input id="" name="c10"  type="text" value="${ caiwu.c10}" /></td>
            <td ><input id="" name="c10t"  type="text" value="${ caiwu.c10t}" /></td>
            
            <td >应付利息</td>
            <td >	40</td>
            <td ><input id="" name="c40"  type="text" value="${ caiwu.c40}" /></td>
            <td ><input id="" name="c40t"  type="text" value="${ caiwu.c40t}" /></td>
           
          </tr>	
          
          <tr>
            <td >短期贷款及垫款</td>
            <td >	11</td>
            <td ><input id="" name="c11"  type="text" value="${ caiwu.c11}" /></td>
            <td ><input id="" name="c11t"  type="text" value="${ caiwu.c11t}" /></td>
            
            <td >应付股利</td>
            <td >	41</td>
            <td ><input id="" name="c41"  type="text" value="${ caiwu.c41}" /></td>
            <td ><input id="" name="c41t"  type="text" value="${ caiwu.c41t}" /></td>
           
          </tr>	
          
          <tr>
            <td > 减：贷款损失准备</td>
            <td >	12</td>
            <td ><input id="" name="c12"  type="text" value="${ caiwu.c12}" /></td>
            <td ><input id="" name="c12t"  type="text" value="${ caiwu.c12t}" /></td>
            
            <td >其他应付款</td>
            <td >	42</td>
            <td ><input id="" name="c42"  type="text" value="${ caiwu.c42}" /></td>
            <td ><input id="" name="c42t"  type="text" value="${ caiwu.c42t}" /></td>
           
          </tr>	
          
          <tr>
            <td >  存货</td>
            <td >	13</td>
            <td ><input id="" name="c13"  type="text" value="${ caiwu.c13}" /></td>
            <td ><input id="" name="c13t"  type="text" value="${ caiwu.c13t}" /></td>
            
            <td >一年内到期的非流动负债</td>
            <td >	43</td>
            <td ><input id="" name="c43"  type="text" value="${ caiwu.c43}" /></td>
            <td ><input id="" name="c43t"  type="text" value="${ caiwu.c43t}" /></td>
           
          </tr>		
          <tr>
            <td > 一年内到期的非流动资产</td>
            <td >	14</td>
            <td ><input id="" name="c14"  type="text" value="${ caiwu.c14}" /></td>
            <td ><input id="" name="c14t"  type="text" value="${ caiwu.c14t}" /></td>
            
            <td >其他流动负债	</td>
            <td >	44</td>
            <td ><input id="" name="c44"  type="text" value="${ caiwu.c44}" /></td>
            <td ><input id="" name="c44t"  type="text" value="${ caiwu.c44t}" /></td>
           
          </tr>	
          <tr>
            <td >其他流动资产</td>
            <td >	15</td>
            <td ><input id="" name="c15"  type="text" value="${ caiwu.c15}" /></td>
            <td ><input id="" name="c15t"  type="text" value="${ caiwu.c15t}" /></td>
            
            <td >流动负债合计</td>
            <td >	45</td>
           <td ><input id="" name="c45"  type="text" value="${ caiwu.c45}" /></td>
            <td ><input id="" name="c45t"  type="text" value="${ caiwu.c45t}" /></td>
          </tr>	
          <tr>
            <td >流动资产合计</td>
            <td >16</td>
             <td ><input id="" name="c16"  type="text" value="${ caiwu.c16}" /></td>
            <td ><input id="" name="c16t"  type="text" value="${ caiwu.c16t}" /></td>
             
            
            <td >非流动负债：</td>
            <td >		</td>
            <td ></td>
            <td ></td>
           
          </tr>	
          <tr>
            <td > 非流动资产：</td>
            <td >	</td>
           <td ></td>
            <td ></td>
            
            <td >长期借款	</td>
            <td >	46	</td>
            <td ><input id="" name="c46"  type="text" value="${ caiwu.c46}" /></td>
            <td ><input id="" name="c46t"  type="text" value="${ caiwu.c46t}" /></td>
           
          </tr>	
          <tr>
            <td > 中长期贷款及垫款</td>
            <td >17</td>
            <td ><input id="" name="c17"  type="text" value="${ caiwu.c17}" /></td>
            <td ><input id="" name="c17t"  type="text" value="${ caiwu.c17t}" /></td>
            
            <td >	预计负债</td>
            <td >	47</td>
            <td ><input id="" name="c47"  type="text" value="${ caiwu.c47}" /></td>
            <td ><input id="" name="c47t"  type="text" value="${ caiwu.c47t}" /></td>
           
          </tr>	
          
          <tr>
            <td > 可供出售金融资产</td>
            <td >	18	</td>
            <td ><input id="" name="c18"  type="text" value="${ caiwu.c18}" /></td>
            <td ><input id="" name="c18t"  type="text" value="${ caiwu.c18t}" /></td>
            
            <td >递延所得税负债</td>
            <td >	48	</td>
            <td ><input id="" name="c48"  type="text" value="${ caiwu.c48}" /></td>
            <td ><input id="" name="c48t"  type="text" value="${ caiwu.c48t}" /></td>
           
          </tr>	
          <tr>
            <td > 固定资产</td>
            <td >	19	</td>
            <td ><input id="" name="c19"  type="text" value="${ caiwu.c19}" /></td>
            <td ><input id="" name="c19t"  type="text" value="${ caiwu.c19t}" /></td>
            
            <td >其他非流动负债	</td>
            <td >49</td>
            <td ><input id="" name="c49"  type="text" value="${ caiwu.c49}" /></td>
            <td ><input id="" name="c49t"  type="text" value="${ caiwu.c49t}" /></td>
           
          </tr>
          <tr>
            <td >减：累计折旧</td>
            <td >	20	</td>
            <td ><input id="" name="c20"  type="text" value="${ caiwu.c20}" /></td>
            <td ><input id="" name="c20t"  type="text" value="${ caiwu.c20t}" /></td>
            
            <td >非流动负债合计	</td>
            <td >50	</td>
            <td ><input id="" name="c50"  type="text" value="${ caiwu.c50}" /></td>
            <td ><input id="" name="c50t"  type="text" value="${ caiwu.c50t}" /></td>
           
          </tr>
          <tr>
            <td >  固定资产净值</td>
            <td >21</td>
            <td ><input id="" name="c21"  type="text" value="${ caiwu.c21}" /></td>
            <td ><input id="" name="c21t"  type="text" value="${ caiwu.c21t}" /></td>
            
            <td >	负债合计</td>
            <td >51	</td>
            <td ><input id="" name="c51"  type="text" value="${ caiwu.c51}" /></td>
            <td ><input id="" name="c51t"  type="text" value="${ caiwu.c51t}" /></td>
           
          </tr>
          <tr>
            <td >  在建工程</td>
            <td >	22</td>
            <td ><input id="" name="c22"  type="text" value="${ caiwu.c22}" /></td>
            <td ><input id="" name="c22t"  type="text" value="${ caiwu.c22t}" /></td>
            
            <td >	所有者权益：</td>
            <td >	</td>
            <td ></td>
            <td ></td>
           
          </tr>
           <tr>
            <td >固定资产清理</td>
            <td >	 23</td>
             <td ><input id="" name="c23"  type="text" value="${ caiwu.c23}" /></td>
            <td ><input id="" name="c23t"  type="text" value="${ caiwu.c23t}" /></td>
            
            <td >	 实收资本（或股本）</td>
            <td >	52</td>
            <td ><input id="" name="c52"  type="text" value="${ caiwu.c52}" /></td>
            <td ><input id="" name="c52t"  type="text" value="${ caiwu.c52t}" /></td>
           
          </tr>
           <tr>
            <td >  无形资产</td>
            <td >	24</td>
            <td ><input id="" name="c24"  type="text" value="${ caiwu.c24}" /></td>
            <td ><input id="" name="c24t"  type="text" value="${ caiwu.c24t}" /></td>
            
            <td >	 资本公积</td>
            <td >	53</td>
            <td ><input id="" name="c53"  type="text" value="${ caiwu.c53}" /></td>
            <td ><input id="" name="c53t"  type="text" value="${ caiwu.c53t}" /></td>
           
          </tr>
          
          <tr>
            <td > 商誉</td>
            <td >	25	</td>
            <td ><input id="" name="c25"  type="text" value="${ caiwu.c25}" /></td>
            <td ><input id="" name="c25t"  type="text" value="${ caiwu.c25t}" /></td>
            
            <td >减;库存股</td>
            <td >	54</td>
            <td ><input id="" name="c54"  type="text" value="${ caiwu.c54}" /></td>
            <td ><input id="" name="c54t"  type="text" value="${ caiwu.c54t}" /></td>
           
          </tr>
          
          <tr>
            <td > 长期待摊费用</td>
            <td >	26	</td>
            <td ><input id="" name="c26"  type="text" value="${ caiwu.c26}" /></td>
            <td ><input id="" name="c26t"  type="text" value="${ caiwu.c26t}" /></td>
            
            <td > 盈余公积</td>
            <td >	55</td>
            <td ><input id="" name="c55"  type="text" value="${ caiwu.c55}" /></td>
            <td ><input id="" name="c55t"  type="text" value="${ caiwu.c55t}" /></td>
           
          </tr>
           <tr>
            <td > 递延所得税资产</td>
            <td >	27	</td>
            <td ><input id="" name="c27"  type="text" value="${ caiwu.c27}" /></td>
            <td ><input id="" name="c27t"  type="text" value="${ caiwu.c27t}" /></td>
            
            <td > 一般风险准备</td>
            <td >	56</td>
            <td ><input id="" name="c56"  type="text" value="${ caiwu.c56}" /></td>
            <td ><input id="" name="c56t"  type="text" value="${ caiwu.c56t}" /></td>
           
          </tr>
           <tr>
            <td > 其他非流动资产</td>
            <td >	28</td>
            <td ><input id="" name="c28"  type="text" value="${ caiwu.c28}" /></td>
            <td ><input id="" name="c28t"  type="text" value="${ caiwu.c28t}" /></td>
            
            <td > 未分配利润</td>
            <td >	57</td>
            <td ><input id="" name="c57"  type="text" value="${ caiwu.c57}" /></td>
            <td ><input id="" name="c57t"  type="text" value="${ caiwu.c57t}" /></td>
           
          </tr>
           <tr>
            <td > 非流动资产合计</td>
            <td >	29	</td>
            <td ><input id="" name="c29"  type="text" value="${ caiwu.c29}" /></td>
            <td ><input id="" name="c29t"  type="text" value="${ caiwu.c29t}" /></td>
            
            <td >所有者权益合计</td>
            <td >	58</td>
            <td ><input id="" name="c58"  type="text" value="${ caiwu.c58}" /></td>
            <td ><input id="" name="c58t"  type="text" value="${ caiwu.c58t}" /></td>
           
          </tr>
           <tr>
            <td > 资产总计</td>
            <td >	30	</td>
            <td ><input id="" name="c30"  type="text" value="${ caiwu.c30}" /></td>
            <td ><input id="" name="c30t"  type="text" value="${ caiwu.c30t}" /></td>
            
            <td >负债及所有者权益合计</td>
            <td >	59</td>
            <td ><input id="" name="c59"  type="text" value="${ caiwu.c59}" /></td>
            <td ><input id="" name="c59t"  type="text" value="${ caiwu.c59t}" /></td>
           
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