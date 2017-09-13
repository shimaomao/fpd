<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
    <script type="text/javascript" src="${ctxStatic}/column.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			show_com();
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

			/**
		 * 动态增加属性
		 * @param tableName表称
		*/
		function addDyColumn1(tableName){
		   //显示层
		   var chineseName = $("#chineseName");
		   if(chineseName.val()==""){
		       $("#message").html("<font color='red'>请输入属性名称!</font>");
		       return;
		   }
		   var flag = confirm("请确定是否要增加属性: "+chineseName.val());
			if(flag){
				$.ajax({
				url : "${ctx}/product/tProduct/addColumn",
				type : 'POST',
				dataType : 'html',
				data: {"tableName":tableName,"chineseName":chineseName.val()},  
				timeout : 10000,
				error : function() {
				},
				success : function(content) {
					if(content=="exist"){
					  $("#message").html("<font color='red'>已经存在相同的属性名！</font>");
					  return;
					}else if(content=="error"){
					   $("#message").html("<font color='red'>添加错误！</font>");
					   return;
					}else{debugger;
					   $("#message").html("<font color='green'>添加成功！</font>");
					   location.reload() ;
					  // $("#message").html("<font color='green'>添加成功！</font>");
					   //document.getElementById("extendColumn-Div").innerHTML = "扩展属性"+content ;
					   //$("#chineseName").val("") ;
					}
				 }
			  });
			}
		}
			
			function show_com(type){
				if(type==1){
					var releasesObje = $("#releasesObje").val();
					if(releasesObje==2){
						$("#comsales_span").show();
					}else{
						$("#comsales_span").hide();
					}
				}else if(type==2){
					var releasesObje =getCheckValue("advanceRepay");
					if(releasesObje==1){
						$("#showtr").show();
					}else{
						$("#showtr").hide();
					}
				}
				
			}
			
	</script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li><a href="${ctx}/product/tProduct/">产品管理列表</a></li> --%>
<%-- 		<li class="active"><a href="${ctx}/product/tProduct/form?id=${tProduct.id}">产品管理<shiro:hasPermission name="product:tProduct:edit">${not empty tProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:tProduct:edit">查看</shiro:lacksPermission></a></li> --%>
<!-- 	</ul> -->
      <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
<%-- 		 <%=Cons.LocationUrl.URL.replace("-",">")%> --%>
		               产品中心>产品配置>标准产品
	   </div>
	</div>
	<br/>
	<br/>
	<form:form id="inputForm" modelAttribute="tProduct" action="${ctx}/product/tProduct/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table-form">
			
		    <tr>
				<td class="tit">产品类型：</td>
				<td>
				    <form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</td>
				<td class="tit">产品名称：</td>
				<td>
				    <form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>
		   <tr>
				<td class="tit">发行对象：</td>
				<td>
				    <form:select path="releasesObje" class="input-xlarge" id="releasesObje" onchange="show_com(1);">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_releases')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				   </form:select>
				   <span id="comsales_span" style="display: none">最低销售额<form:input path="comSales" id="comsales" htmlEscape="false" maxlength="255"  cssStyle="width:100px;"/></span>
				</td>
				<td class="tit">发行地区：</td>
				<td>
				    <sys:treeselect id="area" name="area.id" value="${tProduct.area.id}" labelName="area.name" labelValue="${tProduct.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				    
				</td>
			</tr>
	     
	       <tr>
				<td class="tit">发行渠道：</td>
				<td>
				   	<form:select path="releasesWay" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				   </form:select>
				</td>
				<td class="tit">额度（元）：</td>
				<td>
				      上限：<form:input path="amountMax" htmlEscape="false" cssStyle="width:130px;"/>
				       下限：<form:input path="amountMin" htmlEscape="false" cssStyle="width:130px;"/>
				</td>
			</tr>
		      <tr>
				<td class="tit">产品介绍：</td>
				<td>
				   	 <form:input path="detail" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
				<td class="tit">产品期限：</td>
				<td>
				    <input name="period" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tProduct.period}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
			</tr>
	
              <tr>
				<td class="tit">放款方式：</td>
				<td>
				   <form:select path="loanWay" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('loan_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td class="tit">还款方式：</td>
				<td>
				    <form:select path="payType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				</td>
			</tr>
			 <tr>
				<td class="tit">费率优惠率：</td>
				<td>
				     <form:input path="rateDiscount" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>%
				</td>
				<td class="tit">允许利息减免：</td>
				<td>
				    <form:input path="iflixiRedu" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>%
				</td>
			</tr>
			
			 <tr>
				<td class="tit">宽限期：</td>
				<td>
					<form:input path="gracePeriod" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>天
				</td>
				<td class="tit">宽限期罚息：</td>
				<td>
				    <form:input path="graceFaxi" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>%
				</td>
			</tr>
			
			 <tr>
				<td class="tit">逾期罚息：</td>
				<td>
				   <form:input path="yuqiFaxi" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>%
				</td>
				<td class="tit">逾期罚费：</td>
				<td>
				    <form:input path="yuqiFee" htmlEscape="false" maxlength="255" class="input-xlarge " cssStyle="width:150px;"/>%
				</td>
			</tr>
			 <tr>
				<td class="tit">贷款方式：</td>
				<td>
				   <form:checkboxes path="loanTypeList" items="${fns:getDictList('loan_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onchange="loanTypeChange()"/>
				</td>
				<td class="tit">贷款方式二级：</td>
				<td>
				    <form:select path="loanType2" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('laon_type2')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				   </form:select>
				</td>
			</tr>
			 <tr>
				<td class="tit">抵押率\质押率：</td>
				<td>
				   <form:input path="diyalv" htmlEscape="false" maxlength="255" cssStyle="width:100px;" class="input-xlarge "/>%\
				   <form:input path="zhiyalv" htmlEscape="false" maxlength="255" cssStyle="width:100px;" class="input-xlarge "/>%
				</td>
				<td class="tit">授信方式：</td>
				<td>
				     <form:select path="creditWay" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('credit_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
			</tr>
			 <tr>
				<td class="tit">授信期限：</td>
				<td colspan="3">
				   <form:input path="creditPeriod" htmlEscape="false" maxlength="255" class="input-xlarge "/>
				</td>
			</tr>
			  <tr>
				<td class="tit">产品定价：</td>
				<td colspan="3">
				    <table  style="width: 60%">
				           <tr>
								<td class="tit">计息规则(设定一年):</td>
								<td colspan="2"><form:input  cssStyle="width:100px;" path="yearDays" htmlEscape="false" maxlength="255" />天</td>
			               </tr>
			                <tr>
								<td class="tit">年华利率:</td>
								<td colspan="2"><form:input  cssStyle="width:150px;margin-top: 6px;" path="rate" htmlEscape="false" class="input-xlarge "/>%</td>
			               </tr>
			                <tr>
								<td class="tit">前期服务费:</td>
								<td><form:input  cssStyle="width:150px;margin-top: 6px;" path="serverFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>%</td>
								<td>是否退费:<form:radiobuttons path="serverRefund" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"/></td>
			               </tr>
			                <tr>
								<td class="tit">担保费:</td>
								<td><form:input  cssStyle="width:150px;margin-top: 6px;" path="guaranteeFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>%</td>
								<td>是否退费:<form:radiobuttons path="guaranteeRefund" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"/></td>
			               </tr>
			                <tr>
								<td class="tit">管理费:</td>
								<td><form:input  cssStyle="width:150px;margin-top: 6px;" path="mangeFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>%</td>
								<td>是否退费:<form:radiobuttons path="mangeRefund" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"/></td>
			               </tr>
			                <tr>
								<td class="tit">可提前还款:</td>
								<td colspan="2">
								    <form:radiobuttons  cssStyle="height:40px;" path="advanceRepay" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" onclick="show_com(2);"/>
								    <span id="showtr" style="display: none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;违约比例:<form:input cssStyle="width:150px;margin-top: 6px;" path="breakFee" id="breakFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>%</span>
								 </td>
			               </tr>
			                <tr>
								<td class="tit">滞纳金:</td>
								<td colspan="2"><form:input cssStyle="width:150px;margin-top: 6px;" path="lateFee" htmlEscape="false" maxlength="255" class="input-xlarge "/>%</td>
			               </tr>
				    </table>
				</td>
			</tr>
			  <tr>
				<td class="tit">风险措施说明：</td>
				<td colspan="3">
				    <form:textarea path="riskDetail" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</td>
			</tr>
			 <tr>
				<td class="tit">备注：</td>
				<td colspan="3">
				   <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
				</td>
			</tr>
		
		</table>
		
		<c:if test="${productbizList!=null}">
			<table class="table-form">
		            <tr style="line-height: 30px;">
						<td colspan="2" align="center">业务配置</td>
	               </tr>
	                <tr style="line-height: 30px;">
						<td class="tit" width="10%">贷前管理:</td>
						<td>
			               <c:forEach items="${productbizList}" var="productbiz" varStatus="index">
							    <c:if test="${productbiz[8]=='1'}">
										${productbiz[1]}&nbsp;&nbsp;
							    </c:if>
							</c:forEach>
                        </td>
	               </tr>

					 <tr style="line-height: 30px;">
						<td class="tit">贷中管理:</td>
						<td>
							 <c:forEach items="${productbizList}" var="productbiz" varStatus="index">
							    <c:if test="${productbiz[8]=='2'}">
										${productbiz[1]}&nbsp;&nbsp;
							    </c:if>
							 </c:forEach>
                        </td>
	               </tr>
					
					 <tr style="line-height: 30px;">
						<td class="tit">贷后管理:</td>
						<td>
							<c:forEach items="${productbizList}" var="productbiz" varStatus="index">
							    <c:if test="${productbiz[8]=='3'}">
										${productbiz[1]}&nbsp;&nbsp;
							    </c:if>
							</c:forEach>
					</td>
	               </tr>
	               
					 <tr style="line-height: 30px;">
						<td class="tit">风控配置:</td>
						<td >
							<c:forEach items="${twindentyList}" var="wind" varStatus="index">
								${wind.name}&nbsp;&nbsp;
							</c:forEach>
					</td>
	               </tr>
			 </table>
			 
		  </c:if>

      <!-- 客户自定义添加字段界面start -------------------->
	   <c:if test="${!empty extendColumns &&  fn:length(extendColumns)>0}">
	         <div id="extendColumn-Div" style="display: inline;">
	              <font color="green">扩展属性</font>
                   <c:forEach items="${extendColumns}" var="extendcolumn">	              
	            		<div class="control-group">
							<label class="control-label">${extendcolumn.chinese_name}：</label>
							<div class="controls">
								<input type='text' name='${extendcolumn.form_name}' class='input_text' value="${extendcolumn.form_value}"/>
							</div>
						</div>
	              </c:forEach>
	        </div>
	   </c:if>
	     ${content}


		 <!-- 动态添加字段(属性)界面层start-------------------- -->
       <div class="control-group">
             <div id="msgDiv" align="center" style="color: red">${msg}</div>
             <div  id="addColumnDiv"  style="display: none;margin-left: 113px;" >
				字段名称：
				<input type="text" name="extendColumn.chineseName" id="chineseName" class="input_text" onfocus="cleanDivMsg('message');cleanDivMsg('msgDiv');"/>
				<font color="red">*</font>
				<a href="javascript:void();" class="add" onclick="addDyColumn1('t_product');">增加</a>
				<a href="javascript:void();" class="add" onclick="showDiv('addColumnDiv','none');">取消</a>
				<div id="message" style="display: inline;"></div>
             </div>
             
             <!-- -----删除扩展字段 start------------------>
             <div  id="dropColumnDiv" style="display: none">
				字段名称：
				<div id="toDropColumnDiv" style="display: inline;"></div>
                <a href="javascript:void();" id="checkAll" class="add" onclick="checkAll('toDropColumn')">全选</a>
				<a href="javascript:void();" class="add" onclick="dropColumn('t_employee');">删除</a>
				<a href="javascript:void();" class="add" onclick="showDiv('dropColumnDiv','none');">取消</a>
				<div id="message-Delete" style="display: inline;"></div>
             </div>
             <!-- -----删除扩展字段 end---------------------->
       </div>
       <!-- 动态添加字段(属性)界面层end-------------------- -->

		<div class="form-actions">
			<a class="btn btn-primary" onclick="showDiv('addColumnDiv','display');showDiv('msgDiv','none');showDiv('dropColumnDiv','none')">增加属性</a>
			<shiro:hasPermission name="product:tProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>