package vn.com.phuclocbao.dao.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.entity.UserAccount;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.util.PasswordHashing;
@Repository
@Transactional
public class DefaultUserDao extends BaseDaoJpaImpl<UserAccount, Long> implements UserDao
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
				return query.getSingleResult();
			}
			return null;
		}

}