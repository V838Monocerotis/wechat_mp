package com.goomesoft.common.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * Created by eternal on 2014/12/4.
 */
public class JsonRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**消息号*/
    private String msgno;
    /**消息体*/
    private JsonNode data;

    public String getMsgno() {
        return msgno;
    }

    public void setMsgno(String msgno) {
        this.msgno = msgno;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    @Override
    public String toString() {
    	 return "{\"msgno\":\"" + msgno + "\",\"data\":" + data + "}";
    }
    
}
