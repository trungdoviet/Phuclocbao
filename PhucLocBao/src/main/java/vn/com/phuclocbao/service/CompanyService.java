package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.vo.UserActionParamVO;

public interface CompanyService {
	public CompanyDto mergeFinancial(CompanyDto dto, UserActionParamVO userParam) throws BusinessException;
	public CompanyDto findById(Integer id) throws BusinessException;
	public List<CompanyDto> findAll() throws BusinessException;
	public CompanyDto persist(CompanyDto entityActionP, UserActionParamVO useraram) throws BusinessException;
	public boolean remove(Integer id, UserActionParamVO useraram)  throws BusinessException;
}
