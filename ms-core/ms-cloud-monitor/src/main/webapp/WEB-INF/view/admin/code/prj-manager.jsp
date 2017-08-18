<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目管理-源码管理</title>
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
						<div class="col-sm-5 title">项目 / 项目管理 / <b>源码管理</b></div>
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
								  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增模板</a>
								  	</div>
								</div>
							</div>
							</div>
							<div id="infoPanel" class="table-panel"></div>
							<div id="infoPage" class="table-page-panel"></div>
				  		</div>
				  		<div class="col-sm-7" id="createInfo">
				  			<div class="table-tool-panel">
					  			<div class="row">
									<div class="col-sm-4 text-muted" id="createTitle">
									</div>
									<div class="col-sm-8 text-right">
									  	<div class="btn-group">
									  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="create.edit()">生成源码</a>
									  	</div>
									  	<div class="btn-group">
							  				<a href="${webroot}/prjDs/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm" target="_blank">配置数据源</a>
									  	</div>
									</div>
								</div>
						  	</div>
							<div id="createPanel" class="table-panel"></div>
							<div id="createPage" class="table-page-panel"></div>
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
				                         '<th width="80">类型</th>',
				                         '<th width="150">名称</th>',
				                         '<th>描述</th>',
				                         '<th width="120">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/codeTemplate/f-json/pageQuery.shtml',
				data : { prjId: '${param.prjId}', code: '${param.prjId}', page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _statusName = '';
							return ['<tr>',
							    	'<td>',obj.typeName,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td style="word-break:break-all">',obj.remark,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(\'',obj.code,'\',\'',obj.name,'\')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj.code,'\',\'',obj.name,'\')" title="删除"></a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑数据源
		edit : function(code, name) {
			dialog({
				title: '编辑数据源',
				url: webroot + '/codeTemplate/f-view/edit.shtml?prjId=${param.prjId}&code='+(code?code:'') + '&name='+(name?name:''),
				type: 'iframe',
				width: 430,
				height: 500
			});
		},
		del : function(code, name) {
			if(confirm('您确定要删除该模板吗?')) {
				JUtil.ajax({
					url : '${webroot}/codeTemplate/f-json/delete.shtml',
					data : { code: code, name: name },
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
		}
};

var createPage = undefined;
var create = {
		loadInfo : function(page) {
			//$('#createTitle').html('<small>发布的版本号：</small><span class="label label-warning">' + client.version + '</span>');
			if(!createPage) {
				createPage = new Page('createPage', create.loadInfo, 'createPanel', 'createPage');
				createPage.beginString = ['<table class="table table-striped table-hover"><thead><tr class="info">',
				                         '<th width="80">编号</th>',
				                         '<th width="120">包路径</th>',
				                         '<th>表名</th>',
				                         '<th width="110">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				createPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				createPage.page = page;

			JUtil.ajax({
				url : '${webroot}/codeCreate/f-json/pageQuery.shtml',
				data : { code: '${param.prjId}', page:createPage.page, size:createPage.size },
				beforeSend: function(){ createPage.beforeSend('加载信息中...'); },
				error : function(json){ createPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _finish = '';
							if(obj.finishTime) {
								_finish = '&nbsp; <a class="glyphicon text-success" href="javascript:create.download('+obj.id+')" title="点击下载源码">下载源码</a>';
							}
							
							return ['<tr>',
							    	'<td>',obj.id,'<br/>',
							    	'<td>',obj.packagePath,'</td>',
							    	'<td style="word-break:break-all">',obj.tables,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:create.edit(',obj.id,')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:create.del(',obj.id,')" title="删除"></a>',
							    	_finish,
							    	'</td>',
								'</tr>'].join('');
						}
						createPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑
		edit : function(id) {
			dialog({
				title: '编辑生成源码',
				url: webroot + '/codeCreate/f-view/edit.shtml?prjId=${param.prjId}&code=${param.prjId}&id='+(id?id:''),
				type: 'iframe',
				width: 400,
				height: 350
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该源码吗?')) {
				JUtil.ajax({
					url : '${webroot}/codeCreate/f-json/delete.shtml',
					data : { id: id },
					success : function(json) {
						if (json.code === 0) {
							message('删除成功');
							create.loadInfo(1);
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		},
		download: function(id) {
			window.open(webroot + '/codeCreate/f-view/download?id=' + id);
		}
};
$(function() {
	info.loadInfo(1);
	create.loadInfo(1);
});
</script>
</body>
</html>