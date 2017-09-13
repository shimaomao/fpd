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
				<a href="#">收费服务</a>
			</div>
		</div>
	</div>
	<ul class="nav nav-tabs">
		<li <c:if test="${biRule.type eq 2}">class="active"</c:if>><a href="${ctx}/billing/collect/biCollect/openServer?type=2">计量服务列表</a></li>
		<li <c:if test="${biRule.type eq 1}">class="active"</c:if>><a href="${ctx}/billing/collect/biCollect/openServer?type=1">计时服务列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<div class="box-down" >
		<c:forEach items="${dicts}" var="dict" varStatus="idx">
		<div class="box group">
		    <div class="col-md-12 col-sm-12 title"><h5>${idx.index+1}、${dict.label}</h5></div>
		    <c:forEach items="${groups}" var="biRule">
			    <c:if test="${biRule.price.element.type eq dict.value}">
			    <div class="col-sm-6 col-md-4 col-md-4 col-lg-3 ">
		          <div class="thumbnail">
		            <div class="caption">
		              <h4 class="text-center">
		                <a href="#" title="万众服务"  >${biRule.name}<br><small>${biRule.remarks}</small></a>
		              </h4><hr/>
	              		<div>单价：${biRule.price.price}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</div>
						<div>平均价：${biRule.averagePrice}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</div>
						<div>数量/单位： <c:if test="${biRule.price.type eq 1}">${biRule.unitVal}${fns:getDictLabel(biRule.unit, 'biling_rule_unit', '')}</c:if>
							 <c:if test="${biRule.price.type eq 2}">${biRule.number}${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}</c:if>
						</div>
						<div>
							总价:${biRule.price.price*biRule.number}元
						</div>
						<div>
							折后价:${biRule.totalPrice}
						</div>
						<div>
							优惠率：${fns:getDictLabel(biRule.rate, 'biling_rule_rate', '')}
						</div>
						<div>
							  期限：<c:if test="${biRule.price.type eq 1}">${biRule.totalTime}</c:if>
							 <c:if test="${biRule.price.type eq 2}">不限</c:if>
						</div>
						<div>
							 说明：${biRule.remarks}
						</div>
						<div>
							发布时间：<fmt:formatDate value="${biRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</div><hr/>
						<div>
							<a href="javascript:void(0);" onclick="ajaxOpenServer(this,'${biRule.id}')" class="btn btn-default btn-small">开通服务</a>
						</div>
		            </div>
		          </div>
		        </div>
		        </c:if>
			</c:forEach>
		</div>
		</c:forEach>
	</div>
	<%-- <div class="container">
		<sys:message content="${message}"/>
		<c:forEach items="${groups}" var="group" varStatus="idx">
		    <div class="row group">
		        <div class="span12 title"><h4>${idx.index+1}、${group.key}</h4> </div>
		        <c:forEach items="${group.value}" var="biRule">
		        	<div class="span4 module">
		        		<div>名称：<a href="${ctx}/billing/rule/biRule/form?id=${biRule.id}">${biRule.name}</a></div>
						<div>原价：<a href="${ctx}/billing/price/biPrice/form?id=${biRule.price.id}">
							${biRule.price.price}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}
						</a></div>
						<div>折后价：<a href="${ctx}/billing/rule/biRule/form?id=${biRule.id}">
							${biRule.averagePrice}/${fns:getDictLabel(biRule.price.unit, 'biling_price_unit', '')}
						</a></div>
						<div>优惠率：${fns:getDictLabel(biRule.rate, 'biling_rule_rate', '')}</div>
						<div>数量：${biRule.number}</div>
						<div>总价：${biRule.totalPrice}</div>
						<div>期限：<c:if test="${biRule.price.type eq 1}">${biRule.totalTime}</c:if>
							 <c:if test="${biRule.price.type eq 2}">不限</c:if></div>
						<div>${biRule.price.remarks}<br/>${biRule.remarks}</div>
						<div>发布时间：<fmt:formatDate value="${biRule.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
		        	</div>
				</c:forEach>
	        </div> 
		</c:forEach>
	</div> --%>
</body>
</html>