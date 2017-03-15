package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.converter.PaymentHistoryConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserHistoryDao;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.UserActionHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.UserHistoryUtil;
import vn.com.phuclocbao.viewbean.CompanyProfitBean;
@Service
public class DefaultPaymentHistoryService extends BaseService implements PaymentHistoryService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultPaymentHistoryService.class);
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;
	@Autowired
	private CompanyDao companyDao;
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public List<PaymentHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException {
		
		return transactionWrapper(new PersistenceExecutable<List<PaymentHistoryDto>>() {
			@Override
			public List<PaymentHistoryDto> execute() throws BusinessException, ClassNotFoundException, IOException {;
				List<PaymentHistory> histories = paymentHistoryDao.getHistoriesInDateRange(companyId, startDate, endDate);
				return PaymentHistoryConverter.getInstance().toDtos(histories);
			}
		});
	}

	public PaymentHistoryDao getPaymentHistoryDao() {
		return paymentHistoryDao;
	}

	public void setPaymentHistoryDao(PaymentHistoryDao paymentHistoryDao) {
		this.paymentHistoryDao = paymentHistoryDao;
	}
	
	public CompanyDao getCompanyDao() {
		return companyDao;
	}

	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}


	public UserHistoryDao getUserHistoryDao() {
		return userHistoryDao;
	}

	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}


	@Autowired
	private UserHistoryDao userHistoryDao;
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}

	@Override
	public CompanyProfitBean buildProfitStatistic(List<CompanyDto> companies, List<StatisticInfo> statitics)
			throws BusinessException {
		CompanyProfitBean bean = new CompanyProfitBean();
		if(CollectionUtils.isNotEmpty(companies)){
			companies.stream().sorted((o1,o2) -> o1.getName().compareToIgnoreCase(o2.getName()))
					.forEach(item -> {
						StatisticInfo stat = findStatisticInfoByCompanyId(statitics,  item.getId());
						if(stat != null){
							bean.getStatistics().add(stat);
							stat.setCompanyName(item.getName());
						} else {
							StatisticInfo newStat = new StatisticInfo(item.getId());
							newStat.setCompanyName(item.getName());
							newStat.getRentingCostByMonth().add(0D);
							newStat.getProfitByMonth().add(0D);
							bean.getStatistics().add(newStat);
						}
					});
		}
		return bean;
	}
	
	private StatisticInfo findStatisticInfoByCompanyId(List<StatisticInfo> stats, Integer companyId){
		if(CollectionUtils.isNotEmpty(stats)){
			 Optional<StatisticInfo> result = stats.stream().filter(item->item.getCompanyId().equals(companyId)).findFirst();
			 if(result.isPresent()){
				 return result.get();
			 }
		}
		return null;
	}

	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public PaymentHistoryDto saveNewPayment(Integer companyId, PaymentHistoryType type, Double amount,
			String description, String username) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<PaymentHistoryDto>() {
			@Override
			public PaymentHistoryDto execute() throws BusinessException, ClassNotFoundException, IOException {
				
				CompanyEntity company = companyDao.findById(companyId);
				if(company == null){
					logger.error("can not find company with id " + companyId);
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
				if(type == PaymentHistoryType.INVEST_FUNDING || type == PaymentHistoryType.OTHER_PROFIT){
					if(company.getTotalFund() == null) {
						company.setTotalFund(0D);
					}
					company.setTotalFund(company.getTotalFund() + amount);
				} else {
					company.setTotalFund(company.getTotalFund() - amount);
				}
				companyDao.merge(company);
				if(type == PaymentHistoryType.INVEST_FUNDING || type == PaymentHistoryType.TAKE_OUT_FUNDING) {
					UserHistory userHistory = UserHistoryUtil.createNewHistory(null, companyId, 
							company.getName(), username, 
							UserActionHistoryType.UPDATE_COMPANY_FINANCIAL, StringUtils.EMPTY);
					userHistoryDao.persist(userHistory);
				} 
				PaymentHistory history = new PaymentHistory();
				history.setLogDate(DateTimeUtil.getCurrentDate());
				history.setDetail(description);
				history.setFee(amount);
				history.setHistoryType(type.getType());
				history.setCompanyId(companyId);
				return PaymentHistoryConverter.getInstance().toDto(paymentHistoryDao.persist(history), new PaymentHistoryDto());
			}
		});
	}
	
}
