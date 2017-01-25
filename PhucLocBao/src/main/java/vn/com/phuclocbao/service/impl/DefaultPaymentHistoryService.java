package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.PaymentHistoryConverter;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.PaymentHistoryService;
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
	
}
