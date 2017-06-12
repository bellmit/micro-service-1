<%@page import="com.module.admin.prj.enums.PrjClientStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目管理-发布到客户端</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="prj"/>
			<jsp:param name="second" value="prjInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">项目 / 项目管理 / <b>发布到客户端</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-4">
						</div>
						<div class="col-sm-8 text-right">
							<div class="btn-group">
								<select id="refreshInterval" onchange="info.refreshChange()" class="form-control input-sm">
									<option value="">自动刷新:关闭</option>
									<option value="5">5s刷新一次</option>
									<option value="10">10s刷新一次</option>
								</select>
						  	</div>
						  	<div class="btn-group">
						  		<a href="javascript:;" class="btn btn-info btn-sm" onclick="info.releaseAll()">发布项目</a>
						  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增客户端</a>
						  		<a href="${webroot}/prjMonitor/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm" target="_blank">查看服务</a>
						  	</div>
						  	<div class="btn-group">
						  		<a href="${webroot}/prjVersion/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel"></div>
					<div id="infoPage"></div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/utils/page.jsp"></jsp:include>
<script type="text/javascript">
var infoPage = undefined;
var info = {
		//获取用户信息
		loadInfo : function(page) {
			if(!infoPage) {
				infoPage = new Page('infoPage', info.loadInfo, 'infoPanel', 'infoPage');
				infoPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th>客户端编号</th>',
				                         '<th>IP : 端口</th>',
				                         '<th>状态</th>',
				                         '<th>发布时间</th>',
				                         '<th>项目</th>',
				                         '<th width="230">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjClient/f-json/pageQuery.shtml',
				data : { prjId: '${param.prjId}', version: '${param.version}', page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _statusName = '';
							if(obj.status === <%=PrjClientStatus.WAIT.getCode()%>) {
								_statusName = '<span class="text-muted">'+obj.statusName+'</span>';
							} else if(obj.status === <%=PrjClientStatus.ING.getCode()%>) {
								_statusName = '<span class="text-warning">'+obj.statusName+'</span>';
							} else if(obj.status === <%=PrjClientStatus.SUCC.getCode()%>) {
								_statusName = '<span class="text-success">'+obj.statusName+'</span>';
							} else if(obj.status === <%=PrjClientStatus.FAIL.getCode()%>) {
								_statusName = '<span class="text-danger">'+obj.statusName+'</span>';
							} else {
								_statusName = '<span class="text-muted">'+obj.statusName+'</span>';
							}
							return ['<tr>',
							    	'<td>',obj.clientId,'</td>',
							    	'<td>',obj.ip,' : ',obj.port,'</td>',
							    	'<td>',_statusName,(JUtil.isNotEmpty(obj.statusMsg) ? ' | <a href="javascript:info.lookResult(\''+obj.clientId+'\');">结果</a>':''),'<textarea class="hidden" id="statusMsg',obj.clientId,'">',obj.statusMsg,'</textarea></td>',
							    	'<td>',obj.releaseTime,'</td>',
							    	'<td><a class="glyphicon text-success" href="javascript:info.lookLog(\'',obj.clientId,'\')" title="查询项目的日志文件">查看日志</a>',
							    	'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(\'',obj.clientId,'\')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj.clientId,'\')" title="删除"></a>',
							    	'&nbsp; | &nbsp;<a class="glyphicon text-success" href="javascript:info.shell(\'',obj.clientId,'\')" title="设置发布的Shell">设置执行命令</a>',
							    	'&nbsp; | &nbsp;<a class="glyphicon text-success" href="javascript:info.release(\'',obj.clientId,'\')" title="发布到客户端(相当于重新发布项目)">发布项目</a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//查看结果
		lookResult : function(clientId) {
			var _msg = $('#statusMsg' + clientId).val();
			if(JUtil.isEmpty(_msg)) {
				_msg = '暂无';
			}
			alert(_msg);
		},
		//编辑客户端
		edit : function(clientId) {
			dialog({
				title: '编辑发布到的客户端',
				url: webroot + '/prjClient/f-view/edit.shtml?prjId=${param.prjId}&version=${param.version}&clientId='+(clientId?clientId:''),
				type: 'iframe',
				width: 400,
				height: 250
			});
		},
		//查看日志
		lookLog : function(clientId) {
			var url = webroot + '/prjClient/f-view/lookLog.shtml?prjId=${param.prjId}&version=${param.version}&clientId='+(clientId?clientId:'');
			
			open(url);
			/* dialog({
				title: '查看日志',
				url: webroot + '/prjClient/f-view/lookLog.shtml?prjId=${param.prjId}&version=${param.version}&clientId='+(clientId?clientId:''),
				type: 'iframe',
				width: 700,
				height: 500
			}); */
		},
		//设置发布的shell
		shell : function(clientId) {
			dialog({
				title: '设置发布的shell',
				url: webroot + '/prjClient/f-view/shell.shtml?prjId=${param.prjId}&version=${param.version}&clientId='+(clientId?clientId:''),
				type: 'iframe',
				width: 600,
				height: 480
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该客户端吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/delete.shtml',
					data : { prjId: '${param.prjId}', version: '${param.version}', clientId: id },
					success : function(json) {
						if (json.code === 0) {
							message('删除成功');
							info.loadInfo(1);
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		},
		//发布到所有客户端
		releaseAll : function() {
			if(confirm('您确定要发布到所有客户端吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/releaseAll.shtml',
					data : { prjId: '${param.prjId}', version: '${param.version}' },
					success : function(json) {
						if (json.code === 0) {
							message('操作成功');
							info.loadInfo(1);
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		},
		//发布到指定客户端
		release : function(clientId) {
			if(confirm('您确定要发布到指定的客户端吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/release.shtml',
					data : { prjId: '${param.prjId}', version: '${param.version}', clientId: clientId },
					success : function(json) {
						if (json.code === 0) {
							message('操作成功');
							info.loadInfo(1);
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		},
		refreshChange: function() {
			if(info.interval) {
				window.clearInterval(info.interval);
			}
			var _refresh = $('#refreshInterval').val();
			if(JUtil.isEmpty(_refresh)) {
				return;
			}
			info.interval = window.setInterval('info.loadInfo()', _refresh * 1000);
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>