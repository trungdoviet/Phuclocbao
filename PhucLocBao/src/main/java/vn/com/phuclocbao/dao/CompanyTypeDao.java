package vn.com.phuclocbao.dao;

import java.util.List;

import vn.com.phuclocbao.entity.CompanyType;
import vn.com.phuclocbao.exception.BusinessException;

public interface CompanyTypeDao{
	public CompanyType findById(Integer id);
	public List<CompanyType> findAll() throws BusinessException;
}
