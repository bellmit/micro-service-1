<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-编辑</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-sm">
		<input type="hidden" id="cliId" value="${param.cliId}">
		<div class="form-group">
			<label for="prjId" class="col-sm-4">项目 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="prjId" headerKey="" headerValue="请选择项目" items="${prjInfos}" value="${msSecretApi.prjId}" cssCls="form-control" exp="onchange=\"info.loadApi()\"" /></div>
		</div>
		<div class="form-group">
			<label for="cusUrl" class="col-sm-4">拦截码 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="prjCode" placeholder="如admin、api等" value=""></div>
		</div>
		<div class="form-group">
			<label for="cusUrl" class="col-sm-4">自定义地址</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="cusUrl" placeholder="支持通配符 * 代表不限制字符，如：/test/*" value="" title="**代表后面的路径全部匹配"></div>
		</div>
		<hr/>
		<div class="form-group">
			<label class="col-sm-4"><input type="checkbox" name="selectAll" value="" onclick="info.selectAll(this)"> <small>全选API</small></label>
			<div class="col-sm-8">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-4"></label>
			<div class="col-sm-8" id="tablePanel">
			</div>
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
	var info = {
			selectAll : function(_this) {
				var _val = $(_this).prop('checked');
				$('input[name="tables"]').each(function(i, obj) {
					$(obj).prop('checked', _val);
				});
			},
			//根据项目编号获取API的地址
			loadApi : function() {
				var _tablePanel = $('#tablePanel').empty();
				JUtil.ajax({
					url : '${webroot}/prjApi/f-json/findByPrjId.shtml',
					data : {
						prjId: $('#prjId').val()
					},
					success : function(json) {
						if (json.code === 0) {
							var _conts = ['<div>'];
							$.each(json.body, function(i, obj) {
								_conts.push('<div class="col-sm-4"><label><input type="checkbox" name="tables" value="',obj.path,'"> <small>',obj.path,'</small></label></div>');
							});
							_conts.push('</div>');
							_tablePanel.append(_conts.join(''));
						}
						else if (json.code === -1)
							alert(JUtil.msg.ajaxErr);
						else
							alert(json.message);
					}
				});
			}
	};
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();

			var _prjId = $('#prjId');
			if(JUtil.isEmpty(_prjId.val())) {
				_saveMsg.append('请选择项目');
				_prjId.focus();
				return;
			}
			var _prjCode = $('#prjCode');
			if(JUtil.isEmpty(_prjCode.val())) {
				_saveMsg.append('请输入zuul拦截的编码');
				_prjCode.focus();
				return;
			}
			var _url = [];
			var _cusUrl = $('#cusUrl');
			if(JUtil.isNotEmpty(_cusUrl.val())) {
				_url.push(_cusUrl.val());
			}
			$('input[name="tables"]:checked').each(function(i, obj) {
				_url.push($(obj).val());
			});
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/msSecretApi/f-json/save.shtml',
				data : {
					cliId: $('#cliId').val(),
					prjId: _prjId.val(),
					prjCode: _prjCode.val(),
					urls: _url.join(';')
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