package com.usekamba.kambapaysdk.core.model;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class QrCode implements Serializable  {

	@Json(name = "string")
	private String string;

	@Json(name = "svg")
	private String svg;

	@Json(name = "png")
	private Png png;

	@Json(name = "html")
	private String html;

	public void setString(String string){
		this.string = string;
	}

	public String getString(){
		return string;
	}

	public void setSvg(String svg){
		this.svg = svg;
	}

	public String getSvg(){
		return svg;
	}

	public void setPng(Png png){
		this.png = png;
	}

	public Png getPng(){
		return png;
	}

	public void setHtml(String html){
		this.html = html;
	}

	public String getHtml(){
		return html;
	}

	@Override
 	public String toString(){
		return 
			"QrCode{" + 
			"string = '" + string + '\'' + 
			",svg = '" + svg + '\'' + 
			",png = '" + png + '\'' + 
			",html = '" + html + '\'' + 
			"}";
		}
}