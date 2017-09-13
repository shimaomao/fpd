<%@ page contentType="text/html;charset=UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
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