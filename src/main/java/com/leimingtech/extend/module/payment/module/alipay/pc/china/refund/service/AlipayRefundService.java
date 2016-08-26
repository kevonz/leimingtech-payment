package com.leimingtech.extend.module.payment.module.alipay.pc.china.refund.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.AliPayRefund;

public interface AlipayRefundService {

	public String toRefund(AliPayRefund aliPayRefund);
	
	public Map<String,Object> Refundback(HttpServletRequest request);
	
}
