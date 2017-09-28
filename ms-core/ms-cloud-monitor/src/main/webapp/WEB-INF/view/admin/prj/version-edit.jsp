<%@page import="com.module.admin.sys.enums.SysFileType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目版本</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-xs">
		<input type="hidden" id="prjId" value="${param.prjId}">
		<div class="form-group">
			<label for="version" class="col-sm-4">版本号 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="version" placeholder="版本号" value="${prjVersion.version}"></div>
		</div>
		<div class="form-group">
			<label for="remark" class="col-sm-4">备注</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="备注" value="${prjVersion.remark}"></div>
		</div>
		<div class="form-group">
			<label for="isRelease" class="col-sm-4">发布 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="isRelease" name="isRelease" dictcode="boolean" value="${prjVersion.isRelease}" defvalue="1"/></div>
		</div>
		<div class="form-group">
			<label for="rbVersion" class="col-sm-4">参考版本 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="rbVersion" headerKey="" headerValue="参考版本" items="${list}" value="${prjVersion.rbVersion}" cssCls="form-control" /></div>
		</div>
		<div class="form-group">
			<label for="rbVersion" class="col-sm-4">版本上传 <span class="text-danger">*</span></label>
			<div class="col-sm-8">
				<input type="file" id="files" name="files" onchange="fi.uploadFile()"/><img id="filesLoading" alt="上传中..." src="${webroot}/resources/images/loading.gif" style="display: none;"/>
				<input type="hidden" id="pathUrl" name="pathUrl" value="${prjVersion.pathUrl}"/>
			</div>
		</div>
		<div class="form-group" id="pathUrlPanel">
			<c:choose>
			<c:when test="${prjVersion == null}"><span class="text-info">请上传项目</span></c:when>
			<c:otherwise>
				<label for="rbVersion" class="col-sm-4"></label>
				<div class="col-sm-8">
					<a href="${webroot}/sysFile/f-view/download.shtml?url=${prjVersion.pathUrl}" target="_blank">下载项目</a>
				</div>
			</c:otherwise>
			</c:choose>
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
	<jsp:include page="/WEB-INF/view/inc/utils/upload.jsp"></jsp:include>
	<script type="text/javascript">
	var fi = {
			uploadFile : function() {
				upload.file({
					fileId : 'files',
					loading : 'filesLoading',
					url : '${webroot}/sysFile/f-json/upload.shtml',
					param : {
						filename : 'files', type : '<%=SysFileType.PRJ.getCode()%>'
					},
					success : function(data) {
						$('#pathUrl').val(data.body.url);
						$('#pathUrlPanel').empty().append(['<a href="',webroot,'/sysFile/f-view/download.shtml?url=',data.body.url,'" target="_blank">下载项目</a>'].join(''));
					}
				});
			}
	};
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _prjId = $('#prjId').val();
			var _version = $('#version');
			if(JUtil.isEmpty(_version.val())) {
				_saveMsg.append('请输入版本号');
				_version.focus();
				return;
			}
			var _isRelease = $('input[name="isRelease"]:checked');
			/* if(JUtil.isEmpty(_isRelease.val())) {
				_saveMsg.append('请选择是否发布');
				_isRelease.focus();
				return;
			} */
			var _pathUrl = $('#pathUrl');
			if(JUtil.isEmpty(_pathUrl.val())) {
				_saveMsg.append('请上传项目');
				return;
			}
			
			var _rbVersion = $('#rbVersion');
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/prjVersion/f-json/save.shtml',
				data : {
					prjId: _prjId,
					version: _version.val(),
					remark: $('#remark').val(),
					isRelease: _isRelease.val(),
					rbVersion: _rbVersion.val(),
					pathUrl: _pathUrl.val()
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