<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>理财产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	    include('ckeditor_lib','${ctxStatic}/ckeditor/',['ckeditor.js']);
		$(document).ready(function() {
			console.info('ready信息');
			//$("#name").focus();
			var ifRansfer = "${tFinancialProduct.ifRansfer}"
				//console.info("测试ifRansfer："+ifRansfer);
			if(ifRansfer == "false" || ifRansfer == "0"){
				//console.info("测试111111");
				$(".conDisabled").hide();
				$("#transferLimit").attr("disabled", true);
				$("#rateDiscount").attr("disabled", true);
			}
			
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
			
			
			$("#btnSubmit").click(function(){
				var amount = $("input[name='amount']").val();
				if(amount==0){
					alert("请先选择债权信息（点击《资产包管理》选择债权信息）！");
					return false;
				}
			});
			
			$("input[name=ifRansfer]").click(function(){
				//console.info("测试1："+$("input[name=ifRansfer]:checked").val());
				var chooseValue = $("input[name=ifRansfer]:checked").val();
				if(chooseValue=="1"){
					$(".conDisabled").show();
					//$("#transferLimit").show();
					//$("#rateDiscount").show();
					$("#transferLimit").attr("disabled", false);
					$("#rateDiscount").attr("disabled", false);
				}else{
					$(".conDisabled").hide();
					//$("#transferLimit").hide();
					//$("#rateDiscount").hide();
					$("#transferLimit").attr("disabled", true);
					$("#rateDiscount").attr("disabled", true);
				}
			});
			//$(":radio[name='ifRansfer']").click(function(){
			//	console.info("测试2："+$("input[name=ifRansfer]:checked").val());
		    //});
		});
		 
		function rtchange(){
			    var repayType = $("#repayType").val();
				var danwei =$("input[name='danwei']:checked").val();
				if(danwei=='3'){//日
					if(repayType=='1' || repayType== '2' || repayType=='3'){
						$("#repayType").val("");
						alert("等额本息,等额本金,等本等息不允许按日还款");
						return false;
					}
				}
		 }
		
		//添加附件图片
		function updateImg(){
			var financialProductId = $("#id").val();
			var loancontractIds = $("#loancontractIds").val();
			top.$.jBox.open(
					"iframe:${ctx}/tfinancialproduct/tFinancialProduct/editImgList?id="+financialProductId+"&loancontractIds="+loancontractIds, "附件材料处理",850,385,
					{
						buttons : {
							"同步文件" : "return",
						},
						bottomText : "",
						submit : function(v, h, f) {
							var ifrWin = h.find("iframe")[0].contentWindow;
							if (v == "return") {
								//console.info("测试图片提交后返回内容2");
								//获取图片数量和马赛克处理数量
								 $.ajax({
							         	type: "POST",
							         	//url: "${ctx}/tfinancialproduct/tFinancialProduct/getFilesCount",
							         	url: "${ctx}/files/tContractFiles/synchroFiles",
							         	data: {taskId:financialProductId},
							         	dataType: "json",
							         	success: function(data){//{"totle":0,"dealed":0}
							         		//console.info("data="+data);
							         		//console.info("data.totle="+data.totle);
							         		//console.info("data.dealed="+data.dealed);
							         		var zmTotal = data.totle;
							         		var zmDeal = data.dealed;
							         		//console.info("11 zmTotal="+zmTotal);
							         		//console.info("12 zmDeal="+zmDeal);
							         		$("#zmTotal").html(zmTotal);
							         		$("#zmDeal").html(zmDeal);
							         		
							         		$("#materialTotal").val(zmTotal);
							         		$("#materialDeal").val(zmDeal);
							         	}
							      });
							}
						},
						loaded : function(h) {
							$(".jbox-content", top.document).css("overflow-y","hidden");
						},
						closed : function(){}
					});
		};
		
		function selectLoan() {
			    
			    var loancontractIds = $("#loancontractIds").val();
			    var financialProductId = $("#id").val();
				top.$.jBox.open(
						"iframe:${ctx}/tfinancialproduct/tFinancialProduct/getLoanRecord?loancontractIds="+loancontractIds, "挑选债权信息",1000,600,
						{
							buttons : {
								"确定" : "return",
							},
							bottomText : "",
							submit : function(v, h, f) {
								var ifrWin = h.find("iframe")[0].contentWindow;
								if (v == "return") {
// 									alert(ifrWin.$("#loancontractIds").val());
									 $("#loancontractIds").val(ifrWin.$("#loancontractIds").val());
									 $.ajax({
								         	type: "POST",
								         	url: "${ctx}/tfinancialproduct/tFinancialProduct/getContractAmount",
								         	data: {id:ifrWin.$("#loancontractIds").val(),financialProductId:$("#id").val()},
								         	dataType: "json",
								         	success: function(data){
								         		$("#amount").val(data);
								         	}
								       });
								}
							},
							loaded : function(h) {
								$(".jbox-content", top.document).css("overflow-y","hidden");
							},
							closed : function(){}
						});
		};
		

		
		
		function  checkAmount(){
			
			var buyAmountMin = $("#buyAmountMin").val();//起投金额
			var buyAmountMax = $("#buyAmountMax").val();//最高购买金额
			var amount       = $("#amount").val();//产品融资金额
			
		    if(amount==''){
		    	alert("请先选择资产包");
		    	$("#buyAmountMin").val("");
		    	 $("#buyAmountMax").val("");
		    	return false;
		    }
			
		    if(buyAmountMax==''){
		    	alert("请先填写最高购买金额");
		    	return false;
		    }
			
		    if(parseInt(buyAmountMax)>parseInt(amount)){
   				alert("最高购买金额不能大于产品融资金额");
   				 $("#buyAmountMax").val("");
		    	return false;
   			}
			
           	if(parseInt(buyAmountMin)>=parseInt(buyAmountMax)){
   				alert("起投金额不能大于最高购买金额");
   				$("#buyAmountMin").val("")
		    	return false;
   			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/tfinancialproduct/tFinancialProduct/">理财产品列表</a></li>
		<li class="active"><a href="${ctx}/tfinancialproduct/tFinancialProduct/form?id=${tFinancialProduct.id}">理财产品<shiro:hasPermission name="tfinancialproduct:tFinancialProduct:edit">${not empty tFinancialProduct.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="tfinancialproduct:tFinancialProduct:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tFinancialProduct" action="${ctx}/tfinancialproduct/tFinancialProduct/save" method="post" class="form-horizontal" >
<!-- 		<input id="loancontractIds" name="loancontractIds" type="text"/> -->
		<form:hidden path="id" id="id"/>
		<form:hidden path="loancontractIds" id="loancontractIds"/>
		
		<form:hidden path="shiConversion"/>
		<form:hidden path="progress"/>
		<form:hidden path="kemoney"/>
		<form:hidden path="zrmoney"/>
		<form:hidden path="rzstatus" />
		<form:hidden path="status"/>
		<form:hidden path="materialTotal" id="materialTotal"/>
		<form:hidden path="materialDeal" id="materialDeal"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><h3>产品基本信息</h3></label>
			<div class="controls" >
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品分类：</label>
			<div class="controls">
				<form:select path="productType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('RzproductType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称：</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="50" class="input-xlarge required"/>(产品标题名称长度至少3个字符，最长20个字符)
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">融资期限：</label>
			<div class="controls">
				<form:input path="limitTime" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">期限单位：</label>
			<div class="controls">
				<form:radiobuttons path="danwei" items="${fns:getDictList('financial_period_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			    <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年化率：</label>
			<div class="controls">
				<form:input path="yearConversion" htmlEscape="false" class="input-xlarge required number"/>%
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买截止日期：</label>
			<div class="controls">
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${tFinancialProduct.endDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品说明：</label>
			<div class="controls">
				<form:textarea path="productDetail" htmlEscape="false" rows="4" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><h3>债权信息</h3></label>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><h5 onclick="selectLoan()">资产包管理</h5></label>
			<div class="controls" style="padding-top: 8px;">(点击《资产包管理》挑选债权信息)
			   <span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">融资金额：</label>
			<div class="controls">
				<form:input path="amount" id="amount" htmlEscape="false" class="input-xlarge " readonly="true"/>元（债权资产总和，对应的债权信息会展示给投资人）
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证明材料：</label>
			<div class="controls" style="padding-top: 8px;">
				共<font id="zmTotal" color="red">${tFinancialProduct.materialTotal}</font>张，已处理<font id="zmDeal" color="red">${tFinancialProduct.materialDeal}</font>张&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><a href="javascript:void(0)" onclick="updateImg()">材料处理</a></strong>
				<!-- <div id="zmTotal">5</div>张，已处理<div id="zmDeal">3</div>张&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><a href="javascript:void(0)" onclick="updateImg()">材料处理</a></strong> -->
			</div>
		</div>
		<br>
		<div class="control-group">
			<label class="control-label"><h3>限制条件</h3></label>
			<div class="controls">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发行对象：</label>
			<div class="controls">
				<%-- <form:select path="releasesObje" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
                    <form:checkboxes path="ReleasesObjeList" items="${fns:getDictList('customer_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最高购买金额：</label>
			<div class="controls">
				<form:input path="buyAmountMax" id="buyAmountMax" htmlEscape="false" class="input-xlarge required number" onchange="checkAmount();"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">起投金额：</label>
			<div class="controls">
				<form:input path="buyAmountMin" id="buyAmountMin" htmlEscape="false" class="input-xlarge required number" onchange="checkAmount();"/>元
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可转让：</label>
			<div class="controls">
				<form:radiobuttons path="ifRansfer" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(投资人在投资期限内是否允许将债权（收益权）转让给平台的其他用户。)
			</div>
		</div>
		<div class="control-group conDisabled">
			<label class="control-label">转让限定期：</label>
			<div class="controls">
				<form:input path="transferLimit" htmlEscape="false" maxlength="10" class="input-xlarge required digits"/>日(从开始起息到转让限定期结束之前，不允许转让。)
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group conDisabled">
			<label class="control-label">转让收费费率：</label>
			<div class="controls">
				<form:input path="rateDiscount" htmlEscape="false" maxlength="10" class="input-xlarge required number"/>%(手续费 = 转让的本金 × 转让手续费费率)
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
	
		<div class="control-group">
			<label class="control-label">回款方式：</label>
			<div class="controls">
				<form:select path="repayType" class="input-xlarge required" onchange="rtchange()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_paytype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">逾期罚息利率(天)：</label>
			<div class="controls">
				<form:input path="yuqiFree" htmlEscape="false" maxlength="10" class="input-xlarge required" />%(违约费用（未还本金*预设违约利率*时长），时长按天算)
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group" style="display:none">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks"  htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="tfinancialproduct:tFinancialProduct:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>