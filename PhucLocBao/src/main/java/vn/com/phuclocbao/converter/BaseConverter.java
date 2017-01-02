package vn.com.phuclocbao.converter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import vn.com.phuclocbao.controller.LoginController;
import vn.com.phuclocbao.dto.base.IBaseDTO;
import vn.com.phuclocbao.entity.base.IBaseEntity;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;

public abstract class BaseConverter<D extends IBaseDTO, E extends IBaseEntity> implements ObjectConvertable<D, E>{
	private static org.apache.log4j.Logger logger = Logger.getLogger(LoginController.class);
	
	public D toDto( E entity, D dest, String... ignoredProperties) throws BusinessException{
		String[] allIgnoreProps = new String[0];
		allIgnoreProps = collectAllIgnoredProps(allIgnoreProps, ignoredProperties);
		
		 populateProperty(entity, dest, allIgnoreProps);
		 return toDtoExtraProps(entity, dest);
	}
	
	public E toEntity( D dto, E dest, String... ignoredProperties) throws BusinessException{
		String[] allIgnoreProps = new String[0];
		allIgnoreProps = collectAllIgnoredProps(allIgnoreProps, ignoredProperties);
		return populateProperty(dto, dest, allIgnoreProps);
	}
	
	public E toEntityExtra( D dto, E dest, String... ignoredProperties) throws BusinessException{
		E en = toEntity(dto, dest, ignoredProperties);
		return toEntityExtraProps(dto, en);
	}
	
	
	public String[] getIgnoredProperties(){
		return null;
	}
	
	public org.apache.log4j.Logger getLog(){
		return logger;
	}
	private String[] collectAllIgnoredProps(String[] allIgnoreProps, String... ignoredProperties) {
		String[] defaultIgnoredProps = getIgnoredProperties();
		if(ArrayUtils.isNotEmpty(defaultIgnoredProps)){
			allIgnoreProps = ArrayUtils.addAll(allIgnoreProps, defaultIgnoredProps);
		}
		if(ArrayUtils.isNotEmpty(ignoredProperties)){
			allIgnoreProps = ArrayUtils.addAll(allIgnoreProps, ignoredProperties);
		}
		return allIgnoreProps;
	}
	
	protected <T extends IBaseDTO> T findDTOById(Long id, List<T> dtos){
		if (id != null && CollectionUtils.isNotEmpty(dtos)){
			for(T dto : dtos){
				if(dto.getId().longValue() == id.longValue()){
					return dto;
				}
			}
		}		
		return null;
	}
	
	protected <T extends IBaseDTO, P extends IBaseEntity> boolean isSameId(
			T dto, P entity) {
		return dto.getId() != null
				&& entity.getId().longValue() == dto.getId().longValue();
	}
	public <T> Set<T> convertToSet(List<T> inputList){
		return CollectionUtils.isNotEmpty(inputList) ? new HashSet<T>(inputList): new HashSet<T>();
	}
	
	public <T> List<T> convertToList(Set<T> inputSets){
		return CollectionUtils.isNotEmpty(inputSets) ? new ArrayList<T>(inputSets): new ArrayList<T>();
	}
	protected void ignoreProperty(Map<String, String> properties,
			String... ignoredProperties) {
		if(ArrayUtils.isNotEmpty(ignoredProperties)) {
			for(String ingoredParam : ignoredProperties){
				properties.remove(ingoredParam);
			}
		}
	}
 
 /**
 * Should not use to populate the array. It's just get the first element.
 */
protected <T> T populateProperty(Object source, T dest, String... ignoredProperties) throws BusinessException{
	try{
		DateTimeConverter dtConverter = new DateConverter();
        dtConverter.setPattern("dd.MM.yyyy");
        
		ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();
        convertUtilsBean.deregister(Date.class);
        convertUtilsBean.register(dtConverter, Date.class);
        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());
        Map<String, String> properties = beanUtilsBean.describe(source);
        ignoreProperty(properties, ignoredProperties);
        beanUtilsBean.populate(dest, properties);

        
		//BeanUtils.populate(dest, properties);
	 } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
		 logger.error(e.getMessage(), e);
		throw new BusinessException(PLBErrorCode.CONVERTION_EXCEPTION.name());
	 } 
	 return dest;
 }

	
	protected int getStringAsInteger(String input) {
		if (input != null && !input.isEmpty()) {
			int val = 0;
			try {
				val = Integer.parseInt(input);
			} catch (Exception e) {
				return 0;
			}
			return val;
		}
		return 0;
	}


	
}