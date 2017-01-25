package vn.com.phuclocbao.service;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface PaymentHistoryService {
	public List<PaymentHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException;
}
