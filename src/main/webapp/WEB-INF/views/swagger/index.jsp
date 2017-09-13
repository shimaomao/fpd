<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<title>B端API接口说明</title>
	<%@ include file="/WEB-INF/views/swagger/include/head.jsp" %>
	<style type="text/css">.bs-docs-sidenav > li.active > a {background:none;} .nav-list > .active > a, .nav-list > .active > a:hover {color: #08c;}</style>
</head>
<body data-spy="scroll" data-target=".bs-docs-sidebar" data-twttr-rendered="true">
	<%@ include file="/WEB-INF/views/swagger/include/header.jsp" %>
	<header class="jumbotron subhead" id="overview">
         <div class="container">
             <h1>B端接口文档说明</h1>
             <p class="lead">基于Bootstrap+Swagger+SpringMvc环境</p>
         </div>
	</header>

	<div class="container">
        <div class="row"> </div>
        <div class="row">
			<%@ include file="/WEB-INF/views/swagger/include/doc/menu.jsp" %>
			
	        <div class="span9">
				<!-- 接口规范 -->
				<%@ include file="/WEB-INF/views/swagger/include/doc/apiBase.jsp" %>

				<!-- 标准接口 -->
	            <c:forEach var="entity" items="${entitys}" varStatus="idx">
	            	<c:set var="table" value="${entity['table']}"/>  
	            	<c:set var="className" value="${entity['className']}"/>  
	            	<c:set var="ClassName" value="${entity['ClassName']}"/>  
	            	<c:set var="functionName" value="${entity['functionName']}"/>  
	            	<c:set var="functionNameSimple" value="${entity['functionNameSimple']}"/>  
	            	
	            	<section id="id${table.className}">
			    	<div class="page-header">
			        	<h1>${functionName}</h1>（${ClassName}-${className}s）
			        </div>
			        <div>
			     		<ul>
			     			<li>
			     				<b>模型属性：<a href="${ctxApi }/api/${className}s"><span class="icon-info-sign"></span>${functionName} : api/${className}s </a></b>   
			     				<table class="table table-bordered table-striped">
									<thead><tr><th>属性</th><th>说明</th><th>数据类型</th><th>参数说明</th><th>必填</th><th>插入</th><th>查询</th></tr></thead>
									<tbody><c:forEach var="c" items="${table.columnList}"><c:if test="${(c.javaType eq 'String') || (c.javaType eq 'java.util.Date') || (c.javaType eq 'Integer') || (c.javaType eq 'Double') || (c.javaType eq 'Float') || (c.javaType eq 'Long')}">
										<tr <c:if test="${c.delFlag eq '1'}"> class="error" title="已删除的列，保存之后消失！" </c:if><c:if test="${c.delFlag ne '1'}"></c:if>>
											<td>${c.javaField}<c:if test="${c.isPk == 1}">(PK)</c:if></td>
											<td>${c.comments}</td>
											<td>${c.javaType}</td>
											<c:if test="${not empty c.dictType}">
											<td><a href="${ctxApi }/api/dicts?expand=true&fields=label,value&type=${c.dictType}"><span class="icon-info-sign"></span></a>：
												<c:forEach items="${fns:getDictList(c.dictType)}" var="item">
												${item.value}、${item.label};
												</c:forEach>
											</td>
											</c:if>
											<c:if test="${empty c.dictType}"><td></td></c:if>
											<td><c:if test="${c.isNull eq '0'}">Y</c:if><c:if test="${c.isNull ne '0'}">-</c:if></td>
											<td><c:if test="${c.isApiInsert eq '1'}">Y</c:if><c:if test="${c.isApiInsert ne '1'}">-</c:if></td>
											<td><c:if test="${c.isApiQuery eq '1'}">Y</c:if><c:if test="${c.isApiQuery ne '1'}">-</c:if></td>
	            						</tr></c:if></c:forEach>
									</tbody>
								</table> 
				            </li>
				            <li>
								<b>关联模型属性：</b>
								<table class="table table-bordered table-striped">
									<thead><tr><th>属性</th><th>说明</th><th>数据类型</th><th>必填</th><th>插入</th><th>查询</th></tr></thead>
									<tbody>
										<c:forEach var="c" items="${table.columnList}" varStatus="i">
											<c:if test="${(c.javaType ne 'String') && (c.javaType ne 'java.util.Date') && (c.javaType ne 'Integer') && (c.javaType ne 'Double') && (c.javaType ne 'Float') && (c.javaType ne 'Long')}">
											<tr <c:if test="${c.delFlag eq '1'}"> class="error" title="已删除的列，保存之后消失！" </c:if><c:if test="${c.delFlag ne '1'}"></c:if>>
												<td>${c.javaField}<c:if test="${c.isPk == 1}">(PK)</c:if></td>
												<td>${c.comments}</td>
												<td>${c.javaType}</td>
												<td><c:if test="${c.isNull eq '0'}">Y</c:if><c:if test="${c.isNull ne '0'}">-</c:if></td>
												<td><c:if test="${c.isApiInsert eq '1'}">Y</c:if><c:if test="${c.isApiInsert ne '1'}">-</c:if></td>
												<td><c:if test="${c.isApiQuery eq '1'}">Y</c:if><c:if test="${c.isApiQuery ne '1'}">-</c:if></td>
		            						</tr>
		            						</c:if>
										</c:forEach>
									</tbody>
								</table>
			       			</li>
			       			<li><b>模型接口：</b>
			       				<ul>
			       					<li>标准接口: 参阅 <code>API基本规范-使用规范</code>
			       						<ul>
					       					<li>
								            	<b>必填参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 50px;">${fns:toJson(jsons[idx.index].isNotNull)}</textarea>
								            </li>
								            <li>
								            	<b>Post参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 150px;">${fns:toJson(jsons[idx.index].isApiInsert)}</textarea>
								            </li>
								            <li>
								            	<b>默认响应参数：<a href="${ctxApi }/swagger/test" target="_blank"><span class="icon-info-sign"></span>测试</a></b>   
												<textarea placeholder="新增Json值" style="width:98%; height: 150px;">${fns:toJson(jsons[idx.index].isApiQuery)}</textarea>
								            </li>
								         </ul>
			       					</li>
			       					<li>关联接口: 参阅 <code>API基本规范-使用规范</code>(关联模块json结构请看相应模块标准规范)
			        					<ol>
			        						<c:forEach var="c" items="${table.columnList}" varStatus="i">
											<c:if test="${(c.javaType ne 'String') && (c.javaType ne 'java.util.Date') && (c.javaType ne 'Integer') && (c.javaType ne 'Double') && (c.javaType ne 'Float') && (c.javaType ne 'Long')}">
			        						<li>${c.javaField}<c:if test="${c.isPk == 1}">(PK)</c:if>:${c.javaType}</li> 
			        						</c:if>
			        						</c:forEach>
			        					</ol>
			        				</li>
			       					<li>定制接口:无</li>
			       				</ul>
			       			</li>
			       		</ul>
					</div>
				</section>
	            </c:forEach>
		
    
           		<!-- 定制接口 -->
				<%-- <%@ include file="/WEB-INF/views/swagger/include/doc/userDoc.jsp" %>
				<%@ include file="/WEB-INF/views/swagger/include/doc/roleDoc.jsp" %>
				<%@ include file="/WEB-INF/views/swagger/include/doc/officeDoc.jsp" %> 
				<%@ include file="/WEB-INF/views/swagger/include/doc/tProductDoc.jsp" %>
				<%@ include file="/WEB-INF/views/swagger/include/doc/dictDoc.jsp" %> 
				
				<%@ include file="/WEB-INF/views/swagger/include/doc/tLoanContractDoc.jsp" %>
				<%@ include file="/WEB-INF/views/swagger/include/doc/tCompanyDoc.jsp" %>
				<%@ include file="/WEB-INF/views/swagger/include/doc/tEmployeeDoc.jsp" %>

				<%@ include file="/WEB-INF/views/swagger/include/doc/tRepayPlanDoc.jsp" %>
				--%>
				<%@ include file="/WEB-INF/views/swagger/include/doc/wOrderDoc.jsp" %>
	        </div>
	    </div>
	</div>
	 
	<%@ include file="/WEB-INF/views/swagger/include/footer.jsp" %>
	<script src="${ctxStatic}/jquery-ui-bootstrap/assets/js/docs.js" type="text/javascript"></script>
</body>
</html>