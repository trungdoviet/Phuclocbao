package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.view.ContractView;
import vn.com.phuclocbao.viewbean.ManageContractBean;

public interface ContractService {
	public boolean saveNewContract(ContractDto contractDto) throws BusinessException;
	public ContractView findContractById(Integer id) throws BusinessException;
	public List<ContractDto> findContractsByStateAndId(ContractStatusType state, Integer companyId) throws BusinessException;
	public ManageContractBean buildManageContractBean(List<ContractDto> dtos);
	public ManageContractBean buildManageOldContractBean(List<ContractDto> dtos);
	public ContractDto findContractDtoById(Integer id, Integer companyId) throws BusinessException;
	public ContractDto updateContractInPaidTime(ContractDto dto) throws BusinessException;
	public ContractDto updateContractInPayOffTime(ContractDto dto) throws BusinessException;
	public ContractDto updateAsDraftContractInPayOffTime(ContractDto dto) throws BusinessException;
	public ContractDto updateOldContract(ContractDto dto) throws BusinessException;
}
