<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>小贷财务报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
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
		});
	</script>
</head>
<body>	

<ul class="nav nav-tabs">
		<li><a href="${ctx}/catipal/tCaiwu/caiwuUpload">小货财务数据导入</a></li>
	
	</ul><br/>
	<form id="inputForm"  action="${ctx}/catipal/tCaiwu/save" method="post" enctype="multipart/form-data" class="form-horizontal" >		
		<sys:message content="${message}"/>			
		
	
		
		<table class="table-form">	
							
	      <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
             
             <td   style="width: 60px;"></td>
          
    		 <td  >所属月份：<input id="caiwu.baoDate" name="baoDate"  maxlength="20" type="text" value="${caiwu.baoDate}" class="Wdate" readonly="readonly" onfocus="WdatePicker({dateFmt: 'yyyy-MM'})" /></td>
            <td  colspan="2">附件: </td> 
            <td >
            	 <c:if test="${caiwu.filePath eq null}"> </c:if>
				    	<input id="file" name="file" class="inputbox set_form_text" maxlength="200" type="file" >	 
				    	<span style="color: red">请上传excel版本</span>			  
            
            </td>  
		 </tr>  
		 
		   <tr style="text-align:center;line-height: 23px;" class="tit_left_bg">
             
          
          
    		 <td  colspan="5">
    		 
		<div class="form-actions">
			<shiro:hasPermission name="catipal:tCaiwu:edit"></shiro:hasPermission><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
    		 </td>
           
		 </tr>  	
		
		
	</form>
</body>
</html>