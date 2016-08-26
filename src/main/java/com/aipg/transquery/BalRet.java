package com.aipg.transquery;

public class BalRet
{
	private String RET_CODE="";
	private String BALANCE ="";
	private String ERR_MSG ="";
	
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
}
