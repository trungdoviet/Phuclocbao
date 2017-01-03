package vn.com.phuclocbao.controller;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.validator.LoginUserValidator;
import vn.com.phuclocbao.validator.NewContractValidator;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.LoginBean;



@Controller
@RequestMapping("/")
public class ContractController {
	
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
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
	private static org.apache.log4j.Logger logger = Logger.getLogger(LoginController.class);
	@RequestMapping(value = { "/newContractAction"}, params="save", method = RequestMethod.POST)
	public ModelAndView createContract(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("contractBean") @Validated ContractBean contractBean, 
			BindingResult result, SessionStatus status) {
		
		ModelAndView model = null;
		if (result.hasErrors()) {
			model = new ModelAndView("newContract");
			contractBean = populateFormData(request, contractBean, model);
		} else {
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			contractBean.setCurrentCompany(plbSession.getUserAccount().getCompanyEntity());
			contractBean.getContractDto().getCompany().setId(contractBean.getCurrentCompany().getId());
			try {
				
				contractService.saveNewContract(contractBean.getContractDto());
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
			}
			model = new ModelAndView("home");
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
		model.addObject("contractBean", contractBean);
		return contractBean;
	}
	
	@RequestMapping(value = { "/newContractAction/cancel"}, method = RequestMethod.GET)
	public ModelAndView cancelCreatingContract(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("redirect:/home");
		return model;
	}

}