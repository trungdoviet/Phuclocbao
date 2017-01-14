package vn.com.phuclocbao.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.delegator.LoginDelegator;
import vn.com.phuclocbao.dto.UserAccountDto;
import vn.com.phuclocbao.enums.AlertType;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.MessageBundleUtil;
import vn.com.phuclocbao.validator.LoginUserValidator;
import vn.com.phuclocbao.viewbean.LoginBean;

@Controller
public class LoginController {
		private static org.apache.log4j.Logger logger = Logger.getLogger(LoginController.class);
		@Autowired
		private LoginDelegator loginDelegate;
		@Autowired
		LoginUserValidator validator;
	
		
		@RequestMapping(value={"/", "/index", "/login"},method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
		public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean){
			ModelAndView model = null;
			PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			if(plbSession != null && plbSession.getUserAccount() != null){
				model = new ModelAndView("redirect:/home");
				request.setAttribute("loggedInUser", plbSession.getUserAccount().getFullname());
			} else {
				model = new ModelAndView("index");
				model.addObject("loginBean", loginBean);
			}
			return model;
		}
		
		@RequestMapping(value="/login",method=RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
		public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, 
														@ModelAttribute("loginBean")LoginBean loginBean, 
														BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes){
				ModelAndView model= null;
				PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
				if(plbSession != null && plbSession.getUserAccount() != null){
					model = new ModelAndView("home");
					request.setAttribute("loggedInUser", plbSession.getUserAccount().getFullname());
					return model;
				}
				
				//Validation code
			    validator.validate(loginBean, result);
			     
			    //Check validation errors
			    if (result.hasErrors()) {
			    	model = new ModelAndView("index");
			        return model;
			    }
			    
				try{
						boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
						if(isValidUser){
								UserAccountDto userAccount = loginDelegate.getUserService().getUserByUsername(loginBean.getUsername());
								request.setAttribute("loggedInUser", userAccount.getFullname());
								plbSession = new PLBSession();
								plbSession.setUserAccount(userAccount);
								plbSession.setCurrentCompany(userAccount.getCompanyEntity());
								request.getSession().setAttribute(PLBSession.SESSION_ATTRIBUTE_KEY, plbSession);
								System.out.println("User Login Successful with username:" + userAccount.getFullname());
								model = new ModelAndView("redirect:/home");
								redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage("msg.welcomeLogin") + userAccount.getFullname());
								redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.SUCCESS.getName());
						}
						else {
								model = new ModelAndView("index");
								request.setAttribute("message", MessageBundleUtil.getMessage("error.userLoginFailed"));
						}

				}catch(Exception e) {
						e.printStackTrace();
				}

				return model;
		}
		
		@RequestMapping(value="/logout",method=RequestMethod.GET, produces="application/x-www-form-urlencoded;charset=UTF-8")
		public ModelAndView performLogout(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean){
			ModelAndView model = null;
			request.getSession().removeAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
			System.out.println("User logout");
			model = new ModelAndView("redirect:/index");
			return model;
		}
}
