/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.usekamba.kambapaysdk.R;
import com.usekamba.kambapaysdk.core.model.CheckoutResponse;

import java.util.List;

public class KambaButton extends RelativeLayout {
    private static final String KAMBA_APP_PACKAGE = "com.usekamba.kamba.kamba";
    private static final String TRANSACTION_RECEIVER_ID = "transactionReceiverId";
    private static final String TRANSACTION_RECEIVER_FIRST_NAME = "transactionReceiverFirstName";
    private static final String TRANSACTION_IS_MERCHANT = "business";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String TRANSACTION_AMOUNT = "amount";
    private static final String DESCRIPTION = "description";
    private static final String WEBSITE = "https://www.usekamba.com/";

    public KambaButton(Context context) {
        super(context);
    }

    public KambaButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public KambaButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public KambaButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.KambaButton);
        View mRoot = inflate(context, R.layout.button, this);
        RelativeLayout mBackground = mRoot.findViewById(R.id.kamba_button_bg);
        TextView mText = mRoot.findViewById(R.id.kamba_button_text);
        ImageView mLogo = mRoot.findViewById(R.id.kamba_logo);

        mText.setText(R.string.kamba_res_button_text);
        Typeface font = ResourcesCompat.getFont(context, R.font.montserrat_bold);
        mText.setTypeface(font, Typeface.BOLD);

        if (attributes.getBoolean(R.styleable.KambaButton_lightTheme, true)) {
            mBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.button_light_theme));
        } else {
            mBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.button));
        }
        attributes.recycle();
    }

    public void payWithWallet(CheckoutResponse checkoutResponse, Context context) {
        if (checkoutResponse != null) {
            PackageManager packageManager = context.getPackageManager();
            Intent walletIntent = new Intent(Intent.ACTION_VIEW);
            walletIntent.setData(Uri.parse("checkout://com.usekamba"));
            List<ResolveInfo> activities = packageManager.queryIntentActivities(walletIntent, 0);
            boolean isIntentSafe = activities.size() > 0;
            if (isIntentSafe) {
                walletIntent.putExtra(TRANSACTION_IS_MERCHANT, true);
                walletIntent.putExtra(TRANSACTION_RECEIVER_ID, checkoutResponse.getMerchant().getId());
                walletIntent.putExtra(TRANSACTION_RECEIVER_FIRST_NAME, checkoutResponse.getMerchant().getBusinessName());
                walletIntent.putExtra(TRANSACTION_AMOUNT, (checkoutResponse.getTotalAmount().intValue()));
                walletIntent.putExtra(DESCRIPTION, checkoutResponse.getNotes());
                walletIntent.putExtra(PHONE_NUMBER, checkoutResponse.getMerchant().getPhoneNumber());
                context.startActivity(walletIntent);
            } else {
                try {
                    walletIntent = new Intent(Intent.ACTION_VIEW);
                    walletIntent.setData(Uri.parse("market://details?id="+KAMBA_APP_PACKAGE));
                    context.startActivity(walletIntent);
                } catch (ActivityNotFoundException e) {
                    walletIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE));
                    context.startActivity(walletIntent);
                }
            }

        } else {
            throw new IllegalStateException("CheckoutRespone must not be null");
        }

    }

}


