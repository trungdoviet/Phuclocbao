package vn.com.phuclocbao.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.ManageContractBean;



@Controller
@RequestMapping("/")
public class NavigationController {
	private static org.apache.log4j.Logger logger = Logger.getLogger(NavigationController.class);
	public static final int DEFAULT_PERIOD_OF_PAYMENT = 10;
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public String productsPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HOME);
		return "home";
	}
	
	@RequestMapping(value = { "/mngContracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openManageContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.MANAGE_CONTRACT);
		ModelAndView model = new ModelAndView("mngContracts");
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndId(ContractStatusType.IN_PROGRESS, plbSession.getCompanyId());
			ManageContractBean mcb = contractService.buildManageContractBean(contracts);
			logger.info("Contract Found:" + mcb.getTotalContract());
			model.addObject("mngContract", mcb);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = { "/oldContracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public String openOldContract(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.OLD_CONTRACT);
		return "oldContracts";
	}
	
	@RequestMapping(value = { "/newContract"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openNewContract(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean) {
		ModelAndView model = new ModelAndView("newContract");
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NEW_CONTRACT);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setCurrentCompany(plbSession.getCurrentCompany());
		contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
		Date today = DateTimeUtil.getCurrentDate();
		contractBean.getContractDto().setStartDate(today);
		contractBean.getContractDto().setExpireDate(DateTimeUtil.addMoreDate(today, 30));
		contractBean.getContractDto().setPeriodOfPayment(DEFAULT_PERIOD_OF_PAYMENT);
		contractBean.setSearchedCustomerContract(null);
		contractBean.setCities(VietnamCityService.loadCities());
		System.out.println("==Current company:" + plbSession.getUserAccount().getCompanyEntity());
		model.addObject("contractBean", contractBean);
		return model;
	}

}