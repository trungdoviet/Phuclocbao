package vn.com.phuclocbao.controller;
import java.util.List;

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

import vn.com.phuclocbao.bean.PLBSession;
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.dto.UserHistoryDto;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.service.UserHistoryService;
import vn.com.phuclocbao.viewbean.PaymentHistoryView;
import vn.com.phuclocbao.viewbean.UserActionHistoryView;



@Controller
@RequestMapping("/")
public class UserHistoryController extends BaseController {
	private static final String MSG_ERROR_WHEN_OPEN = "msg.errorWhenOpen";
	private static org.apache.log4j.Logger logger = Logger.getLogger(UserHistoryController.class);
	
	@Autowired
	UserHistoryService userHistoryService;
	
	@RequestMapping(value = { "/filterUserHistory"}, params="search", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterPaymentHistory(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("historyView") UserActionHistoryView userHistoryView, 
			BindingResult result, SessionStatus status) {
		
		ModelAndView model = new ModelAndView(MenuDefinition.HISTORY.getName());
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
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
		return model;
	}

}