package com.usekamba.kamba_payments_sdk;

import android.app.Application;

import com.usekamba.kambapaysdk.core.client.ClientConfig;

public class PaymentsDemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClientConfig.getInstance()
                .configure("8f74aff4-1821-4ad7-9d51-7cf3a303743a",
                        "D4P2HM+35xby+2Ajm0+3FNcD1HF1RGptPp7UqnNgLl8=",
                        ClientConfig.Environment.PRODUCTION);
    }
}
