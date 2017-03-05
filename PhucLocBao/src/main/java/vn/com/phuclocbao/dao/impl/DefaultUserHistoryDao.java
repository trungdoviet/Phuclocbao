package vn.com.phuclocbao.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.UserHistoryDao;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.exception.BusinessException;
@Repository
public class DefaultUserHistoryDao extends BaseDaoJpaImpl<UserHistory, Integer> implements UserHistoryDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Override
	public UserHistory findById(Integer id, Integer companyId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<UserHistory> getHistoriesInDateRange(Integer companyId, Date startDate, Date endDate) throws BusinessException {
		TypedQuery<UserHistory> query = getEm().createNamedQuery("userHistory_getHistoriesInDateRange", UserHistory.class);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("companyId", companyId);
		return query.getResultList();
	}
	
}
