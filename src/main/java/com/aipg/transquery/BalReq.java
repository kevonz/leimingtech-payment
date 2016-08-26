package com.aipg.transquery;

public class BalReq
{
	private String MERCHANT_ID;
	private String SUBMIT_TIME;
	private String TERM_CODE  ;
	private String TRACK2     ;
	private String TRACK3     ;
	private String PINCODE    ;
	private String PINFMT     ;
	private String ACCOUNT_NO ;
	private String TLTMERID;
	private String INSTID;
	
	public String getTLTMERID()
	{
		return TLTMERID;
	}
	public void setTLTMERID(String tLTMERID)
	{
		TLTMERID = tLTMERID;
	}
	public String getINSTID()
	{
		return INSTID;
	}
	public void setINSTID(String iNSTID)
	{
		INSTID = iNSTID;
	}
	public String getSUBMIT_TIME()
	{
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String sUBMITTIME)
	{
		SUBMIT_TIME = sUBMITTIME;
	}
	public String getMERCHANT_ID()
	{
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID)
	{
		MERCHANT_ID = mERCHANTID;
	}
	public String getTERM_CODE()
	{
		return TERM_CODE;
	}
	public void setTERM_CODE(String tERMCODE)
	{
		TERM_CODE = tERMCODE;
	}
	public String getTRACK2()
	{
		return TRACK2;
	}
	public void setTRACK2(String tRACK2)
	{
		TRACK2 = tRACK2;
	}
	public String getTRACK3()
	{
		return TRACK3;
	}
	public void setTRACK3(String tRACK3)
	{
		TRACK3 = tRACK3;
	}
	public String getPINCODE()
	{
		return PINCODE;
	}
	public void setPINCODE(String pINCODE)
	{
		PINCODE = pINCODE;
	}
	public String getPINFMT()
	{
		return PINFMT;
	}
	public void setPINFMT(String pINFMT)
	{
		PINFMT = pINFMT;
	}
	public String getACCOUNT_NO()
	{
		return ACCOUNT_NO;
	}
	public void setACCOUNT_NO(String aCCOUNTNO)
	{
		ACCOUNT_NO = aCCOUNTNO;
	}
}
