package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.entity.PaymentSchedule;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.util.LambdaExceptionUtil;

public class PaymentScheduleConverter extends BaseConverter<PaymentScheduleDto, PaymentSchedule>{

	private static PaymentScheduleConverter instance;
	
	private PaymentScheduleConverter() {}
	
	public static synchronized  PaymentScheduleConverter getInstance() {
		if (instance == null) {
			instance = new PaymentScheduleConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{ "contract", "payDate", "notifiedDate","expectedPayDate"};
	}

	@Override
	public PaymentSchedule toEntityExtraProps(PaymentScheduleDto dto, PaymentSchedule dest) throws BusinessException {
		dest.setPayDate(dto.getPayDate());
		dest.setNotifiedDate(dto.getNotifiedDate());
		dest.setExpectedPayDate(dto.getExpectedPayDate());
		return dest;
	}
	
	

	@Override
	public PaymentScheduleDto toDtoExtraProps(PaymentSchedule entity, PaymentScheduleDto dest)
			throws BusinessException {
		dest.setPayDate(entity.getPayDate());
		dest.setExpectedPayDate(entity.getExpectedPayDate());
		dest.setNotifiedDate(entity.getNotifiedDate());
		return dest;
	}

	public List<PaymentScheduleDto> toDtos(Set<PaymentSchedule> paymentSchedules, String... ignoredProperties) throws BusinessException{
		List<PaymentScheduleDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(paymentSchedules)){
			paymentSchedules.forEach(LambdaExceptionUtil.rethrowConsumer(item -> {
				PaymentScheduleDto dto = this.toDto(item, new PaymentScheduleDto(), ignoredProperties);
				dtos.add(dto);
			}));
		}
		return dtos;
	}
	
	public Set<PaymentSchedule> toEntities(List<PaymentScheduleDto> paymentSchedules, String... ignoredProperties) throws BusinessException{
		Set<PaymentSchedule> entities = new HashSet<>();
		if(CollectionUtils.isNotEmpty(paymentSchedules)){
			paymentSchedules.forEach(LambdaExceptionUtil.rethrowConsumer(item -> {
				PaymentSchedule entity = this.toEntityExtra(item, new PaymentSchedule(), ignoredProperties);
				entities.add(entity);
			}));
		}
		return entities;
	}

	public Set<PaymentSchedule> updatePaymentSchedules(List<PaymentScheduleDto> dtos, Set<PaymentSchedule> entities){
		if(CollectionUtils.isNotEmpty(entities) && CollectionUtils.isNotEmpty(dtos)){
			entities.forEach(item ->{
				PaymentScheduleDto dto = findPaymentSchedulebyExpectedDate(dtos, item.getExpectedPayDate());
				if(dto != null){
					item.setPayDate(dto.getPayDate());
					item.setFinish(dto.getFinish());
				}
			});
		}
		return entities;
	}
	
	private PaymentScheduleDto findPaymentSchedulebyExpectedDate(List<PaymentScheduleDto> dtos, Date expectedDate){
		Optional<PaymentScheduleDto> result = dtos.stream().filter(item -> item.getExpectedPayDate().compareTo(expectedDate) == 0).findFirst();
		if(result.isPresent()){
			return result.get();
		}
		return null;
	}
	
	
}
