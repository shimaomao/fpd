<%-- <%@ page contentType="text/html;charset=UTF-8" %> --%>
<%-- <%@ include file="/WEB-INF/views/include/taglib.jsp"%> --%>

<!-- <!DOCTYPE html> -->
<%-- <c:set var='nid' value="${empty param.nid ? 'guaranteeselect' : param.nid}" /> --%>
<%-- <c:set var='buttondisplay' value='none' /> --%>
<%-- <c:if test="${param.oper!='view'}"> --%>
<%--     <c:set var='buttondisplay' value='block' /> --%>
<%-- </c:if> --%>
<%-- <c:set var='height' value='${param.height }' /> --%>
<%-- <c:if test="${empty param.height}"> --%>
<%--     <c:set var='height' value='200' /> --%>
<%-- </c:if> --%>
<!-- <html> -->
<!--     <head> -->
<!--         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!--     </head> -->
<!--     <body> -->
<%--     	<form:form id="${nid}searchForm" modelAttribute="tGuaranteeContract" action="" method="post" class="breadcrumb form-search"> --%>
<!-- 		<ul class="ul-form"> -->
<!-- 			<li><label>证件号码：</label> -->
<%-- 				<input id="${nid}guaranteeNumber" value="" class="input-medium"> --%>
<!-- 			</li> -->
<!-- 			<li><label>担保人：</label> -->
<%-- 			<sys:treeselect id="${nid}guarantor" name="${nid}guarantorId" value="" labelName="${nid}guarantorName" labelValue="" --%>
<%-- 						title="客户" url="/company/tCompany/treeData" cssClass="" allowClear="true" notAllowSelectParent="true"/> --%>
<!-- 			</li> -->
<%-- 			<li class="btns"><input id="${nid}btnSubmit" class="btn btn-primary" type="button" value="查询"/></li> --%>
<!-- 			<li class="clearfix"></li> -->
<!-- 		</ul> -->
<%-- 		</form:form> --%>
<%-- 		<sys:message content="${message}"/> --%>
    
<%--     	<div id="gridTips_${nid}"></div> --%>
<%--         <div id="tableGrid_${nid}"> --%>
<%-- 	        <table id="grid_${nid}" class="${param.ng == 1 ? '' : 'GlobalGrid'}"></table> --%>
<%-- 	        <div id="gridPager_${nid}"></div> --%>
<%-- 	        <div class="layoutcontent"  style="display:${buttondisplay}"> --%>
<!-- 	        	<table class="table table-bordered"> -->
<!-- 					<tr><td> -->
<%-- 						<div class="text-right"><input type=button id="okButton_${nid}" value="确定" class="btn btn-primary"/> --%>
<%-- 			        	<input type=button id="closeButton_${nid}" value="关闭" class="btn"/></div> --%>
<!-- 					</td></tr> -->
<!-- 			    </table> -->
<!-- 	        </div> -->
<!--         </div> -->
<!--     <script type="text/javascript"> -->
//         function getpostdata_${nid}(){
// 			var data={
// 					"guaranteeNumber" : $("#${nid}guaranteeNumber").val(),
// 					"guarantorId" : $("#${nid}guarantorId").val(),
// 					"businessId":"null"
//             };
//          	return data;
// 		};
		

//         $(function(){        	
//         	//grid初始化
//         	var grid = $("#grid_${nid}").jqGrid({
//         		caption:"担保信息列表", //Grid名称
//                 mtype: "post",
//                 datatype: "json",
//                 rowNum: 5,
//                 viewrecords: true,
//                 height: '${height}',//290,
//                 rowList:"",
//                 //rowList:[10,20,30,50,100,200],
//                 autowidth: true,
//                 shirinkToFit:true,
//                 rownumbers: true,
//                 rownumWidth: 40,
//                 multiselect: true,
//                 multiboxonly: true,
//                 gridview: true,
//                 pager: $("#gridPager_${nid}"),
//                 url: '${ctx}/guarantee/tGuaranteeContract/jqgrid',
//         		jsonReader: {// 自定义json数据格式
//                     root: function (obj) {return obj.list;},
//                     page: function (obj) {return obj.pageNo;},
//                     total: function (obj) {return obj.last;},
//                     records: function (obj) {return obj.count;},
//                     subgrid: {root:obj.list},
//                     repeatitems: false
//                 },
//             	altRows : true,
//             	postData:getpostdata_${nid}(),
//                 sortname: "",
//                 sortorder: "asc",
//                 colModel: [
//                     { name: "id", index: "id", width: 0, hidden: true, search: false, sortable: false },
//                     { name: "guaranteeNumber", index: "guaranteeNumber", label: "保证合同编号", width: 200, hidden: false, search: false, sortable: true},
//                     { name: "contractDate", index: "contractDate", label: "担保日期", align:"center", width:50, hidden: false, search: false, sortable: true,formatter:"date",formatoptions: {srcformat:'Y-m-d H:i:s',newformat:'Y-m-d H:i:s'}},
//                     { name: "amount", index: "amount", label: "保证金额", width: 200, hidden: false, search: false, sortable: true},
//                     { name: "period", index: "period", label: "担保期限", width: 200, hidden: false, search: false, sortable: true},
//                     { name: "guarantorName", index: "guarantorName", label: "保证人", width: 200, hidden: false, search: false, sortable: true},
//                     { name: "guaranteeType", index: "guaranteeType", label: "保证方式", width: 200, hidden: false, search: false, sortable: true},
//                     { name: "relation", index: "relation", label: "与借款人的关系", width: 200, hidden: false, search: false, sortable: true}
//                 ],
//                 loadComplete: function(oj){ //加载完成（初始加载），回调函数
//                 }
// 		    }).navGrid('#gridPager_${nid}',{edit:false,add:false,del:false,search:false});
        	
//         	$("#${nid}btnSubmit").click(function(){
//         		grid.setGridParam({postData:getpostdata_${nid}()}).trigger("reloadGrid");
//         	});
        	
//         	$("#okButton_${nid}").click(function () {
//         		var ids = grid.jqGrid('getGridParam','selarrrow');
//         		if(ids == null || ids.length == 0 ){
//         			showTip("请选择数据");
//         			return;
//         		}
//         		loading('正在保存，请稍等...');
// 				$.post(
// 					'${ctx}/guarantee/tGuaranteeContract/relateContract',
// 					{ "ids": ids, "businessTable" : "${businessTable}", "businessId" : "${businessId}" },
// 					function(response, status, xhr) {
// 							closeLoading();
// 							showTip(response.message);
// 							if(response.status == 1){							
// 								$("#closeButton_${nid}").click();
// 							}else{
// 								$("#refresh_grid_${nid}").click();
// 							}
// 					}
// 				);
        		
//         	});
        	
//         	//点关闭按钮
//         	$("#closeButton_${nid}").click(function (){
//         		//dialog.dialog("close");
//     	     	formClose($(this));
//         	});
//         });
            

<!--     </script> -->
<!--     </body> -->
<!-- </html> -->
