<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑客户端</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
  		<div class="form-group">
			<input type="text" class="form-control" id="clientId" placeholder="客户端编码" value="${cliInfo.clientId}" <c:if test="${cliInfo != null}">readonly="readonly"</c:if>>
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="name" placeholder="名称" value="${cliInfo.name}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="备注" value="${cliInfo.remark}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="ip" placeholder="IP地址" value="${cliInfo.ip}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="port" placeholder="端口" value="${cliInfo.port}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="token" placeholder="token" value="${cliInfo.token}">
		</div>
		<div class="form-group">
			<my:select id="status" dictcode="cli_info_status" value="${cliInfo.status}" cssCls="form-control" />
		</div>
  		<div class="form-group">
 			<div class="btn-group">
				<button type="button" id="saveBtn" class="btn btn-success enter-fn">保存</button>
			</div>
			<span id="saveMsg" class="label label-danger"></span>
		</div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _clientId = $('#clientId');
			if(JUtil.isEmpty(_clientId.val())) {
				_saveMsg.append('请输入客户端编码');
				_clientId.focus();
				return;
			}
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _ip = $('#ip');
			if(JUtil.isEmpty(_ip.val())) {
				_saveMsg.append('请输入IP地址');
				_ip.focus();
				return;
			}
			var _port = $('#port');
			if(JUtil.isEmpty(_port.val())) {
				_saveMsg.append('请输入端口');
				_port.focus();
				return;
			}
			var _token = $('#token');
			if(JUtil.isEmpty(_token.val())) {
				_saveMsg.append('请输入token');
				_token.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/cliInfo/f-json/save.shtml',
				data : {
					clientId: _clientId.val(),
					name: _name.val(),
					remark: $('#remark').val(),
					status: $('#status').val(),
					ip: _ip.val(),
					port: _port.val(),
					token: _token.val()
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