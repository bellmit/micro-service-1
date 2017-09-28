<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定时任务-编辑用户</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-xs">
		<input type="hidden" id="userId" value="${sysUser.userId}">
  		<div class="form-group">
			<label for="username" class="col-sm-4">用户名 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="username" placeholder="用户名" value="${sysUser.username}"></div>
		</div>
  		<div class="form-group">
			<label for="password" class="col-sm-4">密码 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="password" placeholder="密码" value=""></div>
		</div>
  		<div class="form-group">
			<label for="nickname" class="col-sm-4">昵称 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="nickname" placeholder="昵称" value="${sysUser.nickname}"></div>
		</div>
		<div class="form-group">
			<label for="nickname" class="col-sm-4">状态 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="status" name="status" dictcode="sys_user_status" value="${sysUser.status}" defvalue="10" /></div>
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
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _userId = $('#userId').val();
			var _username = $('#username');
			if(JUtil.isEmpty(_username.val())) {
				_saveMsg.append('请输入用户名');
				_username.focus();
				return;
			}
			var _password = $('#password');
			if(_userId === '' && JUtil.isEmpty(_password.val())) {
				_saveMsg.append('请输入密码');
				_password.focus();
				return;
			}
			var _nickname = $('#nickname');
			if(JUtil.isEmpty(_nickname.val())) {
				_saveMsg.append('请输入昵称');
				_nickname.focus();
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/sysUser/f-json/save.shtml',
				data : {
					userId: _userId,
					username: _username.val(),
					password: _password.val(),
					nickname: _nickname.val(),
					status: $('input[name="status"]:checked').val()
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
						_saveMsg.append(json.message);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>