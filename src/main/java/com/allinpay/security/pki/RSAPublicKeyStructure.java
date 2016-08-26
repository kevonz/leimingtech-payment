package com.allinpay.security.pki;

import java.math.BigInteger;

public class RSAPublicKeyStructure
  implements KeyStructure
{
  private BigInteger modulus;
  private BigInteger pubexp;

  public RSAPublicKeyStructure(BigInteger _modulus, BigInteger _pubexp)
  {
    this.modulus = _modulus;
    this.pubexp = _pubexp;
  }

  public BigInteger getModulus()
  {
    return this.modulus;
  }

  public BigInteger getPubExp()
  {
    return this.pubexp;
  }
}