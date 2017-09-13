var apiTool = {
	ajaxPost:function(apiModel){
		console.info("----------------------POST----------------------")
		if(!apiModel.data){
			apiModel.data = JSON.parse($("#"+apiModel.model+"Json").val());
		}
		$.ajax({ 
          type:"POST", 
          url:apiModel.url, 
          dataType:"json",      
          contentType:"application/json",           
          data:JSON.stringify(apiModel.data), 
          success:function(data){ 
              console.info(data);                  
          } 
       });
	},
	ajaxPut:function(apiModel, id){
		console.info("----------------------PUT----------------------")
		if(!apiModel.data){
			apiModel.data = JSON.parse($("#"+apiModel.model+"Json").val());
		}
		$.ajax({ 
          type:"PUT", 
          url:apiModel.url+id, 
          dataType:"json",      
          contentType:"application/json",           
          data:JSON.stringify(apiModel.data), 
          success:function(data){ 
              console.info(data);                  
          } 
       });
	},
	ajaxDelete:function(apiModel, id){
		console.info("----------------------DELETE----------------------")
		$.ajax({ 
          type:"DELETE", 
          url:apiModel.url+id,
          dataType:"json",      
          contentType:"application/json",      
          success:function(data){ 
              console.info(data);                  
          } 
       });
	}
};