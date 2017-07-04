<%@page import="com.system.comm.enums.Boolean"%>
<%@page import="com.module.admin.prj.enums.PrjMonitorMonitorStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-版本脚本</title>
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
						<div class="col-sm-5 title">项目 / 版本管理 / <b>升级脚本</b></div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="${webroot}/prjVersion/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
				  	<div class="table-tool-panel">
						<div class="row">
							<div class="col-sm-8">
								<div class="btn-group">
								<my:select id="prjId" items="${prjInfos}" headerKey="" headerValue="全部项目" cssCls="form-control input-sm" value="${param.prjId}"/>
								</div>
								<span class="enter-panel">
									<input type="text" style="width: 150px;display: inline;" class="form-control input-sm" id="dsCode" placeholder="数据源名称">
							  		<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
						  		</span>
							</div>
							<div class="col-sm-4 text-right">
							  	<div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增脚本</a>
							  		<a href="${webroot}/prjDs/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm" target="_blank">配置数据源</a>
							  	</div>
							</div>
						</div>
				  	</div>
					<div id="infoPanel" class="table-panel"></div>
					<div id="infoPage" class="table-page-panel"></div>
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
				                         '<th>备注</th>',
				                         '<th width="80">是否升级</th>',
				                         '<th width="200">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjVersionScript/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size,
					prjId:'${param.prjId}', version: '${param.version}', dsCode: $('#dsCode').val()
				},
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _isUpCls = '';
							if(obj.isUp === <%=Boolean.TRUE.getCode()%>) {
								_isUpCls = ' class="text-success"';
							} else {
								_isUpCls = ' class="text-danger"';
							}
							return ['<tr>',
							    	'<td>',obj.remark,'</td>',
							    	'<td><span',_isUpCls,'>',obj.isUpName,'</span></td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.pvsId,')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.pvsId,')" title="删除"></a>',
							    	'&nbsp; &nbsp;&nbsp;<a class="glyphicon text-success" href="javascript:info.up(',obj.pvsId,')" title="执行升级的sql">执行升级</a>',
							    	'&nbsp; &nbsp;&nbsp;<a class="glyphicon text-success" href="javascript:info.callback(',obj.pvsId,')" title="执行回滚的sql">执行回滚</a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑脚本
		edit : function(pvsId) {
			dialog({
				title: '编辑脚本',
				url: webroot + '/prjVersionScript/f-view/edit.shtml?prjId=${param.prjId}&version=${param.version}&pvsId='+(pvsId?pvsId:''),
				type: 'iframe',
				width: 460,
				height: 490
			});
		},
		del : function(pvsId) {
			if(confirm('您确定要删除该脚本吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjVersionScript/f-json/delete.shtml',
					data : { pvsId: pvsId },
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
		//执行升级sql
		up: function(pvsId) {
			if(confirm('您确定要升级该脚本吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjVersionScript/f-json/exec.shtml',
					data : { pvsId: pvsId, type: 'up' },
					success : function(json) {
						if (json.code === 0) {
							alert('升级成功<br/>' + json.body);
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
		//执行回滚sql
		callback: function(pvsId) {
			if(confirm('您确定要回滚该脚本吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjVersionScript/f-json/exec.shtml',
					data : { pvsId: pvsId, type: 'callback' },
					success : function(json) {
						if (json.code === 0) {
							alert('回滚成功<br/>' + json.body);
							info.loadInfo(1);
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		}
};
$(function() {
	info.loadInfo(1);
	$('#prjId').change(function() {
		info.loadInfo(1);
	});
});
</script>
</body>
</html>