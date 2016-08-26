package com.aipg.agrmqx;

public class XQSignReq {
	private String MERCHANT_ID;	//商户代码
	private String ACCT;		//账号
	private String AGREEMENTNO;	//协议号
	
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getACCT() {
		return ACCT;
	}
	public void setACCT(String aCCT) {
		ACCT = aCCT;
	}
	public String getAGREEMENTNO() {
		return AGREEMENTNO;
	}
	public void setAGREEMENTNO(String aGREEMENTNO) {
		AGREEMENTNO = aGREEMENTNO;
	}
	
	
	

}
