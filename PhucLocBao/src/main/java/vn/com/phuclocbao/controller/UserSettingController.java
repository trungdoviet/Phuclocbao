package vn.com.phuclocbao.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.delegator.LoginDelegator;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.service.UserHistoryService;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.validator.OldContractValidator;
import vn.com.phuclocbao.validator.UserSettingValidator;
import vn.com.phuclocbao.viewbean.PaymentHistoryView;
import vn.com.phuclocbao.viewbean.UserActionHistoryView;
import vn.com.phuclocbao.viewbean.UserSettingBean;



@Controller
@RequestMapping("/")
public class UserSettingController extends BaseController {
	private static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	private static org.apache.log4j.Logger logger = Logger.getLogger(UserSettingController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	@Qualifier("userSettingValidator")
	UserSettingValidator userSettingValidator;
	
	@RequestMapping(value = { "/saveUserSetting"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView saveUserSetting(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("usBean")  UserSettingBean usBean, 
			BindingResult result, SessionStatus status) {
		
		ModelAndView model = null;
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		String userFullname = usBean.getUser().getFullname();
		String userEmail = usBean.getUser().getEmail();
		usBean.setUser(plbSession.getUserAccount());
		usBean.getUser().setFullname(userFullname);
		usBean.getUser().setEmail(userEmail);
		userSettingValidator.validate(usBean, result);
		if(result.hasErrors()){
			model = new ModelAndView(MenuDefinition.USER_SETTING.getName());
			usBean.setUser(plbSession.getUserAccount());
			model.addObject("usBean", usBean);

		} else {
			try {
				if(StringUtils.isEmpty(usBean.getOldPassword())){
					usBean.getUser().setPassword(StringUtils.EMPTY);
				} else {
					usBean.getUser().setPassword(usBean.getNewPassword());
				}
				UserAccountDto user = userService.saveUser(usBean.getUser());
				model = new ModelAndView(MenuDefinition.HOME.getRedirectCommand());
				plbSession.setUserAccount(user);
			} catch (BusinessException e) {
				logger.error(e);
				e.printStackTrace();
				model = new ModelAndView(MenuDefinition.HOME.getName());
				showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
			}
		}
		return model;
	}

}