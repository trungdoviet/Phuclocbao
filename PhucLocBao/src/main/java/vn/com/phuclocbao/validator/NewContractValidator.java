package vn.com.phuclocbao.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.LoginBean;

@Component
public class NewContractValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return ContractBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Validate customer
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.idNo", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.name", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.phone", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.address", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.province", "error.requiredField", "This field is required.");
	}

}
