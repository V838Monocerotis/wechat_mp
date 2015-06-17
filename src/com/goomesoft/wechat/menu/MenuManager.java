package com.goomesoft.wechat.menu;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.config.WechatConfig;
import com.goomesoft.wechat.model.AccessToken;
import com.goomesoft.wechat.utils.WechatUtil;

public class MenuManager {
	
	private static Logger log = LogManager.getLogger(MenuManager.class);
	
	// 菜单创建（POST） 限100（次/天）  
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN"; 
	
	public static void main(String[] args) {
		// 第三方用户唯一凭证  
        String appId = WechatConfig.APPID;
        // 第三方用户唯一凭证密钥  
        String appSecret = WechatConfig.APPSECRET; 
  
        // 调用接口获取access_token  
        AccessToken at = WechatUtil.getAccessToken(appId, appSecret);  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = createMenu(getMenu(), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)  {
            	log.debug("菜单创建成功！");  
            } else {
            	log.debug("菜单创建失败，错误码：" + result);  
            }
        }  
	}
	
	/** 
	 * 创建菜单 
	 *  
	 * @param menu 菜单实例 
	 * @param accessToken 有效的access_token 
	 * @return 0表示成功，其他值表示失败 
	 */  
	public static int createMenu(Menu menu, String accessToken) {
		int ret = 0;
		// 拼装创建菜单的url  
	    String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);  
	    // 将菜单对象转换成json字符串  
	    String jsonMenu = JsonUtil.toJsonString(menu);
	    log.debug(jsonMenu);
	    // 调用接口创建菜单  
	    String result = HttpUtil.doPost(url, jsonMenu);
	    JsonNode jsonNode = JsonUtil.toJsonNode(result);
	    System.out.println(jsonNode.toString());
	    if (null != jsonNode) {  
	        if (0 != jsonNode.get("errcode").asInt()) {  
	        	ret = jsonNode.get("errcode").asInt();  
	            log.log(Level.INFO, "创建菜单失败 errcode:{} errmsg:{}" + jsonNode.get("errcode").asInt() + "," +jsonNode.get("errmsg").asText());
	        }  
	    }  
	  
	    return ret; 
	}
	
	//洗车
	public static Menu getMenu() {
		ViewButton btn11 = new ViewButton();
		btn11.setName("我要洗车");
		btn11.setType("view");
		btn11.setUrl(getOAuthUrl(11));
		
		ViewButton btn21 = new ViewButton();
		btn21.setName("我的账户");
		btn21.setType("view");
		btn21.setUrl(getOAuthUrl(21));
		
		ViewButton btn22 = new ViewButton();
		btn22.setName("我的订单");
		btn22.setType("view");
		btn22.setUrl(getOAuthUrl(22));
		
		ViewButton btn23 = new ViewButton();
		btn23.setName("我要充值");
		btn23.setType("view");
		btn23.setUrl(getOAuthUrl(23));
		
		ViewButton btn31 = new ViewButton();
		btn31.setName("流程说明");
		btn31.setType("view");
		btn31.setUrl(getOAuthUrl(31));
		
		ViewButton btn32 = new ViewButton();
		btn32.setName("关于我们");
		btn32.setType("view");
		btn32.setUrl(getOAuthUrl(32));
		
		ViewButton btn33 = new ViewButton();
		btn33.setName("推广");
		btn33.setType("view");
		btn33.setUrl(getOAuthUrl(33));
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("团购");
		btn34.setType("view");
		btn34.setUrl(getOAuthUrl(34));
		
		ViewButton btn35 = new ViewButton();
		btn35.setName("微社区");
		btn35.setType("view");
		btn35.setUrl(getOAuthUrl(35));
		
		ParentButton mainBtn1 = new ParentButton();
		mainBtn1.setName("洗车");
		mainBtn1.setSub_button(new Button[] { btn11 });
	
		ParentButton mainBtn2 = new ParentButton();
		mainBtn2.setName("我的");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 });

		ParentButton mainBtn3 = new ParentButton();
		mainBtn3.setName("更多");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34, btn35 });

		/**
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“使用帮助”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn34 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { btn11, mainBtn2, mainBtn3 });

		return menu;
	}

	
	/**
	 * 点击微信菜单时获取openid
	 * @param state
	 * @return
	 */
	private static String getOAuthUrl(int state) {
		String redirectUri = "http://www.goomesoft.com/wechat_mp/menu.action";
		try {
			redirectUri = URLEncoder.encode(redirectUri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		String oauthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WechatConfig.APPID + "&redirect_uri=" + redirectUri + "&response_type=code&scope=snsapi_base&state=" + state + "#wechat_redirect";
		return oauthUrl;
	}
}
