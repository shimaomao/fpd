<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html>
<c:set var='nid' value="${empty param.nid ? 'guaranteelist' : param.nid}" />
<c:set var='buttondisplay' value='none' />
<c:if test="${param.oper!='view'}">
    <c:set var='buttondisplay' value='block' />
</c:if>
<c:set var='height' value='${param.height }' />
<c:if test="${empty param.height}">
    <c:set var='height' value='200' />
</c:if>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
    	<div id="gridTips_${nid}"></div>
        <div id="tableGrid_${nid}">
	        <table id="grid_${nid}" class="${param.ng == 1 ? '' : 'GlobalGrid'}"></table>
	        <div id="gridPager_${nid}"></div>
        </div>
    <script type="text/javascript">
    	function getpostdata_${nid}(){
			var data={
					"businessTable": "${businessTable}",
					"businessId": "${businessId}"
            };
         	return data;
		};
		

        $(function(){        	
        	//grid初始化
        	var grid = $("#grid_${nid}").jqGrid({
        		caption:"担保信息列表", //Grid名称
                mtype: "post",
                datatype: "json",
                rowNum: 5,
                viewrecords: true,
                height: '${height}',//290,
                rowList:"",
                //rowList:[10,20,30,50,100,200],
                autowidth: true,
                shirinkToFit:true,
                rownumbers: true,
                rownumWidth: 40,
                multiselect: true,
                multiboxonly: true,
                gridview: true,
                pager: $("#gridPager_${nid}"),
                url: '${ctx}/guarantee/tGuaranteeContract/jqgrid',
        		jsonReader: {// 自定义json数据格式
                    root: function (obj) {return obj.list;},
                    page: function (obj) {return obj.pageNo;},
                    total: function (obj) {return obj.last;},
                    records: function (obj) {return obj.count;},
                    subgrid: {root:obj.list},
                    repeatitems: false
                },
            	altRows : true,
            	postData: getpostdata_${nid}(),
                sortname: "",
                sortorder: "asc",
                colModel: [
                    { name: "id", index: "id", width: 0, hidden: true, search: false, sortable: false },
                    { name: "guaranteeNumber", index: "guaranteeNumber", label: "保证合同编号", width: 200, hidden: false, search: false, sortable: true},
                    { name: "contractDate", index: "contractDate", label: "担保日期", align:"center", width:200, hidden: false, search: false, sortable: true,
                    	formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},
                    { name: "amount", index: "amount", label: "保证金额", align:"center", width: 200, hidden: false, search: false, sortable: true},
                    { name: "period", index: "period", label: "担保期限", align:"center", width: 200, hidden: false, search: false, sortable: true},
                    { name: "guarantorName", index: "guarantorName", label: "保证人", align:"center", width: 200, hidden: false, search: false, sortable: true},
                    { name: "guaranteeType", index: "guaranteeType", label: "保证方式", align:"center", width: 200, hidden: false, search: false, sortable: true},
                    { name: "relation", index: "relation", label: "与借款人的关系", width: 200, hidden: false, search: false, sortable: true},
                    { name: "edit", index: "edit", label: "修改", align:'center', width: 80, hidden: false, search: false, sortable: true,
                    	formatter: function(cellvalue, options, rowdata){
                    		var url = "${ctx}/guarantee/tGuaranteeContract/form.html?"
        			    			+"id="+rowdata.id+"&businessTable=${businessTable}&businessId=${businessId}&nid=${nid}edit";
                    		return "<a href='javascript:void(0);' style='color:#f60' onclick='dialog_show_${nid}(\"" + url + "\",\"编辑担保信息\")'>修改</a>";
                    	}}
                ],
                loadComplete: function(oj){ //加载完成（初始加载），回调函数
                }
		    }).navGrid('#gridPager_${nid}',{
		    	edit:false,add:false,del:false,search:false
		    });
        	
        	<c:if test="${param.oper!='view'}">
        		//添加操作按钮
	        	grid.navButtonAdd('#gridPager_${nid}',{
			    	   caption:"删除", 
			    	   buttonicon:"ui-icon-trash", 
			    	   onClickButton: function(){ 
			    	      	grid_batch_${nid}();
			    	   }, 
			    	   position:"first"
			    }).navButtonAdd('#gridPager_${nid}',{
			    	   caption:"添加", 
			    	   buttonicon:"ui-icon-plus", 
			    	   onClickButton: function(){ 
			    	      dialog_show_${nid}("${ctx}/guarantee/tGuaranteeContract/form.html?"
			    			+"businessTable=${businessTable}&businessId=${businessId}&nid=${nid}new","添加担保信息");
			    	   }, 
			    	   position:"first"
			    });
        	</c:if>
        	
        });
            
        //删除
         function grid_batch_${nid}() {
             var tips = $("#gridTips_${nid}");
             var rows = $("#grid_${nid}").jqGrid('getGridParam', 'selarrrow');
             if (rows.length==0){
                 alert("请选择需要删除的担保信息");
                 return;
             }
            if(confirm("您确定要删除吗?")){
         	   	loading('正在删除，请稍等...');
				$.post(
					"${ctx}/looploan/tLoopLoan/remmoveBanding",
					{ "ids": rows, table : "t_guarantee_contract"},
					function(response, status, xhr) {
							closeLoading();
							showTip(response.message);
							$("#grid_${nid}").trigger("reloadGrid");
					}
				);
          	}
         }
         
 		
 		//显示对话栏
 		function dialog_show_${nid}(url, title) {
 			top.$.jBox.open("iframe:"+url, title,$(window).width()-100, $(top.document).height()-150,{
	    		buttons:{"保存":"save", "刷新":"refresh", "关闭":true}, 
	    		bottomText:"",
	    		submit:function(v, h, f){
	    			var ifrWin = h.find("iframe")[0].contentWindow;
	    			if(v=="refresh"){
	    				ifrWin.location.reload(true);
	    				return false;
	                }else if(v=="save"){
	                	ifrWin.btnSubmit.click();
	    				return false;
	                }
	    		}, closed:function(){
	    			$("#grid_${nid}").trigger("reloadGrid");
	    		}, loaded:function(h){
	    			$(".jbox-content", top.document).css("overflow-y","hidden");
	    		}
	    	});
 		};
    </script>
    </body>
</html>
