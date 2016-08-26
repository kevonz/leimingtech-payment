package com.aipg.common;

import java.util.HashMap;
import java.util.Map;

public class RetCodeMapping {
	
	
	public static Map<String,String> retCodeMap;
	
	static{
		retCodeMap = new HashMap<String,String>();
		retCodeMap.put("", "");                    //处理中
		retCodeMap.put("0000","交易成功");
		retCodeMap.put("4000","跨行交易已发送银行");//(表示最终成功)
		retCodeMap.put("0397","渠道不支持");
		retCodeMap.put("3001","查开户方原因");
		retCodeMap.put("3002","没收卡");
		retCodeMap.put("3003","不予承兑");
		retCodeMap.put("3004","无效卡号"); 
		retCodeMap.put("3005","受卡方与安全保密部门联系");
		retCodeMap.put("3006","已挂失卡");
		retCodeMap.put("3007","被窃卡");
		retCodeMap.put("3008","余额不足");
		retCodeMap.put("3009","无此账户");
		retCodeMap.put("3010","过期卡");
		retCodeMap.put("3011","密码错");
		retCodeMap.put("3012","不允许持卡人进行的交易");
		retCodeMap.put("3013","超出提款限额");
		retCodeMap.put("3014","原始金额不正确");
		retCodeMap.put("3015","超出取款次数限制");
		retCodeMap.put("3016","已挂失折");
		retCodeMap.put("3017","账户已冻结");
		retCodeMap.put("3018","已清户");
		retCodeMap.put("3019","原交易已被取消或冲正");
		retCodeMap.put("3020","账户被临时锁定");
		retCodeMap.put("3021","未登折行数超限");
		retCodeMap.put("3022","存折号码有误");
		retCodeMap.put("3023","当日存入的金额当日不能支取");
		retCodeMap.put("3024","日期切换正在处理");
		retCodeMap.put("3025","PIN格式出错");
		retCodeMap.put("3026","发卡方保密子系统失败");
		retCodeMap.put("3027","原始交易不成功");
		retCodeMap.put("3028","系统忙，请稍后再提交");
		retCodeMap.put("3029","交易已被冲正");
		retCodeMap.put("3030","账号错误");
		retCodeMap.put("3031","账号户名不符");
		retCodeMap.put("3032","账号货币不符");
		retCodeMap.put("3033","无此原交易");
		retCodeMap.put("3034","非活期账号");
		retCodeMap.put("3035","找不到原记录");
		retCodeMap.put("3036","货币错误");
		retCodeMap.put("3037","磁卡未生效");
		retCodeMap.put("3038","非通兑户");
		retCodeMap.put("3039","账户已关户");
		retCodeMap.put("3040","金额错误");
		retCodeMap.put("3041","非存折户");
		retCodeMap.put("3042","交易金额小于该储种的最低支取金额");
		retCodeMap.put("3043","未与银行签约");
		retCodeMap.put("3044","超时拒付");
		retCodeMap.put("3045","合同（协议）号在协议库里不存在");
		retCodeMap.put("3046","合同（协议）号还没有生效");
		retCodeMap.put("3047","合同（协议）号已撤销");
		retCodeMap.put("3048","业务已经清算，不能撤销");
		retCodeMap.put("3049","业务已被拒绝，不能撤销");
		retCodeMap.put("3050","业务已撤销");
		retCodeMap.put("3051","重复业务");
		retCodeMap.put("3052","找不到原业务");
		retCodeMap.put("3053","批量回执包未到规定最短回执期限（M日）");
		retCodeMap.put("3054","批量回执包超过规定最长回执期限（N日）");
		retCodeMap.put("3055","当日通兑业务累计金额超过规定金额");
		retCodeMap.put("3056","退票");
		retCodeMap.put("3057","账户状态错误");
		retCodeMap.put("3058","数字签名或证书错");
		retCodeMap.put("3059","通讯失败");
		retCodeMap.put("3065","户名错");
		retCodeMap.put("3066","渠道不支持，交易无法支持");
		retCodeMap.put("3067","该账户已欠费，不能办理此业务");
		retCodeMap.put("3068","非个人活期结算户");
		retCodeMap.put("3069","长期不动户，请到柜台办理业务");
		retCodeMap.put("3071","外部系统错误");
		retCodeMap.put("3072","提交金额等于或低于应收取的手续费");
		retCodeMap.put("3999","其它错误");
	}
}
