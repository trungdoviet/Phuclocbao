package vn.com.phuclocbao.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.viewbean.LoginBean;

@Component
public class LoginUserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return LoginBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username", "Username là bắt buộc.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password", "Password là bắt buộc.");
	}

}
