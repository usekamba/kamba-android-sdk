/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core;

import android.content.Context;
import android.widget.Toast;

import com.usekamba.kambapaysdk.core.client.ClientConfig;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha1 {

    private static final String ALGORITHM = "HmacSHA1";
    private Context context;

    private String getHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public byte[] computeHmac(String message, String secretKey) {
        Mac hmac;
        SecretKey secretKey1 = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);
        try {
            hmac = Mac.getInstance("HmacSHA1");
            hmac.init(secretKey1);
            return hmac.doFinal(message.getBytes());
        } catch (InvalidKeyException e) {

        } catch (NoSuchAlgorithmException e1) {
        }
        return null;
    }

}

