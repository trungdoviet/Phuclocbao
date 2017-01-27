package vn.com.phuclocbao.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.util.MessageConstant;
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
		ContractBean contract = (ContractBean) target;
		ContractDto dto = contract.getContractDto();
		//Validate customer
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.idNo", "error.requiredField1", MessageConstant.MSG_REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.name", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.phone", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.address", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.customer.province", "error.requiredField", "This field is required.");
        //validate contract
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.totalAmount", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.feeADay", "error.requiredField", "This field is required.");
        if(Objects.isNull(dto.getTotalAmount())  || dto.getTotalAmount() <= 0 ) {
        	errors.rejectValue("contractDto.totalAmount", "error.mustbeBiggerThanZero");
        }
        
        if(Objects.isNull(dto.getFeeADay())  || dto.getFeeADay() <= 0 ) {
        	errors.rejectValue("contractDto.feeADay", "error.mustbeBiggerThanZero");
        }
        
        if(dto.getStartDate().compareTo(dto.getExpireDate()) > 0){
        	errors.rejectValue("contractDto.startDate", "error.startDateMustBeBeforeEndDate");
        	errors.rejectValue("contractDto.expireDate", "error.startDateMustBeBeforeEndDate");
        }
        
        //validate owner
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.owner.name", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.owner.transportType", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.owner.numberPlate", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.owner.chassisFrameNumber", "error.requiredField", "This field is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractDto.owner.chassisNumber", "error.requiredField", "This field is required.");
        
        
	}

}
