package com.system.rmi.test.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.system.rmi.model.RmiModel;
import com.system.rmi.service.DataCenterService;

/**
 * 服务端保存数据
 * @author yuejing
 *
 */
@Component
public class ServerDataCenterServiceImpl implements DataCenterService {
	private static Map<String, RmiModel> data = new HashMap<String, RmiModel>();

	@Override
	public void add(RmiModel rmiModel) {
		data.put(rmiModel.getUrl(), rmiModel);
	}

	@Override
	public void del(String url) {
		data.remove(url);
	}

	@Override
	public List<RmiModel> list() {
		List<RmiModel> list = new ArrayList<RmiModel>();
		Iterator<Entry<String, RmiModel>> entryKeyIterator = data.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, RmiModel> e = entryKeyIterator.next();
			RmiModel value = e.getValue();
			list.add(value);
		}
		return list;
	}

}