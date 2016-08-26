package com.leimingtech.extend.module.payment.module.whchat.h5.pay.scan.WechatScanService.impl;

import java.net.InetAddress;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.leimingtech.core.entity.PayCommon;
import com.leimingtech.extend.module.payment.ComContents;
import com.leimingtech.extend.module.payment.module.whchat.h5.pay.scan.WechatScanService.WechatScanService;
import com.leimingtech.extend.module.payment.wechat.h5.config.WachatContent;
import com.leimingtech.extend.module.payment.wechat.h5.handler.GetWxOrderno;
import com.leimingtech.extend.module.payment.wechat.h5.handler.RequestHandler;
import com.leimingtech.extend.module.payment.wechat.h5.handler.TenpayUtil;

@Slf4j
@Service
public class WechatScanServiceImpl implements WechatScanService{
	@Override
	public String toPay(PayCommon payCommon) {
		        // 1 参数
				// 订单号
				String orderId =payCommon.getOutTradeNo();
				// 附加数据 原样返回
				String attach = "商品";
				// 总金额以分为单位，不带小数点
				String totalFee = getMoney(payCommon.getPayAmount()+"");
				// 订单生成的机器 IP
				InetAddress ia=null;
				//String spbill_create_ip =ia.getHostAddress();//获取本地ip
				String spbill_create_ip ="125.34.215.215";//获取本地ip
				// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
				String notify_url = ComContents.payserviceurl+payCommon.getNotifyUrl();;
				String trade_type = "NATIVE";
				// 商户号
				String mch_id = WachatContent.partner;
				//appid
				String appid=WachatContent.appid;
				//应用密钥
				String appsecret=WachatContent.appsecret;
				//API密钥，在商户平台设置
				String apikey=WachatContent.apikey;
				// 随机字符串
				String nonce_str = getNonceStr();
				// 商品描述根据情况修改
				String body ="雷铭购物";
				// 商户订单号
				String out_trade_no = payCommon.getOutTradeNo();

				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				packageParams.put("appid", WachatContent.appid);
				packageParams.put("mch_id",mch_id);
				packageParams.put("nonce_str", nonce_str);
				packageParams.put("body", body);
				packageParams.put("attach", attach);
				packageParams.put("out_trade_no", out_trade_no);

				// 这里写的金额为1 分到时修改
				packageParams.put("total_fee", totalFee);
				packageParams.put("spbill_create_ip", spbill_create_ip);
				packageParams.put("notify_url", notify_url);

				packageParams.put("trade_type", trade_type);

				RequestHandler reqHandler = new RequestHandler(null, null);
				reqHandler.init(appid, appsecret,apikey);
				String sign = reqHandler.createSign(packageParams);
				String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
						+ mch_id + "</mch_id>" + "<nonce_str>" + nonce_str
						+ "</nonce_str>" + "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + body + "]]></body>" 
						+ "<out_trade_no>" + out_trade_no
						+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
						+ "<total_fee>" + totalFee + "</total_fee>"
						+ "<spbill_create_ip>" + spbill_create_ip
						+ "</spbill_create_ip>" + "<notify_url>" + notify_url
						+ "</notify_url>" + "<trade_type>" + trade_type
						+ "</trade_type>" + "</xml>";
				String code_url = "";
				String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
				code_url = new GetWxOrderno().getCodeUrl(createOrderURL, xml);
				System.out.println("code_url----------------"+code_url);
				return code_url;
	}
	
	/**
	 * 获取随机字符串
	 * @return
	 */
	public static String getNonceStr() {
		// 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
	}

	/**
	 * 元转换成分
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// 金额转化为分为单位
		String currency =  amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
	
	
	public static void main(String[] args) {
		System.out.println(getMoney("0.02"));
	}
}
