package com.aipg.signquery;

public class QSignReq
{
	private String MERCHANT_ID;
	private String ACCT       ;
	private String START_TIME ;
	private String END_TIME   ;
	private String AGREEMENTNO;	
	public String getAGREEMENTNO()
	{
		return AGREEMENTNO;
	}
	public void setAGREEMENTNO(String aGREEMENTNO)
	{
		AGREEMENTNO = aGREEMENTNO;
	}
	public String getMERCHANT_ID()
	{
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID)
	{
		MERCHANT_ID = mERCHANTID;
	}
	public String getACCT()
	{
		return ACCT;
	}
	public void setACCT(String aCCT)
	{
		ACCT = aCCT;
	}
	public String getSTART_TIME()
	{
		return START_TIME;
	}
	public void setSTART_TIME(String sTARTTIME)
	{
		START_TIME = sTARTTIME;
	}
	public String getEND_TIME()
	{
		return END_TIME;
	}
	public void setEND_TIME(String eNDTIME)
	{
		END_TIME = eNDTIME;
	}
}
