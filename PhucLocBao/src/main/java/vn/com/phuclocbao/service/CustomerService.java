package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface CustomerService {
	public List<CustomerDto> getCustomersByIdNo(String idNo) throws BusinessException;
	public ContractResponseBody buildContractResponse(List<CustomerDto> dtos);
}
