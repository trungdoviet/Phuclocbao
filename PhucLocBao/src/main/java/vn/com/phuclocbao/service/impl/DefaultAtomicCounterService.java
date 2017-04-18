package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.CompanyConverter;
import vn.com.phuclocbao.dao.AtomicCounterDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.entity.AtomicCounter;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.enums.AtomicCounterOption;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.UserActionHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.AtomicCounterService;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.PaymentHistoryUtil;
import vn.com.phuclocbao.util.UserHistoryUtil;
@Service
public class DefaultAtomicCounterService extends BaseService implements AtomicCounterService {
	@Autowired
	private AtomicCounterDao atomicCounterDao;
	@PersistenceContext
	private EntityManager manager;
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultAtomicCounterService.class);
	

	public AtomicCounterDao getAtomicCounterDao() {
		return atomicCounterDao;
	}

	public void setAtomicCounterDao(AtomicCounterDao atomicCounterDao) {
		this.atomicCounterDao = atomicCounterDao;
	}

	@Override
	public boolean isBadContractSynchronized(Integer companyId) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				AtomicCounter entity = atomicCounterDao.findByKey(AtomicCounterOption.SYNCHRONIZE_BAD_CONTRACT.getName(), companyId);
				if(entity != null){
					Date today = DateTimeUtil.getCurrentDateWithoutTime();
					if(DateTimeUtil.calculateDayBetween(today, entity.getSetTime()) == 0){
						return true;
					}
				}
				return false;
			}
		});
	}

	@Override
	public EntityManager getEm() {
		return manager;
	}

	@Override
	@Transactional(rollbackFor=BusinessException.class)
	public boolean addNewOrUpdateSynchronizedCounter(Integer companyId) throws BusinessException {
		 return transactionWrapper(new PersistenceExecutable<Boolean>() {
				@Override
				public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
					AtomicCounter entity = atomicCounterDao.findByKey(AtomicCounterOption.SYNCHRONIZE_BAD_CONTRACT.getName(), companyId);
					if(entity == null){
						AtomicCounter newCounter = new AtomicCounter();
						newCounter.setContent(companyId.toString());
						newCounter.setSetTime(DateTimeUtil.getCurrentDate());
						newCounter.setKeyName(AtomicCounterOption.SYNCHRONIZE_BAD_CONTRACT.getName());
						atomicCounterDao.persist(newCounter);
					} else {
						entity.setSetTime(DateTimeUtil.getCurrentDate());
						atomicCounterDao.merge(entity);
					}
					return true;
				}
			 });
	}

	@Override
	@Transactional(rollbackFor=BusinessException.class)
	public boolean removeSynchronizedBadFlag(Integer companyId) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				AtomicCounter entity = atomicCounterDao.findByKey(AtomicCounterOption.SYNCHRONIZE_BAD_CONTRACT.getName(), companyId);
				if(entity != null){
					 atomicCounterDao.remove(entity);
				}
				return true;
			}
		 });
	}
	
}
