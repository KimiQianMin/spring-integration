package com.tech.zuul.security.validate.code;

import com.tech.zuul.ZuulConstants;

public enum ValidateCodeType {

	SMS {
		@Override
		public String getParamNameOnValidate() {
			return ZuulConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	},

	IMAGE {
		@Override
		public String getParamNameOnValidate() {
			return ZuulConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
	};

	public abstract String getParamNameOnValidate();

}
