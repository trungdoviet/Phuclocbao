package vn.com.phuclocbao.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.viewbean.CompanyFinancialBean;

@Component
public class CompanyFinancialValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return CompanyFinancialBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Validate customer
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company.name", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company.phoneNumber", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company.city", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company.address", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "company.startDate", "error.requiredField", "This field is required.");
        
	}

}
