package com.aipg.transfer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;

public class TransferUtils {

	final static Logger log = LoggerFactory.getLogger(TransferUtils.class);

	/**
	 * 检查请求信息
	 * 
	 * @param aipgReq
	 * @return
	 */
	public static AipgRsp verifyReq(AipgReq aipgReq) {
		if (aipgReq.getINFO() == null) {
			log.info("报文INFO区不能为空，请检查");
			return packRsp(aipgReq, "1000", "报文INFO区不能为空，请检查");
		}
		if (aipgReq.getTrxData() == null || aipgReq.trxObj() == null) {
			log.info("报文TRANSFERREQ区不能为空，请检查");
			return packRsp(aipgReq, "1000", "报文TRANSFERREQ区不能为空，请检查");
		}
		if (isEmpty(aipgReq.getINFO().getVERSION())) {
			log.info("版本不能为空，请检查");
			return packRsp(aipgReq, "1000", "版本不能为空，请检查");
		}
		if (isEmpty(aipgReq.getINFO().getLEVEL())) {
			log.info("处理级别不能为空，请检查");
			return packRsp(aipgReq, "1000", "处理级别不能为空，请检查");
		}
		if (isEmpty(aipgReq.getINFO().getUSER_NAME())) {
			log.info("用户名不能为空，请检查");
			return packRsp(aipgReq, "1000", "用户名不能为空，请检查");
		}
		if (isEmpty(aipgReq.getINFO().getUSER_PASS())) {
			log.info("用户密码不能为空，请检查");
			return packRsp(aipgReq, "1000", "用户密码不能为空，请检查");
		}
		if (isEmpty(aipgReq.getINFO().getREQ_SN())) {
			log.info("交易流水号不能为空，请检查");
			return packRsp(aipgReq, "1000", "交易流水号不能为空，请检查");
		}
		TransferReq transferReq = (TransferReq) aipgReq.trxObj();
		if (isEmpty(transferReq.getBUSINESS_CODE())) {
			log.info("业务代码不能为空，请检查");
			return packRsp(aipgReq, "1000", "业务代码不能为空，请检查");
		}
		if (isEmpty(transferReq.getMERCHANT_ID())) {
			log.info("商户代码不能为空，请检查");
			return packRsp(aipgReq, "1000", "商户代码不能为空，请检查");
		}
		if (isEmpty(transferReq.getAMOUNT())) {
			log.info("交易金额不能为空，请检查");
			return packRsp(aipgReq, "1000", "交易金额不能为空，请检查");
		}
		if (!transferReq.getAMOUNT().matches("[\\d]+")) {
			log.info("交易金额不正确，请检查");
			return packRsp(aipgReq, "1000", "交易金额不正确，请检查");
		}
		if (!isEmpty(transferReq.getFEE()) && !transferReq.getFEE().matches("[\\d]+")) {
			log.info("转账手续费不正确，请检查");
			return packRsp(aipgReq, "1000", "转账手续费不正确，请检查");
		}
		// 转出账户检查
		if (isEmpty(transferReq.getD_ACCOUNT_NO())) {
			log.info("转出账号不能为空，请检查");
			return packRsp(aipgReq, "1000", "转出账号不能为空，请检查");
		}
		if (isEmpty(transferReq.getD_ACCOUNT_NAME())) {
			log.info("转出账号名不能为空，请检查");
			return packRsp(aipgReq, "1000", "转出账号名不能为空，请检查");
		}
		String dAcctProp = transferReq.getD_ACCOUNT_PROP();
		if (isEmpty(dAcctProp)) {
			log.info("转出账号属性不能为空，请检查");
			return packRsp(aipgReq, "1000", "转出账号属性不能为空，请检查");
		}
		if (!"0".equals(dAcctProp) && !"1".equals(dAcctProp)) {
			log.info("转出账号属性不正确，请检查");
			return packRsp(aipgReq, "1000", "转出账号属性不正确，请检查");
		}
		String dAcctType = transferReq.getD_ACCOUNT_TYPE();
		if (!isEmpty(dAcctType) && !"00".equals(dAcctType)
				&& !"01".equals(dAcctType) && !"02".equals(dAcctType)) {
			log.info("转出账号类型不正确，请检查");
			return packRsp(aipgReq, "1000", "转出账号类型不正确，请检查");
		}
		String dIdType = transferReq.getD_ID_TYPE();
		if (!isEmpty(dIdType) && !"0".equals(dIdType) && !"1".equals(dIdType)
				&& !"2".equals(dIdType) && !"3".equals(dIdType)
				&& !"4".equals(dIdType) && !"5".equals(dIdType)
				&& !"6".equals(dIdType) && !"7".equals(dIdType)
				&& !"8".equals(dIdType) && !"9".equals(dIdType)
				&& !"X".equals(dIdType)) {
			log.info("转出账号开户证件类型不正确，请检查");
			return packRsp(aipgReq, "1000", "转出账号开户证件类型不正确，请检查");
		}

		// 转入账户检查
		if (isEmpty(transferReq.getP_ACCOUNT_NO())) {
			log.info("转入账号不能为空，请检查");
			return packRsp(aipgReq, "1000", "转入账号不能为空，请检查");
		}
		if (isEmpty(transferReq.getP_ACCOUNT_NAME())) {
			log.info("转入账号名不能为空，请检查");
			return packRsp(aipgReq, "1000", "转入账号名不能为空，请检查");
		}
		String pAcctProp = transferReq.getP_ACCOUNT_PROP();
		if (isEmpty(pAcctProp)) {
			log.info("转入账号属性不能为空，请检查");
			return packRsp(aipgReq, "1000", "转入账号属性不能为空，请检查");
		}
		if (!"0".equals(pAcctProp) && !"1".equals(pAcctProp)) {
			log.info("转入账号属性不正确，请检查");
			return packRsp(aipgReq, "1000", "转入账号属性不正确，请检查");
		}
		String pAcctType = transferReq.getP_ACCOUNT_TYPE();
		if (!isEmpty(pAcctType) && !"00".equals(pAcctType)
				&& !"01".equals(pAcctType) && !"02".equals(pAcctType)) {
			log.info("转入账号类型不正确，请检查");
			return packRsp(aipgReq, "1000", "转入账号类型不正确，请检查");
		}
		String pIdType = transferReq.getP_ID_TYPE();
		if (!isEmpty(pIdType) && !"0".equals(pIdType) && !"1".equals(pIdType)
				&& !"2".equals(pIdType) && !"3".equals(pIdType)
				&& !"4".equals(pIdType) && !"5".equals(pIdType)
				&& !"6".equals(pIdType) && !"7".equals(pIdType)
				&& !"8".equals(pIdType) && !"9".equals(pIdType)
				&& !"X".equals(pIdType)) {
			log.info("转入账号开户证件类型不正确，请检查");
			return packRsp(aipgReq, "1000", "转入账号开户证件类型不正确，请检查");
		}
		return null;
	}

	public static AipgRsp packRsp(AipgReq aipgReq, String retCode, String msg) {
		AipgRsp aipgRsp = AipgRsp.packRsp(aipgReq, retCode, msg);
		TransferRsp transferRep = new TransferRsp();
		transferRep.setRET_CODE(retCode);
		transferRep.setERR_MSG(msg);
		transferRep.setFEE("");
		aipgRsp.addTrx(transferRep);
		return aipgRsp;
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().length() == 0;
	}
}
