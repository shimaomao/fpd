<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模板初始化管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/form/builder/assets/js/lib/require.js" type="text/javascript"></script>
	<script src="${ctxStatic}/form/builder/assets/js/main.js?c=11" type="text/javascript"></script>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			//db模板
			var dbformNodes=[
				{id:"0", pId:"0", name:"担保"},
				<c:forEach items="${formTpls.dbForms }" var="form">
				{id:"${form.model}", pId:"0", name:"${form.sname}"},		            
				</c:forEach>];				
			// 初始化树结构
			var dbformtree = $.fn.zTree.init($("#dbformTree"), setting, dbformNodes);
			// 不选择父节点
			dbformtree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			dbformtree.expandAll(true);
			//xd模板
			var xdformNodes=[
				{id:"0", pId:"0", name:"贷款"},
				<c:forEach items="${formTpls.xdForms }" var="form">
				{id:"${form.model}", pId:"0", name:"${form.sname}"},		            
				</c:forEach>];				
			// 初始化树结构
			var xdformtree = $.fn.zTree.init($("#xdformTree"), setting, xdformNodes);
			// 不选择父节点
			xdformtree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			xdformtree.expandAll(true);			
			
			
			// db机构
			var dbzNodes=[
					<c:forEach items="${officeList.dbList}" var="office">
					{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},		            
					</c:forEach>];
			// 初始化树结构
			var dbtree = $.fn.zTree.init($("#dbTree"), setting, dbzNodes);
			// 不选择父节点
			dbtree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			dbtree.expandAll(true);
			// xd机构
			var xdzNodes=[
					<c:forEach items="${officeList.xdList}" var="office">
					{id:"${office.id}", pId:"${not empty office.parent?office.parent.id:0}", name:"${office.name}"},		            
					</c:forEach>];
			// 初始化树结构
			var xdtree = $.fn.zTree.init($("#xdTree"), setting, xdzNodes);
			// 不选择父节点
			xdtree.setting.check.chkboxType = { "Y" : "ps", "N" : "s" };
			// 默认展开全部节点
			xdtree.expandAll(true);	
			
			
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					updateFormVals();
					
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			ajaxSaveAuto("#ajaxAutoXdBtn");
			ajaxSaveAuto("#ajaxAutoDbBtn");
			ajaxViewAuto()
		});
		
		/**更新机构类型**/
		function changeOfficeType(){
			var type = $("input[name='officeType']:checked").val();
			if(type==1){
				$('#dbTreeDiv').css("display","");
				$('#xdTreeDiv').css("display","none");
			}else if(type==2){
				$('#dbTreeDiv').css("display","none");
				$('#xdTreeDiv').css("display","");
			}
		}
		/**获取机构类型**/
		function getOfficeType(){
			return $("input[name='officeType']:checked").val();
		}
		/**更新机构、表单数据**/
		function updateFormVals(){
			var temptree = null, tempformtree = null;
			var type = getOfficeType();
			if(type==1){
				temptree = $.fn.zTree.getZTreeObj("dbTree");
				tempformtree = $.fn.zTree.getZTreeObj("dbformTree");
			}else if(type==2){
				temptree = $.fn.zTree.getZTreeObj("xdTree");
				tempformtree = $.fn.zTree.getZTreeObj("xdformTree");
			}
			if((temptree != null) || (tempformtree != null)){
				var ids0 = "", ids1 = "";
				var nodes0 = tempformtree.getCheckedNodes(true);
				var nodes1 = temptree.getCheckedNodes(true);
				for(var i=1; i<nodes0.length; i++) {
					ids0 += nodes0[i].id + ";";
				}
				for(var i=1; i<nodes1.length; i++) {
					ids1 += nodes1[i].id + ";";
				}
				$('#officeName').val(ids1);
				$('#formName').val(ids0);
			}
		}
		
		function ajaxSaveAuto(selector){
			$(selector).click(function(){
				updateFormVals();
				var id = "";
				var type = getOfficeType();
				var tplModels = $("#formName").val();
				$.ajax({
		         	type: "POST",
		         	url: "${ctx}/init/sysInitFormtpl/ajaxSaveAuto",
		         	data: {id:id, type:type, tplModels:tplModels},
		         	dataType: "text",
		         	success: function(data){
		         		console.info(data);
		         		if(data.istrue){
			         		console.info(data.list);
		         		}
		         	}
		        });
			});
		}
		
		function ajaxViewAuto(){
			$(".btn-view").click(function(){
				updateFormVals();
				var type = getOfficeType();
				var tplModels = $("#formName").val();
				if((type != null) && (type != undefined)  && (type != "")){
					if((tplModels != null) && (tplModels != undefined) && (tplModels != "")){
						if((tplModels.indexOf(";") != -1)){
							var tlen = tplModels.length;
							tplModels = tplModels.substr(0,tlen-1);
						}
						
						if((tplModels.indexOf(";") == -1)){
							jboxOpenUrl("${ctx}/form/tpl/dfFormTpl/ajaxView?type="+type+"&tplModel="+tplModels, "JSON表单-预览", "JSON表单-预览("+type+"-"+tplModels+")");
						}else{
							console.info("一次只能预览一个类型模板！");
						}
					}else{
						console.info("请选择模板类型！");
					}
				}else{
					console.info("请选择业务类型！");
				}
			});
		}
		
		function jboxOpenUrl(url, title, bottomText){
			top.$.jBox.open("iframe:"+url, title, 830, $(top.document).height()-240,{
				buttons:{"返回":"return", "刷新":"refresh", "关闭":true}, 
				bottomText:bottomText,
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
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/init/sysInitFormtpl/">模板初始化列表</a></li>
		<li class="active"><a href="${ctx}/init/sysInitFormtpl/form?id=${sysInitFormtpl.id}">模板初始化<shiro:hasPermission name="init:sysInitFormtpl:edit">${not empty sysInitFormtpl.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="init:sysInitFormtpl:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysInitFormtpl" action="${ctx}/init/sysInitFormtpl/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">租户类型：</label>
			<div class="controls">
				<form:radiobuttons path="officeType" items="${fns:getDictList('company_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="" onclick="changeOfficeType()"/><span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div id="dbTreeDiv" style="display: none;">
		<div class="control-group">
			<label class="control-label">模板：</label>
			<div class="controls">				
				<div id="dbformTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件：</label>
			<span class="help-inline" style="cursor: pointer; height: 20px;line-height: 20px; margin-top: 3px; padding-left: 30px; " ><font id="ajaxAutoDbBtn" color="red">更新文件请点击</font> <a class="btn-view" href="javascript:void(0);" style="padding-left: 20px;" title="预览JSON表单布局">预览</a></span>
		</div>
		<div class="control-group">
			<label class="control-label">租户：</label>
			<div class="controls">				
				<div id="dbTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</div>
		<div id="xdTreeDiv" style="display: none;">
		<div class="control-group">
			<label class="control-label">模板：</label>
			<div class="controls">				
				<div id="xdformTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件：</label>
			<span class="help-inline" style="cursor: pointer; height: 20px;line-height: 20px; margin-top: 3px; padding-left: 30px; " ><font id="ajaxAutoXdBtn" color="red">更新文件请点击</font> <a class="btn-view" href="javascript:void(0);" style="padding-left: 20px;" title="预览JSON表单布局">预览</a></span>
		</div>	
		<div class="control-group">
			<label class="control-label">租户：</label>
			<div class="controls">				
				<div id="xdTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</div>
		<input type="hidden" name="formName"  id="formName" value=""/>
		<input type="hidden" name="officeName"  id="officeName" value=""/>
		<div class="control-group">
			<label class="control-label">记录标识：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="init:sysInitFormtpl:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>