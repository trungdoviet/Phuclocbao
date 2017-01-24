package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.ContractConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.ContractDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.enums.ContractHistoryType;
import vn.com.phuclocbao.enums.ContractSeverity;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.ContractHistoryUtil;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.PlbUtil;
import vn.com.phuclocbao.view.ContractView;
import vn.com.phuclocbao.viewbean.ManageContractBean;
import vn.com.phuclocbao.viewbean.NotificationContractBean;
@Service
public class DefaultContractService extends BaseService implements ContractService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultContractService.class);
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ContractDao contractDao;
	
	@Transactional
	@Override
	public boolean saveNewContract(ContractDto contractDto) throws BusinessException {
	  Long id = methodWrapper(new PersistenceExecutable<Long>() {

		@Override
		public Long execute() throws BusinessException, ClassNotFoundException, IOException {
			Contract contract = new Contract();
			if(contractDto.getCompany() != null){
				buildNotifiedDate(contractDto);
				CompanyEntity company = companyDao.findById(contractDto.getCompany().getId());
				if(company == null){
					logger.error("Company which has id "+contractDto.getCompany().getId() + " can not be found");
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
				contract = ContractConverter.getInstance().toNewContract(contractDto, contract);
				mapReference(contract, company);
				ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.RENTING_NEW_MOTOBIKE, 0D, StringUtils.EMPTY);
				Contract persistedObject = contractDao.merge(contract);
				return Long.valueOf(persistedObject.getId());
			}
			return null;
		}

		private void buildNotifiedDate(ContractDto contractDto) {
			if(CollectionUtils.isNotEmpty(contractDto.getPaymentSchedules())){
				contractDto.getPaymentSchedules().stream()
				.filter(item -> ConstantVariable.NO_OPTION.equalsIgnoreCase(item.getFinish()))
				.forEach(item ->{
					item.setNotifiedDate(DateTimeUtil.addMoreDate(item.getExpectedPayDate(), -contractDto.getPeriodOfPayment()));
				});
			}
		}

		private void mapReference(Contract contract, CompanyEntity company) {
			contract.setCompany(company);
			contract.getCompany().getContracts().add(contract);
			contract.getCustomer().setContract(contract);
			contract.getOwner().setContract(contract);
			if(CollectionUtils.isNotEmpty(contract.getPaymentSchedules())){
				contract.getPaymentSchedules().forEach(item -> item.setContract(contract));
			}
		}
	  });
		return Objects.nonNull(id);
	}

	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	public CompanyDao getCompanyDao() {
		return companyDao;
	}
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	public ContractDao getContractDao() {
		return contractDao;
	}
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	@Override
	public ContractView findContractById(Integer id) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractView>() {
			@Override
			public ContractView execute() throws BusinessException, ClassNotFoundException, IOException {
				Contract contract = contractDao.findById(id);
				if(contract != null) {
					return ContractConverter.getInstance().toContractView(contract);
				}
				return null;
			}
		});
	}
	@Override
	public List<ContractDto> findContractsByStateAndId(ContractStatusType state, Integer companyId)
			throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<ContractDto>>() {
			@Override
			public List<ContractDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<Contract> contracts = contractDao.getContractByStatusAndCompanyId(state, companyId);
				if(CollectionUtils.isNotEmpty(contracts)) {
					List<ContractDto> result = ContractConverter.getInstance().toDtosWithCustomer(contracts);
					return result.stream().sorted((o1,o2) -> o2.getStartDate().compareTo(o1.getStartDate())).collect(Collectors.toList());
				}
				return null;
			}
		});
	}
	public ManageContractBean buildManageContractBean(List<ContractDto> dtos){
		ManageContractBean mcb = new ManageContractBean();
		if(CollectionUtils.isNotEmpty(dtos)){
			mcb.setContracts(dtos);
			mcb.setInProgressContract(dtos.size());
			mcb.setTotalContract(dtos.size());
			mcb.setTotalFeeADay(dtos.stream().map(item ->item.getFeeADay()).reduce(0D, Double::sum));
			mcb.setTotalPayoffAmmount(dtos.stream().map(item -> item.getTotalAmount()).reduce(0D, (x,y) -> x + y));
			logger.info("total:" + mcb.getTotalPayoffAmmount() + "-feeADay:" + mcb.getTotalFeeADay());
		}
		return mcb;
	}
	public ManageContractBean buildManageOldContractBean(List<ContractDto> dtos){
		ManageContractBean mcb = new ManageContractBean();
		if(CollectionUtils.isNotEmpty(dtos)){
			mcb.setContracts(dtos);
			mcb.setFinishContract(dtos.size());
			mcb.setTotalAlreadyPayoffAmmount(dtos.stream().map(item -> item.getTotalAmount()).reduce(0D, (x,y) -> x + y));
			dtos.forEach(item -> item.setTotalContractDays(DateTimeUtil.daysBetweenDates(item.getStartDate(), item.getPayoffDate())));
		}
		return mcb;
	}
	@Override
	public ContractDto findContractDtoById(Integer id, Integer companyId) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractDto>() {

			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(id, companyId);
					return ContractConverter.getInstance().toContract(new ContractDto(), contract);
					
				} catch (NoResultException nre){
					logger.error("Contract not found: id:" + id +" - companyid:" + id);
					logger.error(nre);
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
			}
		
		});
	}
	@Transactional
	@Override
	public ContractDto updateContractInPaidTime(ContractDto dto) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractDto>() {

			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					
					 Double totalPaidCost = 10D;
					 long numberOfOldPaidTime = contract.getPaymentSchedules().stream().filter(item-> item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).count();
					 long numberOfNewPaidTime  = dto.getPaymentSchedules().stream().filter(item-> item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).count();
					 totalPaidCost = (numberOfNewPaidTime - numberOfOldPaidTime) * contract.getPeriodOfPayment() * contract.getFeeADay();
					 ContractConverter.getInstance().updateContractInPaidTime(dto, contract);
					 ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.RENTING_COST, totalPaidCost, StringUtils.EMPTY);
					 return ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					
				} catch (Exception nre){
					logger.error("Can not update contract: id:" + dto.getId() +" - companyid:" + dto.getCompany().getId());
					logger.error(nre);
					throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
				}
			}
		
		});
	}
	@Transactional
	@Override
	public ContractDto updateContractInPayOffTime(ContractDto dto) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					
					 ContractConverter.getInstance().updateContractInPayOffTime(dto, contract);
					 ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.PAYOFF, 0D, StringUtils.EMPTY);
					 return ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					
				} catch (Exception nre){
					logger.error("Can not payoff contract: id:" + dto.getId() +" - companyid:" + dto.getCompany().getId());
					logger.error(nre);
					throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
				}
			}
		
		});
	}
	@Transactional
	@Override
	public ContractDto updateAsDraftContractInPayOffTime(ContractDto dto) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					Double customerDebt = dto.getCustomerDebt() - contract.getCustomerDebt();
					Double companyDebt = dto.getCompanyDebt() - contract.getCompanyDebt();
					ContractConverter.getInstance().updateAsDraftContractInPayOffTime(dto, contract);
					if(isIncreasingDebt(customerDebt)){
						ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.CUSTOMER_DEBT, customerDebt, StringUtils.EMPTY);
					} else if(isDecreasingDebt(customerDebt)){
						ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.CUSTOMER_PAY_DEBT, customerDebt, StringUtils.EMPTY);
					}
					
					if(isIncreasingDebt(companyDebt)){
						ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.COMPANY_DEBT, companyDebt, StringUtils.EMPTY);
					} else if(isDecreasingDebt(companyDebt)){
						ContractHistoryUtil.createNewHistory(contract, ContractHistoryType.COMPANY_PAY_DEBT, companyDebt, StringUtils.EMPTY);
					}
					
					 return ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					
				} catch (Exception nre){
					logger.error("Can not payoff contract: id:" + dto.getId() +" - companyid:" + dto.getCompany().getId());
					logger.error(nre);
					throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
				}
			}

			private boolean isDecreasingDebt(Double debt) {
				return debt < 0;
			}

			private boolean isIncreasingDebt(Double debt) {
				return debt > 0;
			}
		
		});
	}
	
	@Transactional
	@Override
	public ContractDto updateOldContract(ContractDto dto) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					ContractConverter.getInstance().updateOldContract(dto, contract);
					return ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					
				} catch (Exception nre){
					logger.error("Can not update contract: id:" + dto.getId() +" - companyid:" + dto.getCompany().getId());
					logger.error(nre);
					throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
				}
			}
		});
	}
	@Override
	public List<ContractDto> getNotifiedContractBySpecificDateAndCompanyId(Date targetDate, Integer companyId)
			throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<ContractDto>>() {
			@Override
			public List<ContractDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<Contract> contracts = contractDao.getNotifiedContractBySpecificDateAndCompanyId(targetDate, companyId);
				if(CollectionUtils.isNotEmpty(contracts)) {
					List<ContractDto> result = ContractConverter.getInstance().toDtosWithCustomerAndPayments(contracts);
					return result;
				}
				return null;
			}
		});
	}
	@Override
	public List<NotificationContractBean> convertToNotificationBeans(Date selectedDate, List<ContractDto> contracts)
			throws BusinessException {
		List<NotificationContractBean> ncBeans = contracts.stream().map(item -> {
			NotificationContractBean bean = new NotificationContractBean();
			bean.setContract(item);
			PaymentScheduleDto latestPaid = PlbUtil.getLatestPaid(item.getPaymentSchedules());
			if(latestPaid != null && DateTimeUtil.daysBetweenDates(item.getExpireDate(), selectedDate) != 0){
				bean.setStage(ProcessStaging.PAID.getName());
				bean.setAmountDays(DateTimeUtil.daysBetweenDates(latestPaid.getExpectedPayDate(), selectedDate));
				bean.setPaidDate(latestPaid.getExpectedPayDate());
			} else {
				bean.setStage(ProcessStaging.PAYOFF.getName());
				bean.setAmountDays(DateTimeUtil.daysBetweenDates(item.getExpireDate(), selectedDate));
				bean.setPaidDate(item.getExpireDate());
			}
			bean.setSeverity(ContractSeverity.getSeverityByAmountDays(bean.getAmountDays()).getName());
			return bean;
		}).sorted((o1,o2) -> o1.getPaidDate().compareTo(o2.getPaidDate()))
		.collect(Collectors.toList());
		return ncBeans;
	}
}
