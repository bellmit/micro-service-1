package com.client.rel.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度失败的数据
 * @author yuejing
 * @date 2016年10月22日 上午10:01:24
 * @version V1.0.0
 */
public class DispatchFailData {

	/**
	 * 发布项目成功，调度服务端更新状态
	 */
	private static List<Integer> updateReleaseSuccPrjIds = new ArrayList<Integer>();
	
	/**
	 * 添加发布项目成功，调度服务端更新状态 的数据
	 * @param prjId
	 */
	public static void addUpdateReleaseSuccPrjIds(Integer prjId) {
		if(updateReleaseSuccPrjIds.contains(prjId)) {
			return;
		}
		updateReleaseSuccPrjIds.add(prjId);
	}
	/**
	 * 移除发布项目成功，调度服务端更新状态 的数据
	 * @param prjId
	 */
	public static void removeUpdateReleaseSuccPrjIds(Integer prjId) {
		if(updateReleaseSuccPrjIds.contains(prjId)) {
			updateReleaseSuccPrjIds.remove(prjId);
			return;
		}
	}
	/**
	 * 获取所有 发布项目成功，调度服务端更新状态
	 * @return
	 */
	public static List<Integer> getUpdateReleaseSuccPrjIds() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(updateReleaseSuccPrjIds);
		return list;
	}

	/**
	 * 发布项目失败，调度服务端更新状态
	 */
	private static List<Integer> updateReleaseFailPrjIds = new ArrayList<Integer>();
	
	/**
	 * 添加发布项目失败，调度服务端更新状态 的数据
	 * @param prjId
	 */
	public static void addUpdateReleaseFailPrjIds(Integer prjId) {
		if(updateReleaseFailPrjIds.contains(prjId)) {
			return;
		}
		updateReleaseFailPrjIds.add(prjId);
	}
	/**
	 * 移除发布项目失败，调度服务端更新状态 的数据
	 * @param prjId
	 */
	public static void removeUpdateReleaseFailPrjIds(Integer prjId) {
		if(updateReleaseFailPrjIds.contains(prjId)) {
			updateReleaseFailPrjIds.remove(prjId);
			return;
		}
	}
	/**
	 * 获取所有 发布项目失败，调度服务端更新状态
	 * @return
	 */
	public static List<Integer> getUpdateReleaseFailPrjIds() {
		List<Integer> list = new ArrayList<Integer>();
		list.addAll(updateReleaseFailPrjIds);
		return list;
	}
}