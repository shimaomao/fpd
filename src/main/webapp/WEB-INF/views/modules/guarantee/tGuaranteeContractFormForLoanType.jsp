<%@page import="com.wanfin.fpd.common.config.Cons"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>添加担保信息</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		require(['helper/api'], function(api){
			$(function(){
				<c:if test="${isClose == 1}">				
					showTip("${message}");		
					top.$.jBox.close(true);					
				</c:if>
				
				var data = ${fns:toJson(data)};
				var form = $("#target");
				var btn = $("#submitViewForm");
				api.form.init(form, eval(data));
				form.attr("method", "post");
				var divWrap = form.find("#divWrap");
				if((divWrap == null) || ((divWrap != null) && (divWrap.length <= 0))){
					form.append("<div id=\"divWrap\"></div>");
					divWrap = form.find("#divWrap");
				}
				//divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 if(data.id==''||typeof(data.id)=='undefined'){
						divWrap.html('<input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }else{
						divWrap.html('<input type="hidden" name="id" value="'+data.id+'"><input type="hidden" name="modelId" value="${dfFormTpl.id}"><input type="hidden" name="modelType" value="step2">');
				 }
				
				//form.attr("target", "mainFrame");
				form.attr("action", ctx+"${action}");

				//添加关联业务的标签
				var $business = $('<div class="control-group"></div>');
				$business.append($('<input type="hidden" name="businessTable" value="t_loan_contract">'));				
				form.find("fieldset").append($business);	
				
				
				document.getElementById("cardNum").className="";
				$("#cardNum").addClass("input-xlarge");
				
				form.validate({
					submitHandler: function(form){
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
				$(btn).click(function(){
					form.submit();
				});
			});
		});
	</script>
</head>
<body>	
	<sys:message content="${message}"/>
	${dfFormTpl.parsehtml }
	
	<br/>
	<label class="control-label">&nbsp;<!-- 相关附件： --></label>
	<div id="${nid}filelist" class="controls" ></div>
	<br/>
	<div class="container">
		<div class="row clearfix">
			<div class="span12">
				<div class="clearfix" style="margin-bottom: 50px;">
					<input id="submitViewForm" type="button" class="btn btn-primary" value="保存" />
				
				</div>
			</div>
		</div>
	</div>
</body>
</html>
