package com.module.admin.prj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.module.admin.prj.pojo.PrjMonitor;

/**
 * prj_monitor的Dao
 * @author yuejing
 * @date 2016-11-30 13:29:59
 * @version V1.0.0
 */
public interface PrjMonitorDao {

	public abstract void save(PrjMonitor prjMonitor);

	public abstract void update(PrjMonitor prjMonitor);

	public abstract void delete(@Param("prjmId")Integer prjmId);

	public abstract PrjMonitor get(@Param("prjmId")Integer prjmId);

	public abstract List<PrjMonitor> findPrjMonitor(PrjMonitor prjMonitor);
	
	public abstract int findPrjMonitorCount(PrjMonitor prjMonitor);

	public abstract List<PrjMonitor> findMonitor();

	public abstract void updateMonitorStatus(@Param("prjmId")Integer prjmId, @Param("monitorStatus")Integer monitorStatus, @Param("monitorFailNum")Integer monitorFailNum);

	public abstract void updateMonitorFailSendTime(@Param("prjmId")Integer prjmId);

	public abstract PrjMonitor getByPrjIdRemark(@Param("prjId")Integer prjId, @Param("remark")String remark);

	public abstract PrjMonitor getByPrjIdType(@Param("prjId")String prjId, @Param("type")Integer type);
}
