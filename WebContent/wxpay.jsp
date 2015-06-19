<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信支付调用方法</title>
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
</head>
<body>

<script type="text/javascript">
$(document).ready(function() {
	getJsPkg();	
});

var jsPkg;

function getJsPkg() {
	//在这里调用com.goomesoft.wechat.pay.WxPayHelper.getJsPkg(...)得到jspkg
	//支付成功后微信平台会调用com.goomesoft.wechat.controller。WechatPayCallbackServlet
}

function onBridgeReady() {
	WeixinJSBridge.invoke('getBrandWCPayRequest', 
		jsPkg, function(res) {
			if(res.err_msg == 'get_brand_wcpay_request:ok') {
				alert("支付成功")
			}
			else if(res.err_msg == 'get_brand_wcpay_request:fail') {
				alert("支付失败");
			}
			else if(res.err_msg == 'get_brand_wcpay_request:cancel') {
				alert("用户取消");
			}
			else {
				alert(res.err_msg);
			}
		}
	);
}
</script>
</body>
</html>