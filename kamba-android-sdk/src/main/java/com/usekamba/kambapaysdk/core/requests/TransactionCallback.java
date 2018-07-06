/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import com.usekamba.kambapaysdk.core.model.CheckoutResponse;

import java.io.IOException;

import okhttp3.Response;

public interface TransactionCallback {
    void onSuccess(CheckoutResponse checkout);
    void onFailure(String message);
}
