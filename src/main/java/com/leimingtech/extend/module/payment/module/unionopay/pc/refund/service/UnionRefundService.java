package com.leimingtech.extend.module.payment.module.unionopay.pc.refund.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leimingtech.core.entity.UnionRefund;

public interface UnionRefundService {

	public String toUnionRefund(UnionRefund unionRefund);
	
	public Map<String,Object> UnionRefundfront(HttpServletRequest request,HttpServletResponse rep);
	
	public Map<String,Object> UnionRefundback(HttpServletRequest request,HttpServletResponse resp);
	
}
