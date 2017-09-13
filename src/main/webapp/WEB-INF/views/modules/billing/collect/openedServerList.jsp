<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收费服务管理</title>
    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
	<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
	<script src="${ctxStatic}/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<style type="text/css">
		.group .title{ min-height: 30px; border-bottom:1px solid #eee; margin-bottom: 5px; }
		.group .module{ min-height: 150px; margin-bottom: 30px; }
	</style>
	<script type="text/javascript">
		function ajaxOpenServer(dom, biRuleId){
			$.ajax({
	             type:"POST",
	             url:"${ctx}/billing/collect/biCollect/ajaxOpenServer",
	             data:{"biRuleId":biRuleId},
	             dataType: "json",
	             success: function(data){
	            	 var data = eval(data);
	            	 if(data.status){
	            		 $(dom).parent().html("已开通服务!");
	            		 $(dom).remove();
	            		 alert(data.msg);
	            	 }
	             }
	        });
		}
	</script>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">收费服务&gt;</a>
				<a href="#">已开通服务</a>
			</div>
		</div> 
	</div>
	<ul class="nav nav-tabs">
		<li <c:if test="${biCollect.rule.type eq 2}">class="active"</c:if>><a href="${ctx}/billing/collect/biCollect/openedServer?rule.type=2">计量服务列表</a></li>
		<li <c:if test="${biCollect.rule.type eq 1}">class="active"</c:if>><a href="${ctx}/billing/collect/biCollect/openedServer?rule.type=1">计时服务列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<div class="box-down" >
		<c:forEach items="${dicts}" var="dict" varStatus="idx">
		<div class="box group">
		    <div class="col-md-12 col-sm-12 title"><h5>${idx.index+1}、${dict.label}</h5></div>
		    <c:forEach items="${biCollects}" var="biCollect">
			    <c:if test="${biCollect.rule.type eq dict.value}">
			    <div class="col-sm-6 col-md-4 col-md-4 col-lg-3 ">
		          <div class="thumbnail">
		            <div class="caption">
		              	<h4 class="text-center"><a href="#" title="万众服务"  >${biCollect.rule.name}</a></h4>
		              	<hr/>
						<div>数量/单位： <c:if test="${biCollect.rule.type eq 1}">${biCollect.rule.unitVal}${fns:getDictLabel(biCollect.rule.unit, 'biling_rule_unit', '')}</c:if>
							 <c:if test="${biCollect.rule.type eq 2}">${biCollect.rule.number}${fns:getDictLabel(biCollect.rule.price.unit, 'biling_price_unit', '')}</c:if>
						</div>
						<div>
							总价:${biCollect.totalPrice}元
						</div>
						<div>
							<c:if test="${biCollect.rule.type eq 2}">剩余数/总数:${biCollect.surplusNumber}/${biCollect.number}${fns:getDictLabel(biCollect.rule.price.unit, 'biling_price_unit', '')}</c:if>
							<c:if test="${biCollect.rule.type eq 1}">剩余时间/总时间:${biCollect.surplusTime}/${biCollect.totalTime}小时</c:if>
							
						</div>
						<div>
							  期限：<c:if test="${biCollect.rule.type eq 1}">${biCollect.surplusNumber}小时</c:if>
							 <c:if test="${biCollect.rule.type eq 2}">不限</c:if>
						</div>
						<div>
							 说明：${biCollect.rule.remarks}
						</div>
						<div>
							购买时间：<fmt:formatDate value="${biCollect.rule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</div><hr/>
						<div><code><small>${biCollect.element.name}</small></code></div>
		            </div>
		          </div>
		        </div>
		        </c:if>
			</c:forEach>
		</div>
		</c:forEach>
	</div>
</body>
</html>