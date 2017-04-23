package vn.com.phuclocbao.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.entity.CompanyEntity;
@Repository
public class DefaultCompanyDao extends BaseDaoJpaImpl<CompanyEntity, Integer> implements CompanyDao
{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public EntityManager getEm() {
		return manager;
	}

	@Override
	public void remove(CompanyEntity entity) {
		Query query = getEm().createNamedQuery("companyEntity_deleteById");
		query.setParameter("companyId", entity.getId());
		query.executeUpdate();
	}

	/*@Override
	public CompanyEntity findById(Integer id) {
		TypedQuery<CompanyEntity> query = getEm().createNamedQuery("companyEntity_getCompanyById", CompanyEntity.class);
		query.setParameter("companyId", id);
		return query.getSingleResult();
	}*/
	
	
	
	
}