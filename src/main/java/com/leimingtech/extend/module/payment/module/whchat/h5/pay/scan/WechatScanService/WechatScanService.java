package com.leimingtech.extend.module.payment.module.whchat.h5.pay.scan.WechatScanService;

import com.leimingtech.core.entity.PayCommon;
/**
 * 微信扫描二维码支付
 * @author ihui
 *
 */
public interface WechatScanService {
	    //生成二维码
		public String toPay(PayCommon payCommon);
}
