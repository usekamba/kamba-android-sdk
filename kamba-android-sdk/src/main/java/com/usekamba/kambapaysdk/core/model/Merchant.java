package com.usekamba.kambapaysdk.core.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Merchant implements Serializable {

	@Json(name = "business_name")
	private String businessName;

	@Json(name = "phone_number")
	private String phoneNumber;

	@Json(name = "id")
	private String id;

	@Json(name = "email")
	private String email;

	public void setBusinessName(String businessName){
		this.businessName = businessName;
	}

	public String getBusinessName(){
		return businessName;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Merchant{" + 
			"business_name = '" + businessName + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}