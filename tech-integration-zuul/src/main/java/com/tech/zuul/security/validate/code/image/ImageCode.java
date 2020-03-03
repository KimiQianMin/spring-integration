package com.tech.zuul.security.validate.code.image;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.tech.zuul.security.validate.code.ValidateCode;

public class ImageCode extends ValidateCode {

	private static final long serialVersionUID = 2268401857424847009L;

	private BufferedImage image;

	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code, expireTime);
		this.image = image;
	}

	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code, expireIn);
		this.image = image;
	}

	public ImageCode(String code, LocalDateTime expireTime) {
		super(code, expireTime);
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
