package com.aipg.qvd;

import java.util.ArrayList;
import java.util.List;
/**
 * 验证明细查询响应报文
 * @author DON
 *
 */
public class QVDRsp {
	private String PGTAG;	//下次查询的页标识
	private List details = new ArrayList();
	public String getPGTAG() {
		return PGTAG;
	}
	public void setPGTAG(String pGTAG) {
		PGTAG = pGTAG;
	}
	
	
	public List getDetails() {
		return details;
	}
	public void setDetails(List details) {
		this.details = details;
	}
	
	public void addDTL(QVDRspDetail dtl){
		if(details == null) details = new ArrayList();
		details.add(dtl);
	}
}
