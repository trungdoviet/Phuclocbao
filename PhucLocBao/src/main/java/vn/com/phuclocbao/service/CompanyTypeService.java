package vn.com.phuclocbao.service;

import java.util.List;

import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.exception.BusinessException;

public interface CompanyTypeService {
	public CompanyTypeDto findById(Integer id) throws BusinessException;
	public List<CompanyTypeDto> findAll() throws BusinessException;
}
