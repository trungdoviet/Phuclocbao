package vn.com.phuclocbao.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import vn.com.phuclocbao.dto.PaymentHistoryDto;
import vn.com.phuclocbao.enums.MenuDefinition;
import vn.com.phuclocbao.enums.PaymentHistoryType;
import vn.com.phuclocbao.exception.BusinessException;
import vn.com.phuclocbao.service.PaymentHistoryService;
import vn.com.phuclocbao.util.DateTimeUtil;
import vn.com.phuclocbao.viewbean.PaymentHistoryView;



@Controller
@RequestMapping("/")
public class PaymentHistoryController extends BaseController {
	
	private static final String MSG_CREATE_PAYMENT_FAILED = "msg.createPaymentFailed";

	private static final String MSG_CREATE_PAYMENT_SUCESS = "msg.createPaymentSucess";

	private static org.apache.log4j.Logger logger = Logger.getLogger(PaymentHistoryController.class);
	
	@Autowired
	PaymentHistoryService paymentHistoryService;
	
	@RequestMapping(value = { "/filterHistory"}, params="search", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView filterPaymentHistory(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("historyView") PaymentHistoryView paymentHistory, 
			BindingResult result, SessionStatus status) {
		
		ModelAndView model = new ModelAndView(MenuDefinition.DAILY_WORK.getName());
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		try {
			List<PaymentHistoryDto> paymentDtos = paymentHistoryService.getHistories(plbSession.getCompanyId(), paymentHistory.getStartDate(), DateTimeUtil.addMoreDate(paymentHistory.getEndDate(), 1));
			paymentHistory.setPaymentHistories(paymentDtos);
			model.addObject("historyView", paymentHistory);
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(model, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}
	
	@RequestMapping(value = { "/addPayment"}, params="save", method = RequestMethod.POST, produces="application/x-www-form-urlencoded;charset=UTF-8")
	public ModelAndView addPayment(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute("historyView") PaymentHistoryView paymentHistory, 
			BindingResult result, SessionStatus status, final RedirectAttributes redirectAttributes) {
		
		ModelAndView model = new ModelAndView(MenuDefinition.DAILY_WORK.getRedirectCommand());
		if(paymentHistory.getPaymentAmount() == 0 || StringUtils.isEmpty(paymentHistory.getAdditionalInfo())){
			return model;
		}
		PLBSession plbSession = (PLBSession) request.getSession().getAttribute(PLBSession.SESSION_ATTRIBUTE_KEY);
		try {
			PaymentHistoryDto paymentDto = paymentHistoryService.saveNewPayment(plbSession.getWorkingCompanyId(), 
					PaymentHistoryType.getByType(paymentHistory.getPaymentType()), 
					paymentHistory.getPaymentAmount(), paymentHistory.getAdditionalInfo(), plbSession.getUserAccount().getUsername());
			if(paymentDto != null){
				showSucessAlert(redirectAttributes, MSG_CREATE_PAYMENT_SUCESS);
			} else {
				showErrorAlert(redirectAttributes, MSG_CREATE_PAYMENT_FAILED);
			}
		} catch (BusinessException e) {
			logger.error(e);
			e.printStackTrace();
			showErrorAlert(redirectAttributes, MSG_ERROR_WHEN_OPEN);
		}
		return model;
	}

}