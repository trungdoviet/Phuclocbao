package vn.com.phuclocbao.dao;

import java.util.List;

import javax.persistence.PersistenceException;

import vn.com.phuclocbao.entity.Customer;

public interface CustomerDao{
		public List<Customer> getCustomerContainIdNo(String idNo) throws PersistenceException;
}
