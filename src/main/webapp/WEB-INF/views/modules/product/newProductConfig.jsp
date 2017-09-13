<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品业务功能配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/util.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {			
			/* $("#saveBtn").bind("click",function(){
				alert("新增");
			}) */		
		})
		
	    function toSubmit(){
	 	   var daiqian = "";
	 	   var daizhong = "";
	 	   var daihou = "";
	 	   
	 	   //获取贷前的选择的数据	 	   
		  if($("#loanDiv_1 ul").length > 0){	 		
	 		 $.each($("#loanDiv_1 ul"), function(i, v){		 			
	    		var checkedId = $(this).children("li").children("input").attr("id");
	    		var temp = checkedId.split("-")[1];
	    		if(daiqian == ""){
	    			daiqian = temp;
	    		}else{
	    			daiqian = daiqian+","+temp;
	    		}
	         });
	 	   }
	 	   
	 	   //获取贷中选择的数据
	 	  if($("#loanDiv_2 ul").length > 0){
	 		 $.each($("#loanDiv_2 ul"), function(i, v){		 			
	    		var checkedId = $(this).children("li").children("input").attr("id");
	    		var temp = checkedId.split("-")[1];
	    		if(daizhong == ""){
	    			daizhong = temp;
	    		}else{
	    			daizhong = daizhong+","+temp;
	    		}
	         });
	 	   }
	 	   
	 	   //获取贷后选择的数据
	 	 //获取贷中选择的数据
	 	  if($("#loanDiv_3 ul").length > 0){
	 		 $.each($("#loanDiv_3 ul"), function(i, v){		 			
	    		var checkedId = $(this).children("li").children("input").attr("id");
	    		var temp = checkedId.split("-")[1];
	    		if(daizhong == ""){
	    			daizhong = temp;
	    		}else{
	    			daizhong = daizhong+","+temp;
	    		}
	         });
	 	   }
	 	   
	 	   $("#qian").val(daiqian);
	 	   $("#zhong").val(daizhong);
	 	   $("#hou").val(daihou); 
	 	   
    	   document.forms[0].submit();
	    }
	    
	    function listEvent(dom){
	    	var idStr = dom.id;	    	
	    	var id = idStr.substr(4,idStr.length);	  
	    	var item=[{ name:"贷前管理",id:"1"},{name:"贷中管理",id:"2"},{name:"贷后管理",id:"3"}] ;
	    	var data= eval(item);
	    	for(var i=0;i<data.length;i++){
	    		//alert(data[i].id);
	    	}	    	
	    	//判断业务管理流程的那块Div 可以显示 需要隐藏
	    	var landDivId = "loanDiv_"+id;
	    	if($("#"+landDivId) != undefined){
	    		$.each($("div[id^='loanDiv_']"), function(i, v){  
		    		  if(landDivId == this.id){
		    			  $("#"+this.id).show();
		    		  }else{
		    			  $("#"+this.id).hide();
		    		  }
		         });
	    	}
	    	
	    	//动态分配管理业务流程的div块（默认:loanDiv_  +id）
	    	
	    	
	        //给管理业务流程 h3加值  如：loanDiv_p_1
	        $("#loanManageDiv span").attr("id","loanManageDiv_"+id);	
	    	if($("#loanManageDiv_"+id) != undefined){
	    		var divTitle = $("#loanManageDiv_"+id).text();
	    		if(divTitle.indexOf("(") >= 0){
	    			$("#loanManageDiv_"+id).text("管理业务流程("+$("#"+idStr).text()+")");
	    		}else{
	    			$("#loanManageDiv_"+id).text($("#loanManageDiv_"+id).text()+"("+$("#"+idStr).text()+")");
	    		}
	    		
	    	}
	    	
	        //初始化新建业务流程的数据
	    	//如果已经加载过一次   就不需要再次请求后台加载
            if($("#addLoan_div_s").has("ul").length > 0){            	
            	if($("#check_ul_"+id) != undefined){
            		$('#addLoan_div_s').empty();   
            		var productId = $("#productId").val();
            		//异步请求后台返回JSON数据
            		$.ajax({
						type:"POST",
						dataType:"json",
						contentType:"application/json;charset=utf-8",
						url:"${ctx}/product/tProduct/ajaxProduct",
						data: {productId: productId},
						async:false,  //false表示同步
						success:function(result){
						   var executerDiv= $("#addLoan_div_s");
	             		   executerDiv.innerHTML="";
	             		   var ul=window.document.createElement("ul");
	             		   ul.setAttribute("id","check_ul_"+id);
						   for(var j=0;j<result.length;j++){
							    if(id == result[j][8]){
							    	 // 加入复选框
		             		        var checkBox=document.createElement("input");
		             		        checkBox.setAttribute("type","checkbox");
		             		        checkBox.setAttribute("id","check_"+id+"-"+result[j][0]);
		             		        checkBox.setAttribute("value", result[j][1]);
		             		        checkBox.setAttribute("onclick","selectBox(this)");

		             		        var li=document.createElement("li");
		             		        li.setAttribute("style","list-style-type :none;line-height:24px;font-size: 16px");
		             		        li.appendChild(checkBox);     
		             		        li.appendChild(document.createTextNode(result[j][1]));
		             		       
		             		        ul.appendChild(li); 
							    }else{
							    	 // 加入复选框
		             		        var checkBox=document.createElement("input");
		             		        checkBox.setAttribute("type","checkbox");
		             		        checkBox.setAttribute("id","check_"+id+"-"+result[j][0]);
		             		        checkBox.setAttribute("value", result[j][1]);
		             		        checkBox.setAttribute("disabled", "disabled");
		             		        checkBox.setAttribute("onclick","selectBox(this)");

		             		        var li=document.createElement("li");
		             		        li.setAttribute("style","list-style-type :none;line-height:24px;font-size: 16px");
		             		        li.appendChild(checkBox);     
		             		        li.appendChild(document.createTextNode(result[j][1]));
		             		       
		             		        ul.appendChild(li); 
							    }
	             		       
						   }
						   executerDiv.append(ul);	 
		                
					     },
						error:function(XMLHttpRequest,textStatus,errorThrown){
						    alert(errorThrown);
						},
						
				    });
            	}  	
	    	}else{    		
	    		var productId = $("#productId").val();
        		//异步请求后台返回JSON数据
        		$.ajax({
					type:"POST",
					dataType:"json",
					contentType:"application/json;charset=utf-8",
					url:"${ctx}/product/tProduct/ajaxProduct",
					data: {productId: productId},
					async:false,  //false表示同步
					success:function(result){
					   var executerDiv= $("#addLoan_div_s");
             		   executerDiv.innerHTML="";
             		   var ul=window.document.createElement("ul");
             		   ul.setAttribute("id","check_ul_"+id);
					   for(var j=0;j<result.length;j++){	
						   if(id == result[j][8]){
							   // 加入复选框
	             		        var checkBox=document.createElement("input");
	             		        checkBox.setAttribute("type","checkbox");
	             		        checkBox.setAttribute("id","check_"+id+"-"+result[j][0]);
	             		        checkBox.setAttribute("value", result[j][1]);
	             		        checkBox.setAttribute("onclick","selectBox(this)");

	             		        var li=document.createElement("li");
	             		        li.setAttribute("style","list-style-type :none;line-height:24px;font-size: 16px");
	             		        li.appendChild(checkBox);     
	             		        li.appendChild(document.createTextNode(result[j][1]));
	             		       
	             		        ul.appendChild(li); 
						   }else{
							   // 加入复选框
	             		        var checkBox=document.createElement("input");
	             		        checkBox.setAttribute("type","checkbox");
	             		        checkBox.setAttribute("id","check_"+id+"-"+result[j][0]);
	             		        checkBox.setAttribute("value", result[j][1]);
	             		        checkBox.setAttribute("disabled", "disabled");
	             		        checkBox.setAttribute("onclick","selectBox(this)");

	             		        var li=document.createElement("li");
	             		        li.setAttribute("style","list-style-type :none;line-height:24px;font-size: 16px");
	             		        li.appendChild(checkBox);     
	             		        li.appendChild(document.createTextNode(result[j][1]));
	             		       
	             		        ul.appendChild(li);
						   }
             		      
					   }
					   executerDiv.append(ul);	 
	                
				     },
					error:function(XMLHttpRequest,textStatus,errorThrown){
					    alert(errorThrown);
					},
					
			    });
	    		
	    	}	    	   	
	    }
	    
	    function loanManage(dom){
	    	var idStr = dom.id;
	    	var id = idStr.substr(14,idStr.length);	
	    	
	    	//显示新建业务流程的模块 addLoan_div
	    	//$("#addLoan_div").hide();	
	    	if($("#addLoan_div").is(":hidden")){
	    		$("#addLoan_div").show();
	    	}
	    	
	    	//如果已经加载过一次   就不需要再次请求后台加载
            if($("#addLoan_div_s").has("ul").length > 0){            	
            	if($("#check_ul_"+id) != undefined){
            		$('#addLoan_div_s').empty();            		
            		var checkItem=[{ name:"受理质询",id:"1"},{name:"创建用户",id:"2"},{name:"业务申请",id:"3"}] ;
         	    	var data= eval(checkItem);
         	    	if(data.length > 0){
             			var executerDiv= $("#addLoan_div_s");
             		    executerDiv.innerHTML="";
             		    var ul=window.document.createElement("ul");
             		    ul.setAttribute("id","check_ul_"+id);
             		    for(var i=0;i<data.length;i++){
             		        var arr=data[i];

             		        // 加入复选框
             		        var checkBox=document.createElement("input");
             		        checkBox.setAttribute("type","checkbox");
             		        checkBox.setAttribute("id","check_"+id+"-"+arr.id);
             		        checkBox.setAttribute("value", arr.name);
             		        checkBox.setAttribute("onclick","selectBox(this)");

             		        var li=document.createElement("li");
             		        li.setAttribute("style","list-style-type :none;");
             		        li.appendChild(checkBox);     
             		        li.appendChild(document.createTextNode(arr.name));
             		       
             		        ul.appendChild(li);        
             		    }   
             		    executerDiv.append(ul);	    		
         	    	}	   
            	}  	
	    	}else{    		
	    		var checkItem=[{ name:"受理质询",id:"1"},{name:"创建用户",id:"2"},{name:"业务申请",id:"3"}] ;
    	    	var data= eval(checkItem);
    	    	if(data.length > 0){
        			var executerDiv= $("#addLoan_div_s");
        		    executerDiv.innerHTML="";
        		    var ul=window.document.createElement("ul");
        		    ul.setAttribute("id","check_ul_"+id);
        		    for(var i=0;i<data.length;i++){
        		        var arr=data[i];

        		        // 加入复选框
        		        var checkBox=document.createElement("input");
        		        checkBox.setAttribute("type","checkbox");
        		        checkBox.setAttribute("id","check_"+id+"-"+arr.id);
        		        checkBox.setAttribute("value", arr.name);
        		        checkBox.setAttribute("onclick","selectBox(this)");
        		        

        		        var li=document.createElement("li");
        		        li.setAttribute("style","list-style-type :none;");
        		        li.appendChild(checkBox);     
        		        li.appendChild(document.createTextNode(arr.name));
        		       
        		        ul.appendChild(li);        
        		    }   
        		    executerDiv.append(ul);	    		
    	    	}	
	    		
	    	}
	    }
	    
	    function selectBox(dom){	    	
	    	//根据选择的ID 动态的给管理业务流程赋值选项
	    	var idStr = dom.id;	    	    	
	    	var id = idStr.substring(idStr.indexOf("_") + 1,idStr.indexOf("-")); 
	    	var checkId = idStr.split("-")[1];	    	
	    	
	    	//loanDiv_
	    	if($("#loanDiv_"+id) != undefined){
	    		
	    		$.each($("div[id^='loanDiv_']"), function(i, v){  
	    			  var temp = "loanDiv_"+id;
		    		  if(temp == this.id){
		    			  $("#"+this.id).show();
		    		  }else{
		    			  $("#"+this.id).hide();
		    		  }
		         });
	    		
	    		if($("#"+dom.id).is(":checked")){
	    			var bulidCheckId = "checked_"+id+"-"+checkId;
	    			if($("#"+bulidCheckId).size() > 0){
	    				
	    			}else{
	    				
	    				//"checked_"+id+"-"+checkId
	    				//创建选定的复选框
			    		var executerDiv= $("#loanDiv_"+id);
		    		    executerDiv.innerHTML="";
		    		    var ul=window.document.createElement("ul");
		    		    //ul.setAttribute("id","check_ul_"+id);
		    		    
		   		        // 加入复选框
		   		        var checkBox=document.createElement("input");
		   		        checkBox.setAttribute("type","checkbox");
		   		        checkBox.setAttribute("id","checked_"+id+"-"+checkId);
		   		        checkBox.setAttribute("value", $("#"+idStr).val());
		   		       // checkBox.setAttribute("onclick","selectBox(this)");
		   		        

		   		        var li=document.createElement("li");
		   		        li.setAttribute("style","list-style-type :none;font-size: 16px");
		   		        li.appendChild(checkBox);     
		   		        li.appendChild(document.createTextNode($("#"+idStr).val()));
		   		       
		   		        ul.appendChild(li);        
		    		       
		    		    executerDiv.append(ul);	 
	    			}
		    	}	
	    	}	    
	    }
	    
	    function del(){
	    	confirm("你确定删除吗？");
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
		
		.list_table {
		  padding-left: 0;
		  margin-bottom: 20px;
		}
		.list_gropu_item {
		  position: relative;
		  display: block;
		  padding: 10px 15px;
		  margin-bottom: -1px;
		  background-color: #fff;
		  border: 1px solid #ddd;
		}
		.list_gropu_item:first-child {
		  border-top-left-radius: 4px;
		  border-top-right-radius: 4px;
		}
		.list_gropu_item:last-child {
		  margin-bottom: 0;
		  border-bottom-right-radius: 4px;
		  border-bottom-left-radius: 4px;
		}
	</style>
</head>
<body>
	<div class="content_block">
		<div class="ax_ico"><img src="${ctxStatic}/images/pz.png" width="20px" height="20px"/></div>
		<div class="ax_title">产品业务流程配置-(产品名称：${tProduct.name})</div>
		<div style="clear:left;"></div>
	</div>
	<div id="addYewuMoKuai" style="padding-left: 45px;">
	    <input type="text" placeholder="新增业务模块名称" style="font-size: 15px;"/> 
	    <button class="btn btn-xs btn-primary" id="saveBtn" style="float:left;">
           <span class="lbl">新增</span>
         </button>
     </div> 
			
	<form:form id="inputForm" action="${ctx}/product/tProduct/depLoysecount" method="post">
		<input type="hidden" id="productId" value="${tProduct.id}" name="id">
		<input type="hidden" name="qian" id="qian" >
		<input type="hidden"  name="zhong" id="zhong" >
		<input type="hidden"  name="hou" id="hou" >
		
		<div class="container-fluid" style="height: 561px;">
		 <div class="row padding-left-6" >
			 <div class="span4 ">	
			    <span style="padding-left: 40px;line-height: 44px;"><font color="black" size="3px;">新增业务模块</font></span>
			    <br/>		  
			    <!-- 贷前管理  贷中管理   贷后管理 -->
			    <ul id="guanli" class="list_table Offsets" style="padding-top: 10px;">
			       <li class="list_gropu_item" >
			         <font  size="3px"> 
			           <span id="dev_1" onclick="listEvent(this)">贷前管理</span>
			         </font>
			         <i class="icon-trash" onclick="del()" style="font-size: 20px; float:right;"  aria-hidden="true"></i>			        
			       </li>			       			      
			       <li class="list_gropu_item" >
			         <font  size="3px"> 
			           <span id="dev_2" onclick="listEvent(this)">贷中管理</span>
			         </font>
			         <i class="icon-trash" onclick="del()" style="font-size: 20px; float:right;"  aria-hidden="true"></i>			        
			       </li>
			        <li class="list_gropu_item" >
			         <font size="3px"> 
			           <span id="dev_3" onclick="listEvent(this)">贷后管理</span>
			         </font>
			         <i class="icon-trash" onclick="del()" style="font-size: 20px; float:right;"  aria-hidden="true"></i>			        
			       </li>	
			    </ul>
			 </div>
			 
			 <div class="span4" id="loanManageDiv">
			     <span style="font-size: 16px;line-height: 40px;">管理业务流程</span>
			    <!--贷前div  -->
			    <div id="loanDiv_1" style="padding-top: 20px;">
			    			    
			    </div>
			     <!--贷中div  -->
			    <div id="loanDiv_2" style="padding-top: 20px;">
			    
			    </div>
			     <!--贷后div  -->
			    <div id="loanDiv_3" style="padding-top: 20px;">
			    			    
			    </div>			    			 
			 </div>
			 
			 <!--新建业务流程  -->
			 <div class="span6" id="addLoan_div"  style="font-size: 20px;">
			   <font  size="3px"> 
	             <span  onclick="">新建业务流程</span>
	           </font>
			   <input type="text" placeholder="新增业务流程" style="margin-top: 4px;font-size: 15px;"/> 
			   <i class="icon-trash" onclick="del()" style="font-size: 20px; float:right;margin-top: 12px;"  aria-hidden="true"></i>			        
			   <div id="addLoan_div_s">			         
			   
			   </div>		     
			 </div>
		 </div>
	    </div>
	    	
	    <div style="padding-left: 40px;">
			<shiro:hasPermission name="product:tProduct:edit">
			 <input id="btnSubmit" class="btn btn-primary"  onclick="toSubmit();"  value="下一步"/>&nbsp;
			</shiro:hasPermission>
		     <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	
	</form:form>
</body>
</html>