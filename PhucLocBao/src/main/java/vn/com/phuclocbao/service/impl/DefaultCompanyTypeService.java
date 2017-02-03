package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.CompanyTypeConverter;
import vn.com.phuclocbao.dao.CompanyTypeDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.entity.CompanyType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.CompanyTypeService;
@Service
public class DefaultCompanyTypeService extends BaseService implements CompanyTypeService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultCompanyTypeService.class);
	@Autowired
	private CompanyTypeDao companyTypeDao;
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	@Transactional
	@Override
	public CompanyTypeDto findById(Integer id) throws BusinessException{
		return methodWrapper(new PersistenceExecutable<CompanyTypeDto>() {
				@Override
				public CompanyTypeDto execute() throws BusinessException, ClassNotFoundException, IOException {
					CompanyType entity = companyTypeDao.findById(id);
					if(entity == null){
						logger.error("Can not find company with id " + id);
						throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
					}
					
					return CompanyTypeConverter.getInstance().toDto(entity, new CompanyTypeDto());
				}
			});
	}

	@Override
	public List<CompanyTypeDto> findAll() throws BusinessException {
		
		return methodWrapper(new PersistenceExecutable<List<CompanyTypeDto>>() {
			@Override
			public List<CompanyTypeDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<CompanyTypeDto> dtos = CompanyTypeConverter.getInstance().toDtos(companyTypeDao.findAll());
				return dtos;
			}

			
		});
	}



}
