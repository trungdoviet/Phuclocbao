package vn.com.phuclocbao.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.phuclocbao.dao.BaseDaoJpaImpl;
import vn.com.phuclocbao.dao.UserDao;
import vn.com.phuclocbao.entity.UserAccount;
@Repository
@Transactional
public class DefaultUserDao extends BaseDaoJpaImpl<UserAccount, Long> implements UserDao
{
	@PersistenceContext
	private EntityManager manager;
	

		@Override
		public boolean isValidUser(String username, String password){
			List<UserAccount> allUsers =  this.findAll();	
			if(CollectionUtils.isNotEmpty(allUsers)){
				UserAccount user = allUsers.stream()
							.filter(item -> item.getUsername().equalsIgnoreCase(username) && item.getPassword().equalsIgnoreCase(password))
							.findFirst().orElse(null);
				return user != null;
			}
			return false;
		}


		@Override
		public EntityManager getEm() {
			return manager;
		}

}