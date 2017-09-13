<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" import="com.wanfin.fpd.common.config.Cons.FileType" pageEncoding="UTF-8"  %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人客户管理</title>
	<meta name="decorator" content="default"/>
    <link href="${ctxStatic}/echarts/2.2.7/doc/asset/css/bootstrap.css" rel="stylesheet">
    <link href="${ctxStatic}/bootstrap/switch/bootstrapSwitch.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/modules/productConfig/css/common.css"/>
	<script src="${ctxStatic}/arttemplate/3.0/template.js"></script>
	<script src="${ctxStatic}/bootstrap/switch/bootstrapSwitch.js"></script>
	<style type="text/css">.sinput input{margin-top: -2px;}</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="ptag">
				<a href="#">系统设置&gt;</a>
				<a href="#">征信管理&gt;</a>
				<a href="#">个人客户</a>
			</div>
		</div>
	</div>
	<div class="box-down" style="margin-top: 20px;">
		<div class="box">
			<div class="col-md-12" >
				<div class="panel panel-default">
					<div class="panel-heading">客户信息</div>
					<div class="panel-body">
						<div class="col-md-6">
							<div class="list-group">
							  <div>客户姓名：${tEmployee.name }</div>
							  <div>客户性别：${fns:getDictLabel(tEmployee.sex, 'sex', '')}</div>
							  <div>身份证号：${tEmployee.cardNum }</div>
							  <div>客户来源：${fns:getDictLabel(tEmployee.customerSource, 'customer_source', '')}</div>
							</div>
						</div>
						<div class="col-md-6" >
							<div class="list-group">
							  <div>手机号码：${tEmployee.mobile }</div>
							  <div>联系电话：${tEmployee.telephone }</div>
							  <div>户籍地址：${tEmployee.householdRegAddr }</div>
							  <div>现住地址：${tEmployee.currentLiveAddress }</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-12" >
				<div class="panel panel-default">
					<div class="panel-heading">数据源 <span class="pull-right">* 开通数据源可能会产生服务费</span></div>
					<div class="panel-body" id="dbDiv"></div>
				</div>
			</div>
		</div>
	</div>
	 
	<script id="dbTpl" type="text/html">
	<div class="panel-group" id="db-accordion">
		{{each list}}
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title"><a data-toggle="collapse" data-parent="#db-accordion" href="#db-{{$value.key}}">{{$value.name}}</a><span  class="switch switch-mini pull-right"><input class="ic_db" name="{{$value.key}}" type="checkbox" {{if $value.status}}checked{{/if}} /></span></h4>
				</div>
				<div id="db-{{$value.key}}" class="panel-collapse collapse {{if $value.status}}in{{/if}}">
					<div class="panel-body">
						<div id="{{$value.key}}-groupDiv" class="col-md-12" ></div>
					</div>
				</div>
			</div>
		{{/each}}
	</div>
	</script>
	
	<script id="groupTpl" type="text/html">
		<div class="panel-group" id="groups-accordion-{{key}}">
		{{each groups}}
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title"><a data-toggle="collapse" data-parent="#groups-accordion-{{$value.pkey}}" href="\#groups-{{$value.pkey}}-{{$value.key}}">{{$value.name}}</a><span  class="switch switch-mini pull-right"><input class="ic_group" name="{{$value.key}}" type="checkbox" {{if $value.status}}checked{{/if}} /></span></h4>
				</div>
				<div id="groups-{{$value.pkey}}-{{$value.key}}" class="panel-collapse collapse {{if $value.status}}in{{/if}}">
					<div id="{{key}}-{{$value.key}}-itemsDiv" class="panel-body"></div>
				</div>
			</div>
		{{/each}}
		</div>
	</script>
	
	<script id="itemTpl" type="text/html">
	<div class="panel-group" id="items-accordion-{{key}}-{{name}}">
		{{each items}}
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title"><a data-toggle="collapse" data-parent="#items-accordion-{{key}}-{{$value.pkey}}" href="\#items-{{pkey}}-{{$value.pkey}}-{{$value.key}}">{{$value.name}}</a><span  class="switch switch-mini pull-right"><input class="ic_item" name="{{$value.key}}" type="checkbox" {{if $value.status}}checked{{/if}} /></span></h4>
				</div>
				<div id="items-{{pkey}}-{{$value.pkey}}-{{$value.key}}" class="panel-collapse collapse ">
					<div id="{{pkey}}-{{$value.pkey}}-{{$value.key}}-adaptersDiv" class="panel-body"></div>
				</div>
			</div>
		{{/each}}
	</div>
	</script>
	
	<script id="adapterTpl" type="text/html">
		{{each vo.result.data.cmaps as value index}}
			<div class="col-md-4">
				<span>{{value.remark}}</span>&nbsp;:&nbsp;
				<span>{{value.val}}</span>
			</div>
		{{/each}}
	</script>
	
	<script type="text/javascript">
		$(function(){
			loadDB('${tEmployee.id }');
		});
		function loadDB(id){
			$.ajax({
				type: 'POST',
			 	url:"${ctx}/adapter/ajaxDB",
			    success:function(data){
			    	/*加载模板*/
					$('#dbDiv').html(template("dbTpl", {list: data}));
					/* $(data).each(function(){
						if(this.status){
							loadGroup(this.key, id);
						}
					}); */

					/*绑定事件*/
					$('#dbDiv .switch').bootstrapSwitch();
					$(".ic_db").change(function(){
						if($(this).prop("checked")){
							loadGroup($(this).attr("name"), id);
						}
					});
			    }
			});
		}
		function loadGroup(db, id){
			$.ajax({
				type: 'POST',
			 	url:"${ctx}/adapter/ajaxGroup",
			 	data:{"type":"person", "db":db},
			    success:function(data){
			    	/*加载模板*/
					$('#'+db+'-groupDiv').html(template("groupTpl", data));
					/* $(data).each(function(){
						$(this.groups).each(function(){
							if(this.status){
								loadItem(this.pkey, this.key, id);
							}
						});
					}); */
					
					/*绑定事件*/
					$('#'+db+'-groupDiv .switch').bootstrapSwitch();
			    	$(".ic_group").change(function(){
						if($(this).prop("checked")){
							loadItem(db, $(this).attr("name"), id);
						}
					});
					
			    }
			});
		}
		function loadItem(db, type, id){
			$.ajax({
				type: 'POST',
			 	url:"${ctx}/adapter/ajaxItem",
			    data:{"db":db, "type":type} ,
			    success:function(data){
			    	/*加载模板*/
			    	$('#'+db+'-'+type+'-itemsDiv').html(template("itemTpl", data));
			    	/* $(data).each(function(){
			    		$(this.items).each(function(){
							if(this.status){
								loadAdapter(this.db, this.pkey, this.key, id);
							}
						});
					}); */
			    	
					/*绑定事件*/
					$('#'+db+'-'+type+'-itemsDiv .switch').bootstrapSwitch();
					
					$('.ic_item').change(function(){
						if($(this).prop("checked")){
							loadAdapter(db, type, $(this).attr("name"), id);
						}
					});
			    }
			});
		}
		function loadAdapter(db, type, sub, id){
			$.ajax({
				type: 'POST',
			 	url:"${ctx}/adapter/ajaxAdapter",
			    data:{"db":db,"type":type,"sub":sub,"id":id},
			    success:function(data){
			    	if(data.vo.result.status){
				    	$('#'+db+'-'+type+'-'+sub+'-adaptersDiv').html(template("adapterTpl", data));
			    	}else{
				    	$('#'+db+'-'+type+'-'+sub+'-adaptersDiv').html(data.vo.result.msg);
			    	}
			    }
			});
		}
	</script>
</body>
</html>