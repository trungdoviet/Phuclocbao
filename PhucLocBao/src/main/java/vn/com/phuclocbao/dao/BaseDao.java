package vn.com.phuclocbao.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import vn.com.phuclocbao.bean.PropertyOrder;

public interface BaseDao<T, PK extends Serializable> {
	public T persist(T entity);

	public void remove(T entity);

	public T findById(PK id);

	public T merge(T entity);	
	 
    public List<T> findAll();
 
    public Long getTotalResult();
    
    public T refresh(T entity);
    	
    public List<T> persistList(List<T> entityList);
    
    public void removeList(List<T> entityList);
    

	List<T> mergeList(List<T> entityList);
	 public List<T> findByProperties(Map<String,Object> propertiesMap, int firstResult, int maxResult, List<PropertyOrder> orders) ;

}