/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kamba_payments_sdk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.usekamba.kambapaysdk.core.client.ClientConfig;
import com.usekamba.kambapaysdk.core.model.CheckoutResponse;
import com.usekamba.kambapaysdk.core.requests.CheckoutRequest;
import com.usekamba.kambapaysdk.core.requests.CheckoutTransaction;
import com.usekamba.kambapaysdk.core.requests.CheckoutTransactionBuilder;
import com.usekamba.kambapaysdk.core.requests.TransactionCallback;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private Button startPayment = null;
    CheckoutRequest checkoutRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientConfig.getInstance().configure("Teresa da Purificação manuel",
                "Correia Antonio Chumbo",
                ClientConfig.Environment.PRODUCTION);
        checkoutRequest = new CheckoutRequest();
        checkoutRequest.setInitialAmount(90);
        checkoutRequest.setNotes("Curso de programação android: Básico");
        findViewById(R.id.start_payment).setOnClickListener(initVerification());


    }


    public View.OnClickListener initVerification() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String merchantId = ClientConfig.getInstance().getMerchantId();
                String secreKey = ClientConfig.getInstance().getSecretKey();
                String environment = ClientConfig.Environment.SANDBOX.toString();
                if (merchantId.equals("") || secreKey.equals("") || environment.equals("SANDBOX")) {
                    Toast.makeText(MainActivity.this, "the marchantId or secretKye can not be null", Toast.LENGTH_LONG).show();
                } else {
                    initKambaTransaction();
                }
            }
        };
    }

    private void initKambaTransaction() {
        CheckoutTransaction checkoutTransaction = new CheckoutTransactionBuilder()
                .addClientConfig(ClientConfig.getInstance())
                .addCheckoutRequest(checkoutRequest)
                .build();
        checkoutTransaction.enqueue(new TransactionCallback() {
            @Override
            public void onSuccess(final CheckoutResponse checkout) {

                runOnUiThread(() -> startActivity(new Intent(context, CheckoutActivity.class).putExtra("checkout", checkout)));
            }

            @Override
            public void onFailure(String message) {
                runOnUiThread(() -> Toast.makeText(context, "Error initiating Payment request: " + message, Toast.LENGTH_LONG).show());
            }
        });
    }
}
