package vn.com.phuclocbao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.enums.AlertType;
import vn.com.phuclocbao.util.ConstantVariable;
import vn.com.phuclocbao.util.MessageBundleUtil;

public class BaseController {
	protected static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	public void showErrorAlert(final RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
	}

	public void showSucessAlert(final RedirectAttributes redirectAttributes, String message) {
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		redirectAttributes.addFlashAttribute(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.SUCCESS.getName());
	}
	
	public void showErrorAlert(ModelAndView model, String message) {
		model.addObject(ConstantVariable.ATTR_FLASH_MSG, MessageBundleUtil.getMessage(message));
		model.addObject(ConstantVariable.ATTR_FLASH_MSG_CSS, AlertType.DANGER.getName());
	}
	public boolean isAdmin(HttpServletRequest request){
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		return plbSession.getUserAccount().getIsAdmin().equalsIgnoreCase(ConstantVariable.YES_OPTION);
	}
	
	public boolean isHeadOffice(HttpServletRequest request){
		return request.getSession().getAttribute(LoginController.SESSION_IS_HEAD_OFFICE) != null 
				&& request.getSession().getAttribute(LoginController.SESSION_IS_HEAD_OFFICE).toString().equalsIgnoreCase(ConstantVariable.YES_OPTION);
	}
}
