/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class CheckoutRequest implements Serializable {
    @Json(name = "channel")
    private final String channel = "ANDROID";
    @Json(name = "redirect_url_success")
    private final String redirect_url_success = "https://www.usekamba.com";
    @Json(name = "notes")
    private String notes;
    @Json(name = "initial_amount")
    private int initial_amount;
    @Json(name = "payment_method")
    private final String payment_method = "WALLET";

    public CheckoutRequest() { }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getInitialAmount() {
        return initial_amount;
    }

    public void setInitialAmount(int initial_amount) {
        this.initial_amount = initial_amount;
    }
}
