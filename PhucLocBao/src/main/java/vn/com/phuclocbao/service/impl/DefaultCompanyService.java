package vn.com.phuclocbao.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.CompanyService;
@Service
@Transactional
public class DefaultCompanyService extends BaseService implements CompanyService {
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}

}
