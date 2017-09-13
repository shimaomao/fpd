<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
			<!-- RoleUser 测试================================================== -->
			<div class="span4 module">
				<div class="top-box">RoleUser角色用户</div>
				<script type="text/javascript">
				  	$(function(){
				  		var roleUserModel = {
					   		Post:{
					   		  model:"roleUser",
					   		  url:"${ctxApi}/api/roles/783658d6788244f7849e05e6e2f1a180/users"
					   		},
					   	  	Put:{
					   		  model:"roleUser",
					 	   	  url:"${ctxApi}/api/users/"
				   			},
					   	   	Delete:{
					   	  	  url:"${ctxApi}/api/users/"
					   		}
					   	 };
				  		$("#btnRoleUserPost").click(function(){apiTool.ajaxPost(roleUserModel.Post);});
				  		$("#btnRoleUserPut").click(function(){ apiTool.ajaxPut(roleUserModel.Put, $("#roleUserId").val());});
				  		$("#btnRoleUserDelete").click(function(){ apiTool.ajaxDelete(roleUserModel.Delete, $("#roleUserId").val());});
				  	});
				</script>
				<div class="box-tom">
                   	<div class="inbox">
                   		 <a id="btnRoleUserPost" class="btna co1">post</a>
                            <a id="btnRoleUserPut" class="btna co2">put</a>
                            <a id="btnRoleUserDelete" class="btna co3">delete</a>
                            <hr/>
                            <div class="cont-box"><input class="ids" id="roleUserId" type="text" value="" placeholder="ID值" /></div>
                            <div class="cont-box">
	                            <textarea class="jsons" id="roleUserJson" placeholder="新增Json值" >{"no":"1","mobile":"1","photo":"1","loginFlag":"1","delFlag":"1","office":{"id":"1"},"password":"1","company":{"id":"1"},"phone":"1","loginName":"1","loginIp":"1","name":"1","userType":"1","email":"1","remarks":"1"}</textarea>
				  			</div>
                        </div>
                    </div>
			</div>