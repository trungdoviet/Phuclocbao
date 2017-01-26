package vn.com.phuclocbao.service;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.vo.UserActionParamVO;

public interface CompanyService {
	public CompanyDto mergeFinancial(CompanyDto dto, UserActionParamVO userParam) throws BusinessException;
	public CompanyDto findById(Integer id) throws BusinessException;
}
