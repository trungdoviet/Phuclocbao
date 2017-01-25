package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.LambdaExceptionUtil;

public class PaymentHistoryConverter extends BaseConverter<PaymentHistoryDto, PaymentHistory>{

	private static PaymentHistoryConverter instance;
	
	private PaymentHistoryConverter() {}
	
	public static synchronized  PaymentHistoryConverter getInstance() {
		if (instance == null) {
			instance = new PaymentHistoryConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "logDate"};
	}

	@Override
	public PaymentHistoryDto toDtoExtraProps(PaymentHistory entity, PaymentHistoryDto dest)
			throws BusinessException {
		dest.setLogDate(entity.getLogDate());
		return super.toDtoExtraProps(entity, dest);
	}

	public List<PaymentHistoryDto> toDtos(List<PaymentHistory> entities) throws BusinessException {
		List<PaymentHistoryDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(entities)){
			dtos = entities.stream()
					.map(LambdaExceptionUtil.rethrowFunction( item ->  this.toDto(item, new PaymentHistoryDto())))
					.sorted((o1,o2)->o2.getLogDate().compareTo(o1.getLogDate()))
					.collect(Collectors.toList());
					
		}
		return dtos;
	}


	
	
	
}
