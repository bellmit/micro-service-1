<%@page import="com.module.admin.sys.enums.SysFileType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看日志</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
  		<div class="form-group">
			<input type="text" class="form-control" id="logPath" placeholder="日志路径" value="${prjClient.logPath}">
		</div>
		<div class="row">
			<div class="col-sm-6">
				<select id="readLine" style="display: inline-block;width: auto;" class="form-control">
					<option value="20">每次加载20行</option>
					<option value="50">每次加载50行</option>
					<option value="100">每次加载100行</option>
					<option value="150">每次加载150行</option>
				</select>
				<select id="refreshInterval" onchange="info.refreshChange()" style="display: inline-block;width: auto;" class="form-control">
					<option value="">自动加载:关闭</option>
					<option value="2">2s加载一次</option>
					<option value="5" selected="selected">5s加载一次</option>
					<option value="10">10s加载一次</option>
				</select>
			</div>
			<div class="col-sm-6 text-right">
				<small id="saveMsg" class="text-danger"></small> &nbsp;
				<div class="btn-group">
					<button type="button" id="saveBtn" class="btn btn-info enter-fn">加载日志</button>
				</div>
			</div>
		</div>
		<br clear="all"/>
  		<div class="form-group bg-success" style="padding: 10px;" id="logInfoPanel">
			<!-- <textarea class="form-control" rows="10" id="logInfo" placeholder="获取的日志"></textarea> -->
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	var info = {
			loadInfo: function() {
				var _saveMsg = $('#saveMsg').empty();
				_saveMsg.attr('class', 'text-danger');
				var _logPath = $('#logPath');
				if(JUtil.isEmpty(_logPath.val())) {
					_saveMsg.append('请输入项目查看日志的地址');
					_logPath.focus();
					return;
				}
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/lookLog.shtml',
					data : {
						prjId: '${param.prjId}',
						version: '${param.version}',
						clientId: '${param.clientId}',
						logPath: _logPath.val(),
						readLine: $('#readLine').val()
					},
					success : function(json) {
						if (json.code === 0) {
							var _logInfoPanel = $('#logInfoPanel').empty();
							_saveMsg.attr('class', 'text-success').append(JUtil.date.formatStr(JUtil.date.getTime()) + ' 加载成功');
							$.each(json.body, function(i, obj) {
								_logInfoPanel.append('<div>' + obj + '</div>');
							});
						}
						else if (json.code === -1)
							_saveMsg.append(JUtil.msg.ajaxErr);
						else
							_saveMsg.append(json.message);
					}
				});
			},
			refreshChange: function() {
				if(info.interval) {
					window.clearInterval(info.interval);
				}
				var _refresh = $('#refreshInterval').val();
				if(JUtil.isEmpty(_refresh)) {
					return;
				}
				info.interval = window.setInterval('info.loadInfo()', _refresh * 1000);
			}
	};
	$(function() {
		$('#saveBtn').click(function() {
			info.loadInfo();
		});
		$('#saveBtn').click();
		info.refreshChange();
	});
	</script>
</body>
</html>