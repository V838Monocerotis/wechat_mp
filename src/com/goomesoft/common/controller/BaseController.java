package com.goomesoft.common.controller;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonNode;

public interface BaseController {
	
	/**
	 * @param opcode 操作码
	 * @param params 参数
	 * @param request HttpServletRequest
	 * @return json
	 */
	public String dispatchMessage(int opcode, JsonNode params, HttpServletRequest request);
	
}
