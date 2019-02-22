package com.usekamba.kambapaysdk.helpers

import java.math.BigDecimal

fun formatCheckoutAmount(amount: BigDecimal): String? {
    return when (amount.toString().contains(".0")) {
        true -> { amount.toString() + "0" }
        false -> { amount.toString() }
    }
}