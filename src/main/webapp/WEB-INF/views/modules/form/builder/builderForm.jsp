<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<style type="text/css">
		#components .component, #components .component .control-group{width:30%; display: inline-block;}
		#components .component .control-group{margin-bottom: 0px;}
		#components .component .control-group .controls{display: none;}
		
		#components #自定义 .component, #components #自定义 .component .control-group{width:99%; display: block;}
		#components #自定义 .component .control-group{margin-bottom: 20px; }
		#components #自定义 .component .control-group .controls{display: block;}
	</style>
	<script type="text/javascript">
		require(['app/builder'], function(app){
			$(function(){
				var url = "${ctx}/form/builder/builder/ajaxRecords";
				if('${params}' != ''){
					url += "?params=${params}";
				}else{
					url += "?category=${category}";
				}
				$.ajax({
					 type: "POST",
		             url: url,
		             dataType: "json",
		             success: function(result){
		            	app.initialize(result, '${category}', '${modsub}', eval('${dfjson}'), '${dfid}', '${relId}', '${productId}', '${modelUrl}');
	            	}
				});
			});
		});
	</script>
</head>

<body id="ilayer" style="padding-top: 5px;">
     <ul class="nav nav-tabs">
		<li ><a href="${ctx}/form/tpl/dfFormTpl/">自定义表单列表</a></li>
		<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><li class="active"><a href="${ctx}/form/builder/builder/selectModel">表单设计</a></li></shiro:hasPermission>
	</ul>
    <div class="container">
   		<div class="row clearfix">
        	<div class="span12">
        		<div id="messageBox" style="display: block;">${msg }</div>
	   		</div>
   		</div>
   		<div class="row clearfix">
        	<div class="span12">
		    	<h4>表单设计器<button id="btnReset" class="btn btn-primary pull-right" onclick="javascript:location.reload(true);" style="margin-right:5px;" >刷新</button><a id="btnReturn" class="btn btn-primary pull-right" href="javascript:" onclick="self.location=document.referrer;" style="margin-right:5px;" >上一步</a></h4>
		      	<hr>
	      	</div>
   		</div>
      	<div class="row clearfix">
        <!-- Building Form. -->
        <div class="span6">
          <div class="clearfix">
            <h4>我的表单</h4>
            <form class="form-inline">
            	<div class="form-group">
			      <label class="sr-only" for="formName"><strong>表单名称 : </strong></label>
			      <input type="text" class="form-control" id="formName" value="${formName}" placeholder="请填写表单名称">
			   	</div>
			</form>
            <hr>
            <div id="build">
	            <form id="target" class="form-horizontal" method="post"></form>
            </div>
            <p/>
            <input id="submitDesign" type="button" class="btn btn-primary" value="提交"/>
          </div>
        </div>
        <!-- / Building Form. -->

        <!-- Components -->
        <div class="span6">
          <h4>我的模块组件</h4>
          <hr>
          <div class="tabbable">
            <ul class="nav nav-tabs" id="formtabs">
              <!-- Tab nav -->
            </ul>
            <form class="form-horizontal" id="components">
              <fieldset>
                <div class="tab-content">
                  <!-- Tabs of snippets go here -->
                </div>
              </fieldset>
            </form>
          </div>
        </div>
      </div>
    </div> <!-- /container -->
</body>
</html>