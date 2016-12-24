package vn.com.phuclocbao.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.viewbean.ContractBean;



@Controller
@RequestMapping("/")
public class NavigationController {
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET)
	public String productsPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HOME);
		return "home";
	}
	
	@RequestMapping(value = { "/contracts"}, method = RequestMethod.GET)
	public String openContract(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.CONTRACT);
		return "contracts";
	}
	
	@RequestMapping(value = { "/newContract"}, method = RequestMethod.GET)
	public ModelAndView openNewContract(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean) {
		ModelAndView model = new ModelAndView("newContract");
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NEW_CONTRACT);
		model.addObject("contractBean", contractBean);
		return model;
	}

}