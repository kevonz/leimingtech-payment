package com.aipg.singleacctvalid;
/**
 * 
 * @功能: 2.5.	单笔实时验证响应
 * @作者： 胡东强
 * @创建时间：2012-6-11下午04:22:45
 * @版本:v1.0
 */
public class ValidRet {
	private String RET_CODE;	//返回码	
	private String ERR_MSG;	    //错误文本	
	
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String rETCODE) {
		RET_CODE = rETCODE;
	}
	public String getERR_MSG() {
		return ERR_MSG;
	}
	public void setERR_MSG(String eRRMSG) {
		ERR_MSG = eRRMSG;
	}

}
