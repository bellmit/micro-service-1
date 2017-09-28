<%@page import="com.module.admin.prj.enums.PrjInfoContainer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld-body">
	<div class="enter-panel ep-lg">
		<input type="hidden" id="prjId" value="${prjInfo.prjId}">
		<div class="form-group">
			<label for="code" class="col-sm-4">编码 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="code" placeholder="编码" value="${prjInfo.code}"></div>
		</div>
		<div class="form-group">
			<label for="name" class="col-sm-4">名称 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><input type="text" class="form-control" id="name" placeholder="名称" value="${prjInfo.name}"></div>
		</div>
		<div class="form-group">
			<label for="remark" class="col-sm-4">备注</label>
			<div class="col-sm-8"><input type="text" class="form-control" id="remark" placeholder="备注" value="${prjInfo.remark}"></div>
		</div>
		<div class="form-group">
			<label for="status" class="col-sm-4">状态 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:radio id="status" name="status" dictcode="prj_info_status" value="${prjInfo.status}" defvalue="10" /></div>
		</div>
		<div class="form-group">
			<label for="container" class="col-sm-4">容器类型 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><my:select id="container" headerKey="" headerValue="请选择容器类型" dictcode="prj_info_container" value="${prjInfo.container}" cssCls="form-control" /></div>
		</div>
		<div class="form-group">
			<label for="shellScript" class="col-sm-4">发布脚本 <span class="text-danger">*</span></label>
			<div class="col-sm-8"><textarea class="form-control" rows="6" id="shellScript" placeholder="发布的shell脚本">${prjInfo.shellScript}</textarea></div>
		</div>
		<div>
			<small>
				<a href="${webroot}/help.jsp#prjInfoParam" target="_blank">[prj.path]、[prj.name]、[current.date]、[current.time]等参数帮助</a>
				<span>|</span>
				<a href="${webroot}/help.jsp#prjInfoShell" target="_blank">Shell脚本内容解析</a>
			</small>
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
		$('#container').change(function() {
			var _shellScript = $('#shellScript');
			if($(this).val() == '<%=PrjInfoContainer.LINUX_TOMCAT.getCode()%>') {
				//类型为tomcat
				_shellScript.val(['cd /opt/apache-tomcat-7.0.32\n',
						'./bin/shutdown.sh\n',
						'cp [prj.path][prj.name] /opt/apache-tomcat-7.0.32/webapps\n',
						'rm -rf /opt/apache-tomcat-7.0.32/webapps/test.zip /opt/apache-tomcat-7.0.32/webapps/test\n',
						'mv /opt/apache-tomcat-7.0.32/webapps/[prj.name] /opt/apache-tomcat-7.0.32/webapps/test.zip\n',
						'cd /opt/apache-tomcat-7.0.32/webapps\n',
						'unzip test.zip\n',
						'cd /opt/apache-tomcat-7.0.32\n',
						'./bin/startup.sh'].join(''));
			} else if($(this).val() == '<%=PrjInfoContainer.LINUX_CUST.getCode()%>') {
				//类型为自定义服务
				_shellScript.val(['cd /home/www/project.restful-1.0.0\n',
				                  './bin/stop.sh\n',
				                  'cp [prj.path][prj.name] /home/www\n',
				                  'rm -rf /home/www/project.restful-1.0.0.zip /home/www/project.restful-1.0.0\n',
				                  'mv /home/www/[prj.name] /home/www/project.restful-1.0.0.zip\n',
				                  'cd /home/www\n',
				                  'unzip project.restful-1.0.0.zip\n',
				                  'cd /home/www/project.restful-1.0.0\n',
				                  'chmod a+x bin/*\n',
				                  './bin/start.sh'].join(''));
			} else if($(this).val() == '<%=PrjInfoContainer.WIN_TOMCAT.getCode()%>') {
				//类型为tomcat
				_shellScript.val(['E:\n',
				        'cd soft\\apache-tomcat-7.0.68\\bin\n',
						'start shutdown.bat\n',
						'choice /t 3 /d y /n >nul\n',	//延迟3秒
						'copy [prj.path][prj.name] E:\\soft\\apache-tomcat-7.0.68\\webapps\n',
						'del E:\\soft\\apache-tomcat-7.0.68\\webapps\\test.rar\n',
						'del /F /S /Q E:\\soft\\apache-tomcat-7.0.68\\webapps\\test\n',
						'rename E:\\soft\\apache-tomcat-7.0.68\\webapps\\[prj.name] test.rar\n',
						'cd E:\\soft\\apache-tomcat-7.0.68\\webapps\n',
						'unrar x -t -o-p E:\\soft\\apache-tomcat-7.0.68\\webapps\\test.rar E:\\soft\\apache-tomcat-7.0.68\\webapps\n',
						'choice /t 5 /d y /n >nul\n',	//延迟5秒
						'cd E:\\soft\\apache-tomcat-7.0.68\\bin\n',
						'start startup.bat'].join(''));
			} else if($(this).val() == '<%=PrjInfoContainer.WIN_CUST.getCode()%>') {
				//类型为自定义
				_shellScript.val(['@echo off & setlocal enabledelayedexpansion\n',
						'echo Starting ...\n',
						'java -Xms256m -Xmx256m -XX:MaxPermSize=64M -jar ../../project.restful-1.0.0.jar\n',
						':end\n',
						'pause'].join(''));
			} else {
				_shellScript.val('');
			}
		});
		
		$('#saveBtn').click(function() {
			var _saveMsg = $('#saveMsg').empty();
			
			var _prjId = $('#prjId').val();
			var _code = $('#code');
			if(JUtil.isEmpty(_code.val())) {
				_saveMsg.append('请输入编码');
				_code.focus();
				return;
			}
			var _name = $('#name');
			if(JUtil.isEmpty(_name.val())) {
				_saveMsg.append('请输入名称');
				_name.focus();
				return;
			}
			var _container = $('#container');
			if(JUtil.isEmpty(_container.val())) {
				_saveMsg.append('请选择容器类型');
				_container.focus();
				return;
			}
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
				url : '${webroot}/prjInfo/f-json/save.shtml',
				data : {
					prjId: _prjId,
					code: _code.val(),
					name: _name.val(),
					remark: $('#remark').val(),
					status: $('input[name="status"]:checked').val(),
					container: _container.val(),
					shellScript: _shellScript.val()
				},
				success : function(json) {
					if (json.code === 0) {
						_saveMsg.attr('class', 'label label-success').append('保存成功');
						if('${param.source}'!='dtl') {
							setTimeout(function() {
								parent.info.loadInfo();
								parent.dialog.close();
							}, 800);
						}
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