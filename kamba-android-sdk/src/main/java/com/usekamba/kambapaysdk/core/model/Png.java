package com.usekamba.kambapaysdk.core.model;

import java.io.Serializable;
import java.util.List;
import com.squareup.moshi.Json;

public class Png implements Serializable {

	@Json(name = "metadata")
	private Metadata metadata;

	@Json(name = "pixels")
	private List<Long> pixels;

	@Json(name = "width")
	private int width;

	@Json(name = "height")
	private int height;

	public void setMetadata(Metadata metadata){
		this.metadata = metadata;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public void setPixels(List<Long> pixels){
		this.pixels = pixels;
	}

	public List<Long> getPixels(){
		return pixels;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public int getWidth(){
		return width;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getHeight(){
		return height;
	}

	@Override
 	public String toString(){
		return 
			"Png{" + 
			"metadata = '" + metadata + '\'' + 
			",pixels = '" + pixels + '\'' + 
			",width = '" + width + '\'' + 
			",height = '" + height + '\'' + 
			"}";
		}
}