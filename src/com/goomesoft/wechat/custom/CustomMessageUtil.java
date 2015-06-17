package com.goomesoft.wechat.custom;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.config.WechatConfig;

/**
 * 发送客服消息
 * @author eternal
 *
 */
public class CustomMessageUtil {
	
	public static String getAccessToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WechatConfig.APPID+"&secret=" + WechatConfig.APPSECRET;
		String result = HttpUtil.doGet(url);
		JsonNode node = JsonUtil.toJsonNode(result);
		if(node.get("access_token") != null) {
			return node.get("access_token").asText();
		}
		return null;
	}
	
	public static void sendMessage(String json) {
		String accessToken = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accessToken;
		HttpUtil.doPost(url, json);
	}
	
	public static void sendTextMessage(String openid, String content) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\"touser\":\"").append(openid).append("\", \"msgtype\":\"text\", ")
		.append("\"text\":{\"content\":\"").append(content).append("\"}}");
		
		sendMessage(sb.toString());
	}
	
	public static void sendNewsMessage(String touser, String desc, String url) {
		Article article = new Article();
		article.setTitle("通知");
		article.setDescription(desc);
		article.setUrl(url);
		article.setPicurl("https://www.baidu.com/img/bd_logo1.png");
		
		News news = new News();
		List<Article> articles = new ArrayList<Article> ();
		articles.add(article);
		news.setArticles(articles);
		
		NewsMessage msg = new NewsMessage();
		msg.setTouser(touser);
		msg.setMsgtype("news");
		msg.setNews(news);
		
		String json = JsonUtil.toJsonString(msg);
		sendMessage(json);
	}
	
	public static void main(String[] args) {
		//CustomMessageUtil.sendTextMessage("oGP_hjvJcFj2YHXI43MDwUtOG4Vg", "fuck 你妹");
		String touser = "oGP_hjvJcFj2YHXI43MDwUtOG4Vg";
		String desc = "fuck 通知";
		String url = "http://www.baidu.com";
		CustomMessageUtil.sendNewsMessage(touser, desc, url);
	}
	
}
