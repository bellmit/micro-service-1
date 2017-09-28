<%@page import="com.system.comm.enums.Boolean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目监控</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-sm">
		<input type="hidden" id="prjmId" value="${prjMonitor.prjmId}">
		<div class="form-group">
			<label for="prjId" class="col-sm-4">项目 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="prjId" headerKey="" headerValue="请选择项目" items="${prjInfos}" value="${prjMonitor.prjId}" cssCls="form-control" /></div>
		</div>
		<div class="form-group">
			<label for="type" class="col-sm-4">监控类型 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="type" headerKey="" headerValue="请选择监控类型" dictcode="prj_monitor_type" value="${prjMonitor.type}" cssCls="form-control" /></div>
		</div>
		<div class="form-group">
			<label for="remark" class="col-sm-4">服务信息</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="服务信息(备注)" value="${prjMonitor.remark}"></div>
		</div>
		<div class="form-group">
			<label for="monitorIs" class="col-sm-4">是否检测 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="monitorIs" name="monitorIs" dictcode="boolean" defvalue="1" value="${prjMonitor.monitorIs}" exp="onclick=\"info.monitorIs()\"" /></div>
		</div>
		<div id="monitorPanel">
			<hr/>
	  		<div class="form-group" style="display: none;">
				<label for="monitorSuccStr" class="col-sm-4">成功标识</label>
				<div class="col-sm-8"><input type="text" class="form-control" id="monitorSuccStr" placeholder="检测成功的标识符" value="<c:out value="${prjMonitor.monitorSuccStr}"></c:out>"></div>
			</div>
	  		<div class="form-group">
				<label for="monitorUrl" class="col-sm-4">检测地址</label>
				<div class="col-sm-8"><input type="text" class="form-control" id="monitorUrl" placeholder="检测地址" value="${prjMonitor.monitorUrl}"></div>
			</div>
	  		<div class="form-group">
				<label for="monitorFailNumRemind" class="col-sm-4">失败次数</label>
				<div class="col-sm-8"><input type="text" class="form-control" id="monitorFailNumRemind" placeholder="检测失败达到该次发送提醒" value="${prjMonitor.monitorFailNumRemind}"></div>
			</div>
	  		<div class="form-group">
				<label for="monitorFailSendInterval" class="col-sm-4">失败间隔</label>
				<div class="col-sm-8"><input type="text" class="form-control" id="monitorFailSendInterval" placeholder="检测失败发送信息间隔(单位：分钟)" value="${prjMonitor.monitorFailSendInterval}"></div>
			</div>
	  		<div class="form-group">
				<label for="monitorFailEmail" class="col-sm-4">通知邮箱</label>
				<div class="col-sm-8"><input type="text" class="form-control" id="monitorFailEmail" placeholder="检测失败接收邮箱(多个用,分隔)" value="${prjMonitor.monitorFailEmail}"></div>
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
			monitorIs: function() {
				var _monitorIs = $('input[name="monitorIs"]:checked').val();
				if('<%=Boolean.FALSE.getCode()%>' === _monitorIs) {
					$('#monitorPanel').css('display', 'none');
				} else {
					$('#monitorPanel').css('display', 'block');
				}
			}
	};
	$(function() {
		if($('#prjmId').val() === '') {
			$('#monitorSuccStr').val('{"code":0}');
			$('#monitorFailNumRemind').val('3');
			$('#monitorFailSendInterval').val('30');
		}
		info.monitorIs();
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _prjmId = $('#prjmId').val();
			var _prjId = $('#prjId');
			if(JUtil.isEmpty(_prjId.val())) {
				_saveMsg.append('请选择项目');
				_prjId.focus();
				return;
			}
			var _type = $('#type');
			if(JUtil.isEmpty(_type.val())) {
				_saveMsg.append('请选择监控类型');
				_type.focus();
				return;
			}
			var _monitorSuccStr = $('#monitorSuccStr');
			var _monitorUrl = $('#monitorUrl');
			var _monitorFailNumRemind = $('#monitorFailNumRemind');
			var _monitorFailSendInterval = $('#monitorFailSendInterval');
			var _monitorFailEmail = $('#monitorFailEmail');
			var _monitorIs = $('input[name="monitorIs"]:checked').val();
			if('<%=Boolean.TRUE.getCode()%>'===_monitorIs) {
				//是
				/* if(JUtil.isEmpty(_monitorSuccStr.val())) {
					_saveMsg.append('请输入检测成功的标识符');
					_monitorSuccStr.focus();
					return;
				} */
				if(JUtil.isEmpty(_monitorUrl.val())) {
					_saveMsg.append('请输入检测地址');
					_monitorUrl.focus();
					return;
				}
				if(JUtil.isEmpty(_monitorFailNumRemind.val())) {
					_saveMsg.append('请输入检测失败达到该次发送提醒');
					_monitorFailNumRemind.focus();
					return;
				}
				if(JUtil.isEmpty(_monitorFailSendInterval.val())) {
					_saveMsg.append('检测失败发送信息间隔');
					_monitorFailSendInterval.focus();
					return;
				}
				/* if(JUtil.isEmpty(_monitorFailEmail.val())) {
					_saveMsg.append('请输入检测失败接收邮箱');
					_monitorFailEmail.focus();
					return;
				} */
			}
			
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/prjMonitor/f-json/save.shtml',
				data : {
					prjmId: _prjmId,
					prjId: _prjId.val(),
					type: _type.val(),
					remark: $('#remark').val(),
					monitorIs: _monitorIs,
					monitorSuccStr: _monitorSuccStr.val(),
					monitorUrl: _monitorUrl.val(),
					monitorFailNumRemind: _monitorFailNumRemind.val(),
					monitorFailSendInterval: _monitorFailSendInterval.val(),
					monitorFailEmail: _monitorFailEmail.val()
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