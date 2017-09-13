<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
		$(document).ready(function() {
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
			}
			
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
	<form:form id="inputForm" modelAttribute="mortgageContract"  method="post" class="form-horizontal">
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
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div> --%>
		<form:hidden path="remarks"/>
		<div id="collaContent">
			
		</div>
    	<div>
			<div class="text-center"><input type=button id="okButton_${nid}" value="确定" class="btn btn-primary"/>&nbsp;
			<input type=button id="closeButton_${nid}" value="关闭" class="btn"/></div>
					
        </div>
	</form:form>
    <script type="text/javascript">
        $(function(){        	
        	$("#okButton_${nid}").click(function () {
        		var options = {
    					url : '${ctx}/mortgage/mortgageContract/saveContr',
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
