/**
 * 
 */
package com.leimingtech.extend.module.payment.unionpay.mobile.config.base;

/**
 * @company 雷铭智信
 * @author 谢进伟
 * @DateTime 2015-3-29 下午12:20:48
 */
public class UnionpayBaseConfig {
	
	/** 交易类型 01-消费 */
	protected String txnType = "";
	/** 前台通知地址 ，控件接入方式无作用 */
	protected String frontUrl = "";
	/** 交易币种 */
	protected String currencyCode = "";
	/** 渠道类型，07-PC，08-手机 */
	protected String channelType = "";
	/** 订单描述，可不上送，上送时控件中会显示该信息 */
	protected String orderDesc = "";
	/** 商户号码，请改成自己的商户号 */
	protected String merId = "";
	/** 交易子类型 01:自助消费 02:订购 03:分期付款 */
	protected String txnSubType = "";
	/** 交易金额，单位分 */
	protected String txnAmt = "";
	/** 版本号 */
	protected String version = "";
	/** 签名方法 01 RSA */
	protected String signMethod = "";
	/** 后台通知地址 */
	protected String backUrl = "";
	/** 字符集编码 默认"UTF-8" */
	protected String encoding = "";
	/** 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现 */
	protected String reqReserved = "";
	/** 业务类型 */
	protected String bizType = "";
	/** 商户订单号，8-40位数字字母 */
	protected String orderId = "";
	/** 订单发送时间，取系统时间 */
	protected String txnTime = "";
	/** 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户 */
	protected String accessType = "";
	
	/**
	 * @return 得到 txnType的值
	 */
	public String getTxnType() {
		return txnType;
	}
	
	/**
	 * @param txnType
	 *            设置 txnType值
	 */
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	
	/**
	 * @return 得到 frontUrl的值
	 */
	public String getFrontUrl() {
		return frontUrl;
	}
	
	/**
	 * @param frontUrl
	 *            设置 frontUrl值
	 */
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}
	
	/**
	 * @return 得到 currencyCode的值
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * @param currencyCode
	 *            设置 currencyCode值
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	/**
	 * @return 得到 channelType的值
	 */
	public String getChannelType() {
		return channelType;
	}
	
	/**
	 * @param channelType
	 *            设置 channelType值
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	
	/**
	 * @return 得到 orderDesc的值
	 */
	public String getOrderDesc() {
		return orderDesc;
	}
	
	/**
	 * @param orderDesc
	 *            设置 orderDesc值
	 */
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
	/**
	 * @return 得到 merId的值
	 */
	public String getMerId() {
		return merId;
	}
	
	/**
	 * @param merId
	 *            设置 merId值
	 */
	public void setMerId(String merId) {
		this.merId = merId;
	}
	
	/**
	 * @return 得到 txnSubType的值
	 */
	public String getTxnSubType() {
		return txnSubType;
	}
	
	/**
	 * @param txnSubType
	 *            设置 txnSubType值
	 */
	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}
	
	/**
	 * @return 得到 txnAmt的值
	 */
	public String getTxnAmt() {
		return txnAmt;
	}
	
	/**
	 * @param txnAmt
	 *            设置 txnAmt值
	 */
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	
	/**
	 * @return 得到 version的值
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * @param version
	 *            设置 version值
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return 得到 signMethod的值
	 */
	public String getSignMethod() {
		return signMethod;
	}
	
	/**
	 * @param signMethod
	 *            设置 signMethod值
	 */
	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	
	/**
	 * @return 得到 backUrl的值
	 */
	public String getBackUrl() {
		return backUrl;
	}
	
	/**
	 * @param backUrl
	 *            设置 backUrl值
	 */
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
	
	/**
	 * @return 得到 encoding的值
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * @param encoding
	 *            设置 encoding值
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * @return 得到 reqReserved的值
	 */
	public String getReqReserved() {
		return reqReserved;
	}
	
	/**
	 * @param reqReserved
	 *            设置 reqReserved值
	 */
	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	
	/**
	 * @return 得到 bizType的值
	 */
	public String getBizType() {
		return bizType;
	}
	
	/**
	 * @param bizType
	 *            设置 bizType值
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	/**
	 * @return 得到 orderId的值
	 */
	public String getOrderId() {
		return orderId;
	}
	
	/**
	 * @param orderId
	 *            设置 orderId值
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * @return 得到 txnTime的值
	 */
	public String getTxnTime() {
		return txnTime;
	}
	
	/**
	 * @param txnTime
	 *            设置 txnTime值
	 */
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	
	/**
	 * @return 得到 accessType的值
	 */
	public String getAccessType() {
		return accessType;
	}
	
	/**
	 * @param accessType
	 *            设置 accessType值
	 */
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
}
