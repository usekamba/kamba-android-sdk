/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
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
import com.usekamba.kambapaysdk.helpers.FourmeKt;
import java.math.BigDecimal;
import java.util.List;

public class KambaButton extends RelativeLayout implements PreferenceManager.OnActivityResultListener {
    private static final String KAMBA_APP_PACKAGE = "com.usekamba.kamba.kamba";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final String TRANSACTION_AMOUNT = "amount";
    private static final String DESCRIPTION = "description";
    private static final String WEBSITE = "https://www.usekamba.com/";
    private static final String MERCHANT_PAYMENT = "localBusinessPayment";
    private static final int PURCHASE_COMPLETE = 0;
    private static final int PURCHASE_NOT_COMPLETED = -1;
    private static final int PURCHASE_ITEM = 0;
    private PaymentResultListener listener;

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

    public void setOnPaymentListener(PaymentResultListener listener) {
        this.listener = listener;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.kamba_res_KambaButton);
        View mRoot = inflate(context, R.layout.button, this);
        RelativeLayout mBackground = mRoot.findViewById(R.id.kamba_button_bg);
        TextView mText = mRoot.findViewById(R.id.kamba_button_text);
        ImageView mLogo = mRoot.findViewById(R.id.kamba_logo);
        mText.setText(R.string.kamba_res_button_text);
        Typeface font = ResourcesCompat.getFont(context, R.font.google_sans_bold);
        mText.setTypeface(font, Typeface.BOLD);
        if (attributes.getBoolean(R.styleable.kamba_res_KambaButton_lightTheme, true)) {
            mBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.kamba_res_button_light_theme));
        } else {
            mBackground.setBackground(ContextCompat.getDrawable(context, R.drawable.kamba_res_button));
        }
        attributes.recycle();
    }

    public void payWithWallet(CheckoutResponse checkoutResponse, Context context) {
        if (checkoutResponse != null) {
            PackageManager packageManager = context.getPackageManager();
            Intent walletIntent = new Intent(Intent.ACTION_VIEW);
            walletIntent.setData(Uri.parse("https://comerciante.usekamba.com/pay?chID=" + checkoutResponse.getId()));
            List<ResolveInfo> activities = packageManager.queryIntentActivities(walletIntent, 0);
            boolean isIntentSafe = activities.size() > 0;
            if (isIntentSafe) {
                walletIntent.putExtra(TRANSACTION_AMOUNT, FourmeKt.formatCheckoutAmount(new BigDecimal(checkoutResponse.getTotalAmount())));
                walletIntent.putExtra(MERCHANT_PAYMENT, true);
                walletIntent.putExtra(DESCRIPTION, checkoutResponse.getNotes());
                walletIntent.putExtra(PHONE_NUMBER, checkoutResponse.getMerchant().getPhoneNumber());
                ((Activity) context).startActivityForResult(walletIntent, PURCHASE_ITEM);
            } else {
                try {
                    walletIntent = new Intent(Intent.ACTION_VIEW);
                    walletIntent.setData(Uri.parse("market://details?id=" + KAMBA_APP_PACKAGE));
                    context.startActivity(walletIntent);
                } catch (ActivityNotFoundException e) {
                    walletIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WEBSITE));
                    context.startActivity(walletIntent);
                }
            }
        } else {
            throw new IllegalStateException("CheckoutResponse must not be null");
        }

    }

    /**
     * This method triggers the PaymentResultListener based on the request code and result code returned
     * from the Wallets PayQRViaActivity.
     *
     * This code must be called as such:
     * <pre>
     *     @Override
     *     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
     *         super.onActivityResult(requestCode, resultCode, data);
     *         payButton.onActivityResult(requestCode, resultCode, data);
     *     }
     *     If not called in your classes onActivityResult method
     *     then the PaymentResultListener will not be able to dispatch the correct action.
     *</pre>
     * @param requestCode int: The integer request code for initiating a PaymentRequest.
     *                    Should always be PURCHASE_ITEM since the library itself will handle requests
     * @param resultCode int: The integer result code received from the Kamba Wallet. Should always be either
     *                   PURCHASE_COMPLETE or PURCHASE_NOT_COMPLETED
     * @param data Intent: Data returned from called Activity
     */
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PURCHASE_ITEM) {
            if (resultCode == PURCHASE_COMPLETE) {
                if (listener != null) {
                    listener.onSuccessfulPayment();
                }
                return true;
            } if (resultCode == PURCHASE_NOT_COMPLETED) {
                if (listener != null) {
                    listener.onFailure();
                }
                return false;
            }
        } else {
            listener.onFailure();
            return false;
        }
        return false;
    }
}


