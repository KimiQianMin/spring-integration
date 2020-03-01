package com.tech.zuul.security.validate.code;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.tech.zuul.ZuulConstants;

@RestController
public class ValidateCodeController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;

	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("createCode is calling... ");

		ImageCode imageCode = imageCodeGenerator.createImageCode();
		ImageCode savedImageCode = new ImageCode(imageCode.getCode(), imageCode.getExpireTime());
		sessionStrategy.setAttribute(new ServletWebRequest(request), ZuulConstants.VALIDATE_CODE_IMAGE_CODE_SESSION_KEY,
				savedImageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

}
