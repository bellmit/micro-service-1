<%@page import="com.module.admin.prj.enums.PrjInfoContainer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑生成源码</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="id" value="${codeCreate.id}">
		<input type="hidden" id="code" value="${param.code}">
  		<div class="form-group">
			<input type="text" class="form-control" id="packagePath" placeholder="功能包路径" value="${codeCreate.packagePath}">
		</div>
  		<div class="form-group">
			<my:select id="dsCode" headerKey="" headerValue="请选择数据源" items="${dsList}" value="${codeCreate.dsCode}" cssCls="form-control"/>
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="dbName" placeholder="数据库名或sid" value="${codeCreate.dbName}">
		</div>
		<div class="text-right"><small><a href="javascript:info.loadTables()">加载所有表</a></small></div>
  		<div class="form-group" id="tablePanel">
		</div>
		<hr/>
  		<div class="form-group">
 			<div class="btn-group">
				<button type="button" id="saveBtn" class="btn btn-success enter-fn">保存</button>
			</div>
			<span id="saveMsg" class="label label-danger"></span>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	var info = {
			loadTables: function() {
				var _saveMsg = $('#saveMsg').empty();
				_saveMsg.attr('class', 'label label-danger');
				var _dsCode = $('#dsCode');
				if(JUtil.isEmpty(_dsCode.val())) {
					_saveMsg.append('请输选择数据源');
					_dsCode.focus();
					return;
				}
				var _dbName = $('#dbName');
				if(JUtil.isEmpty(_dbName.val())) {
					_saveMsg.append('请输入数据库或sid的名称');
					_dbName.focus();
					return;
				}
				var _tablePanel = $('#tablePanel').empty();
				JUtil.ajax({
					url : '${webroot}/codeCreate/f-json/findTables.shtml',
					data : { dsCode: _dsCode.val(), prjId: '${param.prjId}',dbName: _dbName.val() },
					success : function(json) {
						if (json.code === 0) {
							var _conts = ['<hr/><div>'];
							$.each(json.body, function(i, obj) {
								_conts.push('<div class="col-sm-4"><label><input type="checkbox" name="tables" value="',obj,'"> <small>',obj,'</small></label></div>');
							});
							_conts.push('</div>');
							_tablePanel.append(_conts.join(''));

							var _orgTables = '${codeCreate.tables}';
							_orgTables = _orgTables.split(",");
							$('input[name="tables"]').each(function(i, obj) {
								$.each(_orgTables, function(j, ot) {
									if(ot == $(obj).val()) {
										$(obj).attr('checked', 'checked');
									}
								});
							});
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
	};
	$(function() {
		if(JUtil.isNotEmpty($('#id').val())) {
			info.loadTables();
		}
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			_saveMsg.attr('class', 'label label-danger');

			var _packagePath = $('#packagePath');
			if(JUtil.isEmpty(_packagePath.val())) {
				_saveMsg.append('请输入功能包路径');
				_packagePath.focus();
				return;
			}
			var _dsCode = $('#dsCode');
			if(JUtil.isEmpty(_dsCode.val())) {
				_saveMsg.append('请输选择数据源');
				_dsCode.focus();
				return;
			}
			var _dbName = $('#dbName');
			if(JUtil.isEmpty(_dbName.val())) {
				_saveMsg.append('请输入数据库或sid的名称');
				_dbName.focus();
				return;
			}

			var _tables = [];
			$('input[name="tables"]:checked').each(function(i, obj) {
				_tables.push($(obj).val());
			});
			if(_tables.length == 0) {
				_saveMsg.append('请选择要生成的表');
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/codeCreate/f-json/save.shtml',
				data : {
					id: $('#id').val(),
					code: $('#code').val(),
					packagePath: _packagePath.val(),
					dsCode: _dsCode.val(),
					dbName: _dbName.val(),
					tables: _tables.join(',')
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						setTimeout(function() {
							parent.create.loadInfo(1);
							parent.dialog.close();
						}, 800);
					}
					else if (json.code === -1)
						_saveMsg.append(JUtil.msg.ajaxErr);
					else
						_saveMsg.append(json.message);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>