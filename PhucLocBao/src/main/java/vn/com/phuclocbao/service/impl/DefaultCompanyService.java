package vn.com.phuclocbao.service.impl;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.CompanyConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.CompanyService;
@Service
public class DefaultCompanyService extends BaseService implements CompanyService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultCompanyService.class);
	@Autowired
	private CompanyDao companyDao;
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Transactional
	@Override
	public CompanyDto mergeFinancial(CompanyDto dto) throws BusinessException {
		 return methodWrapper(new PersistenceExecutable<CompanyDto>() {
			@Override
			public CompanyDto execute() throws BusinessException, ClassNotFoundException, IOException {
				CompanyEntity entity = companyDao.findById(dto.getId());
				if(entity == null){
					logger.error("Can not find company with id " + dto.getId());
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
					
				}
				CompanyEntity updatedCompany = CompanyConverter.getInstance().updateEntity(entity, dto);
				return CompanyConverter.getInstance().toDto(companyDao.merge(updatedCompany), new CompanyDto());
			}
		 });
	}
	@Transactional
	@Override
	public CompanyDto findById(Integer id) throws BusinessException{
		return methodWrapper(new PersistenceExecutable<CompanyDto>() {
				@Override
				public CompanyDto execute() throws BusinessException, ClassNotFoundException, IOException {
					CompanyEntity entity = companyDao.findById(id);
					if(entity == null){
						logger.error("Can not find company with id " + id);
						throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
					}
					
					return CompanyConverter.getInstance().toDto(companyDao.merge(entity), new CompanyDto());
				}
			});
	}
	public CompanyDao getCompanyDao() {
		return companyDao;
	}
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}



}
