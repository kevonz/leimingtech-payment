package com.aipg.queryreq;

import com.aipg.common.InfoReq;
import com.aipg.common.XStreamEx;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Test {
	public static void main(String[] args) {

		// String strTemp = "1234,56745,a1,b1";
		// System.out.println(strTemp.substring(0, strTemp.indexOf(",", 0)));
		// ProcessServlet s = new ProcessServlet( );

//		String strSendData = "<?xml version=\"1.0\" encoding=\"GBK\"?><AIPG><INFO><TRX_CODE>100001</TRX_CODE><VERSION>02</VERSION></INFO></AIPG>";

//		XStream xstream = new XStreamEx( );
		XStream xstream = new XStreamEx(new DomDriver());
		xstream.alias("AIPG", AIPG.class);
		xstream.alias("INFO", InfoReq.class);
	
		
		xstream.alias("QUERY_TRANS", Query_Trans.class);
	

		
//		xstream.aliasField("TRX->CODE1", Info.class, "TRX_CODE");
		
		AIPG g = new AIPG( );
		InfoReq info = new InfoReq( );
		info.setTRX_CODE("-----");
		info.setVERSION("03");
		g.setINFO(info);
		
		Body body = new Body( );
//		Trans_Sum transsum = new Trans_Sum( );
		Query_Trans ret_detail=new Query_Trans();
        ret_detail.setQUERY_SN("sdjf;alsd");
        body.setQUERY_TRANS(ret_detail);
		
		g.setBODY(body);
		System.out.println(xstream.toXML(g).replaceAll("__", "_"));
		
		
	}
}
