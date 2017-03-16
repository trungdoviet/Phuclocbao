package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.converter.CompanyConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.CompanyTypeDao;
import vn.com.phuclocbao.dao.PaymentHistoryDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dao.UserHistoryDao;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.CompanyType;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.UserHistory;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.enums.UserActionHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.CompanyService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.PaymentHistoryUtil;
import vn.com.phuclocbao.util.UserHistoryUtil;
import vn.com.phuclocbao.vo.UserActionParamVO;
@Service
public class DefaultCompanyService extends BaseService implements CompanyService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultCompanyService.class);
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private CompanyTypeDao companyTypeDao;
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;
	@Autowired
	private UserHistoryDao userHistoryDao;
	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	private static final String ADMIN_SPAN="<span class='adminRole' title='Admin'>{0}</span>";
	private static final String NORMAL_USER_SPAN="<span class='userRole'>{0}</span>";
	
	public UserHistoryDao getUserHistoryDao() {
		return userHistoryDao;
	}

	public void setUserHistoryDao(UserHistoryDao userHistoryDao) {
		this.userHistoryDao = userHistoryDao;
	}

	public PaymentHistoryDao getPaymentHistoryDao() {
		return paymentHistoryDao;
	}

	public void setPaymentHistoryDao(PaymentHistoryDao paymentHistoryDao) {
		this.paymentHistoryDao = paymentHistoryDao;
	}
	

	public CompanyTypeDao getCompanyTypeDao() {
		return companyTypeDao;
	}

	public void setCompanyTypeDao(CompanyTypeDao companyTypeDao) {
		this.companyTypeDao = companyTypeDao;
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public CompanyDto mergeFinancial(CompanyDto dto, UserActionParamVO userActionParam) throws BusinessException {
		 return transactionWrapper(new PersistenceExecutable<CompanyDto>() {
			@Override
			public CompanyDto execute() throws BusinessException, ClassNotFoundException, IOException {
				CompanyEntity entity = companyDao.findById(dto.getId());
				if(entity == null){
					logger.error("Can not find company with id " + dto.getId());
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
					
				}
				Double investFunding = dto.getTotalFund();
				Double oldInvestFunding = entity.getTotalFund();
				CompanyEntity updatedCompany = CompanyConverter.getInstance().updateEntity(entity, dto);
				CompanyDto updatedCompanyDto = CompanyConverter.getInstance().toDto(companyDao.merge(updatedCompany), new CompanyDto());
				if(updatedCompany != null){
					if(investFunding.equals(oldInvestFunding)){
						UserHistory userHistory = UserHistoryUtil.createNewHistory(null, dto.getId(), 
								dto.getName(), userActionParam.getUsername(), 
								UserActionHistoryType.UPDATE_COMPANY_INFO, StringUtils.EMPTY);
						userHistoryDao.persist(userHistory);
					}else {
						paymentHistoryDao.persist(PaymentHistoryUtil.createNewHistory(null, dto.getId(), PaymentHistoryType.UPDATE_FUNDING, investFunding, null));
						UserHistory userHistory = UserHistoryUtil.createNewHistory(null, dto.getId(), 
								dto.getName(), userActionParam.getUsername(), 
								UserActionHistoryType.UPDATE_COMPANY_FINANCIAL, StringUtils.EMPTY);
						userHistoryDao.persist(userHistory);
					}
				}
				return updatedCompanyDto;
			}
		 });
	}
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
					
					return CompanyConverter.getInstance().toDto(entity, new CompanyDto());
				}
			});
	}
	public CompanyDao getCompanyDao() {
		return companyDao;
	}
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@Override
	public List<CompanyDto> findAll() throws BusinessException {
		
		return methodWrapper(new PersistenceExecutable<List<CompanyDto>>() {
			@Override
			public List<CompanyDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<CompanyDto> dtos = CompanyConverter.getInstance().toDtos(companyDao.findAll());
				if(CollectionUtils.isNotEmpty(dtos)){
					dtos.forEach(item ->{
						item.setCityInString(VietnamCityService.getProvinceName(item.getCity()));
						item.setUserAccountsInString(buildUserAccountString(item.getUserAccounts()));
					});
				}
				return dtos;
			}

			private String buildUserAccountString(List<UserAccountDto> userAccounts) {
				if(CollectionUtils.isNotEmpty(userAccounts)){
					List<String> userNames = userAccounts.stream().map(item->{
							if(ConstantVariable.YES_OPTION.equalsIgnoreCase(item.getIsAdmin())){
								return MessageFormat.format(ADMIN_SPAN, item.getUsername());
							} else {
								return MessageFormat.format(NORMAL_USER_SPAN, item.getUsername());
							}
						}).collect(Collectors.toList());
					return StringUtils.join(userNames, ",");
				}
				return StringUtils.EMPTY;
			}
		});
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public CompanyDto persist(CompanyDto dto, UserActionParamVO userActionParam) throws BusinessException {
		
		return transactionWrapper(new PersistenceExecutable<CompanyDto>() {

			@Override
			public CompanyDto execute() throws BusinessException, ClassNotFoundException, IOException {
				CompanyEntity entity = CompanyConverter.getInstance().toNewEntity(dto);
				CompanyType companyType = companyTypeDao.findById(dto.getType().getId());
				if(companyType == null || entity == null){
					logger.error("Can not find company type with id " + dto.getType().getId());
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
				companyType.getCompanies().add(entity);
				entity.setType(companyType);
				CompanyDto persistedCompany = CompanyConverter.getInstance().toDto(companyDao.persist(entity), new CompanyDto());
				if(persistedCompany != null){
					UserHistory userHistory = UserHistoryUtil.createNewHistory(nothing(), userActionParam.getCompanyId(), 
							userActionParam.getCompanyName(), userActionParam.getUsername(), 
							UserActionHistoryType.CREATE_COMPANY_BRANCH, persistedCompany.getName());
					userHistoryDao.persist(userHistory);
				}
				return persistedCompany;
			}

			
		});
	}
	private Contract nothing() {
		return null;
	}
	@Transactional(rollbackFor=BusinessException.class)
	@Override
	public boolean remove(Integer id, UserActionParamVO userActionParam) throws BusinessException {
		return transactionWrapper(new PersistenceExecutable<Boolean>() {
			@Override
			public Boolean execute() throws BusinessException, ClassNotFoundException, IOException {
				CompanyEntity company = companyDao.findById(id);
				String companyName = company.getName();
				if(company != null){
					if(CollectionUtils.isNotEmpty(company.getContracts())){
						return false;
					}
					companyDao.remove(company);
					userHistoryDao.deleteHistoryByCompanyId(id);
					paymentHistoryDao.deletePaymentHistoryByCompanyId(id);
					UserHistory userHistory = UserHistoryUtil.createNewHistory(nothing(), userActionParam.getCompanyId(), 
							userActionParam.getCompanyName(), userActionParam.getUsername(), 
							UserActionHistoryType.DELETE_COMPANY_BRANCH, companyName);
					userHistoryDao.persist(userHistory);
					
				}
				return true;
			}
		});
		
	}


}
