package com.leimingtech.extend.module.payment.module.whchat.h5.pay.mp.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leimingtech.core.entity.SNSUserINfo;
import com.leimingtech.core.entity.WeiXinOauth2Token;
import com.leimingtech.extend.module.payment.module.whchat.h5.pay.mp.service.WechatService;
import com.leimingtech.extend.module.payment.wechat.h5.config.WachatContent;
import com.leimingtech.extend.module.payment.wechat.h5.util.WeixinUtil;
import com.leimingtech.service.module.trade.service.PayService;

@Controller
@RequestMapping("/weChatpayment")
public class WechatAction {
	@Resource
	private WechatService wechatService;
	@Resource
	private PayService payService;
	
	//微信进行首次链接验证确保正确的token
	@RequestMapping("topay")
	public void toPay(HttpServletRequest request ,HttpServletResponse response){
		wechatService.toPay(request, response);
	}
	/**
	 * 提交订单
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitOrder")
	@ResponseBody
	public Map<String,Object> submitOrder(HttpServletRequest request,HttpServletResponse response) {
		return wechatService.submitwhchat(request, response);
	}
	
	//获得code后进行第二次跳转跳转的pay.html支付页面
	@RequestMapping("/topay2")
	public void toPay2(HttpServletRequest request ,HttpServletResponse response)throws  IOException{
		wechatService.toPay2(request, response);
	}
	//修改订单状态
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateorderstate")
	public void updateorderstate(HttpServletRequest request ,HttpServletResponse response,Model model)throws  IOException, DocumentException{
		PrintWriter out = null;
		int status = 0;//0失败 ，1 成功
		try {
			out= response.getWriter();
		}catch (IOException e2){
			e2.printStackTrace();
		}
		 // 解析结果存储在HashMap
	    Map<String, String> map = new HashMap<String, String>();
	    InputStream inputStream = request.getInputStream();
	    // 读取输入流
	    SAXReader reader = new SAXReader();
	    Document document = reader.read(inputStream);
	    // 得到xml根元素
	    Element root = document.getRootElement();
	    // 得到根元素的所有子节点
	    List<Element> elementList = root.elements();
	    // 遍历所有子节点
	    for (Element e : elementList)
	        map.put(e.getName(), e.getText());
	    // 释放资源
	    inputStream.close();
	    inputStream = null;
	    System.out.println(map);
	    //根据反过来支付信息修改订单状态
	    if(map.get("result_code")!=null&& map.get("result_code").equals("SUCCESS")){
	    	System.out.println("修改成功了"+map.get("out_trade_no"));//原商户订单
	    	System.out.println("修改成功了"+map.get("transaction_id"));//微信支付订单号
	    	//根据订单号修改订单信息
	    	payService.updatePayFinish(map.get("out_trade_no"),map.get("transaction_id"),"mp_weichatpay");//公共平台支付
	    	out.print("SUCCESS");
	    }else{
	    	out.print("FAIL");
	    }
	    /*String rebackurl="";
		String msg="";
		if(!"".equals(map.get("out_trade_no"))){
			if(map.get("out_trade_no").contains("R")){
				status = 1;
				rebackurl="/cart/topup_result";
				msg = "恭喜您，充值成功！";
			}else{
				status = 1;
				rebackurl="/cart/pay_result";
				msg = "恭喜您，支付成功！";
			}
		}
		return null;*/
	}
	
	 //获取网页授权凭证
	  @RequestMapping("getAccessToken")
	  public void getWeiXinOauth2Token(HttpServletRequest request){
		  String code=request.getParameter("code");
		  WeiXinOauth2Token wat=null;
		  if(StringUtils.isNotEmpty(code)&&code.equals("authdeny")){
			  WeixinUtil.httpRequest(WachatContent.h5index,"GET", null);
		  }else if(StringUtils.isNotEmpty(code)&&code.equals("CODE")){
			  wechatService.getOauth2AccessToken(WachatContent.appid, WachatContent.appsecret,code);//appid 公众账号唯一标示 ，appsecret 公众账号的秘钥
		  }
	  }
	  //刷新网页授权凭证  appid 公众账号唯一标示,refreshToken 刷新凭证 
	  public WeiXinOauth2Token refreshOauth2AccessToken(String Appid,String refreshToken){

		  if(StringUtils.isNotEmpty(refreshToken) && StringUtils.isNotEmpty(Appid)){
			  wechatService.refreshOauth2AccessToken(Appid, refreshToken);
		  }
		  return null;
	  }
	  
	  //通过网页授权获取页面信息  access_token 网页授权接口调用凭证,openid 用户的唯一标识 
	  public SNSUserINfo getSNSUserInfo(String accessToken, String openid){
		  SNSUserINfo snsUserInfo=null;
		  if(StringUtils.isNotEmpty(accessToken) && StringUtils.isNotEmpty(openid)){
			  snsUserInfo=wechatService.getSNSUSerInfo(accessToken, openid);
		  }
		  return snsUserInfo;
	  }
}
