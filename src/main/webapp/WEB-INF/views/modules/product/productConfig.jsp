<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品业务功能配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	    function toSubmit(){
	 	   var daiqian = getCheckValue("daiqian");
	 	   var daizhong = getCheckValue("daizhong");
	 	   var daihou = getCheckValue("daihou");
	 	   $("#qian").val(daiqian);
	 	   $("#zhong").val(daizhong);
	 	   $("#hou").val(daihou);
    	   document.forms[0].submit();
	    }
	    
	    function selectAll(){ 
	   	 	var sellall = getCheckValue("selAll");
	        var obj = document.getElementsByName("daizhong"); 
	        var daiqian = document.getElementsByName("daiqian");
	        var daihou = document.getElementsByName("daihou");
	        for(var i=0; i<obj.length; i++){	
		       	 if(sellall==1){
		       		 obj[i].checked=true; 
		       	 }else{
		       		 obj[i].checked=false; 
		       	 }
	         }	
	        for(var i=0; i<daiqian.length; i++){	  
		       	 if(sellall==1){
		       		 daiqian[i].checked=true; 
		       	 }else{
		       		 daiqian[i].checked=false; 
		       	 } 
	         }	
	        for(var i=0; i<daihou.length; i++){	  
		      	 if(sellall==1){
		      		 daihou[i].checked=true; 
		      	 }else{
		      		 daihou[i].checked=false; 
		      	 }
	         }	
	      } 
	</script>
	<style type="text/css">
		.content_block {
			width:100%;
			border-bottom:0px #999999 solid;
			margin-top:0px;
			margin-bottom:10px;
			padding-bottom:10px;
		}
		.content_block .ax_ico {
			float:left;
			margin-right: 10px;
		}
		.content_block .ax_title {
		    font-family: '微软雅黑 Regular','微软雅黑';
		    font-weight: 400;
		    font-style: normal;
		    font-size: 13px;
		    color: rgb(0, 0, 0);
		    text-align: center;
		    line-height: 20px;
			height:20px;
			float:left;
		}	
	</style>
</head>
<body>
	<div class="content_block">
		<div class="ax_ico"><img src="${ctxStatic}/images/pz.png" width="20px" height="20px"/></div>
		<div class="ax_title">产品业务流程配置-(产品名称：${tProduct.name})</div>
		<div style="clear:left;"></div>
	</div>
	<form:form id="inputForm" action="${ctx}/product/tProduct/depLoysecount" method="post">
		<input type="hidden" value="${tProduct.id}" name="id">
		<input type="hidden" name="qian" id="qian" >
		<input type="hidden"  name="zhong" id="zhong" >
		<input type="hidden"  name="hou" id="hou" >
		<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width:60%">
			<thead><tr><th>业务模块</th><th><input type="checkbox"  name="selAll" onclick="selectAll();" value="1" style="cursor: pointer;"/>业务功能</th></tr></thead>
			<tr>
				<td style="height: 100px;width: 100px;">
					贷前管理
				</td>
				<td>
					<div style="float: left;">
						<c:forEach items="${productbizList}" var="productbiz" varStatus="index">
						    <c:if test="${productbiz[8]=='1'}">
						       <input type="checkbox" name="daiqian" value="${productbiz[0]}"/>${productbiz[1]}<br>
						    </c:if>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td  style="height: 100px;">
					贷中管理
				</td>
				<td>
				<div style="float: left;">
						<c:forEach items="${productbizList}" var="productbiz">
						    <c:if test="${productbiz[8]=='2'}">
						       <input type="checkbox" name="daizhong" value="${productbiz[0]}"/>${productbiz[1]}<br>
						    </c:if>
					   </c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<td  style="height: 100px;">
					贷后管理
				</td>
				<td>
				<div style="float: left;">
						<c:forEach items="${productbizList}" var="productbiz">
						    <c:if test="${productbiz[8]=='3'}">
						       <input type="checkbox" name="daihou" value="${productbiz[0]}"/>${productbiz[1]}<br>
						    </c:if>
						</c:forEach>
					</div>
				</td>
			</tr>
		</table>
			
		<div class="form-actions">
			<shiro:hasPermission name="product:tProduct:edit"><input id="btnSubmit" class="btn btn-primary" onclick="toSubmit();"  value="下一步"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>