<%@page import="com.module.admin.prj.enums.PrjInfoContainer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑模板</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-lg">
		<input type="hidden" id="prjId" value="${param.prjId}">
		<div class="form-group">
			<label for="type" class="col-sm-4">类型 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="type" name="type" dictcode="code_template_type" value="${codeTemplate.type}" defvalue="10"/></div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-4">文件名 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="name" placeholder="文件名" value="${codeTemplate.name}"<c:if test="${codeTemplate!=null}"> readonly="readonly"</c:if>></div>
		</div>
  		<div class="form-group">
			<label for="remark" class="col-sm-4">描述</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="描述" value="${codeTemplate.remark}"></div>
		</div>
  		<div class="form-group">
			<label for="suffix" class="col-sm-4">后缀</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="suffix" placeholder="后缀，如ServiceImpl" value="${codeTemplate.suffix}"></div>
		</div>
  		<div class="form-group">
			<label for="packageName" class="col-sm-4">包名 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="packageName" placeholder="包名" value="${codeTemplate.packageName}"></div>
		</div>
		<div class="form-group">
			<label for="content" class="col-sm-4">模版内容 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><textarea class="form-control" rows="5" id="content" placeholder="模版内容">${codeTemplate.content}</textarea></div>
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
			
	};
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			_saveMsg.attr('class', 'label label-danger');
			
			var _prjId = $('#prjId').val();

			var _type = $('input[name="type"]:checked');
			
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _packageName = $('#packageName');
			if(JUtil.isEmpty(_packageName.val())) {
				_saveMsg.append('请输入包名');
				_packageName.focus();
				return;
			}
			var _content = $('#content');
			if(JUtil.isEmpty(_content.val())) {
				_saveMsg.append('请输入模板内容');
				_content.focus();
				return;
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/codeTemplate/f-json/save.shtml',
				data : {
					prjId: _prjId,
					code: _prjId,
					type: _type.val(),
					name: _name.val(),
					remark: $('#remark').val(),
					packageName: _packageName.val(),
					content: _content.val(),
					suffix: $('#suffix').val()
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