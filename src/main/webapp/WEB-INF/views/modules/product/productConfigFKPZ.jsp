<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品业务配置</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/modules/productConfig/front/include/head2.jsp" %>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable21").treeTable({expandLevel : 5}).show();
			$("#treeTable22").treeTable({expandLevel : 5}).show();
			$("#treeTable23").treeTable({expandLevel : 5}).show();
			checkFK();
		});
		function toSubmit(){
      		document.forms[0].submit();
        }
		function checkFK(){
			var ckBox = {
				"ckFK":{
					"selAllClass":".checkAllFK",
					"fclass":".windCheck"
				},"checkTBox":function(ckbox){
					if($(ckbox).prop("checked")){
						$(ckBox.ckFK.fclass).attr("checked", true);
					}else{
						$(ckBox.ckFK.fclass).attr("checked", false);
					}
				},"checkAllTBox":function(){
					var isCheck = true;
					var checkboxs = $(ckBox.ckFK.fclass);
					for(var i=0; i<checkboxs.length; i++){
						if(checkboxs[i].checked == false){
							isCheck = false;
						}
					}
					if(isCheck){
						$(ckBox.ckFK.selAllClass).attr("checked", true);
					}else{
						$(ckBox.ckFK.selAllClass).attr("checked", false);
					}
				},"getIds":function(){
					var ckFKs = $(ckBox.ckFK.fclass);
					var curIds = "";
					var isFirst = true;
					$(ckFKs).each(function(e){
						if(isFirst){
							curIds += $(this).val();
							isFirst = false;
						}else{
							curIds += ","+$(this).val();
						}
					});
					return curIds;
				},"ajaxFKSave":function(wids){
					 $.ajax({
			             type:"POST",
			             url:"${ctx}/windcfg/tWindControlCfg/ajaxSave",
			             data:{"product.id":'${tProduct.id}',"wind.id":wids},
			             dataType:"json",
			             success:function(data){}
			         });
				},"ajaxFKDelete":function(wids){
					$.ajax({
			             type:"POST",
			             url:"${ctx}/windcfg/tWindControlCfg/ajaxDelete",
			             data:{"product.id":'${tProduct.id}',"wind.id":wids},
			             dataType:"json",
			             success:function(data){}
			         });
				}
			};
			$(ckBox.ckFK.selAllClass).click(function(){
				var curIds = ckBox.getIds();
				if($(this).prop("checked")){
					ckBox.ajaxFKSave(curIds);
				}else{
					ckBox.ajaxFKDelete(curIds);
				}
				
				ckBox.checkTBox(this);
			});	
			$(ckBox.ckFK.fclass).click(function(){
				if($(this).prop("checked")){
					ckBox.ajaxFKSave($(this).val());
				}else{
					ckBox.ajaxFKDelete($(this).val());
				}
				ckBox.checkAllTBox();
			});
			/*页面加载初始化全选*/
			ckBox.checkAllTBox();
		}
		 
		function openWin(url, title){
			top.$.jBox.open("iframe:"+url, 
	    			title, $(top.document).width()-200,$(top.document).height()-150,{
	    		buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
	    				ifrWin.location.reload(true);
	                	//ifrWin.clearAssign();
	    				return false;
	                }else if(v=="return"){
	                	ifrWin.history.go(-1);
	                	ifrWin.location.reload();
	    				return false;
	                }
	    		}, loaded:function(h){
	    			$(".jbox-content", top.document).css("overflow-y","hidden");
	    		}
	    	});

		}
	</script>
	<style type="text/css">
		.content_block {
			width:100%;
			border-bottom:0px #999999 solid;
			margin-top:0px;
			margin-bottom:10px;
			padding-bottom:10px;
		}
		.content_block .ax_ico {
			float:left;
			margin-right: 10px;
		}
		.content_block .ax_title {
		    font-family: '微软雅黑 Regular','微软雅黑';
		    font-weight: 400;
		    font-style: normal;
		    font-size: 13px;
		    color: rgb(0, 0, 0);
		    text-align: center;
		    line-height: 20px;
			height:20px;
			float:left;
		}
		input, .input-append, .input-append input, .input-append a{padding: 0px; margin: 0px;}
		.input-append a, .input-append a i{ height: 22px;}
		.isNot{color:#ccc;}
		.col_yw{width: 15%;}
		.col_bd{width: 20%;}
		.col_lc{width: 50%;}
		.table th.atop, .table td.atop{vertical-align:top}
	</style>
</head>
<body>
	<%-- <c:set var="navPC" value="ywlc"></c:set>
	<%@ include file="/WEB-INF/views/modules/product/include/nav.jsp" %>
	
	<div class="content_block">
		<div class="ax_ico"><img src="${ctxStatic}/images/pz.png" width="20px" height="20px"/></div>
		<div class="ax_title">产品业务流程配置-(产品名称：${tProduct.name})</div>
		<div style="clear:left;"></div>
	</div> --%>
	
	<c:set var="stpsItem" value="4" />
	<c:set var="ptagItem" value="productConfigFKPZ"></c:set>
	<%@ include file="/WEB-INF/views/modules/productConfig/include/ptag.jsp" %>
	<div class="box-down">
		<div class="box">
			<%@ include file="/WEB-INF/views/modules/productConfig/include/stepsCPPZ.jsp" %>
		</div>
	</div>
	
	<form id="inputForm" action="${ctx}/product/tProduct/saveProductConfigFKPZ" method="post">
		<input type="hidden" value="${tProduct.id}" name="id">
		<input type="hidden" name="modelType" value="step8" />
		<table class="table table-bordered table-condensed">
			<thead><tr>
				<th width="10%">业务模块</th>
				<th width="60%">业务功能</th>
				<th width="30%"><input class="checkAllFK" type="checkbox" />风控配置</th>
			</tr></thead>
			<tbody>
				<tr>
					<td>
						贷前管理
					</td>
					<td>
					<c:if test="${not empty dqList}">
						<table id="treeTable21" class="table table-bordered table-condensed hide">
							<tbody><c:forEach items="${dqList}" var="pbizVo" varStatus="idex">
								<tr id="${pbizVo.id}" pId="${(pbizVo.parent.id ne '1')?pbizVo.parent.id:'0'}" class="bizType${pbizVo.type }">
									<td class="col_yw" width="60%" nowrap>
										${pbizVo.bizName}
									</td>
									<c:if test="${pbizVo.status eq '1' }">
										<td class="col_bd" ><a href="${ctx }/form/builder/builder/selectData?category=${pbizVo.category}&relId=${pbizVo.cfgId}&categoryId=${pbizVo.productId}&productId=${pbizVo.productId}&modsub=${pbizVo.bizCode}"><c:forEach items="${dfFormTpls}" var="dfTpl" varStatus="idx"><c:if test="${dfTpl.relId eq pbizVo.cfgId}">${dfTpl.sname}</c:if></c:forEach></a></td>
									</c:if>
									<c:if test="${pbizVo.status ne '1' }">
										<td class="col_bd isNot">无表单</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '1') || (pbizVo.isFlow eq '2')}">
										<td class="col_lc">
											<nobr>
												<c:if test="${not empty pbizVo.procDefName}">
													${pbizVo.procDefName}
												</c:if>
												<c:if test="${empty pbizVo.procDefName}">
													<span style="color:#fff; background-color: red; padding: 1px 5px;">警告！你有流程未配置！</span>
												</c:if>
											 </nobr>
										</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '0') || (empty pbizVo.isFlow)}">
										<td class="col_lc isNot">非流程</td>
									</c:if>
								</tr>
								<c:set var="indexCount" value="${indexCount+1}"/>
							</c:forEach></tbody>
						</table>
					</c:if>
					</td>
					<td rowspan="${(fn:length(dqList))+(fn:length(dzList))+(fn:length(dhList))}"  class="atop">
						 <c:forEach items="${winds}" var="wind">
					    	<span ><input class="windCheck" type="checkbox" name="windCheck" value="${wind.id}" <c:if test="${wind.checked}">checked="checked"</c:if> />${wind.name}</span><br/><br/>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						贷中管理
					</td>
					<td>
					<c:if test="${not empty dzList}">
						<table id="treeTable22" class="table table-bordered table-condensed hide">
							<tbody><c:forEach items="${dzList}" var="pbizVo" varStatus="idex">
								<tr id="${pbizVo.id}" pId="${(pbizVo.parent.id ne '1')?pbizVo.parent.id:'0'}" class="bizType${pbizVo.type }">
									<td class="col_yw" nowrap>${pbizVo.bizName}
										<input type="hidden" value="${pbizVo.cfgId}" name="bizCfgList[${indexCount}].id">
								       	<input type="hidden" value="${pbizVo.id}" name="bizCfgList[${indexCount}].biz.id">
									</td>
									<c:if test="${pbizVo.status eq '1' }">
										<td class="col_bd" ><a href="${ctx }/form/builder/builder/selectData?category=business_application&relId=${pbizVo.cfgId}&categoryId=${pbizVo.productId}&productId=${pbizVo.productId}&modsub=${pbizVo.bizCode}"><c:forEach items="${dfFormTpls}" var="dfTpl" varStatus="idx"><c:if test="${dfTpl.relId eq pbizVo.cfgId}">${dfTpl.sname}</c:if></c:forEach></a></td>
									</c:if>
									<c:if test="${pbizVo.status ne '1' }">
										<td class="col_bd isNot">无表单</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '1') || (pbizVo.isFlow eq '2')}">
										<td class="col_lc">
											<nobr>
												<c:if test="${not empty pbizVo.procDefName}">
													${pbizVo.procDefName}
												</c:if>
												<c:if test="${empty pbizVo.procDefName}">
													<span style="color:#fff; background-color: red; padding: 1px 5px;">警告！你有流程未配置！</span>
												</c:if>
											 </nobr>
										</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '0') || (empty pbizVo.isFlow)}">
										<td class="col_lc isNot">非流程</td>
									</c:if>
								</tr>
								<c:set var="indexCount" value="${indexCount+1}"/>
							</c:forEach></tbody>
						</table>
					</c:if>
					</td>
				</tr>
				<tr>
					<td>
						贷后管理
					</td>
					<td>
					<c:if test="${not empty dhList}">
						<table id="treeTable23" class="table table-bordered table-condensed hide">
							<tbody><c:forEach items="${dhList}" var="pbizVo" varStatus="idex">
								<tr id="${pbizVo.id}" pId="${(pbizVo.parent.id ne '1')?pbizVo.parent.id:'0'}" class="bizType${pbizVo.type }">
									<td class="col_yw" nowrap>${pbizVo.bizName}</td>
									<c:if test="${pbizVo.status eq '1' }">
										<td class="col_bd" ><a href="${ctx }/form/builder/builder/selectData?category=business_application&relId=${pbizVo.cfgId}&categoryId=${pbizVo.productId}&productId=${pbizVo.productId}&modsub=${pbizVo.bizCode}"><c:forEach items="${dfFormTpls}" var="dfTpl" varStatus="idx"><c:if test="${dfTpl.relId eq pbizVo.cfgId}">${dfTpl.sname}</c:if></c:forEach></a></td>
									</c:if>
									<c:if test="${pbizVo.status ne '1' }">
										<td class="col_bd isNot">无表单</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '1') || (pbizVo.isFlow eq '2')}">
										<td class="col_lc">
											<nobr>
												<c:if test="${not empty pbizVo.procDefName}">
													${pbizVo.procDefName}
												</c:if>
												<c:if test="${empty pbizVo.procDefName}">
													<span style="color:#fff; background-color: red; padding: 1px 5px;">警告！你有流程未配置！</span>
												</c:if>
											 </nobr>
										</td>
									</c:if>
									<c:if test="${(pbizVo.isFlow eq '0') || (empty pbizVo.isFlow)}">
										<td class="col_lc isNot">非流程</td>
									</c:if>
								</tr>
								<c:set var="indexCount" value="${indexCount+1}"/>
							</c:forEach></tbody>
						</table>
					</c:if>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="container">
			<div class="row clearfix">
				<div class="span12">
					<div class="clearfix" style="margin-bottom: 50px;">
						<input id="btnCancel" class="btn btn-primary" type="button" value="上一步" onclick="history.go(-1)"/>
						<shiro:hasPermission name="product:tProduct:edit">
						<input id="btnSubmit" class="btn btn-primary" type="button" value="下一步" onclick="toSubmit()"/>
						</shiro:hasPermission>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>