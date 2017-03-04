package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.viewbean.CustomerSearchBean;

public interface CustomerService {
	public List<CustomerDto> getCustomersByIdNo(String idNo) throws BusinessException;
	public ContractResponseBody buildContractResponse(List<CustomerDto> dtos);
	public List<CustomerDto> getCustomersByIdNoOrName(String idNoOrName, Integer companyId) throws BusinessException;
	public List<CustomerDto> filterCustomerDtoInOtherCompany(List<CustomerDto> customers, Integer companyId) throws BusinessException;
	public List<CustomerDto> filterCustomerDtoInCompany(List<CustomerDto> customers, Integer companyId) throws BusinessException;
	
}
