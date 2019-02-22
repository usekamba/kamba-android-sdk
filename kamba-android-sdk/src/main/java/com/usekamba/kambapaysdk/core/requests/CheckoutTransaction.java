/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.usekamba.kambapaysdk.core.Platform;
import com.usekamba.kambapaysdk.core.model.CheckoutResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckoutTransaction {
    private OkHttpClient client;
    private Request request;
    private final Moshi moshi = new Moshi.Builder().build();
    private final JsonAdapter<CheckoutResponse> checkoutResponseJsonAdapter = moshi.adapter(CheckoutResponse.class);
    private TransactionCallback callback;
    private String TAG = this.getClass().getSimpleName();

    void setClient(OkHttpClient client) {
        this.client = client;
    }


    public void enqueue(final TransactionCallback transactionCallback) {
        if (callback == null) {
            this.callback = transactionCallback;
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 201) {
                    callback.onSuccess( checkoutResponseJsonAdapter.fromJson(response.body().string()));
                }
                if (response.code() == 422) {
                    callback.onFailure("CheckoutRequest data is not correct");
                }

                if (response.code() == 401) {
                    callback.onFailure("API Key is invalid. Verify that your API KEY is valid otherwise contact suporte@usekamba.com");
                }

                if (response.code() == 403) {
                    Log.d(TAG, "KAMBA ANDROID SDK: " + response.message());
                    callback.onFailure("Signature is Invalid");
                }
            }
        });


    }


   void setRequest(Request request) {
       this.request = request;
   }

}
