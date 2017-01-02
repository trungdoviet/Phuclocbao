package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface ContractService {
	public boolean saveNewContract(ContractDto contractDto) throws BusinessException;
}
