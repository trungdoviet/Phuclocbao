package vn.com.phuclocbao.controller;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.bean.StatisticInfo;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.exception.errorcode.PLBErrorCode;
import vn.com.phuclocbao.service.CompanyService;
import vn.com.phuclocbao.service.ContractService;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.validator.CompanyFinancialValidator;
import vn.com.phuclocbao.viewbean.CompanyBranchBean;
import vn.com.phuclocbao.viewbean.CompanyFinancialBean;
import vn.com.phuclocbao.viewbean.CompanyProfitBean;
import vn.com.phuclocbao.vo.UserActionParamVO.UserActionParamVOBuilder;



@Controller
@RequestMapping("/")
public class CompanyController extends BaseController{
	private static final String MSG_CREATE_COMPANY_FAILED = "msg.createCompanyFailed";
	private static final String MSG_CREATE_COMPANY_SUCCESS = "msg.createCompanySuccess";
	private static final String MSG_DELETE_COMPANY_SUCCESS = "msg.deleteCompanySuccess";
	private static final String MSG_DELETE_COMPANY_FAILED = "msg.deleteCompanyFailed";
	private static final String MSG_UPDATE_COMPANY_FINANCIAL_SUCCESS = "msg.updateCompanyFinancialSuccess";
	private static final String MSG_UPDATE_COMPANY_FINANCIAL_FAILED = "msg.updateCompanyFinancialFailed";
	@Autowired
	@Qualifier(value="contractService")
	ContractService contractService;
	@Autowired
	PaymentHistoryService paymentHistoryService;
	@Autowired
	@Qualifier(value="companyService")
	CompanyService companyService;
	@Autowired
	CompanyFinancialValidator financialValidator;
	private static org.apache.log4j.Logger logger = Logger.getLogger(CompanyController.class);
	
	@RequestMapping(value = { "/updateCompanyFinancial"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView updateFinancial(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("cfBean") @Validated CompanyFinancialBean cfBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) throws BusinessException {
		/*if(!isAdmin(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}*/
		ModelAndView model = null;
		financialValidator.validate(cfBean, result);
		if (result.hasErrors()) {
			model = new ModelAndView(MenuDefinition.COMPANY_FINANCIAL.getName());
			cfBean = populateFormData(request, cfBean);
			model.addObject("cfBean", cfBean);
		} else {
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			model = new ModelAndView(MenuDefinition.HOME.getRedirectCommand());
			try {
				cfBean.getCompany().setId(plbSession.getCompanyId());
				CompanyDto savedCompany = companyService.mergeFinancial(cfBean.getCompany(), UserActionParamVOBuilder.createBuilder()
																													.setUsername(plbSession.getUserAccount().getUsername())
																													.build());
				if(savedCompany != null){
					plbSession.setCurrentCompany(savedCompany);
					if(plbSession.getCompanyId().equals(plbSession.getUserAccount().getCompanyEntity().getId())){
						plbSession.getUserAccount().setCompanyEntity(savedCompany);
					}
					showSucessAlert(redirectAttributes, MSG_UPDATE_COMPANY_FINANCIAL_SUCCESS);
				}
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
				showErrorAlert(redirectAttributes, MSG_UPDATE_COMPANY_FINANCIAL_FAILED);
			}
		}
		return model;
	}
	private CompanyFinancialBean populateFormData(HttpServletRequest request, CompanyFinancialBean cfBean ){
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		
		if(cfBean == null){
			cfBean = new CompanyFinancialBean();
		}
		cfBean.setCities(VietnamCityService.loadCities());
		cfBean.setCompany(plbSession.getCurrentCompany());
		return cfBean;
	}
	
	@RequestMapping(value = { "/addBranch"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView createBranch(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("cbBean") CompanyBranchBean cbBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) throws BusinessException {
		if(!isAdmin(request) || !isHeadOffice(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = null;
		model = new ModelAndView(MenuDefinition.COMPANY_BRANCH.getRedirectCommand());
		try {
			
			CompanyDto persistedDto = companyService.persist(cbBean.getCompany(), UserActionParamVOBuilder.createBuilder()
																											.setUsername(plbSession.getUserAccount().getUsername())
																											.setCompanyId(plbSession.getCompanyId())
																											.setCompanyName(plbSession.getCurrentCompany().getName())
																											.build());
			if(persistedDto !=null){
				showSucessAlert(redirectAttributes, MSG_CREATE_COMPANY_SUCCESS);
			} else {
				showErrorAlert(redirectAttributes, MSG_CREATE_COMPANY_FAILED);
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(redirectAttributes, MSG_CREATE_COMPANY_FAILED);
		}
		return model;
	}
	
	@RequestMapping(value = { "/companyBranch/{id}/delete"}, method = RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView deleteBranch(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("id") int id,  final RedirectAttributes redirectAttributes) throws BusinessException {
		if(!isAdmin(request) || !isHeadOffice(request)){
			throw new BusinessException(PLBErrorCode.USER_NOT_AUTHORIZED.name());
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		ModelAndView model = null;
		model = new ModelAndView(MenuDefinition.COMPANY_BRANCH.getRedirectCommand());
		try {
			
			boolean isOK = companyService.remove(id, UserActionParamVOBuilder.createBuilder().setUsername(plbSession.getUserAccount().getUsername())
																							.setCompanyId(plbSession.getCompanyId())
																							.setCompanyName(plbSession.getCurrentCompany().getName())
																							.build());
			if(isOK){
				showSucessAlert(redirectAttributes, MSG_DELETE_COMPANY_SUCCESS);
			} else {
				showErrorAlert(redirectAttributes, MSG_DELETE_COMPANY_FAILED);
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(redirectAttributes, MSG_DELETE_COMPANY_FAILED);
		}
		return model;
	}
	
	@RequestMapping(value = { "/filterProfit"}, method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterProfitByYear(HttpServletRequest request, HttpServletResponse response, CompanyProfitBean cpBean) {
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		plbSession.getMenuBean().makeActive(MenuDefinition.COMPANY_PROFIT);
		ModelAndView model = new ModelAndView(MenuDefinition.COMPANY_PROFIT.getName());
		try {
			List<CompanyDto> companies = companyService.findAll();
			int selectedYear = cpBean.getYear();
			List<StatisticInfo> stats = contractService.collectAllProfitStatistic(selectedYear);
			cpBean = paymentHistoryService.buildProfitStatistic(companies, stats);
			cpBean.setYear(selectedYear);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		
		model.addObject("cpBean", cpBean);
		return model;
	}

}