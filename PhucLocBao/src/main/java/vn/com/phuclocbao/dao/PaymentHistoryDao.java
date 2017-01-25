package vn.com.phuclocbao.dao;

import java.util.List;

import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;

public interface PaymentHistoryDao {
	public PaymentHistory findById(Integer id, Integer companyId) throws BusinessException;
	public PaymentHistory persist(PaymentHistory entity) throws BusinessException;
	public List<PaymentHistory> persistList(List<PaymentHistory> entities) throws BusinessException;
}
