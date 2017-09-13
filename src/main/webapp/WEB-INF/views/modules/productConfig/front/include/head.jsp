<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" /><meta http-equiv="Pragma" content="no-cache" /><meta http-equiv="Expires" content="0" />
<meta name="author" content="http://thinkgem.iteye.com"/><meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/productConfig/css/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
<script type="text/javascript" src="${ctxStatic }/modules/productConfig/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/modules/productConfig/js/bootstrap.min.js"></script>
<script type="text/javascript">
	function setStepWidth(pBox,childBox){
		var offset = 5;
		var pBoxWidth = $(pBox).width();
		var childNum = $(pBox).children().length;
		var boxWidth = pBoxWidth/childNum;
		$(pBox).css("width",(pBoxWidth+(offset*childNum))+"px");
		$(childBox).css("width",(boxWidth)+"px");
	}
</script>

