package com.leimingtech.extend.module.payment.module.unionopay.pc.pay.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leimingtech.core.entity.PayCommon;

public interface UnionpayService {

	public String toUnionpay(PayCommon payCommon);
	
	public Map<String,Object> Unionpayfront(HttpServletRequest request,HttpServletResponse rep);
	
	public Map<String,Object> Unionpayback(HttpServletRequest request,HttpServletResponse resp);
	
}
