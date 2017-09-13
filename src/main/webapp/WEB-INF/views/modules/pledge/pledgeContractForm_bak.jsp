<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<c:set var="nid" value="pledgeform"/>
<head>
	<title>质押信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1 || param.isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
			
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
			
			//初始化质押品信息
			doPledge();
			
			$("#btnCancel").click(function(){
				top.$.jBox.close(true);
			});
			/*
			$("#btnSubmit").click(function(){
				$('#flag').val('yes');
				 var options = {
							url : '${ctx}/pledge/pledgeContract/save',
							type : 'post',
							dataType : 'json',//'text',
							data : $("#inputForm").serializeArray(),
							success : function(data, textStatus) {
								alert(data.msg);
								top.$.jBox.close(true);
							}
						};
						$.ajax(options);
						return false;
			});
			*/
		});
		
		//质押品信息
		function doPledge(){
			var sval = $('#${nid}pledgeType').val();//对应值
			if(typeof(sval) == "undefined" || sval.length == 0){
				return;
			}
			var loadhref;
			if(sval == 1){//车辆
				loadhref = "${ctx}/collateral/car/add?dizhiContractId=${pledgeContract.id}";
			}else if(sval == 2){//存货
				loadhref = "${ctx}/collateral/cunhuo/add?dizhiContractId=${pledgeContract.id}";
			}else if(sval == 3){//股权
				loadhref = "${ctx}/collateral/guquan/add?dizhiContractId=${pledgeContract.id}";
			}else if(sval == 4){//机器
				loadhref = "${ctx}/collateral/machine/add?dizhiContractId=${pledgeContract.id}";
			}else if(sval == 5){//无形权力
				loadhref = "${ctx}/collateral/quanli/add?dizhiContractId=${pledgeContract.id}";
			}else if(sval == 6){//用地
				loadhref = "${ctx}/collateral/yongLand/add?dizhiContractId=${pledgeContract.id}";
			}else {
			}
			
			loadPage( loadhref );
		};
		
		function loadPage(href){
			if(typeof(href) == "undefined" || href.length == 0){
				
			}else{
				$("#${nid}pledgeinfo").empty();
				$("#${nid}pledgeinfo").load(href);
				$("#${nid}pledgeinfo").show();
			}
		};
		
		
	</script>
</head>
<body>
       <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>保质抵押>质押>编辑
	   </div>
	</div>
	<br>
	<br>
	<form:form id="inputForm" modelAttribute="pledgeContract" action="${ctx}/pledge/pledgeContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="businessId"/>
		<form:hidden path="businessTable"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">地址：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">质押名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抵押物类型：</label>
			<div class="controls">
				<form:select id="${nid}pledgeType" path="pledgeType" class="input-xlarge " onchange="doPledge()">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('collateral_pledge')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">抵押物的状态：</label>
			<div class="controls">
				<form:select path="struts" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('mortgage_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>			
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价值：</label>
			<div class="controls">
				<form:input path="worth" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>

		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div> --%>
		<form:hidden path="remarks"/>
		<div id="${nid}pledgeinfo" class="control-group">
		
		</div>
		
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="pledge:pledgeContract:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回""/>
		</div>
	</form:form>
</body>
</html>