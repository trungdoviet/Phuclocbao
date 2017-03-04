package vn.com.phuclocbao.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.CustomerDao;
import vn.com.phuclocbao.entity.Customer;
@Repository
@Transactional
public class DefaultCustomerDao extends BaseDaoJpaImpl<Customer, Integer> implements CustomerDao
{
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public EntityManager getEm() {
		return manager;
	}

	@Override
	public List<Customer> getCustomerContainIdNo(String idNo) throws PersistenceException {
		TypedQuery<Customer> query = getEm().createNamedQuery("Customer_getCustomerByContainingId", Customer.class);
		query.setParameter("customerIdNo", "%" + idNo +"%");
		return query.getResultList();
	}
	public List<Customer> getCustomerContainNameOrIdNo(String nameOrIdNo, Integer companyId) throws PersistenceException {
		if(companyId != null && companyId > 0){
			TypedQuery<Customer> query = getEm().createNamedQuery("Customer_getCustomerByContainingNameOrIdInCompany", Customer.class);
			query.setParameter("customerIdNoOrName", "%" + nameOrIdNo +"%");
			query.setParameter("companyId", companyId);
			return query.getResultList();
		} else {
			TypedQuery<Customer> query = getEm().createNamedQuery("Customer_getCustomerByContainingNameOrId", Customer.class);
			query.setParameter("customerIdNoOrName", "%" + nameOrIdNo +"%");
			return query.getResultList();
			
		}
		
	}

		
}