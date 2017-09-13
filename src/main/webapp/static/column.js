/**
 * 管理中增加属性的js工具
 * 
*/



/**
 * 动态增加属性
 * @param tableName表称
*/
function addDyColumn(tableName){
   //显示层
   var chineseName = $("#chineseName");
   if(chineseName.val()==""){
       $("#message").html("<font color='red'>请输入属性名称!</font>");
       return;
   }
   var flag = confirm("请确定是否要增加属性: "+chineseName.val());
   alert(flag);
	if(flag){
		  alert(flag);
		$.ajax({
		url : "${ctx}/product/tProduct/addColumn",
		type : 'POST',
		dataType : 'html',
		data: {"tableName":tableName,"chineseName":chineseName.val()},  
		timeout : 10000,
		error : function() {
		},
		success : function(content) {
			if(content=="exist"){
			  $("#message").html("<font color='red'>已经存在相同的属性名！</font>");
			  return;
			}else if(content=="error"){
			   $("#message").html("<font color='red'>添加错误！</font>");
			   return;
			}else{debugger;
			   $("#message").html("<font color='green'>添加成功！</font>");
			   document.getElementById("extendColumn-Div").innerHTML = "扩展属性"+content ;
			   $("#chineseName").val("") ;
			}
			
		 }
	  });
	}
}

//清除消息
function hideMessage(){
   cleanDivMsg('message-Delete');
   cleanDivMsg('message');
}

/**
 * 显示要删除的属性
 * @param tableName表称
*/
function showExtendColumn(tableName){
    $("#toDropColumnDiv").html("");
    showDiv('dropColumnDiv','none');
    //显示层
	$.ajax({
		url : "${basePath}/customer_ExtendColumn!showExtendColumns.do",
		type : 'POST',
		dataType : 'xml',
		data: {"extendColumn.tableName":tableName},  
		timeout : 10000,
		error : function() {
		},
		success : function(xml) {
		    var content = "";
			$(xml).find("extendColumn").each(function() {
				var chineseName = $(this).children("chineseName").text();
				var columnName = $(this).children("columnName").text();
				//动态构造要删除的属性
				content+="<input type='checkbox' onclick='hideMessage();' name='toDropColumn' value='"+columnName+"'>"+chineseName;
			});
			if(content==""){
			  $("#msgDiv").html("<font color='red'>没有可删除的属性！</font>");
			  return;
			}else{
			   showDiv('dropColumnDiv','display');
			   showDiv('msgDiv','none');
			   showDiv('addColumnDiv','none');
			   $("#toDropColumnDiv").html(content);
			}
			
		}
	});
}


/**
 * 删除扩展属性
 * @param tableName表称
*/
function dropColumn(tableName){
	var array = getCheckValue("toDropColumn");
	 if(array.length==0){
    	$("#message-Delete").html("<font color='red'>请选择要删除的属性！</font>");
    	return;
    }
    var columns = "";//组装要删除的字段名称
    for(var i=0;i<array.length;i++){
       if(i==array.length-1){
          columns+=array[i];
       }else{
          columns+=array[i]+",";
       }
    }
	var flag = confirm("请确定要删除这"+array.length+"个属性?");
	if(flag){
	   $.ajax({
		url : "${basePath}/customer_ExtendColumn!dropColumn.do",
		type : 'POST',
		dataType : 'html',
		data: {"extendColumn.tableName":tableName,"extendColumn.columnName":columns},  
		timeout : 10000,
		error : function() {
		
		},
		success : function(content) {
			if(content=="error"){
			   $("#message").html("<font color='red'>删除错误！</font>");
			   return;
			}else{
			   $("#message").html("<font color='green'>删除成功！</font>");
			   if(content!=undefined && content!="" && content!=null){
			        $("#extendColumn-Div").html("扩展属性"+content);
			   }else{
			        $("#extendColumn-Div").html("");
			   }
			   //重新加载可以显示的删除字段
			   showExtendColumn(tableName);
			}
		}
	});
	}
}

