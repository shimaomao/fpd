<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="nid" value="upload_${param.nid}"/>
<!DOCTYPE html>
<html>
<head>
	<title>附件管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript">
		$(document).ready(function() {
			$("#${nid}btnSubmit").click(function (){
				var form = $("#${nid}inputForm");
				var fileName = $("#${nid}file").val();
				if(fileName == ""){
					showTip("请选择上传的文件");
				}else if(fileName.indexOf("xls")>=0||fileName.indexOf("png")>=0
						||fileName.indexOf("doc")>=0||fileName.indexOf("jpg")>=0
						||fileName.indexOf("pdf")>=0 ||fileName.indexOf("zip")>=0 
				        ){//>=0就表示有
					var fileType = '${tContractFiles.type}';
					//console.info("fileType="+fileType);
					if(fileType == "financial_product" && !(fileName.indexOf("jpg")>=0 || fileName.indexOf("png")>=0)){
						showTip("文件类型不支持!");
					}else{
						$("#${nid}btnSubmit").attr("disabled", "disabled");   
						loading('正在提交，请稍等...');
						closeLoading();
						form.ajaxSubmit({
							type: "post",
							dataType: "json",
							success: function(response, status) {
								$("#${nid}btnSubmit").removeAttr("disabled");
								closeLoading();
			            		//$("#submit").removeAttr("disabled");               	
			                	if(response.status==1){
			                		showTip("上传完成");
			                		$("#${nid}btnCancel").click();
			                	}else{
			                		showTip(response.message.value);
			                	}
			       			}
			           });
					}
				}else{
					showTip("文件类型不支持");
				}
			});
		});
		//点关闭按钮
	    $("#${nid}btnCancel").click(function(){
	    	//dialog.dialog("close");
	     	formClose($(this));
	    }); 
	</script>
</head>
	<div class="text-center" style="font-size: 12px; color: red; display: block; height: 20px; line-height: 20px; background-color: #eee; margin-bottom: 5px;">
	            附件大小不能大于10M
	      <c:choose><%-- #4498 修改 --%>
			<c:when test="${!empty tContractFiles.type && tContractFiles.type == 'financial_product'}">
				(支持：jpg和png(不透明)图片)
			</c:when>
			<c:otherwise>
	      		(支持：xls、png、doc、jpg、pdf、zip)
	      	</c:otherwise>
		  </c:choose>
	 </div>
	<form:form id="${nid}inputForm" action="${ctx}/files/tContractFiles/upload" method="post" 
		modelAttribute="tContractFiles" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<form:hidden path="taskId" />
		<form:hidden path="type"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">文件标题：</label>
			<div class="controls">
				<form:input id="${nid}title" path="title" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<input type="file" id="${nid}file" name="file" class="required"  style="width:300px"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="${nid}btnSubmit" class="btn btn-primary" type="button" value="上传"/>
			<input id="${nid}btnCancel" class="btn" type="button" value="关闭" />
		</div>
	</form:form>
	
</html>