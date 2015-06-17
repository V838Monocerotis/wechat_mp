package com.goomesoft.wechat.utils;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.model.AccessToken;

/**
 * 公众平台通用接口工具类 
 * @author YXG
 *
 */
public class WechatUtil {
	
	public static Logger log = Logger.getLogger(WechatUtil.class);
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET"; ;
	
	/** 
	 * 获取access_token 
	 *  
	 * @param appid 凭证 
	 * @param appsecret 密钥 
	 * @return 
	 */  
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		String result = HttpUtil.doGet(requestUrl);
		JsonNode jsonNode = JsonUtil.toJsonNode(result);
		if(null != jsonNode) {
			accessToken = new AccessToken();
			accessToken.setToken(jsonNode.get("access_token").asText());
			accessToken.setExpiresIn(jsonNode.get("expires_in").asInt());
		}
		
		return accessToken;
	}
	
}
