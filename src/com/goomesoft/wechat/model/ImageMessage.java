package com.goomesoft.wechat.model;

/**
 * 图片消息
 * @author YXG
 *
 */
public class ImageMessage extends BaseMessage {
	
	private String PicUrl;

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		this.PicUrl = picUrl;
	}
	
}
