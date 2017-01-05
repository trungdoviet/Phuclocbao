package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.controller.LoginController;
import vn.com.phuclocbao.converter.ContractConverter;
import vn.com.phuclocbao.dao.CompanyDao;
import vn.com.phuclocbao.dao.ContractDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.entity.CompanyEntity;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
@Service
public class DefaultContractService extends BaseService implements ContractService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(DefaultContractService.class);
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ContractDao contractDao;
	
	@Transactional
	@Override
	public boolean saveNewContract(ContractDto contractDto) throws BusinessException {
	  Long id = methodWrapper(new PersistenceExecutable<Long>() {

		@Override
		public Long execute() throws BusinessException, ClassNotFoundException, IOException {
			Contract contract = new Contract();
			if(contractDto.getCompany() != null){
				buildNotifiedDate(contractDto);
				CompanyEntity company = companyDao.findById(contractDto.getCompany().getId());
				if(company == null){
					logger.error("Company which has id "+contractDto.getCompany().getId() + " can not be found");
					throw new BusinessException(PLBErrorCode.OBJECT_NOT_FOUND.name());
				}
				contract = ContractConverter.getInstance().toNewContract(contractDto, contract);
				mapReference(contract, company);
				Contract persistedObject = contractDao.merge(contract);
				return Long.valueOf(persistedObject.getId());
			}
			return null;
		}

		private void buildNotifiedDate(ContractDto contractDto) {
			if(CollectionUtils.isNotEmpty(contractDto.getPaymentSchedules())){
				contractDto.getPaymentSchedules().stream()
				.filter(item -> ConstantVariable.NO_OPTION.equalsIgnoreCase(item.getFinish()))
				.forEach(item ->{
					item.setNotifiedDate(DateTimeUtil.addMoreDate(item.getPayDate(), -contractDto.getPeriodOfPayment()));
				});
			}
		}

		private void mapReference(Contract contract, CompanyEntity company) {
			contract.setCompany(company);
			contract.getCompany().getContracts().add(contract);
			contract.getCustomer().setContract(contract);
			contract.getOwner().setContract(contract);
			if(CollectionUtils.isNotEmpty(contract.getPaymentSchedules())){
				contract.getPaymentSchedules().forEach(item -> item.setContract(contract));
			}
		}
	  });
		return Objects.nonNull(id);
	}

	@PersistenceContext
	private EntityManager manager;
	@Override
	public EntityManager getEm() {
		return manager;
	}
	public CompanyDao getCompanyDao() {
		return companyDao;
	}
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}
	public ContractDao getContractDao() {
		return contractDao;
	}
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}
	

}
