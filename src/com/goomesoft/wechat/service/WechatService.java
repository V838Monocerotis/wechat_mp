package com.goomesoft.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.goomesoft.wechat.model.Article;
import com.goomesoft.wechat.model.NewsMessage;
import com.goomesoft.wechat.model.TextMessage;
import com.goomesoft.wechat.utils.MessageUtil;

public class WechatService {
	
	private static Logger log = LogManager.getLogger(WechatService.class);

	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		
		// 默认返回的文本消息内容  
        String respContent = "请求处理异常，请稍候尝试！";   
        
        try{
        	// xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);  

            // 发送方帐号（open_id）  
            String fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            String toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType"); 
            // 发送的内容
            String content = requestMap.get("Content");
           
            // 文本消息  
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {  
                return handleTextMessage(content, fromUserName, toUserName);
            }  
            // 图片消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {  
                respContent = "您发送的是图片消息！";  
            }  
            // 地理位置消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {  
                respContent = "您发送的是地理位置消息！";  
            }  
            // 链接消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {  
                respContent = "您发送的是链接消息！";  
            }  
            // 音频消息  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {  
                respContent = "您发送的是音频消息！";  
            }  
            // 事件推送  
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {  
                // 事件类型  
                String eventType = requestMap.get("Event");  
                String eventKey = requestMap.get("EventKey");
                log.debug("EventType:" + eventType);
                log.debug("EventKey:" + eventKey);
                // 订阅  
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {  
                    respContent = "感谢您的关注！";  
                }  
                // 取消订阅  
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {  
                    //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息  
                }  
                //地理位置
                else if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                	String latitude = requestMap.get("Latitude");
                	String longitude = requestMap.get("Longitude");
                	log.debug("-lat:" + latitude + " - lng:" + longitude);
                	if(latitude != null && longitude != null) {
                		double lat = Double.parseDouble(latitude);
                		double lng = Double.parseDouble(longitude);
                    	
                    	respContent = "customer - lat：" + lat + " lng：" + lng;
                	}
                }
            }
            
            respMessage = getTextMessageResponse(respContent, fromUserName, toUserName);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        return respMessage;
	}
	
	public static String handleTextMessage(String content, String fromUserName, String toUserName) {
		String str = "您发送的是文本消息";
		return getTextMessageResponse(str, fromUserName, toUserName);
	}

	//创建图文消息  
	private static String getNewsMessageResponse(List<Article> articles, String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName(fromUserName);  
        newsMessage.setFromUserName(toUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0); 
        newsMessage.setArticleCount(articles.size());  
        newsMessage.setArticles(articles);
        
		return MessageUtil.newsMessageToXml(newsMessage);  
	}
	
	// 创建文本消息  
	private static String getTextMessageResponse(String content, String fromUserName, String toUserName) {
        TextMessage textMessage = new TextMessage();  
        textMessage.setToUserName(fromUserName);  
        textMessage.setFromUserName(toUserName);  
        textMessage.setCreateTime(new Date().getTime());  
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
        textMessage.setFuncFlag(0);  
        textMessage.setContent(content);
        
		return MessageUtil.textMessageToXml(textMessage);
	}
}
