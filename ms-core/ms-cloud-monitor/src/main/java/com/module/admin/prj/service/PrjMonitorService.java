package com.module.admin.prj.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.module.admin.prj.pojo.PrjMonitor;
import com.system.handle.model.ResponseFrame;

/**
 * prj_monitor的Service
 * @author yuejing
 * @date 2016-11-30 13:30:00
 * @version V1.0.0
 */
@Component
public interface PrjMonitorService {
	
	/**
	 * 保存或修改
	 * @param prjMonitor
	 * @return
	 */
	public ResponseFrame saveOrUpdate(PrjMonitor prjMonitor);
	
	/**
	 * 根据prjmId获取对象
	 * @param prjmId
	 * @return
	 */
	public PrjMonitor get(Integer prjmId);

	/**
	 * 分页获取对象
	 * @param prjMonitor
	 * @return
	 */
	public ResponseFrame pageQuery(PrjMonitor prjMonitor);
	
	/**
	 * 根据prjmId删除对象
	 * @param prjmId
	 * @return
	 */
	public ResponseFrame delete(Integer prjmId);

	/**
	 * 获取所有需要监控的项目
	 * @return
	 */
	public List<PrjMonitor> findMonitor();

	/**
	 * 根据编号修改为检测成功(修改monitorTime和monitorStatus，修改monitorFailNum为0)
	 * @param prjmId
	 */
	public void updateMonitorSucc(Integer prjmId);

	/**
	 * 根据编号修改检测失败(修改monitorTime和monitorStatus，修改monitorFailNum + 1)
	 * @param prjmId
	 * @param monitorFailNum
	 */
	public void updateMonitorFail(Integer prjmId, Integer monitorFailNum);

	/**
	 * 根据编号修改检测失败发送信息的时候(将monitorFailNum，重置为0，monitorFailSendTime修改为最新时间)
	 * @param prjmId
	 */
	public void updateMonitorFailSendInfo(Integer prjmId);

	/**
	 * 获取服务
	 * @param prjId
	 * @return
	 */
	public PrjMonitor getService(String prjId);
}
