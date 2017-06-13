<%@page import="com.module.admin.cli.enums.CliInfoStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-客户端管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="cli"/>
			<jsp:param name="second" value="cliInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">客户端 / <b>客户端管理</b></div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
				  	<div class="table-tool-panel">
						<div class="row">
							<div class="col-sm-6">
								<span class="enter-panel">
									<input type="text" style="width: 100px;display: inline;" class="form-control input-sm" id="searchString" placeholder="编码 / ip地址">
							  		<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
						  		</span>
							</div>
							<div class="col-sm-6 text-right">
							  	<div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增客户端</a>
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
				                         '<th>客户端编号</th>',
				                         '<th>名称</th>',
				                         '<th>IP : 端口</th>',
				                         '<th>状态</th>',
				                         '<th width="150">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/cliInfo/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, searchString: $('#searchString').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _statusCls = '';
							if(obj.status === <%=CliInfoStatus.NORMAL.getCode()%>) {
								_statusCls = ' class="text-success"';
							} else {
								_statusCls = ' class="text-danger"';
							}
							return ['<tr>',
							    	'<td>',obj.clientId,'</td>',
							    	'<td>',obj.name,'</td>',
							    	'<td>',obj.ip,' : ',obj.port,'</td>',
							    	'<td><span',_statusCls,'>',obj.statusName,'</span></td>',
							    	'<td><a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(\'',obj.clientId,'\')" title="修改"></a> ',
							    	'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj.clientId,'\')" title="删除"></a>',
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
				title: '编辑客户端',
				url: webroot + '/cliInfo/f-view/edit.shtml?clientId='+(id?id:''),
				type: 'iframe',
				width: 400,
				height: 470
			});
		},
		del : function(id) {
			if(confirm('您确定要删除该客户端吗?')) {
				JUtil.ajax({
					url : '${webroot}/cliInfo/f-json/delete.shtml',
					data : { clientId: id },
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