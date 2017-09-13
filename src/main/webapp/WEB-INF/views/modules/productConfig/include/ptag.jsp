<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ptagItem" value="${(empty ptagItem)?'dfFormTplList':ptagItem}" />
<!-- 页眉 -->
<div class="container-fluid">
	<div class="row-fluid">
		<c:if test="${ptagItem eq 'tWindSysList'}">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">风控管理&gt;</a>
				<a href="#">风控审核</a>
			</div>
		</c:if>
		<c:if test="${ptagItem eq 'tWindSysCorporation'}">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">风控管理&gt;</a>
				<a href="#">风控审核&gt;</a>
				<a href="#">机构</a>
			</div>
		</c:if>
		<c:if test="${ptagItem eq 'tWindSysPerson'}">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">风控管理&gt;</a>
				<a href="#">风控审核&gt;</a>
				<a href="#">个人</a>
			</div>
		</c:if>
		
		
		<c:if test="${ptagItem eq 'dfFormTplList'}">
			<div class="ptag">
				产品中心&gt;产品管理&gt;
				<a href="#">产品设计&gt;</a>
				<a href="#">产品模板列表</a>
			</div>
		</c:if>
		<c:if test="${ptagItem eq 'dfFormTplForm'}">
			<div class="ptag">
				产品中心&gt;产品管理&gt;
				<a href="#">产品设计&gt;</a>
				<a href="#">产品模板添加</a>
			</div>
		</c:if>
		<c:if test="${ptagItem eq 'selectModel'}">
			<div class="ptag">
				产品中心&gt;产品管理&gt;
				<a href="#">产品设计&gt;</a>
				<a href="#">产品模板添加&gt;</a>
				<a href="#">选择模块</a>
			</div>
		</c:if>
		
		
		
		
		
		
		<!-- 产品的页眉 -->
		<c:if test="${ptagItem eq 'tProductList'}">
			<div class="row-fluid">
				<div class="ptag">
					产品中心&gt;产品管理&gt;
					<a href="${ctx }/product/tProduct/list">产品列表</a>
				</div>
			</div>
		</c:if>
		<c:if test="${(ptagItem eq 'productConfigIndex') || (ptagItem eq 'productConfigYWPZ') || (ptagItem eq 'productConfigYWLC') || (ptagItem eq 'productConfigFKPZ') || (ptagItem eq 'productConfigSPPZ')}">
			<div class="row-fluid">
				<div class="ptag">
					产品中心&gt;产品管理&gt;
					<a href="${ctx }/product/tProduct/list">产品配置&gt;</a>
					<c:if test="${(ptagItem eq 'productConfigIndex')}">
						<a href="${ctx }/product/tProduct/productConfigIndex?id=${tProduct.id}"><b>产品基本信息</b></a>
					</c:if>
					<c:if test="${(ptagItem eq 'productConfigYWPZ')}">
						<a href="${ctx }/product/tProduct/productConfigYWPZ?id=${tProduct.id}"><b>业务配置</b></a>
					</c:if>
					<c:if test="${(ptagItem eq 'productConfigYWLC')}">
						<a href="${ctx }/product/tProduct/productConfigYWLC?id=${tProduct.id}"><b>业务流程</b></a>
					</c:if>
					<c:if test="${(ptagItem eq 'productConfigFKPZ')}">
						<a href="${ctx }/product/tProduct/productConfigFKPZ?id=${tProduct.id}"><b>风控配置</b></a>
					</c:if>
					<c:if test="${(ptagItem eq 'productConfigSPPZ')}">
						<a href="${ctx }/product/tProduct/productConfigSPPZ?id=${tProduct.id}"><b>审批配置</b></a>
					</c:if>
				</div>
			</div>
		</c:if>
	</div>
</div>