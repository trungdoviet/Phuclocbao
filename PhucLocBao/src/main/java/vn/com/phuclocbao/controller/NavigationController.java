package vn.com.phuclocbao.controller;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.enums.AlertType;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.CompanyService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.util.MessageBundleUtil;
import vn.com.phuclocbao.viewbean.CompanyFinancialBean;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.ManageContractBean;
import vn.com.phuclocbao.viewbean.NotificationPage;
import vn.com.phuclocbao.viewbean.PaymentHistoryView;



@Controller
@RequestMapping("/")
public class NavigationController {
	private static final String MSG_CANNOT_FIND_COMPANY = "msg.cannotFindCompany";
	private static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	private static org.apache.log4j.Logger logger = Logger.getLogger(NavigationController.class);
	public static final int DEFAULT_PERIOD_OF_PAYMENT = 10;
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	@Autowired
	@Qualifier(value="companyService")
	CompanyService companyService;
	
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public String productsPage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HOME);
		reloadCompanySession(null, request);
		return "home";
	}
	
	private void reloadCompanySession(ModelAndView model,  HttpServletRequest request){
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		try {
			CompanyDto company = companyService.findById(plbSession.getCompanyId());
			plbSession.setCurrentCompany(company);
			if(plbSession.getUserAccount().getCompanyEntity().getId().equals(company.getId())){
				plbSession.getUserAccount().setCompanyEntity(company);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			logger.error(e);
			if(model != null){
				showErrorAlert(model, MSG_CANNOT_FIND_COMPANY);
			}
		}
	}
	
	@RequestMapping(value = { "/mngContracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openManageContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.MANAGE_CONTRACT);
		ModelAndView model = new ModelAndView("mngContracts");
		reloadCompanySession(model, request);
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
	public ModelAndView openOldContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.OLD_CONTRACT);
		ModelAndView model = new ModelAndView("oldContracts");
		reloadCompanySession(model, request);
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndId(ContractStatusType.FINISH, plbSession.getCompanyId());
			ManageContractBean mcb = contractService.buildManageOldContractBean(contracts);
			logger.info("Contract Found:" + mcb.getTotalContract());
			model.addObject("oldContract", mcb);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = { "/newContract"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openNewContract(HttpServletRequest request, HttpServletResponse response, ContractBean contractBean) {
		ModelAndView model = new ModelAndView("newContract");
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NEW_CONTRACT);
		reloadCompanySession(model, request);
		if(contractBean == null){
			contractBean = new ContractBean();
		}
		contractBean.setProcessStaging(ProcessStaging.NEW.getName());
		contractBean.setCurrentCompany(plbSession.getCurrentCompany());
		contractBean.getContractDto().getCompany().setId(plbSession.getCompanyId());
		Date today = DateTimeUtil.getCurrentDate();
		contractBean.getContractDto().setStartDate(today);
		contractBean.getContractDto().setExpireDate(DateTimeUtil.addMoreDate(today, 30));
		contractBean.getContractDto().setPeriodOfPayment(DEFAULT_PERIOD_OF_PAYMENT);
		contractBean.setSearchedCustomerContract(null);
		contractBean.setCities(VietnamCityService.loadCities());
		model.addObject("contractBean", contractBean);
		return model;
	}
	
	@RequestMapping(value = { "/notificationContract"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openNotification(HttpServletRequest request, HttpServletResponse response, NotificationPage ncPage) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.NOTIFICATION);
		ModelAndView model = new ModelAndView("notificationContract");
		reloadCompanySession(model, request);
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

	

	@RequestMapping(value = { "/dailyWorks"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openDailyWorks(HttpServletRequest request, HttpServletResponse response, PaymentHistoryView paymentHistory) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.DAILY_WORK);
		ModelAndView model = new ModelAndView("dailyWorks");
		if(paymentHistory == null){
			paymentHistory = new PaymentHistoryView();
		}
		try {
			List<PaymentHistoryDto> paymentDtos = paymentHistoryService.getHistories(plbSession.getCompanyId(), paymentHistory.getStartDate(), paymentHistory.getEndDate());
			paymentHistory.setPaymentHistories(paymentDtos);
			model.addObject("historyView", paymentHistory);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		reloadCompanySession(model, request);
		return model;
	}
	
	@RequestMapping(value = { "/history"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openHistory(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HISTORY);
		ModelAndView model = new ModelAndView("history");
		
		
		reloadCompanySession(model, request);
		
		return model;
	}
	
	@RequestMapping(value = { "/badContracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openBadContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.BAD_CONTRACT);
		ModelAndView model = new ModelAndView(MenuDefinition.BAD_CONTRACT.getName());
		reloadCompanySession(model, request);
		return model;
	}
	
	@RequestMapping(value = { "/companyFinancial"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCompanyFinancial(HttpServletRequest request, HttpServletResponse response, CompanyFinancialBean cfBean) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_FINANCIAL);
		
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_FINANCIAL.getName());
		if(cfBean == null){
			cfBean = new CompanyFinancialBean();
		}
		cfBean.setCities(VietnamCityService.loadCities());
		reloadCompanySession(model, request);
		cfBean.setCompany(plbSession.getCurrentCompany());
		model.addObject("cfBean", cfBean);
		return model;
	}
	
	@RequestMapping(value = { "/companyBranch"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCompanyBranch(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_BRANCH);
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_BRANCH.getName());
		return model;
	}
	
	private void showErrorAlert(ModelAndView model, String message) {
		model.addObject(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		model.addObject(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
	}

}