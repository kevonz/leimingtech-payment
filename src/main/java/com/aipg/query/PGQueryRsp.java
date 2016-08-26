package com.aipg.query;

import java.util.ArrayList;
import java.util.List;

public class PGQueryRsp
{
	private PGQueryTrx QUERY_TRANS;
	private List details = new ArrayList();
	public PGQueryTrx getQUERY_TRANS()
	{
		return QUERY_TRANS;
	}
	public void setQUERY_TRANS(PGQueryTrx qUERY_TRANS)
	{
		QUERY_TRANS = qUERY_TRANS;
	}
	public List getDetails()
	{
		return details;
	}
	public void setDetails(List details)
	{
		this.details = details;
	}
	public void addDetail(PGQueryRecord detail) {
		details.add(detail);
	}
}
