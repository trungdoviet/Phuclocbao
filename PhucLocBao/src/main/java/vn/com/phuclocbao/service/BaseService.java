package vn.com.phuclocbao.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;

import vn.com.phuclocbao.dao.PersistenceExecutable;
import vn.com.phuclocbao.dto.base.IBaseDTO;
import vn.com.phuclocbao.exception.BusinessException;

public abstract class BaseService {
	private static org.apache.log4j.Logger logger = Logger.getLogger(BaseService.class);
	public <T extends IBaseDTO, K extends Number> K  findItemInIds(List<K> inputList, T searchObj ){
		for( K item : inputList){
			if(item.longValue() == searchObj.getId().longValue()) {
				return item;
			}
		}
		return null;
	}
	
	public abstract EntityManager getEm();
	
	public <T extends IBaseDTO, K extends Number> boolean isSameId (T source, K des ){
		if(source != null && des != null 
				&& source.getId() != null){
			return source.getId().longValue() == des.longValue();
		}
		return false;
	}
	
	public <K extends Number> boolean isSameId (K source, K des ){
		if(source != null && des != null){
			return source.longValue() == des.longValue();
		}
		return false;
	}
	
	public <T>  T methodWrapper( PersistenceExecutable<T> executable ) throws BusinessException {
		T result;
		try {
			result = executable.execute();
		} catch (Exception ex) {
			if (ex instanceof BusinessException) {
				throw ((BusinessException) ex);
			}
			throw new PersistenceException("Failed to excecute service \""
					+ this.getClass().getName() + "\" for persistence unit plb_unit \""
					+  "\".", ex);
        } finally {
            getEm().close();
            
        }		
	    return result; 
	}
	
	
	
	public <T>  T transactionWrapper(String transactionName, PersistenceExecutable<T> executable) throws BusinessException {
		return executeMethod(transactionName, getEm(), executable);
	}
	
	private <T>  T executeMethod(String operationName, EntityManager em,			
			final PersistenceExecutable<T> executable) throws BusinessException {
		try {
				EntityTransaction transaction = em.getTransaction();
				EntityTransaction finalTransaction = transaction;

				boolean beginTransaction = (!finalTransaction.isActive());

				if (beginTransaction) {
					finalTransaction.begin();
				}

				T result = executable.execute();

				if (beginTransaction) {
					finalTransaction.commit();
				}
				return result;
		} catch (Exception ex) {
			logger.error("Failed to excecute operation \"{0}\" for persistence unit \"{1}\".",ex);

			EntityTransaction transaction = em.getTransaction();
			if (transaction.isActive()) {
				try {
					transaction.rollback();
				} catch (Exception rollbackEx) {
					logger.error("Failed to rollback transaction for persistence unit plb_unit");
				}
			}
			if (ex instanceof RuntimeException) {
				throw ((RuntimeException) ex);
			}
			if (ex instanceof BusinessException) {
				throw ((BusinessException) ex);
			}
			
			throw new PersistenceException("Failed to excecute operation \""
					+ operationName + "\" for persistence unit plb_unit\""
					+ "\".", ex);
		} finally {
			getEm().close();
		}
	}
}