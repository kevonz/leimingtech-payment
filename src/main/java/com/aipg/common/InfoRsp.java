package com.aipg.common;

public class InfoRsp
{
	public static InfoRsp packRsp(InfoReq inforeq, String errcode,String strMsg)
	{
		InfoRsp info = new InfoRsp();
		if (inforeq != null)
		{
			if(errcode==null) errcode="1000";
			info.setDATA_TYPE(inforeq.getDATA_TYPE());
			info.setERR_MSG(strMsg);
			info.setREPTIME(inforeq.getREQTIME());
			info.setREQ_SN(inforeq.getREQ_SN());
			info.setRET_CODE(errcode);
			info.setTRX_CODE(inforeq.getTRX_CODE());
			info.setVERSION(inforeq.getVERSION());
		}
		else
		{
			if(errcode==null) errcode="1001";
			info.setDATA_TYPE("");
			info.setERR_MSG(strMsg);
			info.setREPTIME("");
			info.setREQ_SN("");
			info.setRET_CODE("1001");
			info.setTRX_CODE("");
			info.setVERSION("");
		}
		return info;
	}
	public String getTRX_CODE()
	{
		return TRX_CODE;
	}
	public void setTRX_CODE(String tRXCODE)
	{
		TRX_CODE = tRXCODE;
	}
	public String getVERSION()
	{
		return VERSION;
	}
	public void setVERSION(String vERSION)
	{
		VERSION = vERSION;
	}
	public String getDATA_TYPE()
	{
		return DATA_TYPE;
	}
	public void setDATA_TYPE(String dATATYPE)
	{
		DATA_TYPE = dATATYPE;
	}
	public String getREQ_SN()
	{
		return REQ_SN;
	}
	public void setREQ_SN(String rEQSN)
	{
		REQ_SN = rEQSN;
	}
	public String getRET_CODE()
	{
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE)
	{
		RET_CODE = rETCODE;
	}
	public String getERR_MSG()
	{
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG)
	{
		ERR_MSG = eRRMSG;
	}
	public String getREPTIME() {
		return REPTIME;
	}
	public void setREPTIME(String rEPTIME) {
		REPTIME = rEPTIME;
	}
	public String getSIGNED_MSG()
	{
		return SIGNED_MSG;
	}
	public void setSIGNED_MSG(String sIGNEDMSG)
	{
		SIGNED_MSG = sIGNEDMSG;
	}
	
	private String TRX_CODE="";
	private String VERSION="";
	private String DATA_TYPE="";
	private String REQ_SN="";
	private String RET_CODE="";	
	private String ERR_MSG="";	
	private String REPTIME="";	
	private String SIGNED_MSG="";
}
