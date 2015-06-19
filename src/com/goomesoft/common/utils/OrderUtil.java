package com.goomesoft.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderUtil {
	
	/**
	 * 获取唯一订单号，支付宝、微信支付通用
	 * @return
	 */
	public static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
		
}
