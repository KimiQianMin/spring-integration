package com.tech.zuul.security.validate.code;

import java.io.IOException;

public interface ValidateCodeGenerator {
	
	ImageCode createImageCode() throws IOException;

}
