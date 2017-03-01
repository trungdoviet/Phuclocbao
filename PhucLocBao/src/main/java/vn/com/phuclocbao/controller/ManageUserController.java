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
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.UserService;
import vn.com.phuclocbao.viewbean.ManageUserBean;
import vn.com.phuclocbao.viewbean.UserPasswordBean;



@Controller
@RequestMapping("/")
public class ManageUserController extends BaseController {
	private static final String MSG_CREATE_USER_FAILED = "msg.createUserFailed";
	private static final String MSG_CREATE_USER_SUCESS = "msg.createUserSucess";
	private static final String MSG_CHANGE_PASS_USER_FAILED = "msg.changePassUserFailed";
	private static final String MSG_CHANGE_PASS_USER_SUCESS = "msg.changePassUserSucess";
	private static org.apache.log4j.Logger logger = Logger.getLogger(ManageUserController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/addUser"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("muBean")  ManageUserBean muBean,  final RedirectAttributes redirectAttributes,
			BindingResult result, SessionStatus status) {
		ModelAndView model = null;
		model = new ModelAndView(MenuDefinition.MANAGE_USER.getRedirectCommand());
		try {
			boolean isOk = userService.addNewUser(muBean.getUser());
			if(isOk){
				showSucessAlert(redirectAttributes, MSG_CREATE_USER_SUCESS);
			} else {
				showErrorAlert(redirectAttributes, MSG_CREATE_USER_FAILED);
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(redirectAttributes, MSG_CREATE_USER_FAILED);
		}
		return model;
	}
	
	@RequestMapping(value = { "/changePassword"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView changePassword(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("cpBean")  UserPasswordBean cpBean,  final RedirectAttributes redirectAttributes,
			BindingResult result, SessionStatus status) {
		ModelAndView model = null;
		model = new ModelAndView(MenuDefinition.MANAGE_USER.getRedirectCommand());
		try {
			
			boolean isOk = userService.updatePassword(Integer.valueOf(cpBean.getUserid()), cpBean.getNewPassword());
			if(isOk){
				showSucessAlert(redirectAttributes, MSG_CHANGE_PASS_USER_SUCESS);
			} else {
				showErrorAlert(redirectAttributes, MSG_CHANGE_PASS_USER_FAILED);
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(redirectAttributes, MSG_CHANGE_PASS_USER_FAILED);
		}
		return model;
	}

}