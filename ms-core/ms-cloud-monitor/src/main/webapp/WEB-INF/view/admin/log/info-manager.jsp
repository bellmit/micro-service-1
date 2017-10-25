<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-日志管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="prj"/>
			<jsp:param name="second" value="logInfoManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">项目 / <b>日志管理</b></div>
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
							<div class="col-sm-9">
								<div class="enter-panel">
									<div class="btn-group">
									<my:select id="prjId" items="${prjInfos}" cssCls="form-control input-sm" value="${param.prjId}" exp="onchange=\"info.loadInfo()\""/>
									</div>
									<select id="key" class="form-control input-sm" style="width: 100px;display: inline;" onchange="info.loadInfo()">
										<option value="INFO">INFO</option>
										<option value="ERROR">ERROR</option>
									</select>
									<input type="text" style="width: 150px;display: inline;" class="form-control input-sm" id="beginTime" placeholder="开始时间" value="">
									<input type="text" style="width: 150px;display: inline;" class="form-control input-sm" id="endTime" placeholder="结束时间" value="">
							  		<button type="button" class="btn btn-sm btn-default enter-fn" onclick="info.loadInfo(1)">查询</button>
						  		</div>
							</div>
							<div class="col-sm-3 text-right">
							  	<!-- <div class="btn-group">
							  		<a href="javascript:;" class="btn btn-success btn-sm" onclick="info.edit()">新增密钥</a>
							  	</div> -->
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
			JUtil.ajax({
				url : '${webroot}/logInfo/f-json/find.shtml',
				data : { prjId: prjId, key: $('#key').val(), beginTime: $('#beginTime').val(),endTime: $('#endTime').val() },
				error : function(json){ alert('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						var cont = ['<table class="table table-striped table-hover"><thead><tr class="info">',
                         '<th>traceId</th>',
                         '<th>key</th>',
                         '<th>value</th>',
                         '<th>创建时间</th>',
                         '<th width="100">操作</th>',
                         '</tr></thead><tbody>'];
						if(json.body.length > 0) {
							$.each(json.body, function(i, obj) {
								cont.push('<tr>',
								    	'<td>',obj.traceId,'</td>',
								    	'<td>',obj.aKey,'</td>',
								    	'<td>',obj.aValue,'</td>',
								    	'<td>',obj.aTime,'</td>',
								    	'<td><a class="glyphicon text-success" href="javascript:info.look(\'',obj.traceIdHex,'\')" title="查看调度链">查看</a>',
								    	'</td>',
									'</tr>');
							});
						} else {
							cont.push('<tr><td colspan="5" align="center">没有日志记录</td>',
							    	'</td></tr>');
						}
						cont.push('</tbody></table>');
						panel.append(cont.join(''));
						$('#infoPage').html('<small class="text-muted">共' + json.body.length + '条记录</small>');
					}
					else panel.append('<label class="label label-danger">' + JUtil.msg.ajaxErr + '</label>');
				}
			});
		},
		//查看
		look : function(id) {
			window.open('http://127.0.0.1:7400/traces/'+(id?id:''));
		}
};
$(function() {
	$('#beginTime').val(JUtil.date.formatStr(JUtil.date.getDateAddHour(JUtil.date.getDate(), -24 * 3)));
	$('#endTime').val(JUtil.date.formatStr(JUtil.date.getDate()));
	info.loadInfo();
});
</script>
</body>
</html>