package com.leimingtech.extend.module.payment.unionpay.pc.gwj.service;

import com.leimingtech.core.entity.Area;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.conf.GwjConfig;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.conf.GwjConstants;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain.Biz;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.domain.Category;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.util.HttpUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.util.JsonUtil;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.vo.BaseVo;
import com.leimingtech.extend.module.payment.unionpay.pc.gwj.vo.ResponseBaseVo;

public class GwjService {
	
	/**
	 * 获取已开通业务的地区列表
	 * @return 已开通业务的地区列表
	 */
	public static Area[] getAreas() {
		String areas = HttpUtil.getAsString(GwjConfig.GWJ_URL + GwjConstants.PREFIX + GwjConstants.SUFFIX_URL_AREAS);
		return JsonUtil.fromJson(areas, Area[].class);
    }
	
	/**
	 * 获取指定地区的业务列表
	 * @param areaCode 地区代码
	 * @return 业务列表
	 */
	public static Category[] getCategories(String areaCode){
		String categories = HttpUtil.getAsString(GwjConfig.GWJ_URL + GwjConstants.PREFIX + GwjConstants.SUFFIX_URL_CATEGORIES + "/" + areaCode);
		return JsonUtil.fromJson(categories, Category[].class);
	}
	
	/**
	 * 获取指定的业务要素
	 * 
	 * @param bussCode 业务编码
	 * @return 业务要素
	 */
	public static Biz getBiz(String bussCode){
		String biz = HttpUtil.getAsString(GwjConfig.GWJ_URL + GwjConstants.PREFIX + GwjConstants.SUFFIX_URL_BIZ + "/" + bussCode);
		return JsonUtil.fromJson(biz, Biz.class);
	}
	
	/**
	 * 获取验证码图片
	 * 
	 * @param vid 验证码编号
	 * @return 验证码图片字节流
	 */
	public static byte[] getVerifyCodeImg(String vid){
		return HttpUtil.get(GwjConfig.GWJ_URL + GwjConstants.ENTRY + GwjConstants.SUFFIX_URL_GETCAPTCHA + "&vid=" + vid);
	}
	
	/**
	 * 获取验证码图片
	 * 
	 * @param vid 验证码编号
	 * @param color 验证码图片背景颜色
	 * @return 验证码图片字节流
	 */
	public static byte[] getVerifyCodeImg(String vid, String color){
		return HttpUtil.get(GwjConfig.GWJ_URL + GwjConstants.ENTRY + GwjConstants.SUFFIX_URL_GETCAPTCHA + "&vid=" + vid + "&color=" + color);
	}
	
	/**
	 * 获取验证码编号
	 * 
	 * @return 验证码编号
	 */
	public static String getVid(){
		return new String(HttpUtil.get(GwjConfig.GWJ_URL + GwjConstants.ENTRY + GwjConstants.SUFFIX_URL_GETVID));
	}
	
	/**
	 * 发起账单查询预操作、获取账单结果、账单缴费预操作
	 * 
	 * @param request 请求
	 * @return 应答
	 */
	public static ResponseBaseVo transaction(BaseVo request){
		String resp = HttpUtil.post(GwjConfig.GWJ_URL + GwjConstants.ENTRY, JsonUtil.toJson(request));
		return JsonUtil.fromJson(resp, ResponseBaseVo.class);
	}
	
	public static void main(String[] args) {
		System.out.println(JsonUtil.toJson(getBiz("D1_3600_0201")));
	}
}
