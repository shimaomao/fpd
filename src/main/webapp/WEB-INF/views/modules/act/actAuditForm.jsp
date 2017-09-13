<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务处理</title>
	<%@ include file="/WEB-INF/views/include-builder/head.jsp"%>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
	require(['helper/api'], function(api){
		$(function(){
			//初始化业务数据
			var data = ${fns:toJson(data)};
			var form = $("#target");
			api.form.init(form, eval(data));
			//禁用form表单中所有的选项
			disableForm("target",true);
			
			//页签
			$('.nav-tabs').find('li').click(function(){
				$(this).addClass('active').siblings().removeClass('active');
				$('.nav-tab-con').eq($(this).index()).show().siblings('.nav-tab-con').hide();
			});
			
			$("#inputForm").validate({
				submitHandler: function(form){
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
			
			$("#btnSubmit,#btnReBack").click(function(){
				var form = $("#inputForm");
				if(form.validate().form()){
					loading('正在提交，请稍等...');
					form.ajaxSubmit({
						type: "post",
						dataType: "json",
						success: function(result, status) {
							$("#${nid}btnSubmit").removeAttr("disabled");
							closeLoading();
							showTip(result);
							if(result == true){
								location.href = "${ctx}${empty endUrl ? '/act/task/todo/' : 'endUrl' }";
			            	}
			   			}
			       });
				}
			});
			
		});
		
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<!-- <li class="active"><a href="javasricpt:void(0);">业务信息</a></li>
		<li><a href="javasricpt:void(0);">申请/审批</a></li> -->
		<li class="active"><a href="#" onclick="javascript:return false;">业务信息</a></li>
		<li><a href="#" onclick="javascript:return false;">申请/审批</a></li> 
	</ul>
	<div class="nav-tab-con" >
		${dfFormTpl.parsehtml }
	</div>
	<div class="nav-tab-con" style="display:none;">
		<br/>
		<form:form id="inputForm" action="${ctx}/act/task/complete" method="post" class="form-horizontal">
			<input type="hidden" name="id" value="${act.id}"/>
			<input type="hidden" name="taskId" value="${act.taskId}"/>
			<input type="hidden" name="taskName" value="${act.taskName}"/>
			<input type="hidden" name="taskDefKey" value="${act.taskDefKey}"/>
			<input type="hidden" name="procInsId" value="${act.procInsId}"/>
			<input type="hidden" name="procDefId" value="${act.procDefId}"/>
			<input type="hidden" name="procDefKey" value="${act.procDefKey}"/>
			<input type="hidden" name="businessTable" value="${act.businessTable}"/>
			<input type="hidden" name="businessId" value="${act.businessId}"/>
			<input type="hidden" name="title" value="${act.title}"/>
			<input type="hidden" id="vars" name="vars.map['pass']" />
			<sys:message content="${message}"/>		
			
			<div class="control-group">
				<label class="control-label">您的意见：</label>
				<div class="controls">
					<textarea name="comment" class="required" rows="5" maxlength="20000" style="width:500px">${act.comment}</textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="同 意" onclick="$('#vars').val('1');"/>&nbsp;
				<input id="btnReBack" class="btn btn-inverse" type="submit" value="驳 回" onclick="$('#vars').val('0');"/>&nbsp;
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
			<act:histoicFlow procInsId="${act.procInsId}"/>
		</form:form>
	</div>
</body>
</html>