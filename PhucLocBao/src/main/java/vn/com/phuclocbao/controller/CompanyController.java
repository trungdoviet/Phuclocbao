package vn.com.phuclocbao.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.CompanyDto;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.CompanyService;
import vn.com.phuclocbao.service.VietnamCityService;
import vn.com.phuclocbao.validator.CompanyFinancialValidator;
import vn.com.phuclocbao.viewbean.CompanyFinancialBean;
import vn.com.phuclocbao.vo.UserActionParamVO.UserActionParamVOBuilder;



@Controller
@RequestMapping("/")
public class CompanyController extends BaseController{
	private static final String MSG_UPDATE_COMPANY_FINANCIAL_SUCCESS = "msg.updateCompanyFinancialSuccess";
	private static final String MSG_UPDATE_COMPANY_FINANCIAL_FAILED = "msg.updateCompanyFinancialFailed";
	@Autowired
	@Qualifier(value="companyService")
	CompanyService companyService;
	@Autowired
	CompanyFinancialValidator financialValidator;
	private static org.apache.log4j.Logger logger = Logger.getLogger(CompanyController.class);
	
	@RequestMapping(value = { "/updateCompanyFinancial"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView createContract(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("cfBean") @Validated CompanyFinancialBean cfBean, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
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

}