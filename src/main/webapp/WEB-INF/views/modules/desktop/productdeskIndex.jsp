<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/desktop/tab.css">
	<script type="text/javascript">
	
		
		function changeProduct(productid){
			 $.ajax({
		         	type: "POST",
		         	url: "${ctx}/index/todo/toChageProduct",
		         	data: {syscode:productid},
		         	dataType: "html",
		         	success: function(data){
		         		 top.document.location.reload();
		         	}
		       });
		}
		
	</script>
</head>
<body>
	<div class="wrap">
		<div class="tab-top">
			<span class="active"><a id="initproduct" href="${ctx}/index/todo/productdeskindex">首页</a></span>
		</div>
		<div class="tab-box" style="display: block">
			<ul class="clearfix">
			    <c:forEach items="${productlist}" var="tProduct">
				  	<li onclick="changeProduct('${tProduct.id}');" style="cursor:pointer;">
						<div class="title-box">${tProduct.name}</div>
						<div class="cont-box">
                                     <font size="2px;" style="margin-left:8px;">待审批业务：${tProduct.exanple_amount}(笔)</font><br>
							         <font size="2px;" style="margin-left:8px;">待签订业务：${tProduct.sign_amount}(笔)</font><br>
							         <font size="2px;" style="margin-left:8px;">待放款业务：${tProduct.loan_amount}(笔)</font><br>
                        
                        </div>
					</li>
				  </c:forEach>

			</ul>
		</div>
	</div>
	<script type="text/javascript" src="${ctxStatic}/desktop/jq.js"></script>
	<script type="text/javascript">
		function Tab(menubox, ele, showbox,className) {
		    $(menubox).find(ele).click(function() {
		        var index = $(this).index();
		        className? $(this).addClass(className).siblings().removeClass(className):'';
		        $(showbox).hide().eq(index).show();
		    });
		}
		Tab('.tab-top','span','.tab-box','active');
	</script>
	
	
	
</body>
</html>