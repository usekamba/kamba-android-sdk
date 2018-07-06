package com.usekamba.kambapaysdk.core.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class Merchant implements Serializable{

	@Json(name = "business_name")
	private String business_name;

	@Json(name = "phone_number")
	private String phone_number;

	@Json(name = "id")
	private String id;

	@Json(name = "email")
	private String email;

	public void setBusiness_name(String business_name){
		this.business_name = business_name;
	}

	public String getBusiness_name(){
		return business_name;
	}

	public void setPhone_number(String phone_number){
		this.phone_number = phone_number;
	}

	public String getPhone_number(){
		return phone_number;
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
			"business_name = '" + business_name + '\'' +
			",phone_number = '" + phone_number + '\'' +
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}