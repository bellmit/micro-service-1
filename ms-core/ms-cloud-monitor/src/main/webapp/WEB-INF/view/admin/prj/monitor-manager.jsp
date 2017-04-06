<%@page import="com.module.admin.prj.enums.PrjMonitorMonitorStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-服务监控</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="prj"/>
			<jsp:param name="second" value="prjMonitorManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">项目 / <b>服务监控</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="btn-group">
							<my:select id="prjId" items="${prjInfos}" headerKey="" headerValue="全部项目" cssCls="form-control" value="${param.prjId}"/>
							</div>
							<div class="btn-group">
							<my:select id="monitorStatus" headerKey="" headerValue="全部状态" dictcode="prj_monitor_monitor_status" cssCls="form-control" />
							</div>
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
								<select id="refreshInterval" onchange="info.refreshChange()" class="form-control">
									<option value="">关闭自动刷新</option>
									<option value="5">5s刷新一次</option>
									<option value="10" selected="selected">10s刷新一次</option>
								</select>
						  	</div>
						  	<div class="btn-group">
						  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增项目监控</a>
						  	</div>
						  	<div class="btn-group">
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
				                         '<th>项目名称</th>',
				                         '<th>服务信息</th>',
				                         '<th>类型</th>',
				                         '<th>是否开启监控</th>',
				                         '<th>监控状态</th>',
				                         '<th>监控时间</th>',
				                         '<th width="100">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjMonitor/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size,
					prjId:$('#prjId').val(), monitorStatus:$('#monitorStatus').val()
				},
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _msCont = [];
							if(obj.monitorStatus === <%=PrjMonitorMonitorStatus.ERROR.getCode()%>) {
								_msCont.push('<span class="text-danger">',obj.monitorStatusName,'</span>');
								_msCont.push('<br>');
								_msCont.push('<small>检测失败次数：',obj.monitorFailNum,'<br>');
								_msCont.push('</small>');
							} else {
								_msCont.push('<span class="text-success">',obj.monitorStatusName,'</span>');
							}
							return ['<tr>',
							    	'<td>',obj.prjName,'</td>',
							    	'<td>',obj.remark,'</td>',
							    	'<td>',obj.typeName,'</td>',
							    	'<td>',obj.monitorIsName,'</td>',
							    	'<td>',_msCont.join(''),'</td>',
							    	'<td>',obj.monitorTime,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.prjmId,')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.prjmId,')" title="删除"></a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑项目
		edit : function(id) {
			dialog({
				title: '编辑项目监控',
				url: webroot + '/prjMonitor/f-view/edit.shtml?prjmId='+(id?id:''),
				type: 'iframe',
				width: 600,
				height: 530
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该项目监控吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjMonitor/f-json/delete.shtml',
					data : { prjmId: id },
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
		//版本管理
		version : function(prjId) {
			location = '${webroot}/prjVersion/f-view/manager.shtml?prjId=' + prjId;
		},
		//发布到客户端的管理
		cli : function(prjId) {
			location = '${webroot}/prjClient/f-view/manager.shtml?prjId=' + prjId;
		},
		refreshChange: function() {
			if(info.interval) {
				window.clearInterval(info.interval);
			}
			var _refresh = $('#refreshInterval').val();
			if(JUtil.isEmpty(_refresh)) {
				return;
			}
			info.interval = window.setInterval('info.loadInfo(1)', _refresh * 1000);
		}
};
$(function() {
	info.loadInfo(1);
	$('#prjId').change(function() {
		info.loadInfo(1);
	});
	$('#monitorStatus').change(function() {
		info.loadInfo(1);
	});
});
</script>
</body>
</html>