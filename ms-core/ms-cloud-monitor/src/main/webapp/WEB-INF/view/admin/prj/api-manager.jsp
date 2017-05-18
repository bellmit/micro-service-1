<%@page import="com.system.comm.enums.Boolean"%>
<%@page import="com.module.admin.prj.enums.PrjMonitorMonitorStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-API</title>
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
				<div class="panel-heading">项目 / <b>API</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
							<div class="btn-group">
							<my:select id="prjId" items="${prjInfos}" headerKey="" headerValue="全部项目" cssCls="form-control" value="${param.prjId}"/>
							</div>
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  	</div>
						  	<div class="btn-group">
						  		<a href="${webroot}/prjInfo/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm">返回</a>
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
				                         '<th>地址</th>',
				                         '<th width="80">是否使用</th>',
				                         '<th width="450">方法详情</th>',
				                         '<th width="100">操作</th>',
				                         '</tr></thead><tbody>'].join('');
				infoPage.endString = '</tbody></table>';
			}
			if(page != undefined)
				infoPage.page = page;

			JUtil.ajax({
				url : '${webroot}/prjApi/f-json/pageQuery.shtml',
				data : { page:infoPage.page, size:infoPage.size,
					prjId:$('#prjId').val()
				},
				beforeSend: function(){ infoPage.beforeSend('加载信息中...'); },
				error : function(json){ infoPage.error('加载信息出错了!'); },
				success : function(json){
					if(json.code === 0) {
						function getResult(obj) {
							var _isUseCls = '';
							if(obj.isUse === <%=Boolean.FALSE.getCode()%>) {
								_isUseCls = ' class="text-danger"';
							} else {
								_isUseCls = ' class="text-success"';
							}
							return ['<tr>',
							    	'<td>',obj.path,'<br/>',obj.name,'</td>',
							    	'<td><span',_isUseCls,'>',obj.isUseName,'</span></td>',
							    	'<td style="word-wrap:break-word;word-break:break-all;">',obj.method,'</td>',
							    	'<td>',//<a class="glyphicon glyphicon-edit text-success" href="javascript:info.edit(',obj.prjmId,')" title="修改"></a>',
							    	//'&nbsp; <a class="glyphicon glyphicon-remove text-success" href="javascript:info.del(',obj.prjmId,')" title="删除"></a>',
							    	'</td>',
								'</tr>'].join('');
						}
						infoPage.operate(json.body, { resultFn:getResult, dataNull:'没有记录噢' });
					}
					else alert(JUtil.msg.ajaxErr);
				}
			});
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