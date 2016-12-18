package vn.com.phuclocbao.dao;

import java.io.IOException;

import vn.com.phuclocbao.exception.BusinessException;

public interface PersistenceExecutable<T> {	
	public T execute() throws BusinessException, ClassNotFoundException, IOException;
	
}