/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.usekamba.kambapaysdk.core.client.ClientConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CheckoutTransactionBuilder implements Transaction.TransactionBuilder {
    private OkHttpClient client = new OkHttpClient();
    private CheckoutRequest checkoutRequest;
    private ClientConfig clientConfig;
    private final Moshi moshi = new Moshi.Builder().build();
    private final MediaType mediaType = MediaType.parse("application/json");
    private final JsonAdapter<CheckoutRequest> checkoutRequestJsonAdapter = moshi.adapter(CheckoutRequest.class);
    private Request request;

    private void setUpRequestAuthorization(ClientConfig clientConfig) {
        if (clientConfig.getEnvironment() == ClientConfig.Environment.SANDBOX) {
            RequestBody requestBody = RequestBody.create(mediaType, checkoutRequestJsonAdapter.toJson(checkoutRequest));
            String API_SANDBOX_URL = "https://kamba-api-staging.herokuapp.com/v1/checkouts";
            request = new Request.Builder().url(API_SANDBOX_URL)
                    .header("User-Agent", "SDK")
                    .addHeader("Authorization", "Token " + clientConfig.getApiKey())
                    .addHeader("Content-Type", "application/json;charset=utf-8")
                    .post(requestBody)
                    .build();
        } else {
            RequestBody requestBody = RequestBody.create(mediaType, checkoutRequestJsonAdapter.toJson(checkoutRequest));
            String API_PRODUCTION_URL = "https://api.usekamba.com/v1/checkouts";
            request = new Request.Builder().url(API_PRODUCTION_URL)
                    .header("User Agent", "SDK")
                    .addHeader("Authorization", "Token " + clientConfig.getApiKey())
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();
        }

    }

    @Override
    public CheckoutTransactionBuilder addCheckoutRequest(CheckoutRequest checkoutRequest) {
        this.checkoutRequest = checkoutRequest;
        return this;
    }

    @Override
    public CheckoutTransactionBuilder addClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        return this;
    }

    @Override
    public CheckoutTransaction build() {
        setUpRequestAuthorization(clientConfig);
        CheckoutTransaction transaction = new CheckoutTransaction();
        transaction.setClient(client);
        transaction.setRequest(request);
        return transaction;
    }
}
