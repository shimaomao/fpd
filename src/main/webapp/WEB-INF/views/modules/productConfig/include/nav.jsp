<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="navPC" value="${(empty navPC)?'index':navPC}" />
<ul class="nav nav-tabs" style="background-color: #fff;">
	<li <c:if test="${navPC eq 'index'}"> class="active"</c:if>><a href="${ctx}/product/tProduct/productConfigIndex?id=${tProduct.id}">产品基本信息</a></li>
	<li <c:if test="${navPC eq 'ywpz'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigYWPZ?id=${tProduct.id}')">业务配置</a></li>
	<li <c:if test="${navPC eq 'ywlc'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigYWLC?id=${tProduct.id}')">业务流程</a></li>
<%-- 	<li <c:if test="${navPC eq 'qxpz'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigQXPZ?id=${tProduct.id}')">权限配置</a></li>
	<li <c:if test="${navPC eq 'cdpz'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigCDPZ?id=${tProduct.id}')">菜单配置</a></li> --%>
	<li <c:if test="${navPC eq 'fkpz'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigFKPZ?id=${tProduct.id}')">风控配置</a></li>
	<li <c:if test="${navPC eq 'sppz'}"> class="active"</c:if>><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigSPPZ?id=${tProduct.id}')">审批配置</a></li>
</ul><br/>
<script type="text/javascript">
function toUrl(url){
	var fid = '${tProduct.id}';
	if(fid){
		window.location.href=url;
	}else{
		/* if(url.indexOf("tIncubator/form")>=0){
			alertx("请先录入“申请审核”内容！", closed);
		}else{
			alertx("请先录入孵化器基本信息完成初始化工作！", closed);
		} */
	}
}
</script>