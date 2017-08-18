<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目管理-数据源</title>
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
						<div class="col-sm-5 title">项目 / 项目管理 / <b>数据源</b></div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="${webroot}/prjInfo/f-view/manager.shtml?name=${param.name}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div class="table-tool-panel">
						<div class="row">
							<div class="col-sm-6">
							</div>
							<div class="col-sm-6 text-right">
							  	<div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增数据源</a>
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
				                         '<th width="120">编码</th>',
				                         '<th width="80">类型</th>',
				                         '<th>jdbc的URL</th>',
				                         '<th width="120">用户名</th>',
				                         '<th width="150">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjDs/f-json/pageQuery.shtml',
				data : { prjId: '${param.prjId}', page:infoPage.page, size:infoPage.size },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _statusName = '';
							return ['<tr>',
							    	'<td>',obj.code,'</td>',
							    	'<td>',obj.type,'</td>',
							    	'<td>',obj.url,'</td>',
							    	'<td>',obj.username,'</td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(\'',obj.code,'\')" title="修改"></a>',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj.code,'\')" title="删除"></a>',
							    	'&nbsp; &nbsp;&nbsp; <a class="glyphicon text-success" href="javascript:info.test(\'',obj.code,'\')" title="测试链接信息是否成功">测试连接</a>',
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
		edit : function(code) {
			dialog({
				title: '编辑数据源',
				url: webroot + '/prjDs/f-view/edit.shtml?prjId=${param.prjId}&code='+(code?code:''),
				type: 'iframe',
				width: 430,
				height: 450
			});
		},
		test : function(code) {
			JUtil.ajax({
				url : '${webroot}/prjDs/f-json/test.shtml',
				data : {
					prjId: '${param.prjId}',
					code: code,
				},
				success : function(json) {
					if (json.code === 0) {
						message('执行测试语句成功');
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
				}
			});
		},
		del : function(code) {
			if(confirm('您确定要删除该数据源吗?')) {
				JUtil.ajax({
					url : '${webroot}/prjDs/f-json/delete.shtml',
					data : { prjId: '${param.prjId}', code: code },
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
$(function() {
	info.loadInfo(1);
});
</script>
</body>
</html>