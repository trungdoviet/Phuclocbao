package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface CompanyService {
	public CompanyDto mergeFinancial(CompanyDto dto) throws BusinessException;
	public CompanyDto findById(Integer id) throws BusinessException;
}
