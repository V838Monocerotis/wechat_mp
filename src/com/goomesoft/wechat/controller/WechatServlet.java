package com.goomesoft.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.goomesoft.wechat.service.WechatService;
import com.goomesoft.wechat.utils.SignUtil;


/**
 * Servlet implementation class WechatServlet
 */
@WebServlet("/wechat.action")
public class WechatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = LogManager.getLogger(WechatServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WechatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
		String signature = request.getParameter("signature");  
		// 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
        
        log.info("signature:" + signature);
        log.info("timestamp:" + timestamp);
        log.info("nonce:" + nonce);
        log.info("echostr:" + echostr);
        
        PrintWriter out = response.getWriter();
    	// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
    	if(SignUtil.checkSignature(signature, timestamp, nonce)) {
    		out.print(echostr);
    	}
    	out.close();
    	out = null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		//关注者帐号的请求以post方式提交
		String respMessage = WechatService.processRequest(request);
		PrintWriter out = response.getWriter();
		out.write(respMessage);
		out.flush();
		out.close();
		out = null;
	}

}
