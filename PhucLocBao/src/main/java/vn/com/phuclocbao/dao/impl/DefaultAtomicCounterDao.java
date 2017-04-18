package vn.com.phuclocbao.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import vn.com.phuclocbao.dao.AtomicCounterDao;
import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.entity.AtomicCounter;
import vn.com.phuclocbao.exception.BusinessException;
@Repository
public class DefaultAtomicCounterDao extends BaseDaoJpaImpl<AtomicCounter, Integer> implements AtomicCounterDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}

	@Override
	public AtomicCounter findByKey(String keyName, Integer companyId) throws BusinessException {
		if(StringUtils.isNotEmpty(keyName) && companyId != null && companyId > 0){
			TypedQuery<AtomicCounter> query = getEm().createNamedQuery("getCounterByKeyAndCompanyId", AtomicCounter.class);
			query.setParameter("keyName", keyName);
			query.setParameter("companyId", companyId.toString());
			 List<AtomicCounter> result = query.getResultList();
			 if(CollectionUtils.isNotEmpty(result)){
				 return result.get(0);
			 }
		}
		return null;
	}
	
}
