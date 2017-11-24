package com.ms.frame.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ms.ConfigCons;
import com.system.auth.AuthUtil;
import com.system.comm.utils.FrameHttpUtil;

public class Get {

	public static void main(String[] args) throws IOException {
		String clientId = ConfigCons.clientId;
		String time = String.valueOf(System.currentTimeMillis());
		String sercret = ConfigCons.sercret;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientId", clientId);
		params.put("time", time);
		params.put("sign", AuthUtil.auth(clientId, time, sercret));
		
		params.put("id", "23");
		
		String result = FrameHttpUtil.post(ConfigCons.address + "/test/get", params);
		System.out.println(result);
		
		/*RestTemplate restTemplate = new RestTemplate();
		String baseUrl="http://localhost:7003/api";
		String resoureUrl = baseUrl + "/test/get";
		System.out.println(resoureUrl);
		Map<String, Object> searchParams =new HashMap<String,Object>();
		searchParams.put("clientId", "196845682");
		searchParams.put("time", "1483667764824");
		searchParams.put("sign", "d2db8a7faec032b50983995c77a2f6dc");
		
		searchParams.put("id", "23");
		//URI userUri = restTemplate.postForLocation(resoureUrl, searchParams);
		
		 MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
		 requestEntity.add("id", "23");
         
		ResponseFrame t = restTemplate.postForObject(resoureUrl, requestEntity, ResponseFrame.class);
		
		Test test = (Test) t.getBody();*/
	}
}
