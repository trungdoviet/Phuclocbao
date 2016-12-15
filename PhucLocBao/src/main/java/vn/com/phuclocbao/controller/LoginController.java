package vn.com.phuclocbao.controller;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import vn.com.phuclocbao.delegator.LoginDelegator;
import vn.com.phuclocbao.validator.LoginUserValidator;
import vn.com.phuclocbao.viewbean.LoginBean;


@Controller
public class LoginController
{
		@Autowired
		private LoginDelegator loginDelegate;
		@Autowired
		LoginUserValidator validator;
		
		@RequestMapping(value="/",method=RequestMethod.GET)
		public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response, LoginBean loginBean)
		{
			ModelAndView model = new ModelAndView("index");
			model.addObject("loginBean", loginBean);
			return model;
		}
		
		@RequestMapping(value="/login",method=RequestMethod.POST)
		public ModelAndView executeLogin(HttpServletRequest request, HttpServletResponse response, 
														@ModelAttribute("loginBean")LoginBean loginBean, 
														BindingResult result, SessionStatus status){
				ModelAndView model= null;
				//Validation code
			    validator.validate(loginBean, result);
			     
			    //Check validation errors
			    if (result.hasErrors()) {
			    	model = new ModelAndView("index");
			        return model;
			    }
			    
				try
				{
						boolean isValidUser = loginDelegate.isValidUser(loginBean.getUsername(), loginBean.getPassword());
						if(isValidUser)
						{
								System.out.println("User Login Successful");
								request.setAttribute("loggedInUser", loginBean.getUsername());
								model = new ModelAndView("home");
						}
						else
						{
								model = new ModelAndView("index");
								request.setAttribute("message", "Invalid credentials!!");
						}

				}
				catch(Exception e)
				{
						e.printStackTrace();
				}

				return model;
		}
}
