package com.aipg.cash;

public class CashReq {
	private String ACCTNO;
	private String BANKACCT;
	private String AMOUNT;
	private String USAGE;
	private String MEMO;
	private String CUST_USERID;
	
	public String getACCTNO() {
		return ACCTNO;
	}
	public void setACCTNO(String aCCTNO) {
		ACCTNO = aCCTNO;
	}
	public String getBANKACCT() {
		return BANKACCT;
	}
	public void setBANKACCT(String bANKACCT) {
		BANKACCT = bANKACCT;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getUSAGE() {
		return USAGE;
	}
	public void setUSAGE(String uSAGE) {
		USAGE = uSAGE;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
	}
	public String getCUST_USERID() {
		return CUST_USERID;
	}
	public void setCUST_USERID(String cUSTUSERID) {
		CUST_USERID = cUSTUSERID;
	}
	
}
