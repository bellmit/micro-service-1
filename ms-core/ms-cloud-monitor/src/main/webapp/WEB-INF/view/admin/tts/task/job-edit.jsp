<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-定时任务-编辑任务</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-sm">
		<input type="hidden" id="id" value="${taskJob.id}">
		<input type="hidden" id="projectid" value="${param.projectid}">
  		<div class="form-group">
			<label for="name" class="col-sm-4">名称 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="name" placeholder="名称" value="${taskJob.name}"></div>
		</div>
  		<div class="form-group">
			<label for="remark" class="col-sm-4">描述</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="描叙" value="${taskJob.remark}"></div>
		</div>
		<div class="form-group">
			<label for="calltype" class="col-sm-4">调用方式 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="calltype" headerKey="" headerValue="请选择调用方式" dictcode="job_calltype" value="${taskJob.calltype}" cssCls="form-control" /></div>
		</div>
  		<div class="form-group">
			<label for="link" class="col-sm-4">调用链接 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="link" placeholder="调用链接(微服务格式:serviceId:地址)" value="${taskJob.link}"></div>
		</div>
  		<div class="form-group">
			<label for="cron" class="col-sm-4">任务规则 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="cron" placeholder="任务规则" value="${taskJob.cron}"></div>
		</div>
		<div class="text-right">
			<small>
				<a href="javascript:;" onclick="$('#cron').val('0/3 * * * * ?')" title="每3秒执行">每3秒</a> |
				<a href="javascript:;" onclick="$('#cron').val('25 0/1 * * * ?')" title="每分钟25秒执行">每分钟</a> |
				<a href="javascript:;" onclick="$('#cron').val('0 0 0/1 * * ?')" title="每小时0点0分执行">每小时</a> |
				<a href="javascript:;" onclick="$('#cron').val('0 30 8,9 ? * MON')" title="每周一的8:30和9:30执行">每周一</a>
			</small>
		</div>
		<hr/>
		<div class="form-group">
			<label for="cron" class="col-sm-4">执行状态 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="status" name="status" dictcode="job_status" value="${taskJob.status}" defvalue="0" /></div>
		</div>
  		<div class="form-group">
  			<label for="cron" class="col-sm-4">失败通知</label>
			<div class="col-sm-8">
			<c:choose>
			<c:when test="${taskJob == null}"><my:radio id="isfailmail" name="isfailmail" dictcode="boolean" value="${taskProject.isrecemail}" defvalue="0"/></c:when>
			<c:otherwise><my:radio id="isfailmail" name="isfailmail" dictcode="boolean" value="${taskJob.isfailmail}" defvalue="0"/></c:otherwise>
			</c:choose>
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
	$(function() {
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _calltype = $('#calltype');
			if(JUtil.isEmpty(_calltype.val())) {
				_saveMsg.append('请选择调用方式');
				_calltype.focus();
				return;
			}
			var _link = $('#link');
			if(JUtil.isEmpty(_link.val())) {
				_saveMsg.append('请输入调用链接');
				_link.focus();
				return;
			}
			var _cron = $('#cron');
			if(JUtil.isEmpty(_cron.val())) {
				_saveMsg.append('请输入任务规则');
				_cron.focus();
				return;
			}
			var _status = $('input[name="status"]:checked').val();
			if(JUtil.isEmpty(_status)) {
				_saveMsg.append('请选择任务执行状态');
				return;
			}

			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/ttsTaskJob/f-json/save.shtml',
				data : {
					id: $('#id').val(),
					projectid: $('#projectid').val(),
					name: _name.val(),
					remark: $('#remark').val(),
					calltype: _calltype.val(),
					link: _link.val(),
					cron: _cron.val(),
					status: _status,
					isfailmail: $('input[name="isfailmail"]:checked').val()
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
						_saveMsg.append(json.msg);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		});
	});
	</script>
</body>
</html>