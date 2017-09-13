<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<script src="${ctxStatic}/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/lib/require.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/main.js?c=11" type="text/javascript"></script>
	<script type="text/javascript">
		require(['helper/api'], function(){
			$(function(){
				var form = $("#target");
				var btn = $("#submitViewForm");
				$(btn).click(function(){
					form.append($('<input type="hidden" name="modelId" value="${dfFormTpl.id}">'));
					form.attr("target", "mainFrame");
					form.attr("method", "post");
					form.attr("action", ctx+"${action}").submit();
				});
			});
		});
	</script>
</head>
<body id="ilayer">
     <div class="container">
   		<div class="row clearfix">
   			<div class="span12">${msg }</div>
   		</div>
      	<div class="row clearfix">
        <!-- Building Form. -->
        <div class="span12">
          <div class="clearfix">
            <div id="build">
              ${dfFormTpl.parsehtml }
            </div>
          </div>
        </div>
        <div class="span12">
          <div class="clearfix">
            <input id="submitViewForm" type="button" class="btn btn-primary" value="提交"/>
          </div>
        </div>
        <!-- / Building Form. -->
      </div>
    </div>
</body>
</html>