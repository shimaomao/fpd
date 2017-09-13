<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
    String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
	String basePath = basePath1+"/";
	
	String styleName = (String) session.getAttribute("styleName");
	styleName = (styleName==null)?"public_index.css":styleName;
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>产品管理</title>
    <%-- <script type="text/javascript" src="<%=basePath%>/js/jquery-1.8.2.js"></script> --%>
    <script type="text/javascript" src="<%=basePath%>/static/jquery/jquery-1.8.3.min.js"></script>
    <style type="text/css">
      body{margin-left: 0px;margin-top: 0px;}
      #targets{width: 1000px;padding: 0px;}
      #targets li{list-style-type:none;padding: 0px;border: 0px; margin: 0px;background: gray;}
      #targets img{padding: 0px;border: 0px; margin: 0px;}
    </style>
</head>
<body>
  <ul id="targets">
     
  </ul>
  
  <div class="" style="position:absolute; top: 8px;left: 8px;height: 100px; width: 100px;z-index: 2; cursor: url(images/cursor.cur),auto;" id="img_div">
      
  </div>
  <div id="images">
     <ul>
        
     </ul>
  </div>
</body>
<script type="text/javascript">
var etag = "";
var oldTime = 0;
var images =  eval('(' + '${images}' + ')');
    if(images.length>0){
       for(i=0;i<images.length;i++){
          var img = images[i];
          var name = img.name;
          var sname = img.name.length>6?img.name.substr(0,6)+"..":name;
          var id = img.id;
          var url = img.url;
          var path = img.path;
          var productId = img.productId;
          //var dpath = "<%=basePath1%>/product/toDelEditimg.html?productId="+productId+"&id="+id;
          var childPath = img.childPath;
          var childSize = parseInt(img.childSize);
          $("#images ul").append('<li><a href="javascript:" url="<%=basePath1%>'+url+'" path="'+path+'"  childPath="'+childPath+'" childSize="'+childSize+'"  fileid="'+id+'" >'+sname+'</a>'+
          '&nbsp;&nbsp;</li>');
       }
       $("#images a").click(function(){
    	   $("#targets").html("");
    	   var url = $(this).attr("url");
    	   //url = url.substring(0,url.lastIndexOf("."));
    	   var childPath = $(this).attr("childPath");
    	   var childSize = parseInt($(this).attr("childSize"));
    	   //for(i=0;i<childSize;i++){
    		   //$("#targets").append('<img alt="" src="'+url+'/'+i+'.jpg" >');
    		   //$("#targets").append('<img alt="" src="'+url+'.jpg" >');
    		   $("#targets").append('<img alt="" src="'+url+'" >');
           //}
           $("#targets").attr("src",$(this).attr("url"));
           $("#targets").attr("path",$(this).attr("path"));
           $("#targets").attr("fileid",$(this).attr("fileid"));
           $("#targets").attr("childPath",$(this).attr("childPath"));
           
           $("#images a").css("color","#000");
           $(this).css("color","red")
           $("#img_div").css({"height":$("#targets").height()+"px","width":$("#targets").width()+"px"})
       })
       $("#images a").eq(0).click();
       f = function(){
    	   $("#img_div").css({"height":$("#targets").height()+"px","width":$("#targets").width()+"px"})
    	   $("#images").css({position:"absolute",top:(document.body.scrollTop+200)+"px",right:"0px"}); 
       }
       f();
       $(window).scroll(function(e) { 
           f();
       }); 
    }else{
        $("#targets").remove();
        $("body").html("没有需要处理的图片。");
    }
$(window).load(function() {
   $("#img_div").css({"height":$("#targets").height()+"px","width":$("#targets").width()+"px"})
   var i = 0;
   var xy = "";
   $("#img_div").mousemove(function(event){
        if (event.which == 1) {
        	xy += event.pageX + ", " + event.pageY + "|";
	        i++;
	    }
	}).mousedown(function(){
		i = 0;
		xy = "";
    }).mouseup(function(event){
        var param = {};
        param.xy = xy;
        var lastY = event.pageY;
        var index = parseInt(lastY/500);
        
        oldTime = new Date().getTime();
        
        $.ajax({url:"<%=basePath%>/a/files/tContractFiles/editimg?path="+$("#targets").attr("path")+"&childPath="+$("#targets").attr("childPath")+"&index="+index+"&id="+$("#targets").attr("fileid")+"&xy="+xy,success:function(img){
        	$("#targets img").eq(index).attr("src","data:image/png;base64,"+img);
        }})
    });
   
});

</script>
</html>