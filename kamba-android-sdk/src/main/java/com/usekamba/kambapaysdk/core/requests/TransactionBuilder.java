/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import com.usekamba.kambapaysdk.core.client.ClientConfig;

class Transaction {

    public interface TransactionBuilder {
        CheckoutTransaction build();
        CheckoutTransactionBuilder addCheckoutRequest(CheckoutRequest checkoutRequest);
        CheckoutTransactionBuilder addClientConfig(ClientConfig clientConfig);
    }

}
