<%@page import="com.system.comm.enums.Boolean"%>
<%@page import="com.module.admin.prj.enums.PrjMonitorMonitorStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-API</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${webroot}/resources/json-viewer/jquery.json-viewer.css">
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
							</div>
						</div>
						<div class="col-sm-6 text-right">
						  	<div class="btn-group">
						  	</div>
						  	<div class="btn-group">
						  		<a href="${webroot}/prjApi/f-view/manager.shtml?prjId=${param.prjId}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
						  	</div>
						</div>
					</div>
				  	<hr/>
					<div id="infoPanel">
						<div class="panel panel-warning">
							<div class="panel-heading">${prjApi.path} - ${prjApi.name}</div>
							<div class="panel-body">
								<div class="text-success">参数列表</div>
								<div>
									<input type="hidden" id="monitorPrjId" name="monitorPrjId" value="${prjApi.prjId}"/>
									<input type="hidden" id="monitorPath" name="monitorPath" value="${prjApi.path}"/>
									<table class="table table-striped table-hover">
										<thead><tr>
				                        <th width="100">参数</th>
				                        <th width="300">值</th>
				                        <th>描叙</th>
				                        <th width="300">类型</th>
				                        </tr>
				                        </thead>
				                        <tbody>
				                        <c:forEach items="${params}" var="info">
				                        	<c:choose>
				                        	<c:when test="${info.isShow=='0'}">
				                        	<tr style="display: none;">
				                        		<td colspan="4">
				                        			<input type="hidden" id="${info.code}" name="${info.code}" style="width: 200px;" value="${info.value}"/>
				                        		</td>
				                        	</tr>
				                        	</c:when>
				                        	<c:otherwise>
				                        	<tr>
				                        		<td>${info.code}<c:if test="${info.required=='true'}"> <span class="text-danger">*</span></c:if></td>
				                        		<td><input type="text" id="${info.code}" name="${info.code}" style="width: 200px;" value="${info.value}"/></td>
				                        		<td>${info.name}</td>
				                        		<td>${info.clazz}</td>
				                        	</tr>
				                        	</c:otherwise>
				                        	</c:choose>
				                        </c:forEach>
										</tbody>
									</table>
								</div>
								<hr/>
								<div class="form-group">
						 			<div class="btn-group">
										<button type="button" id="saveBtn" class="btn btn-success btn-sm">发送请求</button>
									</div>
									<span id="saveMsg" class="label label-danger"></span>
								</div>
								<hr/>
								<div id="resultPanel" class="form-group">
								</div>
							</div>
						</div>
					</div>
					<div id="infoPage"></div>
				</div>
			</div>
		</div>
		<br clear="all">
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
<script type="text/javascript" src="${webroot}/resources/json-viewer/jquery.json-viewer.js"></script>
<script type="text/javascript">

$(function() {
	$('#saveBtn').click(function() {
		var _saveMsg = $('#saveMsg').empty();
		_saveMsg.attr('class', 'label label-danger');
		var _data = {};
		$('input').each(function(i, obj) {
			_data[$(obj).attr('name')] = $(obj).val();
		});
		
		var _saveBtn = $('#saveBtn');
		var _orgVal = _saveBtn.html();
		_saveBtn.attr('disabled', 'disabled').html('请求中...');
		JUtil.ajax({
			url : '${webroot}/prjApi/f-json/request.shtml',
			data : _data,
			success : function(json) {
				if (json.code === 0) {
					_saveMsg.attr('class', 'label label-success').append('请求成功');
					var _res = ['<div class="text-success">请求地址</div>'];
					_res.push('<p class="alert alert-warning" style="padding: 5px;font-size: 12px;word-wrap: break-word;"><a href="',json.body.requestUrl,'" target="_blank" class="text-warning">',json.body.requestUrl,'</a></p>');
					_res.push('<div class="text-success">响应结果</div>');
					_res.push('<div id="resultInfoFormatPanel" class="alert alert-warning" style="padding: 5px 20px;"></div>');
					_res.push('<p id="resultInfoPanel" class="alert alert-warning" style="padding: 5px;font-size: 12px;">',JSON.stringify(json.body.result),'</p>');
					$('#resultPanel').html(_res.join(''));
					try {
				      var input = eval('(' + $('#resultInfoPanel').html() + ')');
				    }
				    catch (error) {
				      return alert("Cannot eval JSON: " + error);
				    }
				    var options = {
				    	//是否关闭
				      	collapsed: false,
				      	//是否加双引号
				      	withQuotes: true
				    };
				    $('#resultInfoFormatPanel').jsonViewer(input, options);
				}
				else if (json.code === -1)
					_saveMsg.append(JUtil.msg.ajaxErr);
				else
					_saveMsg.append(json.message);
				_saveBtn.removeAttr('disabled').html(_orgVal);
			}
		});
	});
});
</script>
</body>
</html>