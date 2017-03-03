package vn.com.phuclocbao.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.CustomerService;
import vn.com.phuclocbao.viewbean.CustomerSearchBean;
import vn.com.phuclocbao.viewbean.ManageContractBean;



@Controller
@RequestMapping("/")
public class CustomerController extends BaseController {
	
	private static final String MSG_CAN_NOT_FIND_CONTRACT = "msg.contractNotFound";
	private static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	private static org.apache.log4j.Logger logger = Logger.getLogger(CustomerController.class);
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = { "/filterInProgressContract"}, method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterInProgressContractByCustomerName(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("csBean") @Validated CustomerSearchBean customerSearchBean, 
			BindingResult result, SessionStatus status) {
		if(StringUtils.isEmpty(customerSearchBean.getName())){
			return new ModelAndView(MenuDefinition.MANAGE_CONTRACT.getRedirectCommand());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = new ModelAndView(MenuDefinition.MANAGE_CONTRACT.getName());
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndIdAndCustomerName(ContractStatusType.IN_PROGRESS, plbSession.getCompanyId(), customerSearchBean.getName());
			ManageContractBean mcb = contractService.buildManageContractBean(contracts);
			model.addObject("mngContract", mcb);
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}
	
	@RequestMapping(value = { "/filterFinishContract"}, method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterFinishContractByCustomerName(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("csBean") @Validated CustomerSearchBean customerSearchBean, 
			BindingResult result, SessionStatus status) {
		if(StringUtils.isEmpty(customerSearchBean.getName())){
			return new ModelAndView(MenuDefinition.OLD_CONTRACT.getRedirectCommand());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = new ModelAndView(MenuDefinition.OLD_CONTRACT.getName());
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndIdAndCustomerName(ContractStatusType.FINISH, plbSession.getCompanyId(), customerSearchBean.getName());
			ManageContractBean mcb = contractService.buildManageOldContractBean(contracts);
			model.addObject("oldContract", mcb);
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}
	
	@RequestMapping(value = { "/filterBadContract"}, method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterBadContractByCustomerName(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("csBean") @Validated CustomerSearchBean customerSearchBean, 
			BindingResult result, SessionStatus status) {
		if(StringUtils.isEmpty(customerSearchBean.getName())){
			return new ModelAndView(MenuDefinition.BAD_CONTRACT.getRedirectCommand());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = new ModelAndView(MenuDefinition.BAD_CONTRACT.getName());
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndIdAndCustomerName(ContractStatusType.BAD, plbSession.getCompanyId(), customerSearchBean.getName());
			ManageContractBean mcb = contractService.buildManageBadContractBean(contracts);
			model.addObject("badContract", mcb);
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}
	
}