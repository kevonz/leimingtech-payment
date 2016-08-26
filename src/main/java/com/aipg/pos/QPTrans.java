package com.aipg.pos;

public class QPTrans
{
	public String getMERCHANT_ID()
	{
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID)
	{
		MERCHANT_ID = mERCHANTID;
	}
	public String getSUBMIT_TIME()
	{
		return SUBMIT_TIME;
	}
	public void setSUBMIT_TIME(String sUBMITTIME)
	{
		SUBMIT_TIME = sUBMITTIME;
	}
	public String getTERM_CODE()
	{
		return TERM_CODE;
	}
	public void setTERM_CODE(String tERMCODE)
	{
		TERM_CODE = tERMCODE;
	}
	public String getINSTID()
	{
		return INSTID;
	}
	public void setINSTID(String iNSTID)
	{
		INSTID = iNSTID;
	}
	public String getREMARK()
	{
		return REMARK;
	}
	public void setREMARK(String rEMARK)
	{
		REMARK = rEMARK;
	}
	public String getF46()
	{
		return F46;
	}
	public void setF46(String f46)
	{
		F46 = f46;
	}
	public String getREMARK2()
	{
		return REMARK2;
	}
	public void setREMARK2(String rEMARK2)
	{
		REMARK2 = rEMARK2;
	}
	public String getRESERVE()
	{
		return RESERVE;
	}
	public void setRESERVE(String rESERVE)
	{
		RESERVE = rESERVE;
	}	
	public String getTLTMERID()
	{
		return TLTMERID;
	}
	public void setTLTMERID(String tLTMERID)
	{
		TLTMERID = tLTMERID;
	}

	private String MERCHANT_ID;
	private String TLTMERID;
	private String SUBMIT_TIME;
	private String TERM_CODE  ;
	private String INSTID     ;
	private String REMARK     ;
	private String F46        ;
	private String REMARK2    ;
	private String RESERVE    ;
}
