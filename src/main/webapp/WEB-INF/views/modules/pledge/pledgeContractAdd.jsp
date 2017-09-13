<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			
			//初始化质押品信息
			doPledge();
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
	<form:form id="inputForm" modelAttribute="pledgeContract" method="post" class="form-horizontal">
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
		
		<div class="form-actions">
			<div class="text-center"><input type=button id="okButton_${nid}" value="确定" class="btn btn-primary"/>&nbsp;
			<input type=button id="closeButton_${nid}" value="关闭" class="btn"/></div>
			
		</div>
	</form:form>
	<script type="text/javascript">
	 $(function(){        	
     	$("#okButton_${nid}").click(function () {
     		var options = {
 					url : '${ctx}/pledge/pledgeContract/saveContr',
 					type : 'post',
 					dataType : 'json',//'text',
 					data : $("#inputForm").serializeArray(),
 					success : function(data, textStatus) {
 						if(data.params == "paramErr"){
 							alert(data.msg);
 						}else{
 							alert(data.msg);
 							$("#closeButton_${nid}").click();
 						}
 						//alert("status:"+textStatus+",params:"+data.params+",data.msg:"+data.msg);
 					}
 				};
 				$.ajax(options);
 				return false;
     	});
     	
     	//点关闭按钮
     	$("#closeButton_${nid}").click(function (){
 	     	formClose($(this));
     	});
     });
	</script>
</body>
</html>