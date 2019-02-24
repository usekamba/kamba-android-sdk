package com.usekamba.kamba_payments_sdk;

import android.app.Application;

import com.usekamba.kambapaysdk.core.client.ClientConfig;

public class PaymentsDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClientConfig.getInstance().configure("", "", ClientConfig.Environment.PRODUCTION);
    }
}
