<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押物品信息管理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				var data = ${fns:toJson(data)};
				var form = $("#target");
				var btn = $("#submitViewForm");
				api.form.init(form, eval(data));
				form.attr("method", "post");
				var divWrap = form.find("#divWrap");
				if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
					form.append("<div id=\"divWrap\"></div>");
					divWrap = form.find("#divWrap");
				}
				//divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				
				 if(data.id==''||typeof(data.id)=='undefined'){
						divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }else{
						divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }
				
				
				form.attr("target", "mainFrame");
				form.attr("action", ctx+"${action}");
				
				//添加关联业务的标签
				var $business = $('<div class="control-group"><label class="control-label" for="remarks">关联业务</label></div>');
				var $select = $('<select id="businessId" name="businessId" class="input-xlarge"></select>');
				$.post("${ctx}/contract/tLoanContract/getLoanContracts",{id : data.businessId, loanType : 2, status : <%=Cons.LoanContractStatus.TO_REVIEW%>},
					function(list){
						if(list){
							$select.append('<option value=""></option>')
							for(var i in list) {
								if(data.businessId==list[i].id){
									$select.append('<option value="'+list[i].id+'" selected="selected">'+list[i].contractNumber+'</option>');
								}else{
									$select.append('<option value="'+list[i].id+'">'+list[i].contractNumber+'</option>');
								}
								
							}
						}
					}
				);
				//if(data.businessId) //回显关联的业务
				//$select.val(data.businessId);
				$business.append($('<div class="controls"></div>').append($select)).append($('<input type="hidden" name="businessTable" value="t_loan_contract">'));
				form.find("fieldset").append($business);
				
				form.validate({
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
				$(btn).click(function(){
					form.submit();
				});
				
				
				$("#pledgeType").val(data.pledgeType);
				
				$("#pledgeType").change(function(){
					console.info("change");
					var sval = $('#pledgeType').val();//对应值
					if(typeof(sval) == "undefined" || sval.length == 0){
						return;
					}
					var loadhref;
		
					if(sval == 1){//商铺写字楼
						loadhref = "${ctx}/collateral/building/add?mortgageid=${data.id}";//"${ctx}/collateral/building/form?id=${building.id}";
					}else if(sval == 2){//住宅
						loadhref = "${ctx}/collateral/house/add?mortgageid=${data.id}";
					}else if(sval == 3){//工业用
						loadhref = "${ctx}/collateral/gongLand/add?mortgageid=${data.id}";
					}else if(sval == 4){//商宅用地
						loadhref = "${ctx}/collateral/zhuLand/add?mortgageid=${data.id}";
					}else if(sval == 5){//公寓信息
						loadhref = "${ctx}/collateral/gongyu/add?mortgageid=${data.id}";
					}else if(sval == 6){//独栋别墅
						loadhref = "${ctx}/collateral/singleVilla/add?mortgageid=${data.id}";
					}else if(sval == 7){//住宅信息
						loadhref = "${ctx}/collateral/zhuZhai/add?mortgageid=${data.id}";
					}else if(sval == 8){//联排别墅
						loadhref = "${ctx}/collateral/terraceVilla/add?mortgageid=${data.id}";
					}else if(sval == 9){//车辆
						loadhref = "${ctx}/collateral/car/add?dizhiContractId=${data.id}";
					}else if(sval == 10){//机器设备
						loadhref = "${ctx}/collateral/machine/add?dizhiContractId=${data.id}";
					}else if(sval == 11){//林权
						loadhref = "${ctx}/collateral/quanli/add?dizhiContractId=${data.id}";
					}else if(sval == 12){//农业用地
						loadhref = "${ctx}/collateral/gongLand/add?mortgageid=${data.id}";
					}else {
					}
					console.log(loadhref);
					if(typeof(loadhref) == "undefined" || loadhref.length == 0){
						
					}else{
						
						var $loanentity = $('<div id="collaContent" class="form-horizontal"></div>');

						form.find("fieldset").append($loanentity);
						
						$("#collaContent").empty();
						$("#collaContent").load(loadhref);
						$("#collaContent").show();
					}
				});
				
				$("#pledgeType").change();  
			});
		});
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/mortgage/mortgageContract/">抵押物品详细</a></li>
	</ul>
	<sys:message content="${message}"/>
	 ${dfFormTpl.parsehtml }
	<div class="container">
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
			      <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
				</div>
			</div>
		</div>
	</div>
</body>
</html>