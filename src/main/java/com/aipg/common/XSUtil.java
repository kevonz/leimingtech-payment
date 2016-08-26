package com.aipg.common;

import com.aipg.acctvalid.ValbSum;
import com.aipg.acctvalid.ValidBD;
import com.aipg.acctvalid.ValidBReq;
import com.aipg.acctvalid.VbDetail;
import com.aipg.acquery.AcNode;
import com.aipg.acquery.AcQueryRep;
import com.aipg.acquery.AcQueryReq;
import com.aipg.agrmqx.XQSDetail;
import com.aipg.agrmqx.XQSignReq;
import com.aipg.agrmqx.XQSignRsp;
import com.aipg.agrmsync.SignInfoDetail;
import com.aipg.agrmsync.SignInfoSync;
import com.aipg.ahquery.AHQueryRep;
import com.aipg.ahquery.AHQueryReq;
import com.aipg.ahquery.BalNode;
import com.aipg.cash.CashRep;
import com.aipg.cash.CashReq;
import com.aipg.downloadrsp.DownRsp;
import com.aipg.etdtlquery.EtDtl;
import com.aipg.etdtlquery.EtQReq;
import com.aipg.etdtlquery.EtQRsp;
import com.aipg.etdtlquery.EtSum;
import com.aipg.etquery.EtNode;
import com.aipg.etquery.EtQueryRep;
import com.aipg.etquery.EtQueryReq;
import com.aipg.idverify.IdVer;
import com.aipg.idverify.VerQry;
import com.aipg.loginrsp.LoginRsp;
import com.aipg.netbank.NetBankReq;
import com.aipg.notify.Notify;
import com.aipg.payresp.Body;
import com.aipg.payresp.Ret_Detail;
import com.aipg.pos.PinVerifyReq;
import com.aipg.pos.PinVerifyRsp;
import com.aipg.pos.QPTrans;
import com.aipg.pos.QPTransRet;
import com.aipg.pos.QPTrf;
import com.aipg.pos.QPTrfret;
import com.aipg.pos.Trfer;
import com.aipg.pos.Trfret;
import com.aipg.qtd.QTDReq;
import com.aipg.qtd.QTDRsp;
import com.aipg.qtd.QTDRspDetail;
import com.aipg.qvd.QVDReq;
import com.aipg.qvd.QVDRsp;
import com.aipg.qvd.QVDRspDetail;
import com.aipg.refund.Refund;
import com.aipg.rev.TransRev;
import com.aipg.rev.TransRevRsp;
import com.aipg.rtreq.Trans;
import com.aipg.rtrsp.TransRet;
import com.aipg.signquery.NSignReq;
import com.aipg.signquery.QSignDetail;
import com.aipg.signquery.QSignReq;
import com.aipg.signquery.QSignRsp;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.singleacctvalid.ValidRet;
import com.aipg.syncex.SyncReqEx;
import com.aipg.syncex.SyncReqExDetail;
import com.aipg.syncex.SyncRspEx;
import com.aipg.syncex.SyncRspExDetail;
import com.aipg.synreq.SCloseReq;
import com.aipg.synreq.SCloseRsp;
import com.aipg.synreq.SvrfReq;
import com.aipg.synreq.SvrfRsp;
import com.aipg.synreq.Sync;
import com.aipg.synreq.SyncDetail;
import com.aipg.transfer.TransferReq;
import com.aipg.transfer.TransferRsp;
import com.aipg.transquery.BalReq;
import com.aipg.transquery.BalRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.aipg.tunotify.TUNotifyRep;
import com.aipg.tunotify.TUNotifyReq;
import com.thoughtworks.xstream.XStream;

public class XSUtil
{
	public static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	private static final XStream xsreq=initXStream(new XStreamEx(), true);
	private static final XStream xsrsp=initXStream(new XStreamEx(), false);
	public static AipgReq makeNotify(String qsn)
	{
		AipgReq req=new AipgReq();
		Notify notify=new Notify();
		notify.setNOTIFY_SN(qsn);
		req.setINFO(XSUtil.makeReq("200003",""+System.currentTimeMillis()));
		req.addTrx(notify);
		return req;
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
	public static String toXml(XStream xs,Object o)
	{
		String xml;
		xml=xs.toXML(o);
		xml = xml.replaceAll("__", "_");
		xml = HEAD + xml;
		return xml;
	}		
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String acctname,String status,String errmsg)
	{
		return makeNSignReq(agrno,contractno,acct,acctname,status,"1",errmsg);
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String acctname,String status,String signtype,String errmsg)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setACCTNAME(acctname);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		qsd.setSIGNTYPE(signtype);
		qsd.setERRMSG(errmsg);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtil.makeReq("210003",""+System.currentTimeMillis()));
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq makeNSignReqEx(String merno,String agrno,String contractno,String acct,String acctname,String status,String signtype,String errmsg)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setACCTNAME(acctname);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		qsd.setSIGNTYPE(signtype);
		qsd.setERRMSG(errmsg);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtil.makeReq("210003",""+System.currentTimeMillis()));
		req.getINFO().setMERCHANT_ID(merno);
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq xmlReq(String xmlMsg)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, true);
		AipgReq req=(AipgReq) xs.fromXML(xmlMsg);
		return req;
	}
	public static AipgRsp xmlRsp(String xmlMsg)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, false);
		AipgRsp rsp=(AipgRsp) xs.fromXML(xmlMsg);
		return rsp;
	}
	public static String reqXml(AipgReq req)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, true);
		String xml=HEAD+xs.toXML(req);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static String rspXml(AipgRsp rsp)
	{
		XStream xs=new XStreamEx();
		XSUtil.initXStream(xs, false);
		String xml=HEAD+xs.toXML(rsp);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static XStream initXStream(XStream xs,boolean isreq)
	{
		if(isreq) xs.alias("AIPG", AipgReq.class); else xs.alias("AIPG", AipgRsp.class);
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QTRANSRSP", QTransRsp.class);
		xs.alias("QTDETAIL", QTDetail.class);
		xs.alias("DOWNRSP", DownRsp.class);
		xs.alias("NOTIFY", Notify.class);
		xs.alias("SYNC", Sync.class);
		xs.alias("QSIGNRSP", QSignRsp.class);
		xs.alias("QSDETAIL", QSignDetail.class);
		xs.alias("NSIGNREQ", NSignReq.class);
		xs.alias("QSIGNREQ", QSignReq.class);
		xs.alias("TRANS", Trans.class);
		xs.alias("TRANSRET", TransRet.class);
		//xs.alias("TRXPAY", Trans.class);
		//xs.alias("TRXPAYRET", TransRet.class);
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SVRFRSP", SvrfRsp.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);

		xs.alias("VALIDR", ValidR.class);
		xs.alias("VALIDRET", ValidRet.class);
		
		xs.alias("TRANSFERREQ", TransferReq.class);
		xs.alias("TRANSFERRSP", TransferRsp.class);
		
		xs.alias("QVDREQ", QVDReq.class);
		xs.alias("QVDRSP", QVDRsp.class);
		xs.alias("QVDRSPDETAIL", QVDRspDetail.class);
		xs.addImplicitCollection(QVDRsp.class, "details");
		
		xs.alias("VALIDBREQ", ValidBReq.class);
		xs.alias("VALBSUM", ValbSum.class);
		xs.alias("VALIDBD", ValidBD.class);
		xs.alias("VBDETAIL", VbDetail.class);
		xs.addImplicitCollection(ValidBD.class, "details");
		
		
		xs.alias("TRFER", Trfer.class);
		xs.alias("TRFRET", Trfret.class);
		xs.alias("QPTRF", QPTrf.class);
		xs.alias("QPTRFRET", QPTrfret.class);
		xs.alias("QPTRANS", QPTrans.class);
		xs.alias("QPTRANSRET", QPTransRet.class);
		
		xs.addImplicitCollection(Sync.class, "details");
		xs.addImplicitCollection(QTransRsp.class, "details");
		xs.addImplicitCollection(QSignRsp.class, "details");
		xs.alias("SYNCDETAIL", SyncDetail.class);
		
		xs.alias("SYNCREQEX", SyncReqEx.class);
		xs.alias("SYNCRSPEX", SyncRspEx.class);
		xs.alias("SYNCREQEXDETAIL", SyncReqExDetail.class);
		xs.alias("SYNCRSPEXDETAIL", SyncRspExDetail.class);
		xs.addImplicitCollection(SyncReqEx.class, "details");
		xs.addImplicitCollection(SyncRspEx.class, "details");
		xs.alias("QTDREQ", QTDReq.class);
		xs.alias("QTDRSP", QTDRsp.class);
		xs.alias("QTDRSPDETAIL", QTDRspDetail.class);
		xs.addImplicitCollection(QTDRsp.class, "details");	
		xs.alias("ACQUERYREQ", AcQueryReq.class);
		xs.alias("ACQUERYREP", AcQueryRep.class);
		xs.alias("ACNODE", AcNode.class);
		xs.addImplicitCollection(AcQueryRep.class, "details");
		xs.alias("AHQUERYREQ", AHQueryReq.class);
		xs.alias("AHQUERYREP", AHQueryRep.class);
		xs.alias("BALNODE", BalNode.class);
		xs.addImplicitCollection(AHQueryRep.class, "details");
		xs.alias("TUQNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		
		xs.alias("BODY", Body.class);
		xs.alias("BODY", Body.class);
		xs.aliasField("RET_DETAILS", Body.class, "details");
		xs.alias("RET_DETAIL", Ret_Detail.class);
		xs.alias("NETBANKREQ", NetBankReq.class);
		
		xs.alias("ETQREQ", EtQReq.class);
		xs.alias("ETQRSP", EtQRsp.class);
		xs.alias("ETSUM", EtSum.class);
		xs.alias("ETDTL", EtDtl.class);
		xs.aliasField("ETDTLS", EtQRsp.class, "details");
		
		
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("SIGNINFODETAIL",SignInfoDetail.class);
		
		xs.alias("XQSIGNREQ", XQSignReq.class);
		xs.alias("XQSIGNRSP", XQSignRsp.class);
		xs.addImplicitCollection(XQSignRsp.class, "details");
		xs.alias("XQSDETAIL", XQSDetail.class);
		
		xs.alias("REFUND", Refund.class);
		
		xs.alias("IDVER", IdVer.class);
		xs.alias("VERQRY", VerQry.class);
		return xs;
	}
	public static InfoReq makeReq(String trxcod, String sn)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(null);
		ir.setUSER_NAME(null);
		ir.setUSER_PASS(null);
		return ir;
	}
	public static InfoReq makeReq(String trxcod, String sn,String user,String pass,int level)
	{
		InfoReq ir=new InfoReq();
		ir.setTRX_CODE(trxcod);
		ir.setDATA_TYPE("2");
		ir.setVERSION("03");
		ir.setSIGNED_MSG("");
		ir.setREQ_SN(sn);
		ir.setLEVEL(""+level);
		ir.setUSER_NAME(user);
		ir.setUSER_PASS(pass);
		return ir;
	}
}
