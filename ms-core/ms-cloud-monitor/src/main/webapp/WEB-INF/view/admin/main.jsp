<%@page import="com.module.admin.prj.enums.PrjInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-${user.nickname}</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp"></jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading">欢迎您 <b><a href="${webroot}/sysUser/f-view/main.shtml">${user.nickname}</a></b></div>
				<div class="panel-body">
					<div class="row" id="infoPanel"></div>
					<div id="infoPage" class="table-page-panel"></div>
				</div>
			</div>
		</div>
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
				/* infoPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th>编号</th>',
				                         '<th>名称</th>',
				                         '<th>状态</th>',
				                         '<th>创建时间</th>',
				                         '<th width="230">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>'; */
				infoPage.size = 16;
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjInfo/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, name: $('#name').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _statusCls = '';
							if(obj.status === <%=PrjInfoStatus.NORMAL.getCode()%>) {
								_statusCls = ' class="text-success"';
							} else {
								_statusCls = ' class="text-danger"';
							}
							return ['<div class="col-sm-6 col-md-3">',
								    '<div class="thumbnail">',
								    '  <div class="caption">',
								    '    <p><b>',obj.name,'</b> <small',_statusCls,'>(',obj.statusName,')</small></p>',
								    '    <p class="text-muted">',obj.code,'</p>',
								    '    <div><a href="javascript:;" class="btn btn-success btn-xs" onclick="info.api(',obj.prjId,',\'',obj.name,'\')">API</a> ',
								    '		&nbsp; <a href="javascript:;" class="btn btn-warning btn-xs" onclick="info.version(',obj.prjId,',\'',obj.name,'\')">版本管理</a>',
								    '  &nbsp; <span class="dropdown opt-more">',
									'<a class="btn btn-default btn-xs dropdown-toggle" href="javascript:;" data-toggle="dropdown">更多...</a>',
									'<ul class="dropdown-menu" role="menu">',
									'<li role="presentation"><a href="javascript:info.ds(',obj.prjId,',\'',obj.name,'\')">数据源</a></li>',
									'<li role="presentation"><a href="javascript:info.monitor(',obj.prjId,',\'',obj.name,'\')" title="查看项目的监控">查看监控</a></li>',
									'<li role="presentation"><a href="javascript:info.autoCode(',obj.prjId,',\'',obj.name,'\')">生成源码</a></li>',
									'</ul>',
									'</span></div>',
									'</div>',
								    '</div>',
								  '</div>'].join('');
							/* return ['<tr>',
							    	'<td>',obj.code,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td><span',_statusCls,'>',obj.statusName,'</span></td>',
							    	'<td>',obj.createTime,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.prjId,')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.prjId,')" title="删除"></a>',
							    	'&nbsp; &nbsp;&nbsp; <a class="glyphicon text-success" href="javascript:info.api(',obj.prjId,')" title="API列表">API</a>',
							    	'&nbsp; &nbsp;&nbsp; <a class="glyphicon text-success" href="javascript:info.version(',obj.prjId,')" title="版本发布管理">版本管理</a>',
							    	'&nbsp; &nbsp;&nbsp; <span class="dropdown opt-more">',
									'<a class="glyphicon text-success dropdown-toggle" href="javascript:;" data-toggle="dropdown">更多...</a>',
									'<ul class="dropdown-menu" role="menu">',
									'<li role="presentation"><a href="javascript:info.ds(',obj.prjId,')">数据源</a></li>',
									'<li role="presentation"><a href="javascript:info.monitor(',obj.prjId,')" title="查看项目的监控">查看监控</a></li>',
									'<li role="presentation"><a href="javascript:info.autoCode(',obj.prjId,')">生成源码</a></li>',
									'</ul>',
									'</span>',
							    	//'&nbsp; |&nbsp; <a class="glyphicon text-success" href="javascript:info.process(',obj.prjId,')" title="项目流程">详细</a>',
							    	'</td>',
								'</tr>'].join(''); */
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
						JUtil.sys.initOptMore();
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//版本管理
		version : function(prjId, name) {
			location = '${webroot}/prjVersion/f-view/manager.shtml?prjId=' + prjId + '&name=' + name;
		},
		//查看项目的api信息
		api : function(prjId, name) {
			location = '${webroot}/prjApi/f-view/manager.shtml?prjId=' + prjId + '&name=' + name;
		},
		//查看监控
		monitor : function(prjId, name) {
			location = '${webroot}/prjMonitor/f-view/manager.shtml?prjId=' + prjId + '&name=' + name;
		},
		//数据源
		ds : function(prjId, name) {
			location = '${webroot}/prjDs/f-view/manager.shtml?prjId=' + prjId + '&name=' + name;
		},
		//生成源码
		autoCode : function(prjId, name) {
			location = '${webroot}/codePrj/f-view/manager.shtml?prjId=' + prjId + '&name=' + name;
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>