package vn.com.phuclocbao.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;
@Repository
public class DefaultPaymentHistoryDao extends BaseDaoJpaImpl<PaymentHistory, Integer> implements PaymentHistoryDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Override
	public PaymentHistory findById(Integer id, Integer companyId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<PaymentHistory> getHistoriesInDateRange(Integer companyId, Date startDate, Date endDate) throws BusinessException {
		TypedQuery<PaymentHistory> query = getEm().createNamedQuery("paymentHistory_getHistoriesInDateRange", PaymentHistory.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("companyId", companyId);
		return query.getResultList();
	}
	@Override
	public List<PaymentHistory> getHistoriesInDateRangeAllCompany(Date startDate, Date endDate)
			throws BusinessException {
		TypedQuery<PaymentHistory> query = getEm().createNamedQuery("paymentHistory_getHistoriesInDateRangeAllCompany", PaymentHistory.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	@Override
	public void deletePaymentHistoryByCompanyId(Integer companyId) throws BusinessException {
		Query query = getEm().createNamedQuery("paymentHistory_deleteHistoriesByCompanyId");
		query.setParameter("companyId", companyId);
		query.executeUpdate();
		
	}
	
}
