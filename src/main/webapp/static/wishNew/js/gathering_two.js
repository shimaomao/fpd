
    function zShow(content, title, divid,width,height){
        var dialog = artDialog({
            id: 'zShow',
            title: title,
            fixed: true,
            lock: true,
            width:width,
            height:height,
            content: content,
            yesFn: false
        });

        $(divid+" .close").click(function(){
            dialog.close();
        });
    }
    
    function alertMsg(content,title,width,height){
    	var width = width?width:400,
    		title = title?title:'提示',
    		height = height?height:200;
		  var dialog = artDialog({
          id: 'zShow',
          title: title,
          fixed: true,
          lock: true,
          width:width,
          height:height,
          content: content,
          yesFn: false
      });

	}

   

