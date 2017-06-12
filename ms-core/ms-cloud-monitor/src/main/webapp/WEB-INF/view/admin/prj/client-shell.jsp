<%@page import="com.module.admin.sys.enums.SysFileType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置发布的shell</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="prjId" value="${param.prjId}">
  		<div class="form-group">
			<input type="text" class="form-control" id="clientId" placeholder="客户端编码" readonly="readonly" value="${prjClient.clientId}">
		</div>
  		<div class="form-group">
			<textarea class="form-control" rows="10" id="shellScript" placeholder="发布的shell脚本 (客户端会优先执行这里的shell，如果这里没有设置，则会执行项目的shell)">${prjClient.shellScript}</textarea>
		</div>
		<div>
			<small>
				<a href="javascript:info.loadPrjShell();">加载项目的Shell</a>
				<textarea class="hidden" rows="5" id="prjShellScript">${prjShellScript}</textarea>
			</small>
		</div>
		<hr/>
		<div>
			<small>
				<a href="${webroot}/help.jsp#prjInfoParam" target="_blank">[prj.path]、[prj.name]、[current.date]、[current.time]等参数帮助</a>
				<span>|</span>
				<a href="${webroot}/help.jsp#prjInfoShell" target="_blank">Shell脚本内容解析</a>
			</small>
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
			loadPrjShell: function() {
				$('#shellScript').val($('#prjShellScript').val());
			}
	};
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _prjId = $('#prjId').val();
			var _clientId = $('#clientId').val();
			
			var _shellScript = $('#shellScript');
			if(JUtil.isEmpty(_shellScript.val())) {
				_saveMsg.append('请输入发布的shell脚本');
				_shellScript.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/prjClient/f-json/updateShellScript.shtml',
				data : {
					prjId: _prjId,
					version: '${param.version}',
					clientId: _clientId,
					shellScript: _shellScript.val()
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						setTimeout(function() {
							parent.client.loadInfo(1);
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