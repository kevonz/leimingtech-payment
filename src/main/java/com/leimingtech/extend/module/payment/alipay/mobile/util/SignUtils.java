package com.leimingtech.extend.module.payment.alipay.mobile.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import com.leimingtech.core.entity.base.Pay;
import com.leimingtech.extend.module.payment.alipay.mobile.config.AlipayConfig;
import com.leimingtech.extend.module.payment.alipay.mobile.sign.Base64;

/**
 * 支付宝签名工具
 * 
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-22 下午9:16:39
 */
public class SignUtils {
	
	private static final String ALGORITHM = "RSA";
	
	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 * @return
	 */
	private static String sign(String content) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(AlipayConfig.PRIVATE_KEY));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));
			byte [] signed = signature.sign();
			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对订单进行签名
	 * 
	 * @param porder
	 *            订单对象
	 * @param isTopUp
	 * @param b
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String signOrderInfo(Pay order , boolean isTopUp) throws UnsupportedEncodingException {
		if(order == null) {
			return null;
		}
		String outTradeNo = order.getPaySn();// 订单编号
		String goodsName = new String((isTopUp ? "余额充值" : AlipayConfig.GOODSNAME).getBytes("ISO8859-1") , "UTF-8");// 商品名称
		String goodsDetail = new String((isTopUp ? "余额充值" : AlipayConfig.GOODSDETAIL).getBytes("ISO8859-1") , "UTF-8");// 商品描述
		String goodsPrice = order.getPayAmount() + "";// MoneyConvertUtil.yuanToFen(porder.getSummoney()
		String sign;
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + AlipayConfig.PARTNER + "\"";
		try {
			// 卖家支付宝账号
			orderInfo += "&seller_id=" + "\"" + AlipayConfig.SELLERID + "\"";
			// 商户网站唯一订单号
			orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";
			// 商品名称
			orderInfo += "&subject=" + "\"" + goodsName + "\"";
			// 商品详情
			orderInfo += "&body=" + "\"" + goodsDetail + "\"";
			// 商品金额
			orderInfo += "&total_fee=" + "\"" + goodsPrice + "\"";
			// 服务器异步通知页面路径
			orderInfo += "&notify_url=" + "\"" + (isTopUp ? (AlipayConfig.NOTIFYURL_TOPUP) : (AlipayConfig.NOTIFYURL)) + "\"";
			// 接口名称， 固定值
			orderInfo += "&service=\"" + "mobile.securitypay.pay" + "\"";
			// 支付类型， 固定值
			orderInfo += "&payment_type=\"1\"";
			// 参数编码， 固定值
			orderInfo += "&_input_charset=\"" + AlipayConfig.INPUT_CHARSET + "\"";
			// 设置未付款交易的超时时间
			// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
			// 取值范围：1m～15d。
			// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
			// 该参数数值不接受小数点，如1.5h，可转换为90m。
			orderInfo += "&it_b_pay=\"" + AlipayConfig.ITBPAY + "\"";
			// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
			orderInfo += "&return_url=\"" + AlipayConfig.RETURNURL + "\"";
			// 调用银行卡支付，需配置此参数，参与签名， 固定值
			// orderInfo += "&paymethod=\"expressGateway\"";
			sign = SignUtils.sign(orderInfo);
			sign = URLEncoder.encode(sign , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			sign = null;
		}
		String payInfo = orderInfo + "&sign=\"" + sign + "\"&sign_type=\"" + AlipayConfig.SIGN_TYPE + "\"";
		return payInfo;
	}
}
