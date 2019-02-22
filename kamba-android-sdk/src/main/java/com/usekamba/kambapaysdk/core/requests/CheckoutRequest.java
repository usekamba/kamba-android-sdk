/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import com.squareup.moshi.Json;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class CheckoutRequest extends RequestBody implements Serializable {
    @Json(name = "channel")
    private final String channel = "ANDROID";
    @Json(name = "redirect_url_success")
    private String redirect_url_success = "https://comerciante.usekamba.com/";
    @Json(name = "notes")
    private String notes;
    @Json(name = "initial_amount")
    private Double initial_amount;

    public CheckoutRequest() { }

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

    }

    public String getNotes() {
        return notes;
    }

    protected void setRedirectUrlSuccess(String urlSuccess) {
        this.redirect_url_success = urlSuccess;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getInitialAmount() {
        return initial_amount;
    }

    public void setInitialAmount(double initial_amount) {
        this.initial_amount = initial_amount;
    }
}
