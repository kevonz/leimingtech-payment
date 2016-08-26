package com.leimingtech.extend.module.payment.alipay.mobile.config;

import lombok.extern.slf4j.Slf4j;

import com.leimingtech.extend.module.payment.ComContents;

/* *
 * 类名：AlipayConfig
 * 功能：基础配置类
 * 详细：设置帐户有关信息及返回路径
 * 版本：3.3
 * 日期：2012-08-10
 * 说明：
 * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 * 
 * 提示：如何获取安全校验码和合作身份者ID
 * 1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 * 2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 * 3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”
 * 
 * 安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 * 解决方法：
 * 1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 * 2、更换浏览器或电脑，重新登录查询。
 */
@Slf4j
public class AlipayConfig {
	
	/**
	 * 支付宝异步通知路径
	 */
	public static String NOTIFYURL =ComContents.payserviceurl+AlipayMobileContents.alipaymobilebackUrl;
	/**
	 * 支付宝充值异步通知路径
	 */
	public static String NOTIFYURL_TOPUP = "";
	/**
	 * 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	 */
	public static String RETURNURL = "m.alipay.com";
	/**
	 * 设置未付款交易的超时时间 默认30分钟，一旦超时，该笔交易就会自动被关闭。 取值范围：1m～15d。
	 * m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点，如1.5h，可转换为90m。
	 */
	public static String ITBPAY = AlipayMobileContents.it_b_pay;
	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 合作身份者ID，以2088开头由16位纯数字组成的字符串
	 */
	public static String PARTNER = AlipayMobileContents.partner;
	
	/**
	 * 商户的私钥
	 */
	public static String PRIVATE_KEY = AlipayMobileContents.privatekey;
	
	/**
	 * 支付宝的公钥，无需修改该值
	 */
	public static String ALI_PUBLIC_KEY =AlipayMobileContents.alipublickey;
	
	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * 调试用，创建TXT日志文件夹路径
	 */
	public static String LOG_PATH = "E:\\";
	
	/**
	 * 字符编码格式 目前支持 gbk 或 utf-8
	 */
	public static String INPUT_CHARSET = "utf-8";
	
	/**
	 * 签名方式 不需修改
	 */
	public static String SIGN_TYPE = "RSA";
	
	/**
	 * 卖家支付宝账号
	 */
	public static String SELLERID=AlipayMobileContents.sellerid;
	/**
	 * 商品名称
	 */
	public static String GOODSNAME=AlipayMobileContents.goodsName;
	/**
	 * 商品详情描述
	 */
	public static String GOODSDETAIL=AlipayMobileContents.goodsDetail;
	
	/*static {
		initAlipayConfig();
	}
	
	*//**
	 * 初始化支付宝支付参数
	 *//*
	private static void initAlipayConfig() {
		ResourceBundle rb = ResourceBundle.getBundle("com.leimingtech.front.pay.mobile.alipay.config.alipayConfig");
		String notifyUrl = rb.getString("notifyUrl");
		String return_url = rb.getString("return_url");
		String it_b_pay = rb.getString("it_b_pay");
		String goodsName = rb.getString("goodsName");
		String goodsDetail = rb.getString("goodsDetail");
		String partner = rb.getString("partner");
		String private_key = rb.getString("private_key");
		String ali_public_key = rb.getString("ali_public_key");
		String log_path = rb.getString("log_path");
		String input_charset = rb.getString("input_charset");
		String sign_type = rb.getString("sign_type");
		String sellerId = rb.getString("sellerId");
		String notifyUrl_topUp = rb.getString("notifyUrl_topUp");
		//if(StringUtil.isEmpty(partner) || StringUtil.isEmpty(private_key) || StringUtil.isEmpty(ali_public_key) || StringUtil.isEmpty(log_path) || StringUtil.isEmpty(input_charset) || StringUtil.isEmpty(sign_type)) {
		if(partner.isEmpty()|| private_key.isEmpty() || ali_public_key.isEmpty() ||log_path.isEmpty() || input_charset.isEmpty() || sign_type.isEmpty()) {
			//Logger loger = Logger.getLogger(AlipayConfig.class);
			NullPointerException e = new NullPointerException("请完整设置支付宝支付相关参数参数!");
			log.error("请完整设置支付宝支付相关参数参数!(参数设置位置：com.leimingtech.pinche.pay.mobile.alipay.config.alipayConfig.properties)" , e);
		}
		AlipayConfig.NOTIFYURL = notifyUrl;
		AlipayConfig.RETURNURL = return_url;
		AlipayConfig.ITBPAY = it_b_pay;
		AlipayConfig.GOODSNAME = goodsName;
		AlipayConfig.GOODSDETAIL = goodsDetail;
		AlipayConfig.PARTNER = partner;
		AlipayConfig.PRIVATE_KEY = private_key;
		AlipayConfig.ALI_PUBLIC_KEY = ali_public_key;
		AlipayConfig.LOG_PATH = log_path;
		AlipayConfig.INPUT_CHARSET = input_charset;
		AlipayConfig.SIGN_TYPE = sign_type;
		AlipayConfig.SELLERID = sellerId;
		AlipayConfig.NOTIFYURL_TOPUP = notifyUrl_topUp;
	}*/
}
