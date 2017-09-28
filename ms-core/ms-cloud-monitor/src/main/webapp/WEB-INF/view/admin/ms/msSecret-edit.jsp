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
<body class="cld-body">
	<div class="enter-panel ep-xs">
  		<div class="form-group">
			<label for="cliId" class="col-sm-4">编码 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="cliId" placeholder="客户端编码" value="${msSecret.cliId}"<c:if test="${msSecret.cliId!=null}"> readonly="readonly"</c:if>></div>
		</div>
  		<div class="form-group">
			<label for="name" class="col-sm-4">名称 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="name" placeholder="名称" value="${msSecret.name}"></div>
		</div>
  		<div class="form-group">
			<label for="token" class="col-sm-4">token <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="token" placeholder="token" value="${msSecret.token}"></div>
		</div>
  		<div class="form-group">
			<label for="domain" class="col-sm-4">domain</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="domain" placeholder="host地址" value="${msSecret.domain}"></div>
		</div>
  		<div class="form-group">
			<label for="remark" class="col-sm-4">备注</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="备注" value="${msSecret.remark}"></div>
		</div>
		<div class="form-group">
			<label for="isUse" class="col-sm-4">使用 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="isUse" name="isUse" dictcode="boolean" value="${msSecret.isUse}" defvalue="1" /></div>
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
					isUse: $('input[name="isUse"]:checked').val()
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