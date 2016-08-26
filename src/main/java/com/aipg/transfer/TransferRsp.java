package com.aipg.transfer;

/**
 * 实时转账、快速转账、标准转账 响应
 * 
 * @author luolc
 * @version 1.0
 */
public class TransferRsp {

	private String RET_CODE;// 返回码
	private String ERR_MSG;// 错误文本
	private String FEE;// 预留

	public String getRET_CODE() {
		return RET_CODE;
	}

	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}

	public String getERR_MSG() {
		return ERR_MSG;
	}

	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}

	public String getFEE() {
		return FEE;
	}

	public void setFEE(String fEE) {
		FEE = fEE;
	}

}
