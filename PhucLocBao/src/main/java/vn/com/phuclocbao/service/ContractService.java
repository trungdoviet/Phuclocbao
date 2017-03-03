package vn.com.phuclocbao.service;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.view.ContractView;
import vn.com.phuclocbao.viewbean.GeneralView;
import vn.com.phuclocbao.viewbean.ManageContractBean;
import vn.com.phuclocbao.viewbean.NotificationContractBean;
import vn.com.phuclocbao.vo.UserActionParamVO;

public interface ContractService {
	public boolean saveNewContract(ContractDto contractDto, UserActionParamVO userActionParam) throws BusinessException;
	public ContractView findContractById(Integer id) throws BusinessException;
	public List<ContractDto> findContractsByStateAndId(ContractStatusType state, Integer companyId) throws BusinessException;
	public List<ContractDto> findContractsByStateAndIdAndCustomerName(ContractStatusType state, Integer companyId, String customerName) throws BusinessException;
	public ManageContractBean buildManageContractBean(List<ContractDto> dtos);
	public ManageContractBean buildManageOldContractBean(List<ContractDto> dtos);
	public ManageContractBean buildManageBadContractBean(List<ContractDto> dtos);
	public ContractDto findContractDtoById(Integer id, Integer companyId) throws BusinessException;
	public ContractDto updateContractInPaidTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException;
	public ContractDto updateContractInPayOffTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException;
	public ContractDto updateAsDraftContractInPayOffTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException;
	public ContractDto updateOldContract(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException;
	public List<ContractDto> getNotifiedContractBySpecificDateAndCompanyId(Date targetDate, Integer companyId) throws BusinessException;
	public List<NotificationContractBean> convertToNotificationBeans(Date selectedDate, List<ContractDto> contracts) throws BusinessException;
	public GeneralView collectStatistic(Integer companyId) throws BusinessException;
	public StatisticInfo collectProfitStatistic(Integer companyId, int year) throws BusinessException;
	public List<StatisticInfo> collectAllProfitStatistic(int year) throws BusinessException;
	public int updateBadContract(Integer companyId) throws BusinessException;
}
