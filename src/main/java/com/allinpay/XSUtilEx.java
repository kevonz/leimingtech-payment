package com.allinpay;

import com.aipg.acctvalid.ValbSum;
import com.aipg.acctvalid.ValidBReq;
import com.aipg.acctvalid.VbDetail;
import com.aipg.acquery.AcNode;
import com.aipg.acquery.AcQueryRep;
import com.aipg.acquery.AcQueryReq;
import com.aipg.agrmqx.XQSignReq;
import com.aipg.agrmsync.SignInfoDetail;
import com.aipg.agrmsync.SignInfoSync;
import com.aipg.ahquery.AHQueryRep;
import com.aipg.ahquery.AHQueryReq;
import com.aipg.ahquery.BalNode;
import com.aipg.cardbin.QCardBinReq;
import com.aipg.cardbin.QCardBinRsp;
import com.aipg.cash.CashRep;
import com.aipg.cash.CashReq;
import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.downloadrsp.DownRsp;
import com.aipg.etdtlquery.EtQReq;
import com.aipg.etquery.EtNode;
import com.aipg.etquery.EtQueryRep;
import com.aipg.etquery.EtQueryReq;
import com.aipg.idverify.IdVer;
import com.aipg.idverify.VerQry;
import com.aipg.loginrsp.LoginRsp;
import com.aipg.notify.Notify;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
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
import com.aipg.rev.TransRev;
import com.aipg.rev.TransRevRsp;
import com.aipg.rtreq.Trans;
import com.aipg.rtrsp.TransRet;
import com.aipg.signquery.NSignReq;
import com.aipg.signquery.QSignDetail;
import com.aipg.signquery.QSignReq;
import com.aipg.signquery.QSignRsp;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.syncex.SyncReqEx;
import com.aipg.syncex.SyncReqExDetail;
import com.aipg.syncex.SyncRspEx;
import com.aipg.syncex.SyncRspExDetail;
import com.aipg.synreq.SCloseReq;
import com.aipg.synreq.SCloseRsp;
import com.aipg.synreq.SvrfReq;
import com.aipg.synreq.Sync;
import com.aipg.synreq.SyncDetail;
import com.aipg.transfer.TransferReq;
import com.aipg.transquery.BalReq;
import com.aipg.transquery.BalRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.aipg.tunotify.TUNotifyRep;
import com.aipg.tunotify.TUNotifyReq;
import com.thoughtworks.xstream.XStream;

public class XSUtilEx
{
	private static final String HEAD = "<?xml version=\"1.0\" encoding=\"GBK\"?>";
	public static AipgReq makeNotify(String qsn)
	{
		AipgReq req=new AipgReq();
		Notify notify=new Notify();
		notify.setNOTIFY_SN(qsn);
		req.setINFO(XSUtilEx.makeReq("200003",""+System.currentTimeMillis()));
		req.addTrx(notify);
		return req;
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status)
	{
		return makeNSignReq(agrno,contractno,acct,status,"1");
	}
	public static AipgReq makeNSignReq(String agrno,String contractno,String acct,String status,String signtype)
	{
		AipgReq req=new AipgReq();
		QSignDetail qsd=new QSignDetail();
		qsd.setAGREEMENTNO(agrno);
		qsd.setACCT(acct);
		qsd.setCONTRACTNO(contractno);
		qsd.setSTATUS(status);
		//qsd.setSIGNTYPE(signtype);
		NSignReq nsr=new NSignReq();
		nsr.addDtl(qsd);
		req.setINFO(XSUtilEx.makeReq("210003",""+System.currentTimeMillis()));
		req.addTrx(nsr);
		return req;		
	}
	public static AipgReq xmlReq(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		AipgReq req=(AipgReq) xs.fromXML(xmlMsg);
		return req;
	}
	public static AipgRsp xmlRsp(String xmlMsg)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		AipgRsp rsp=(AipgRsp) xs.fromXML(xmlMsg);
		return rsp;
	}
	public static String reqXml(AipgReq req)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, true);
		String xml=HEAD+xs.toXML(req);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static String rspXml(AipgRsp rsp)
	{
		XStream xs=new XStreamIg();
		XSUtilEx.initXStream(xs, false);
		String xml=HEAD+xs.toXML(rsp);
		xml=xml.replace("__", "_");
		return xml;
	}
	public static void initXStream(XStream xs,boolean isreq)
	{
		if(isreq) 
			xs.alias("AIPG", AipgReq.class); 
		else 
			xs.alias("AIPG", AipgRsp.class);
		xs.alias("INFO", InfoReq.class);
		xs.addImplicitCollection(AipgReq.class, "trxData");
		xs.addImplicitCollection(AipgRsp.class, "trxData");
		xs.alias("BODY", Body.class) ;
		xs.alias("TRANS_DETAIL", Trans_Detail.class);
		xs.aliasField("TRANS_DETAILS", Body.class, "details");
		
		xs.alias("VALIDBREQ", ValidBReq.class) ;
		xs.alias("VALBSUM", ValbSum.class) ;
		xs.alias("VBDETAIL", VbDetail.class);
		xs.aliasField("VALIDBD", ValidBReq.class, "VALIDBD");
		xs.alias("VALIDR", ValidR.class) ;
		
		xs.alias("QTRANSREQ", TransQueryReq.class);
		xs.alias("QVDREQ", QVDReq.class);
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
		xs.alias("REVREQ", TransRev.class);
		xs.alias("REVRSP", TransRevRsp.class);
		xs.alias("LOGINRSP", LoginRsp.class);
		xs.alias("BALREQ", BalReq.class);
		xs.alias("BALRET", BalRet.class);
		xs.alias("SVRFREQ", SvrfReq.class);
		xs.alias("SCLOSEREQ", SvrfReq.class);
		xs.alias("SCLOSERSP", SCloseRsp.class);
		xs.alias("PINSIGNREQ", PinVerifyReq.class);
		xs.alias("PINSIGNRSP", PinVerifyRsp.class);

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
		xs.alias("TUNOTIFYREQ", TUNotifyReq.class);
		xs.alias("TUNOTIFYREP", TUNotifyRep.class);
		xs.alias("CASHREQ", CashReq.class);
		xs.alias("CASHREP", CashRep.class);
//		xs.alias("TUQNOTIFYREQ", NoticeReq.class);
//		xs.alias("TUNOTIFYREP", NoticeRep.class);
		xs.alias("ETQUERYREQ", EtQueryReq.class);
		xs.alias("ETQUERYREP", EtQueryRep.class);
		xs.alias("ETNODE", EtNode.class);
		xs.alias("ETQREQ", EtQReq.class);
		xs.addImplicitCollection(EtQueryRep.class, "details");
		xs.alias("SIGNINFODETAIL", SignInfoDetail.class);
		xs.alias("SIGNINFOSYNC", SignInfoSync.class);
		xs.alias("SCLOSEREQ", SCloseReq.class);
		xs.addImplicitCollection(SignInfoSync.class, "details");
		xs.alias("XQSIGNREQ", XQSignReq.class);

		/**
		 * 转账
		 */
		xs.alias("TRANSFERREQ",TransferReq.class);
		
		xs.alias("IDVER", IdVer.class);
		xs.alias("VERQRY", VerQry.class);
		
		xs.alias("QCARDBINREQ", QCardBinReq.class);
		xs.alias("QCARDBINRSP", QCardBinRsp.class);
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
	
	public static Object parseXml(String xml)
	{
		XStream xs=new XStreamIg();
		XSUtil.initXStream(xs,true);
		return xs.fromXML(xml);
	}
}
