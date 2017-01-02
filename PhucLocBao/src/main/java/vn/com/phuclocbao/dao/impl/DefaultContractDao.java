package vn.com.phuclocbao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.ContractDao;
import vn.com.phuclocbao.entity.Contract;
@Repository
@Transactional
public class DefaultContractDao extends BaseDaoJpaImpl<Contract, Long> implements ContractDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}

}
