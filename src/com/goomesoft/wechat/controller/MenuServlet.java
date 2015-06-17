package com.goomesoft.wechat.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.config.WechatConfig;

/**
 * 点击微信菜单时获取openid
 */
@WebServlet("/menu.action")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(MenuServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuServlet() {
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
		
		String code = request.getParameter("code");
		log.info("---oauth code---" + code);
		int state = Integer.valueOf(request.getParameter("state"));
		log.info("---oauth state---" + state);
		String url = "";
		switch(state) {
		case 11:
			url = "demo11.jsp";
			break;
		case 21:
			url = "demo12.jsp";
			break;
		default:
			break;
		}
		String openid = this.getOpenid(code);
		
		response.sendRedirect(url + "?openid=" + openid);
	}

	private String getOpenid(String code) {
		log.info("------code------" + code);
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WechatConfig.APPID+"&secret="+WechatConfig.APPSECRET+"&code="+code+"&grant_type=authorization_code";
		String json = HttpUtil.doGet(url);
		log.info("------return------" + json);
		JsonNode node = JsonUtil.toJsonNode(json);
		return node.get("openid").asText();
	}
}
