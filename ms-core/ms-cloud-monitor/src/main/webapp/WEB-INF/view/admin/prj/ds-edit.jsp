<%@page import="com.module.admin.prj.enums.PrjInfoContainer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑数据源</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="prjId" value="${param.prjId}">
  		<div class="form-group">
			<input type="text" class="form-control" id="code" placeholder="编码" value="${prjDs.code}">
		</div>
		<div class="form-group">
			<span>类型：&nbsp;</span><my:radio id="type" name="type" dictcode="prj_ds_type" value="${prjDs.type}" defvalue="mysql" exp="onclick=\"info.change(this)\""/>
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="driverClass" placeholder="驱动类" value="${prjDs.driverClass}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="url" placeholder="jdbc的url" value="${prjDs.url}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="username" placeholder="用户名" value="${prjDs.username}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="password" placeholder="密码" value="${prjDs.password}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="initialSize" placeholder="初始连接数" value="${prjDs.initialSize}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="maxIdle" placeholder="最大连接数" value="${prjDs.maxIdle}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="minIdle" placeholder="最小连接数" value="${prjDs.minIdle}">
		</div>
		<div class="form-group">
			<textarea class="form-control" rows="5" id="testSql" placeholder="测试的sql语句">${prjDs.testSql}</textarea>
		</div>
		<hr/>
  		<div class="form-group">
 			<div class="btn-group">
				<button type="button" id="saveBtn" class="btn btn-success enter-fn">保存</button>
				<button type="button" id="testBtn" class="btn btn-default enter-fn">测试sql语句</button>
			</div>
			<span id="saveMsg" class="label label-danger"></span>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	var info = {
			change: function(_this) {
				var _type = $(_this).val();
				if(!_type || _type == '') {
					_type = 'mysql';
				}
				if(_type === 'mysql') {
					$('#driverClass').val('com.mysql.jdbc.Driver');
					$('#url').val('jdbc:mysql://127.0.0.1:3306/db?useUnicode=true&characterEncoding=UTF-8');
					$('#username').val('root');
					$('#password').val('root');
					$('#initialSize').val('10');
					$('#maxIdle').val('30');
					$('#minIdle').val('10');
					$('#testSql').val('select now() as dt from dual');
				} else {
					$('#driverClass').val('oracle.jdbc.driver.OracleDriver');
					$('#url').val('jdbc:oracle:thin:@127.0.0.1:1521:orcl');
					$('#username').val('sys');
					$('#password').val('sys');
					$('#initialSize').val('10');
					$('#maxIdle').val('30');
					$('#minIdle').val('10');
					$('#testSql').val('select sysdate as dt from dual');
				}
			}
	};
	$(function() {
		if('${prjDs.code}'==='') {
			info.change();
		}
		$('#testBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			_saveMsg.attr('class', 'label label-danger');
			
			var _prjId = $('#prjId').val();
			var _code = $('#code');
			if(JUtil.isEmpty(_code.val())) {
				_saveMsg.append('请输入编码');
				_code.focus();
				return;
			}
			var _type = $('input[name="type"]:checked');
			var _driverClass = $('#driverClass');
			if(JUtil.isEmpty(_driverClass.val())) {
				_saveMsg.append('请输入驱动类');
				_driverClass.focus();
				return;
			}
			var _url = $('#url');
			if(JUtil.isEmpty(_url.val())) {
				_saveMsg.append('请输入jdbc的url');
				_url.focus();
				return;
			}
			var _username = $('#username');
			if(JUtil.isEmpty(_username.val())) {
				_saveMsg.append('请输入用户名');
				_username.focus();
				return;
			}
			var _password = $('#password');
			if(JUtil.isEmpty(_password.val())) {
				_saveMsg.append('请输入密码');
				_password.focus();
				return;
			}
			var _initialSize = $('#initialSize');
			if(JUtil.isEmpty(_initialSize.val())) {
				_saveMsg.append('请输入初始大小');
				_initialSize.focus();
				return;
			}
			var _maxIdle = $('#maxIdle');
			if(JUtil.isEmpty(_maxIdle.val())) {
				_saveMsg.append('请输入最大连接数');
				_maxIdle.focus();
				return;
			}
			var _minIdle = $('#minIdle');
			if(JUtil.isEmpty(_minIdle.val())) {
				_saveMsg.append('请输入最小连接数');
				_minIdle.focus();
				return;
			}
			var _testSql = $('#testSql');
			if(JUtil.isEmpty(_testSql.val())) {
				_saveMsg.append('请输入测试的sql语句');
				_testSql.focus();
				return;
			}
			
			var _saveBtn = $('#testBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('提交中...');
			JUtil.ajax({
				url : '${webroot}/prjDs/f-json/test.shtml',
				data : {
					prjId: '${param.prjId}',
					code: _code.val(),
					type: _type.val(),
					driverClass: _driverClass.val(),
					url: _url.val(),
					username: _username.val(),
					password: _password.val(),
					initialSize: _initialSize.val(),
					maxIdle: _maxIdle.val(),
					minIdle: _minIdle.val(),
					testSql: _testSql.val()
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('执行测试语句成功');
					}
					else if (json.code === -1)
						_saveMsg.append(JUtil.msg.ajaxErr);
					else
						_saveMsg.append(json.message);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
		
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			_saveMsg.attr('class', 'label label-danger');
			
			var _prjId = $('#prjId').val();
			var _code = $('#code');
			if(JUtil.isEmpty(_code.val())) {
				_saveMsg.append('请输入编码');
				_code.focus();
				return;
			}
			var _type = $('input[name="type"]:checked');
			var _driverClass = $('#driverClass');
			if(JUtil.isEmpty(_driverClass.val())) {
				_saveMsg.append('请输入驱动类');
				_driverClass.focus();
				return;
			}
			var _url = $('#url');
			if(JUtil.isEmpty(_url.val())) {
				_saveMsg.append('请输入jdbc的url');
				_url.focus();
				return;
			}
			var _username = $('#username');
			if(JUtil.isEmpty(_username.val())) {
				_saveMsg.append('请输入用户名');
				_username.focus();
				return;
			}
			var _password = $('#password');
			if(JUtil.isEmpty(_password.val())) {
				_saveMsg.append('请输入密码');
				_password.focus();
				return;
			}
			var _initialSize = $('#initialSize');
			if(JUtil.isEmpty(_initialSize.val())) {
				_saveMsg.append('请输入初始大小');
				_initialSize.focus();
				return;
			}
			var _maxIdle = $('#maxIdle');
			if(JUtil.isEmpty(_maxIdle.val())) {
				_saveMsg.append('请输入最大连接数');
				_maxIdle.focus();
				return;
			}
			var _minIdle = $('#minIdle');
			if(JUtil.isEmpty(_minIdle.val())) {
				_saveMsg.append('请输入最小连接数');
				_minIdle.focus();
				return;
			}
			var _testSql = $('#testSql');
			if(JUtil.isEmpty(_testSql.val())) {
				_saveMsg.append('请输入测试的sql语句');
				_testSql.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/prjDs/f-json/save.shtml',
				data : {
					prjId: _prjId,
					code: _code.val(),
					type: _type.val(),
					driverClass: _driverClass.val(),
					url: _url.val(),
					username: _username.val(),
					password: _password.val(),
					initialSize: _initialSize.val(),
					maxIdle: _maxIdle.val(),
					minIdle: _minIdle.val(),
					testSql: _testSql.val()
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