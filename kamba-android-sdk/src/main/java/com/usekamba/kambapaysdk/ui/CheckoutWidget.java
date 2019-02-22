/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.usekamba.kambapaysdk.R;
import com.usekamba.kambapaysdk.helpers.DateHelper;

import net.glxn.qrgen.android.QRCode;

import java.text.NumberFormat;
import java.util.Locale;

public class CheckoutWidget extends ConstraintLayout {
    private TextView mCheckoutAmount;
    private ImageView mCheckoutQrCode;
    private TextView mCheckoutExpirationDate;
    private TextView mCheckoutItemDescription;
    private TextView mCheckoutItemAmount;
    private TextView mCheckoutTotalAmount;
    private final Locale currentLocale = new Locale("fr", "FR");

    public CheckoutWidget(Context context) {
        super(context);
    }

    public CheckoutWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CheckoutWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View mRoot = inflate(context, R.layout.checkoutwidget, this);
        ConstraintLayout mBackground = mRoot.findViewById(R.id.background);
        CardView mCardView = mRoot.findViewById(R.id.materialCardView);
        mCheckoutAmount = mRoot.findViewById(R.id.checkout_amount);
        mCheckoutQrCode = mRoot.findViewById(R.id.checkout_kamba_qr);
        mCheckoutExpirationDate = mRoot.findViewById(R.id.checkout_expiration_date);
        mCheckoutItemDescription = mRoot.findViewById(R.id.checkout_item_description);
        mCheckoutItemAmount = mRoot.findViewById(R.id.checkout_item_amount);
        mCheckoutTotalAmount = mRoot.findViewById(R.id.checkout_total_amount);
    }

    private String setCurrency(Double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
        return currencyFormatter.format(amount).replace("€", "Kz");
    }

    public void setQrCode(String qrCode) {
        Bitmap myBitmap = QRCode.from(qrCode).bitmap();
        mCheckoutQrCode.setImageBitmap(myBitmap);
    }

    public void setAmount(Double amount) {
        mCheckoutAmount.setText(setCurrency(amount));
    }

    public void setExpirationDate(String expirationDate) {
        mCheckoutExpirationDate.setText(String.format("%s às %s", DateHelper.convertDate(expirationDate), DateHelper.convertTimeToMin(expirationDate)));
    }

    public void setItemDescription(String itemDescription) {
        mCheckoutItemDescription.setText(itemDescription);
    }

    public void setItemAmount(Double itemAmount) {
        mCheckoutItemAmount.setText(setCurrency(itemAmount));
    }

    public void setTotalCheckoutAmount(Double totalCheckoutAmount) {
        mCheckoutTotalAmount.setText(setCurrency(totalCheckoutAmount));
    }

}
