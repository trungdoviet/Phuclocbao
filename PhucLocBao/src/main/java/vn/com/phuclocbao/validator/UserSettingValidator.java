package vn.com.phuclocbao.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.PasswordHashing;
import vn.com.phuclocbao.viewbean.UserSettingBean;

@Component
public class UserSettingValidator implements Validator {
	@Autowired
	@Qualifier("emailValidator")
	EmailValidator emailValidator;
	@Override
	public boolean supports(Class<?> clazz) {
		 return UserSettingBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Validate customer
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.fullname", "error.requiredField", "This field is required.");
        
        UserSettingBean usBean = (UserSettingBean) target;
        if(StringUtils.isNotEmpty(usBean.getUser().getEmail()) && !emailValidator.valid(usBean.getUser().getEmail())){
        	errors.rejectValue("user.email", "error.emailInvalid", "Email is not valid");
        }
        
        if(StringUtils.isNotEmpty(usBean.getOldPassword())){
        	try {
				String hashing = PasswordHashing.hashMD5(usBean.getOldPassword());
				if(!hashing.equalsIgnoreCase(usBean.getUser().getPassword())){
	        		errors.rejectValue("oldPassword", "error.passwordIncorrect", "Password is invalid");
	        	}
			} catch (BusinessException e) {
				e.printStackTrace();
			}
        	
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "error.requiredField", "This field is required.");
        	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retypePassword", "error.requiredField", "This field is required.");
        	 
        	if(StringUtils.isNotEmpty(usBean.getNewPassword()) && StringUtils.isNotEmpty(usBean.getRetypePassword())){
        		if(usBean.getNewPassword().length() < 6){
        			 errors.rejectValue("newPassword", "error.passwordTooShort", "Password is too short.");
        		}else {
	        		 if(!usBean.getNewPassword().equalsIgnoreCase(usBean.getRetypePassword())){
	        			 errors.rejectValue("retypePassword", "error.passwordIsNotMatch", "Password does not match.");
	        		 }
        		}
        	}
        }
	}

}
