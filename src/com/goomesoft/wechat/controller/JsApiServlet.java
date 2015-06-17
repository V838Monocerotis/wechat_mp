package com.goomesoft.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.utils.JsApiUtil;


/**
 * Servlet implementation class JsApiServlet
 */
@WebServlet("/jsapi.action")
public class JsApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsApiServlet() {
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
		
		String url = request.getParameter("url");
		
		Map<String, String> map = null;
		try {
			map = JsApiUtil.getSign(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String json = JsonUtil.toJsonString(map);
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

}
