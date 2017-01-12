package vn.com.phuclocbao.dao;

import java.util.List;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;

public interface ContractDao {
	public Contract persist( Contract entity) throws BusinessException;
	public Contract merge( Contract entity) throws BusinessException;
	public Contract findById(Integer id) throws BusinessException;
	public List<Contract> getContractByStatusAndCompanyId(ContractStatusType state, Integer id) throws BusinessException;
}
