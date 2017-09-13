<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品初始化</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
    <style type="text/css">
	.srtBox{margin-top: 30px;}    
	.srtBox .bBox{height: 500px;border: 1px solid red;}    
	.srtBox .bBox .title{height: 30px;line-height: 30px;text-align: center;background-color:#eff;border-bottom: 1px solid red;}
	.srtBox .bBox .groups .group{height: 467px;overflow: auto;padding: 5px 5px;}
	.srtBox .sBox tr, .srtBox .rBox tr, .srtBox .tBox tr{cursor: pointer;}
	.table td.std{text-align: left;}
	.table td.ttd{text-align: right;}
    </style>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">产品中心&gt;</a>
				<a href="#">产品管理&gt;</a>
				<a href="#">产品关联&gt;</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box srtBox">
			<div class="col-md-3 col-sm-3">
				<div class="bBox">
					<div class="title">B产品列表</div>
					<div class="rows groups">
						<div class="col-md-12 group">
							<table class="sBox table table-striped table-condensed table-hover">
							<c:forEach items="${tProducts }" var="tproduct">
								<c:if test="${not empty tproduct.wtypeId }">
								<tr class="success" data-id="${tproduct.id}" data-name="${tproduct.name}" ondblclick="bindSBox(this)"><td>${tproduct.name }</td></tr>
								</c:if>
								<c:if test="${empty tproduct.wtypeId }">
									<tr data-id="${tproduct.id}" data-name="${tproduct.name}" ondblclick="bindSBox(this)"><td>${tproduct.name }</td></tr>
								</c:if>
							</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="bBox">
					<div class="title">关联列表</div>
					<div class="rows groups">
						<div class="col-md-12 col-sm-12 group">
							<table class="rBox table table-condensed table-hover">
							<c:forEach items="${tRProducts }" var="tproduct">
								<c:forEach items="${wRProducts }" var="wproduct">
									<c:if test="${wproduct.id eq tproduct.wtypeId }"> 
										<tr data-sid="${tproduct.id }" data-tid="${wproduct.id}" ondblclick="bindRBox(this)"><td class="std" width="49%">${tproduct.name }</td><td>|</td><td class="ttd" width="49%">${wproduct.loan_name }</td></tr>
									</c:if>
								</c:forEach>
							</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3 col-sm-3">
				<div class="bBox">
					<div class="title">W产品列表</div>
					<div class="rows groups">
						<div class="col-md-12 group">
							<table class="tBox table table-striped table-condensed table-hover">
							<c:forEach items="${wRProducts }" var="wproduct">
								<tr class="success" data-id="${wproduct.id}" data-name="${wproduct.loan_name}" ondblclick="bindTBox(this)"><td>${wproduct.loan_name }</td></tr>
							</c:forEach>
							<c:forEach items="${wNProducts }" var="wproduct">
								<tr data-id="${wproduct.id}" data-name="${wproduct.loan_name}" ondblclick="bindTBox(this)"><td>${wproduct.loan_name }</td></tr>
							</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		function bindRBox(dom){
			delRow($(dom));
		}
		function bindSBox(dom){
			$(dom).addClass("success");
			var curTr = getCurSRboxRow();
			if(curTr){
				updateRboxRow(curTr, $(dom).attr("data-id"), $(dom).attr("data-name"));
			}else{
				addRboxRow($(dom).attr("data-id"), $(dom).attr("data-name"), null, null);
			}
		}
		function bindTBox(dom){
			$(dom).addClass("success");
			var curTr = getCurTRboxRow();
			if(curTr){
				updateRboxRow(curTr, $(dom).attr("data-id"), $(dom).attr("data-name"));
			}else{
				addRboxRow(null, null, $(dom).attr("data-id"), $(dom).attr("data-name"));
			}
		}
		
		
		/* 新增行 */
		function addRboxRow(sid, sname, tid, tname){
			var data_status;
			var data_sid = sid;
			var data_tid = tid;
			if((data_sid == null) || (data_sid == undefined)){
				data_status = "w";
				sname = "";
			}
			if((data_tid == null) || (data_tid == undefined)){
				data_status = "b";
				tname = "";
			} 
			if(((data_sid != null) && (data_sid != undefined)) && ((data_tid != null) && (data_tid != undefined))){
				data_status = null;
			}
			
			$(".rBox").append(initRowTpl(data_status, sid, sname, tid, tname));
		}
		/* 初始化行模板*/
		function initRowTpl(data_status, sid, sname, tid, tname){
			if(data_status == null){
				return '<tr data-tid="'+tid+'" data_sid="'+sid+'" ondblclick="bindRBox(this)"><td class="std" width="49%">'+sname+'</td><td>|</td><td class="ttd" width="49%">'+tname+'</td></tr>';
			}else if(data_status == "b"){
				if(isHaveSRow(sid)){
					return '<tr data-tid="" data-sid="'+sid+'" data-status="'+data_status+'" class="error" ondblclick="bindRBox(this)"><td class="std" width="49%">'+sname+'</td><td>|</td><td class="ttd" width="49%"></td></tr>';
				}
			}else if(data_status == "w"){
				if(isHaveTRow(tid)){
					return '<tr data-tid="'+tid+'" data-sid="" data-status="'+data_status+'" class="error" ondblclick="bindRBox(this)"><td class="std" width="49%"></td><td>|</td><td class="ttd" width="49%">'+tname+'</td></tr>';
				}
			}
			return null;
		}
		function isHaveSRow(sid){
			var curTrs = $('.rBox tr[data-sid="'+sid+'"]');
			if((curTrs == null) || (curTrs.length == 0)){
				return true;
			}
			return false;
		}
		function isHaveTRow(tid){
			var curTrs = $('.rBox tr[data-tid="'+tid+'"]');
			if((curTrs == null) || (curTrs.length == 0)){
				return true;
			}
			return false;
		}
		/*获取Rbox的S为空的第一行*/
		function getCurSRboxRow(){
			var curTrs = $('.rBox tr[data-sid=""]');
			if((curTrs == null) || (curTrs.length <= 0)){
				return null;
			}else{
				return curTrs.first();
			}
		}
		/*获取Rbox的T为空的第一行*/
		function getCurTRboxRow(){
			var curTrs = $('.rBox tr[data-tid=""]');
			if((curTrs == null) || (curTrs.length <= 0)){
				return null;
			}else{
				return curTrs.first();
			}
		}
		/*获取Sbox的行*/
		function getCurSBoxRow(id){
			return getCurSTBoxRow(".sBox", id);
		}
		/*获取Tbox的行*/
		function getCurTBoxRow(id){
			return getCurSTBoxRow(".tBox", id);
		}
		/*获取STbox的行*/
		function getCurSTBoxRow(boxClazz, id){
			var curTrs = $(boxClazz+' tr[data-id="'+id+'"]');
			if((curTrs == null) || (curTrs.length != 1)){
				return null;
			}else{
				return curTrs;
			}
		}

		/*获取更新Rbox的S兄弟节点*/
		function updateSboxSlibingRow(dom, sid){
			var curSiblings = $(dom).siblings('.rBox tr[data-sid="'+sid+'"]');
			$(curSiblings).each(function(){
				$(this).attr("data-status", "w")
				$(this).attr("data-sid", "")
				$(this).find(".std").html("");
				$(this).addClass("error");
			});
			
		}
		/*获取更新Rbox的T兄弟节点*/
		function updateTboxSlibingRow(dom, tid){
			var curSiblings = $(dom).siblings('.rBox tr[data-tid="'+tid+'"]');
			$(curSiblings).each(function(){
				$(this).attr("data-status", "b")
				$(this).attr("data-tid", "")
				$(this).find(".ttd").html("");
				$(this).addClass("error");
			});
			
		}
		
		/* 修改行 */
		function updateRboxRow(curTr, id, name){
			var data_sid = $(curTr).attr("data-sid");
			var data_tid = $(curTr).attr("data-tid");
			var data_sname = $(curTr).find(".std").html();
			var data_tname = $(curTr).find(".ttd").html();
			var data_status = $(curTr).attr("data-status");
			if(data_status == "w"){
				data_sid = id;
				data_sname = name;
			}else if(data_status == "b"){
				data_tid = id;
				data_tname = name;
			}

			$.ajax({
				type: "POST",
				url:"${ctx}/product/tProduct/ajaxOption", 
				data:{"id":data_sid, "wtypeId":data_tid}, 
				success:function(data){
					if(data){
						if(data_status == "w"){
							$(curTr).attr("data-sid", id);
							$(curTr).find(".std").html(name);
							updateSboxSlibingRow(curTr, id);
						}else if(data_status == "b"){
							$(curTr).attr("data-tid", id);
							$(curTr).find(".ttd").html(name);
							updateTboxSlibingRow(curTr, id);
						}
						$(curTr).removeClass("error");
					}
				} 
			});
		}

		/* 删除RBox行 */
		function delRow(rdom){
			var sid = $(rdom).attr("data-sid");
			var tid = $(rdom).attr("data-tid");
			var url = "${ctx}/product/tProduct/ajaxDelOption";
			if((sid != null) && (sid != undefined)){
				delSboxRow(rdom, sid, url);
			}

			if((tid != null) || (tid != undefined)){
				delTboxRow(rdom, tid, url);
			}
		}
		/* 删除SBox行 */
		function delSboxRow(rdom, sid, url){
			var curSRow = getCurSBoxRow(sid);
			if(curSRow){
				$.ajax({
					type: "POST",
					url:url, 
					data:{"id":sid}, 
					success:function(data){
						if(data){
							curSRow.removeClass("success");
							delRboxRow(rdom);
						}else{
							curSRow.removeClass("success");
							curSRow.addClass("error");
						}
					} 
				});
			}
		}
		/* 删除TBox行 */
		function delTboxRow(rdom, tid, url){
			var curTRow = getCurTBoxRow(tid);
			if(curTRow){
				curTRow.removeClass("success");
				delRboxRow(rdom);
			}
		}
		/* 删除RBox行 */
		function delRboxRow(rdom){
			if(($(rdom) != null) && ($(rdom) != undefined)){
				$(rdom).remove();
			}
		}
	</script>
</body>
</html>