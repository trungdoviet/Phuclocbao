package vn.com.phuclocbao.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.delegator.LoginDelegator;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.dto.CompanyTypeDto;
import vn.com.phuclocbao.dto.ContractDto;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.enums.ContractStatusType;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.ProcessStaging;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.CompanyService;
import vn.com.phuclocbao.service.CompanyTypeService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.service.UserHistoryService;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.viewbean.CompanyBranchBean;
import vn.com.phuclocbao.viewbean.CompanyFinancialBean;
import vn.com.phuclocbao.viewbean.CompanyProfitBean;
import vn.com.phuclocbao.viewbean.ContractBean;
import vn.com.phuclocbao.viewbean.CustomerSearchBean;
import vn.com.phuclocbao.viewbean.GeneralView;
import vn.com.phuclocbao.viewbean.ManageContractBean;
import vn.com.phuclocbao.viewbean.ManageUserBean;
import vn.com.phuclocbao.viewbean.NotificationPage;
import vn.com.phuclocbao.viewbean.PaymentHistoryView;
import vn.com.phuclocbao.viewbean.UserActionHistoryView;
import vn.com.phuclocbao.viewbean.UserPasswordBean;
import vn.com.phuclocbao.viewbean.UserSettingBean;



@Controller
@RequestMapping("/")
public class NavigationController extends BaseController {
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
	@Qualifier(value="companyTypeService")
	CompanyTypeService companyTypeService;
	@Autowired
	private LoginDelegator loginDelegate;
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	@Autowired
	UserHistoryService userHistoryService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = { "/home"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView productsPage(HttpServletRequest request, HttpServletResponse response, GeneralView gv) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HOME);
		ModelAndView model = new ModelAndView(MenuDefinition.HOME.getName());
		try {
			gv = contractService.collectStatistic(plbSession.getCompanyId());
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		model.addObject("generalView", gv);
		reloadCompanySession(model, request);
		return model;
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
		long startTime = System.currentTimeMillis();
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.MANAGE_CONTRACT);
		ModelAndView model = new ModelAndView("mngContracts");
		reloadCompanySession(model, request);
		try {
			
			List<ContractDto> contracts = contractService.findContractsByStateAndId(ContractStatusType.IN_PROGRESS, plbSession.getCompanyId());
			ManageContractBean mcb = contractService.buildManageContractBean(contracts);
			model.addObject("mngContract", mcb);
			CustomerSearchBean customerSearchBean = new CustomerSearchBean();
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
		}
		System.out.println("Total time=:" + (System.currentTimeMillis() - startTime)/1000);
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
			model.addObject("oldContract", mcb);
			CustomerSearchBean customerSearchBean = new CustomerSearchBean();
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
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
		ModelAndView model = new ModelAndView(MenuDefinition.DAILY_WORK.getName());
		if(paymentHistory == null){
			paymentHistory = new PaymentHistoryView();
		}
		try {
			List<PaymentHistoryDto> paymentDtos = paymentHistoryService.getHistories(plbSession.getCompanyId(), paymentHistory.getStartDate(), DateTimeUtil.addMoreDate(paymentHistory.getEndDate(), 1));
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
	public ModelAndView openHistory(HttpServletRequest request, HttpServletResponse response, UserActionHistoryView userHistoryView) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.HISTORY);
		ModelAndView model = new ModelAndView(MenuDefinition.HISTORY.getName());
		if(userHistoryView == null){
			userHistoryView = new UserActionHistoryView();
		}
		try {
			List<UserHistoryDto> paymentDtos = userHistoryService.getHistories(plbSession.getCompanyId(), userHistoryView.getStartDate(), userHistoryView.getEndDate());
			userHistoryView.setUserHistories(paymentDtos);
			userHistoryView.setCurrentCompany(plbSession.getCurrentCompany());
			model.addObject("historyView", userHistoryView);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		reloadCompanySession(model, request);
		
		return model;
	}
	
	@RequestMapping(value = { "/badContracts"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openBadContract(HttpServletRequest request, HttpServletResponse response) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.BAD_CONTRACT);
		ModelAndView model = new ModelAndView(MenuDefinition.BAD_CONTRACT.getName());
		reloadCompanySession(model, request);
		try {
			List<ContractDto> contracts = contractService.findContractsByStateAndId(ContractStatusType.BAD, plbSession.getCompanyId());
			ManageContractBean mcb = contractService.buildManageBadContractBean(contracts);
			model.addObject("badContract", mcb);
			CustomerSearchBean customerSearchBean = new CustomerSearchBean();
			model.addObject("csBean", customerSearchBean);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}
	
	private boolean isAdmin(HttpServletRequest request){
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		return plbSession.getUserAccount().getIsAdmin().equalsIgnoreCase(ConstantVariable.YES_OPTION);
	}
	
	private boolean isHeadOffice(HttpServletRequest request){
		return request.getSession().getAttribute(LoginController.SESSION_IS_HEAD_OFFICE) != null 
				&& request.getSession().getAttribute(LoginController.SESSION_IS_HEAD_OFFICE).toString().equalsIgnoreCase(ConstantVariable.YES_OPTION);
	}
	
	@RequestMapping(value = { "/companyFinancial"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCompanyFinancial(HttpServletRequest request, HttpServletResponse response, CompanyFinancialBean cfBean) throws BusinessException {
		if(!isAdmin(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_FINANCIAL);
		
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_FINANCIAL.getName());
		if(cfBean == null){
			cfBean = new CompanyFinancialBean();
		}
		cfBean.setCities(VietnamCityService.loadCities());
		reloadCompanySession(model, request);
		cfBean.setCompany(plbSession.getCurrentCompany());
		if(cfBean.getCompany().getStartDate() == null){
			cfBean.getCompany().setStartDate(DateTimeUtil.getCurrentDate());
		}
		model.addObject("cfBean", cfBean);
		return model;
	}
	
	@RequestMapping(value = { "/companyBranch"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCompanyBranch(HttpServletRequest request, HttpServletResponse response, CompanyBranchBean cbBean) throws BusinessException {
		if(!isAdmin(request) || !isHeadOffice(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_BRANCH);
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_BRANCH.getName());
		if(cbBean == null){
			cbBean = new CompanyBranchBean();
		}
		try {
			List<CompanyDto> companies = companyService.findAll();
			cbBean.setCompanies(companies);
			List<CompanyTypeDto> availableTypes = companyTypeService.findAll();
			cbBean.setAvailableCompanyTypes(availableTypes);
			cbBean.setCities(VietnamCityService.loadCities());
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		model.addObject("cbBean", cbBean);
		return model;
	}
	
	@RequestMapping(value = { "/companyProfit"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCompanyProfit(HttpServletRequest request, HttpServletResponse response, CompanyProfitBean cpBean) throws BusinessException {
		if(!isAdmin(request) || !isHeadOffice(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_PROFIT);
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_PROFIT.getName());
		try {
			List<CompanyDto> companies = companyService.findAll();
			List<StatisticInfo> stats = contractService.collectAllProfitStatistic(LocalDate.now().getYear());
			cpBean = paymentHistoryService.buildProfitStatistic(companies, stats);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		
		model.addObject("cpBean", cpBean);
		return model;
	}
	
	@RequestMapping(value = { "/mngUser"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openManageUser(HttpServletRequest request, HttpServletResponse response, ManageUserBean muBean, UserPasswordBean upBean) throws BusinessException {
		if(!isAdmin(request) || !isHeadOffice(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.MANAGE_USER);
		ModelAndView model = new ModelAndView(MenuDefinition.MANAGE_USER.getName());
		if(muBean == null){
			muBean = new ManageUserBean();
		}
		if(upBean == null){
			upBean = new UserPasswordBean();
		}
		try {
			List<UserAccountDto> users = userService.findAll();
			muBean.setUsers(users);
			List<CompanyDto> companies = companyService.findAll();
			muBean.setCompanies(companies);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		model.addObject("muBean", muBean);
		model.addObject("upBean", upBean);
		return model;
	}
	
	@RequestMapping(value = { "/userSetting"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openUserSetting(HttpServletRequest request, HttpServletResponse response, UserSettingBean usBean) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = new ModelAndView(MenuDefinition.USER_SETTING.getName());
		if(usBean == null){
			usBean = new UserSettingBean();
		}
		try {
			 UserAccountDto user = loginDelegate.getUserService().getUserByUsername(plbSession.getUserAccount().getUsername());
			 usBean.setUser(user);
			 plbSession.setUserAccount(user);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		model.addObject("usBean", usBean);
		return model;
	}
	
	@RequestMapping(value = { "/customerSearch"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView openCustomerSearch(HttpServletRequest request, HttpServletResponse response, CustomerSearchBean csBean) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = new ModelAndView(MenuDefinition.CUSTOMER_SEARCH.getName());
		plbSession.getMenuBean().makeActive(MenuDefinition.CUSTOMER_SEARCH);
		if(csBean == null){
			csBean = new CustomerSearchBean();
		}
		csBean.setCustomers(new ArrayList<>());
		model.addObject("csBean", csBean);
		return model;
	}
}