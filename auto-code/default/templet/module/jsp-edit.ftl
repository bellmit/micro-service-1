<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/sys.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑信息</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
<div class="easyui-panel edit-panel" title="编辑信息" iconCls="icon-edit" fit="true">
<form id="editForm${table.className}" method="post">
	<input type="hidden" name="${table.firstKColumn.fieldName}" value="${r"${"}${table.beanName}.${table.firstKColumn.fieldName}}" />
	<table class="table">
	<#list table.columns as column>
		<tr>
			<td class="t_title">${column.comments}：</td>
			<td class="t_cont">
				<input type="text" name="${column.fieldName}" value="${r"${"}${table.beanName}.${column.fieldName}${r"}"}" class="easyui-validatebox" required="true">
				<span class="red">*</span>
			</td>
		</tr>
	</#list>
		<tr>
			<td colspan="2" class="t_operate">
				<a href="javascript:;" id="${table.beanName}SubmitBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="$('#editForm${table.className}').submit()">保存</a>
				<a href="${r"${webroot}"}/${table.beanName}/f-view/manager.shtml" class="easyui-linkbutton" iconCls="icon-back">返回</a>
			</td>
		</tr>
	</table>
</form>
</div>
<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
<script type="text/javascript">
$(document).ready(function () {
	window.setTimeout(function(){
		JUtil.form({
			id: 'editForm${table.className}',
			url: '${r"${webroot}"}/${table.beanName}/f-json/save.shtml',
			subbtn: '${table.beanName}SubmitBtn',
			success : function(json) {
				if(json.code === 0) {
				   	show('操作成功!');
				   	setTimeout(function() {
				   		location = '${r"${webroot}"}/${table.beanName}/f-view/manager.shtml';
				   	}, 1000);
				}
				else if(json.code === -1) show('操作失败!');
				else show(json.message);
			}
		});
	},100);
});
</script>
</body>
</html>