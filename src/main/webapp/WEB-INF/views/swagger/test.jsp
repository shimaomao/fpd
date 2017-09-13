<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>B端API接口测试</title>
	<%@ include file="/WEB-INF/views/swagger/include/head.jsp" %>
	
	<link href="${ctxStatic}/swagger/swagger-test.css" rel="stylesheet">
  	<script src='${ctxStatic}/swagger/swagger-test.js' type="text/javascript" ></script>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar" data-twttr-rendered="true">
	<%@ include file="/WEB-INF/views/swagger/include/header.jsp" %>
	<header class="jumbotron subhead" id="overview">
         <div class="container">
             <h1>B端接口测试</h1>
             <p class="lead">基于Bootstrap+Swagger+SpringMvc环境</p>
         </div>
	</header>
	
	<div class="container">
	    <div class="row">
	        <div class="span12">
	        	<div class="tabbable">
	                <ul class="nav nav-tabs">
	                    <li class="active"><a href="#panel-787391" data-toggle="tab">标准测试</a></li>
	                    <li><a href="#panel-697548" data-toggle="tab">自定义测试</a></li>
	                </ul>
	                <div class="tab-content">
	                    <div class="tab-pane active" id="panel-787391">
	                       <div class="row">
	                            <!-- 标准接口 -->
					            <c:forEach var="entity" items="${entitys}" varStatus="idx">
					           	<c:set var="table" value="${entity['table']}"/>  
					           	<c:set var="className" value="${entity['className']}"/>  
					           	<c:set var="ClassName" value="${entity['ClassName']}"/>  
					           	<c:set var="functionName" value="${entity['functionName']}"/>  
						        <div class="span4 module">
						        	<div class="top-box">${ClassName} ${functionName}</div>
						        	<script type="text/javascript">
								   	  	$(function(){
								   	  		var ${className}Model = {
									  	   		Post:{
					  			   	  			  model:'${className}',
									  	   		  url:"${ctxApi}/api/${className}s"
									  	   	  	},
									  	   	  	Put:{
						  			   	  			model:'${className}',
										  	   	  	url:"${ctxApi}/api/${className}s/"
									  	   	    },
									  	   	   	Delete:{
						  			   	  			model:'${className}',
									  	   	  	  	url:"${ctxApi}/api/${className}s/"
									  	   		}
									  	   	 };
								   	  		$("#btn${ClassName}Post").click(function(){ apiTool.ajaxPost(${className}Model.Post);});
								   	  		$("#btn${ClassName}Put").click(function(){ apiTool.ajaxPut(${className}Model.Put, $("#${className}Id").val());});
								   	  		$("#btn${ClassName}Delete").click(function(){ apiTool.ajaxDelete(${className}Model.Delete, $("#${className}Id").val());});
								   	  	});
							    	</script>
	                                <div class="box-tom">
	                                    <div class="inbox">
	                                        <a id="btn${ClassName}Post" class="btna co1">post</a>
	                                        <a id="btn${ClassName}Put" class="btna co2">put</a>
	                                        <a id="btn${ClassName}Delete" class="btna co3">delete</a>
	                                        <hr/>
	                                        <div class="cont-box"><input class="ids" id="${className}Id" type="text" value="" placeholder="ID值" /></div>
	                                        <div class="cont-box"><textarea class="jsons" id="${className}Json" placeholder="新增Json值">${fns:toJson(jsons[idx.index].isApiInsert)}</textarea></div>
	                                    </div>
	                                </div>
						        </div>
						        </c:forEach>
	                        </div>
	                    </div>
	                    <div class="tab-pane" id="panel-697548">
	                        <div class="row">
								<%@ include file="/WEB-INF/views/swagger/include/test/wOrderTest.jsp" %>
								<%@ include file="/WEB-INF/views/swagger/include/test/roleUserTest.jsp" %>
                            </div>
	                    </div>
	                </div>
	            </div>
	    	</div>
	    </div>
	</div>
	 
	<%@ include file="/WEB-INF/views/swagger/include/footer.jsp" %>
</body>
</html>