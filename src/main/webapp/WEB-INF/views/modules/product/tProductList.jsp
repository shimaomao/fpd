<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function toAdd(){
			 var url = "${ctx}/product/tProduct/form";
				location.href = url;
		}
		
		function test(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条产品!");
			    	return;
			    }
			 var url = "${ctx}/product/tProduct/test?id="+array[0];
			 location.href = url;
		}
		
		function deploy(id){
			 var url = "${ctx}/product/tProduct/test?id="+id;
			 //location.href = url;
			 $.jBox.open("iframe:"+url, "产品-菜单配置", 830, $(top.document).height()-240,{
					buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
					bottomText:"产品-菜单",
					submit:function(v, h, f){
						var ifrWin = h.find("iframe")[0].contentWindow;
						if(v=="refresh"){
							ifrWin.location.reload(true);
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
		
		function toUpdate(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条产品!");
			    	return;
			    }
			    var url = "${ctx}/product/tProduct/form?id="+array[0];
				location.href = url;
		}
		
		function todelete(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条产品!");
			    	return;
			    }
		       var url = "${ctx}/product/tProduct/delete?id="+array[0];
		       return confirmx('确认要删除该产品吗？',url);
		}
		
		
		function addapath(){
			 var array = getCheckValue("cid");
			    if(array.length==0){
			    	alert("请选择一条产品!");
			    	return;
			    }
			    var url = "${ctx}/product/tProduct/depLoy?id="+array[0];
				location.href = url;
			
		}
		function delProduct(cid){
			    if(cid.length==0){
			    	alert("请选择一条产品!");
			    	return;
			    }
		       var url = "${ctx}/product/tProduct/delete?id="+cid;
		       return confirmx('删除后业务数据将丢失,确认要删除该产品吗？',url);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/tProduct/list">产品列表</a></li>
		<shiro:hasPermission name="product:tProduct:edit"><li><a href="${ctx}/product/tProduct/form">产品添加</a></li></shiro:hasPermission>
	</ul>

     <%--  <div style="padding-top: 10px;">
		<div><img src="${ctxStatic}/images/pz.png" style="height: 17px;"/>

		               产品中心>产品配置>标准产品
	   </div>
	</div> --%>
	<form:form id="searchForm" modelAttribute="tProduct" action="${ctx}/product/tProduct/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>产品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>发行对象：</label> 
 				<form:select path="releasesObje" class="input-medium"> 
				<form:option value="" label=""/> 
 					<form:options items="${fns:getDictList('product_releases')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
 				</form:select> 
			</li> 
			 <li><label>发行地区：</label>
				<sys:treeselect id="area" name="area.id" value="${tProduct.area.id}" labelName="area.name" labelValue="${tProduct.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>发行渠道：</label>
				<form:select path="releasesWay" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('product_way')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> 
			<li><label>还款方式：</label>
				<form:select path="payType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('还款方式')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>年化利率：</label>
				<form:input path="rate" htmlEscape="false" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	
	<%-- <shiro:hasPermission name="product:tProduct:edit">
	    <a  class="btn btn-primary" onclick="toAdd();">添加</a>
    	<a  class="btn btn-primary" onclick="toUpdate();">修改</a>
    	<a  class="btn btn-primary"  onclick="todelete();">删除</a>
		<a  class="btn btn-primary" onclick="addapath();">业务配置</a>
		
		<a  class="btn btn-primary" onclick="test();">菜单配置</a>
	</shiro:hasPermission> --%>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <!-- <th>选择</th> -->
				<th>产品名称</th>
				<th>发行对象</th>
				<!-- <th>发行地区</th>-->
				<th>发行渠道</th> 
				<th>还款方式</th>
				<th>年化利率</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tProduct">
			<tr onclick="$(this).children(':first').children(':first').attr('checked','checked');">
				<%-- <td><a href="${ctx}/product/tProduct/form?id=${tProduct.id}">
					${tProduct.name}
				</a></td> --%>
				<td>${tProduct.name}</td>
				<td>
					${fns:getDictLabels(tProduct.releasesObje, 'product_releases', '')}
				</td>
				<%-- <td>
					${tProduct.area.name}
				</td>--%>
				<td>
					${fns:getDictLabel(tProduct.releasesWay, 'product_way', '')}
				</td> 
				<td>
					${fns:getDictLabel(tProduct.payType, 'product_paytype', '')}
				</td>
				<td>
					${tProduct.rate} 
				</td>
				<td>
					<fmt:formatDate value="${tProduct.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
						<a href="${ctx}/product/tProduct/productConfigIndex?id=${tProduct.id}">产品配置</a>
					<c:if test="${tProduct.status eq 'new'}">
						<a href="${ctx}/product/tProduct/published?id=${tProduct.id}&status=published">发布</a>
						<%-- <a href="${ctx}/product/tProduct/delete?id=${tProduct.id}">删除</a> --%>
						<a href="javascript:delProduct('${tProduct.id}')">删除</a>
					</c:if>
					<c:if test="${tProduct.status eq 'published'}">
						<a href="${ctx}/product/tProduct/published?id=${tProduct.id}&status=new">撤销发布</a>
						<a href="${ctx}/syscode/${tProduct.id}.html" target="_blank">访问产品</a>
						<%-- <a href="${ctx}/product/tProduct/delete?id=${tProduct.id}">删除</a> --%>
						<a href="javascript:delProduct('${tProduct.id}')">删除</a>
					</c:if>
					<%-- <a href="${ctx}/product/pCfg/ptConfigIndex">New</a> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>