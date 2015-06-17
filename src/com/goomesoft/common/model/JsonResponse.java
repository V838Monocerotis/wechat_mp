package com.goomesoft.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by eternal on 2014/12/4.
 * @param <T>
 */
public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**消息号*/
    private String msgno;
    /**错误码*/
    private int code;
    /**消息体*/
    private List<T> data;

    public String getMsgno() {
        return msgno;
    }

    public void setMsgno(String msgno) {
        this.msgno = msgno;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
