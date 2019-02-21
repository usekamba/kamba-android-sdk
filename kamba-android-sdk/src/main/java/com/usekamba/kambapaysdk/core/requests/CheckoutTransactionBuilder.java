/*
 * Copyright (C) UseKamba Ltda - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential and will be punished by law
 * Written by Alexandre Antonio Juca <corextechnologies@gmail.com>
 *
 */

package com.usekamba.kambapaysdk.core.requests;

import android.util.Log;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.usekamba.kambapaysdk.core.HmacSha1;
import com.usekamba.kambapaysdk.core.client.ClientConfig;
import com.usekamba.kambapaysdk.core.security.Charsets;
import com.usekamba.kambapaysdk.core.security.binary.Hex;
import com.usekamba.kambapaysdk.core.security.digest.HmacUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CheckoutTransactionBuilder implements Transaction.TransactionBuilder {
    private OkHttpClient client = new OkHttpClient();
    private CheckoutRequest checkoutRequest;
    private ClientConfig clientConfig;
    private final Moshi moshi = new Moshi.Builder().build();
    private final MediaType mediaType = MediaType.parse("application/json; charset=utf-8\"");
    private final JsonAdapter<CheckoutRequest> checkoutRequestJsonAdapter = moshi.adapter(CheckoutRequest.class);
    private Request request;
    private String URL;
    private String timeStamp;

    private void setUpRequestAuthorization(ClientConfig clientConfig) {
        if (clientConfig.getEnvironment() == ClientConfig.Environment.SANDBOX) {
            RequestBody requestBody = RequestBody.create(mediaType, checkoutRequestJsonAdapter.toJson(checkoutRequest));
            String API_SANDBOX_URL = "https://sandbox.usekamba.com/v1/checkouts";
            URL = API_SANDBOX_URL.replace("/checkouts", "");
            getTimeStamp();
            String conanicalString = generateConanicalString(checkoutRequest);
            byte[] digest = HmacUtils.hmacSha1(conanicalString, clientConfig.getSecretKey());
            Log.d("MainActivity", digest.toString());
            Request.Builder builder = new Request.Builder();
            Headers a = new Headers.Builder()
                    .add("Content-Type", "application/json;charset=utf-8")
                    .add("Signature", timeStamp + "." + digest.toString())
                    .build();
            builder.url(API_SANDBOX_URL);
            builder.headers(a);
            builder.post(requestBody);
            request = builder.build();
        } else {
            RequestBody requestBody = RequestBody.create(mediaType, checkoutRequestJsonAdapter.toJson(checkoutRequest));
            String API_PRODUCTION_URL = "https://api.usekamba.com/v1/checkouts";
            URL = API_PRODUCTION_URL.replace("/checkouts", "");
            String conanicalString = generateConanicalString(checkoutRequest);
            String digest = HmacSha1.hmacSha1(clientConfig.getSecretKey(), conanicalString);
            String hex = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hex = Base64.getEncoder().encodeToString(digest.getBytes());
            } else{
               hex = android.util.Base64.encodeToString(digest.getBytes(), android.util.Base64.DEFAULT);
            }
            Log.d("MainActivity", hex);
            String signature = timeStamp + "." + hex.replace("\n", "");
            Log.d("MainActivity", "Signature: " + signature);
            Request.Builder builder = new Request.Builder();
            Map<String, String> headers = new HashMap<>();
            headers.put("Merchant-ID", clientConfig.getMerchantId());
            headers.put("Content-Type", "application/json");
            headers.put("Signature", signature);
            Headers.of(headers);
            builder.url(API_PRODUCTION_URL);
            builder.headers(Headers.of(headers));
            builder.post(requestBody);
            request = builder.build();
        }
    }

    private void getTimeStamp() {
        this.timeStamp = DateTime.now(DateTimeZone.UTC).toString("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        Log.d("MainActivity", timeStamp);
    }

    private String hmacSha1(String conanicalString, String secretKey) {
        String result = null;
        try {
            byte[] keyBytes = secretKey.getBytes();
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(conanicalString.getBytes());
            byte[] hexBytes = new Hex().encode(rawHmac);
            result = new String(hexBytes, Charsets.UTF_8);
            Log.d("MainActivity", "MAC : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String createDigest(String body) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.reset();
        messageDigest.update(body.getBytes(Charsets.UTF_8));
        final byte[] resultByte = messageDigest.digest();
        final String result = Hex.encodeHexString(resultByte);
        return result;
    }

    private String generateConanicalString(CheckoutRequest checkoutRequest) {
        getTimeStamp();
        Log.d("MainActivity", checkoutRequestJsonAdapter.toJson(checkoutRequest));
        Log.d("MainActivity", "POST,application/json," + createDigest(checkoutRequestJsonAdapter.toJson(checkoutRequest)) + ",/v1/checkouts,");
        return "POST,application/json," + createDigest(checkoutRequestJsonAdapter.toJson(checkoutRequest)) + ",/v1/checkouts," + timeStamp;
    }

    @Override
    public CheckoutTransactionBuilder addCheckoutRequest(CheckoutRequest checkoutRequest) {
        this.checkoutRequest = checkoutRequest;
        return this;
    }

    @Override
    public CheckoutTransactionBuilder addClientConfig(ClientConfig clientConfig) {
        this.clientConfig = clientConfig;
        return this;
    }

    @Override
    public CheckoutTransaction build() {

            setUpRequestAuthorization(clientConfig);
            // this.checkoutRequest.setRedirectUrlSuccess(URL);
            CheckoutTransaction transaction = new CheckoutTransaction();
            transaction.setClient(client);
            transaction.setRequest(request);
            return transaction;

    }
}