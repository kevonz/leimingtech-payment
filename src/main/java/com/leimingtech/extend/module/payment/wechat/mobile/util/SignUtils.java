package com.leimingtech.extend.module.payment.wechat.mobile.util;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.extend.module.payment.wechat.mobile.config.WXpayConfig;
import com.tencent.mm.sdk.modelpay.PayReq;

/**
 * 微信签名工具
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-22 下午9:16:39
 */
@Slf4j
public class SignUtils {
	
	private PayReq req = null;// 支付请求对象
	private Map<String , String> resultunifiedorder;// 预支付返回参数集合
	//private final Logger log = Logger.getLogger(SignUtils.class);
	
	/**
	 * 微信预支付
	 * 
	 * @param b
	 * 
	 * @param params
	 * @return
	 */
	public  Map<String , Object> advancePayment(Pay order , boolean isTopUp) {
		req = new PayReq();
		Map<String , String> xml = doInBackground(order , isTopUp);// 发送预支付请求
		System.out.println("********xml"+xml);
		onPostExecute(xml);// 接收预支付请求返回的参数
		try {
			System.out.println(new String(xml.toString().getBytes(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		genPayReq();// 根据订单信息和返回的预支付参数生成微信支付对象
		Map<String , Object> req_map = new HashMap<String , Object>();// 取出支付对象重要支付参数返回给手机端调起微信支付
		req_map.put("appid" , req.appId);
		req_map.put("noncestr" , req.nonceStr);
		req_map.put("packageValue" , req.packageValue);
		req_map.put("partnerid" , req.partnerId);
		req_map.put("prepayid" , req.prepayId);
		req_map.put("timestamp" , req.timeStamp);
		req_map.put("sign" , req.sign);
		log.info("req_map:" + req_map);
		return req_map;
	}
	
	/**
	 * 生成签名参数
	 * 
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
	private void genPayReq() {
		req.appId = WXpayConfig.APP_ID;
		req.partnerId = WXpayConfig.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";// "prepay_id=" +
										// resultunifiedorder.get("prepay_id");
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid" , req.appId));
		signParams.add(new BasicNameValuePair("noncestr" , req.nonceStr));
		signParams.add(new BasicNameValuePair("package" , req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid" , req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid" , req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp" , req.timeStamp));
		req.sign = genAppSign(signParams);
	}
	
	/**
	 * 接收预支付返回参数
	 * 
	 * @param result
	 */
	private void onPostExecute(Map<String , String> result) {
		resultunifiedorder = result;
	}
	
	/**
	 * 发起预支付请求
	 * 
	 * @param porder
	 *            需要进行预支付的订单
	 * @param isTopUp
	 * @return
	 */
	private Map<String , String> doInBackground(Pay order , boolean isTopUp) {
		String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
		String entity = genProductArgs(order,isTopUp);
		byte [] buf = Util.httpPost(url , entity);
		String content=null;
		try {
			content = new String(buf,"utf-8");
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String , String> xml = decodeXml(content);
		return xml;
	}
	
	/**
	 * 解析预支付响应结果
	 * 
	 * @param content
	 *            预支付响应结果
	 * @return
	 */
	private Map<String , String> decodeXml(String content) {
		try {
			Map<String , String> xml = new HashMap<String , String>();
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String nodeName = parser.getName();
				switch(event){
					case XmlPullParser.START_DOCUMENT:
						break;
					case XmlPullParser.START_TAG:
						if("xml".equals(nodeName) == false) {
							xml.put(nodeName , parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 生成商品参数
	 * 
	 * @param porder
	 *            需要预支付的订单
	 * @param isTopUp
	 * @return
	 */
	private String genProductArgs(Pay order , boolean isTopUp) {
		try {
			if(order == null) {
				return null;
			}
			String outTradeNo =order.getPaySn();// 订单编号
			// 商品名称
			String goodsDetail =new String(( isTopUp ? "pinGo pay" : WXpayConfig.GOODSDETAIL).getBytes());// 商品描述
			String goodsPrice = MoneyConvertUtil.yuanToFen(order.getPayAmount() + "");
			String sign;
			StringBuffer xml = new StringBuffer();
			String nonceStr = genNonceStr();
			xml.append("</xml>");
			List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid" , WXpayConfig.APP_ID));
			packageParams.add(new BasicNameValuePair("body" , goodsDetail));
			packageParams.add(new BasicNameValuePair("mch_id" , WXpayConfig.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str" , nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url" , new String((isTopUp ? (WXpayConfig.NOTIFYURL_TOPUP) : (WXpayConfig.NOTIFYURL)).getBytes() , "UTF-8")));
			packageParams.add(new BasicNameValuePair("out_trade_no" , outTradeNo));
			packageParams.add(new BasicNameValuePair("spbill_create_ip" , "127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee" , goodsPrice));
			packageParams.add(new BasicNameValuePair("trade_type" , "APP"));
			sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign" , sign));
			String xmlstring = toXml(packageParams);
			return xmlstring;
		} catch (Exception e) {
			log.error("genProductArgs fail, ex = " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * 将生成的商品参数组合换成了个微信支付指定的格式
	 * 
	 * @param params
	 *            已生成的商品参数
	 * @return
	 */
	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < params.size() ; i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXpayConfig.API_KEY);
		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		return packageSign;
	}
	
	/**
	 * 将预支付参数进行签名
	 * 
	 * @param params
	 *            预支付参数
	 * @return
	 */
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < params.size() ; i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			System.out.println(params.get(i).getName()+"==>"+params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(WXpayConfig.API_KEY);
		String appSign = MD5.getMessageDigest(sb.toString().getBytes());
		return appSign;
	}
	
	/**
	 * 将参数转换为xml形式
	 * 
	 * @param params
	 *            参数
	 * @return
	 */
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for(int i = 0 ; i < params.size() ; i++) {
			sb.append("<" + params.get(i).getName() + ">");
			sb.append(params.get(i).getValue());
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**
	 * 生成时间戳
	 * 
	 * @return
	 */
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
//	public static void main(String[] args) {
//		SignUtils df=new SignUtils();
//		OrderPay order=new OrderPay();
//		order.setPaySn(System.nanoTime()+"");
//		order.setPayAmount(new BigDecimal("0.01"));
//		Map<String, Object> map = df.advancePayment(order,false);
//		Iterator<String> iterator = map.keySet().iterator();
//		while(iterator.hasNext()){
//			String key = iterator.next();
//			System.out.println(key+"----"+map.get(key));
//		}
//	}
}
