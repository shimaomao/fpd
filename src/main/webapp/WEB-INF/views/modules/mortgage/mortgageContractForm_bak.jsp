<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>抵押物品信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			<c:if test="${isClose == 1 || param.isClose == 1}">
				showTip("${message}");
				top.$.jBox.close(true);
			</c:if>
			$("#collaContent").hide();
			
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
			
			$("#pledgeType").change(function(){
				var sval = $('#pledgeType').val();//对应值
				
				doPledge(sval);
			});
			
			var pleval = $('#pledgeType').val();//对应值
			//alert("pledgeType="+pledgeType);
			if(typeof(pleval) != "undefined" || pleval.length == 1){
				doPledge(pleval);
			};
			
			$("#btnCancel").click(function(){
				top.$.jBox.close(true);
			});
			/*
			$("#btnSubmit").click(function(){
				
				var options = {
							url : '${ctx}/mortgage/mortgageContract/save',
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
		
		function doPledge(sval){
			//var sval = $('#pledgeType').val();//对应值
			//var stext = $("#pledgeType").find("option:selected").text();//选取的内容
			//alert("选取的内容："+ stext +"（："+sval);//
			var loadhref;
			if(sval == 1){//商铺写字楼
				loadhref = "${ctx}/collateral/building/add?mortgageid=${mortgageContract.id}";//"${ctx}/collateral/building/form?id=${building.id}";
			}else if(sval == 2){//住宅
				loadhref = "${ctx}/collateral/house/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 3){//工业用
				loadhref = "${ctx}/collateral/gongLand/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 4){//商宅用地
				loadhref = "${ctx}/collateral/zhuLand/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 5){//公寓信息
				loadhref = "${ctx}/collateral/gongyu/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 6){//独栋别墅
				loadhref = "${ctx}/collateral/singleVilla/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 7){//住宅信息
				loadhref = "${ctx}/collateral/zhuZhai/add?mortgageid=${mortgageContract.id}";
			}else if(sval == 8){//联排别墅
				loadhref = "${ctx}/collateral/terraceVilla/add?mortgageid=${mortgageContract.id}";
			}else {
				//loadhref = "";
			}
			//alert(loadhref);
			
			loadPage( loadhref );
		};
		
		function loadPage(href){
			//alert("loadPage href:"+href);
			if(typeof(href) == "undefined" || href.length == 0){
				
			}else{
				$("#collaContent").empty();
				$("#collaContent").load(href);
				$("#collaContent").show();
			}
		};
	</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li ><a href="${ctx}/mortgage/mortgageContract/">抵押物品信息列表</a></li>
		<shiro:hasPermission name="mortgage:mortgageContract:edit"><li class="active"><a href="${ctx}/mortgage/mortgageContract/form">抵押物品信息${(not empty mortgageContract.id && fn:contains(mortgageContract.id, 'new_') == false ) ? '修改' : '添加'}</a></li></shiro:hasPermission>
	</ul>
       <%-- <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>
		               业务中心>我的业务>保质抵押>抵押>编辑
	   </div>
	</div>
	<br>
	<br> --%>
	<form:form id="inputForm" modelAttribute="mortgageContract"  action="${ctx}/mortgage/mortgageContract/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="businessId"/>
		<form:hidden path="businessTable"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">存放地点：</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量及单位：</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">评估价值(元)：</label>
			<div class="controls">
				<form:input path="worth" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联业务表的名称：</label>
			<div class="controls">
				<form:input path="businessTable" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">抵押物类型：</label>
			<div class="controls">
				<form:select path="pledgeType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('collateral_mortgage')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<label class="control-label">抵押性质：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">关联业务的id：</label>
			<div class="controls">
				<form:input path="businessId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div> --%>
		<form:hidden path="remarks"/>
		<%-- <div class="control-group">
			<label class="control-label">租户ID：</label>
			<div class="controls">
				<form:input path="organId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div> --%>
		
		<div id="collaContent">
			
		</div>
		
		<div class="form-actions" style="display: none;">
			<shiro:hasPermission name="mortgage:mortgageContract:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="修 改"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回"/>
		</div>
	</form:form>
</body>
</html>