<%@page import="com.module.admin.prj.enums.PrjInfoContainer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑脚本</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="pvsId" value="${prjVersionScript.pvsId}">
		<input type="hidden" id="prjId" value="${param.prjId}">
		<input type="hidden" id="version" value="${param.version}">
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="名称/描叙" value="${prjVersionScript.remark}">
		</div>
		<div class="form-group">
			<my:select id="dsCode" headerKey="" headerValue="请选择数据源" items="${dsList}" value="${prjVersionScript.dsCode}" cssCls="form-control" />
		</div>
		<div class="form-group">
			<textarea class="form-control" rows="5" id="upSql" placeholder="升级的sql语句，多条用;分隔">${prjVersionScript.upSql}</textarea>
		</div>
		<div class="form-group">
			<textarea class="form-control" rows="5" id="callbackSql" placeholder="回滚的sql语句，多条用;分隔">${prjVersionScript.callbackSql}</textarea>
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
	};
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			_saveMsg.attr('class', 'label label-danger');
			
			var _remark = $('#remark');
			if(JUtil.isEmpty(_remark.val())) {
				_saveMsg.append('请输入名称或描叙');
				_remark.focus();
				return;
			}
			var _dsCode = $('#dsCode');
			if(JUtil.isEmpty(_dsCode.val())) {
				_saveMsg.append('请选择数据源');
				_dsCode.focus();
				return;
			}
			var _upSql = $('#upSql');
			if(JUtil.isEmpty(_upSql.val())) {
				_saveMsg.append('请输入升级的sql语句');
				_upSql.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/prjVersionScript/f-json/save.shtml',
				data : {
					pvsId: $('#pvsId').val(),
					prjId: $('#prjId').val(),
					version: $('#version').val(),
					remark: _remark.val(),
					dsCode: _dsCode.val(),
					upSql: _upSql.val(),
					callbackSql: $('#callbackSql').val()
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						setTimeout(function() {
							parent.info.loadInfo(1);
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