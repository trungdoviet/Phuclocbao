package vn.com.phuclocbao.converter;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.entity.PaymentHistory;
import vn.com.phuclocbao.exception.BusinessException;

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



	
	
	
}
