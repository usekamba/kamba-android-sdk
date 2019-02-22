package com.usekamba.kambapaysdk.core.model;

import android.util.Log;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class CheckoutResponse implements Serializable {

	@Json(name = "notes")
	private String notes;

	@Json(name = "expires_at")
	private String expiresAt;

	@Json(name = "total_amount")
	private Double totalAmount;

	@Json(name = "redirect_url_success")
	private String redirectUrlSuccess;

	@Json(name = "fee")
	private int fee;

	@Json(name = "merchant")
	private Merchant merchant;

	@Json(name = "qr_code")
	private String qrCode;

	@Json(name = "initial_amount")
	private Double initialAmount;

	@Json(name = "id")
	private String id;

	@Json(name = "transaction_type")
	private String transactionType;

	@Json(name = "status")
	private String status;

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setExpiresAt(String expiresAt){
		this.expiresAt = expiresAt;
	}

	public String getExpiresAt(){
		return expiresAt;
	}

	public void setTotalAmount(Double totalAmount){
		this.totalAmount = totalAmount;
	}

	public Double getTotalAmount(){
		return totalAmount;
	}

	public void setRedirectUrlSuccess(String redirectUrlSuccess){
		this.redirectUrlSuccess = redirectUrlSuccess;
	}

	public String getRedirectUrlSuccess(){
		return redirectUrlSuccess;
	}

	public void setFee(int fee){
		this.fee = fee;
	}

	public int getFee(){
		return fee;
	}

	public void setMerchant(Merchant merchant){
		this.merchant = merchant;
	}

	public Merchant getMerchant(){
		return merchant;
	}

	public void setQrCode(String qrCode){
		this.qrCode = qrCode;
	}

	public String getQrCode(){
		Log.d("MainActivity", getRedirectUrlSuccess() + "/pay?chID=" + getId());
		return getRedirectUrlSuccess() + "/pay?chID=" + getId();
	}

	public void setInitialAmount(Double initialAmount){
		this.initialAmount = initialAmount;
	}

	public Double getInitialAmount(){
		return initialAmount;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"CheckoutResponse{" + 
			"notes = '" + notes + '\'' + 
			",expires_at = '" + expiresAt + '\'' + 
			",total_amount = '" + totalAmount + '\'' + 
			",redirect_url_success = '" + redirectUrlSuccess + '\'' + 
			",fee = '" + fee + '\'' + 
			",merchant = '" + merchant + '\'' + 
			",qr_code = '" + qrCode + '\'' + 
			",initial_amount = '" + initialAmount + '\'' + 
			",id = '" + id + '\'' + 
			",transaction_type = '" + transactionType + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}