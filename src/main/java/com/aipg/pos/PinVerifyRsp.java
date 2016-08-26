package com.aipg.pos;

public class PinVerifyRsp
{
	public String getRET_CODE()
	{
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE)
	{
		RET_CODE = rETCODE;
	}
	public String getBALANCE()
	{
		return BALANCE;
	}
	public void setBALANCE(String bALANCE)
	{
		BALANCE = bALANCE;
	}
	public String getERR_MSG()
	{
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG)
	{
		ERR_MSG = eRRMSG;
	}
	public String getF46()
	{
		return F46;
	}
	public void setF46(String f46)
	{
		F46 = f46;
	}
	public String getREMARK()
	{
		return REMARK;
	}
	public void setREMARK(String rEMARK)
	{
		REMARK = rEMARK;
	}
	public String getRESERVED()
	{
		return RESERVED;
	}
	public void setRESERVED(String rESERVED)
	{
		RESERVED = rESERVED;
	}
	private String RET_CODE;
	private String BALANCE ;
	private String ERR_MSG ;
	private String F46     ;
	private String REMARK  ;
	private String RESERVED;
}
