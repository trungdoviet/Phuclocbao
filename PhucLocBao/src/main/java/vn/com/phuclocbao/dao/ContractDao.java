package vn.com.phuclocbao.dao;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;

public interface ContractDao {
	public Contract persist( Contract entity) throws BusinessException;
	public Contract merge( Contract entity) throws BusinessException;
	public List<Contract> mergeList( List<Contract> entity) throws BusinessException;
	public Contract findById(Integer id) throws BusinessException;
	public List<Contract> getContractByStatusAndCompanyId(ContractStatusType state, Integer id) throws BusinessException;
	public Double sumContractByDateRangeAndStatusAndCompanyId(ContractStatusType state, Integer id, Date maxDate, Date minDate) throws BusinessException;
	public List<Contract> getNotifiedContractBySpecificDateAndCompanyId(Date targetDate, Integer companyId) throws BusinessException;
	public Contract findById(Integer id, Integer companyId) throws BusinessException;
	public List<Contract> getContractByStateAndCompanyAndCustomerName(ContractStatusType state, Integer id, String customerName) throws BusinessException;
	public Long countNotifiedContractBySpecificDateAndCompanyId(Date targetDate, Integer companyId) throws BusinessException;
	public Long countContractByStatusAndCompanyId(ContractStatusType state, Integer id) throws BusinessException;
	public Double sumContractValueByStatusAndCompanyId(ContractStatusType state, Integer id) throws BusinessException;
	public Long countContractByCompanyId(Integer companyId) throws BusinessException;
	public int updateBadContract(Integer companyId) throws BusinessException;
}
