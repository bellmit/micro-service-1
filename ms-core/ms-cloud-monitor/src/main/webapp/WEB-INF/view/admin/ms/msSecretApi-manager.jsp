<%@page import="com.module.admin.prj.enums.PrjClientStatus"%>
<%@page import="com.system.comm.enums.Boolean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-密钥管理-API管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="ms"/>
			<jsp:param name="second" value="msSecretManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">微服务 / 密钥管理 / <b>API管理</b> (${msSecret.name})</div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="${webroot}/msSecret/f-view/manager.shtml" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
				  	<div class="table-tool-panel">
						<div class="row">
							<div class="col-sm-6 enter-panel">
								<div class="btn-group">
								<my:select id="prjId" items="${prjInfos}" headerKey="" headerValue="请选择项目" cssCls="form-control input-sm" value="${param.prjId}"/>
								</div>
								<input type="text" style="width: 200px;display: inline;" class="form-control input-sm" id="url" placeholder="API的地址" value="">
							  	<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
							</div>
							<div class="col-sm-6 text-right">
							  	<div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增API</a>
							  		<%-- <a href="${webroot}/sysConfig/f-view/manager.shtml?code=config." class="btn btn-default btn-sm">设置配置系统参数</a> --%>
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
				                         '<th>项目</th>',
				                         '<th>zuul拦截的编码</th>',
				                         '<th>地址</th>',
				                         '<th>创建时间</th>',
				                         '<th width="100">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/msSecretApi/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size, cliId: '${param.cliId}', prjId: $('#prjId').val(), url: $('#url').val() },
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							return ['<tr>',
							    	'<td>',obj.prjName,'</td>',
							    	'<td>',obj.prjCode,'</td>',
							    	'<td>',obj.url,'</td>',
							    	'<td>',obj.createTime,'</td>',
							    	'<td><a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj.cliId,'\',',obj.prjId,',\'',obj.url,'\')" title="删除"></a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
		},
		//编辑
		edit : function(id) {
			dialog({
				title: '编辑API',
				url: webroot + '/msSecretApi/f-view/edit.shtml?cliId=${msSecret.cliId}',
				type: 'iframe',
				width: 520,
				height: 450
			});
		},
		del : function(cliId, prjId, url) {
			if(confirm('您确定要删除该API吗?')) {
				JUtil.ajax({
					url : '${webroot}/msSecretApi/f-json/delete.shtml',
					data : { cliId: cliId, prjId: prjId, url: url },
					success : function(json) {
						if (json.code === 0) {
							message('删除成功');
							info.loadInfo();
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