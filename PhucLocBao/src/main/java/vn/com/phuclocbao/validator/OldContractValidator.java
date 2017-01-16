package vn.com.phuclocbao.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.LoginBean;

@Component
public class OldContractValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		 return ContractBean.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//Validate customer
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.phone", "error.requiredField", "This field is required.");
        
	}

}
