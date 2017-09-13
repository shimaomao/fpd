<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="stpsItem" value="${(empty stpsItem)?'1':stpsItem}" />
<div class="stepGroup">
	<div class="row-fluid linebox stepBox">
		<div class="step <c:if test="${stpsItem eq '1'}">curStep</c:if>"><a href="${ctx}/product/tProduct/productConfigIndex?id=${tProduct.id}"><span>1、产品基本信息</span></a></div>
		<div class="step <c:if test="${stpsItem eq '2'}">curStep</c:if>"><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigYWPZ?id=${tProduct.id}')"><span>2、业务配置</span></a></div>
		<div class="step <c:if test="${stpsItem eq '3'}">curStep</c:if>"><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigYWLC?id=${tProduct.id}')"><span>3、业务流程</span></a></div>
		<div class="step <c:if test="${stpsItem eq '4'}">curStep</c:if>"><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigFKPZ?id=${tProduct.id}')"><span>4、风控配置</span></a></div>
		<%-- <div class="step <c:if test="${stpsItem eq '5'}">curStep</c:if>"><a href="javascript:void(0);" onclick="toUrl('${ctx}/product/tProduct/productConfigSPPZ?id=${tProduct.id}')"><span>5、审批配置</span></a></div> --%>
	</div>
	
	<script type="text/javascript">
		$(function(){
			setStepWidth(".stepBox", ".step");
		});
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
</div>