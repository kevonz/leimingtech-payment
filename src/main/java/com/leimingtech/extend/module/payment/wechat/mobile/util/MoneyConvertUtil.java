/**
 * 
 */
package com.leimingtech.extend.module.payment.wechat.mobile.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-23 下午4:41:25
 */
public class MoneyConvertUtil {
	
	/**
	 * 将一个以元为单位的价格转换为以分为单位的价格
	 * 
	 * @param yuan_price
	 *            单位为元的价格
	 * @return
	 */
	public static String yuanToFen(String yuan_price) {
		BigDecimal yuan = new BigDecimal("0");// 元
		BigDecimal jiao = new BigDecimal("0");// 角
		BigDecimal fen = new BigDecimal("0");// 分
		BigDecimal fen_price = new BigDecimal("0");
		if(!yuan_price.contains(".")) {
			yuan_price = yuan_price + ".00";
		}
		if(yuan_price.split("\\.")[1].length()==1){
			yuan_price = yuan_price + "0";
		}
		yuan = new BigDecimal(yuan_price.split("\\.")[0]);
		jiao = new BigDecimal(yuan_price.split("\\.")[1].charAt(0) + "");
		fen = new BigDecimal(yuan_price.split("\\.")[1].charAt(1) + "");
		fen_price = yuan.multiply(new BigDecimal("100")).add(jiao.multiply(new BigDecimal("10"))).add(fen);
		return fen_price.toString();
	}
	
	/**
	 * 将一个以分为单位的价格转换为以元为单位的价格
	 * 
	 * @param yuan_price
	 *            单位为元的价格
	 * @return
	 */
	public static String fenToYuan(String fen_price) {
		BigDecimal fen = new BigDecimal(fen_price);// 分
		BigDecimal yuan = fen.divide(new BigDecimal("100") , 2 , RoundingMode.HALF_EVEN);
		return yuan.toString();
	}
}
