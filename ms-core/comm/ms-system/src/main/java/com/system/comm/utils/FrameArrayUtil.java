package com.system.comm.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 集合工具类<br>
 * public static void main(String[] args) {
		List<Integer> orgList = new ArrayList<Integer>();
		for (int i = 0; i < 52; i++) {
			orgList.add(i);
		}
		List<List<?>> splitList = splitArray(orgList, 10);
		for (List<?> list : splitList) {
			System.out.println(list.size());
		}
	}
 * @author 岳静
 * @date 2016年5月10日 下午3:19:48 
 * @version V1.0
 */
public class FrameArrayUtil {

	/**
	 * 根据指定数量切分数组
	 * @param <T>
	 * @param array
	 * @param splitNum
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> splitArray(List<?> array, Integer splitNum) {
		List<List<?>> list = new ArrayList<List<?>>();
		int fromIndex = 0;
		int toIndex = array.size() < splitNum ? array.size() : splitNum;
		while(true) {
			List<?> splitList = array.subList(fromIndex, toIndex);
			if(splitList != null && splitList.size() > 0) {
				list.add(splitList);
				if(splitList.size() < splitNum) {
					break;
				}
				fromIndex = fromIndex + splitNum;
				toIndex = toIndex + splitNum;
				if(toIndex > array.size()) {
					toIndex = array.size();
				}
			} else {
				break;
			}
		}
		return (List<T>) list;
	}
	
}