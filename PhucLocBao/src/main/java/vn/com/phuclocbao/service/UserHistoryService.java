package vn.com.phuclocbao.service;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface UserHistoryService {
	public List<UserHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException;
}
