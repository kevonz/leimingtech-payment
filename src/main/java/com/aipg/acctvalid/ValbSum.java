package com.aipg.acctvalid;

/**
 * 批量验证报文基本信息
 * @author DON
 *
 */
public class ValbSum {
	private String MERCHANT_ID;  //商户代码
	private String SUBMIT_TIME;  //提交时间
	private String TOTAL_ITEM;   //总记录数
	private String TOTAL_SUM;    //总金额
	
	
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getSUBMIT_TIME() {
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String sUBMITTIME) {
		SUBMIT_TIME = sUBMITTIME;
	}
	public String getTOTAL_ITEM() {
		return TOTAL_ITEM;
	}
	public void setTOTAL_ITEM(String tOTALITEM) {
		TOTAL_ITEM = tOTALITEM;
	}
	public String getTOTAL_SUM() {
		return TOTAL_SUM;
	}
	public void setTOTAL_SUM(String tOTALSUM) {
		TOTAL_SUM = tOTALSUM;
	}
	
	

}
