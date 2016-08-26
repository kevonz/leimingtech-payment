package com.aipg.pay;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes","unchecked"})
public class PGPayRsp
{
	private List details = new ArrayList();
	public List getDetails() 
	{
		return details;
	}
	public void setDetails(List details) 
	{
		this.details = details;
	}
	
	public void addDetail(PGPayRspRecord detail)
	{
		details.add(detail);
	}
}
