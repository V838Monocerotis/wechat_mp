package com.goomesoft.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.config.Message;
import com.goomesoft.common.model.JsonRequest;
import com.goomesoft.common.utils.JsonUtil;

/**
 * Servlet implementation class MessageDispatcher
 */
@WebServlet("/index.action")
public class MessageDispatcher extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LogManager.getLogger(MessageDispatcher.class);
	
	private static final int BYTE_OFFSET = 12;
	
    private BaseController baseController;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageDispatcher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		JsonRequest jsonRequest = JsonUtil.parseObject(request.getInputStream(), JsonRequest.class);
		log.debug("request - " + jsonRequest.toString());
		
		String json = dispatchMessage(jsonRequest, request);
		log.debug("response - " + json);
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

	/**
     * 分派消息
     * @param jsonRequest
     * @param request
     * @return
     */
	private String dispatchMessage(JsonRequest jsonRequest, HttpServletRequest request) {
		int msgno = Integer.valueOf(jsonRequest.getMsgno().replace("0x", ""), 16).intValue(); //消息号
		int module = msgno >> BYTE_OFFSET; //模块号
		int opcode = msgno & 0x000FFF; //操作码
		JsonNode params = jsonRequest.getData();
		
		String json = "";
		switch(module) {
			case Message.MESSAGE_MODULE >> BYTE_OFFSET:
				json = baseController.dispatchMessage(opcode, params, request);
				break;
			default:
				break;
		}
		
		return json;
	}
}
