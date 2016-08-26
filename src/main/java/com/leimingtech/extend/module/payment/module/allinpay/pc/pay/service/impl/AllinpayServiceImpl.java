package com.leimingtech.extend.module.payment.module.allinpay.pc.pay.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import com.aipg.rtreq.Trans;
import com.allinpay.XmlTools;
import com.allinpay.xmltrans.service.TranxServiceImpl;
import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.extend.module.payment.allinpay.pc.config.AllinpayBase;
import com.leimingtech.extend.module.payment.allinpay.pc.config.AllinpayContents;
import com.leimingtech.extend.module.payment.module.allinpay.pc.pay.service.AllinpayService;
import com.leimingtech.extend.module.payment.module.allinpay.pc.sdk.LogUtil;
import com.leimingtech.extend.module.payment.module.allinpay.pc.sdk.SDKUtil;
import com.leimingtech.service.module.trade.service.PayService;

@Service
@Slf4j
public class AllinpayServiceImpl implements AllinpayService {

	@Resource
	private PayService payService;

	/**
	 * 提现
	 */
	
	
	public String withdraw(Trans trans) {
		String retCode = "";
		String testTranURL = "https://113.108.182.3/aipg/ProcessServlet"; // 通联测试环境，外网（商户测试使用）
		String testTranURL11 = "https://172.16.1.11/aipg/ProcessServlet"; // 通联测试环境，内网（通联内部技术使用）
		String tranURL = "http://tlt.allinpay.com/aipg/ProcessServlet";// 通联生产环境（商户上线时使用）
		String testURLOfBill = "https://113.108.182.3/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		String URLOfBill = "https://tlt.allinpay.com/aipg/GetConFile.do?SETTDAY=@xxx&REQTIME=@yyy&MERID=@zzz&SIGN=@sss&CONTFEE=1"; //
		boolean isfront = false;// 是否发送至前置机（由前置机进行签名）如不特别说明，商户技术不要设置为true
		String reqsn = "1404826943281";// 交易流水号(交易结果查询时，待查询的原支付交易流水号)
		String trx_code, busicode;// 100001批量代收 100 002批量代付 100011单笔实时代收 // 100014单笔实时代付
									
		TranxServiceImpl tranxService = new TranxServiceImpl();
		trx_code = "100014";
		/**
		 * 测试的时候不用修改以下业务代码，但上生产环境的时候，必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		 * 另外，特别说明：如果生产环境对接的时候返回”未开通产品“那么说明该商户开通的接口与目前测试的接口不一样，需要找业务确认
		 * 代收是批量代收接口的简称，代付 是批量代付接口的简称，
		 * 对接报文中，info下面的用户名一般是：商户号+04，比如商户号为：200604000000445
		 * ，那么对接用户一般为：20060400000044504
		 */
		if ("100011".equals(trx_code))// 收款的时候，填写收款的业务代码
			busicode = "10600";
		else
			busicode = "00600";
		// 设置安全提供者,注意，这一步尤为重要
		BouncyCastleProvider provider = new BouncyCastleProvider();
		XmlTools.initProvider(provider);

		trx_code = "100014";
		try {
			retCode = tranxService.singleTranx(trans, tranURL, trx_code, busicode, isfront);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retCode;
	}

	public String toPay(PayCommon payCommon) {
		// Pay pay = payService.findPayBySn(orderSn);
		// String encoding = "UTF-8";
		/**
		 * 5.0.0
		 *
		 * //String version = "5.0.0"; Map<String,String> paramMap = new
		 * HashMap<String, String>(); /* // 版本号 paramMap.put("version",
		 * UnionpayContents.version); // 字符集编码 默认"UTF-8"
		 * paramMap.put("encoding", UnionpayContents.encoding); // 签名方法 01 RSA
		 * paramMap.put("signMethod",UnionpayContents.signMethod); // 交易类型 01-消费
		 * paramMap.put("txnType", "01"); // 交易子类型 01:自助消费 02:订购 03:分期付款
		 * paramMap.put("txnSubType", UnionpayContents.txnSubType); // 业务类型
		 * 000201 B2C网关支付 paramMap.put("bizType",UnionpayContents.bizType); //
		 * 渠道类型 07-互联网渠道
		 * paramMap.put("channelType",UnionpayContents.channelType); //
		 * 商户/收单后台接收地址 必送 //后台服务对应的写法参照 BackRcvResponse.java
		 * paramMap.put("backUrl",
		 * ComContents.payserviceurl+payCommon.getNotifyUrl());
		 * paramMap.put("frontUrl",
		 * ComContents.payserviceurl+payCommon.getReturnUrl()); // 接入类型:商户接入填0
		 * 0- 商户 ， 1： 收单， 2：平台商户 paramMap.put("accessType",
		 * UnionpayContents.accessType); // 商户号码
		 * paramMap.put("merId",UnionpayContents
		 * .MEM_ID);//生产环境UnionpayContents.MEM_ID // 订单号 商户根据自己规则定义生成，每订单日期内不重复
		 * paramMap.put("orderId",payCommon.getOutTradeNo()); // 订单发送时间 格式：
		 * YYYYMMDDhhmmss 商户发送交易时间，根据自己系统或平台生成 paramMap.put("txnTime", new
		 * SimpleDateFormat("yyyyMMddHHmmss").format(new Date())); // 交易金额 分
		 * Double amount = 0d; //订单总金额 amount = 100 *
		 * payCommon.getPayAmount().doubleValue();
		 * paramMap.put("txnAmt",amount.intValue()+"");
		 * //paramMap.put("txnAmt",amount.toString()); // 交易币种
		 * paramMap.put("currencyCode",UnionpayContents.currencyCode); // 加密证书ID
		 * 根据需求选送 参考接口规范 有报文域加密时，该字段必须上送 paramMap.put("encryptCertId", ""); //
		 * 持卡人ip 根据需求选送 参考接口规范 防钓鱼用 paramMap.put("customerIp", "127.0.0.1");
		 * String html = ""; SDKConfig config = SDKConfig.getConfig();
		 * //获取本地商户私钥证书（签名），银联公钥证书（验签）等地址 String
		 * relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+
		 * "com/leimingtech/extend/module/payment/unionpay/pc";
		 * //加载配置acp_sdk.properties文件
		 * config.loadPropertiesFromPath(relapath);//loadPropertiesFromSrc();
		 */
		// 加载订单后后进行拼接
		// html =
		// UnionpayBase.createHtml(config.getFrontRequestUrl(),UnionpayBase.signData(paramMap));
		// 建立请求

		String html = "";
		Double amount = 0d; // 订单总金额
		amount = 100 * payCommon.getPayAmount().doubleValue();
		String orderDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("inputCharset", AllinpayContents.inputCharset);
		// paramMap.put("inputCharset", "1");

		paramMap.put("pickupUrl", AllinpayContents.pickupUrl + payCommon.getNotifyUrl());
		// paramMap.put("pickupUrl",
		// "http://www.riskoncloud.com/leimingtech-seller" +
		// payCommon.getNotifyUrl());

		paramMap.put("receiveUrl", AllinpayContents.receiveUrl + payCommon.getReturnUrl());
		// paramMap.put("receiveUrl",
		// "http://www.riskoncloud.com/leimingtech-seller" +
		// payCommon.getReturnUrl());
		// paramMap.put("pickupUrl",
		// "http://ceshi.allinpay.com/demo/eshop/display-pay-result/display.do");
		// paramMap.put("receiveUrl",
		// "http://ceshi.allinpay.com/demo/eshop/recv-pay-result/recv.do");

		paramMap.put("version", AllinpayContents.version);
		// paramMap.put("version", "v1.0");

		paramMap.put("language", AllinpayContents.language);
		// paramMap.put("language", "1");

		paramMap.put("signType", AllinpayContents.signType);
		// paramMap.put("signType", "1");

		paramMap.put("merchantId", AllinpayContents.merchantId);
		// paramMap.put("merchantId", "109060211512014");

		paramMap.put("orderNo", payCommon.getOutTradeNo());
		paramMap.put("orderAmount", String.valueOf(amount.longValue()));

		paramMap.put("orderCurrency", AllinpayContents.orderCurrency);
		// paramMap.put("orderCurrency", "0");

		paramMap.put("orderDatetime", orderDatetime);
		// paramMap.put("orderDatetime", orderDatetime);

		paramMap.put("payType", AllinpayContents.payType);
		// paramMap.put("payType", "0");

		paramMap.put("tradeNature", AllinpayContents.tradeNature);
		// paramMap.put("tradeNature", "GOODS");

		com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
		requestOrder.setInputCharset(Integer.parseInt(AllinpayContents.inputCharset));
		requestOrder.setPickupUrl(AllinpayContents.pickupUrl + payCommon.getNotifyUrl());
		requestOrder.setReceiveUrl(AllinpayContents.receiveUrl + payCommon.getReturnUrl());
		// requestOrder.setPickupUrl("http://ceshi.allinpay.com/demo/eshop/display-pay-result/display.do");
		// requestOrder.setReceiveUrl("http://ceshi.allinpay.com/demo/eshop/recv-pay-result/recv.do");
		requestOrder.setVersion(AllinpayContents.version);
		requestOrder.setLanguage(Integer.parseInt(AllinpayContents.language));
		requestOrder.setSignType(Integer.parseInt(AllinpayContents.signType));
		requestOrder.setPayType(Integer.parseInt(AllinpayContents.payType));
		requestOrder.setIssuerId("");
		requestOrder.setMerchantId(AllinpayContents.merchantId);
		requestOrder.setPayerName(AllinpayContents.PayerName);
		requestOrder.setPayerEmail(AllinpayContents.PayerEmail);
		requestOrder.setPayerTelephone(AllinpayContents.PayerTelephone);
		requestOrder.setPayerIDCard(AllinpayContents.PayerIDCard);
		requestOrder.setPid(AllinpayContents.Pid);
		requestOrder.setOrderNo(payCommon.getOutTradeNo());
		requestOrder.setOrderAmount(amount.longValue());
		requestOrder.setOrderCurrency(AllinpayContents.orderCurrency);
		requestOrder.setOrderDatetime(orderDatetime);
		requestOrder.setOrderExpireDatetime("");
		requestOrder.setProductName("");
		// requestOrder.setProductPrice(Long.parseLong(productPrice));
		// requestOrder.setProductNum(Integer.parseInt(productNum));
		// requestOrder.setProductId(productId);
		// requestOrder.setProductDesc(productDesc);
		// requestOrder.setExt1(ext1);
		// requestOrder.setExt2(ext2);
		// requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
		// requestOrder.setPan(pan);
		requestOrder.setTradeNature(AllinpayContents.tradeNature);
		requestOrder.setKey("1234567890"); // key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

		String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
		String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
		paramMap.put("signMsg", strSignMsg);
		System.out.println(strSignMsg);
		html = AllinpayBase.createHtml("https://service.allinpay.com/gateway/index.do?", AllinpayBase.signData(paramMap));
		return html;
	}

	@Override
	public Map<String, Object> toPayfront(HttpServletRequest request, HttpServletResponse rep) {
		// 获取本地商户私钥证书（签名），银联公钥证书（验签）等地址
		// String
		// relapath=SpringContextUtil.getResourceRootRealPath()+File.separator+"com/leimingtech/extend/module/payment/unionpay/pc";
		// 加载配置acp_sdk.properties文件
		// SDKConfig.getConfig().loadPropertiesFromPath(relapath);//loadPropertiesFromSrc();
		LogUtil.writeLog("FrontRcvResponse前台接收报文返回开始");
		PrintWriter out = null;
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("payResult", "0");
		rep.setCharacterEncoding("UTF-8");
		try {
			out = rep.getWriter();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String encoding = "UTF-8";// request.getParameter(SDKConstants.param_encoding);
		log.debug("返回报文中encoding=[" + encoding + "]");
		/*
		 * String pageResult = ""; if ("UTF-8".equalsIgnoreCase(encoding)) {
		 * pageResult = "/cart/pay_result"; } else { pageResult =
		 * "/cart/pay_result"; }
		 */
		Map<String, String> respParam = getAllRequestParam(request);
		// 打印请求报文
		LogUtil.printRequestLog(respParam);
		Map<String, String> valideData = null;
		StringBuffer page = new StringBuffer();
		if (null != respParam && !respParam.isEmpty()) {
			Iterator<Entry<String, String>> it = respParam.entrySet().iterator();
			valideData = new HashMap<String, String>(respParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes("UTF-8"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				page.append("<tr><td width=\"30%\" align=\"right\">" + key + "(" + key + ")</td><td>" + value + "</td></tr>");
				valideData.put(key, value);
			}
		}
		if (!SDKUtil.validate(valideData, encoding)) {
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>失败</td></tr>");
			log.debug("验证签名结果[失败].");
			// out.println("failure");
			payMap.put("payResult", "0");
			payMap.put("msg", "验证签名结果[失败]");
		} else {
			System.out.println("ordersn:**********" + valideData.get("orderId"));
			page.append("<tr><td width=\"30%\" align=\"right\">验证签名结果</td><td>成功</td></tr>");
			log.debug("验证签名结果[成功].");
			// payService.updatePayFinish(valideData.get("orderId"));
			// out.print("success");//向银行发送处理结果
			payMap.put("payResult", "1");
			payMap.put("orderNo", valideData.get("orderNo"));
			payMap.put("paymentOrderId", valideData.get("paymentOrderId"));// 交易流水号
			payMap.put("msg", "验证签名结果[成功]");
		}
		System.out.println("ordersn:**********" + valideData.get("orderNo"));
		request.setAttribute("result", page.toString());
		log.debug("FrontRcvResponse前台接收报文返回结束");
		return payMap;
	}

	@Override
	public Map<String, Object> toPayback(HttpServletRequest request, HttpServletResponse resp) {
		// 获取本地商户私钥证书（签名），银联公钥证书（验签）等地址
		/*
		 * String
		 * relapath=SpringContextUtil.getResourceRootRealPath()+File.separator
		 * +"com/leimingtech/extend/module/payment/allinpay/pc";
		 * //加载配置acp_sdk.properties文件
		 * SDKConfig.getConfig().loadPropertiesFromPath
		 * (relapath);//loadPropertiesFromSrc();
		 */// PrintWriter out = null;
			// String mes="";
		Map<String, Object> payMap = new HashMap<String, Object>();
		payMap.put("payResult", "0");
		// try {
		// out= resp.getWriter();
		// } catch (IOException e2) {
		// e2.printStackTrace();
		// }
		log.debug("BackRcvResponse接收后台通知开始");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String encoding = "UTF-8";// request.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = getAllRequestParam(request);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				try {
					value = new String(value.getBytes("UTF-8"), encoding);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				valideData.put(key, value);
			}
		}
		/*
		 * // 验证签名 if (!SDKUtil.validate(valideData, encoding)) {
		 * log.debug("验证签名结果[失败]."); payMap.put("paystate","failure");
		 * payMap.put("msg","验证签名结果[失败]"); // out.println("failure"); //
		 * mes="false"; }else {
		 * //payService.updatePayFinish(valideData.get("orderId"));
		 * log.debug("验证签名结果[成功]."); payMap.put("paystate","success");
		 * payMap.put("out_trade_no",valideData.get("orderId"));
		 * payMap.put("trade_no",valideData.get("queryId"));//交易流水号
		 * payMap.put("msg","验证签名结果[成功]"); }
		 */
		log.debug("BackRcvResponse接收后台通知结束");
		payMap.put("payResult", "1");
		payMap.put("msg", "验证签名结果[成功]");
		payMap.put("orderNo", valideData.get("orderNo"));// 订单号
		payMap.put("paymentOrderId", valideData.get("paymentOrderId"));// 交易流水号
		return payMap;
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				if (res.get(en) == null || "".equals(res.get(en))) {
					// System.out.println("======为空的字段名===="+en);
					res.remove(en);
				}
			}
		}
		return res;
	}

	/**
	 * 拼接订单body
	 * 
	 * @param orderId
	 * @return
	 */
	// public String getBody(int orderId){
	// //List<OrderCommon> orderCommon=
	// orderCommonDao.selectByOrderId(Integer.valueOf(odrderId));
	// int[] ids = new int[]{orderId};
	// List<OrderGoods> orderGoods = orderGoodsDao.findByOrderId(ids);
	// String body = "无数据";
	// if(orderGoods.size()>0){
	// String name = "";
	// for(int i=0; i<orderGoods.size(); i++){
	// OrderGoods goods = orderGoods.get(i);
	// name += goods.getGoodsName()+",";
	// }
	// body = name;
	// }
	// return body;
	// }

	public static void main(String[] args) {
		com.allinpay.ets.client.RequestOrder requestOrder = new com.allinpay.ets.client.RequestOrder();
		requestOrder.setInputCharset(1);
		requestOrder.setPickupUrl("http://ceshi.allinpay.com/demo/eshop/display-pay-result/display.do");
		requestOrder.setReceiveUrl("http://ceshi.allinpay.com/demo/eshop/recv-pay-result/recv.do");
		requestOrder.setVersion("v1.0");
		requestOrder.setLanguage(1);
		requestOrder.setSignType(1);
		requestOrder.setPayType(0);
		requestOrder.setIssuerId("");
		requestOrder.setMerchantId("100020091218001");
		requestOrder.setPayerName("");
		requestOrder.setPayerEmail("");
		requestOrder.setPayerTelephone("");
		requestOrder.setPayerIDCard("");
		requestOrder.setPid("");
		requestOrder.setOrderNo("NO20151226131748");
		requestOrder.setOrderAmount(Long.parseLong("200"));
		requestOrder.setOrderCurrency("0");
		requestOrder.setOrderDatetime("20151226131748");
		requestOrder.setOrderExpireDatetime("");
		requestOrder.setProductName("");
		// requestOrder.setProductPrice(Long.parseLong(productPrice));
		// requestOrder.setProductNum(Integer.parseInt(productNum));
		// requestOrder.setProductId(productId);
		// requestOrder.setProductDesc(productDesc);
		// requestOrder.setExt1(ext1);
		// requestOrder.setExt2(ext2);
		// requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
		// requestOrder.setPan(pan);
		requestOrder.setTradeNature("GOODS");
		requestOrder.setKey("1234567890"); // key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

		String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
		String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
		System.out.println(strSignMsg);
	}
}
