package com.goomesoft.wechat.custom;

/**
 * 图文消息
 * @author eternal
 *
 */
public class NewsMessage {
	
	private String touser;
	private String msgtype;
	private News news;
	
	public String getTouser() {
		return touser;
	}
	public void setTouser(String touser) {
		this.touser = touser;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public News getNews() {
		return news;
	}
	public void setNews(News news) {
		this.news = news;
	}
	
}
