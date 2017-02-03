package vn.com.phuclocbao.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.ContractResponseBody;
import vn.com.phuclocbao.bean.CustomerContract;
import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.CustomerDto;
import vn.com.phuclocbao.dto.PaymentScheduleDto;
import vn.com.phuclocbao.enums.AlertType;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.CustomerService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.MessageBundleUtil;
import vn.com.phuclocbao.util.PaymentScheduleParser;
import vn.com.phuclocbao.util.PlbUtil;
import vn.com.phuclocbao.validator.NewContractValidator;
import vn.com.phuclocbao.validator.OldContractValidator;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.NotificationPage;
import vn.com.phuclocbao.vo.UserActionParamVO.UserActionParamVOBuilder;



@Controller
@RequestMapping("/")
public class ContractController {
	
	private static final String MSG_CONTRACT_UPDATE_SUCCESS = "msg.contractUpdateSuccess";

	private static final String MSG_CONTRACT_UPDATE_FAILED = "msg.contractUpdateFailed";

	private static final String MSG_CREATE_CONTRACT_SUCCESS = "msg.createContractSuccess";

	private static final String MSG_CREATE_CONTRACT_FAILED = "msg.createContractFailed";

	private static final String MSG_CAN_NOT_FIND_CONTRACT = "msg.contractNotFound";
	private static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	private static org.apache.log4j.Logger logger = Logger.getLogger(ContractController.class);
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	@Qualifier("newContractValidator")
	NewContractValidator validator;
	
	@Autowired
	@Qualifier("oldContractValidator")
	OldContractValidator oldValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println("=======Binding validator");
		 binder.registerCustomEditor(Date.class,     
                 new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));
		/* ContractBean contractBean = (ContractBean) binder.getTarget();
		 if(StringUtils.isEmpty(contractBean.getProcessStaging()) || contractBean.getProcessStaging().equalsIgnoreCase(ProcessStaging.NEW.getName())){
			 binder.setValidator(validator);
		 } else {
			 binder.setValidator(oldValidator);
		 }*/
	}
	
	@RequestMapping(value = { "/newContractAction"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView createContract(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		validator.validate(contractBean, result);
		contractBean.setProcessStaging(ProcessStaging.NEW.getName());
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = new ModelAndView("newContract");
			contractBean = populateFormData(request, contractBean, model);
		} else {
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			contractBean.setCurrentCompany(plbSession.getCurrentCompany());
			contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
			try {
				String paymentInfo =  contractBean.getPaidInfo();
				logger.info("payment schedule:" + contractBean.getPaidInfo());
				contractBean.getContractDto().setPaymentSchedules(PaymentScheduleParser.parsePaymentSchedule(paymentInfo));
				boolean isSuccess = contractService.saveNewContract(contractBean.getContractDto(), UserActionParamVOBuilder.createBuilder()
																															.setUsername(plbSession.getUserAccount().getUsername())
																															.build());
				if(isSuccess){
					showSucessAlert(redirectAttributes, MSG_CREATE_CONTRACT_SUCCESS);
				} else {
					showErrorAlert(redirectAttributes, MSG_CREATE_CONTRACT_FAILED);
				}
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
				showErrorAlert(redirectAttributes, MSG_CREATE_CONTRACT_FAILED);
			}
			model = new ModelAndView("redirect:/home");
		}
		return model;
	}

	private ContractBean populateFormData(HttpServletRequest request, ContractBean contractBean, ModelAndView model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setCurrentCompany(plbSession.getCurrentCompany());
		contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
		contractBean.setCities(VietnamCityService.loadCities());
		if(contractBean.getContractDto().getPeriodOfPayment() == null || contractBean.getContractDto().getPeriodOfPayment()  <= 0){
			contractBean.getContractDto().setPeriodOfPayment(NavigationController.DEFAULT_PERIOD_OF_PAYMENT);
		}
		if(plbSession.getContractResponseBody() != null){
			List<CustomerContract> searchedCustomers = plbSession.getContractResponseBody().getCustomerContracts();
			CustomerContract selectedContract = findCustomerContractbyIdNo(contractBean.getContractDto().getCustomer().getIdNo(), searchedCustomers);
			contractBean.setSearchedCustomerContract(selectedContract);
		}
		model.addObject("contractBean", contractBean);
		return contractBean;
	}
	
	private CustomerContract findCustomerContractbyIdNo(String idNo, List<CustomerContract> searchedCustomers){
		if(CollectionUtils.isNotEmpty(searchedCustomers)){
			for (CustomerContract customerContract : searchedCustomers) {
				if(customerContract.getIdNo().equalsIgnoreCase(idNo)){
					return customerContract;
				}
			}
		}
		return null;
	}
	
	@RequestMapping(value = { "/newContractAction/cancel"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView cancelCreatingContract(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("redirect:/home");
		return model;
	}
	
	@RequestMapping(value = { "/mngContract/cancel"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView cancelUpdateInprogressContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = PlbUtil.getPlbSession(request);
		ModelAndView model = new ModelAndView(plbSession.getMenuBean().getDefActiveMenu().getRedirectCommand());
		return model;
	}
	@RequestMapping(value = { "/oldContract/cancel"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView cancelUpdateOldContract(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("redirect:/oldContracts");
		return model;
	}
	
	// show update form
	@RequestMapping(value = "/contract/{id}/paid", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showPaidContractForm(HttpServletRequest request, @PathVariable("id") int id, Model model, ContractBean contractBean) {
		ModelAndView modelView = new ModelAndView("contract");
		logger.info("open Form : {}" + id);
		try {
			populateContractForm(request, id, contractBean, ProcessStaging.PAID.getName());
			model.addAttribute("contractBean", contractBean);
		} catch (BusinessException e) {
			model.addAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CAN_NOT_FIND_CONTRACT));
			model.addAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
			logger.error(e);
			e.printStackTrace();
		}
		return modelView;

	}
	// show update form
		@RequestMapping(value = "/contract/{id}/update", method = {RequestMethod.GET, RequestMethod.POST})
		public ModelAndView showUpdateContractForm(HttpServletRequest request, @PathVariable("id") int id, Model model, ContractBean contractBean) {
			ModelAndView modelView = new ModelAndView("contract");
			logger.info("open Form : {}" + id);
			try {
				populateContractForm(request, id, contractBean, ProcessStaging.UPDATE.getName());
				model.addAttribute("contractBean", contractBean);
			} catch (BusinessException e) {
				model.addAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CAN_NOT_FIND_CONTRACT));
				model.addAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
				logger.error(e);
				e.printStackTrace();
			}
			return modelView;

		}
	
	@RequestMapping(value = "/contract/{id}/payoff", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView showPayoffContractForm(HttpServletRequest request, @PathVariable("id") int id, Model model, ContractBean contractBean) {
		ModelAndView modelView = new ModelAndView("contract");
		logger.info("open Form : {}" + id);
		try {
			populateContractForm(request, id, contractBean,ProcessStaging.PAYOFF.getName());
			contractBean.getContractDto().setPayoffDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("contractBean", contractBean);
		} catch (BusinessException e) {
			model.addAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CAN_NOT_FIND_CONTRACT));
			model.addAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
			logger.error(e);
			e.printStackTrace();
		}
		return modelView;

	}
	
	@RequestMapping(value = { "/contract/{id}/saveContract"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView paidContract(HttpServletRequest request,  @PathVariable("id") int id, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		oldValidator.validate(contractBean, result);
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = reloadUpdateContractPage(request, id, contractBean,ProcessStaging.PAID);
		} else {
			logger.info("phone==" + contractBean.getContractDto().getCustomer().getPhone());
			
			PLBSession plbSession = PlbUtil.getPlbSession(request);
			model = new ModelAndView(plbSession.getMenuBean().getDefActiveMenu().getRedirectCommand());
			contractBean.setProcessStaging(ProcessStaging.PAID.getName());
			contractBean.getContractDto().setId(id);
			contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
			contractBean.getContractDto().setPaymentSchedules(PaymentScheduleParser.parsePaymentSchedule(contractBean.getPaidInfo()));
			try {
				ContractDto updatedContract = contractService.updateContractInPaidTime(contractBean.getContractDto(), UserActionParamVOBuilder.createBuilder()
																																	.setUsername(plbSession.getUserAccount().getUsername())
																																	.build());
				if(updatedContract != null){
					showSucessAlert(redirectAttributes, MSG_CONTRACT_UPDATE_SUCCESS);
				}
			} catch (BusinessException e) {
				showErrorAlert(redirectAttributes, MSG_CONTRACT_UPDATE_FAILED);
				e.printStackTrace();
				logger.error(e);
			}
		}
		return model;
	}
	
	@RequestMapping(value = { "/contract/{id}/saveContract"}, params="savedraft", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView saveAsDraftContract(HttpServletRequest request,  @PathVariable("id") int id, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		oldValidator.validate(contractBean, result);
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = reloadUpdateContractPage(request, id, contractBean,ProcessStaging.PAYOFF);
		} else {
			logger.info("phone==" + contractBean.getContractDto().getCustomer().getPhone());
			PLBSession plbSession = PlbUtil.getPlbSession(request);
			model = new ModelAndView(plbSession.getMenuBean().getDefActiveMenu().getRedirectCommand());
			contractBean.setProcessStaging(ProcessStaging.PAYOFF.getName());
			contractBean.getContractDto().setId(id);
			contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
			contractBean.getContractDto().setPaymentSchedules(PaymentScheduleParser.parsePaymentSchedule(contractBean.getPaidInfo()));
			try {
				ContractDto updatedContract = contractService.updateAsDraftContractInPayOffTime(contractBean.getContractDto(), UserActionParamVOBuilder.createBuilder()
																																	.setUsername(plbSession.getUserAccount().getUsername())
																																	.build());
				if(updatedContract != null){
					showSucessAlert(redirectAttributes, MSG_CONTRACT_UPDATE_SUCCESS);
				}
			} catch (BusinessException e) {
				showErrorAlert(redirectAttributes, MSG_CONTRACT_UPDATE_FAILED);
				e.printStackTrace();
				logger.error(e);
			}
		}
		return model;
	}
	
	@RequestMapping(value = { "/contract/{id}/saveContract"}, params="payoff", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView payOffContract(HttpServletRequest request,  @PathVariable("id") int id, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		oldValidator.validate(contractBean, result);
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = reloadUpdateContractPage(request, id, contractBean, ProcessStaging.PAYOFF);
		} else {
			logger.info("phone==" + contractBean.getContractDto().getCustomer().getPhone());
			PLBSession plbSession = PlbUtil.getPlbSession(request);
			model = new ModelAndView(plbSession.getMenuBean().getDefActiveMenu().getRedirectCommand());
			contractBean.setProcessStaging(ProcessStaging.PAYOFF.getName());
			contractBean.getContractDto().setId(id);
			contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
			contractBean.getContractDto().setPaymentSchedules(PaymentScheduleParser.parsePaymentSchedule(contractBean.getPaidInfo()));
			try {
				ContractDto updatedContract = contractService.updateContractInPayOffTime(contractBean.getContractDto(), UserActionParamVOBuilder.createBuilder()
																																.setUsername(plbSession.getUserAccount().getUsername())
																																.build());
				if(updatedContract != null){
					showSucessAlert(redirectAttributes, MSG_CONTRACT_UPDATE_SUCCESS);
				}
			} catch (BusinessException e) {
				showErrorAlert(redirectAttributes, MSG_CONTRACT_UPDATE_FAILED);
				e.printStackTrace();
				logger.error(e);
			}
		}
		return model;
	}
	

	@RequestMapping(value = { "/contract/{id}/saveContract"}, params="update", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView updateOldContract(HttpServletRequest request,  @PathVariable("id") int id, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		oldValidator.validate(contractBean, result);
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = reloadUpdateContractPage(request, id, contractBean,ProcessStaging.UPDATE);
		} else {
			model = new ModelAndView("redirect:/oldContracts");
			PLBSession plbSession = PlbUtil.getPlbSession(request);
			contractBean.setProcessStaging(ProcessStaging.UPDATE.getName());
			contractBean.getContractDto().setId(id);
			contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
			try {
				ContractDto updatedContract = contractService.updateOldContract(contractBean.getContractDto(), UserActionParamVOBuilder.createBuilder()
																																.setUsername(plbSession.getUserAccount().getUsername())
																																.build());
				if(updatedContract != null){
					showSucessAlert(redirectAttributes, MSG_CONTRACT_UPDATE_SUCCESS);
				}
			} catch (BusinessException e) {
				showErrorAlert(redirectAttributes, MSG_CONTRACT_UPDATE_FAILED);
				e.printStackTrace();
				logger.error(e);
			}
		}
		return model;
	}

	private void showErrorAlert(final RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
	}

	private void showSucessAlert(final RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.SUCCESS.getName());
	}

	private ModelAndView reloadUpdateContractPage(HttpServletRequest request, int id, ContractBean contractBean, ProcessStaging staging) {
		ModelAndView model;
		model = new ModelAndView("contract");
		try{
			populateContractForm(request, id, contractBean, staging.getName());
		} catch (BusinessException e) {
			model.addObject(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CAN_NOT_FIND_CONTRACT));
			model.addObject(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}

	private void populateContractForm(HttpServletRequest request, int id, ContractBean contractBean, String processStaging)
			throws BusinessException {
		PLBSession plbSession = PlbUtil.getPlbSession(request);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setProcessStaging(processStaging);
		ContractDto dto = contractService.findContractDtoById(id, plbSession.getCompanyId());
		contractBean.setCities(VietnamCityService.loadCities());
		contractBean.setCurrentCompany(plbSession.getCurrentCompany());
		if(StringUtils.isEmpty(contractBean.getPaidInfo())){
			contractBean.setPaidInfo(PaymentScheduleParser.parsePaymentInfoString(dto.getPaymentSchedules()));
		}
		contractBean.setContractDto(dto);
		if(ProcessStaging.PAYOFF.getName().equalsIgnoreCase(processStaging)){
			Double totalRefunding = calculateRefundWhenPayoff(contractBean, dto);
			contractBean.setTotalRefunding(totalRefunding);
		}
		
		List<CustomerDto> dtos = customerService.getCustomersByIdNo(dto.getCustomer().getIdNo());
		ContractResponseBody responseContract = customerService.buildContractResponse(dtos);
		if(responseContract != null){
			List<CustomerContract> searchedCustomers = responseContract.getCustomerContracts();
			CustomerContract selectedContract = findCustomerContractbyIdNo(contractBean.getContractDto().getCustomer().getIdNo(), searchedCustomers);
			selectedContract.getContracts().removeIf(item -> item.getId().equals(id));
			contractBean.setSearchedCustomerContract(selectedContract);
		}
	}

	private Double calculateRefundWhenPayoff( ContractBean contractBean, ContractDto dto) {
		Double totalRefunding = 0D;
		PaymentScheduleDto latestPaid = PlbUtil.getLatestPaid(dto.getPaymentSchedules());
		Date targetDate = null;
		Date today = DateTimeUtil.getCurrentDateWithoutTime();
		if(latestPaid != null){
			targetDate = latestPaid.getExpectedPayDate();
		} else {
			targetDate = dto.getStartDate();
		}
		long substractionDays = DateTimeUtil.daysBetweenDates(today, targetDate);
		contractBean.setSubtractionDays(String.valueOf(substractionDays).replaceFirst("-", ""));
		totalRefunding = (substractionDays * dto.getFeeADay());
		contractBean.setTotalRefunding(totalRefunding);
		return totalRefunding;
	}
	
	
	@RequestMapping(value = { "/notification"}, params="search", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView searchNotification(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("notificationPage") NotificationPage ncPage, 
			BindingResult result, SessionStatus status) {
		
		ModelAndView model = new ModelAndView("notificationContract");
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NOTIFICATION);
		if(ncPage == null){
			ncPage = new NotificationPage();
		}
		ncPage.setCurrentCompany(plbSession.getCurrentCompany());
		try {
			List<ContractDto> contracts = contractService.getNotifiedContractBySpecificDateAndCompanyId(ncPage.getSelectedDate(), plbSession.getCompanyId());
			if(CollectionUtils.isNotEmpty(contracts)){
				ncPage.setContracts(contractService.convertToNotificationBeans(ncPage.getSelectedDate(), contracts));
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		model.addObject("notificationPage", ncPage);
		return model;
	}
	private void showErrorAlert(ModelAndView model, String message) {
		model.addObject(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		model.addObject(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
	}
}