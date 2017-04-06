<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${projectName}-帮助</title>
<jsp:include page="/WEB-INF/view/inc/css.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/header.jsp"></jsp:include>
	<div class="container">
		<div id="prjInfoParam" class="panel panel-success">
			<div class="panel-heading">添加项目的Shell的参数说明</div>
			<div class="panel-body">
	  			<ul>
		  			<li>[prj.path]:项目在客户端的路径。 值如：/home/www/1/</li>
		  			<li>[prj.name]:项目在客户端的名称包含后缀。 值如：omd.zip</li>
		  		</ul>
			</div>
	  	</div>
	  	
		<div id="prjInfoShell" class="panel panel-success">
			<div class="panel-heading">添加项目的Shell脚本的解析</div>
			<div class="panel-body">
	  			<ul>
		  			<li>容器为【linux: tomcat】的Shell的解析说明
			  			<ul>
				  			<li>cd /opt/apache-tomcat-7.0.32<br/>
				  				<small class="text-muted">将目录改变为tomcat主目录</small>
				  			</li>
				  			<li>./bin/shutdown.sh<br/>
				  				<small class="text-muted">停止tomcat服务</small>
				  			</li>
				  			<li>cp [prj.path][prj.name] /opt/apache-tomcat-7.0.32/webapps<br/>
				  				<small class="text-muted">复制客户端的项目到tomcat的webapps目录下</small>
				  			</li>
				  			<li>rm -rf /opt/apache-tomcat-7.0.32/webapps/test.zip /opt/apache-tomcat-7.0.32/webapps/test<br/>
				  				<small class="text-muted">删除tomcat下项目为test.zip和目录为test及其目录下的所有文件</small>
				  			</li>
				  			<li>mv /opt/apache-tomcat-7.0.32/webapps/[prj.name] /opt/apache-tomcat-7.0.32/webapps/test.zip<br/>
				  				<small class="text-muted">将tomcat下的要发布的项目的zip的名称改变为test.zip</small>
				  			</li>
				  			<li>cd /opt/apache-tomcat-7.0.32/webapps<br/>
				  				<small class="text-muted">进入tomcat的webapps目录下</small>
				  			</li>
				  			<li>unzip test.zip<br/>
				  				<small class="text-muted">解压test.zip项目</small>
				  			</li>
				  			<li>cd /opt/apache-tomcat-7.0.32<br/>
				  				<small class="text-muted">进入tomcat主目录</small>
				  			</li>
				  			<li>./bin/startup.sh<br/>
				  				<small class="text-muted">启动项目</small>
				  			</li>
				  		</ul><hr/>
		  			</li>
		  			
		  			<li>容器为【linux: 自定义服务】的Shell的解析说明
			  			<ul>
				  			<li>cd /home/www/project.restful-1.0.0<br/>
				  				<small class="text-muted">将目录改变为项目的主目录</small>
				  			</li>
				  			<li>./bin/stop.sh<br/>
				  				<small class="text-muted">停止项目的服务</small>
				  			</li>
				  			<li>cp [prj.path][prj.name] /home/www<br/>
				  				<small class="text-muted">复制客户端的项目到/home/www的目录下</small>
				  			</li>
				  			<li>rm -rf /home/www/project.restful-1.0.0.zip /home/www/project.restful-1.0.0<br/>
				  				<small class="text-muted">删除/home/www目录下项目为project.restful-1.0.0.zip和目录为project.restful-1.0.0及其目录下的所有文件</small>
				  			</li>
				  			<li>mv /home/www/[prj.name] /home/www/project.restful-1.0.0.zip<br/>
				  				<small class="text-muted">将/home/www下的要发布的项目的zip的名称改变为project.restful-1.0.0.zip</small>
				  			</li>
				  			<li>cd /home/www<br/>
				  				<small class="text-muted">进入/home/www目录下</small>
				  			</li>
				  			<li>unzip project.restful-1.0.0.zip<br/>
				  				<small class="text-muted">解压project.restful-1.0.0.zip项目</small>
				  			</li>
				  			<li>cd /home/www/project.restful-1.0.0<br/>
				  				<small class="text-muted">进入项目的主目录</small>
				  			</li>
				  			<li>chmod a+x bin/*<br/>
				  				<small class="text-muted">给项目的bin目录下的sh文件赋值可执行权限</small>
				  			</li>
				  			<li>./bin/start.sh<br/>
				  				<small class="text-muted">启动项目</small>
				  			</li>
				  		</ul><hr/>
		  			</li>
		  		</ul>
			</div>
	  	</div>
	</div>
	<jsp:include page="/WEB-INF/view/inc/footer.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/view/inc/js.jsp"></jsp:include>
</body>
</html>