/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.client;

import android.support.annotation.NonNull;

/**
 * This singleton class is responsible for setting up parameters
 * for Payment Requests
 *
 */
public class ClientConfig {

    private static volatile ClientConfig instance;
    private static final Object LOCK = new Object();
    private String apiKey;
    private String merchantId;
    private Environment environment;

    public enum Environment {SANDBOX, PRODUCTION }

    private ClientConfig() {}

    public static ClientConfig getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ClientConfig();
                }
            }
        }
        return instance;
    }

    @SuppressWarnings("ConstantConditions")
    public ClientConfig configure(@NonNull String apiKey, @NonNull String merchantId, @NonNull Environment environment) {
        if (apiKey == null) {
            throw new NullPointerException("You must provide an api key");
        }

        if (merchantId == null) {
            throw  new NullPointerException("You must provide a merchant id");
        }

        if (environment == null) {
            throw new NullPointerException("You must provide an environment");
        }
        this.apiKey = apiKey;
        this.merchantId = merchantId;
        this.environment = environment;
        return this;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
