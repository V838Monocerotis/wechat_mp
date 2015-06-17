package com.goomesoft.common.utils;

/**
 * Created by eternal on 2014/12/4.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goomesoft.common.config.Constants;
import com.goomesoft.common.model.JsonResponse;

public class ResponseUtil {
	
	public static String getJsonResponse(int msgno, int value) {
        JsonResponse<Object> resp = new JsonResponse<Object>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(getResponseCode(value));
        resp.setData(new ArrayList<Object>());

        return JsonUtil.toJsonString(resp);
    }
	
	public static <T> String getJsonResponse(int msgno, T obj) {
        JsonResponse<T> resp = new JsonResponse<T>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(getResponseCode(obj));

        List<T> list = new ArrayList<T> ();
        if(obj != null) {
            list.add(obj);
        }
        resp.setData(list);

        return JsonUtil.toJsonString(resp);
    }
	
	public static <T> String getJsonResponse(int msgno, List<T> list) {
        JsonResponse<T> resp = new JsonResponse<T>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(getResponseCode(list));

        if(list == null) {
        	list = new ArrayList<T> ();
        }
        resp.setData(list);

        return JsonUtil.toJsonString(resp);
    }

    public static String getSuccessResponse(int msgno) {
        JsonResponse<Object> resp = new JsonResponse<Object>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(Constants.SUCCESS);
        resp.setData(new ArrayList<Object>());

        return JsonUtil.toJsonString(resp);
    }

    public static String getSuccessResponse(int msgno, String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put(key, value);
        return JsonUtil.createJson(encodeHex(msgno), Constants.SUCCESS, map);
    }

	public static String getErrorResponse(int msgno) {
        JsonResponse<Object> resp = new JsonResponse<Object>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(Constants.FAILED);
        resp.setData(new ArrayList<Object>());

        return JsonUtil.toJsonString(resp);
    }
	
	public static String getErrorResponse(int msgno, String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put(key, value);

        return JsonUtil.createJson(encodeHex(msgno), Constants.FAILED, map);
    }
	
	@Deprecated
    public static <T> String getJsonResponse(int msgno, int code, T data) {
        JsonResponse<T> resp = new JsonResponse<T>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(code);

        List<T> list = new ArrayList<T> ();
        if(data != null) {
            list.add(data);
        }
        resp.setData(list);

        return JsonUtil.toJsonString(resp);
    }

	@Deprecated
    public static <T> String getJsonResponse(int msgno, int code, List<T> data) {
        JsonResponse<T> resp = new JsonResponse<T>();
        resp.setMsgno(encodeHex(msgno));
        resp.setCode(code);

        if(data == null) {
            data = new ArrayList<T> ();
        }
        resp.setData(data);

        return JsonUtil.toJsonString(resp);
    }
    
	@Deprecated
    public static String getCustomResponse(int msgno, int code ,String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object> ();
        map.put(key, value);

        return JsonUtil.createJson(encodeHex(msgno), code, map);
    }

	@Deprecated
    public static String getCustomResponse(int msgno, int code, Map<String, Object> map) {
        return JsonUtil.createJson(encodeHex(msgno), code, map);
    }

    
    private static int getResponseCode(int value) {
		if(value == 0) {
			return Constants.FAILED;
		} else {
			return Constants.SUCCESS;
		}
	}
	
	private static int getResponseCode(Object obj) {
		if(obj == null) {
			return Constants.FAILED;
		} else {
			return Constants.SUCCESS;
		}
	}
	
	private static <T> int getResponseCode(List<T> list) {
		if(list == null || list.isEmpty()) {
			return Constants.FAILED;
		} else {
			return Constants.SUCCESS;
		}
	}
	
	private static String encodeHex(int value) {
        String str = Integer.toHexString(value).toUpperCase();
        if(str.length() == 4) {
            str = "0x00" + str;
        } else if(str.length() == 5) {
            str = "0x0" + str;
        } else if(str.length() == 6) {
            str = "0x" + str;
        }
        return str;
    }
	
}
