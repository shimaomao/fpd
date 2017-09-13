<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

			<div class="span3 bs-docs-sidebar">
	            <ul class="nav nav-list bs-docs-sidenav">
					<!-- 接口规范 -->
	                <li><a href="#idapiRule"><i class="icon-chevron-right"></i>API基本规范</a>
                        <ul>
                            <li><a href="#idapiRuleMKGF">模块规范</a></li>
                            <li><a href="#idapiRuleMKMMJKGF">模块命名请求规范</a>
                            	<ul>
		                            <li><a href="#idapiRuleMKMMJKGF_DMK">单模块</a></li>
		                            <li><a href="#idapiRuleMKMMJKGF_GLMK">关联模块</a></li>
		                        </ul>
                            </li>
                            <li><a href="#idapiRuleSYGF">使用规范</a>
                            	<ul>
		                            <li><a href="#idapiRuleSYGF_CXGF">查询规范</a></li>
		                            <li><a href="#idapiRuleSYGF_STGF">实体规范</a></li>
		                            <li><a href="#idapiRuleSYGF_JKCSGF">接口及参数规范</a></li>
		                        </ul>
                            </li>
                        </ul>
	                </li> 
	                
	                <!-- 标准接口 -->
	                <c:forEach var="entity" items="${entitys}">
	            	<c:set var="table" value="${entity['table']}"/>  
	            	<c:set var="className" value="${entity['className']}"/>  
	            	<c:set var="functionName" value="${entity['functionName']}"/>  
	                <li><a href="#id${table.className}"><i class="icon-chevron-right"></i> ${functionName}</a></li>
			        </c:forEach>
			        
			        <!-- 定制接口 -->
	                <!--  
		                <li><a href="#iduser"><i class="icon-chevron-right"></i>用户</a></li>
		                <li><a href="#idrole"><i class="icon-chevron-right"></i> 角色</a></li>
	                <li><a href="#idoffice"><i class="icon-chevron-right"></i> 机构</a></li>
	               	 	<li><a href="#idtProduct"><i class="icon-chevron-right"></i> 产品</a></li>
	               	 	<li><a href="#iddict"><i class="icon-chevron-right"></i> 数据字典</a></li>
	               	 	<li><a href="#idtLoanContract"><i class="icon-chevron-right"></i>合同信息</a></li>
	                <li><a href="#idtCompany"><i class="icon-chevron-right"></i> 客户-机构</a></li>
	                <li><a href="#idtEmplyoee"><i class="icon-chevron-right"></i> 客户-个人</a></li>
	                <li><a href="#idtRepayPlan"><i class="icon-chevron-right"></i> 还款计划</a></li>
	                -->
	                <li><a href="#idwOrder"><i class="icon-chevron-right"></i>订单</a></li>
	                
	            </ul>
	        </div>