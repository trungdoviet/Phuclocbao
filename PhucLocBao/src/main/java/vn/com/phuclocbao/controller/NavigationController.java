package vn.com.phuclocbao.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.viewbean.ContractBean;



@Controller
@RequestMapping("/")
public class NavigationController {
	
	public static final int DEFAULT_PERIOD_OF_PAYMENT = 10;

	@RequestMapping(value = { "/home"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public String productsPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HOME);
		return "home";
	}
	
	@RequestMapping(value = { "/contracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public String openContract(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.CONTRACT);
		return "contracts";
	}
	
	@RequestMapping(value = { "/newContract"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openNewContract(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean) {
		ModelAndView model = new ModelAndView("newContract");
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NEW_CONTRACT);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setCurrentCompany(plbSession.getUserAccount().getCompanyEntity());
		contractBean.getContractDto().getCompany().setId(contractBean.getCurrentCompany().getId());
		Date today = DateTimeUtil.getCurrentDate();
		contractBean.getContractDto().setStartDate(today);
		contractBean.getContractDto().setExpireDate(DateTimeUtil.addMoreDate(today, 30));
		contractBean.getContractDto().setPeriodOfPayment(DEFAULT_PERIOD_OF_PAYMENT);
		DateFormat dateFormat = new SimpleDateFormat(ConstantVariable.DATE_FORMAT);
		contractBean.setSearchedCustomerContract(null);
		System.out.println(dateFormat.format(today));
		contractBean.setCities(VietnamCityService.loadCities());
		System.out.println("==Current company:" + plbSession.getUserAccount().getCompanyEntity());
		model.addObject("contractBean", contractBean);
		return model;
	}

}