///**
// * 
// */
//package com.leimingtech.front.wechat.servlet;
//
//import java.io.IOException;
//import java.util.SortedMap;
//import java.util.TreeMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//
//
//
//import com.leimingtech.front.wechat.handler.GetWxOrderno;
//import com.leimingtech.front.wechat.handler.HttpConnect;
//import com.leimingtech.front.wechat.handler.HttpRespons;
//import com.leimingtech.front.wechat.handler.RequestHandler;
//import com.leimingtech.front.wechat.handler.Sha1Util;
//import com.leimingtech.front.wechat.handler.TenpayUtil;
//
//
//
//
//
///**
// *  
// * @company 雷铭智信
// * @author 张威
// * @DateTime 2015-7-18 下午1:51:45
// */
//public class TopayServlet extends HttpServlet {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * The doGet method of the servlet. <br>
//	 *
//	 * This method is called when a form has its tag value method equals to get.
//	 * 
//	 * @param request the request send by the client to the server
//	 * @param response the response send by the server to the client
//	 * @throws ServletException if an error occurred
//	 * @throws IOException if an error occurred
//	 */
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		// 判断是否微信环境, 5.0 之后的支持微信支付
//		boolean isweixin = isWeiXin(request);
//		if (isweixin) {
//			//商户相关资料 
//			String appid = "wxffed9a33d2c609f5";
//			String appsecret = "38ee1f3e603dce20422ae4ce1b7f3d2a";
//			String partner = "1253150801";
//			String partnerkey = "guangsuxia1253150801a13261807999";
//			
//			String userId=request.getParameter("userId");
//			String orderId=request.getParameter("orderId");
////			String sendAdress=request.getParameter("sendAdress");//发件地址
////			String receiveAdress=request.getParameter("receiveAdress");//收件地址
////			String weight=request.getParameter("weight");//重量
////			String subscribepickuptime=request.getParameter("subscribepickuptime");//收件时间
////			String deliveryType=request.getParameter("deliveryType");//运送类型
////			String pickuptype=request.getParameter("pickuptype");//取件方式
//			String goodsType=request.getParameter("goodsType");//物品类型
//			String supportvalue=request.getParameter("supportvalue");//物品价值
//			String goodsname=request.getParameter("goodsname");//物品名称
//			String sendpay=request.getParameter("sendpay");//运费
//			if(StringUtils.isEmpty(goodsname)){
//				goodsname = goodsType;
//			}
//			
//			String code = request.getParameter("code");
//			//金额转化为分为单位
//			float sessionmoney = Float.parseFloat(supportvalue);
//			String finalmoney = String.format("%.2f", sessionmoney);
//			int i = finalmoney.indexOf(".");
//			int pay = 0;
//			if(Integer.parseInt(finalmoney.substring(i+1,finalmoney.length()))==0){
//				pay = Integer.parseInt(finalmoney.substring(0, i))*100;
//			}else{
//				pay = Integer.parseInt(finalmoney.replace(".", ""));
//			}
////			finalmoney = finalmoney.replace(".", "");
//			String total_fee = String.valueOf(pay);
//			
//			String openId ="";
//			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?token=111&appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
//			HttpRespons temp = HttpConnect.getInstance().doGetStr(URL);		
//			String tempValue="";
//			if( temp == null){
//	//				response.sendRedirect("/weChatpay/error.jsp");
//				response.sendRedirect("http://www.inpingo.com/pinwu/pinwuForWeixin/html/doOrder/payError.html");
//					return;
//			}else
//			{
//				try {
//					tempValue = temp.getStringResult();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				JSONObject  jsonObj = JSONObject.fromObject(tempValue);
//				
//				
//				String requestPath = request.getRequestURI() + "?" + request.getQueryString();
//				if (requestPath.indexOf("&") > -1) {// 去掉其他参数
//					requestPath = requestPath.substring(0, requestPath.indexOf("&"));
//				}
//				requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
//				////LogUtil.info(requestPath);
//				
//				if(jsonObj.containsKey("errcode")){
//					////LogUtil.info(tempValue);
//	//				response.sendRedirect("/weChatpay/error.jsp");
//					response.sendRedirect("http://www.inpingo.com/pinwu/pinwuForWeixin/html/doOrder/payError.html");
//					return;
//				}
//				if(!jsonObj.containsKey("openid")){
//					////LogUtil.info("openid 出现null");
//				}
//				
//				openId = jsonObj.getString("openid");
//			}
//			
//			
//			//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
//			String currTime = TenpayUtil.getCurrTime();
//			//8位日期
//			String strTime = currTime.substring(8, currTime.length());
//			//四位随机数
//			String strRandom = TenpayUtil.buildRandom(4) + "";
//			//10位序列号,可以自行调整。
//			String strReq = strTime + strRandom;
//			
//			
//			//商户号
//			String mch_id = partner;
//			//子商户号  非必须
//	//		String sub_mch_id="";
//			//设备号   非必须
//	//		String device_info="";
//			//随机数 
//			String nonce_str = strReq;
//			//商品描述
//	//		String body = describe;
//			
//			//商品描述根据情况修改
//	//		String body = "美食";
//			//附加数据
//	//		String attach = userId;
//			//商户订单号
////			String out_trade_no = orderNo;
//			//商品金额
////			int intMoney = Integer.parseInt(finalmoney);
//			//总金额以分为单位，不带小数点
////			String total_fee = String.valueOf(intMoney);
//			//订单生成的机器 IP
////			String spbill_create_ip = request.getRemoteAddr();
//			String spbill_create_ip = getIp(request);
//			int j = spbill_create_ip.indexOf(",");
//			spbill_create_ip = spbill_create_ip.substring(j+1).trim();
//			
////			if (spbill_create_ip.equals("0:0:0:0:0:0:0:1")) {
////				spbill_create_ip = "127.0.0.1";
////			}
//			
//			//订 单 生 成 时 间   非必须
//	//		String time_start ="";
//			//订单失效时间      非必须
//	//		String time_expire = "";
//			//商品标记   非必须
//	//		String goods_tag = "";
//			
//			//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
//			String notify_url ="http://www.inpingo.com/pinwu/pinwuForWeixin/html/doOrder/paySuccess.html";
//			
//			
//			String trade_type = "JSAPI";
//			String openid = openId;
//			//LogUtil.info(openid);
//			//非必需
//	//		String product_id = "";
//			SortedMap<String, String> packageParams = new TreeMap<String, String>();
//			packageParams.put("appid", appid);  
//			packageParams.put("mch_id", mch_id);  
//			packageParams.put("nonce_str", nonce_str);  
//			packageParams.put("body", goodsname);  
//			packageParams.put("attach", userId);  
//			packageParams.put("out_trade_no", orderId);  
//	//		
//	//		
//			//这里写的金额为string型
//			packageParams.put("total_fee", total_fee);  
//			packageParams.put("spbill_create_ip", spbill_create_ip);  
//			packageParams.put("notify_url", notify_url);  
//			
//			packageParams.put("trade_type", trade_type);  
//			packageParams.put("openid", openid);  
//	
//			RequestHandler reqHandler = new RequestHandler(request, response);
//			reqHandler.init(appid, appsecret, partnerkey);
//			
//			String sign = reqHandler.createSign(packageParams);
//			String xml="<xml>"+
//					"<appid>"+appid+"</appid>"+
//					"<mch_id>"+mch_id+"</mch_id>"+
//					"<nonce_str>"+nonce_str+"</nonce_str>"+
//					"<sign>"+sign+"</sign>"+
//					"<body><![CDATA["+goodsname+"]]></body>"+
//					"<attach>"+userId+"</attach>"+
//					"<out_trade_no>"+orderId+"</out_trade_no>"+
//					//金额，这里写的1 分到时修改
////					"<total_fee>"+1+"</total_fee>"+
//					"<total_fee>"+total_fee+"</total_fee>"+
//					"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
//					"<notify_url>"+notify_url+"</notify_url>"+
//					"<trade_type>"+trade_type+"</trade_type>"+
//					"<openid>"+openid+"</openid>"+
//					"</xml>";
//			//LogUtil.info(xml);
////			String allParameters = "";
////			try {
////				allParameters =  reqHandler.genPackage(packageParams);
////				//LogUtil.info(allParameters);
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
//			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//			String prepay_id="";
//			try {
//				prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
//				if(prepay_id.equals("")){
//					request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
//	//				response.sendRedirect("/weChatpay/error.jsp");
//					response.sendRedirect("http://www.inpingo.com/pinwu/pinwuForWeixin/html/doOrder/payError.html");
//					return;
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
//			String appid2 = appid;
//			String timestamp2 = Sha1Util.getTimeStamp();
//			String nonceStr2 = nonce_str;
//			String prepay_id2 = "prepay_id="+prepay_id;
//			String packages = prepay_id2;
//			finalpackage.put("appId", appid2);  
//			finalpackage.put("timeStamp", timestamp2);  
//			finalpackage.put("nonceStr", nonceStr2);  
//			finalpackage.put("package", packages);  
//			finalpackage.put("signType", "MD5");
//			String finalsign = reqHandler.createSign(finalpackage);
//			String url="http://www.inpingo.com/pinwu/pinwuForWeixin/html/doOrder/pay.html?appId="+appid2+"&timeStamp="+timestamp2+"&nonceStr="+nonceStr2+"&packageValue="+packages+"&paySign="+finalsign;
//			//LogUtil.info(url);
//			response.sendRedirect(url);
//	//		response.sendRedirect("front/orderController.do?confirm&appid="+appid2+"&timeStamp="+timestamp2+"&nonceStr="+nonceStr2+"&package="+packages+"&sign="+finalsign);
//		}else{
//			response.getWriter().write("<script language='javascript'>alert('您的微信不是5.0以上版本，无法使用微信支付！');history.go(-1);</script>");
//		}
//	}
//	
//	/**
//     * 判断是否来自微信, 5.0 之后的支持微信支付
//     *
//     * @param request
//     * @return
//     */
//    public static boolean isWeiXin(HttpServletRequest request) {
//        String userAgent = request.getHeader("User-Agent");
//        if (StringUtils.isNotBlank(userAgent)) {
//            Pattern p = Pattern.compile("MicroMessenger/(\\d+).+");
//            Matcher m = p.matcher(userAgent);
//            String version = null;
//            if (m.find()) {
//                version = m.group(1);
//            }
//            return (null != version && NumberUtils.toInt(version) >= 5);
//        }
//        return false;
//    }
//
//	/**
//	 * 获取ip
//	 * @param request
//	 * @return
//	 */
//	public static String getIp(HttpServletRequest request) {
//		if (request == null)
//			return "";
//		String ip = request.getHeader("X-Requested-For");
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Forwarded-For");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_CLIENT_IP");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//		}
//		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		return ip;
//	}
//
//	/**
//	 * The doPost method of the servlet. <br>
//	 *
//	 * This method is called when a form has its tag value method equals to post.
//	 * 
//	 * @param request the request send by the client to the server
//	 * @param response the response send by the server to the client
//	 * @throws ServletException if an error occurred
//	 * @throws IOException if an error occurred
//	 */
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		doGet(request,response);
//	}
//
//}
