package com.aipg.synreq;

public class SvrfReq
{
	private String MERCHANT_ID;
	private String ACCT       ;
	private int    AMT        ;
	private String MEMID      ;
	private String AGREEMENTNO;
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
	public int getAMT()
	{
		return AMT;
	}
	public void setAMT(int aMT)
	{
		AMT = aMT;
	}
	public String getMEMID()
	{
		return MEMID;
	}
	public void setMEMID(String mEMID)
	{
		MEMID = mEMID;
	}
	public String getAGREEMENTNO()
	{
		return AGREEMENTNO;
	}
	public void setAGREEMENTNO(String aGREEMENTNO)
	{
		AGREEMENTNO = aGREEMENTNO;
	}
}
