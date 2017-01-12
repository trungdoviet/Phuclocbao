package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.entity.Contract;
import vn.com.phuclocbao.entity.Customer;
import vn.com.phuclocbao.entity.PaymentSchedule;
import vn.com.phuclocbao.entity.TransportOwner;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.ContractType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.LambdaExceptionUtil;
import vn.com.phuclocbao.view.ContractView;
import vn.com.phuclocbao.view.CustomerView;
import vn.com.phuclocbao.view.PropertyDetail;

public class ContractConverter extends BaseConverter<ContractDto, Contract>{

	private static ContractConverter instance;
	private static final String PAYMENT_SCHEDULE_TEMPLATE = "<span id='exPayDate'> Ngày: <strong>{0}</strong></span><span class='glyphicon glyphicon-ok color-check-day' aria-hidden='true'></span> Đã thanh toán ({1}) - Trước {2} ngày";
	private static final String PAYMENT_SCHEDULE_TEMPLATE_NOT_DONE_YET = "<span id='exPayDate'> Ngày: <strong>{0}</strong></span>";
	private static final String PARAM_EXPECTED_PAY_DATE = "{0}";
	private static final String PARAM_PAY_DATE = "{1}";
	private static final String PARAM_PRIOR_TO_DATE = "{2}";
	
	private ContractConverter() {}
	
	public static synchronized  ContractConverter getInstance() {
		if (instance == null) {
			instance = new ContractConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"paymentSchedules", "customer", "owner", "company", "startDate", "expireDate","histories"};
	}

	@Override
	public ContractDto toDtoExtraProps(Contract entity, ContractDto dest) throws BusinessException {
		dest.setStartDate(entity.getStartDate());
		dest.setExpireDate(entity.getExpireDate());
		return dest;
	}
	
	@Override
	public Contract toEntityExtraProps(ContractDto dto, Contract dest) throws BusinessException {
		dest.setStartDate(dto.getStartDate());
		dest.setExpireDate(dto.getExpireDate());
		return dest;
	}

	public Contract toNewContract(ContractDto dto, Contract entity) throws BusinessException {
		entity = this.toEntityExtra(dto, entity, "id");
		entity.setState(ContractStatusType.IN_PROGRESS.getName());
		entity.setCustomer(new Customer());
		entity.setOwner(new TransportOwner());
		entity.setPaymentSchedules(new HashSet<>());
		entity.setCustomer(CustomerConverter.getInstance().toEntity(dto.getCustomer(), entity.getCustomer(),"id"));
		entity.setOwner(TransportOwnerConverter.getInstance().toEntity(dto.getOwner(), entity.getOwner(),"id"));
		entity.setPaymentSchedules(PaymentScheduleConverter.getInstance().toEntities(dto.getPaymentSchedules(),"id"));
		return entity;
	}
	
	public ContractDto toDtoWithCustomer(Contract entity, ContractDto dest) throws BusinessException{
		dest = this.toDto(entity, dest);
		dest.setCustomer(CustomerConverter.getInstance().toDto(entity.getCustomer(), new CustomerDto()));
		return dest;
	}
	
	public List<ContractDto> toDtosWithCustomer(List<Contract> contracts){
		List<ContractDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(contracts)){
			dtos = contracts.stream().map(LambdaExceptionUtil.rethrowFunction(item -> this.toDtoWithCustomer(item, new ContractDto()))).collect(Collectors.toList());
		}
		return dtos;
	}
	
	public ContractView toContractView(Contract contract){
		ContractView view = new ContractView();
		view.setCompanyName(contract.getCompany().getName());
		view.setPhone(contract.getCompany().getPhoneNumber());
		view.setContractType(ContractType.getValueByName(contract.getContractType()).getDesc());
		view.setExpireDate(DateTimeUtil.date2String(contract.getExpireDate()));
		view.setStartDate(DateTimeUtil.date2String(contract.getStartDate()));
		view.setPaymentScheduleDetails(buildPaymentScheduleString(new ArrayList<>(contract.getPaymentSchedules())));
		view.setTotalAmount(contract.getTotalAmount());
		CustomerView customer = new CustomerView();
		customer.setAddress(contract.getCustomer().getAddress());
		customer.setBirthYear(contract.getCustomer().getBirthYear());
		customer.setIdNo(contract.getCustomer().getIdNo());
		customer.setName(contract.getCustomer().getName());
		customer.setPhone(contract.getCustomer().getPhone());
		customer.setProvince(VietnamCityService.getProvinceName( contract.getCustomer().getProvince()));
		view.setCustomerView(customer);
		PropertyDetail detail = new PropertyDetail();
		detail.setChassisFrameNumber(contract.getOwner().getChassisFrameNumber());
		detail.setChassisNumber(contract.getOwner().getChassisNumber());
		detail.setNumberPlate(contract.getOwner().getNumberPlate());
		detail.setType(contract.getOwner().getTransportType());
		view.setPropertyDetail(detail);
		return view;
	}
	private List<String> buildPaymentScheduleString(List<PaymentSchedule> paymentSchedules){
		List<String> result = new ArrayList<String>();
		if(CollectionUtils.isNotEmpty(paymentSchedules)){
			Collections.sort(paymentSchedules, new Comparator<PaymentSchedule>() {
				@Override
				public int compare(PaymentSchedule o1, PaymentSchedule o2) {
					return o1.getExpectedPayDate().compareTo(o2.getExpectedPayDate());
				}
			});
			for (PaymentSchedule item : paymentSchedules) {
				if(item.getPayDate() != null) {
					result.add(PAYMENT_SCHEDULE_TEMPLATE.replace(PARAM_EXPECTED_PAY_DATE, DateTimeUtil.date2String(item.getExpectedPayDate()))
														.replace(PARAM_PAY_DATE, DateTimeUtil.date2String(item.getPayDate()))
														.replace(PARAM_PRIOR_TO_DATE, String.valueOf(DateTimeUtil.calculateDayBetween(item.getExpectedPayDate(), item.getPayDate()))));
				} else {
					result.add(PAYMENT_SCHEDULE_TEMPLATE_NOT_DONE_YET.replace(PARAM_EXPECTED_PAY_DATE, DateTimeUtil.date2String(item.getExpectedPayDate())));
				}
			}
		}
		return result;
	}
	
	
	
	
	
	
}
