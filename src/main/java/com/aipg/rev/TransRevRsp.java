package com.aipg.rev;

public class TransRevRsp
{
	public String getRET_CODE()
	{
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE)
	{
		RET_CODE = rETCODE;
	}
	public String getSETTLE_DAY()
	{
		return SETTLE_DAY;
	}
	public void setSETTLE_DAY(String sETTLEDAY)
	{
		SETTLE_DAY = sETTLEDAY;
	}
	public String getERR_MSG()
	{
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG)
	{
		ERR_MSG = eRRMSG;
	}
	private String RET_CODE  ;
	private String SETTLE_DAY;
	private String ERR_MSG   ;
}
