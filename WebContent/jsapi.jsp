<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>微信JS-SDK调用方法</title>
<script type="text/javascript" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="js/jquery.isloading.min.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>

<script type="text/javascript">
$(document).ready(function() {
	initJsApi();	
});

function initJsApi() {
	$.isLoading({ text: "加载中" });
	
	$.ajax({
		type : "POST",
		url : "/ccxc_engineer/jsapi.action",
		data:{url:"http://www.goomesoft.com/wechat_mp/jsapi.jsp"}, //必须用域名
		dataType : "json",
		async:false,
		success : function(data) {
			var json = eval(data);
			
			wx.config({
				debug:false,
				appId:json.appId,
				timestamp:json.timestamp,
				nonceStr:json.nonceStr,
				signature:json.signature,
				jsApiList:[
		           'chooseImage',
		           'uploadImage'
				]
			});
			
			wx.ready(function() {
				$.isLoading( "hide" );
			});
			
			wx.error(function(res){
				alert("微信验证失败");
			});
		}
	});
}

var images = {
	localId:[],
	serverId:[]
};
function uploadImage() {
	wx.chooseImage({
		success:function(res) {
			images.localId = res.localIds;
			
			wx.uploadImage({
				localId:images.localId[0],
				isShowProgressTips:1,
				success:function(res) {
					var serverId = res.serverId;
					//TODO
				}
			});
		}
	});
}
</script>
</body>
</html>