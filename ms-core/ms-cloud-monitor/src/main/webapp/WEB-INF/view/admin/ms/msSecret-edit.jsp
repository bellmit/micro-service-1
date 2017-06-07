<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-编辑密钥</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
  		<div class="form-group">
			<input type="text" class="form-control" id="cliId" placeholder="客户编码" value="${msSecret.cliId}"<c:if test="${msSecret.cliId!=null}"> readonly="readonly"</c:if>>
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="name" placeholder="名称" value="${msSecret.name}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="token" placeholder="token" value="${msSecret.token}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="domain" placeholder="host地址" value="${msSecret.domain}">
		</div>
  		<div class="form-group">
			<input type="text" class="form-control" id="remark" placeholder="备注" value="${msSecret.remark}">
		</div>
		<div class="form-group">
			<my:select id="isUse" headerKey="" headerValue="是否使用" dictcode="boolean" value="${msSecret.isUse}" cssCls="form-control" />
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
			var _cliId = $('#cliId');
			if(JUtil.isEmpty(_cliId.val())) {
				_saveMsg.append('请输入客户端编码');
				_cliId.focus();
				return;
			}
			
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _token = $('#token');
			if(JUtil.isEmpty(_token.val())) {
				_saveMsg.append('请输入token');
				_token.focus();
				return;
			}
			var _domain = $('#domain');
			/* if(JUtil.isEmpty(_domain.val())) {
				_saveMsg.append('请输入host地址');
				_domain.focus();
				return;
			} */
			
			var _isUse = $('#isUse');
			if(JUtil.isEmpty(_isUse.val())) {
				_saveMsg.append('请选择是否使用');
				_isUse.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/msSecret/f-json/save.shtml',
				data : {
					cliId: _cliId.val(),
					name: _name.val(),
					token: _token.val(),
					domain: _domain.val(),
					remark: $('#remark').val(),
					isUse: _isUse.val()
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