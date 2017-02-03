package vn.com.phuclocbao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.CompanyTypeDao;
import vn.com.phuclocbao.entity.CompanyType;
@Repository
@Transactional
public class DefaultCompanyTypeDao extends BaseDaoJpaImpl<CompanyType, Integer> implements CompanyTypeDao
{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public EntityManager getEm() {
		return manager;
	}
}