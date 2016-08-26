/**
 * 
 */
package com.aipg.synreq;

/**
 * @作者：胡东强
 * @功能说明：
 * @创建时间: 2012-2-16下午04:51:32
 * @版本：V1.0
 * 
 */
public class SCloseReq {
	private String MERCHANT_ID;
	private String ACCT;
	private String MEMID;
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
