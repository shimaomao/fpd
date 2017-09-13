<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<c:set var='nid' value="${empty param.nid ? 'filelist' : param.nid}" />
<c:set var='caption' value="${empty param.caption ? '附件列表' : param.caption}" />
<c:set var='buttondisplay' value='none' />
<c:if test="${param.oper!='view'}">
    <c:set var='buttondisplay' value='block' />
</c:if>
<c:set var='width' value='${param.width }' />
<c:if test="${empty param.width}">
    <c:set var='width' value='1300' />
</c:if>
<c:set var='height' value='${param.height }' />
<c:if test="${empty param.height}">
    <c:set var='height' value='200' />
</c:if>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${ctxStatic}/jqGrid/4.6/css/ui.jqgrid.css" rel="stylesheet"/>
		<link href="${ctxStatic}/jqGrid/4.6/css/default/jquery-ui-1.8.2.custom.css" rel="stylesheet"/>
		<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jqGrid/4.6/js/jquery.jqGrid.extend.js" type="text/javascript"></script>
		<script src="${ctxStatic}/jquery/jquery-ui-1.8.18.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="${ctxStatic}/util.js"></script>
    </head>
    <body>
    	<div id="gridTips_${nid}"></div>
        <div id="tableGrid_${nid}">
	        <table id="grid_${nid}" class=""></table>
	        <div id="gridPager_${nid}"></div>
	        <%-- 
	        <div class="layoutcontent"  style="display:${buttondisplay}">
	        	<table class="table table-bordered">
					<tr><td>
						<div class="text-right"><input type=button id="addButton_${nid}" value="上传" class="btn btn-primary"/>
			        	<input type=button id="deleteButton_${nid}" value="删除" class="btn btn-danger"/></div>
					</td></tr>
			    </table>
	        </div> --%>
        </div>
    <script type="text/javascript">
    
    	function getdata_${nid}(){
			var businessid = '${businessid}';
			var businesstype = "${businesstype}";
			var data={
					"BUSINESSID":businessid,
					"BUSINESSTYPE":businesstype
            };
         	return data;
		};
		

        $(function(){        	
        	//grid初始化
        	var grid = $("#grid_${nid}").jqGrid({
        		caption:"${caption}", //Grid名称
                mtype: "post",
                datatype: "json",
                rowNum: 5,
                viewrecords: true,
                height: '${height}',//290,
                width:'${width}',//width:'1300',
                rowList:"",
                //rowList:[10,20,30,50,100,200],
                autowidth: false,
                shirinkToFit:true,
                rownumbers: true,
                rownumWidth: 40,
                multiselect: true,
                multiboxonly: true,
                gridview: true,
                pager: $("#gridPager_${nid}"),
                url: '${ctx}/files/tContractFiles/jqgrid',
        		jsonReader: {// 自定义json数据格式
                    root: function (obj) {return obj.list;},
                    page: function (obj) {return obj.pageNo;},
                    total: function (obj) {return obj.last;},
                    records: function (obj) {return obj.count;},
                    subgrid: {root:obj.list},
                    repeatitems: false
                },
            	altRows : true,
            	postData: getdata_${nid}(),
                sortname: "",
                sortorder: "asc",
                colModel: [
                    { name: "id", index: "id", width: 0, hidden: true, search: false, sortable: false },
                    { name: "filePath", index: "filePath", width: 0, hidden: true, search: false, sortable: false },
                    { name: "id", index: "id", width: 0, hidden: true, search: false, sortable: false },
                    { name: "title", index: "title", label: "附件标题", width: 200, hidden: false, search: false, sortable: true,
                        formatter: function(cellvalue, options, rowdata){
                            /* return '<a style="TEXT-DECORATION:underline" target="_blank" href="${pageContext.request.contextPath}'+rowdata.filePath+'?outFileName='+rowdata.title+'">'+rowdata.title+' </a>'; */
                            return '<a style="TEXT-DECORATION:underline" target="_blank" href="${pageContext.request.contextPath}/'+rowdata.filePath+'?outFileName='+rowdata.title+'&path='+rowdata.filePath+'">'+rowdata.title+' </a>';
                        }
                    },
                    { name: "updateDate", index: "updateDate", label: "上传日期", align:"center", width:200, hidden: false, search: false, sortable: true,editable:false,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}}
                ],
                loadComplete: function(oj){ //加载完成（初始加载），回调函数
                }
		    }).navGrid('#gridPager_${nid}',{edit:false,add:false,del:false,search:false});
        	

        	<c:if test="${param.oper!='view'}">
        		//添加操作按钮
	        	grid.navButtonAdd('#gridPager_${nid}',{
			    	   caption:"删除", 
			    	   height: '40',
			    	   buttonicon:"ui-icon-trash", 
			    	   onClickButton: function(){ 
			    	      	grid_batch_${nid}("${ctx}/files/tContractFiles/attachmentdelete");
			    	   }, 
			    	   position:"first"
			    }).navButtonAdd('#gridPager_${nid}',{
			    	   caption:"添加", 
			    	   buttonicon:"ui-icon-plus", 
			    	   onClickButton: function(){ 
			    	      dialog_show_${nid}("${ctx}/files/tContractFiles/form?taskId=${businessid}&type=${businesstype}&nid=${nid}","上传");
			    	   }, 
			    	   position:"first"
			    });
        	</c:if>
        	/* 
        	$("#addButton_${nid}").click(function(){
            	dialog_show_${nid}("${ctx}/files/tContractFiles/form?taskId=${businessid}&type=${businesstype}&nid=${nid}","上传");
            });
            $("#deleteButton_${nid}").click(function(){
                grid_batch_${nid}("${ctx}/files/tContractFiles/attachmentdelete");
            }); */
        	
        });
            
         function grid_batch_${nid}(url) {
             var tips = $("#gridTips_${nid}");
             var rows = $("#grid_${nid}").jqGrid('getGridParam', 'selarrrow');
             if (rows.length==0){
                 alert("请选择需要删除的附件");
                 return;
             }
            if(confirm("您确定要删除吗?")){
         	   loading('正在删除，请稍等...');
              $.post(
               url,
               { "ids": rows },
               function(response, status, xhr) {
               		closeLoading();
               		showTip(response.message);
               		$("#refresh_grid_${nid}").click();
               }
           	);
          	}
         }
         
 		
 		//显示对话栏
 		function dialog_show_${nid}(url, title) {
 			var width= 600;
 			var height=260;
 			dlgLoad({
                 title: title,
                 resizable: false,
                 width: width,
                 height:height,
                 position:"center",
                 close: function(){
					$("#refresh_grid_${nid}").click();
                 }
             },url,'');
 		};
 		
 	   $(function(){　　 
 		  document.getElementById("gridPager_${nid}").style.height = 40 + 'px';
   	  });　　
    </script>
    </body>
</html>
