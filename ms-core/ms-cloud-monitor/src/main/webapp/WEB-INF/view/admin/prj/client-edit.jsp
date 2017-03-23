<%@page import="com.module.admin.sys.enums.SysFileType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/view/inc/sys.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑项目客户端</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body class="cld_body">
	<div class="enter-panel">
		<input type="hidden" id="prjId" value="${param.prjId}">
		
  		<div class="input-group has-success publish">
      		<input type="text" class="form-control" id="searchString" placeholder="输入客户端 [编号/ip地址] 都可搜索"/>
	      	<span class="input-group-btn">
	        	<button class="btn btn-success enter-fn" id="queryBtn">搜索</button>
	      	</span>
	    </div>
	    <hr />
	    <div id="listPanel"></div>
	</div>

	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
	<script type="text/javascript">
	var info = {
			save: function(_this, clientId) {
				$(_this).html('提交中');
				JUtil.ajax({
					url : '${webroot}/prjClient/f-json/save.shtml',
					data : {
						prjId: $('#prjId').val(),
						clientId: clientId
					},
					success : function(json) {
						if (json.code === 0) {
							setTimeout(function() {
								parent.info.loadInfo(1);
								parent.dialog.close();
							}, 800);
						}
						else if (json.code === -1)
							parent.message(JUtil.msg.ajaxErr);
						else
							parent.message(json.message);
						$(_this).html('确定');
					}
				});
			}
	};
	$(function() {
		$('#queryBtn').click(function() {
			JUtil.ajax({
				url : '${webroot}/cliInfo/f-json/find.shtml',
				data : {
					searchString: $('#searchString').val(), size: 5
				},
				success : function(json) {
					if (json.code === 0) {
						var _panel = $('#listPanel').empty();
						if(json.body.length > 0) {
							var _cont = ['<ul class="list-group">'];
							$.each(json.body, function(i, obj) {
								_cont.push('<li class="list-group-item">',
							             '<a class="badge" href="javascript:;" onclick="info.save(this, \'',obj.clientId,'\');">确定</a>',obj.ip,':',obj.port,'</li>');
							});
							_cont.push('</ul>');
							_panel.append(_cont.join(''));
						} else {
							_panel.append('<small class="text-success">未搜索到客户端</small>');
						}
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
				}
			});
		});
		
		$('#queryBtn').click();
	});
	</script>
</body>
</html>