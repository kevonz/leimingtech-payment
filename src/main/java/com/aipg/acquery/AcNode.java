package com.aipg.acquery;

public class AcNode {
	private String ACCTNO;
	private String ACCTNAME;
	private String BALANCE;
	private String USABLEBAL;
	private int BALBY;
	private int DEPOSIT;
	private int WITHDRAW;
	private int TRANSFERIN;
	private int TRANSFEROUT;
	private int PAYABLE;
	private int DEFCLR;
	
	public String getACCTNO() {
		return ACCTNO;
	}
	public void setACCTNO(String aCCTNO) {
		ACCTNO = aCCTNO;
	}
	public String getACCTNAME() {
		return ACCTNAME;
	}
	public void setACCTNAME(String aCCTNAME) {
		ACCTNAME = aCCTNAME;
	}
	public String getBALANCE() {
		return BALANCE;
	}
	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}
	public String getUSABLEBAL() {
		return USABLEBAL;
	}
	public void setUSABLEBAL(String uSABLEBAL) {
		USABLEBAL = uSABLEBAL;
	}
	public int getBALBY() {
		return BALBY;
	}
	public void setBALBY(int bALBY) {
		BALBY = bALBY;
	}
	public int getDEPOSIT() {
		return DEPOSIT;
	}
	public void setDEPOSIT(int dEPOSIT) {
		DEPOSIT = dEPOSIT;
	}
	public int getWITHDRAW() {
		return WITHDRAW;
	}
	public void setWITHDRAW(int wITHDRAW) {
		WITHDRAW = wITHDRAW;
	}
	public int getTRANSFERIN() {
		return TRANSFERIN;
	}
	public void setTRANSFERIN(int tRANSFERIN) {
		TRANSFERIN = tRANSFERIN;
	}
	public int getTRANSFEROUT() {
		return TRANSFEROUT;
	}
	public void setTRANSFEROUT(int tRANSFEROUT) {
		TRANSFEROUT = tRANSFEROUT;
	}
	public int getPAYABLE() {
		return PAYABLE;
	}
	public void setPAYABLE(int pAYABLE) {
		PAYABLE = pAYABLE;
	}
	public int getDEFCLR() {
		return DEFCLR;
	}
	public void setDEFCLR(int dEFCLR) {
		DEFCLR = dEFCLR;
	}
}
