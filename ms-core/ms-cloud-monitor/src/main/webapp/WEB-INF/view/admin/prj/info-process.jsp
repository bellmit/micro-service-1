<%@page import="com.system.comm.enums.Boolean"%>
<%@page import="com.module.admin.prj.enums.PrjMonitorMonitorStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="my" uri="/WEB-INF/tld/my.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-项目流程</title>
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
				<div class="panel-heading">项目 / <b>项目流程</b></div>
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6">
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  		<a href="${webroot}/prjInfo/f-view/manager.shtml" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
				  	<div>
				  		<ul class="nav nav-tabs" role="tablist">
				  			<li role="presentation" class="active"><a href="#edit" role="tab" data-toggle="tab">配置shell命令</a></li>
				  			<li role="presentation" class=""><a href="#version" role="tab" data-toggle="tab">设置发布版本</a></li>
				  			<li role="presentation" class=""><a href="#publish" role="tab" data-toggle="tab">发布到服务</a></li>
				  			<li role="presentation" class=""><a href="#log" role="tab" data-toggle="tab">查看启动日志</a></li>
				  			<li role="presentation" class=""><a href="#monitor" role="tab" data-toggle="tab">查看服务监控</a></li>
						</ul>
				  	</div>
				  	<div class="tab-content" style="padding: 15px;">
					  	<div role="tabpanel" class="tab-pane fade active in" id="edit">
					    	<iframe src="${webroot}/prjInfo/f-view/edit.shtml?prjId=${prjInfo.prjId}&source=dtl" width="600px" height="500px" frameborder="0"></iframe>
					    </div>
					  	<div role="tabpanel" class="tab-pane fade" id="version">
					   	 设置发布版本
					    </div>
					  	<div role="tabpanel" class="tab-pane fade" id="publish">
					   	 发布到服务
					    </div>
					  	<div role="tabpanel" class="tab-pane fade" id="log">
					   	 查看启动日志
					    </div>
					  	<div role="tabpanel" class="tab-pane fade" id="monitor">
					   	 查看服务监控
					    </div>
				    </div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
<script type="text/javascript">
var infoPage = undefined;
var info = {
		selType: function(_this) {
			$(_this).parent().each(function(i, obj) {
				$(obj).find('a').attr('class', 'list-group-item');
			});
			$(_this).attr('class', 'list-group-item active');
		}
};
$(function() {
	
});
</script>
</body>
</html>