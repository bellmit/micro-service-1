package com.module.comm.shiro.cache;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.module.admin.user.pojo.LoginUser;
import com.system.cache.redis.RedisClient;
import com.system.comm.utils.FrameJsonUtil;
import com.system.comm.utils.FrameSpringBeanUtil;
import com.system.comm.utils.FrameStringUtil;

public class RedisSessionDao extends AbstractSessionDAO {

	private static Logger LOGGER = Logger.getLogger(RedisSessionDao.class);
	/** 
	 * shiro-redis的session对象前缀 
	 */
	private final String SHIRO_REDIS_SESSION_PRE = "shiro_redis_ses:";

	/** 
	 * 存放uid的对象前缀 
	 */
	private final String SHIRO_SHESSIONID_PRE = "shiro_sesid:";

	/** 
	 * 存放uid 当前状态的前缀 
	 */
	private final String UID_PRE = "crmuid:";

	/** 
	 * 存放用户权限的前缀 
	 */
	private final String PERMISSION_PRE ="permiss:";

	/*
	 * Redis 过期时间，默认为 30分钟
	 */
	private long expire = 30 * 60 * 1000;

	public void setExpire(long expire) {
		this.expire = expire;
	}

	//private JedisPoolManager redisManager;
	private RedisClient redisClient;

	public RedisClient getRedisClient() {
		if(redisClient == null) {
			redisClient = FrameSpringBeanUtil.getBean(RedisClient.class);
		}
		return redisClient;
	}

	@Override
	public void update(Session session) throws UnknownSessionException {
		this.saveSession(session);
	}

	/** 
	 * save session 
	 *
	 * @param session 
	 * @throws UnknownSessionException 
	 */
	private void saveSession(Session session) throws UnknownSessionException {
		if (session == null || session.getId() == null) {
			LOGGER.error("session or session id is null");
			return;
		}
		session.setTimeout(expire);
		Long redisExpire = expire / 1000;
		int timeout = redisExpire.intValue();
		getRedisClient().set(this.SHIRO_REDIS_SESSION_PRE + session.getId(), session, timeout);
		String uid = this.getUserId(session);
		if (null != uid && !"".equals(uid)){
			//保存用户会话对应的UID
			getRedisClient().set(this.SHIRO_SHESSIONID_PRE + session.getId(), uid, timeout);
			//保存在线UID
			getRedisClient().set(this.UID_PRE + uid, "online", timeout);
		}

	}

	@Override
	public void delete(Session session) {
		if (session == null || session.getId() == null) {
			LOGGER.error("session or session id is null");
			return;
		}

		//删除用户会话
		getRedisClient().delete(this.SHIRO_REDIS_SESSION_PRE + session.getId());
		//获取缓存的用户会话对应的UID
		String uid = getRedisClient().get(this.SHIRO_SHESSIONID_PRE + session.getId());

		//删除用户会话对应的UID
		getRedisClient().delete(this.SHIRO_SHESSIONID_PRE + session.getId());

		//删除在线UID
		getRedisClient().delete(this.UID_PRE + uid);

		//删除用户缓存的权限
		getRedisClient().delete(this.PERMISSION_PRE +uid);

	}

	@Override
	public Collection<Session> getActiveSessions() {
		Set<Session> sessions = new HashSet<Session>();

		Set<String> keys = getRedisClient().keys(this.SHIRO_REDIS_SESSION_PRE + "*");
		if (keys != null && keys.size() > 0) {
			for (String key : keys) {
				Session s = getRedisClient().get(key);
				sessions.add(s);
			}
		}
		return sessions;
		
	}

	public boolean isOnLine(String uid){

		Set<String>keys = getRedisClient().keys(this.UID_PRE + uid);
		if (keys != null && keys.size() > 0){
			return true;
		}
		return false;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		this.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if (sessionId == null) {
			LOGGER.error("session id is null");
			return null;
		}

		LOGGER.debug("#####Redis.SessionId=" + this.SHIRO_REDIS_SESSION_PRE + sessionId);

		Session s = getRedisClient().get(this.SHIRO_REDIS_SESSION_PRE + sessionId);
		return s;
	}

	/** 
	 * 获得byte[]型的key 
	 *
	 * @param key 
	 * @return 
	 */
	/*private byte[] getByteKey(String preKey,Serializable sessionId) {
		String key = preKey + sessionId;
		return key.getBytes();

	}*/

	/** 
	 * 获取用户唯一标识 
	 * @param session 
	 * @return 
	 */
	private String getUserId(Session session){
		SimplePrincipalCollection pricipal = (SimplePrincipalCollection)session.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
		if (null != pricipal){
			try {
				String userString = (String) SecurityUtils.getSubject().getPrincipal();
				if(FrameStringUtil.isEmpty(userString)) {
					return "";
				}
				LoginUser account = FrameJsonUtil.toObject(userString, LoginUser.class);
				//LoginUser account = ((LoginUser) pricipal.getPrimaryPrincipal());
				return account == null ? "" : account.getUserId();
			} catch (Exception e) {
				LOGGER.error("异常: " + e.getMessage());
			}
		}
		return "";
	}
	//setter和getter省略

}