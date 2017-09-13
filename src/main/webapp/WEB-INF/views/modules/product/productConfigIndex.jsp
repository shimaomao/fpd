<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品配置</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/modules/productConfig/front/include/head2.jsp" %>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script src="${ctxStatic}/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/lib/require.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/main.js?c=11" type="text/javascript"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				var form = $("#target");
				var btn = $(".submitViewForm");
				//console.log("payType="+${fns:toJson(tProduct.payType)}+"=data="+JSON.stringify(${fns:toJson(tProduct)}));
				api.form.init(form, eval(${fns:toJson(tProduct)}));
				$(btn).click(function(){
					form.attr("method", "post");
					var divWrap = form.find("#divWrap");
					if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
						form.append("<div id=\"divWrap\"></div>");
						divWrap = form.find("#divWrap");
					}
					divWrap.html('<input type="hidden" name="id" value="${tProduct.id}"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
					form.attr("target", "mainFrame");
					form.attr("action", ctx+"${action}").submit();
				});
				//谷歌和火狐不下拉框不自选，手动帮忙选择
				$("#type").val('${tProduct.type}');
				$("#releasesWay").val('${tProduct.releasesWay}');
			});
		});
	</script>
</head>
<body>
	<%-- <c:set var="navPC" value="index"></c:set>
	<%@ include file="/WEB-INF/views/modules/product/include/nav.jsp" %> --%>
	
	<c:set var="stpsItem" value="1" />
	<c:set var="ptagItem" value="productConfigIndex"></c:set>
	<%@ include file="/WEB-INF/views/modules/productConfig/include/ptag.jsp" %>
	<div class="box-down">
		<div class="box">
			<%@ include file="/WEB-INF/views/modules/productConfig/include/stepsCPPZ.jsp" %>
		</div>
	</div>
	<sys:message content="${message}"/>
	<c:if test="${not empty dfFormTpl}">
	${dfFormTpl.parsehtml }
	<div class="container">
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
					<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					<input id="submitViewForm" type="button" class="submitViewForm btn btn-primary" value="保存" />
					<input id="btnCancel" class="submitViewForm btn btn-primary" type="button" value="下一步"/>
				</div>
			</div>
		</div>
	</div>
	</c:if>
</body>
</html>