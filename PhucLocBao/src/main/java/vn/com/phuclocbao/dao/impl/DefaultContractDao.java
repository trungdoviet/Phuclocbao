package vn.com.phuclocbao.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.ContractDao;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.exception.BusinessException;
@Repository
@Transactional
public class DefaultContractDao extends BaseDaoJpaImpl<Contract, Integer> implements ContractDao {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Override
	public List<Contract> getContractByStatusAndCompanyId(ContractStatusType state, Integer id)
			throws BusinessException {
		TypedQuery<Contract> query = getEm().createNamedQuery("Contract_getContractByStatusAndCompany", Contract.class);
		query.setParameter("contractState", state.getName());
		query.setParameter("companyId", id);
		return query.getResultList();
	}
	@Override
	public Contract findById(Integer id, Integer companyId) throws BusinessException {
		TypedQuery<Contract> query = getEm().createNamedQuery("Contract_getContractByIdAndCompany", Contract.class);
		query.setParameter("contractId",id);
		query.setParameter("companyId", companyId);
		return query.getSingleResult();
	}
	@Override
	public List<Contract> getNotifiedContractBySpecificDateAndCompanyId(Date targetDate, Integer companyId)
			throws BusinessException {
		TypedQuery<Contract> query = getEm().createNamedQuery("Contract_getContractByDateAndcompanyId", Contract.class);
		query.setParameter("companyId", companyId);
		query.setParameter("inputDate", targetDate);
		query.setParameter("contractState", ContractStatusType.IN_PROGRESS.getName());
		return query.getResultList();
	}

}
