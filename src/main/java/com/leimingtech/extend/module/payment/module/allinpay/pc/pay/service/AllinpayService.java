package com.leimingtech.extend.module.payment.module.allinpay.pc.pay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aipg.rtreq.Trans;
import com.leimingtech.core.entity.PayCommon;

public interface AllinpayService {

	public String toPay(PayCommon payCommon);
	
	public Map<String,Object> toPayfront(HttpServletRequest request,HttpServletResponse rep);
	
	public Map<String,Object> toPayback(HttpServletRequest request,HttpServletResponse resp);
	
	public String withdraw(Trans trans);
	
}
