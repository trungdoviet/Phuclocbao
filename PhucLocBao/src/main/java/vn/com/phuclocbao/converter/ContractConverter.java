package vn.com.phuclocbao.converter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.dto.TransportOwnerDto;
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
	private static final String PAYMENT_SCHEDULE_TEMPLATE = "<span id='exPayDate'> Ngày: <strong>{0}</strong></span><span class='glyphicon glyphicon-ok color-check-day' aria-hidden='true'></span> Đã thanh toán ({1}) - {2} {3} ngày";
	private static final String PAYMENT_SCHEDULE_TEMPLATE_NOT_DONE_YET = "<span id='exPayDate'> Ngày: <strong>{0}</strong></span>";
	private static final String PARAM_EARLIER = "Sớm";
	private static final String PARAM_LATE = "Trễ";
	private static final String PARAM_EXPECTED_PAY_DATE = "{0}";
	private static final String PARAM_PAY_DATE = "{1}";
	private static final String PARAM_POS_DATE = "{2}";
	private static final String PARAM_PRIOR_TO_DATE = "{3}";
	
	private ContractConverter() {}
	
	public static synchronized  ContractConverter getInstance() {
		if (instance == null) {
			instance = new ContractConverter();
		}
		return instance;
	}

	@Override
	public String[] getIgnoredProperties() {
		return new String[]{"paymentSchedules", "customer", "owner", "company", "startDate", "expireDate","payoffDate","histories", "totalContractDays"};
	}

	@Override
	public ContractDto toDtoExtraProps(Contract entity, ContractDto dest) throws BusinessException {
		dest.setStartDate(entity.getStartDate());
		dest.setExpireDate(entity.getExpireDate());
		dest.setPayoffDate(entity.getPayoffDate());
		return dest;
	}
	
	@Override
	public Contract toEntityExtraProps(ContractDto dto, Contract dest) throws BusinessException {
		dest.setStartDate(dto.getStartDate());
		dest.setExpireDate(dto.getExpireDate());
		dest.setPayoffDate(dto.getPayoffDate());
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
	
	public ContractDto toContract(ContractDto dto, Contract entity) throws BusinessException {
		dto = this.toDto(entity, dto);
		dto.setCustomer(new CustomerDto());
		dto.setOwner(new TransportOwnerDto());
		dto.setPaymentSchedules(new ArrayList<>());
		dto.setCustomer(CustomerConverter.getInstance().toDto(entity.getCustomer(), new CustomerDto()));
		dto.setOwner(TransportOwnerConverter.getInstance().toDto(entity.getOwner(), new TransportOwnerDto()));
		dto.setPaymentSchedules(PaymentScheduleConverter.getInstance().toDtos(entity.getPaymentSchedules()));
		return dto;
	}
	
	public Contract updateContractInPaidTime(ContractDto dto, Contract entity) throws BusinessException {
		updateContract(dto, entity);
		return entity;
	}

	private void updateContract(ContractDto dto, Contract entity) {
		entity.getCustomer().setPhone(dto.getCustomer().getPhone());
		entity.setNote(dto.getNote());
		entity.getOwner().setDetail(dto.getOwner().getDetail());
		List<PaymentScheduleDto> paymentDtos = dto.getPaymentSchedules();
		Set<PaymentSchedule> payments = entity.getPaymentSchedules();
		entity.setPaymentSchedules(PaymentScheduleConverter.getInstance().updatePaymentSchedules(paymentDtos, payments));
	}
	public Contract updateAsDraftContractInPayOffTime(ContractDto dto, Contract entity) throws BusinessException {
		updateContract(dto, entity);
		entity.setPayoffDate(dto.getPayoffDate());
		entity.setCustomerDebt(dto.getCustomerDebt());
		entity.setCompanyDebt(dto.getCompanyDebt());
		return entity;
	}
	
	public Contract updateOldContract(ContractDto dto, Contract entity) throws BusinessException {
		entity.getCustomer().setPhone(dto.getCustomer().getPhone());
		entity.setNote(dto.getNote());
		entity.getOwner().setDetail(dto.getOwner().getDetail());
		return entity;
	}
	
	public Contract updateContractInPayOffTime(ContractDto dto, Contract entity) throws BusinessException {
		updateContract(dto, entity);
		entity.setState(ContractStatusType.FINISH.getName());
		entity.setPayoffDate(dto.getPayoffDate());
		entity.setCustomerDebt(dto.getCustomerDebt());
		entity.setCompanyDebt(dto.getCompanyDebt());
		return entity;
	}
	
	public ContractDto toDtoWithCustomer(Contract entity, ContractDto dest) throws BusinessException{
		dest = this.toDto(entity, dest);
		dest.setCustomer(CustomerConverter.getInstance().toDto(entity.getCustomer(), new CustomerDto()));
		return dest;
	}
	
	private ContractDto toDtoWithCustomerAndPayments(Contract entity, ContractDto dest) throws BusinessException{
		dest = this.toDto(entity, dest);
		dest.setCustomer(CustomerConverter.getInstance().toDto(entity.getCustomer(), new CustomerDto()));
		dest.setPaymentSchedules(PaymentScheduleConverter.getInstance().toDtos(entity.getPaymentSchedules()));
		return dest;
	}
	
	public List<ContractDto> toDtosWithCustomerAndPayments(List<Contract> contracts){
		List<ContractDto> dtos = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(contracts)){
			dtos = contracts.stream().map(LambdaExceptionUtil.rethrowFunction(item -> this.toDtoWithCustomerAndPayments(item, new ContractDto()))).collect(Collectors.toList());
		}
		return dtos;
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
					long days = DateTimeUtil.calculateDayBetween(item.getPayDate(), item.getExpectedPayDate());
					String posInTime = "";
					if(days >= 0) {
						posInTime = PARAM_EARLIER;
					} else {
						posInTime = PARAM_LATE;
					}
					result.add(PAYMENT_SCHEDULE_TEMPLATE.replace(PARAM_EXPECTED_PAY_DATE, DateTimeUtil.date2String(item.getExpectedPayDate()))
														.replace(PARAM_PAY_DATE, DateTimeUtil.date2String(item.getPayDate()))
														.replace(PARAM_POS_DATE, posInTime)
														.replace(PARAM_PRIOR_TO_DATE, String.valueOf(days)));
				} else {
					result.add(PAYMENT_SCHEDULE_TEMPLATE_NOT_DONE_YET.replace(PARAM_EXPECTED_PAY_DATE, DateTimeUtil.date2String(item.getExpectedPayDate())));
				}
			}
		}
		return result;
	}
	
	
	
	
	
	
}
