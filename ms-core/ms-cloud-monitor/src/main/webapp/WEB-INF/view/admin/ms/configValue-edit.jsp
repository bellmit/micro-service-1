<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/sys.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-配置文件属性管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="ms"/>
			<jsp:param name="second" value="msConfigManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">微服务 / 配置文件管理 / <b>配置文件属性管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  	</div>
						  	<div class="btn-group">
						  		<a href="${webroot}/msConfig/f-view/manager.shtml" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel">
						<input type="hidden" id="configId" value="${param.configId}">
						<table class="table table-striped table-hover">
							<thead>
							<tr class="info">
	                        <th>key</th>
	                        <th>value</th>
	                        <th>备注</th>
	                        <th width="150">操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        	<c:forEach items="${values}" var="info">
	                        	<tr>
	                        		<td><input type="text" name="code" class="form-control input-sm" placeholder="key" value="${info.code}"/></td>
	                        		<td><input type="text" name="value" class="form-control input-sm" placeholder="值" value="${info.value}"/></td>
	                        		<td><input type="text" name="remark" class="form-control input-sm" placeholder="描叙" value="${info.remark}"/></td>
	                        		<td><a href="javascript:;" class="btn btn-link btn-sm" onclick="info.del(this)">删除</a></td>
	                        	</tr>
	                        	</c:forEach>
							</tbody>
						</table>
						<div align="center">
							<a href="javascript:info.add()" class="btn btn-default">新增属性</a>
							<a href="javascript:info.save()" class="btn btn-success">确认保存</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/page.jsp"></jsp:include>
<script type="text/javascript">
var info = {
		//编辑
		add : function() {
			var _table = $('#infoPanel').find('table');
			_table.append(['<tr>',
                   		'<td><input type="text" name="code" class="form-control input-sm" placeholder="key" value=""/></td>',
                		'<td><input type="text" name="value" class="form-control input-sm" placeholder="值" value=""/></td>',
                		'<td><input type="text" name="remark" class="form-control input-sm" placeholder="描叙" value="暂无"/></td>',
                		'<td><a href="javascript:;" class="btn btn-link btn-sm" onclick="info.del(this)">删除</a></td>',
                	'</tr>'].join(''));
		},
		del : function(_this) {
			if(confirm('您确定要删除该属性吗?')) {
				$(_this).parent().parent().remove();
			}
		},
		save : function() {
			var _code = [];
			$('input[name="code"]').each(function(i, obj) {
				_code.push($(obj).val());
			});
			var _value = [];
			$('input[name="value"]').each(function(i, obj) {
				_value.push($(obj).val());
			});
			var _remark = [];
			$('input[name="remark"]').each(function(i, obj) {
				_remark.push($(obj).val());
			});
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/msConfigValue/f-json/save.shtml',
				data : {
					configId: $('#configId').val(),
					code: _code.join('#~@#'),
					value: _value.join('#~@#'),
					remark: _remark.join('#~@#'),
					isUse: _remark.join('#~@#'),
					regex: '#~@#'
				},
				success : function(json) {
					if (json.code === 0) {
						message('保存成功');
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		}
};
$(function() {
});
</script>
</body>
</html>