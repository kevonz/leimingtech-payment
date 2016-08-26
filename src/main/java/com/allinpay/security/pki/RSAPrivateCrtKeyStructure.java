package com.allinpay.security.pki;

import java.math.BigInteger;

public class RSAPrivateCrtKeyStructure
  implements KeyStructure
{
  private static final long serialVersionUID = 1L;
  private BigInteger modulus;
  private BigInteger pubExp;
  private BigInteger priExp;
  private BigInteger p;
  private BigInteger q;
  private BigInteger dP;
  private BigInteger dQ;
  private BigInteger qInv;

  public RSAPrivateCrtKeyStructure(BigInteger mod, BigInteger pubExp, BigInteger priExp, BigInteger p, BigInteger q, BigInteger dP, BigInteger dQ, BigInteger qInv)
  {
    this.modulus = mod;
    this.pubExp = pubExp;
    this.priExp = priExp;
    this.p = p;
    this.q = q;
    this.dP = dP;
    this.dQ = dQ;
    this.qInv = qInv;
  }

  public BigInteger getModulus() {
    return this.modulus;
  }

  public void setModulus(BigInteger modulus) {
    this.modulus = modulus;
  }

  public BigInteger getPubExp() {
    return this.pubExp;
  }

  public void setPubExp(BigInteger pubExp) {
    this.pubExp = pubExp;
  }

  public BigInteger getPriExp() {
    return this.priExp;
  }

  public void setPriExp(BigInteger priExp) {
    this.priExp = priExp;
  }

  public BigInteger getP() {
    return this.p;
  }

  public void setP(BigInteger p) {
    this.p = p;
  }

  public BigInteger getQ() {
    return this.q;
  }

  public void setQ(BigInteger q) {
    this.q = q;
  }

  public BigInteger getDP() {
    return this.dP;
  }

  public void setDP(BigInteger dp) {
    this.dP = dp;
  }

  public BigInteger getDQ() {
    return this.dQ;
  }

  public void setDQ(BigInteger dq) {
    this.dQ = dq;
  }

  public BigInteger getQInv() {
    return this.qInv;
  }

  public void setQInv(BigInteger inv) {
    this.qInv = inv;
  }
}