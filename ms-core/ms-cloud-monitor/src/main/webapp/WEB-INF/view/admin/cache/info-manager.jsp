<%@page import="com.ms.env.Env"%>
<%@page import="com.ms.env.EnvUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-缓存管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="prj"/>
			<jsp:param name="second" value="cacheInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">项目 / <b>缓存管理</b></div>
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
							<div class="col-sm-11">
								<div class="enter-panel">
									<div class="btn-group">
									<my:select id="prjId" items="${prjInfos}" cssCls="form-control input-sm" value="${param.prjId}"/>
									</div>
									<input type="text" style="width: 250px;display: inline;" class="form-control input-sm" id="key" placeholder="key(abc*代表获取abc开头的key列表)" value="">
							  		<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
							  		<button type="button" class="btn btn-sm btn-danger" onclick="info.delBatch()">批量删除</button>
						  		</div>
							</div>
							<div class="col-sm-1 text-right">
							  	<!-- <div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增缓存</a>
							  	</div> -->
							</div>
						</div>
				  	</div>
					<div id="infoPanel" class="table-panel"><p class="text-muted" style="padding: 10px;">请输入key搜索</p></div>
					<div id="infoPage" class="table-page-panel"></div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
<script type="text/javascript">
var info = {
		//获取用户信息
		loadInfo : function() {
			var panel = $('#infoPanel').empty();
			var prjId = $('#prjId').val();
			if(JUtil.isEmpty(prjId)) {
				alert('请选择项目');
				return;
			}
			var key = $('#key').val();
			if(JUtil.isEmpty(key)) {
				alert('请输入key');
				return;
			}
			JUtil.ajax({
				url : '${webroot}/cacheInfo/f-json/findKey.shtml',
				data : { prjId: prjId, key: key },
				error : function(json){ alert('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						var cont = ['<table class="table table-striped table-hover"><thead><tr class="info">',
                         '<th>key</th>',
                         '<th width="100">操作</th>',
                         '</tr></thead><tbody>'];
						if(json.body.length > 0) {
							$.each(json.body, function(i, obj) {
								cont.push('<tr>',
								    	'<td>',obj,'</td>',
								    	'<td><a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(\'',obj,'\')" title="删除该key"></a>',
								    	'</td>',
									'</tr>');
							});
						} else {
							cont.push('<tr><td colspan="5" align="center">没有记录</td>',
							    	'</td></tr>');
						}
						cont.push('</tbody></table>');
						panel.append(cont.join(''));
						$('#infoPage').html('<small class="text-muted">共' + json.body.length + '条记录</small>');
					} else if(json.code == '10002') panel.append('<label class="label label-danger">暂时没有开通redis的管理功能</label>');
					else panel.append('<label class="label label-danger">' + JUtil.msg.ajaxErr + '</label>');
				}
			});
		},
		//批量删除
		delBatch : function() {
			if(confirm('您确定要批量删除该Key吗?')) {
				var prjId = $('#prjId').val();
				if(JUtil.isEmpty(prjId)) {
					alert('请选择项目');
					return;
				}
				JUtil.ajax({
					url : '${webroot}/cacheInfo/f-json/deleteBatch.shtml',
					data : { prjId: prjId, key: $('#key').val() },
					success : function(json) {
						if (json.code === 0) {
							message('批量删除成功');
							info.loadInfo();
						}
						else if (json.code === -1)
							message(JUtil.msg.ajaxErr);
						else
							message(json.message);
					}
				});
			}
		},
		//删除
		del : function(key) {
			if(confirm('您确定要删除该Key吗?')) {
				var prjId = $('#prjId').val();
				if(JUtil.isEmpty(prjId)) {
					alert('请选择项目');
					return;
				}
				JUtil.ajax({
					url : '${webroot}/cacheInfo/f-json/delete.shtml',
					data : { prjId: prjId, key: key },
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
});
</script>
</body>
</html>