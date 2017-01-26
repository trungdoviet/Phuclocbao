package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.UserHistoryConverter;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserHistoryDao;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.UserHistoryService;
@Service
public class DefaultUserHistoryService extends BaseService implements UserHistoryService {
	@Autowired
	private UserHistoryDao userHistoryDao;

	@Transactional
	@Override
	public List<UserHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException {
		
		return methodWrapper(new PersistenceExecutable<List<UserHistoryDto>>() {
			@Override
			public List<UserHistoryDto> execute() throws BusinessException, ClassNotFoundException, IOException {;
				List<UserHistory> histories = userHistoryDao.getHistoriesInDateRange(companyId, startDate, endDate);
				return UserHistoryConverter.getInstance().toDtos(histories);
			}
		});
	}

	

	public UserHistoryDao getUserHistoryDao() {
		return userHistoryDao;
	}
	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}



	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	
}
