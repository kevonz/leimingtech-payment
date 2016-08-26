package com.aipg.queryresp;

public class Ret_Detail {
	private String SN="";	
	private String ACCOUNT="";
	private String ACCOUNT_NAME="";
	private String AMOUNT="";
	private String RET_CODE="";
	private String ERR_MSG="";
	private String CUST_USERID;
	private String REMARK;

	public String getCUST_USERID()
	{
		return CUST_USERID;
	}
	public void setCUST_USERID(String cUSTUSERID)
	{
		CUST_USERID = cUSTUSERID;
	}
	public String getREMARK()
	{
		return REMARK;
	}
	public void setREMARK(String rEMARK)
	{
		REMARK = rEMARK;
	}
	public String getACCOUNT_NAME()
	{
		return ACCOUNT_NAME;
	}
	public void setACCOUNT_NAME(String aCCOUNTNAME)
	{
		ACCOUNT_NAME = aCCOUNTNAME;
	}
	public String getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(String amount) {
		AMOUNT = amount;
	}
	
	public String getSN() {
		return SN;
	}
	public void setSN(String sn) {
		SN = sn;
	}
	public String getACCOUNT() {
		return ACCOUNT;
	}
	public void setACCOUNT(String account) {
		ACCOUNT = account;
	}
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String ret_code) {
		RET_CODE = ret_code;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String err_msg) {
		ERR_MSG = err_msg;
	}
}
