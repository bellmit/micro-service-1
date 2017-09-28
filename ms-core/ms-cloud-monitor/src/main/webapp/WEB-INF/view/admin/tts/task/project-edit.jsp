<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-编辑项目</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-lg">
		<input type="hidden" id="id" value="${taskProject.id}">
  		<div class="form-group">
			<label for="name" class="col-sm-4">名称 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="name" placeholder="名称" value="${taskProject.name}"></div>
		</div>
  		<div class="form-group">
			<label for="remark" class="col-sm-4">描述</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="描述" value="${taskProject.remark}"></div>
		</div>
		<div class="form-group">
			<label for="sign" class="col-sm-4">加密方式 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="sign" headerKey="" headerValue="请选择加密方式" dictcode="project_sign" value="${taskProject.sign}" cssCls="form-control" /></div>
		</div>
  		<div class="form-group">
  			<label for="signstring" class="col-sm-4">加密方式 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><textarea class="form-control" rows="2" id="signstring" placeholder="参数(token不会提交)">${taskProject.signstring}</textarea>
  				<small class="text-muted">theCurrentTimestamp：代表当前时间戳<br/>encryptionParameters：代表加密参数</small>
  			</div>
		</div>
		<div class="form-group">
			<label for="isrecemail" class="col-sm-4">邮件通知 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="isrecemail" name="isrecemail" dictcode="boolean" value="${taskProject.isrecemail}" defvalue="0"/>
				<small class="text-muted">(选"否"，代表所有任务都不发邮件)</small>
			</div>
		</div>
  		<div class="form-group">
			<label for="recemail" class="col-sm-4">接收邮箱</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="recemail" placeholder="接收邮箱（多个,分隔）" value="${taskProject.recemail}"></div>
		</div>
		<hr/>
  		<div class="form-group text-right">
			<span id="saveMsg" class="label label-danger"></span>
 			<div class="btn-group">
				<button type="button" id="saveBtn" class="btn btn-success enter-fn">保存</button>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	var projectSigns = ${projectSigns};
	$(function() {
		$('#sign').change(function() {
			var _id = $('#sign').val();
			if(JUtil.isEmpty(_id)) {
				$('#signstring').val('');
				return;
			}
			$.each(projectSigns, function(i, obj) {
				if(obj.code === _id) {
					$('#signstring').val(obj.exp);
				}
			});
		});
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _remark = $('#remark');
			if(JUtil.isEmpty(_remark.val())) {
				_saveMsg.append('请输入描叙');
				_remark.focus();
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/ttsTaskProject/f-json/save.shtml',
				data : {
					id: $('#id').val(),
					name: _name.val(),
					remark: _remark.val(),
					sign: $('#sign').val(),
					signstring: $('#signstring').val(),
					isrecemail: $('input[name="isrecemail"]:checked').val(),
					recemail: $('#recemail').val()
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						setTimeout(function() {
							parent.info.loadInfo();
							parent.dialog.close();
						}, 800);
					}
					else if (json.code === -1)
						_saveMsg.append(JUtil.msg.ajaxErr);
					else
						_saveMsg.append(json.msg);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>