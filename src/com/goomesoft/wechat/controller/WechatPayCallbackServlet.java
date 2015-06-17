package com.goomesoft.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * Servlet implementation class WechatPayCallbackServlet
 */
@WebServlet("/wechatPayCallback.action")
public class WechatPayCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(WechatPayCallbackServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WechatPayCallbackServlet() {
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

		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.debug("---wechat pay callback----");
		log.debug(sb.toString());
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return;
		}
		String outTradeNo = "";
		String resultCode = "";
		Element element = doc.getRootElement();
		Iterator<?> iter = element.elementIterator("out_trade_no");
		while(iter.hasNext()) {
			Element ele = (Element) iter.next();
			outTradeNo = ele.getText();
			break;
		}
		log.debug("---outTradeNo----" + outTradeNo);
		iter = element.elementIterator("result_code");
		while(iter.hasNext()) {
			Element ele = (Element) iter.next();
			resultCode = ele.getText();
			break;
		}
		log.debug("---resultCode---" + resultCode);
		
		PrintWriter out = response.getWriter();
		out.write("success");
		out.flush();
		out.close();
		
		if(resultCode.equals("SUCCESS")) {
			//TODO outTradeNo
		} 
	}

}
