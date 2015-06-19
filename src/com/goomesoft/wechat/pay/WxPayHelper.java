package com.goomesoft.wechat.pay;

import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.goomesoft.common.utils.HttpUtil;
import com.goomesoft.common.utils.JsonUtil;
import com.goomesoft.wechat.config.WechatConfig;

public class WxPayHelper {
	
	private static Logger log = LogManager.getLogger(WxPayHelper.class);

	/**
	 * @param outTradeNo 订单号
	 * @param remoteAddr 客户端地址
	 * @param openid  用户openid
	 * @param totalFee 订单总金额
	 * @param body 订单内容
	 * @return
	 */
	public static String getJsPkg(String outTradeNo, String remoteAddr, String openid, String totalFee, String body) {
		HashMap<String, String> params = new HashMap<String, String> ();
		params.put("appid", WechatConfig.APPID);
		params.put("mch_id", WechatConfig.MCHID);
		params.put("nonce_str", WxPayUtil.createNoncestr());
		params.put("body", body);
		params.put("out_trade_no", outTradeNo);
		params.put("total_fee", String.valueOf(totalFee));
		params.put("spbill_create_ip", remoteAddr);
		params.put("notify_url", "http://www.goomesoft.com/wechat_mp/wechatPayCallback.action");
		params.put("trade_type", "JSAPI");
		params.put("openid", openid);
		String sign = WxPayUtil.createSign(params, WechatConfig.APIKEY);
		params.put("sign", sign);
		String prePayId = WxPayHelper.getPrePayId(params);
		return WxPayHelper.createJsPackage(prePayId);
	}
	
	//1
	public static String getPrePayId(HashMap<String, String> mapParams) {
		String param = WxPayUtil.mapToXml(mapParams);
		String resp = HttpUtil.doPost(WechatConfig.WXPAY_URL, param);
		log.debug("---wechat order---");
		log.debug(resp);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(resp);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		if(doc == null) {
			return null;
		}
		Element element = doc.getRootElement();
		Iterator<?> iter = element.elementIterator("prepay_id");
		while(iter.hasNext()) {
			Element ele = (Element) iter.next();
			String prePayId = ele.getText();
			return prePayId;
		}
		
		return null;
	}
	
	//2
	public static String createJsPackage(String prePayId) {
		HashMap<String, String> nativeObj = new HashMap<String, String>();
		
		String pkg = "prepay_id=" + prePayId;
		nativeObj.put("appId", WechatConfig.APPID);
		nativeObj.put("package", pkg);
		nativeObj.put("timeStamp", WxPayUtil.getTimestamp());
		nativeObj.put("nonceStr", WxPayUtil.createNoncestr());
		nativeObj.put("signType", "MD5");
		nativeObj.put("paySign", WxPayUtil.createSign(nativeObj, WechatConfig.APIKEY));
		
		String jsPkg = JsonUtil.toJsonString(nativeObj);
		log.debug("---JSAPI param---");
		log.debug(jsPkg);
		return jsPkg;
	}
	
	public static void main(String[] args) {
		String jsPkg = WxPayHelper.getJsPkg("0413102053-2143", "114.215.210.189", "oGP_hjsGehwpdx4eXwSfhumT7oas", "1", "洗车");
		System.out.println("js package:" + jsPkg);
	}
}
