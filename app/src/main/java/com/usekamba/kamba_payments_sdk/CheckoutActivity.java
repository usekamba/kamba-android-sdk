/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kamba_payments_sdk;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.usekamba.kambapaysdk.core.model.CheckoutResponse;
import com.usekamba.kambapaysdk.ui.CheckoutWidget;
import com.usekamba.kambapaysdk.ui.KambaButton;

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
        payButton.setOnClickListener(v -> payButton.payWithWallet(checkoutResponse, context));
    }
}
