package vn.com.phuclocbao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.entity.CompanyEntity;
@Repository
@Transactional
public class DefaultCompanyDao extends BaseDaoJpaImpl<CompanyEntity, Long> implements CompanyDao
{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public EntityManager getEm() {
		return manager;
	}
}