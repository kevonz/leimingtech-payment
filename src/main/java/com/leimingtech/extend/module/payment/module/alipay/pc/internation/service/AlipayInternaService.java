package com.leimingtech.extend.module.payment.module.alipay.pc.internation.service;

import javax.servlet.http.HttpServletRequest;

public interface AlipayInternaService {

	public String toPay(String orderSn);
	
	public String payfront(HttpServletRequest request);
	
	public String payback(HttpServletRequest request);
	
}
