package com.aipg.query;

import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.XSUtil;
import com.aipg.common.XStreamEx;
import com.thoughtworks.xstream.XStream;

public class PGQueryUtil
{
	public static XStream buildXS(boolean isreq)
	{
		XStream xs=new XStreamEx();
		if(isreq)
		{
			xs.alias("AIPG",AipgReq.class);
			xs.aliasField("BODY",AipgReq.class,"trxData");
			xs.alias("QUERY_TRANS", PGQueryTrx.class);
		}
		else
		{
			xs.alias("AIPG", AipgRsp.class);
			xs.addImplicitCollection(AipgRsp.class,"trxData");
			xs.alias("BODY", PGQueryRsp.class);
	 		xs.aliasField("RET_DETAILS",PGQueryRsp.class, "details");
			xs.alias("RET_DETAIL",PGQueryRecord.class);
		}
		return xs;
	}
	public static AipgReq parseReq(String xml)
	{
		return (AipgReq) xsreq.fromXML(xml);
	}
	public static AipgRsp parseRsp(String xml)
	{
		return (AipgRsp) xsrsp.fromXML(xml);
	}
	public static String toXml(Object o)
	{
		boolean isreq=(o instanceof AipgReq);
		if(isreq) return XSUtil.toXml(xsreq, o);
		else return XSUtil.toXml(xsrsp, o);
	}	
	private static XStream xsreq=buildXS(true);
	private static XStream xsrsp=buildXS(false);
}
