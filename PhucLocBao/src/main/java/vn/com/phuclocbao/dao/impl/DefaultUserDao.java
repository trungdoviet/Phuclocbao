package vn.com.phuclocbao.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.PasswordHashing;
@Repository
public class DefaultUserDao extends BaseDaoJpaImpl<UserAccount, Integer> implements UserDao
{
	@PersistenceContext
	private EntityManager manager;
	

		@Override
		public boolean isValidUser(String username, String password) throws BusinessException{
			List<UserAccount> allUsers =  this.findAll();	
			if(CollectionUtils.isNotEmpty(allUsers)){
				UserAccount user = null;
				String hashingPassword = PasswordHashing.hashMD5(password);
				user = allUsers.stream()
						.filter(item -> item.getUsername().equalsIgnoreCase(username) && item.getPassword().equalsIgnoreCase(hashingPassword))
						.findFirst().orElse(null);
				return user != null;
			}
			return false;
		}


		@Override
		public EntityManager getEm() {
			return manager;
		}


		@Override
		public UserAccount getUserByUsername(String username) throws PersistenceException {
			if(StringUtils.isNotEmpty(username)){
				TypedQuery<UserAccount> query = getEm().createNamedQuery("getUserByUsername", UserAccount.class);
				query.setParameter("username", username);
				 UserAccount account = query.getSingleResult();
				 Hibernate.initialize(account.getCompanyEntity());
				 System.out.println("==company id:" + account.getCompanyEntity().getId());
				 return account;
			}
			return null;
		}


		@Override
		public boolean isUserExist(String username) throws BusinessException {
			if(StringUtils.isNotEmpty(username)){
				TypedQuery<UserAccount> query = getEm().createNamedQuery("getUserByUsername", UserAccount.class);
				query.setParameter("username", username);
				List<UserAccount> users = query.getResultList();
				return CollectionUtils.isNotEmpty(users);
			}
			return false;
		}


		@Override
		public int deleteByCompanyId(Integer companyId) throws BusinessException {
			Query query = getEm().createNamedQuery("userAccount_deleteByCompanyId");
			query.setParameter("companyId", companyId);
			return query.executeUpdate();
		}

}