package com.usekamba.kambapaysdk.core.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class BigMoney extends BigDecimal {
    public BigMoney(char[] in, int offset, int len) {
        super(in, offset, len);
    }

    public BigMoney(char[] in, int offset, int len, MathContext mc) {
        super(in, offset, len, mc);
    }

    public BigMoney(char[] in) {
        super(in);
    }

    public BigMoney(char[] in, MathContext mc) {
        super(in, mc);
    }

    public BigMoney(String val) {
        super(val);
    }

    public BigMoney(String val, MathContext mc) {
        super(val, mc);
    }

    public BigMoney(double val) {
        super(val);
    }

    public BigMoney(double val, MathContext mc) {
        super(val, mc);
    }

    public BigMoney(BigInteger val) {
        super(val);
    }

    public BigMoney(BigInteger val, MathContext mc) {
        super(val, mc);
    }

    public BigMoney(BigInteger unscaledVal, int scale) {
        super(unscaledVal, scale);
    }

    public BigMoney(BigInteger unscaledVal, int scale, MathContext mc) {
        super(unscaledVal, scale, mc);
    }

    public BigMoney(int val) {
        super(val);
    }

    public BigMoney(int val, MathContext mc) {
        super(val, mc);
    }

    public BigMoney(long val) {
        super(val);
    }

    public BigMoney(long val, MathContext mc) {
        super(val, mc);
    }
}
