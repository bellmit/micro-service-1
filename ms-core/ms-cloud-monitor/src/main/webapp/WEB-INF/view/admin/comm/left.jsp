<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<div class="c-left">
	<div class="panel-group">
		<div class="panel panel-info">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a href="${webroot}/sysUser/f-view/main.shtml">个人中心</a>
				</h4>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapseCli">客户端 <span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapseCli" class="panel-collapse collapse <c:if test="${param.first == 'cli'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'cliInfoManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/cliInfo/f-view/manager.shtml">客户端管理</a>
					</div>
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'cliInfoMonitor'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/cliInfo/f-view/monitor.shtml">客户端监控</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapsePrj">项目 <span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapsePrj" class="panel-collapse collapse <c:if test="${param.first == 'prj'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'prjInfoManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/prjInfo/f-view/manager.shtml">项目管理</a>
					</div>
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'prjMonitorManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/prjMonitor/f-view/manager.shtml">服务监控</a>
					</div>
				</div>
			</div>
		</div>
		<div class="panel panel-success">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapseMs">微服务<span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapseMs" class="panel-collapse collapse <c:if test="${param.first == 'ms'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'msConfigManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/msConfig/f-view/manager.shtml">配置文件管理</a>
					</div>
					<%-- <div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'cliInfoMonitor'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block" href="${webroot}/cliInfo/f-view/monitor.shtml">客户端监控</a>
					</div> --%>
				</div>
			</div>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title text-center">
					<a data-toggle="collapse" href="#mlCollapseSys">系统管理 <span
						class="caret" style="border-top-color: #468847;"></span></a>
				</h4>
			</div>
			<div id="mlCollapseSys" class="panel-collapse collapse <c:if test="${param.first == 'sys'}">in</c:if>">
				<div class="panel-body">
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'userManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block"
							href="${webroot}/sysUser/f-view/manager.shtml">用户管理</a>
					</div>
					<div>
						<a class="btn btn-<c:choose><c:when test="${param.second == 'configManager'}">info</c:when><c:otherwise>link</c:otherwise></c:choose> btn-block"
							href="${webroot}/sysConfig/f-view/manager.shtml">系统配置</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>