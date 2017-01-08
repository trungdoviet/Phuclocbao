package vn.com.phuclocbao.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.bean.CustomerContract;
import vn.com.phuclocbao.bean.EffectiveContract;
import vn.com.phuclocbao.converter.CustomerConverter;
import vn.com.phuclocbao.dao.CustomerDao;
import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.entity.Customer;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.BaseService;
import vn.com.phuclocbao.service.CustomerService;
import vn.com.phuclocbao.util.DateTimeUtil;
@Service
public class DefaultCustomerService extends BaseService implements CustomerService {
	@Autowired
	private CustomerDao customerDao;
	
	public CustomerDao getCustomerDao() {
		return customerDao;
	}
	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public EntityManager getEm() {
		return manager;
	}
	
	@Override
	public List<CustomerDto> getCustomersByIdNo(String idNo) throws BusinessException {
		return methodWrapper(new PersistenceExecutable<List<CustomerDto>>() {

			@Override
			public List<CustomerDto> execute() throws BusinessException, ClassNotFoundException, IOException {
				List<Customer> customers = customerDao.getCustomerContainIdNo(idNo);
				return CustomerConverter.getInstance().toDtosOnSearchingCustomer(customers);
			}
			
		});
	}
	@Override
	public ContractResponseBody buildContractResponse(List<CustomerDto> dtos) {
		ContractResponseBody result = new ContractResponseBody();
		if(CollectionUtils.isNotEmpty(dtos)){
			Map<String, List<CustomerDto>> groupedCustomersByIdNo = new HashMap<>();
			for (CustomerDto customerDto : dtos) {
				if(!groupedCustomersByIdNo.containsKey(customerDto.getIdNo())){
					groupedCustomersByIdNo.put(customerDto.getIdNo(), new ArrayList<>());
				}
				groupedCustomersByIdNo.get(customerDto.getIdNo()).add(customerDto);
			}
			
			if(MapUtils.isNotEmpty(groupedCustomersByIdNo)){
				result.setCustomers(new ArrayList<>());
				result.setCustomerContracts(new ArrayList<>());
				for (Entry<String,List<CustomerDto>> entry : groupedCustomersByIdNo.entrySet()) {
					CustomerDto latestCustomer = findLatestCustomerById(entry.getValue());
					if(latestCustomer != null){
						result.getCustomers().add(latestCustomer);
						CustomerContract cucon = buildCustomerContract(entry.getKey(), entry.getValue());
						if(cucon != null){
							result.getCustomerContracts().add(cucon);
						}
					}
					
				}
			}
			
		}
		return result;
	}
	
	private CustomerDto findLatestCustomerById(List<CustomerDto> dtos){
		if(CollectionUtils.isNotEmpty(dtos)){
			sortCustomerById(dtos);
			return dtos.get(0);
		}
		return null;
	}
	private void sortCustomerById(List<CustomerDto> dtos) {
		Collections.sort(dtos, new Comparator<CustomerDto>() {
			@Override
			public int compare(CustomerDto o1, CustomerDto o2) {
				return o2.getId().compareTo(o1.getId());
			}
		});
	}
	
	private CustomerContract buildCustomerContract(String idNo, List<CustomerDto> dtos){
		if(StringUtils.isNotEmpty(idNo) && CollectionUtils.isNotEmpty(dtos)){
			CustomerContract cucon = new CustomerContract();
			cucon.setIdNo(idNo);
			List<EffectiveContract> effs = new ArrayList<>();
			sortCustomerById(dtos);
			for (CustomerDto dto : dtos) {
				EffectiveContract ef = new EffectiveContract(dto.getContract().getId());
				ef.setState(dto.getContract().getState());
				ef.setStartDate(DateTimeUtil.date2String(dto.getContract().getStartDate()));
				effs.add(ef);
			}
			cucon.setContracts(effs);
			return cucon;
		}
		return null;
	}
}
