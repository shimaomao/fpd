<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
			checkCompany('${office.type}');
		});
		
		function checkCompany(typeid){
		  if(typeid==1){
			  $("#contractNumber").show();
			  $("#companyType").show();
			  $("#childOffice").show();
		  }else{
			  $("#contractNumber").hide();
			  $("#companyType").hide();
			  $("#childOffice").hide();
		  }  
		}
		
		function toCheck(){
			 var typeid = $("#type").val();
			 checkCompany(typeid);
			
			  var parentofficeid = $("#officeId").val();
			  if(parentofficeid==''){
                    alert("请先选择上级机构");
                    return;
			  }
			  $.ajax({
		         	type: "POST",
		         	url: "${ctx}/sys/office/getOfficeType",
		         	data: {id:$("#officeId").val()},
		         	dataType: "json",
		         	success: function(data){
		         		if(data>=2){//当前上级机构不是公司的时候，需要进行判断
		         			 var rootid = typeid;//选择的下级机构的类别
		         			if(rootid<=data){//上级机构的typeid必须大于当前选择的typeid，例如公司为1，部门为2，小组为3，其它为4，如果给2加下级，只能是选type为3或者4
		         			   $("#typemessage").html("<font color='red'>*机构类型选择错误,隐藏保存按钮，请重新选择！</font>");
		         			   $("#btnSubmit").hide();
		         			}else{
		         				 $("#typemessage").html("");
		         				 $("#btnSubmit").show();
		         			}
		         		}
		         	}
		       });
		}
		//检查登录名是否已存在
		function checkUserLoginName(){
			$.ajax({
	         	type: "POST",
	         	url: "${ctx}/sys/user/getLoginCheck",
	         	data: {loginName:$("#userLoginName").val()},
	         	dataType: "json",
	         	success: function(data){
					if(0==data){
						alert('该登录名已存在');
						$('#btnSubmit').attr("disabled","disabled"); 
					}else if(1==data){
						$('#btnSubmit').removeAttr("disabled"); 
					}
	         	}
	       });
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	<c:choose>
		<c:when test="${curUser.admin}">
		<li><a href="${ctx}/sys/office/list?id=${office.id}&parentIds=${office.parentIds}">机构列表</a></li>
		</c:when>
		<c:otherwise>
		<li><a href="${ctx}/sys/sysOfficeDetail/officeList">机构列表</a></li>
		</c:otherwise>
	</c:choose>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/sys/sysOfficeDetail/form?id=${sysOfficeDetail.id}&pid=${office.id}">机构详情${not empty sysOfficeDetail.id?'查看':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" target="mainFrame" class="form-horizontal">
		<form:hidden path="id"/>
		<c:if test="${empty office.id}">
			<c:if test="${curUser.admin}" >
				<input name="office.type" type="hidden" value="2"/>
				<input name="office.grade" type="hidden" value="2"/>
			</c:if>
			<c:if test="${!curUser.admin}" >
				<input name="office.primaryPerson" type="hidden"  value="${office.parent.primaryPerson}"/>
				<input name="office.type" type="hidden"  value="${office.parent.type+1}"/>
				<input name="office.grade" type="hidden" value="${office.parent.grade+1}"/>
				<input name="office.useable" type="hidden" value="1"/>
			</c:if>
		</c:if>
		<c:if test="${not empty office.id}">
			<c:if test="${curUser.admin}" >
				<input name="office.type" type="hidden"  value="${office.type}"/>
				<input name="office.grade" type="hidden"  value="${office.grade}"/>
			</c:if>
			<c:if test="${!(curUser.admin eq 'true')}" >
				<input name="office.primaryPerson" type="hidden"  value="${office.primaryPerson}"/>
				<input name="office.grade" type="hidden"  value="${office.grade}"/>
				<input name="office.useable" type="hidden" value="${office.useable}"/>
			</c:if>
		</c:if>
		
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div class="controls">
                <sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="" allowClear="${office.currentUser.admin}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/><span class="help-inline"><font color="red">*</font> 区域不能为空</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div class="controls">
				<c:set var="curUser" value="${fns:getUser()}" ></c:set>
				<c:set var="sysofficetypeDicts" value="${fns:getDictList('sys_office_type')}"></c:set>
					<form:select path="type" class="input-medium" onchange="toCheck();">
						<!-- 公司类型   1担保   2、贷款 -->		
						<c:forEach var="temp" items="${sysofficetypeDicts}">
							<c:if test="${(not empty temp.value)}">
								<c:if test="${empty office.id}">
									<c:if test="${curUser.admin && (office.parent.type le temp.value)}" >
					                    <form:option value="${temp.value}" label="${temp.label}"/>						
									</c:if>
									<c:if test="${!curUser.admin && (office.parent.type lt temp.value)}" >
					                    <form:option value="${temp.value}" label="${temp.label}"/>						
									</c:if>
								</c:if>
								<c:if test="${not empty office.id}">
									<c:if test="${curUser.admin && (office.parent.type ge temp.value)}" >
					                    <form:option value="${temp.value}" label="${temp.label}"/>						
									</c:if>
									<c:if test="${!curUser.admin && (office.type eq temp.value)}" >
					                    <form:option value="${temp.value}" label="${temp.label}"/>						
									</c:if>
								</c:if>
		                   	</c:if>
	                 	</c:forEach>
		           	</form:select>
				<%-- </c:if> --%>
				<%-- <form:select path="type" class="input-medium" onchange="toCheck();"> --%>
					<!-- 公司类型   1担保   2、贷款 -->		
					<%-- <c:if test="${curUser.admin != true}" >
						<c:forEach var="temp" items="${sysofficetypeDicts}">
							<c:if test="${(not empty temp.value)}"> --%>
								<%-- <c:if test="${office.grade == '2'}" >
			                        <c:if test="${temp.value =='1'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
			                         <c:if test="${temp.value =='2'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
			                        <c:if test="${temp.value =='3'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
								</c:if>	 --%>
								
								<%-- <c:if test="${office.grade < temp.value}" ><!-- Bug #3948 -->
			                    	<form:option value="${temp.value}" label="${temp.label}"/>	
								</c:if>	 --%>
								
								<%-- <c:if test="${(office.type-1) == '1'}" >
							         <c:if test="${temp.value =='2'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
			                        <c:if test="${temp.value =='3'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
								</c:if>
								<c:if test="${(office.type-1) == '2'}" >
			                        <c:if test="${temp.value =='3'}"><!-- Bug #3948 -->
			                         	<form:option value="${temp.value}" label="${temp.label}"/>		
			                         </c:if>
								</c:if> --%>	
							<%-- </c:if>	
	                  	</c:forEach>
					</c:if>
					<c:if test="${curUser.admin}">
						<c:forEach var="temp" items="${sysofficetypeDicts}">
	                        <form:option value="${temp.value}" label="${temp.label}"/>						
	                 	 </c:forEach>
					</c:if>
				</form:select> --%>
				<span class="help-inline" id="typemessage"></span>
			</div>
		</div>
		<c:if test="${curUser.admin }">
			<div class="control-group" id="companyType" style="display: none">
				<label class="control-label">公司类型:</label>
				<div class="controls">
					<form:select path="primaryPerson" class="input-medium">
						<form:options items="${fns:getDictList('company_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">例如：担保；小贷</span>
				</div>
			</div>
		</c:if>
		<div class="control-group" id="contractNumber" style="display: none">
			<label class="control-label">合同编号设定规则:</label>
			<div class="controls">
				<form:input path="contractNumber" htmlEscape="false" maxlength="30" onchange="toCheck();"/>
				<span class="help-inline">例如：创新小贷year年第number号，year和number是必带的</span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">机构级别:</label>
			<div class="controls">
				<form:select path="grade" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<c:if test="${curUser.admin }">
			<div class="control-group">
				<label class="control-label">是否可用:</label>
				<div class="controls">
					<form:select path="useable">
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
					<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
				</div>
			</div>
		</c:if>
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">主负责人:</label>   字段改为公司类型   担保  小贷 -->
<!-- 			<div class="controls"> -->
<%-- 				 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}" --%>
<%-- 					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 		<div class="control-group"> -->
<!-- 			<label class="control-label">副负责人:</label> -->
<!-- 			<div class="controls"> -->
<%-- 				 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}" --%>
<%-- 					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</div> -->
<!-- 		</div> -->
		<c:if test="${empty office.name}">
		<c:set var="curUser" value="${fns:getUser()}"></c:set>
		<c:if test="${curUser.admin}">
		<div class="control-group">
			<label class="control-label">租户管理员姓名:</label>
			<div class="controls">
				<input id="userName" name="userName" type="text" maxlength="50" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">租户管理员登录名:</label>
			<div class="controls">
				<input id="userLoginName" name="userLoginName" type="text" maxlength="50" onchange="checkUserLoginName()"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="userPassword" name="userPassword" type="password" value="" type="text" maxlength="50" minlength="3" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmUserPassword" name="confirmUserPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#userPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</c:if>
		</c:if>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人:</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"  onchange="toCheck();"/><font color="red">*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<c:if test="${empty office.id}">
			<div class="control-group" id="childOffice" style="display: none">
				<label class="control-label">快速添加下级部门:系统默认添加综合部、财务部、业务部、风控部门</label>
				<%-- <div class="controls">
					<form:checkboxes path="childDeptList" items="${fns:getDictList('sys_office_common')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div> --%>
			</div>
		</c:if>
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>