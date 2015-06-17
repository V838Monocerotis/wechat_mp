package com.goomesoft.common.utils;

import com.fasterxml.jackson.databind.JsonNode;

public class MapUtil {
	
	private static final double PI = 3.14159265358979323; //圆周率
    private static final double R = 6371004;             //地球的半径
    private static final String GEO_SERVER_URL = "http://api.map.baidu.com/geocoder/v2/";
    
    /**
     * 获取经纬度两点之间的巨
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
    	return Math.acos(Math.sin(lat1 * PI / 180) * Math.sin(lat2 * PI / 180) + Math.cos(lat1 * PI / 180) * Math.cos(lat2 * PI / 180) * Math.cos(lng2 * PI / 180 - lng1 * PI / 180)) * R;
    }
    
    public static String getCity(double lat, double lng) {
		StringBuilder sb = new StringBuilder();
		sb.append(GEO_SERVER_URL);
		sb.append("?ak=").append("bSqFfXYP1ATYNDvwkp4ZXbGZ");
		sb.append("&callback=renderReverse&location=");
		sb.append(lat).append(",").append(lng);
		sb.append("&output=json&pois=0");
		
		String str = HttpUtil.doGet(sb.toString());
		str = str.substring(str.indexOf("(")+1, str.lastIndexOf(")"));
		
		JsonNode node = JsonUtil.toJsonNode(str);
		String city = node.get("result").get("addressComponent").get("city").asText();
	
		return city;
	}
}
