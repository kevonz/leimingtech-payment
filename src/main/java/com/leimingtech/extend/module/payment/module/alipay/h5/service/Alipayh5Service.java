package com.leimingtech.extend.module.payment.module.alipay.h5.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.leimingtech.core.entity.PayCommon;

public interface Alipayh5Service {

	public String toPay(PayCommon payCommon);
	
	public Map<String,Object> payfront(HttpServletRequest request);
	
	public Map<String,Object> payback(HttpServletRequest request);
	
}
