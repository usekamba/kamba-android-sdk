package com.usekamba.kambapaysdk.ui;

public interface PaymentResultListener {
    void onSuccessfulPayment();
    void onFailure();
}
