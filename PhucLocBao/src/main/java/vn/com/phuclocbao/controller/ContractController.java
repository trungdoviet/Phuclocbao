package vn.com.phuclocbao.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.CustomerContract;
import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.enums.AlertType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.CustomerService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.MessageBundleUtil;
import vn.com.phuclocbao.util.PaymentScheduleParser;
import vn.com.phuclocbao.validator.NewContractValidator;
import vn.com.phuclocbao.viewbean.ContractBean;



@Controller
@RequestMapping("/")
public class ContractController {
	
	private static final String MSG_CREATE_CONTRACT_SUCCESS = "msg.createContractSuccess";

	private static final String MSG_CREATE_CONTRACT_FAILED = "msg.createContractFailed";

	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	@Qualifier("newContractValidator")
	NewContractValidator validator;
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		System.out.println("=======Binding validator");
		 binder.registerCustomEditor(Date.class,     
                 new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true, 10));   
		 binder.setValidator(validator);
	}
	private static org.apache.log4j.Logger logger = Logger.getLogger(ContractController.class);
	@RequestMapping(value = { "/newContractAction"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView createContract(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = new ModelAndView("newContract");
			contractBean = populateFormData(request, contractBean, model);
		} else {
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			contractBean.setCurrentCompany(plbSession.getUserAccount().getCompanyEntity());
			contractBean.getContractDto().getCompany().setId(contractBean.getCurrentCompany().getId());
			try {
				String paymentInfo =  contractBean.getPaidInfo();
				logger.info("payment schedule:" + contractBean.getPaidInfo());
				contractBean.getContractDto().setPaymentSchedules(PaymentScheduleParser.parsePaymentSchedule(paymentInfo));
				boolean isSuccess = contractService.saveNewContract(contractBean.getContractDto());
				if(isSuccess){
					redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CREATE_CONTRACT_SUCCESS));
					redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.SUCCESS.getName());
				} else {
					redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CREATE_CONTRACT_FAILED));
					redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());

				}
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
				redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(MSG_CREATE_CONTRACT_FAILED));
				redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
			}
			model = new ModelAndView("redirect:/home");
		}
		logger.info("AAAAAAAAAAAAAAAAaa:" + contractBean.getContractDto().getStartDate());
		return model;
	}

	private ContractBean populateFormData(HttpServletRequest request, ContractBean contractBean, ModelAndView model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setCurrentCompany(plbSession.getUserAccount().getCompanyEntity());
		contractBean.getContractDto().getCompany().setId(contractBean.getCurrentCompany().getId());
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
	
}