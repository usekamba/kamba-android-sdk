package com.usekamba.kambapaysdk.core.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class CheckoutResponse implements Serializable{

	@Json(name = "notes")
	private String notes;

	@Json(name = "fee")
	private String fee;

	@Json(name = "channel")
	private String channel;

	@Json(name = "merchant")
	private Merchant merchant;

	@Json(name = "created_at")
	private String created_at;

	@Json(name = "initial_amount")
	private String initial_amount;

	@Json(name = "transaction_type")
	private String transaction_type;

	@Json(name = "updated_at")
	private String updated_at;

	@Json(name = "total_amount")
	private String total_amount;

	@Json(name = "redirect_url_success")
	private String redirect_url_success;

	@Json(name = "qr_code")
	private QrCode qr_code;

	@Json(name = "currency")
	private String currency;

	@Json(name = "id")
	private String id;

	@Json(name = "payment_method")
	private String payment_method;

	@Json(name = "status")
	private String status;

	public void setNotes(String notes){
		this.notes = notes;
	}

	public String getNotes(){
		return notes;
	}

	public void setFee(String fee){
		this.fee = fee;
	}

	public String getFee(){
		return fee;
	}

	public void setChannel(String channel){
		this.channel = channel;
	}

	public String getChannel(){
		return channel;
	}

	public void setMerchant(Merchant merchant){
		this.merchant = merchant;
	}

	public Merchant getMerchant(){
		return merchant;
	}

	public void setCreatedAt(String created_at){
		this.created_at = created_at;
	}

	public String getCreatedAt(){
		return created_at;
	}

	public void setInitialAmount(String initial_amount){
		this.initial_amount = initial_amount;
	}

	public String getInitialAmount(){
		return initial_amount;
	}

	public void setTransactionType(String transaction_type){
		this.transaction_type = transaction_type;
	}

	public String getTransactionType(){
		return transaction_type;
	}

	public void setUpdatedAt(String updated_at){
		this.updated_at = updated_at;
	}

	public String getUpdatedAt(){
		return updated_at;
	}

	public void setTotalAmount(String total_amount){
		this.total_amount = total_amount;
	}

	public String getTotalAmount(){
		return total_amount;
	}

	public void setRedirectUrlSuccess(String redirect_url_success){
		this.redirect_url_success = redirect_url_success;
	}

	public String getRedirectUrlSuccess(){
		return redirect_url_success;
	}

	public void setQrCode(QrCode qr_code){
		this.qr_code = qr_code;
	}

	public QrCode getQrCode(){
		return qr_code;
	}

	public void setCurrency(String currency){
		this.currency = currency;
	}

	public String getCurrency(){
		return currency;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPaymentMethod(String payment_method){
		this.payment_method = payment_method;
	}

	public String getPaymentMethod(){
		return payment_method;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
	public String toString() {
		return "CheckoutResponse{" +
				"notes='" + notes + '\'' +
				", fee='" + fee + '\'' +
				", channel='" + channel + '\'' +
				", merchant=" + merchant +
				", created_at='" + created_at + '\'' +
				", initial_amount='" + initial_amount + '\'' +
				", transaction_type='" + transaction_type + '\'' +
				", updated_at='" + updated_at + '\'' +
				", total_amount='" + total_amount + '\'' +
				", redirect_url_success='" + redirect_url_success + '\'' +
				", qr_code=" + qr_code +
				", currency='" + currency + '\'' +
				", id='" + id + '\'' +
				", payment_method='" + payment_method + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}