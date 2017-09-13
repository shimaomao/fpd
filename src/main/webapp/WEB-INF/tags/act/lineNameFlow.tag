<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="taskId" type="java.lang.String" required="true" description="任务ID"%>
<fieldset>
	<!-- <legend>节点按钮</legend> -->
	<div id="lineName">
		正在加载节点按钮信息...
	</div>
</fieldset>
<script type="text/javascript">
	$.get("${ctx}/act/task/lineNameList?taskId=${taskId}", function(data){
		$("#lineName").html(data);
	});
	
</script>