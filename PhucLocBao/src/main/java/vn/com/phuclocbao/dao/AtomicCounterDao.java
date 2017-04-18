package vn.com.phuclocbao.dao;

import vn.com.phuclocbao.entity.AtomicCounter;
import vn.com.phuclocbao.exception.BusinessException;

public interface AtomicCounterDao {
	public AtomicCounter findByKey(String keyName, Integer companyId) throws BusinessException;
	public AtomicCounter persist(AtomicCounter entity) throws BusinessException;
	public AtomicCounter merge(AtomicCounter entity) throws BusinessException;
	public void remove(AtomicCounter entity)  throws BusinessException;
}
