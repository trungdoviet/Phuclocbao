package vn.com.phuclocbao.dao;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserHistoryDao {
	public UserHistory findById(Integer id, Integer companyId) throws BusinessException;
	public UserHistory persist(UserHistory entity) throws BusinessException;
	public List<UserHistory> persistList(List<UserHistory> entities) throws BusinessException;
	public List<UserHistory> getHistoriesInDateRange(Integer companyId, Date startDate, Date endDate) throws BusinessException;
	public void deleteHistoryByCompanyId(Integer companyId) throws BusinessException;
}
