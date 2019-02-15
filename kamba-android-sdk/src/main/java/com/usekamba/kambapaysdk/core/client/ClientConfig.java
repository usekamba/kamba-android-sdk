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
 */
public class ClientConfig {

    private static volatile ClientConfig instance;
    private static final Object LOCK = new Object();

    private String merchantId;
    private String secretKey;
    private Environment environment;

    public enum Environment {SANDBOX, PRODUCTION}

    private ClientConfig() {
    }

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
    public ClientConfig configure(@NonNull String merchantId, @NonNull String secretKey, @NonNull Environment environment) {

        if (merchantId == null) {
            throw new NullPointerException("You must provide a merchant id");
        }

        if (environment == null) {
            throw new NullPointerException("You must provide an environment");
        }

        if (secretKey == null) {
            throw new NullPointerException("You must provide a secret");
        }

        this.merchantId = merchantId;
        this.environment = environment;
        this.secretKey = secretKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
