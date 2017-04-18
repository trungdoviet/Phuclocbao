package vn.com.phuclocbao.service;

import vn.com.phuclocbao.exception.BusinessException;

public interface AtomicCounterService {
	public boolean isBadContractSynchronized(Integer companyId) throws BusinessException;
	public boolean addNewOrUpdateSynchronizedCounter(Integer companyId) throws BusinessException;
	public boolean removeSynchronizedBadFlag(Integer companyId) throws BusinessException;
}
