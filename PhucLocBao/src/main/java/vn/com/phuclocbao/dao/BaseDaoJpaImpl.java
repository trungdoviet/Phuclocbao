package vn.com.phuclocbao.dao;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vn.com.phuclocbao.bean.OrderType;
import vn.com.phuclocbao.bean.PropertyOrder;
public abstract class BaseDaoJpaImpl<T extends Object, PK extends Serializable> implements
		BaseDao<T, PK> {
	protected Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDaoJpaImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass
				.getActualTypeArguments()[0];
		
		

	}
	
	public abstract EntityManager getEm();
	
	/**
	 * For custom query we just using custom Entity Manager (because some disadvantage of ivy)
	 * @see CustomEntityManager
	 * @return the customEntityManager
	 */

	@Override
	public T persist( T entity) {
		getEm().persist(entity);
		getEm().flush();
		return entity;
	}

	@Override
	public T findById(final PK id) {
		return getEm().find(entityClass, id);
	}

	@Override
	public T merge(final T entity) {
		T mergedEntity = getEm().merge(entity);
		 getEm().flush();
		 return mergedEntity;
	}

	@Override
	public void remove(final T entity) {
		getEm().remove(entity);
		getEm().flush();
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		Query query = getEm().createQuery("from " + entityClass.getSimpleName());
		return query.getResultList();
	}

	@Override
	public T refresh(final T entity) {
		getEm().refresh(entity);
		return entity;
	}

	@Override
	public Long getTotalResult() {
		// TODO: not yet implemented
		return null;
	}
	@Override
	public List<T> persistList(List<T> entityList){
		for (int i = 0; i < entityList.size(); i++) {			
			persist(entityList.get(i));
		}		
		getEm().flush();
		return entityList;
	}

	@Override
	public List<T> mergeList(List<T> entityList){
		for (int i = 0; i < entityList.size(); i++) {			
			merge(entityList.get(i));
		}		
		getEm().flush();
		return entityList;
	}
	@Override
	public void removeList(List<T> entityList){
		for (int i = 0; i < entityList.size(); i++) {
			remove(entityList.get(i));			
		}		
		getEm().flush();
	}
	@Override
	public   List<T> findByProperties(Map<String,Object> propertiesMap, int firstResult, int maxResult, List<PropertyOrder> orders) {
        CriteriaBuilder builder = getEm().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(entityClass);
        Root<T> from = criteria.from(entityClass);
        criteria.select(from);
        Predicate   predicate = null;
         
        if (propertiesMap != null) {
            for (Entry<String,Object> entry: propertiesMap.entrySet()) {
                Predicate pred = builder.equal(from.get(entry.getKey()), entry.getValue());
                predicate = predicate == null ? pred : builder.and(predicate, pred);
            }
             
            if (predicate != null) {
            	criteria.where(predicate);
            }
        }
         
        if (orders != null && orders.size() > 0) {
            List<Order> orderList = new ArrayList<Order>();
            for (PropertyOrder order: orders) {
                orderList.add(
                        order.getOrderType() == OrderType.ASC
                            ? builder.asc(from.get(order.getPropertyName()))
                            : builder.desc(from.get(order.getPropertyName()))
                );
            }
            criteria.orderBy(orderList);
        }
         
        TypedQuery<T> typedQuery = getEm().createQuery(criteria);
        if (firstResult >= 0) {
        	typedQuery.setFirstResult(firstResult);
        }
        if (maxResult > 0) {
        	typedQuery.setMaxResults(maxResult);
        }
         
        return typedQuery.getResultList();
    }
}