package com.aipg.queryresp;



import com.aipg.common.InfoReq;
import com.aipg.common.InfoRsp;
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
	
		
		
		xstream.alias("RET_DETAIL", Ret_Detail.class);
 		xstream.aliasField("RET_DETAILS", Body.class, "details");
	

		
//		xstream.aliasField("TRX->CODE1", Info.class, "TRX_CODE");
		
		AIPG g = new AIPG( );
		InfoRsp info = new InfoRsp( );
		info.setTRX_CODE("-----");
		info.setVERSION("03");
		g.setINFO(info);
		
		Body body = new Body( );
//		Trans_Sum transsum = new Trans_Sum( );
		Ret_Detail detail=new Ret_Detail();
		detail.setSN("woshi");
	    body.addDetail(detail);
	    
		
		g.setBODY(body);
		System.out.println(xstream.toXML(g).replaceAll("__", "_"));
		
		
	}
}
