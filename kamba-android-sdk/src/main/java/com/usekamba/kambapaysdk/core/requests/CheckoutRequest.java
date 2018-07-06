/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import java.io.Serializable;

public class CheckoutRequest implements Serializable {
    private final String redirect_url_success = "https://www.usekamba.com";
    private final String channel = "Mobile";
    private String notes;
    private final String currency = "AOA";
    private Double initial_amount;
    private final String payment_method = "wallet";

    public CheckoutRequest() { }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getInitialAmount() {
        return initial_amount;
    }

    public void setInitialAmount(Double initial_amount) {
        this.initial_amount = initial_amount;
    }
}
