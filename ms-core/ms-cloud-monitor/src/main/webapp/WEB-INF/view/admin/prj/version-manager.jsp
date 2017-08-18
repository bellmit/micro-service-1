<%@page import="com.module.admin.prj.enums.PrjClientStatus"%>
<%@page import="com.system.comm.enums.Boolean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目管理-版本管理</title>
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
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">项目 / 项目管理 / <b>版本管理</b></div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="${webroot}/prjInfo/f-view/manager.shtml?name=${param.name}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
				  	<div class="row">
				  		<div class="col-sm-5">
				  			<div class="table-tool-panel">
				  			<div class="row">
								<div class="col-sm-6">
								</div>
								<div class="col-sm-6 text-right">
								  	<div class="btn-group">
								  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增版本</a>
								  	</div>
								</div>
							</div>
							</div>
							<div id="infoPanel" class="table-panel"></div>
							<div id="infoPage" class="table-page-panel"></div>
				  		</div>
				  		<div class="col-sm-7" id="clientInfo" style="display: none;">
				  			<div class="table-tool-panel">
					  			<div class="row">
									<div class="col-sm-4 text-muted" id="clientInfoTitle">
									</div>
									<div class="col-sm-8 text-right">
										<div class="btn-group">
											<select id="refreshInterval" onchange="client.refreshChange()" class="form-control input-sm">
												<option value="">自动刷新:关闭</option>
												<option value="5">5s刷新一次</option>
												<option value="10">10s刷新一次</option>
											</select>
									  	</div>
									  	<div class="btn-group">
									  		<a href="javascript:;" class="btn btn-info btn-sm" onclick="client.releaseAll()">全部部署</a>
									  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="client.edit()">新增客户端</a>
									  		<a href="${webroot}/prjMonitor/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm" target="_blank">查看服务</a>
									  	</div>
									</div>
								</div>
						  	</div>
							<div id="clientPanel" class="table-panel"></div>
							<div id="clientPage" class="table-page-panel"></div>
				  		</div>
				  	</div>
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
				                         '<th>版本号</th>',
				                         '<th>参考版本</th>',
				                         '<th width="110">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjVersion/f-json/pageQuery.shtml',
				data : { prjId: '${param.prjId}', page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _isReleaseName = '';
							if(obj.isRelease === <%=Boolean.TRUE.getCode()%>) {
								_isReleaseName = '<span class="label label-success">发布</span>';
								if(client.version === undefined) {
									//为第一次进来，默认加载发布的项
									client.version = obj.version;
									client.loadInfo(1);
								}
							} else {
								_isReleaseName = '<span class="label label-danger">历史</span>';
							}
							return ['<tr>',
							    	'<td>',_isReleaseName,'&nbsp;',obj.version,'&nbsp;&nbsp;',
							    	'<a href="',webroot,'/sysFile/f-view/download.shtml?url=',obj.pathUrl,'" target="_blank">下载</a></td>',
							    	'<td>',obj.rbVersion,'</td>',
							    	'<td><a class="text-success" href="javascript:info.cli(${param.prjId}, \'',obj.version,'\')" title="发到对应的客户端">去部署</a>',
							    	'&nbsp; &nbsp;&nbsp; <span class="dropdown opt-more">',
									'<a class="glyphicon text-success dropdown-toggle" href="javascript:;" data-toggle="dropdown">更多...</a>',
									'<ul class="dropdown-menu" role="menu">',
									'<li role="presentation"><a href="javascript:info.edit(\'',obj.version,'\')" title="修改">修改</a></li>',
									'<li role="presentation"><a href="javascript:info.del(\'',obj.version,'\')" title="删除">删除</a></li>',
									'<li role="presentation"><a href="javascript:info.script(\'',obj.version,'\')" title="设置升级的脚本">升级脚本</a></li>',
									'</ul>',
									'</span>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑项目版本
		edit : function(id) {
			dialog({
				title: '编辑项目版本',
				url: webroot + '/prjVersion/f-view/edit.shtml?prjId=${param.prjId}&version='+(id?id:''),
				type: 'iframe',
				width: 400,
				height: 390
			});
		},
		//编辑脚本
		script: function(version) {
			location = webroot + '/prjVersionScript/f-view/manager.shtml?prjId=${param.prjId}&version='+(version?version:'');
		},
		del : function(version) {
			if(confirm('您确定要删除该版本吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjVersion/f-json/delete.shtml',
					data : { prjId: '${param.prjId}', version: version },
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
		//发布到客户端的管理
		cli : function(prjId, version) {
			client.version = version;
			client.loadInfo(1);
			//location = '${webroot}/prjClient/f-view/manager.shtml?prjId=' + prjId + '&version=' + version;
		}
};

var clientPage = undefined;
var client = {
		//获取用户信息
		loadInfo : function(page) {
			if(client.version != undefined) {
				$('#clientInfo').css('display', 'block');
			}
			$('#clientInfoTitle').html('<small>发布的版本号：</small><span class="label label-warning">' + client.version + '</span>');
			if(!clientPage) {
				clientPage = new Page('clientPage', client.loadInfo, 'clientPanel', 'clientPage');
				clientPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th>客户端编号</th>',
				                         '<th>状态</th>',
				                         '<th>发布时间</th>',
				                         '<th width="110">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				clientPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				clientPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjClient/f-json/pageQuery.shtml',
				data : { prjId: '${param.prjId}', version: client.version, page:clientPage.page, size:clientPage.size },
				beforeSend: function(){ clientPage.beforeSend('加载信息中...'); },
				error : function(json){ clientPage.error('加载信息出错了!'); },
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
							    	'<td>',obj.clientId,'<br/>',
							    	'<span class="text-success">',obj.ip,':',obj.port,'</span></td>',
							    	'<td>',_statusName,(JUtil.isNotEmpty(obj.statusMsg) ? ' | <a href="javascript:client.lookResult(\''+obj.clientId+'\');">结果</a>':''),'<textarea class="hidden" id="statusMsg',obj.clientId,'">',obj.statusMsg,'</textarea></td>',
							    	'<td>',obj.releaseTime,'</td>',
							    	'<td><a class="glyphicon text-success" href="javascript:client.release(\'',obj.clientId,'\')" title="发布到客户端(相当于重新发布项目)">部署</a>',
							    	'&nbsp; &nbsp;&nbsp;<span class="dropdown opt-more">',
									'<a class="glyphicon text-success dropdown-toggle" href="javascript:;" data-toggle="dropdown">更多...</a>',
									'<ul class="dropdown-menu" role="menu">',
									'<li role="presentation"><a href="javascript:client.edit(\'',obj.clientId,'\')" title="修改">修改</a></li>',
									'<li role="presentation"><a href="javascript:client.del(\'',obj.clientId,'\')" title="删除">删除</a></li>',
									'<li role="presentation"><a href="javascript:client.shell(\'',obj.clientId,'\')" title="设置发布的Shell">设命令</a></li>',
									'<li role="presentation"><a href="javascript:client.lookLog(\'',obj.clientId,'\')" title="查询项目的日志文件">看日志</a></li>',
									'</ul>',
									'</span>',
							    	'</td>',
								'</tr>'].join('');
						}
						clientPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
						JUtil.sys.initOptMore();
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
				url: webroot + '/prjClient/f-view/edit.shtml?prjId=${param.prjId}&version='+client.version+'&clientId='+(clientId?clientId:''),
				type: 'iframe',
				width: 400,
				height: 250
			});
		},
		//查看日志
		lookLog : function(clientId) {
			var url = webroot + '/prjClient/f-view/lookLog.shtml?prjId=${param.prjId}&version='+client.version+'&clientId='+(clientId?clientId:'');
			
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
				url: webroot + '/prjClient/f-view/shell.shtml?prjId=${param.prjId}&version='+client.version+'&clientId='+(clientId?clientId:''),
				type: 'iframe',
				width: 600,
				height: 480
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该客户端吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/delete.shtml',
					data : { prjId: '${param.prjId}', version: client.version, clientId: id },
					success : function(json) {
						if (json.code === 0) {
							message('删除成功');
							client.loadInfo(1);
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
					data : { prjId: '${param.prjId}', version: client.version },
					success : function(json) {
						if (json.code === 0) {
							message('操作成功');
							client.loadInfo(1);
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
					data : { prjId: '${param.prjId}', version: client.version, clientId: clientId },
					success : function(json) {
						if (json.code === 0) {
							message('操作成功');
							client.loadInfo(1);
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
				window.clearInterval(client.interval);
			}
			var _refresh = $('#refreshInterval').val();
			if(JUtil.isEmpty(_refresh)) {
				return;
			}
			info.interval = window.setInterval('client.loadInfo()', _refresh * 1000);
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>