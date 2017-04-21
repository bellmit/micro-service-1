package com.ms.biz.admin.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import com.module.comm.shiro.ShiroDbRealm;
import com.module.comm.shiro.cache.RedisSessionDao;

@Configuration
@Lazy(true)
public class ShiroConfig {

	@Autowired
	private Environment env;

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public ShiroDbRealm shiroDbRealm() {
		return new ShiroDbRealm();
	}

	@Bean
	public RedisSessionDao redisSessionDao() {
		RedisSessionDao redisSessionDao = new RedisSessionDao();
		long expire = 60 * 60 * 1000;
		redisSessionDao.setExpire(expire);
		return redisSessionDao;
	}

	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager dwsm = new DefaultWebSessionManager();
		dwsm.setSessionDAO(redisSessionDao());
		return dwsm;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {
		//记住密码 rememberMe管理器
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		//byte[] cipherKey = org.apache.shiro.codec.Base64.decode("5aaC5qKm5oqA5pyvAAAAAA==");
		byte[] cipherKey = org.apache.shiro.codec.Base64.decode("ea7C5qKm58qA5pyvAdA2A==");
		rememberMeManager.setCipherKey(cipherKey);
		SimpleCookie cookie = new SimpleCookie("rememberMe");
		cookie.setHttpOnly(true);
		//7天
		int maxAge = 7 * 24 * 60 * 60;
		cookie.setMaxAge(maxAge);
		rememberMeManager.setCookie(cookie);
		return rememberMeManager;
	}

	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager dsm = new DefaultWebSecurityManager();
		//设置自定义Realm
		dsm.setRealm(shiroDbRealm());
		dsm.setSessionManager(sessionManager());
		//记住密码管理
		dsm.setRememberMeManager(rememberMeManager());
		return dsm;
	}

	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean() {
		//在方法中 注入  securityManager ，进行代理控制
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		//确定调用目标方法的参数
		Object[] arguments = new Object[1];
		arguments[0] = securityManager();
		methodInvokingFactoryBean.setArguments(arguments);
		return methodInvokingFactoryBean;
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator daapc = new DefaultAdvisorAutoProxyCreator();
		//使用cglib
		daapc.setProxyTargetClass(true);
		return daapc;
	}

	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
		aasa.setSecurityManager(securityManager());
		return aasa;
	}

	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
		//安全管理器
		sfb.setSecurityManager(securityManager());
		//默认的登陆访问url
		sfb.setLoginUrl("/unauthor.shtml");
		//登陆成功后跳转的url
		sfb.setSuccessUrl("/user/f-view/main.shtml");
		//没有权限跳转的url
		sfb.setUnauthorizedUrl("/user/view/unauth.shtml");
		/*anon  不需要认证
        authc 需要认证
        user  验证通过或RememberMe登录的都可以*/

		//默认是空格分隔符
		/*String definitions = "/resources/**=anon,"
				+ "/view/**=anon,"
				+ "/csrf.shtml**=anon,"
				+ "/index.shtml**=anon,"
				+ "/help.shtml**=anon,"
				+ "/user/json/login**=anon,"
				+ "/**=user";
		sfb.setFilterChainDefinitions(definitions);*/
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		filterChainDefinitionMap.put("/resources/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/csrf.shtml**", "anon");
		filterChainDefinitionMap.put("/index.shtml**", "anon");
		filterChainDefinitionMap.put("/unauthor.shtml**", "anon");
		filterChainDefinitionMap.put("/help.shtml**", "anon");
		filterChainDefinitionMap.put("/user/json/login**", "anon");
		filterChainDefinitionMap.put("/**", "user");
		sfb.setFilterChainDefinitionMap(filterChainDefinitionMap);

		// 配置过滤器
		Map<String, Filter> filters = new HashMap<String, Filter>();
		filters.put("anon", new AnonymousFilter());
		filters.put("authc", new FormAuthenticationFilter());
		filters.put("logout", new LogoutFilter());
		filters.put("roles", new RolesAuthorizationFilter());
		filters.put("user", new UserFilter());
		sfb.setFilters(filters);
		return sfb;
	}

	/*@Bean
	@Order(20)
	@DependsOn("shiroFilter")
    public ServletContextInitializer getMyFilter() {
        return new WebXmlConfig();
    }*/
}