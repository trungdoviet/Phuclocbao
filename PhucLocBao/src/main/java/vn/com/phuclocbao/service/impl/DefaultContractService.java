package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.ContractSeverity;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.PaymentHistoryUtil;
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
	
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;
	
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
				 PaymentHistory paidHistory = PaymentHistoryUtil.createNewHistory(contract,contractDto.getCompany().getId(), PaymentHistoryType.RENTING_NEW_MOTOBIKE, 0D, StringUtils.EMPTY);
				Contract persistedObject = contractDao.merge(contract);
				if(persistedObject != null){
					Double totalFunding = company.getTotalFund() - contract.getTotalAmount();
					company.setTotalFund(totalFunding);
					companyDao.merge(company);
					paymentHistoryDao.persist(paidHistory);
				}
				return Long.valueOf(persistedObject.getId());
			}
			return null;
		}

		private void buildNotifiedDate(ContractDto contractDto) {
			if(CollectionUtils.isNotEmpty(contractDto.getPaymentSchedules())){
				contractDto.getPaymentSchedules().stream()
				.filter(item -> ConstantVariable.NO_OPTION.equalsIgnoreCase(item.getFinish()))
				.forEach(item ->{
					Date notifiedDate = DateTimeUtil.addMoreDate(item.getExpectedPayDate(), -contractDto.getPeriodOfPayment());
					Date currentDate = DateTimeUtil.getCurrentDateWithoutTime();
					if(notifiedDate.compareTo(currentDate) < 0){
						item.setNotifiedDate(currentDate);
						
					} else {
						item.setNotifiedDate(notifiedDate);
					}
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
	
	public PaymentHistoryDao getPaymentHistoryDao() {
		return paymentHistoryDao;
	}
	public void setPaymentHistoryDao(PaymentHistoryDao paymentHistoryDao) {
		this.paymentHistoryDao = paymentHistoryDao;
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
					
					 Double totalPaidCost = 0D;
					 long numberOfOldPaidTime = contract.getPaymentSchedules().stream().filter(item-> item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).count();
					 long numberOfNewPaidTime  = dto.getPaymentSchedules().stream().filter(item-> item.getFinish().equalsIgnoreCase(ConstantVariable.YES_OPTION)).count();
					 totalPaidCost = (numberOfNewPaidTime - numberOfOldPaidTime) * contract.getPeriodOfPayment() * contract.getFeeADay();
					 ContractConverter.getInstance().updateContractInPaidTime(dto, contract);
					 PaymentHistory paidHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.RENTING_COST, totalPaidCost, StringUtils.EMPTY);
					 ContractDto updatedContract= ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					 if(updatedContract != null){
						 CompanyEntity company = companyDao.findById(dto.getCompany().getId());
						 if(company != null){
								Double totalFunding = company.getTotalFund() + totalPaidCost;
								company.setTotalFund(totalFunding);
								companyDao.merge(company);
						} else {
							logger.error("Can not update contract to company because company not found: " + dto.getId());
							throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
						}
						 
						paymentHistoryDao.persist(paidHistory);
					 }
					 return updatedContract;
					
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
					 PaymentHistory paidHistory = PaymentHistoryUtil.createNewHistory(contract,dto.getCompany().getId(), PaymentHistoryType.PAYOFF, 0D, StringUtils.EMPTY);
					 ContractDto updatedContract = ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					 if(updatedContract != null){
						 CompanyEntity company = companyDao.findById(dto.getCompany().getId());
						 if(company != null){
								Double totalFunding = company.getTotalFund() + contract.getTotalAmount();
								company.setTotalFund(totalFunding);
								companyDao.merge(company);
						} else {
							logger.error("Can not update contract to company because company not found: " + dto.getId());
							throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
						}
						paymentHistoryDao.persist(paidHistory);
					 }
					 return updatedContract;
					
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
					List<PaymentHistory> paymentHistories = new ArrayList<>();
					if(isIncreasingDebt(customerDebt)){
						PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_DEBT, customerDebt, StringUtils.EMPTY);
						paymentHistories.add(customerDebtHistory);
					} else if(isDecreasingDebt(customerDebt)){
						PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_PAY_DEBT, customerDebt, StringUtils.EMPTY);
						paymentHistories.add(customerDebtHistory);
					}
					
					if(isIncreasingDebt(companyDebt)){
						PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_DEBT, companyDebt, StringUtils.EMPTY);
						paymentHistories.add(companyDebtHistory);
					} else if(isDecreasingDebt(companyDebt)){
						PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_PAY_DEBT, companyDebt, StringUtils.EMPTY);
						paymentHistories.add(companyDebtHistory);
					}
					
					 ContractDto updatedContract = ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					 if(updatedContract != null && CollectionUtils.isNotEmpty(paymentHistories)){
						 paymentHistoryDao.persistList(paymentHistories);
					 }
					 return updatedContract;
					
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
			PaymentScheduleDto latestPaid = PlbUtil.getNearliestUnpaid(item.getPaymentSchedules());
			if(DateTimeUtil.daysBetweenDates(DateTimeUtil.getCurrentDateWithoutTime(), item.getExpireDate()) > 0){
				Date calculatedDate = latestPaid != null ? latestPaid.getNotifiedDate():item.getPaymentSchedules().get(0).getNotifiedDate();
				
				bean.setStage(ProcessStaging.PAID.getName());
				bean.setAmountDays(DateTimeUtil.daysBetweenDates(calculatedDate, selectedDate));
				bean.setPaidDate(calculatedDate);
			} else {
				bean.setStage(ProcessStaging.PAYOFF.getName());
				bean.setAmountDays(DateTimeUtil.daysBetweenDates(item.getExpireDate(), selectedDate));
				bean.setPaidDate(item.getExpireDate());
			}
			if(bean.getAmountDays() <0){
				bean.setAmountDays(bean.getAmountDays() * -1);
			}
			bean.setSeverity(ContractSeverity.getSeverityByAmountDays(bean.getAmountDays()).getName());
			return bean;
		}).sorted((o1,o2) -> o1.getPaidDate().compareTo(o2.getPaidDate()))
		.collect(Collectors.toList());
		return ncBeans;
	}
}
