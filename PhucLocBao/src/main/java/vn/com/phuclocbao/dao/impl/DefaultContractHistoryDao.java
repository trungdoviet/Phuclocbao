package vn.com.phuclocbao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.ContractHistoryDao;
import vn.com.phuclocbao.entity.ContractHistory;
import vn.com.phuclocbao.exception.BusinessException;
@Repository
@Transactional
public class DefaultContractHistoryDao extends BaseDaoJpaImpl<ContractHistory, Integer> implements ContractHistoryDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Override
	public ContractHistory findById(Integer id, Integer companyId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
