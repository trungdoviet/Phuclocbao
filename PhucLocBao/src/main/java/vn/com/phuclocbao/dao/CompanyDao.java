package vn.com.phuclocbao.dao;

import java.util.List;

import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.exception.BusinessException;

public interface CompanyDao{
	public CompanyEntity findById(Integer id);
	public CompanyEntity merge( CompanyEntity entity) throws BusinessException;
	public List<CompanyEntity> findAll() throws BusinessException;
	public CompanyEntity persist(CompanyEntity entity) throws BusinessException;
	public void remove(CompanyEntity entity)  throws BusinessException;
}
