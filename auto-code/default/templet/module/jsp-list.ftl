<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/sys.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${table.className}</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="easyui-layout">
	<div id="${table.beanName}Panel"></div>
	<div id="tb" class="dg_toolbar">
		<span>名称:</span>
		<input type="text" id="${table.beanName}Name"/>
		<a href="javascript:void(0);" onclick="${table.beanName}.query()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<label>|</label>
		<a href="javascript:void(0);" onclick="${table.beanName}.add()" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="javascript:void(0);" onclick="${table.beanName}.edit()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
	</div>
<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
<script type="text/javascript">
var ${table.beanName} = {
		panel : '${table.beanName}Panel',
		//查询
		query : function() {
			$('#'+${table.beanName}.panel).datagrid({
			    queryParams: {
			    	'name': $('#${table.beanName}Name').val()
			    },
				pageNumber: 1
			});
		},
		//新增
		add : function() {
			location = '${r"${webroot}"}/${table.beanName}/f-view/edit.shtml';
		},
		//编辑
		edit : function() {
			var _id = '';
			var _obj = $('#'+${table.beanName}.panel).datagrid('getSelected');
			if (_obj) {
				_id = _obj.${table.firstKColumn.fieldName};
	        } else {
	        	alert('请选择记录');
	        	return;
	        }
			location = '${r"${webroot}"}/${table.beanName}/f-view/edit.shtml?${table.firstKColumn.fieldName}=' + _id;
		},
		//删除
		del : function() {
			var _obj = $('#'+${table.beanName}.panel).datagrid('getSelected');
			if(!_obj) {
				alert('请选择记录');
				return;
			}
			confirm('您确定要删除吗?', function() {
				JUtil.ajax({
					url: webroot + '/${table.beanName}/f-json/delete.shtml',
					data: {id: _obj.id},
					success: function(json) {
						if(json.code === 0) {
							alert('删除成功');
							${table.beanName}.query();
						}
						else if(json.code === -1) alert('操作失败');
						else alert(json.message);
					}
				});
			});
		}
};
$(document).ready(function () {
	$('#'+${table.beanName}.panel).datagrid({
		//title:'${table.className} Manager',
	    //iconCls:'icon-save',
	    nowrap: true,
	    autoRowHeight: false,
	    striped: true,
	    fitColumns: false,
	    collapsible:true,
	    url:'${r"${webroot}"}/${table.beanName}/f-json/pageQuery.shtml',
	    remoteSort: false,
	    singleSelect: true,
	    /*frozenColumns:[
	    	[
	            {title:'操作',field:'operate',width:150,sortable:true,align:'center',
	    			formatter:function(value, rec) {
	    				var _operate = JUtil.btn.update({click: '${table.beanName}.edit('+rec.id+', \'修改\')'});
	    				return _operate;
                	}
	    		}
			]
	    ],*/
	    columns:[
	    	[
	    		<#list table.columns as column>
		        {field:'${column.fieldName}',title:'${column.comments}',sortable:true,width:100<#if (column.fieldName == "addtime")>,formatter:function(value, rec){ return JUtil.date.formatStr(value); }</#if>}<#if column_has_next>,</#if>
		        </#list>
			]
		],
	    pagination:true,
	    rownumbers:true,
	    toolbar:'#tb'
	});
});
</script>
</body>
</html>