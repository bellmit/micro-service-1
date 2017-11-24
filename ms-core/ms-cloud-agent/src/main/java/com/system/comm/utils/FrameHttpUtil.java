package com.system.comm.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * http工具类
 * @author yuejing
 *
 */
public class FrameHttpUtil {

	//private static final Logger LOGGER = Logger.getLogger(FrameHttpUtil.class);
	
	/**
	 * 创建支持http和https的请求
	 * @return
	 */
	private static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				//信任所有
				public boolean isTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return  HttpClients.createDefault();
	}
	
	/**
	 * post方式请求
	 * @param url			请求地址
	 * @return
	 * @throws IOException 
	 */
	public static String get(String url) throws IOException {
		String result = null;
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = createSSLClientDefault();
		// 创建httppost
		HttpGet httpGet = new HttpGet(url);
		// 创建参数队列
		//try {
		CloseableHttpResponse response = httpClient.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			response.close();
		}
		return result;
	}
	
	/**
	 * post方式请求
	 * @param url			请求地址
	 * @param jsonBody	参数内容格式为: {"name":"你好"}
	 * @return
	 * @throws IOException 
	 */
	public static String post(String url, String jsonBody) throws IOException {
		String result = null;
		// 创建默认的httpClient实例
		CloseableHttpClient httpClient = createSSLClientDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列
		//try {
		StringEntity input = new StringEntity(jsonBody, "UTF-8");
		input.setContentType("application/json;charset=utf-8");
		httpPost.setEntity(input);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			response.close();
		}
		/*} catch (ClientProtocolException e) {
			LOGGER.error("异常" + e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("异常" + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("异常" + e.getMessage(), e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("异常" + e.getMessage(), e);
			}
		}*/
		return result;
	}

	/**
	 * post方式请求
	 * @param url			请求地址
	 * @param params		系统参数
	 * @return
	 * @throws IOException 
	 */
	public static String post(String url, Map<String, Object> params) throws IOException {
		String result = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = createSSLClientDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(url);
		// 创建参数队列 
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		Iterator<Entry<String, Object>> entryKeyIterator = params.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, Object> e = entryKeyIterator.next();
			String value = null;
			if(e.getValue() != null) {
				value = e.getValue().toString();
			}
			formparams.add(new BasicNameValuePair(e.getKey(), value));
		}
		UrlEncodedFormEntity uefEntity;
		/*try {*/
		uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
		httpPost.setEntity(uefEntity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
		} finally {
			response.close();
		}
		return result;
	}
	
	/*public static void main(String[] args) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		String str = post("", params);
		System.out.println(str);
	}*/
}