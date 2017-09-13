<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
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
            <p/>
          </div>
        </div>
        <!-- / Building Form. -->
      </div>
    </div>
</body>
</html>