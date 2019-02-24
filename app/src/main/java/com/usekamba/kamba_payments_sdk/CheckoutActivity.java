/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kamba_payments_sdk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.usekamba.kambapaysdk.core.model.CheckoutResponse;
import com.usekamba.kambapaysdk.ui.CheckoutWidget;
import com.usekamba.kambapaysdk.ui.KambaButton;
import com.usekamba.kambapaysdk.ui.PaymentResultListener;

public class CheckoutActivity extends AppCompatActivity {
    private CheckoutWidget checkoutWidget;
    private CheckoutResponse checkoutResponse;
    private KambaButton payButton;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        checkoutWidget = findViewById(R.id.checkout);
        payButton = findViewById(R.id.pay);
        // Para acessar o checkout response da tela anterior usa sempre response "(CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutResponse = (CheckoutResponse) getIntent().getSerializableExtra("checkout");
        checkoutWidget.setAmount(checkoutResponse.getTotalAmount());
        checkoutWidget.setExpirationDate(checkoutResponse.getExpiresAt());
        checkoutWidget.setTotalCheckoutAmount(checkoutResponse.getTotalAmount());
        checkoutWidget.setItemDescription(checkoutResponse.getNotes());
        checkoutWidget.setItemAmount(checkoutResponse.getInitialAmount());
        checkoutWidget.setQrCode(checkoutResponse.getQrCode());
        payButton.setOnPaymentListener(new PaymentResultListener() {
            @Override
            public void onSuccessfulPayment() {
                Toast.makeText(context, "Purchase Made", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(context, "Purchase not performed", Toast.LENGTH_SHORT).show();
            }
        });
        payButton.setOnClickListener(v -> payButton.payWithWallet(checkoutResponse, context));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        payButton.onActivityResult(requestCode, resultCode, data);
    }
}
