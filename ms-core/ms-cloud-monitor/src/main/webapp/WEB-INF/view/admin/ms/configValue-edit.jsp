<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/sys.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-配置文件属性管理</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<jsp:include page="/WEB-INF/view/admin/comm/left.jsp">
			<jsp:param name="first" value="ms"/>
			<jsp:param name="second" value="msConfigManager"/>
		</jsp:include>
		<div class="c-right">
			<div class="panel panel-success">
				<div class="panel-heading panel-heading-tool">
					<div class="row">
						<div class="col-sm-5 title">微服务 / 配置文件管理 / <b>配置文件属性管理</b></div>
						<div class="col-sm-7 text-right">
							<div class="btn-group">
						  		<a href="${webroot}/msConfig/f-view/manager.shtml?name=${param.name}" class="btn btn-default btn-sm">返回</a>
						  		<a href="javascript:location.reload()" class="btn btn-default btn-sm">刷新</a>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body">
					<div id="infoPanel">
						<input type="hidden" id="configId" value="${param.configId}">
						<table class="table table-striped table-hover">
							<thead>
							<tr class="info">
	                        <th width="300">key</th>
	                        <th>value</th>
	                        <th>备注</th>
	                        <th width="150">操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        	<c:forEach items="${values}" var="info">
	                        	<tr class="value-list">
	                        		<td>
	                        			<div class="input-group">
		                        			<input type="text" name="code" class="form-control input-sm" placeholder="key" value="${info.code}"/>
		                        			<div class="input-group-btn">
											  	<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">常用 <span class="caret"></span>
											  	</button>
											  	<ul class="dropdown-menu">
													<li><a href="javascript:;" onclick="info.setUse(this, 'spring.application.name', 'test')">application的名称</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'server.port', '8080')">服务端口</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'eureka.instance.preferIpAddress', 'true')">实例名称显示ip</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'eureka.client.serviceUrl.defaultZone', 'http://cjhxRc1:5201/eureka/,http://cjhxRc2:5202/eureka/')">注册中心地址</a></li>
												    <li role="separator" class="divider"></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.jackson.date-format', 'yyyy-MM-dd HH:mm:ss')">日期展示格式</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.jackson.time-zone', 'GMT+8')">时区</a></li>
												    <li role="separator" class="divider"></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.view.prefix', '/WEB-INF/view/')">spring视图前缀</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.view.suffix', '.jsp')">spring视图后缀</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.thymeleaf.cache', 'false')">关闭thymeleaf缓存</a></li>
												    <li><a href="javascript:;" onclick="info.setUse(this, 'spring.thymeleaf.enabled', 'false')">关闭thymeleaf模板</a></li>
												</ul>
											</div>
										</div>
	                        		</td>
	                        		<td><input type="text" name="value" class="form-control input-sm" placeholder="值" value="${info.value}"/></td>
	                        		<td><input type="text" name="remark" class="form-control input-sm" placeholder="描叙" value="${info.remark}"/></td>
	                        		<td>
	                        			<a href="javascript:;" class="glyphicon glyphicon-arrow-up text-success" onclick="info.up(this)" title="上移"></a>
	                        			&nbsp;<a href="javascript:;" class="glyphicon glyphicon-arrow-down text-success" onclick="info.down(this)" title="下移"></a>
	                        			&nbsp;&nbsp;<a href="javascript:;" class="glyphicon text-success" onclick="info.del(this)">删除</a>
	                        		</td>
	                        	</tr>
	                        	</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-sm-3 text-left">
								<div class="btn-group">
									<button onclick="info.imp()" class="btn btn-default btn-sm" id="impBtn">导入</button>
									<button onclick="info.exp()" class="btn btn-default btn-sm" id="expBtn">导出</button>
								</div>
							</div>
							<div class="col-sm-3 text-right">
								<div class="btn-group">
								  <button type="button" class="btn btn-default" onclick="info.add()">新增属性</button>
								  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								    &nbsp;<span class="caret"></span>&nbsp;
								    <span class="sr-only">Toggle Dropdown</span>
								  </button>
								  <ul class="dropdown-menu">
								  <c:forEach items="${configs}" var="info">
								  	<li><a href="javascript:info.loadValues(${info.configId});">从${info.name}中加载</a></li>
								  </c:forEach>
								  </ul>
								</div>
							</div>
							<div class="col-sm-6 text-left">
								<div class="btn-group">
							  		<button onclick="info.save()" class="btn btn-success" id="saveBtn">确认保存</button>
								</div>
							</div>
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
var info = {
		//编辑
		add : function(code, value, remark) {
			if(code === undefined) {
				code = '';
			}
			if(value === undefined) {
				value = '';
			}
			if(remark === undefined) {
				remark = '暂无';
			}
			var _table = $('#infoPanel').find('table');
			_table.append(['<tr class="value-list">',
                   		'<td>',
			             '  <div class="input-group">',
							'<input type="text" name="code" class="form-control input-sm" placeholder="key" value="',code,'"/>',
							'<div class="input-group-btn">',
							'  	<button type="button" class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">常用 <span class="caret"></span>',
							 ' 	</button>',
							  '	<ul class="dropdown-menu">',
								'	<li><a href="javascript:;" onclick="info.setUse(this, \'spring.application.name\', \'test\')">application的名称</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'server.port\', \'8080\')">服务端口</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'eureka.instance.preferIpAddress\', \'true\')">实例名称显示ip</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'eureka.client.serviceUrl.defaultZone\', \'http://cjhxRc1:5201/eureka/,http://cjhxRc2:5202/eureka/\')">注册中心地址</a></li>',
								'    <li role="separator" class="divider"></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.jackson.date-format\', \'yyyy-MM-dd HH:mm:ss\')">日期展示格式</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.jackson.time-zone\', \'GMT+8\')">时区</a></li>',
								'    <li role="separator" class="divider"></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.view.prefix\', \'/WEB-INF/view/\')">spring视图前缀</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.view.suffix\', \'.jsp\')">spring视图后缀</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.thymeleaf.cache\', \'false\')">关闭thymeleaf缓存</a></li>',
								'    <li><a href="javascript:;" onclick="info.setUse(this, \'spring.thymeleaf.enabled\', \'false\')">关闭thymeleaf模板</a></li>',
								'</ul>',
							'</div>',
						'</div>',
							'</td>',
                		'<td><input type="text" name="value" class="form-control input-sm" placeholder="值" value="',value,'"/></td>',
                		'<td><input type="text" name="remark" class="form-control input-sm" placeholder="描叙" value="',remark,'"/></td>',
                		'<td><a href="javascript:;" class="glyphicon glyphicon-arrow-up text-success" onclick="info.up(this)" title="上移"></a>',
            			'&nbsp;<a href="javascript:;" class="glyphicon glyphicon-arrow-down text-success" onclick="info.down(this)" title="下移"></a>',
            			'&nbsp;&nbsp;<a href="javascript:;" class="glyphicon text-success" onclick="info.del(this)">删除</a></td>',
                	'</tr>'].join(''));
		},
		up : function(_this) {
			var _code = $(_this).parent().parent().find('input[name="code"]');
			var _value = $(_this).parent().parent().find('input[name="value"]');
			var _remark = $(_this).parent().parent().find('input[name="remark"]');
			
			var _upCode = '';
			var _upValue = '';
			var _upRemark = '';
			$('.value-list').each(function(i, obj) {
				if($(obj).find('input[name="code"]').val() == _code.val()) {
					if(_upCode === '') {
						return false;
					}
					var _orgCode = _code.val(), _orgUpCode = _upCode.val();
					_code.val(_orgUpCode);
					_upCode.val(_orgCode);
					var _orgValue = _value.val(), _orgUpValue = _upValue.val();
					_value.val(_orgUpValue);
					_upValue.val(_orgValue);
					var _orgRemark = _remark.val(), _orgUpRemark = _upRemark.val();
					_remark.val(_orgUpRemark);
					_upRemark.val(_orgRemark);
					return false;
				}
				_upCode = $(obj).find('input[name="code"]');
				_upValue = $(obj).find('input[name="value"]');
				_upRemark = $(obj).find('input[name="remark"]');
			});
		},
		down : function(_this) {
			var _code = $(_this).parent().parent().find('input[name="code"]');
			var _value = $(_this).parent().parent().find('input[name="value"]');
			var _remark = $(_this).parent().parent().find('input[name="remark"]');
			
			var _upCode = '';
			var _upValue = '';
			var _upRemark = '';
			var _isUse = false;
			$('.value-list').each(function(i, obj) {
				_upCode = $(obj).find('input[name="code"]');
				_upValue = $(obj).find('input[name="value"]');
				_upRemark = $(obj).find('input[name="remark"]');
				if(_isUse) {
					var _orgCode = _code.val(), _orgUpCode = _upCode.val();
					_code.val(_orgUpCode);
					_upCode.val(_orgCode);
					var _orgValue = _value.val(), _orgUpValue = _upValue.val();
					_value.val(_orgUpValue);
					_upValue.val(_orgValue);
					var _orgRemark = _remark.val(), _orgUpRemark = _upRemark.val();
					_remark.val(_orgUpRemark);
					_upRemark.val(_orgRemark);
					return false;
				}
				if($(obj).find('input[name="code"]').val() == _code.val()) {
					_isUse = true;
				}
			});
		},
		loadValues : function(configId) {
			JUtil.ajax({
				url : '${webroot}/msConfigValue/f-json/findByConfigId.shtml',
				data : {
					configId: configId
				},
				success : function(json) {
					if (json.code === 0) {
						$.each(json.body, function(i, obj) {
							info.add(obj.code, obj.value, obj.remark);
						});
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
				}
			});
		},
		del : function(_this) {
			$(_this).parent().parent().remove();
			/* if(confirm('您确定要删除该属性吗?')) {
				$(_this).parent().parent().remove();
			} */
		},
		//设置使用
		setUse : function(_this, key, value) {
			$(_this).parent().parent().parent().parent().parent().parent().find('input[name="code"]').val(key);
			var _value = $(_this).parent().parent().parent().parent().parent().parent().find('input[name="value"]');
			if(JUtil.isEmpty(_value.val())) {
				_value.val(value);
			}
			var _remark = $(_this).parent().parent().parent().parent().parent().parent().find('input[name="remark"]');
			//if(JUtil.isEmpty(_remark.val())) {
			_remark.val($(_this).text());
			//}
		},
		save : function() {
			var _code = [];
			$('input[name="code"]').each(function(i, obj) {
				_code.push($(obj).val());
			});
			var _value = [];
			$('input[name="value"]').each(function(i, obj) {
				_value.push($(obj).val());
			});
			var _remark = [];
			$('input[name="remark"]').each(function(i, obj) {
				_remark.push($(obj).val());
			});
			var _saveBtn = $('#saveBtn');
			var _orgVal = _saveBtn.html();
			_saveBtn.attr('disabled', 'disabled').html('保存中...');
			JUtil.ajax({
				url : '${webroot}/msConfigValue/f-json/save.shtml',
				data : {
					configId: $('#configId').val(),
					code: _code.join('#~@#'),
					value: _value.join('#~@#'),
					remark: _remark.join('#~@#'),
					isUse: _remark.join('#~@#'),
					regex: '#~@#'
				},
				success : function(json) {
					if (json.code === 0) {
						message('保存成功');
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
					_saveBtn.removeAttr('disabled').html(_orgVal);
				}
			});
		},
		//导入
		imp: function() {
			var _content = ['<div><div class="form-group"><textarea id="impContent" class="form-control" style="width: 350px;height: 230px;"></textarea></div>'];
			_content.push('<div class="form-group">',
		 			'<div class="btn-group">',
					'<button type="button" id="saveImpBtn" class="btn btn-success enter-fn">保存</button>',
				'</div>&nbsp;',
				'<span id="saveImpMsg" class="label label-danger"></span>',
			'</div>');
			_content.push('</div>');
			dialog({
				content: _content.join(''),
				width: 385,
				height: 250
			});
			$('#saveImpBtn').click(function() {
				var _saveMsg = $('#saveImpMsg').empty();
				
				var _content = $('#impContent');
				if(JUtil.isEmpty(_content.val())) {
					_saveMsg.append('请输入内容');
					_content.focus();
					return;
				}
				var _saveBtn = $('#saveBtn');
				var _orgVal = _saveBtn.html();
				_saveBtn.attr('disabled', 'disabled').html('保存中...');
				JUtil.ajax({
					url : '${webroot}/msConfigValue/f-json/imp.shtml',
					data : {
						configId: $('#configId').val(),
						content: _content.val()
					},
					success : function(json) {
						if (json.code === 0) {
							_saveMsg.attr('class', 'label label-success').append('保存成功');
							setTimeout(function() {
								location.reload();
							}, 800);
						}
						else if (json.code === -1)
							_saveMsg.append(JUtil.msg.ajaxErr);
						else
							_saveMsg.append(json.message);
						_saveBtn.removeAttr('disabled').html(_orgVal);
					}
				});
			});
		},
		//导出
		exp: function() {
			JUtil.ajax({
				url : '${webroot}/msConfigValue/f-json/findByConfigId.shtml',
				data : {
					configId: $('#configId').val()
				},
				success : function(json) {
					if (json.code === 0) {
						var _content = ['<div><textarea class="form-control" style="width: 350px;height: 230px;" onclick="$(this).select()">'];
						$.each(json.body, function(i, obj) {
							_content.push(obj.code, '#~@#', obj.value, '#~@#', obj.remark, '#~end@#');
						});
						_content.push('</textarea></div>');
						dialog({
							content: _content.join(''),
							width: 385,
							height: 250
						});
					}
					else if (json.code === -1)
						message(JUtil.msg.ajaxErr);
					else
						message(json.message);
				}
			});
		}
};
$(function() {
});
</script>
</body>
</html>