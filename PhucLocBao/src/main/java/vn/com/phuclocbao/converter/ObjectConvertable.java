package vn.com.phuclocbao.converter;

import vn.com.phuclocbao.dto.base.IBaseDTO;
import vn.com.phuclocbao.entity.base.IBaseEntity;
import vn.com.phuclocbao.exception.BusinessException;

public interface ObjectConvertable<D extends IBaseDTO, E extends IBaseEntity> {
	public D toDto( E entity, D dest, String... ignoredProperties) throws BusinessException;
	public default D toDtoExtraProps(E entity, D dest) throws BusinessException{
		return dest;
	}
	public default D doExtra(E entity, D dest) throws BusinessException{
		return dest;
	}
	
	public default D doSimple(E entity, D dest) throws BusinessException{
		return dest;
	}
	public default D toDtoExtraObject( E entity, D dest, String... ignoredProperties) throws BusinessException{
		D dto = toDto(entity, dest, ignoredProperties);
		return doExtra(entity, dto);
	}
	
	public default D toDtoSimpleObject( E entity, D dest, String... ignoredProperties) throws BusinessException{
		D dto = toDto(entity, dest, ignoredProperties);
		return doSimple(entity, dto);
	}
	
	
	public default E toEntityExtraProps(D dto, E dest) throws BusinessException{
		return dest;
	}
}
