package com.goomesoft.wechat.pay;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.goomesoft.wechat.utils.MD5Util;

public class WxPayUtil {

	private static Logger log = LogManager.getLogger(WxPayUtil.class);
	
	public static String getTimestamp() {
		return Long.toString(new Date().getTime()/1000);
	}
	
	public static String createNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}
	
	
	public static String createSign(HashMap<String, String> params, String partnerkey) {
		//key值排序
		List<Map.Entry<String, String>> keys = new ArrayList<Map.Entry<String, String>> (params.entrySet());
		
		Collections.sort(keys, new Comparator<Map.Entry<String, String>>(){

			@Override
			public int compare(Entry<String, String> entry1,
					Entry<String, String> entry2) {
				return (entry1.getKey()).toString().compareTo(entry2.getKey().toString());
			}
			
		});
		
		StringBuffer sb = new StringBuffer();  
		for(int i = 0; i < keys.size(); i++) {
			Map.Entry<String, String> item = keys.get(i);
			if (item.getKey() != "") {
				
				String k = item.getKey();
				String v = item.getValue();
				if(v == null || v.equals("")) {
					continue;
				}
				sb.append(k + "=" + v + "&");  
			}
		}
		
        sb.append("key=" + partnerkey);  
        log.debug("---create sign---");
        log.debug(sb.toString());
        return MD5Util.md5(sb.toString()).toUpperCase();  
    }  
	
	
	public static boolean isNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	public static String mapToXml(HashMap<String, String> arr) {
		String xml = "<xml>";
		
		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (isNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

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
