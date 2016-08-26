package com.aipg.transquery;

public class TransQueryReq
{
	private String QUERY_SN;
	private String MERCHANT_ID;
	private int    STATUS;
	private int    TYPE;
	private String START_DAY;
	private String END_DAY;
	private String SETTACCT;
	private String CONTFEE ;
	public String getCONTFEE() {
		return CONTFEE;
	}
	public void setCONTFEE(String contfee) {
		CONTFEE = contfee;
	}
	public String getSETTACCT()
	{
		return SETTACCT;
	}
	public void setSETTACCT(String sETTACCT)
	{
		SETTACCT = sETTACCT;
	}
	public String getQUERY_SN()
	{
		return QUERY_SN;
	}
	public void setQUERY_SN(String qUERYSN)
	{
		QUERY_SN = qUERYSN;
	}
	public int getTYPE()
	{
		return TYPE;
	}
	public void setTYPE(int tYPE)
	{
		TYPE = tYPE;
	}
	public int getSTATUS()
	{
		return STATUS;
	}
	public void setSTATUS(int sTATUS)
	{
		STATUS = sTATUS;
	}
	public String getMERCHANT_ID()
	{
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID)
	{
		MERCHANT_ID = mERCHANTID;
	}
	public String getSTART_DAY()
	{
		return START_DAY;
	}
	public void setSTART_DAY(String sTARTDAY)
	{
		START_DAY = sTARTDAY;
	}
	public String getEND_DAY()
	{
		return END_DAY;
	}
	public void setEND_DAY(String eNDDAY)
	{
		END_DAY = eNDDAY;
	}
}
