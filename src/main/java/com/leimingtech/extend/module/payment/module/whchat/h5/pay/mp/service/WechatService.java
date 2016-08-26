package com.leimingtech.extend.module.payment.module.whchat.h5.pay.mp.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.leimingtech.core.entity.SNSUserINfo;

public interface WechatService {
	//微信进行首次链接验证确保正确的token
		public void toPay(HttpServletRequest request ,HttpServletResponse response);
		//点击支付时提交
		public Map<String,Object> submitwhchat(HttpServletRequest request,HttpServletResponse response);
		//获得code后进行第二次跳转
		public void toPay2(HttpServletRequest request ,HttpServletResponse response) throws IOException;
		//获取网页授权信息 appid 公众账号唯一标示 ，appsecret 公众账号的秘钥
		public SNSUserINfo getOauth2AccessToken(String Appid,String appSecret,String code);
		//刷新网页授权凭证  refreshToken 刷新凭证
		public SNSUserINfo refreshOauth2AccessToken(String Appid,String refreshToken);
		//获取用户信息 accessToken网页授权接口调用凭证，openid用户标示
		public SNSUserINfo getSNSUSerInfo(String accessToken,String openid);
		//判断授权是否有效
		public JSONObject getisSq(String accessToken,String openid);
		//snsapi_base获得openid
		public String getopenIdbysnsapibase(String Appid,String appSecret,String code);
	
}
