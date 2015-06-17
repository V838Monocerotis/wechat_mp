package com.goomesoft.wechat.utils;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.config.WechatConfig;

public class JsApiUtil {
	
	public static String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WechatConfig.APPID+"&secret=" + WechatConfig.APPSECRET;
		String result = HttpUtil.doGet(url);
		JsonNode node = JsonUtil.toJsonNode(result);
		if(node.get("access_token") != null) {
			return node.get("access_token").asText();
		}
		return null;
	}
	
	public static String getJsApiTicket(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
		String result = HttpUtil.doGet(url);
		JsonNode node = JsonUtil.toJsonNode(result);
		if(node.get("ticket") != null) {
			return node.get("ticket").asText();
		}
		return null;
	}
	
	public static String getNonceStr() {
		return UUID.randomUUID().toString();
	}
	
	public static String getTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	public static String getSign(String jsApiTicket, String nonceStr, String timestamp, String url) throws Exception {
		String str = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(str.getBytes("UTF-8"));
        
        return byteToHex(crypt.digest());
    }
	
	public static Map<String, String> getSign(String url) throws Exception {
		String accessToken = JsApiUtil.getAccessToken();
		String jsApiTicket = JsApiUtil.getJsApiTicket(accessToken);
		String nonceStr = JsApiUtil.getNonceStr();
		String timestamp = JsApiUtil.getTimestamp();
		
        String str = "jsapi_ticket=" + jsApiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();
        crypt.update(str.getBytes("UTF-8"));
        
        String signature = byteToHex(crypt.digest());
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("appId", WechatConfig.APPID);
        map.put("nonceStr", nonceStr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        
        return map;
	}
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	public static void main(String[] args) {
		String url = "http://localhost:8080/wechat_cp/demo.jsp?openid=?openid=oJ5-ct9oKSeaaC4n9scRZ_a5AcYI";
		
		try {
			JsApiUtil.getSign(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
