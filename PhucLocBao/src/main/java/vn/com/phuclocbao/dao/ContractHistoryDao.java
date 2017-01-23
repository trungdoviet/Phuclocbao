package vn.com.phuclocbao.dao;

import vn.com.phuclocbao.entity.ContractHistory;
import vn.com.phuclocbao.exception.BusinessException;

public interface ContractHistoryDao {
	public ContractHistory findById(Integer id, Integer companyId) throws BusinessException;
}
