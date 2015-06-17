package com.goomesoft.common.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	/**
	 * @param url 地址
	 * @return
	 */
	public static String doGet(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse httpResponse = null;
		String result = null;
		try {
			httpResponse =  httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if(httpEntity != null) {
					result =  EntityUtils.toString(httpEntity);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * @param url 地址
	 * @param json 参数
	 * @return
	 */
	public static String doPost(String url, String json) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse httpResponse = null;
		String result = null;
		
		if(json != null) {
			StringEntity entity = new StringEntity(json, "UTF-8");
			httpPost.setEntity(entity);
		}
		
		try {
			httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				if(httpEntity != null) {
					result =  EntityUtils.toString(httpEntity);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
}
