<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include-builder/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<style type="text/css">
	#selectData.disnone{display: none;}
	.iradio{margin: 3px} .item{width: 250px; height: 50px; line-height: 50px; border:  2px dashed #c1c1c1; border-radius: 8px; display: inline-block; text-align: left;} 
	.item.select{background-color: #eee;}
	.icon-view, .icon-check{display: inline-block; width: 30px; height: 20px; font-size: 12px; background-image:url("${ctxStatic}/form/builder/assets/img/icon-view.png"); background-repeat: no-repeat; background-size: 50% 50%; background-position: center;}
	.icon-check{background-image:url("${ctxStatic}/form/builder/assets/img/icon-check.png");}
	</style>
	<script type="text/javascript">
		require(['app/selectData','helper/api'], function(selectData, api){
			selectData.initialize();
			
			var groups = {
				selectTpl:"#selectTpl",
				items:"#modelTpl .item",
				itemsRadio:"#modelTpl .cradio",
				itemsView:".icon-view",
				attrId:"data-id",
				attrCategory:"data-category"
			};
			$(groups.itemsRadio).click(function(){
				var item = $(this).parent();
				var dfId = $(this).val();
				var dfCategory = $(this).attr(groups.attrCategory);
				$.post(ctx+"/form/builder/builder/ajaxSaveTpl",{id:dfId, relId:'${relId }', productId:'${productId }', modsub:'${modsubKey.key }', category:dfCategory }, function(result){
					if(result){
						item.siblings().removeClass("select");
						item.addClass("select")
						
						var stpl = $(groups.selectTpl);
						if((stpl == null) || (stpl.length == 0)){
							groups.selectTpl = "#selectHidTpl";
							$(groups.selectTpl).show();
						}
						$(groups.selectTpl).find(".title").html(item.find(".title").html());
						$(groups.selectTpl).find(groups.itemsView).attr(groups.attrId, item.find(groups.itemsView).attr(groups.attrId));
					}
				});
			});
			$(groups.itemsView).click(function(){
				api.jbox.openUrl("${ctx}/form/tpl/dfFormTpl/view?id="+$(this).attr(groups.attrId), "表单-预览", "自定义表单-预览");
			});
			$(groups.items).dblclick(function(){
				var dfId = $(this).find("input").val();												
				window.location.href = ctx+"/form/builder/builder/toForm?ftplId="+dfId+"&modelUrl="+ctx+"/form/builder/builder/selectData?urlData={category:${category},modsub:${modsubKey.key},categoryId:${categoryId},productId:${productId},relId:${relId}}";
				//window.location.href = ctx+"/form/builder/builder/toForm?ftplId="+dfId+"&modelUrl="+ctx+"/product/tProduct/productConfigYWLC?id=${categoryId}";
			});
		});
	</script>
</head>
<body id="ilayer" style="padding-top: 5px;">
     <ul class="nav nav-tabs">
		<li ><a href="${ctx}/form/tpl/dfFormTpl/">自定义表单列表</a></li>
		<shiro:hasPermission name="form:tpl:dfFormTpl:edit"><li class="active"><a href="${ctx}/form/builder/builder/selectModel">表单设计</a></li></shiro:hasPermission>
	</ul>
    <div class="container">
    	<div class="row clearfix">
   			<div id="messageBox" style="display: block;">${msg }</div>
   		</div>
   		<div id="DFormGroup" class="row clearfix">
	       	<div id="selectData" class="span12  <c:if test='${not empty relId }'>disnone</c:if>">
				<div class="row clearfix">
				  <!-- Building Form. -->
				  <div class="span12">
				    <div class="clearfix">
				    	<h4>你选择的数据<c:if test="${empty relId }">(公共模板)</c:if><button id="btnBuilder" class="btn btn-primary pull-right" >生成表单</button><button class="btn btn-primary pull-right" onclick="javascript:location.reload(true);" style="margin-right:5px;" >刷新</button><a id="btnReturn" class="btn btn-primary pull-right" href="javascript:" onclick="self.location=document.referrer;" style="margin-right:5px;" >上一步</a></h4>
				      	<hr>
				      	<form id="dformTJ" class="form-inline" action="${ctx }/form/builder/builder/toForm" style="border:1px solid #ddd;">
				     		<div class="control-group">
								<div class="controls">
									<c:forEach var="cmodsub" items="${modsubs }" varStatus="idx">
										<label class="radio iradio">
											<c:if test="${empty modsubKey}">
										 		<input name="modsub" value="${cmodsub.key}" type="radio" <c:if test='${idx.index == 0}'>checked="true"</c:if>/>${cmodsub.name}
											</c:if>
											<c:if test="${not empty modsubKey}">
										 		<input name="modsub" value="${cmodsub.key}" type="radio" <c:if test='${cmodsub.key eq modsubKey.key}'>checked="true"</c:if>/>${cmodsub.name}
										 	</c:if>
										</label>
					  				</c:forEach>
								</div>
							</div>
							<hr>
				     		<div class="control-group">
								<div id="DFormDist" class="controls">
									<input name="relId" value="${relId}" type="hidden" />
									<input name="productId" value="${productId}" type="hidden" />
									<input name="category" value="${category}" type="hidden" />
									<%-- <c:if test="${category eq 'business_application'}"> --%>
									 	<c:if test="${not empty relId }">
									 		<input name="modelUrl" type="hidden" value="${ctx }/form/builder/builder/selectData?urlData={category:${category},modsub:${modsubKey.key},categoryId:${categoryId},relId:${relId},productId:${productId}}" />
									 	</c:if>
								 	<%-- </c:if> --%>
									<c:forEach var="item" items="${list }" varStatus="idx">
										<label class="radio iradio" data-idx="${idx.index}" data-status="0">
										  <span class="badge">${idx.index}-${item.name }</span>
										  <input name="params" value="${item.id}" type="hidden" />
										</label>
					  				</c:forEach>
								</div>
							</div>
				      	</form>
				    </div>
				  </div>
				</div>
<!-- 				<div class="row clearfix"> -->
<!-- 				   	<div class="span12"> -->
<!-- 				    	<div class="clearfix"> -->
<!-- 				     	<h4>排除的数据</h4> -->
<!-- 				      	<hr> -->
<!-- 				     	<form class="form-inline"> -->
<!-- 				     		<div class="control-group"> -->
<!-- 								<div id="DFormSrc" class="controls"></div> -->
<!-- 							</div> -->
<!-- 				      	</form> -->
<!-- 				      </div> -->
<!-- 				    </div> -->
<!-- 			 	</div> -->
		 	</div>
		 	<div class="span12">
			 	<c:if test="${not empty relId }">
					<div class="row clearfix">
					   	<div class="span12"><div class="clearfix"><h4>表单模板<a id="btnReturnPD" class="btn btn-primary pull-right" href="${ctx}/product/tProduct/productConfigYWLC?id=${categoryId}" >返回业务配置</a><button id="btnAddNew" class="btn btn-primary pull-right" style="margin-right:5px;">新增模板</button><button class="btn btn-primary pull-right" onclick="javascript:location.reload(true);" style="margin-right:5px;" >刷新</button><a id="btnReturn" class="btn btn-primary pull-right" href="javascript:" onclick="self.location=document.referrer;" style="margin-right:5px;" >上一步</a></h4><hr></div></div>
					   	<div class="span12">
					   		<form class="form-inline">
						   		<div class="control-group">
									<div class="controls">
										<c:if test="${empty modsubKey}">
											<c:forEach var="cmodsub" items="${modsubs }" varStatus="idx">
												<label class="radio iradio">
													<c:if test="${idx.index eq 0}">
												 		<input name="modsub" value="${cmodsub.key}" type="radio" checked="checked"/>${cmodsub.name}
													</c:if>
													<c:if test="${idx.index ne 0}">
												 		<input name="modsub" value="${cmodsub.key}" type="radio"/>${cmodsub.name}
													</c:if>
												</label>
							  				</c:forEach>
										</c:if>
										<c:if test="${not empty modsubKey}">
									 		<input name="modsub" value="${modsubKey.key}" type="radio" checked="checked"/>${modsubKey.name}
									 	</c:if>
									</div>
								</div>
							</form><hr>
					   	</div>
					 	<div id="selectTplDiv" class="span12">
					 		<h4 id="selectHidTpl" class="text-center img-polaroid item select" style="display: none;" title="预览表单布局"> <i class="icon-check"> </i> <span class="title"></span></h4>
					 		<c:if test="${not empty dfFormTplRel.relId}">
				        		<h5 id="selectTpl" class="text-center img-polaroid item select" title="预览表单布局"> <i class="icon-check"> </i> <span class="title">
				        		<nobr>${dfFormTplRel.sname }</nobr></span><i class="icon-view" data-id="${dfFormTplRel.id}"></i></h5>
					    	</c:if>
					    </div>
				    </div>
					<div class="row clearfix">
						<div class="span12">
						<hr></div>
					 	<div id="modelTpl" class="span12">
					 		系统模板：
						 	<c:forEach var="dfFormTpl" items="${sysTpls }" varStatus="idx">
						 		<c:if test="${relId ne dfFormTpl.relId}">
				        			<h5 class="text-center img-polaroid item" title="预览表单布局"> <input type="radio" name="cradio" class="cradio" value="${dfFormTpl.id}" data-category="${dfFormTpl.model}"/> <span class="title"><nobr>${dfFormTpl.sname }</nobr></span><i class="icon-view" data-id="${dfFormTpl.id}"></i></h5>
						 		</c:if>
						 	</c:forEach>
					 		<hr>租户模板：
						 	<c:forEach var="dfFormTpl" items="${zdyTpls }" varStatus="idx">
						 		<c:if test="${relId ne dfFormTpl.relId}">
				        			<h5 class="text-center img-polaroid item" title="预览表单布局"> <input type="radio" name="cradio" class="cradio" value="${dfFormTpl.id}" data-category="${dfFormTpl.model}"/> <span class="title"><nobr>${dfFormTpl.sname }</nobr></span><i class="icon-view" data-id="${dfFormTpl.id}"></i></h5>
						 		</c:if>
						 	</c:forEach>
					    </div>
				    </div>
			    </c:if>
	        </div>
	    </div>
    </div>
</body>
</html>