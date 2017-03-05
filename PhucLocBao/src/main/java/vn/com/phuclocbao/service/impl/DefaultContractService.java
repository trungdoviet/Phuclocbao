package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.converter.ContractConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.ContractDao;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserHistoryDao;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.entity.PaymentSchedule;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.enums.ContractSeverity;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.enums.UserActionHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.PaymentHistoryUtil;
import vn.com.phuclocbao.util.PlbUtil;
import vn.com.phuclocbao.util.UserHistoryUtil;
import vn.com.phuclocbao.view.ContractView;
import vn.com.phuclocbao.viewbean.GeneralView;
import vn.com.phuclocbao.viewbean.ManageContractBean;
import vn.com.phuclocbao.viewbean.NotificationContractBean;
import vn.com.phuclocbao.vo.UserActionParamVO;
@Service
public class DefaultContractService extends BaseService implements ContractService {
	private static final int EXTRA_LACK_OF_PAYMENT_CONTRACT = 10;
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultContractService.class);
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ContractDao contractDao;
	
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;
	
	@Autowired
	private UserHistoryDao userHistoryDao;
	
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public boolean saveNewContract(ContractDto contractDto, UserActionParamVO userActionParam) throws BusinessException {
	  Long id = transactionWrapper(new PersistenceExecutable<Long>() {

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
				PaymentHistory rentingHistory = PaymentHistoryUtil.createNewHistory(contract,contractDto.getCompany().getId(), PaymentHistoryType.RENTING_NEW_MOTOBIKE, 0D, StringUtils.EMPTY);
				Contract persistedObject = contractDao.merge(contract);
				if(persistedObject != null){
					long finishPaymentAmount = countFinishPayments(contractDto.getPaymentSchedules());
					Double totalPaid = finishPaymentAmount * contract.getPeriodOfPayment() * contract.getFeeADay(); 
					Double totalFunding = company.getTotalFund() - contract.getTotalAmount() + totalPaid;
					company.setTotalFund(totalFunding);
					companyDao.merge(company);
					rentingHistory.setContractId(persistedObject.getId());
					paymentHistoryDao.persist(rentingHistory);
					if(totalPaid > 0){
						 PaymentHistory paidHistory = PaymentHistoryUtil.createNewHistory(persistedObject, contractDto.getCompany().getId(), PaymentHistoryType.RENTING_COST, totalPaid, StringUtils.EMPTY);
						 paidHistory.setContractId(persistedObject.getId());
						 paymentHistoryDao.persist(paidHistory);
					}
					
					UserHistory userHistory = UserHistoryUtil.createNewHistory(persistedObject, contractDto.getCompany().getId(), 
															persistedObject.getCompany().getName(), userActionParam.getUsername(), 
															UserActionHistoryType.NEW_CONTRACT, StringUtils.EMPTY);
					userHistoryDao.persist(userHistory);
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
	
	private long countFinishPayments(List<PaymentScheduleDto> payments){
		if(CollectionUtils.isNotEmpty(payments)){
			return payments.stream().filter(item -> ConstantVariable.YES_OPTION.equalsIgnoreCase(item.getFinish())).count();
		}
		return 0;
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
	
	public UserHistoryDao getUserHistoryDao() {
		return userHistoryDao;
	}
	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
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
					List<ContractDto> result = ContractConverter.getInstance().toDtosWithCustomerAndPayments(contracts);
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
			//logger.info("total:" + mcb.getTotalPayoffAmmount() + "-feeADay:" + mcb.getTotalFeeADay());
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
	public ManageContractBean buildManageBadContractBean(List<ContractDto> dtos) {
		ManageContractBean mcb = new ManageContractBean();
		if(CollectionUtils.isNotEmpty(dtos)){
			dtos.stream().forEach(item -> {
				PaymentScheduleDto lastPayment = PlbUtil.getLatestPaid(item.getPaymentSchedules());
				if(lastPayment != null){
					item.setLastPaidDate(lastPayment.getExpectedPayDate());
					item.setTotalLateDays(DateTimeUtil.daysBetweenDates(item.getLastPaidDate(), DateTimeUtil.getCurrentDateWithoutTime()));
				} else {
					item.setLastPaidDate(null);
					item.setTotalLateDays(DateTimeUtil.daysBetweenDates(item.getStartDate(), DateTimeUtil.getCurrentDateWithoutTime()));
				}
				
				item.setTotalUnpaidFee(item.getTotalLateDays() * item.getFeeADay() + item.getCustomerDebt() - item.getCompanyDebt());
			});
			mcb.setContracts(dtos);
			mcb.setBadContract(dtos.size());
			mcb.setTotalAmountFeeOfBadContract(dtos.stream().map(item ->item.getTotalUnpaidFee()).reduce(0D, Double::sum));
			mcb.setTotalAmountBadContract(dtos.stream().map(item -> item.getTotalAmount()).reduce(0D, (x,y) -> x + y));
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
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public ContractDto updateContractInPaidTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<ContractDto>() {

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
						UserActionHistoryType actionType = UserActionHistoryType.RENTING_COST;
						if(totalPaidCost == 0D){
							actionType = UserActionHistoryType.UPDATE_CONTRACT;
						}
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
								company.getName(), userActionParam.getUsername(), 
								actionType, StringUtils.EMPTY);
						userHistoryDao.persist(userHistory);
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
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public ContractDto updateContractInPayOffTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					Double customerDebt = dto.getCustomerDebt() - contract.getCustomerDebt();
					Double companyDebt = dto.getCompanyDebt() - contract.getCompanyDebt();
					Double profitToCompany = 0D;
					Double totalRefunding = 0D;
					List<PaymentHistory> paymentHistories = new ArrayList<>();
					List<UserHistory> userHistories = new ArrayList<>();
					
					 ContractConverter.getInstance().updateContractInPayOffTime(dto, contract);
					 PaymentHistory paidHistory = PaymentHistoryUtil.createNewHistory(contract,dto.getCompany().getId(), PaymentHistoryType.PAYOFF, 0D, StringUtils.EMPTY);
					 paymentHistories.add(paidHistory);
					 
					 ContractDto updatedContract = ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					 if(updatedContract != null){
						 CompanyEntity company = companyDao.findById(dto.getCompany().getId());
						 if(company != null){
								profitToCompany = contract.getTotalAmount();
								totalRefunding = calculateRefundWhenPayoff(updatedContract);
								profitToCompany -= totalRefunding;
								if(totalRefunding > 0 ){
									PaymentHistory refundingToCustomer = PaymentHistoryUtil.createNewHistory(contract,dto.getCompany().getId(), PaymentHistoryType.REFUNDING_FOR_CUSTOMER, totalRefunding, StringUtils.EMPTY);
									paymentHistories.add(refundingToCustomer);
								} else if(totalRefunding < 0){
									PaymentHistory refundingToCompany = PaymentHistoryUtil.createNewHistory(contract,dto.getCompany().getId(), PaymentHistoryType.REFUNDING_FOR_COMPANY, totalRefunding, StringUtils.EMPTY);
									paymentHistories.add(refundingToCompany);
								}
						} else {
							logger.error("Can not update contract to company because company not found: " + dto.getId());
							throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
						}
						 
						 if(isIncreasingDebt(customerDebt)){
								PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, 
																						dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_DEBT, 
																						customerDebt, StringUtils.EMPTY);
								paymentHistories.add(customerDebtHistory);
								UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
																			dto.getCompany().getName(), userActionParam.getUsername(), 
																			UserActionHistoryType.CUSTOMER_DEBT, StringUtils.EMPTY);
								userHistories.add(userHistory);
								profitToCompany += customerDebt;
							} else if(isDecreasingDebt(customerDebt)){
								PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_PAY_DEBT, customerDebt, StringUtils.EMPTY);
								paymentHistories.add(customerDebtHistory);
								UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
										dto.getCompany().getName(), userActionParam.getUsername(), 
										UserActionHistoryType.CUSTOMER_PAY_DEBT, StringUtils.EMPTY);
								userHistories.add(userHistory);
								profitToCompany -= customerDebt;
							} 
							
							if(isIncreasingDebt(companyDebt)){
								PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_DEBT, companyDebt, StringUtils.EMPTY);
								paymentHistories.add(companyDebtHistory);
								UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
										dto.getCompany().getName(), userActionParam.getUsername(), 
										UserActionHistoryType.COMPANY_DEBT, StringUtils.EMPTY);
								userHistories.add(userHistory);
								profitToCompany -= companyDebt;
							} else if(isDecreasingDebt(companyDebt)){
								PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_PAY_DEBT, companyDebt, StringUtils.EMPTY);
								paymentHistories.add(companyDebtHistory);
								UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
										dto.getCompany().getName(), userActionParam.getUsername(), 
										UserActionHistoryType.COMPANY_PAY_DEBT, StringUtils.EMPTY);
								userHistories.add(userHistory);
								profitToCompany += companyDebt;
							}
							if(profitToCompany != 0){
								Double totalFunding = company.getTotalFund() + profitToCompany;
								company.setTotalFund(totalFunding);
								companyDao.merge(company);
							}
						UserActionHistoryType actionType = UserActionHistoryType.PAYOFF_CONTRACT;
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, company.getId(), 
								company.getName(), userActionParam.getUsername(), 
								actionType, StringUtils.EMPTY);
						userHistories.add(userHistory);
						 if(updatedContract != null && CollectionUtils.isNotEmpty(paymentHistories)){
							 paymentHistoryDao.persistList(paymentHistories);
						 }
						 if(updatedContract != null && CollectionUtils.isNotEmpty(userHistories)){
							 userHistoryDao.persistList(userHistories);
						 }
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
	
	private Double calculateRefundWhenPayoff( ContractDto dto) {
		Double totalRefunding = 0D;
		PaymentScheduleDto latestPaid = PlbUtil.getLatestPaid(dto.getPaymentSchedules());
		Date targetDate = null;
		Date today = DateTimeUtil.getCurrentDateWithoutTime();
		if(latestPaid != null){
			targetDate = latestPaid.getExpectedPayDate();
		} else {
			targetDate = dto.getStartDate();
		}
		long substractionDays = DateTimeUtil.daysBetweenDates(today, targetDate);
		totalRefunding = (substractionDays * dto.getFeeADay());
		return totalRefunding;
	}
	
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public ContractDto updateAsDraftContractInPayOffTime(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					Double customerDebt = dto.getCustomerDebt() - contract.getCustomerDebt();
					Double companyDebt = dto.getCompanyDebt() - contract.getCompanyDebt();
					ContractConverter.getInstance().updateAsDraftContractInPayOffTime(dto, contract);
					List<PaymentHistory> paymentHistories = new ArrayList<>();
					List<UserHistory> userHistories = new ArrayList<>();
					CompanyEntity company = companyDao.findById(dto.getCompany().getId());
					if(company != null){
						dto.getCompany().setName(company.getName());
					}
					Double profitToCompany = 0D;
					if(isIncreasingDebt(customerDebt)){
						PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, 
																				dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_DEBT, 
																				customerDebt, StringUtils.EMPTY);
						paymentHistories.add(customerDebtHistory);
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
																	dto.getCompany().getName(), userActionParam.getUsername(), 
																	UserActionHistoryType.CUSTOMER_DEBT, StringUtils.EMPTY);
						userHistories.add(userHistory);
						profitToCompany -= customerDebt;
					} else if(isDecreasingDebt(customerDebt)){
						PaymentHistory customerDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.CUSTOMER_PAY_DEBT, customerDebt, StringUtils.EMPTY);
						paymentHistories.add(customerDebtHistory);
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
								dto.getCompany().getName(), userActionParam.getUsername(), 
								UserActionHistoryType.CUSTOMER_PAY_DEBT, StringUtils.EMPTY);
						userHistories.add(userHistory);
						profitToCompany += customerDebt;
					} 
					
					if(isIncreasingDebt(companyDebt)){
						PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_DEBT, companyDebt, StringUtils.EMPTY);
						paymentHistories.add(companyDebtHistory);
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
								dto.getCompany().getName(), userActionParam.getUsername(), 
								UserActionHistoryType.COMPANY_DEBT, StringUtils.EMPTY);
						userHistories.add(userHistory);
						profitToCompany += companyDebt;
					} else if(isDecreasingDebt(companyDebt)){
						PaymentHistory companyDebtHistory = PaymentHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), PaymentHistoryType.COMPANY_PAY_DEBT, companyDebt, StringUtils.EMPTY);
						paymentHistories.add(companyDebtHistory);
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
								dto.getCompany().getName(), userActionParam.getUsername(), 
								UserActionHistoryType.COMPANY_PAY_DEBT, StringUtils.EMPTY);
						userHistories.add(userHistory);
						profitToCompany -= companyDebt;
					}
					
					if(isKeepDebt(companyDebt) && isKeepDebt(customerDebt)){
						UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
								dto.getCompany().getName(), userActionParam.getUsername(), 
								UserActionHistoryType.UPDATE_CONTRACT, StringUtils.EMPTY);
						userHistories.add(userHistory);
					} else if(profitToCompany != 0){
						 if(company != null){
								Double totalFunding = company.getTotalFund() + profitToCompany;
								company.setTotalFund(totalFunding);
								companyDao.merge(company);
						} else {
							logger.error("Can not update contract to company because company not found: " + dto.getId());
							throw new BusinessException(PLBErrorCode.CAN_NOT_UPDATE_DATA.name());
						}
					}
					
					 ContractDto updatedContract = ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					 if(updatedContract != null && CollectionUtils.isNotEmpty(paymentHistories)){
						 paymentHistoryDao.persistList(paymentHistories);
					 }
					 if(updatedContract != null && CollectionUtils.isNotEmpty(userHistories)){
						 userHistoryDao.persistList(userHistories);
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
	private boolean isDecreasingDebt(Double debt) {
		return debt < 0;
	}

	private boolean isIncreasingDebt(Double debt) {
		return debt > 0;
	}
	
	private boolean isKeepDebt(Double debt){
		return debt == 0;
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public ContractDto updateOldContract(ContractDto dto, UserActionParamVO userActionParam) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<ContractDto>() {
			@Override
			public ContractDto execute() throws BusinessException, ClassNotFoundException, IOException {
				try {
					Contract contract = contractDao.findById(dto.getId(), dto.getCompany().getId());
					ContractConverter.getInstance().updateOldContract(dto, contract);
					ContractDto updatedContract = ContractConverter.getInstance().toContract(new ContractDto(), contractDao.merge(contract));
					if(updatedContract != null){
						CompanyEntity company = companyDao.findById(dto.getCompany().getId());
						if(company != null){
							UserHistory userHistory = UserHistoryUtil.createNewHistory(contract, dto.getCompany().getId(), 
									company.getName(), userActionParam.getUsername(), 
									UserActionHistoryType.UPDATE_CONTRACT, StringUtils.EMPTY);
							userHistoryDao.persist(userHistory);
						}
						
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
				bean.getContract().setTotalFeeOnePeriod(bean.getContract().getFeeADay() * bean.getContract().getPeriodOfPayment());
			} else {
				bean.setStage(ProcessStaging.PAYOFF.getName());
				bean.setAmountDays(DateTimeUtil.daysBetweenDates(item.getExpireDate(), selectedDate));
				bean.setPaidDate(item.getExpireDate());
				PaymentScheduleDto lastPaid = PlbUtil.getLatestPaid(item.getPaymentSchedules());
				if(lastPaid != null){
					bean.getContract().setLastPaidDate(lastPaid.getExpectedPayDate());
				}
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
	@Override
	public GeneralView collectStatistic(Integer companyId) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<GeneralView>() {

			@Override
			public GeneralView execute() throws BusinessException, ClassNotFoundException, IOException {
				GeneralView view = new GeneralView();
				Long totalInProgressContract = 0L;
				Long totalBadContract = 0L;
				Long totalNotificationToDay = 0L;
				Long totalFinishContract = 0L;
				totalFinishContract = contractDao.countContractByStatusAndCompanyId(ContractStatusType.FINISH, companyId);
				totalInProgressContract = contractDao.countContractByStatusAndCompanyId(ContractStatusType.IN_PROGRESS, companyId);
				totalNotificationToDay = contractDao.countNotifiedContractBySpecificDateAndCompanyId(DateTimeUtil.getCurrentDate(), companyId);
				totalBadContract = contractDao.countContractByStatusAndCompanyId(ContractStatusType.BAD, companyId);
				
				view.setTotalBadContract(totalBadContract);
				view.setTotalFinishContract(totalFinishContract);
				view.setTotalInProgressContract(totalInProgressContract);
				view.setTotalNotification(totalNotificationToDay);
				view.setStatistic(new StatisticInfo());
				int currentYear = view.getStatistic().getYear();
				view.getStatistic().setCompanyId(companyId);
				buildProfitStatistic(companyId, view.getStatistic(), currentYear);
				return view;
			}

			
		});
	}
	private StatisticInfo buildProfitStatistic(Integer companyId, StatisticInfo statistic, int currentYear)
			throws BusinessException {
		List<PaymentHistory> payments =paymentHistoryDao.getHistoriesInDateRange(companyId, DateTimeUtil.getFirstDateOfYear(currentYear), DateTimeUtil.getLastDateOfYear(currentYear));
		buildPaymentStatisticForCompany(statistic, payments);
		return statistic;
	}

	private void buildPaymentStatisticForCompany(StatisticInfo statistic, List<PaymentHistory> payments) {
		if(CollectionUtils.isNotEmpty(payments)){
					payments.stream()
								.filter(item -> item.getHistoryType().equalsIgnoreCase(PaymentHistoryType.RENTING_NEW_MOTOBIKE.getType()))
								.collect(Collectors.groupingBy(
									PaymentHistory::getLogMonth, Collectors.summingDouble(PaymentHistory::getRentingAmount)	
								)).entrySet().stream().sorted((o1,o2) -> o1.getKey().compareTo(o2.getKey())).forEachOrdered(item->{
							statistic.getRentingCostByMonth().set(item.getKey(), item.getValue());
					});
					
					payments.stream()
						.filter(item -> item.getHistoryType().equalsIgnoreCase(PaymentHistoryType.PAYOFF.getType()) )
						.collect(Collectors.groupingBy(
								PaymentHistory::getLogMonth, Collectors.summingDouble(PaymentHistory::getPayoff)	
					)).entrySet().stream().sorted((o1,o2) -> o1.getKey().compareTo(o2.getKey())).forEachOrdered(item->{
						statistic.getProfitByMonth().set(item.getKey(), item.getValue());
				});
					
				payments.stream()
					.filter(item -> item.getHistoryType().equalsIgnoreCase(PaymentHistoryType.RENTING_COST.getType()) )
					.collect(Collectors.groupingBy(
							PaymentHistory::getLogMonth, Collectors.summingDouble(PaymentHistory::getFee)	
				)).entrySet().stream().sorted((o1,o2) -> o1.getKey().compareTo(o2.getKey())).forEachOrdered(item->{
					Double totalValue = statistic.getProfitByMonth().get(item.getKey()) + item.getValue();
					statistic.getProfitByMonth().set(item.getKey(), totalValue);
			});
				
				 List<Double> effectiveRentingCost = statistic.getRentingCostByMonth().stream().map(item -> item /1000000).collect(Collectors.toList());
				 List<Double> effectiveProfitCost = statistic.getProfitByMonth().stream().map(item -> item /1000000).collect(Collectors.toList());
				 statistic.setRentingCostByMonth(effectiveRentingCost);
				 statistic.setProfitByMonth(effectiveProfitCost);
		}
	}
	
	
	@Override
	public StatisticInfo collectProfitStatistic(Integer companyId, int year) throws BusinessException {

		return  methodWrapper(new PersistenceExecutable<StatisticInfo>() {

			@Override
			public StatisticInfo execute() throws BusinessException, ClassNotFoundException, IOException {
				StatisticInfo statistic = new StatisticInfo();
				statistic.setYear(year);
				statistic = buildProfitStatistic(companyId, statistic, year);
				return statistic;
			}
			
		});
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public int updateBadContract(Integer companyId) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<Integer>() {

			@Override
			public Integer execute() throws BusinessException, ClassNotFoundException, IOException {
				return contractDao.updateBadContract(companyId);
			}
		});
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public int updateBadContractBaseOnPaymentDate(Integer companyId) throws BusinessException{
		return methodWrapper(new PersistenceExecutable<Integer>() {

			@Override
			public Integer execute() throws BusinessException, ClassNotFoundException, IOException {
				List<Contract> allProgressContracts = contractDao.getContractByStatusAndCompanyId(ContractStatusType.IN_PROGRESS, companyId);
				if(CollectionUtils.isNotEmpty(allProgressContracts)){
					Date currentDate = DateTimeUtil.getCurrentDate();
					List<Contract> badContracts = allProgressContracts.stream()
																	.filter(item -> !isAllPaymentsFinish(item))
																	.filter(item -> {
																		PaymentSchedule latestPaid = PlbUtil.getLatestPaid(item.getPaymentSchedules());
																		if(latestPaid != null){
																		  int periodPaymentDay = item.getPeriodOfPayment();
																		  Date dateToMarkAsBadContract = DateTimeUtil.addMoreDate(latestPaid.getExpectedPayDate(), periodPaymentDay + ((int)(periodPaymentDay / 2)));
																		  if(dateToMarkAsBadContract.compareTo(currentDate) <= 0){
																			  item.setState(ContractStatusType.BAD.getName());
																			return true;  
																		  }
																		}
																		return false;
																	})
																	.collect(Collectors.toList());;
					if(CollectionUtils.isNotEmpty(badContracts)){
						contractDao.mergeList(badContracts);
						return badContracts.size();
					}
				}
				return 0;
			}

			private boolean isAllPaymentsFinish(Contract item) {
				if(CollectionUtils.isNotEmpty(item.getPaymentSchedules())){
					return !item.getPaymentSchedules().stream()
							.filter(pay -> pay.getFinish().equalsIgnoreCase(ConstantVariable.NO_OPTION))
							.findFirst()
							.isPresent();
					
				}
				return true;
			}
		});
	}

	@Override
	public List<StatisticInfo> collectAllProfitStatistic(int currentYear) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<StatisticInfo>>() {
			@Override
			public List<StatisticInfo> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<PaymentHistory> payments = paymentHistoryDao.getHistoriesInDateRangeAllCompany(DateTimeUtil.getFirstDateOfYear(currentYear), DateTimeUtil.getLastDateOfYear(currentYear));
				List<StatisticInfo> result = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(payments)){
					Map<Integer, List<PaymentHistory>> paymentByCompany = payments.stream().collect(Collectors.groupingBy(PaymentHistory::getCompanyId));
					paymentByCompany.forEach((k,v) -> {
						StatisticInfo stat = new StatisticInfo();
						stat.setCompanyId(k);
						buildPaymentStatisticForCompany(stat, v);
						result.add(stat);
					});
				}
				return result;
			}
		});
	}

	@Override
	public List<ContractDto> findContractsByStateAndIdAndCustomerName(ContractStatusType state, Integer companyId,
			String customerName) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<ContractDto>>() {
			@Override
			public List<ContractDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<Contract> contracts = contractDao.getContractByStateAndCompanyAndCustomerName(state, companyId, customerName);
				if(CollectionUtils.isNotEmpty(contracts)) {
					List<ContractDto> result = ContractConverter.getInstance().toDtosWithCustomer(contracts);
					return result.stream().sorted((o1,o2) -> o2.getStartDate().compareTo(o1.getStartDate())).collect(Collectors.toList());
				}
				return null;
			}
		});
	}
	

	
}
