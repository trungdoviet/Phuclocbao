package vn.com.phuclocbao.dao;

import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.exception.BusinessException;

public interface CompanyDao{
	public CompanyEntity findById(Integer id);
	public CompanyEntity merge( CompanyEntity entity) throws BusinessException;
}
