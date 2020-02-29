package com.tech.zuul.security.properties;

public class ImageCodeProperties {

	private int width = 100;
	private int height = 30;
	private int length = 4;
	private int expireIn = 60;
	private String imageCodeUrl = "/code/image";
	private String filterUrls = "/authentication/form";

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getImageCodeUrl() {
		return imageCodeUrl;
	}

	public void setImageCodeUrl(String imageCodeUrl) {
		this.imageCodeUrl = imageCodeUrl;
	}

	public String getFilterUrls() {
		return filterUrls;
	}

	public void setFilterUrls(String filterUrls) {
		this.filterUrls = filterUrls;
	}

}
