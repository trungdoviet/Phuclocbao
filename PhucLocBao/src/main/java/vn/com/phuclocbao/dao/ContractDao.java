package vn.com.phuclocbao.dao;

import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.exception.BusinessException;

public interface ContractDao {
	public Contract persist( Contract entity) throws BusinessException;
	public Contract merge( Contract entity) throws BusinessException;
}
