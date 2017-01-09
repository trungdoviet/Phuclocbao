package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.view.ContractView;

public interface ContractService {
	public boolean saveNewContract(ContractDto contractDto) throws BusinessException;
	public ContractView findContractById(Integer id) throws BusinessException;
}
