<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目管理</title>
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
				<div class="panel-heading">项目 / <b>项目管理</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增项目</a>
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
				                         '<th>编号</th>',
				                         '<th>名称</th>',
				                         '<th>状态</th>',
				                         '<th>发布的版本号</th>',
				                         '<th>创建时间</th>',
				                         '<th width="200">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjInfo/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							return ['<tr>',
							    	'<td>',obj.code,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td>',obj.statusName,'</td>',
							    	'<td>',obj.releaseVersion,'</td>',
							    	'<td>',obj.createTime,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.prjId,')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.prjId,')" title="删除"></a>',
							    	'&nbsp; |&nbsp; <a class="glyphicon text-success" href="javascript:info.monitor(',obj.prjId,')" title="查看项目的监控">查看监控</a>',
							    	'&nbsp; |&nbsp; <a class="glyphicon text-success" href="javascript:info.version(',obj.prjId,')" title="版本发布管理">版本发布</a>',
							    	//'&nbsp; <a class="glyphicon text-success" href="javascript:info.cli(',obj.prjId,')" title="发到对应的客户端">发布到客户端</a>',
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
				title: '编辑项目',
				url: webroot + '/prjInfo/f-view/edit.shtml?prjId='+(id?id:''),
				type: 'iframe',
				width: 600,
				height: 580
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该项目吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjInfo/f-json/delete.shtml',
					data : { prjId: id },
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
		//查看监控
		monitor : function(prjId) {
			location = '${webroot}/prjMonitor/f-view/manager.shtml?prjId=' + prjId;
		}
};
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>