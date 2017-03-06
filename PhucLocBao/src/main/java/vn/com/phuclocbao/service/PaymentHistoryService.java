package vn.com.phuclocbao.service;

import java.util.Date;
import java.util.List;

import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.viewbean.CompanyProfitBean;

public interface PaymentHistoryService {
	public List<PaymentHistoryDto> getHistories(Integer companyId, Date startDate, Date endDate) throws BusinessException;
	public CompanyProfitBean buildProfitStatistic(List<CompanyDto> company, List<StatisticInfo> statitics) throws BusinessException;
	public PaymentHistoryDto saveNewPayment(Integer companyId, PaymentHistoryType type, Double amount, String description, String username) throws BusinessException;
}
