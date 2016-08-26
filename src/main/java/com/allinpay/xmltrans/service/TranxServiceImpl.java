/**
 *项目：xmlInterf
 *通联支付网络有限公司
 * 作者：张广海
 * 日期：Jan 2, 2013
 * 功能说明：系统对接xml 测试demo
 */
package com.allinpay.xmltrans.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.commons.io.IOUtils;

import com.aipg.acctvalid.ValbSum;
import com.aipg.acctvalid.ValidBD;
import com.aipg.acctvalid.ValidBReq;
import com.aipg.acctvalid.VbDetail;
import com.aipg.ahquery.AHQueryReq;
import com.aipg.cardbin.QCardBinReq;
import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.idverify.IdVer;
import com.aipg.idverify.VerQry;
import com.aipg.payreq.Body;
import com.aipg.payreq.Trans_Detail;
import com.aipg.payreq.Trans_Sum;
import com.aipg.rtreq.Trans;
import com.aipg.rtrsp.TransRet;
import com.aipg.signquery.NSignReq;
import com.aipg.signquery.QSignDetail;
import com.aipg.singleacctvalid.ValidR;
import com.aipg.singleacctvalid.ValidRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.aipg.tranx.conf.XmlTransContents;
import com.allinpay.XmlTools;
import com.allinpay.xmltrans.pojo.TranxCon;
import com.allinpay.xmltrans.tools.FileUtil;

/**
 */
public class TranxServiceImpl {
	TranxCon tranxContants = new TranxCon();
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * 日期：Sep 4, 2012 功能：
	 *  批量代付
	 * @param trx_code
	 * @throws Exception
	 */
	public void batchTranx(String url, String trx_code, String busicode, boolean isTLTFront) throws Exception {

		String xml = "";
		AipgReq aipg = new AipgReq();
		InfoReq info = makeReq(trx_code);
		aipg.setINFO(info);

		Body body = new Body();
		Trans_Sum trans_sum = new Trans_Sum();
		trans_sum.setBUSINESS_CODE(busicode);
		trans_sum.setMERCHANT_ID(tranxContants.merchantId);

		trans_sum.setTOTAL_ITEM("3");
		trans_sum.setTOTAL_SUM("100002");
		body.setTRANS_SUM(trans_sum);
		List<Trans_Detail> transList = new ArrayList<Trans_Detail>();
		Trans_Detail trans_detail = new Trans_Detail();
		Trans_Detail trans_detail2 = new Trans_Detail();
		Trans_Detail trans_detail3 = new Trans_Detail();
		Trans_Detail trans_detail4 = new Trans_Detail();
		trans_detail.setSN("0001");
		trans_detail.setACCOUNT_NAME("许燕");
		trans_detail.setACCOUNT_PROP("0");
		trans_detail.setACCOUNT_NO("603023061216191772");
		trans_detail.setBANK_CODE("103");
		trans_detail.setAMOUNT("100000");
		trans_detail.setCURRENCY("NBK");
		// trans_detail.setSETTGROUPFLAG("xCHM");
		// trans_detail.setSUMMARY("分组清算");
		// trans_detail.setUNION_BANK("234234523523");
		transList.add(trans_detail);

		trans_detail2.setSN("0002");
		trans_detail2.setACCOUNT_NAME("系统对接测试02");
		// trans_detail.setACCOUNT_PROP("1") ;
		trans_detail2.setACCOUNT_NO("622682-0013800763464");
		trans_detail2.setBANK_CODE("103");
		trans_detail2.setAMOUNT("1");
		trans_detail2.setCURRENCY("CNY");
		trans_detail2.setUNION_BANK("234234523523");
		// trans_detail2.setSETTGROUPFLAG("CHM");
		// trans_detail2.setSUMMARY("分组清算");
		transList.add(trans_detail2);

		trans_detail3.setSN("0003");
		trans_detail3.setACCOUNT_NAME("系统对接测试03");
		// trans_detail.setACCOUNT_PROP("1") ;
		trans_detail3.setACCOUNT_NO("621034-32645-1271");
		trans_detail3.setBANK_CODE("103");
		trans_detail3.setAMOUNT("1");
		trans_detail3.setUNION_BANK("234234523523");
		// trans_detail3.setSETTGROUPFLAG("CHM");
		// trans_detail3.setSUMMARY("分组清算");
		transList.add(trans_detail3);
		body.setDetails(transList);
		aipg.addTrx(body);

		xml = XmlTools.buildXml(aipg, true);
		dealRet(sendToTlt(xml, isTLTFront, url));
	}

	/**
	 * 组装报文头部
	 * 
	 * @param trxcod
	 * @return 日期：Sep 9, 2012
	 */
	private InfoReq makeReq(String trxcod) {

		InfoReq info = new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(tranxContants.merchantId + "-" + String.valueOf(System.currentTimeMillis()));
		info.setUSER_NAME(tranxContants.userName);
		info.setUSER_PASS(tranxContants.password);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("03");
		if ("300000".equals(trxcod) || "300001".equals(trxcod) || "300003".equals(trxcod) || "220001".equals(trxcod) || "220003".equals(trxcod)) {
			info.setMERCHANT_ID(tranxContants.merchantId);
		}
//		System.out.println(info);
		return info;
	}

	public String sendXml(String xml, String url, boolean isFront) throws UnsupportedEncodingException, Exception {
		System.out.println("======================发送报文======================：\n" + xml);
		String resp = XmlTools.send(url, xml);
		System.out.println("======================响应内容======================");
		boolean flag = this.verifyMsg(resp, tranxContants.tltcerPath, isFront);
		if (flag) {
			System.out.println("响应内容验证通过");
		} else {
			System.out.println("响应内容验证不通过");
		}
		return resp;
	}

	public String sendToTlt(String xml, boolean flag, String url) {
		try {
			if (!flag) {
				xml = this.signMsg(xml);
			} else {
				xml = xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml, url, flag);
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() instanceof ConnectException || e instanceof ConnectException) {
				System.out.println("请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易");
			}
		}
		return "请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易";
	}

	/**
	 * 报文签名
	 * 
	 * @param msg
	 * @return 日期：Sep 9, 2012
	 * @throws Exception
	 */
	public String signMsg(String xml) throws Exception {
		xml = XmlTools.signMsg(xml, tranxContants.pfxPath, tranxContants.pfxPassword, false);
		return xml;
	}

	/**
	 * 验证签名
	 * 
	 * @param msg
	 * @return 日期：Sep 9, 2012
	 * @throws Exception
	 */
	public boolean verifyMsg(String msg, String cer, boolean isFront) throws Exception {
		boolean flag = XmlTools.verifySign(msg, cer, false, isFront);
		System.out.println("验签结果[" + flag + "]");
		return flag;
	}

	/**
	 * 下载对账文件
	 * 
	 * @see com.aipg.core.TranxOperationInf#downBills(java.lang.String, boolean)
	 */
	public void downBills(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("200002");
		TransQueryReq dqr = new TransQueryReq();
		aipgReq.setINFO(info);
		aipgReq.addTrx(dqr);
		dqr.setSTATUS(2);
		dqr.setMERCHANT_ID(tranxContants.merchantId);
		dqr.setTYPE(1);
		dqr.setSTART_DAY("20140506010000");
		dqr.setEND_DAY("20140506235959");
		dqr.setCONTFEE("1");

		xml = XmlTools.buildXml(aipgReq, true);
		String resp = sendToTlt(xml, isTLTFront, url);
		// 解释并写对账文件
		writeBill(resp);
	}

	/**
	 * @param resp
	 * @throws Exception
	 */
	private void writeBill(String resp) throws Exception {

		int iStart = resp.indexOf("<CONTENT>");
		if (iStart == -1)
			throw new Exception("XML报文中不存在<CONTENT>");
		int end = resp.indexOf("</CONTENT>");
		if (end == -1)
			throw new Exception("XML报文中不存在</CONTENT>");
		String billContext = resp.substring(iStart + 9, end);

		// 写文件
		FileOutputStream sos = null;
		sos = new FileOutputStream(new File("bills/bill.gz"));
		Base64InputStream b64is = new Base64InputStream(IOUtils.toInputStream(billContext), false);
		IOUtils.copy(b64is, sos);
		IOUtils.closeQuietly(b64is);
		// 解压
		ZipInputStream zin = new ZipInputStream(new FileInputStream(new File("bills/bill.gz")));
		ZipEntry zipEntry = null;
		while ((zipEntry = zin.getNextEntry()) != null) {
			String entryName = zipEntry.getName().toLowerCase();
			FileOutputStream os = new FileOutputStream("bills/" + entryName);
			// Transfer bytes from the ZIP file to the output file
			byte[] buf = new byte[1024];
			int len;
			while ((len = zin.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
			os.close();
			zin.closeEntry();
		}
	}

	/**
	 * 签约通知
	 * 
	 */
	public void signNotice(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("210003");// 交易类型
		aipgReq.setINFO(info);
		NSignReq nsign = new NSignReq();
		QSignDetail notify = new QSignDetail();
		notify.setACCTNAME("testt2");// 账户名
		notify.setAGREEMENTNO("0002000012");// 签约协议号
		notify.setACCT("6222023700014285995");// 银行账号
		notify.setSIGNTYPE("2");
		notify.setSTATUS("2");
		nsign.addDtl(notify);
		aipgReq.addTrx(nsign);
		xml = XmlTools.buildXml(aipgReq, true);
		dealRet(sendToTlt(xml, isTLTFront, url));
	}

	/**
	 * 下载简单对账文件
	 */
	public void downSimpleBills(String uRL11, boolean isTLTFront) throws Exception {
		String MERID = tranxContants.merchantId;
		String SETTDAY = "20130527";
		String REQTIME = "20130527121212";// df.format(new Date());
		String CONTFEE = "";
		String SIGN = "";
		CONTFEE = SETTDAY + "|" + REQTIME + "|" + MERID;
		System.out.println(CONTFEE);
		SIGN = XmlTools.signPlain(CONTFEE, tranxContants.getPfxPath(), tranxContants.getPfxPassword(), false);
		uRL11 = uRL11.replaceAll("@xxx", SETTDAY).replaceAll("@yyy", REQTIME).replaceAll("@zzz", MERID).replaceAll("@sss", SIGN);
		System.out.println(uRL11);
		CONTFEE = sendXml("", uRL11, true);
		FileUtil.saveToFile(CONTFEE, "bill.txt", "");// 默认编码GBK
	}

	/**
	 * 日期：Sep 4, 2012 功能：实时单笔代收付，100011是实时代笔代收，100014是实时单笔代付
	 * 
	 * @param trx_code
	 * @throws Exception
	 */
	public String singleTranx(Trans trans, String url, String trx_code, String busicode, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipg = new AipgReq();
		// 组建info 请求头 数据从TranContext中获取
		InfoReq info = makeReq(trx_code);
		aipg.setINFO(info);
		// 组建 INFO 请求头
		// Trans trans=new Trans();
		trans.setBUSINESS_CODE(busicode);
		trans.setMERCHANT_ID(tranxContants.merchantId);
		trans.setSUBMIT_TIME(df.format(new Date()));
		// 放到配置文件中

		trans.setACCOUNT_NAME(XmlTransContents.ACCOUNT_NAME);
		trans.setACCOUNT_NO(XmlTransContents.ACCOUNT_NO);
		trans.setACCOUNT_PROP(XmlTransContents.ACCOUNT_PROP);
		trans.setAMOUNT(XmlTransContents.AMOUNT);
		trans.setBANK_CODE(XmlTransContents.BANK_CODE);
		trans.setCURRENCY(XmlTransContents.CURRENCY);
		// trans.setACCOUNT_TYPE("01");
		trans.setCUST_USERID(XmlTransContents.CUST_USERID);
		trans.setTEL(XmlTransContents.TEL);

		aipg.addTrx(trans);

		xml = XmlTools.buildXml(aipg, true);
		System.out.println(xml);
		return dealRet(sendToTlt(xml, isTLTFront, url));

	}

	/**
	 * @param reqsn
	 *            交易流水号
	 * @param url
	 *            通联地址
	 * @param isTLTFront
	 *            是否通过前置机
	 * @param startDate
	 *            YYYYMMDDHHmmss
	 * @param endDate
	 *            YYYYMMDDHHmmss 日期：Sep 4, 2012 功能：交易结果查询
	 * @throws Exception
	 */
	public void queryTradeNew(String url, String reqsn, boolean isTLTFront, String startDate, String endDate) throws Exception {

		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("200004");
		aipgReq.setINFO(info);
		TransQueryReq dr = new TransQueryReq();
		aipgReq.addTrx(dr);
		dr.setMERCHANT_ID(tranxContants.merchantId);
		dr.setQUERY_SN(reqsn);
		dr.setSTATUS(1);
		dr.setTYPE(1);
		// dr.setSTATUS(2);
		if (reqsn == null || "".equals(reqsn)) {
			dr.setSTART_DAY(startDate);
			dr.setEND_DAY(endDate);
		}
		xml = XmlTools.buildXml(aipgReq, true);
		dealRet(sendToTlt(xml, isTLTFront, url));
	}

	/**
	 * @param url
	 *            接口地址
	 * @param isTLTFront
	 *            是否发送前置机（特殊商户使用）
	 * @param startDate
	 *            yyyyMMdd
	 * @param endDate
	 *            yyyyMMdd 功能： 历史余额查询 300001
	 */
	public void merAcctBalance(String url, boolean isTLTFront, String startDate, String endDate) {
		String xml = "";
		AipgReq aipg = new AipgReq();
		InfoReq info = makeReq("300001");// 交易码
		aipg.setINFO(info);
		AHQueryReq ahquery = new AHQueryReq();
		ahquery.setACCTNO("201303000000000130440");// 商户在通联的虚拟账号
		ahquery.setSTARTDAY(startDate);// 查询开始日期
		ahquery.setENDDAY(endDate);// 查询结束日期
		aipg.addTrx(ahquery);

		xml = XmlTools.buildXml(aipg, true);
		sendToTlt(xml, isTLTFront, url);
		System.err.println("注意：CLOSINGBAL的值为日终余额，也就是当前余额");
	}

	/**
	 * 返回报文处理逻辑
	 * 
	 * @param retXml
	 */
	public String dealRet(String retXml) {
		String trxcode = null;
		AipgRsp aipgrsp = null;
		String retCode = "";
		// 或者交易码
		if (retXml.indexOf("<TRX_CODE>") != -1) {
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0)
				trxcode = retXml.substring(begin, end);
		}
		aipgrsp = XSUtil.parseRsp(retXml);

		// 批量代收付返回处理逻辑
		if ("100001".equals(trxcode) || "100002".equals(trxcode) || "211000".equals(trxcode)) {
			if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("受理成功，请在20分钟后进行10/每次的轮询");
			} else {
				System.out.println("受理失败，失败原因：" + aipgrsp.getINFO().getERR_MSG());
			}
		}
		// 交易查询处理逻辑
		if ("200004".equals(trxcode) || "200005".equals(trxcode)) {
			if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
				QTransRsp qrsq = (QTransRsp) aipgrsp.getTrxData().get(0);
				System.out.println("查询成功，具体结果明细如下:");
				List<QTDetail> details = qrsq.getDetails();
				for (QTDetail lobj : details) {
					System.out.print("原支付交易批次号:" + lobj.getBATCHID() + "  ");
					System.out.print("记录序号:" + lobj.getSN() + "  ");
					System.out.print("账号:" + lobj.getACCOUNT_NO() + "  ");
					System.out.print("户名:" + lobj.getACCOUNT_NAME() + "  ");
					System.out.print("金额:" + lobj.getAMOUNT() + "  ");
					System.out.print("返回结果:" + lobj.getRET_CODE() + "  ");

					if ("0000".equals(lobj.getRET_CODE())) {
						System.out.println("返回说明:交易成功  ");
						System.out.println("更新交易库状态（原交易的状态）");
					} else {
						System.out.println("返回说明:" + lobj.getERR_MSG() + "  ");
						System.out.println("更新交易库状态（原交易的状态）");
					}

				}
			} else if ("2000".equals(aipgrsp.getINFO().getRET_CODE()) || "2001".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2003".equals(aipgrsp.getINFO().getRET_CODE()) || "2005".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2007".equals(aipgrsp.getINFO().getRET_CODE()) || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.print("返回说明:" + aipgrsp.getINFO().getRET_CODE() + "  ");
				System.out.println("返回说明：" + aipgrsp.getINFO().getERR_MSG());
				System.out.println("该状态时，说明整个批次的交易都在处理中");
			} else if ("2004".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("整批交易未受理通过（最终失败）");
			} else if ("1002".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("查询无结果集");
			} else {
				System.out.println("查询请求失败，请重新发起查询");
			}
		}
		// 实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
		if ("100011".equals(trxcode) || "100014".equals(trxcode)) {
			if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("提交成功");
				TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
				if ("0000".equals(ret.getRET_CODE())) {
					System.out.println("交易成功（最终结果）");
				} else {
					System.out.println("交易失败（最终结果）");
					System.out.println("交易失败原因：" + ret.getERR_MSG());
				}
				retCode = ret.getRET_CODE();
			} else if ("2000".equals(aipgrsp.getINFO().getRET_CODE()) || "2001".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2003".equals(aipgrsp.getINFO().getRET_CODE()) || "2005".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2007".equals(aipgrsp.getINFO().getRET_CODE()) || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
			} else if (aipgrsp.getINFO().getRET_CODE().startsWith("1")) {
				String errormsg = aipgrsp.getINFO().getERR_MSG() == null ? "连接异常，请重试" : aipgrsp.getINFO().getERR_MSG();
				System.out.println("交易请求失败，原因：" + errormsg);
			} else {
				TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易失败(最终结果)，失败原因：" + ret.getERR_MSG());
			}

		}
		// (单笔实时身份验证结果返回处理逻辑)
		if ("211003".equals(trxcode)) {
			if ("0000".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("提交成功");
				ValidRet ret = (ValidRet) aipgrsp.getTrxData().get(0);
				System.out.println("交易结果：" + ret.getRET_CODE() + ":" + ret.getERR_MSG());
			} else if ("2000".equals(aipgrsp.getINFO().getRET_CODE()) || "2001".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2003".equals(aipgrsp.getINFO().getRET_CODE()) || "2005".equals(aipgrsp.getINFO().getRET_CODE())
					|| "2007".equals(aipgrsp.getINFO().getRET_CODE()) || "2008".equals(aipgrsp.getINFO().getRET_CODE())) {
				System.out.println("验证处理中或者不确定状态，需要在稍后5分钟后进行验证结果查询（轮询）");
			} else if (aipgrsp.getINFO().getRET_CODE().startsWith("1")) {
				String errormsg = aipgrsp.getINFO().getERR_MSG() == null ? "连接异常，请重试" : aipgrsp.getINFO().getERR_MSG();
				System.out.println("验证请求失败，原因：" + errormsg);
			} else {
				TransRet ret = (TransRet) aipgrsp.getTrxData().get(0);
				System.out.println("验证失败(最终结果)，失败原因：" + ret.getERR_MSG());
			}
		}
		return retCode;
	}

	/**
	 * 日期：Sep 4, 2012 功能：单笔实时身份验证
	 * 
	 * @throws Exception
	 */
	public void singleAcctVerify(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("211003");
		aipgReq.setINFO(info);

		ValidR valid = new ValidR();
		valid.setACCOUNT_NAME("zhanggh");
		valid.setACCOUNT_NO("6228485880571233334");
		// valid.setACCOUNT_PROP("1");
		// valid.setACCOUNT_TYPE("01");
		valid.setBANK_CODE("0105");
		valid.setID("318562198408151666");
		valid.setID_TYPE("0");
		valid.setMERCHANT_ID(tranxContants.merchantId);
		valid.setTEL(tranxContants.tel);
		valid.setSUBMIT_TIME(df.format(new Date()));
		valid.setREMARK("单笔实时身份验证-备注字段");
		aipgReq.addTrx(valid);

		xml = XmlTools.buildXml(aipgReq, true);//
		String resp = sendToTlt(xml, isTLTFront, url);
		dealRet(resp);
	}

	/**
	 * 日期：Sep 4, 2012 功能：批量账户验证 211000
	 * 
	 * @throws Exception
	 */
	public void batchAcctVerify(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("211000");
		aipgReq.setINFO(info);

		ValidBReq vbreq = new ValidBReq();
		ValbSum VALBSUM = new ValbSum();
		VALBSUM.setMERCHANT_ID(tranxContants.merchantId);
		VALBSUM.setSUBMIT_TIME(df.format(new Date()));
		VALBSUM.setTOTAL_ITEM("12");
		// VALBSUM.setTOTAL_SUM("200000");

		ValidBD VALIDBD = new ValidBD();
		VbDetail vbdetail = null;
		for (int i = 0; i < 12; i++) {
			if (i % 2 != 0)
				tranxContants.bankcode = "0104";
			if (i % 3 != 0)
				tranxContants.bankcode = "0105";
			vbdetail = new VbDetail();
			vbdetail.setACCOUNT_NAME(tranxContants.acctName + i);
			vbdetail.setACCOUNT_NO(tranxContants.acctNo + i);
			vbdetail.setACCOUNT_PROP("1");
			vbdetail.setACCOUNT_TYPE("01");
			vbdetail.setBANK_CODE(tranxContants.bankcode);
			vbdetail.setSN("00" + i);
			vbdetail.setTEL(tranxContants.tel);
			vbdetail.setOPTYPE("01");// 01—新增；02—解除；03—更改
			vbdetail.setID_TYPE("0");// 证件类型：0 身份证
			vbdetail.setID("44201010423543543543");// 身份证号
			VALIDBD.addDTL(vbdetail);
		}
		vbreq.setVALBSUM(VALBSUM);
		vbreq.setVALIDBD(VALIDBD);
		aipgReq.addTrx(vbreq);

		xml = XmlTools.buildXml(aipgReq, true).replaceAll("<details>\n", "").replaceAll("</details>\n", "");
		String resp = sendToTlt(xml, isTLTFront, url);
		dealRet(resp);
	}

	/**
	 * 
	 * 功能：国民身份验证 220001
	 * 
	 * @author DON
	 * @throws Exception
	 */
	public void idVerify(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("220001");
		aipgReq.setINFO(info);

		IdVer vbreq = new IdVer();

		vbreq.setNAME("测试");
		vbreq.setIDNO("445224199003030030");
		aipgReq.addTrx(vbreq);

		xml = XmlTools.buildXml(aipgReq, true);
		String resp = sendToTlt(xml, isTLTFront, url);
		dealRet(resp);
	}

	/**
	 * 
	 * 功能：国民身份验证查询 220003
	 * 
	 * @author DON
	 * @throws Exception
	 */
	public void idVerifyQ(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("220003");
		aipgReq.setINFO(info);

		VerQry vbreq = new VerQry();

		vbreq.setQSN("200604000000445-1429522163908");
		vbreq.setQTARGET("1");
		aipgReq.addTrx(vbreq);

		xml = XmlTools.buildXml(aipgReq, true);
		String resp = sendToTlt(xml, isTLTFront, url);
		dealRet(resp);
	}

	/**
	 * 
	 * 功能：卡bin查询
	 * 
	 * @author DON
	 * @throws Exception
	 */
	public void cardbin(String url, boolean isTLTFront) throws Exception {
		String xml = "";
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("200007");
		aipgReq.setINFO(info);
		QCardBinReq vbreq = new QCardBinReq();
		vbreq.setACCTNO("6225881017838390");
		aipgReq.addTrx(vbreq);

		xml = XmlTools.buildXml(aipgReq, true);
		String resp = sendToTlt(xml, isTLTFront, url);
		dealRet(resp);
	}
}
