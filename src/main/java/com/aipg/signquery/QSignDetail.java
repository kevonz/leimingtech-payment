package com.aipg.signquery;

public class QSignDetail
{
	private String MEMID;
	private String AGREEMENTNO;
	private String MOBILE    ;
	private String CONTRACTNO;
	private String ACCT      ;
	private String ACCTNAME  ;
	private String STATUS    ;
	private String SIGNTYPE  ;
	private String ERRMSG    ;
	
	public String getACCTNAME()
	{
		return ACCTNAME;
	}
	public void setACCTNAME(String aCCTNAME)
	{
		ACCTNAME = aCCTNAME;
	}
	public String getERRMSG()
	{
		return ERRMSG;
	}
	public void setERRMSG(String eRRMSG)
	{
		ERRMSG = eRRMSG;
	}
	public String getSIGNTYPE()
	{
		return SIGNTYPE;
	}
	public void setSIGNTYPE(String sIGNTYPE)
	{
		SIGNTYPE = sIGNTYPE;
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
	public String getMOBILE()
	{
		return MOBILE;
	}
	public void setMOBILE(String mOBILE)
	{
		MOBILE = mOBILE;
	}
	public String getCONTRACTNO()
	{
		return CONTRACTNO;
	}
	public void setCONTRACTNO(String cONTRACTNO)
	{
		CONTRACTNO = cONTRACTNO;
	}
	public String getACCT()
	{
		return ACCT;
	}
	public void setACCT(String aCCT)
	{
		ACCT = aCCT;
	}
	public String getSTATUS()
	{
		return STATUS;
	}
	public void setSTATUS(String sTATUS)
	{
		STATUS = sTATUS;
	}
}
