<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<div class="navbar navbar-inverse navbar-fixed-top">
	       <div class="navbar-inner">
	           <div class="container">
	               <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	                   <span class="icon-bar"></span>
	                   <span class="icon-bar"></span>
	                   <span class="icon-bar"></span>
	               </button>
	              <a class="brand" href="${ctxApi}/swagger/index">API文档</a>
	              <div class="nav-collapse collapse">
	                  <ul class="nav">
	                      <li class="active"><a href="${ctxApi}/swagger/test">API测试</a></li>
	                      <li><a href="${ctxApi}/swagger/inter">API接口</a></li>
	                      <li><a href="${ctxApi}/swagger/editor" target="_blank">API编辑器</a></li>
	                      <li><a href="http://www.ruanyifeng.com/blog/2014/05/restful_api.html" target="_blank">Rest API规范</a></li>
	                      <li><a href="http://www.cnblogs.com/DeasonGuan/articles/Hanami.html" target="_blank">Http详解</a></li>
	                  </ul>
	                  <div id="twitter-share" class="pull-right"></div>
	              </div>
	          </div>
	      </div>
	</div>