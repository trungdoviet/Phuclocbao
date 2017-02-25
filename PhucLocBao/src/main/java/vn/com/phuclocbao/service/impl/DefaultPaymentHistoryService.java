package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.converter.PaymentHistoryConverter;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.viewbean.CompanyProfitBean;
@Service
public class DefaultPaymentHistoryService extends BaseService implements PaymentHistoryService {
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;

	@Transactional
	@Override
	public List<PaymentHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException {
		
		return methodWrapper(new PersistenceExecutable<List<PaymentHistoryDto>>() {
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
	
}
